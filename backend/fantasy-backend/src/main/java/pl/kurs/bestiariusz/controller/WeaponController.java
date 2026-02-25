package pl.kurs.bestiariusz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.bestiariusz.commands.CreateWeaponCommand;
import pl.kurs.bestiariusz.commands.UpdateWeaponCommand;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.exceptions.WeaponNotFoundException;
import pl.kurs.bestiariusz.models.Weapon;
import pl.kurs.bestiariusz.services.WeaponService;

import java.util.List;

/**
 * Rest controller for managing ({@link Weapon} entities
 * Provides endpoints for creating, retrieving, updating and filtering weapons
 */
@RestController
@RequestMapping("/weapon")
@RequiredArgsConstructor
public class WeaponController {

    private static final String WEAPON_CREATED = "Weapon created sucessfully";
    private final WeaponService weaponService;

    /**
     * Creates a new weapon
     *
     * @param command command data required to create a weapon
     * @return response with created resource and message
     */
    @PostMapping("/create")
    public ResponseEntity<String> addWeapon(@RequestBody CreateWeaponCommand command) {
        weaponService.addWeapon(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(WEAPON_CREATED);
    }

    /**
     * Returns a weapon by id.
     *
     * @param id weapon identifier
     * @return the weapon or 404 if not found
     * @throws WeaponNotFoundException if no weapon exists with provided ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Weapon> getWeapon(@PathVariable Long id) throws WeaponNotFoundException {
        Weapon byId = weaponService.findById(id);
        return ResponseEntity.ok(byId);
    }

    /**
     * Returns list of all weapons
     *
     * @return response containing the list of all weapons
     */
    @GetMapping
    public ResponseEntity<List<Weapon>> getAllWeapons() {
        List<Weapon> allWeapons = weaponService.getAllWeapons();
        return ResponseEntity.ok(allWeapons);
    }

    /**
     * Retrieves all weapons by a given rarity
     *
     * @param rarity rarity to filter weapons by
     * @return response containing the list of all weapons
     */
    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<Weapon>> findAllWeaponsByRarity(@PathVariable Rarity rarity) {
        List<Weapon> allWeaponsByRarity = weaponService.findAllWeaponsByRarity(rarity);
        return ResponseEntity.ok(allWeaponsByRarity);
    }

    /**
     * Updates an existing weapon by its ID.
     *
     * @param id      the ID of the weapon to update
     * @param command the update request payload
     * @return a response containing the updated weapon
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Weapon> updateWeapon(@PathVariable Long id, UpdateWeaponCommand command) throws WeaponNotFoundException {
        Weapon weapon = weaponService.updateWeapon(id, command);
        return ResponseEntity.ok(weapon);
    }
}

