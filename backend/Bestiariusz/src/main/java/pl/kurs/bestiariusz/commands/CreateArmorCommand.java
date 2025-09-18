package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Armor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateArmorCommand {
    public String name;
    public String description;
    public Rarity rarity;
    public String statistic;
    public String specialBuffs;

    public Armor toArmor() {
        return new Armor(name, description, rarity, statistic, specialBuffs);

    }
}
