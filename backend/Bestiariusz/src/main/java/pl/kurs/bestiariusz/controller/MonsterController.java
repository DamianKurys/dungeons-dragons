package pl.kurs.bestiariusz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.bestiariusz.commands.CreateMonsterCommand;
import pl.kurs.bestiariusz.commands.UpdateMonsterCommand;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.exceptions.MonsterNotFoundException;
import pl.kurs.bestiariusz.models.Monster;
import pl.kurs.bestiariusz.services.MonsterService;

import java.util.List;

@RestController
@RequestMapping("/monster")
@RequiredArgsConstructor
public class MonsterController {
    private static final String MONSTER_CREATED = "Monster created sucessfully";
    public final MonsterService monsterService;

    @PostMapping("/create")
    public ResponseEntity<String> addMonster(@RequestBody CreateMonsterCommand command) {
        monsterService.addMonster(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(MONSTER_CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Monster> getMonster(@PathVariable Long id) throws MonsterNotFoundException {
        Monster monsterById = monsterService.findMonsterById(id);
        return ResponseEntity.ok(monsterById);
    }

    @GetMapping("/region/{region}")
    public ResponseEntity<List<Monster>> findAllMonstersByRegion(@PathVariable Region region) {
        List<Monster> monsterList = monsterService.findAllMonstersByRegion(region);
        return ResponseEntity.ok(monsterList);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Monster> updateMonster(@PathVariable Long id,
                                                 @RequestBody UpdateMonsterCommand command) throws MonsterNotFoundException {
        Monster updatedMonster = monsterService.updateMonster(id, command);
        return ResponseEntity.ok(updatedMonster);
    }

    @GetMapping("/regions")
    public Region[] getRegions() {
        return Region.values();
    }

    @GetMapping
    public List<Monster> list(@RequestParam(required = false) Region region,
                              @RequestParam(required = false) Boolean boss) {
        return monsterService.findBoss(region, boss);
    }

    @GetMapping("/bosses")
    public List<Monster> bosses(@RequestParam(required = false) Region region) {
        return monsterService.findBoss(region, true);
    }
}

