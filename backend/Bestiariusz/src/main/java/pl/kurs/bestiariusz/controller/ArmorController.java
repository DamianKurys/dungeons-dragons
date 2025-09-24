package pl.kurs.bestiariusz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.bestiariusz.commands.CreateArmorCommand;
import pl.kurs.bestiariusz.commands.UpdateArmorCommand;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.exceptions.ArmorNotFoundException;
import pl.kurs.bestiariusz.models.Armor;
import pl.kurs.bestiariusz.services.ArmorService;

import java.util.List;

/**
 * REST controller for managing {@link Armor} entities.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and filtering armors
 * by rarity.
 */
@RestController
@RequestMapping("/armor")
@RequiredArgsConstructor
public class ArmorController {

    private static final String ARMOR_CREATED = "Armor created sucessfully";
    private final ArmorService armorService;


    /**
     * Creates a new armor.
     *
     * @param command the request payload containing armor details
     * @return a {@link ResponseEntity} with status 201 and a confirmation message
     */
    @PostMapping("/create")
    public ResponseEntity<String> addArmor(@RequestBody CreateArmorCommand command) {
        armorService.createArmor(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ARMOR_CREATED);
    }


    /**
     * Retrieves an armor by its ID.
     *
     * @param id the armor ID
     * @return the armor matching the given ID
     * @throws ArmorNotFoundException if no armor exists with the provided ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Armor> getArmor(@PathVariable Long id) throws ArmorNotFoundException {
        Armor armor = armorService.getArmor(id);
        return ResponseEntity.ok(armor);
    }

    /**
     * Retrieves all armors.
     *
     * @return a {@link ResponseEntity} containing the list of all armors
     */
    @GetMapping
    public ResponseEntity<List<Armor>> getAllArmors() {
        List<Armor> allArmors = armorService.getAllArmor();
        return ResponseEntity.ok(allArmors);
    }

    /**
     * Retrieves all armors by a given rarity.
     *
     * @param rarity the rarity to filter armors by
     * @return a {@link ResponseEntity} containing the list of armors with the given rarity
     */
    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<Armor>> getArmorByRarity(@PathVariable Rarity rarity) {
        List<Armor> allArmorsByRarity = armorService.findAllArmorsByRarity(rarity);
        return ResponseEntity.ok(allArmorsByRarity);
    }


    /**
     * Updates an existing armor by its ID.
     *
     * @param id      the ID of the armor to update
     * @param command the update request payload
     * @return a {@link ResponseEntity} containing the updated armor
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Armor> updateArmor(@PathVariable Long id, @RequestBody UpdateArmorCommand command) throws ArmorNotFoundException {
        Armor armor = armorService.updateArmor(id, command);
        return ResponseEntity.ok(armor);
    }
}
