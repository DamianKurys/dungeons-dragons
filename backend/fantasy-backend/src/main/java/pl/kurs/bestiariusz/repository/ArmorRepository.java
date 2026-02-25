package pl.kurs.bestiariusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Armor;

import java.util.List;

/**
 * Repository for managing ({@link Armor} entities.
 */
public interface ArmorRepository extends JpaRepository<Armor, Long> {
    /**
     * Find all armors with the given rarity.
     * @param rarity rarity level of armors
     * @return list of armors matching the given rarity
     */
    List<Armor> findAllByRarity(Rarity rarity);
}
