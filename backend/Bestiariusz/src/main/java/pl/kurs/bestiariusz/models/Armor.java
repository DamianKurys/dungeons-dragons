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
public class Armor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    public String name;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;

    public Armor(String name, String description, Rarity rarity, String statistic, String specialBuffs) {
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.statistic = statistic;
        this.specialBuffs = specialBuffs;
    }
}
