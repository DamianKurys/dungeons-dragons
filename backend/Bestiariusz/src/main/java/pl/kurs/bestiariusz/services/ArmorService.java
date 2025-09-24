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

/**
 * Service layer for managing {@link Armor} entities.
 * Provides business logic for creating, retrieving, updating, and filtering armors.
 */
@Service
@RequiredArgsConstructor
public class ArmorService {

    private final ArmorRepository armorRepository;

    /**
     * Creates a new armor.
     *
     * @param command the request payload containing armor details
     */
    public void createArmor(CreateArmorCommand command) {
        Armor armor = command.toArmor();
        armorRepository.save(armor);
    }

    /**
     * Retrieves an armor by its ID.
     *
     * @param id the armor ID
     * @return the armor matching the given ID
     * @throws ArmorNotFoundException if no armor exists with the provided ID
     */
    public Armor getArmor(Long id) throws ArmorNotFoundException {
        return armorRepository.findById(id)
                .orElseThrow(() -> new ArmorNotFoundException(id));
    }


    /**
     * Retrieves all armors with the specified rarity.
     *
     * @param rarity the rarity to filter armors by
     * @return list of armors with the given rarity
     */
    public List<Armor> findAllArmorsByRarity(Rarity rarity) {
        return armorRepository.findAllByRarity(rarity);
    }

    /**
     * Updates an existing armor by its ID.
     *
     * @param id      the ID of the armor to update
     * @param command the update request payload
     * @return the updated armor
     * @throws ArmorNotFoundException if no armor exists with the provided ID
     */
    public Armor updateArmor(Long id, UpdateArmorCommand command) throws ArmorNotFoundException {
        Armor armor = armorRepository.findById(id)
                .orElseThrow(() -> new ArmorNotFoundException(id));
        command.update(armor);
        return armorRepository.save(armor);
    }


    /**
     * Retrieves all armors.
     *
     * @return list of all armors
     */
    public List<Armor> getAllArmor() {
        return armorRepository.findAll();
    }
}
