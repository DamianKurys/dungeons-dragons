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

@RestController
@RequestMapping("/weapon")
@RequiredArgsConstructor
public class WeaponController {

    private static final String WEAPON_CREATED = "Weapon created sucessfully";
    private final WeaponService weaponService;

    @PostMapping("/create")
    public ResponseEntity<String> addWeapon(@RequestBody CreateWeaponCommand command) {
        weaponService.addWeapon(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(WEAPON_CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weapon> getWeapon(@PathVariable Long id) throws WeaponNotFoundException {
        Weapon byId = weaponService.findById(id);
        return ResponseEntity.ok(byId);

    }

    @GetMapping
    public ResponseEntity<List<Weapon>> getAllWeapons() {
        List<Weapon> allWeapons = weaponService.getAllWeapons();
        return ResponseEntity.ok(allWeapons);
    }

    @GetMapping("/rarity/{rarity}")
    public ResponseEntity<List<Weapon>> findAllWeaponsByRarity(@PathVariable Rarity rarity) {
        List<Weapon> allWeaponsByRarity = weaponService.findAllWeaponsByRarity(rarity);
        return ResponseEntity.ok(allWeaponsByRarity);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Weapon> updateWeapon(@PathVariable Long id, UpdateWeaponCommand command) throws WeaponNotFoundException {
        Weapon weapon = weaponService.updateWeapon(id, command);
        return ResponseEntity.ok(weapon);
    }
}

