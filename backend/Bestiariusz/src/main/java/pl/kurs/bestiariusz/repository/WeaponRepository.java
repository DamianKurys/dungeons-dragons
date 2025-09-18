package pl.kurs.bestiariusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;

import java.util.List;

public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    List<Weapon> findAllByRarity(Rarity rarity);
}
