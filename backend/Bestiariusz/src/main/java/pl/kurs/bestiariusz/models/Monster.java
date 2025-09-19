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
    private int localNumber;
    @Enumerated(EnumType.STRING)
    private Region region;
    private String type;
    private String name;
    private String description;
    private String weakness;
    private String strengths;
    private String level;
    private boolean boss;

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
