package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.models.Monster;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMonsterCommand {
    private String name;
    private String type;
    private String description;
    private String weakness;
    private String strengths;
    private String level;
    private Region region; //
    private Integer localNumber;
    private Boolean boss;

    public void update(Monster monster) {
        if (name != null) monster.setName(name);
        if (type != null) monster.setType(type);
        if (description != null) monster.setDescription(description);
        if (weakness != null) monster.setWeakness(weakness);
        if (strengths != null) monster.setStrengths(strengths);
        if (level != null) monster.setLevel(level);
        if (region != null) monster.setRegion(region);
        if (localNumber != null) monster.setLocalNumber(localNumber);
        if (boss != null) monster.setBoss(boss);
    }
}


