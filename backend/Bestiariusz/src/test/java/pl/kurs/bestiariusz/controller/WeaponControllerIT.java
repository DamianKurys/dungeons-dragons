package pl.kurs.bestiariusz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;
import pl.kurs.bestiariusz.repository.WeaponRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class WeaponControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WeaponRepository weaponRepository;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0")
            .withDatabaseName("fantasy-universe")
            .withUsername("root")
            .withPassword("root");
    private static final String GET_WEAPON_ENDPOINT = "/weapon/{id}";


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl.auto", () -> "create-drop");
    }

    @BeforeEach
    void cleanDatabase() {
        weaponRepository.deleteAll();
    }


    @Test
    void shouldGetWeapon() throws Exception {
        //given
        Weapon weapon = Weapon.builder()
                .damage(50)
                .description("miecz")
                .name("wielki miecz")
                .rarity(Rarity.MYTHIC)
                .specialBuffs("brak")
                .statistic("+1 do obrażen od siły")
                .build();
        weaponRepository.save(weapon);

        Long weaponId = weapon.getId();

        //when
        MvcResult result = mockMvc.perform(get(GET_WEAPON_ENDPOINT, weaponId)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(status().isOk())
                .andReturn();

        Weapon weapon1 = objectMapper.readValue(result.getResponse().getContentAsString(), Weapon.class);
        //then
        assertThat(weapon1.getDamage()).isEqualTo(weapon.getDamage());
        assertThat(weapon1.getDescription()).isEqualTo(weapon.getDescription());
        assertThat(weapon1.getRarity()).isEqualTo(weapon.getRarity());
    }


}
