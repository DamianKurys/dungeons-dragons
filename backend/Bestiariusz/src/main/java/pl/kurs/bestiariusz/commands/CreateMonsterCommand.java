package pl.kurs.bestiariusz.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.models.Monster;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CreateMonsterCommand {

    private int localNumber;  //numer potwora w danym regionie
    private Region region;  //region w którym występuje potwór
    private String type;    //typ potwora
    private String name;   //nazwa potwora
    private String description;  //opis potwora
    private String weakness;  //slabosci, na co jest podatny
    private String strengths; //silne strony
    private String level;    //poziom potwora
    private boolean boss; //czy to boss

    public Monster createMonster() {
        return new Monster(localNumber, region, type, name, description, weakness, strengths, level, boss);
    }
}
