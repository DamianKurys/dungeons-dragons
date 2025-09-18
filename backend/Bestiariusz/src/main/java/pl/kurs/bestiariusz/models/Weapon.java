package pl.kurs.bestiariusz.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import pl.kurs.bestiariusz.enums.Rarity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Weapon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String name;
    public int damage;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;

    public Weapon(String name, int damage, String description, Rarity rarity, String statistic, String specialBuffs) {
        this.name = name;
        this.damage = damage;
        this.description = description;
        this.rarity = rarity;
        this.statistic = statistic;
        this.specialBuffs = specialBuffs;
    }
}
