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

@RestController
@RequestMapping("/armor")
@RequiredArgsConstructor
public class ArmorController {

    private static final String ARMOR_CREATED = "Armor created sucessfully";
    private final ArmorService armorService;

    @PostMapping("/create")
    public ResponseEntity<String> addArmor(@RequestBody CreateArmorCommand command) {
        armorService.createArmor(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(ARMOR_CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Armor> getArmor(@PathVariable Long id) throws ArmorNotFoundException {
        Armor armor = armorService.getArmor(id);
        return ResponseEntity.ok(armor);
    }

    @GetMapping
    public ResponseEntity<List<Armor>> getAllArmors() {
        List<Armor> allArmors = armorService.getAllArmor();
        return ResponseEntity.ok(allArmors);
    }

    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<Armor>> getArmorByRarity(@PathVariable Rarity rarity) {
        List<Armor> allArmorsByRarity = armorService.findAllArmorsByRarity(rarity);
        return ResponseEntity.ok(allArmorsByRarity);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Armor> updateArmor(@PathVariable Long id, @RequestBody UpdateArmorCommand command) throws ArmorNotFoundException {
        Armor armor = armorService.updateArmor(id, command);
        return ResponseEntity.ok(armor);
    }
}
