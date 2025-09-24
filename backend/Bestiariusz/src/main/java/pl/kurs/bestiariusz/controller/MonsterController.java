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

/**
 * REST controller for managing {@link Monster} entities.
 * <p>
 * Provides endpoints for creating, retrieving, updating, and filtering monsters
 * by region or boss status.
 */
@RestController
@RequestMapping("/monster")
@RequiredArgsConstructor
public class MonsterController {
    private static final String MONSTER_CREATED = "Monster created sucessfully";
    public final MonsterService monsterService;

    /**
     * Creates a new monster
     *
     * @param command the request payload containing monster details
     * @return a response with status 201 and confirmation message
     */
    @PostMapping("/create")
    public ResponseEntity<String> addMonster(@RequestBody CreateMonsterCommand command) {
        monsterService.addMonster(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(MONSTER_CREATED);
    }

    /**
     * Retrieves a monster by its ID
     *
     * @param id id the monster ID
     * @return the monster matching the given ID
     * @throws MonsterNotFoundException if no monster exists with the provided ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Monster> getMonster(@PathVariable Long id) throws MonsterNotFoundException {
        Monster monsterById = monsterService.findMonsterById(id);
        return ResponseEntity.ok(monsterById);
    }

    /**
     * Retrieves all monsters belonging to the given region.
     *
     * @param region the region to filter monsters by
     * @return a response containing a list of monsters in the specified region
     */
    @GetMapping("/region/{region}")
    public ResponseEntity<List<Monster>> findAllMonstersByRegion(@PathVariable Region region) {
        List<Monster> monsterList = monsterService.findAllMonstersByRegion(region);
        return ResponseEntity.ok(monsterList);
    }

    /**
     * Updates an existing monster by its ID.
     *
     * @param id      the ID of the monster to update
     * @param command the update request payload
     * @return the updated monster
     * @throws MonsterNotFoundException if no monster exists with the provided ID
     */
    @PutMapping("update/{id}")
    public ResponseEntity<Monster> updateMonster(@PathVariable Long id,
                                                 @RequestBody UpdateMonsterCommand command) throws MonsterNotFoundException {
        Monster updatedMonster = monsterService.updateMonster(id, command);
        return ResponseEntity.ok(updatedMonster);
    }

    /**
     * Retrieves all available regions.
     *
     * @return an array of {@link Region} values
     */
    @GetMapping("/regions")
    public Region[] getRegions() {
        return Region.values();
    }


    /**
     * Retrieves monsters filtered optionally by region and/or boss flag.
     *
     * @param region optional region to filter monsters
     * @param boss   optional flag indicating whether to filter only boss monsters
     * @return a list of monsters matching the given filters
     */
    @GetMapping
    public List<Monster> list(@RequestParam(required = false) Region region,
                              @RequestParam(required = false) Boolean boss) {
        return monsterService.findBoss(region, boss);
    }

    /**
     * Retrieves all boss monsters, optionally filtered by region.
     *
     * @param region optional region to filter boss monsters
     * @return a list of monsters marked as bosses
     */
    @GetMapping("/bosses")
    public List<Monster> bosses(@RequestParam(required = false) Region region) {
        return monsterService.findBoss(region, true);
    }
}

