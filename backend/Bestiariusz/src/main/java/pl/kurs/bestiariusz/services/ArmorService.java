package pl.kurs.bestiariusz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.bestiariusz.commands.CreateArmorCommand;
import pl.kurs.bestiariusz.commands.UpdateArmorCommand;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.exceptions.ArmorNotFoundException;
import pl.kurs.bestiariusz.models.Armor;
import pl.kurs.bestiariusz.repository.ArmorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArmorService {

    private final ArmorRepository armorRepository;

    public void createArmor(CreateArmorCommand command) {
        Armor armor = command.toArmor();
        armorRepository.save(armor);
    }

    public Armor getArmor(Long id) throws ArmorNotFoundException {
        return armorRepository.findById(id)
                .orElseThrow(() -> new ArmorNotFoundException(id));
    }

    public List<Armor> findAllArmorsByRarity(Rarity rarity) {
        return armorRepository.findAllByRarity(rarity);
    }

    public Armor updateArmor(Long id, UpdateArmorCommand command) throws ArmorNotFoundException {
        Armor armor = armorRepository.findById(id)
                .orElseThrow(() -> new ArmorNotFoundException(id));
        command.update(armor);
        return armorRepository.save(armor);
    }

    public List<Armor> getAllArmor() {
        return armorRepository.findAll();
    }
}
