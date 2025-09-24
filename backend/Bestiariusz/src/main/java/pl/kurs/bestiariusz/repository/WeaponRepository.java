package pl.kurs.bestiariusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;

import java.util.List;

/**
 * Repository for managing ({@link Weapon} entites.
 */
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
    /**
     * Find all weapons with the given rarity.
     * @param rarity rarity level to filter weapons
     * @return list of filtered by rarity weapons
     */
    List<Weapon> findAllByRarity(Rarity rarity);
}
