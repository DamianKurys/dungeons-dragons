package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Armor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UpdateArmorCommand {
    public String name;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;


    public void update(Armor armor) {
        if (name != null) armor.setName(name);
        if (description != null) armor.setDescription(description);
        if (rarity != null) armor.setRarity(rarity);
        if (statistic != null) armor.setStatistic(statistic);
        if (specialBuffs != null) armor.setSpecialBuffs(specialBuffs);

    }
}
