package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;

/**
 * DTO command to create new monster ({@link pl.kurs.bestiariusz.models.Weapon}
 * Object is created by data from JSON and HTTP request or Postman
 * and object is converted to entity
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateWeaponCommand {
    public String name;
    public int damage;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;


    public Weapon toWeapon() {
        return new Weapon(name, damage, description, rarity, statistic, specialBuffs);
    }
}
