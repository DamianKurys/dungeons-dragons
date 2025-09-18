package pl.kurs.bestiariusz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.bestiariusz.commands.CreateMonsterCommand;
import pl.kurs.bestiariusz.commands.UpdateMonsterCommand;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.exceptions.MonsterNotFoundException;
import pl.kurs.bestiariusz.models.Monster;
import pl.kurs.bestiariusz.repository.MonsterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonsterService {


    private final MonsterRepository monsterRepository;

    public void addMonster(CreateMonsterCommand command) {
        Monster monster = command.createMonster();
        monsterRepository.save(monster);
    }

    public Monster findMonsterById(Long id) throws MonsterNotFoundException {
        return monsterRepository.findById(id)
                .orElseThrow(() -> new MonsterNotFoundException(id));

    }

    public List<Monster> findAllMonstersByRegion(Region region) {
        return monsterRepository.findAllByRegion(region);
    }

    public Monster updateMonster(Long id, UpdateMonsterCommand command) throws MonsterNotFoundException {
        Monster monster = monsterRepository.findById(id)
                .orElseThrow(() -> new MonsterNotFoundException(id));
        command.update(monster);
        return monsterRepository.save(monster);
    }

    public List<Monster> findBoss(Region region, Boolean boss) {
        if (region != null && Boolean.TRUE.equals(boss))
            return monsterRepository.findAllByRegionAndBossTrue(region);
        if (region != null && boss == null)
            return monsterRepository.findAllByRegion(region);
        if (region == null && Boolean.TRUE.equals(boss))
            return monsterRepository.findAllByBossTrue();
        if (region == null && Boolean.FALSE.equals(boss))
            return monsterRepository.findAllByBoss(false);
        return monsterRepository.findAll();

    }
}
