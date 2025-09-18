package pl.kurs.bestiariusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Armor;

import java.util.List;

public interface ArmorRepository extends JpaRepository<Armor, Long> {
    List<Armor> findAllByRarity(Rarity rarity);
}
