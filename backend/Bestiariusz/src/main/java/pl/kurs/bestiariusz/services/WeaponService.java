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

@Service
@RequiredArgsConstructor
public class WeaponService {

    private final WeaponRepository weaponRepository;

    public void addWeapon(CreateWeaponCommand command) {
        Weapon weapon = command.toWeapon();
        weaponRepository.save(weapon);
    }

    public Weapon findById(Long id) throws WeaponNotFoundException {
        return weaponRepository.findById(id)
                .orElseThrow(() -> new WeaponNotFoundException(id));
    }

    public List<Weapon> findAllWeaponsByRarity(Rarity rarity) {
        return weaponRepository.findAllByRarity(rarity);
    }

    public Weapon updateWeapon(Long id, UpdateWeaponCommand command) throws WeaponNotFoundException {
        Weapon weapon = weaponRepository.findById(id)
                .orElseThrow(() -> new WeaponNotFoundException(id));
        command.update(weapon);
        return weaponRepository.save(weapon);
    }

    public List<Weapon> getAllWeapons() {
        return weaponRepository.findAll();
    }
}
