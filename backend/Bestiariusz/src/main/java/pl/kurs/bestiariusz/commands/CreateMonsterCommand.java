package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.models.Monster;

/**
 * DTO command to create new monster ({@link pl.kurs.bestiariusz.models.Monster}
 * Object is created by data from JSON and HTTP request or Postman
 * and object is converted to entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateMonsterCommand {

    private int localNumber;
    private Region region;
    private String type;
    private String name;
    private String description;
    private String weakness;
    private String strengths;
    private String level;
    private boolean boss;

    public Monster createMonster() {
        return new Monster(localNumber, region, type, name, description, weakness, strengths, level, boss);
    }
}
