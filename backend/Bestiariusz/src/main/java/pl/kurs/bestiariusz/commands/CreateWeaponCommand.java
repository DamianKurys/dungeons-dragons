package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;

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
