package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;

/**
 * DTO command to update ({@link Weapon} entity
 * This class works as a specialized DTO that represents
 * the update request for an existing weapon.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateWeaponCommand {
    public int damage;
    public String name;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;

    /**
     * Applies the updates from comand to the given entity
     *
     * @param weapon entity to upgrade
     */
    public void update(Weapon weapon) {
        if (damage != 0) weapon.setDamage(damage);
        if (name != null) weapon.setName(name);
        if (description != null) weapon.setDescription(description);
        if (rarity != null) weapon.setRarity(rarity);
        if (statistic != null) weapon.setStatistic(statistic);
        if (specialBuffs != null) weapon.setSpecialBuffs(specialBuffs);
    }
}
