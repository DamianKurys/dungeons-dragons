package pl.kurs.bestiariusz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.models.Monster;

import java.util.List;

public interface MonsterRepository  extends JpaRepository<Monster, Long> {
    List<Monster> findAllByRegion(Region region);
    List<Monster> findAllByBossTrue();
    List<Monster> findAllByRegionAndBossTrue(Region region);
    List<Monster> findAllByBoss(boolean boss);


}
