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
public class UpdateWeaponCommand {
    public String name;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;

    public void update(Weapon weapon) {
        if (name != null) weapon.setName(name);
        if (description != null) weapon.setDescription(description);
        if (rarity != null) weapon.setRarity(rarity);
        if (statistic != null) weapon.setStatistic(statistic);
        if (specialBuffs != null) weapon.setSpecialBuffs(specialBuffs);
    }
}
