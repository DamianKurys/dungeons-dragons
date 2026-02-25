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

/**
 * Service layer for managing {@link Monster} entities.
 * Provides business logic for creating, retrieving, updating, and filtering monsters
 * by region or boss status.
 */
@Service
@RequiredArgsConstructor
public class MonsterService {


    private final MonsterRepository monsterRepository;

    /**
     * Creates a new monster.
     *
     * @param command the request payload containing monster details
     */
    public void addMonster(CreateMonsterCommand command) {
        Monster monster = command.createMonster();
        monsterRepository.save(monster);
    }

    /**
     * Retrieves a monster by its ID.
     *
     * @param id the monster ID
     * @return the monster matching the given ID
     * @throws MonsterNotFoundException if no monster exists with the provided ID
     */
    public Monster findMonsterById(Long id) throws MonsterNotFoundException {
        return monsterRepository.findById(id)
                .orElseThrow(() -> new MonsterNotFoundException(id));

    }

    /**
     * Retrieves all monsters in the given region.
     *
     * @param region the region to filter monsters by
     * @return list of monsters in the specified region (may be empty)
     */
    public List<Monster> findAllMonstersByRegion(Region region) {
        return monsterRepository.findAllByRegion(region);
    }

    /**
     * Updates an existing monster by its ID.
     *
     * @param id      the ID of the monster to update
     * @param command the update request payload
     * @return the updated monster
     * @throws MonsterNotFoundException if no monster exists with the provided ID
     */
    public Monster updateMonster(Long id, UpdateMonsterCommand command) throws MonsterNotFoundException {
        Monster monster = monsterRepository.findById(id)
                .orElseThrow(() -> new MonsterNotFoundException(id));
        command.update(monster);
        return monsterRepository.save(monster);
    }

    /**
     * Finds monsters optionally filtered by region and/or boss flag.
     *
     * @param region optional region filter
     * @param boss   optional boss flag filter
     * @return list of monsters matching the given filters
     */
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
