package pl.kurs.bestiariusz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.bestiariusz.commands.CreateWeaponCommand;
import pl.kurs.bestiariusz.commands.UpdateWeaponCommand;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.exceptions.WeaponNotFoundException;
import pl.kurs.bestiariusz.models.Weapon;
import pl.kurs.bestiariusz.repository.WeaponRepository;

import java.util.List;

/**
 * Service layer for managing {@link Weapon} entities.
 * Provides business logic for creating, retrieving, updating, and filtering weapons.
 */
@Service
@RequiredArgsConstructor
public class WeaponService {

    private final WeaponRepository weaponRepository;


    /**
     * Creates a new weapon.
     *
     * @param command the request payload containing weapon details
     */
    public void addWeapon(CreateWeaponCommand command) {
        Weapon weapon = command.toWeapon();
        weaponRepository.save(weapon);
    }


    /**
     * Retrieves a weapon by its ID.
     *
     * @param id the weapon ID
     * @return the weapon matching the given ID
     * @throws WeaponNotFoundException if no weapon exists with the provided ID
     */
    public Weapon findById(Long id) throws WeaponNotFoundException {
        return weaponRepository.findById(id)
                .orElseThrow(() -> new WeaponNotFoundException(id));
    }


    /**
     * Retrieves all weapons with the specified rarity.
     *
     * @param rarity the rarity to filter weapons by
     * @return list of weapons with the given rarity (may be empty)
     */
    public List<Weapon> findAllWeaponsByRarity(Rarity rarity) {
        return weaponRepository.findAllByRarity(rarity);
    }

    /**
     * Updates an existing weapon by its ID.
     *
     * @param id      the ID of the weapon to update
     * @param command the update request payload
     * @return the updated weapon
     * @throws WeaponNotFoundException if no weapon exists with the provided ID
     */
    public Weapon updateWeapon(Long id, UpdateWeaponCommand command) throws WeaponNotFoundException {
        Weapon weapon = weaponRepository.findById(id)
                .orElseThrow(() -> new WeaponNotFoundException(id));
        command.update(weapon);
        return weaponRepository.save(weapon);
    }


    /**
     * Retrieves all weapons.
     *
     * @return list of all weapons (may be empty)
     */
    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }
}
