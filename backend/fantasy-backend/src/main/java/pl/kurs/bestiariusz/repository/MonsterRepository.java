package pl.kurs.bestiariusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.models.Monster;

import java.util.List;

/**
 * Repoository for managin ({@link Monster} entites.
 *
 */
public interface MonsterRepository  extends JpaRepository<Monster, Long> {
    /**
     * Find all monsters in given region
     * @param region region to filter monsters
     * @return list of monster in the specified region
     */
    List<Monster> findAllByRegion(Region region);

    /**
     * Finds all monster that are bosses
     * @return list of monster that are bosses
     */
    List<Monster> findAllByBossTrue();

    /**
     * Find all boss monster in the given region
     * @param region region to filter monsters by
     * @return list of boss monster in specified region
     */
    List<Monster> findAllByRegionAndBossTrue(Region region);

    /**
     * Find all monster by boss flag
     * @param boss whether the monster is a boss
     * @return list of monster filtered by boss flag
     */
    List<Monster> findAllByBoss(boolean boss);


}
