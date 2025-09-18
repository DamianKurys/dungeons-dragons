package pl.kurs.bestiariusz.models;

import jakarta.persistence.*;

import lombok.*;
import pl.kurs.bestiariusz.enums.Region;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Monster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int localNumber;  //numer potwora w danym regionie
    @Enumerated(EnumType.STRING)
    private Region region;  //region w którym występuje potwór
    private String type;    //typ potwora
    private String name;   //nazwa potwora
    private String description;  //opis potwora
    private String weakness;  //slabosci, na co jest podatny
    private String strengths; //silne strony
    private String level;    //poziom potwora
    private boolean boss;   //czy to boss

    public Monster(int localNumber, Region region,
                   String type, String name, String
                           description, String weakness, String strengths, String level, boolean boss) {
        this.localNumber = localNumber;
        this.region = region;
        this.type = type;
        this.name = name;
        this.description = description;
        this.weakness = weakness;
        this.strengths = strengths;
        this.level = level;
        this.boss = boss;
    }
}
