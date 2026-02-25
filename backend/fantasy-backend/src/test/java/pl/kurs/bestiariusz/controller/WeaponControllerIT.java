package pl.kurs.bestiariusz.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import pl.kurs.bestiariusz.commands.UpdateWeaponCommand;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Weapon;
import pl.kurs.bestiariusz.repository.WeaponRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private static final String GET_ALL_WEAPON_ENDPOINT = "/weapon";
    private static final String CREATE_WEAPON_ENDPOINT = "/weapon/create";
    private static final String UPDATE_WEAPON_ENDPOINT = "/weapon/update/{id}";
    private  static final String GET_RARITY_WEAPON_ENDPOINT = "/weapon/rarity/{rarity}";


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

    @Test
    void shouldAddWeapon() throws Exception {
        //given
        Weapon weapon = Weapon.builder()
                .damage(50)
                .description("miecz")
                .name("wielki miecz")
                .rarity(Rarity.MYTHIC)
                .build();

        String json = objectMapper.writeValueAsString(weapon);

        //when
        MvcResult result = mockMvc.perform(post(CREATE_WEAPON_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Weapon created sucessfully");
    }

    @Test
    void shguldGetAllWeapons() throws Exception {
        //given
        List<Weapon> weapons = List.of(
                Weapon.builder()
                        .damage(50)
                        .description("Miecz")
                        .name("Wielki miecz")
                        .rarity(Rarity.MYTHIC)
                        .build(),

                Weapon.builder()
                        .damage(60)
                        .description("Buława")
                        .name("Buzdygan")
                        .rarity(Rarity.MYTHIC)
                        .build()
        );
        weaponRepository.saveAll(weapons);

        //when
        MvcResult mvcResult = mockMvc.perform(get(GET_ALL_WEAPON_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Weapon> list = objectMapper.readValue(contentAsString,
                new TypeReference<>() {
                });

        assertThat(list.size()).isEqualTo(weapons.size());
        assertThat(list.getFirst().getDamage()).isEqualTo(weapons.getFirst().getDamage());
        assertThat(list.getFirst().getDescription()).isEqualTo(weapons.getFirst().getDescription());
        assertThat(list.getFirst().getRarity()).isEqualTo(weapons.getFirst().getRarity());

    }

    @Test
    void shouldUpdateWeapon() throws Exception {
        //given
        Weapon weapon = Weapon.builder()
                .damage(50)
                .description("miecz")
                .name("wielki miecz")
                .rarity(Rarity.MYTHIC)
                .build();
        weaponRepository.save(weapon);

        UpdateWeaponCommand updateWeaponCommand = UpdateWeaponCommand.builder()
                .damage(50)
                .description("miecz")
                .name("wielki miecz")
                .rarity(Rarity.MYTHIC)
                .build();

        //when
        String jsonCommand = objectMapper.writeValueAsString(updateWeaponCommand);
        MvcResult result = mockMvc.perform(put(UPDATE_WEAPON_ENDPOINT, weapon.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCommand)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Weapon updatedWeapon = objectMapper.readValue(result.getResponse().getContentAsString(), Weapon.class);

        assertThat(updatedWeapon.getDamage()).isEqualTo(weapon.getDamage());
        assertThat(updatedWeapon.getDescription()).isEqualTo(weapon.getDescription());
        assertThat(updatedWeapon.getName()).isEqualTo(weapon.getName());
        assertThat(updatedWeapon.getRarity()).isEqualTo(weapon.getRarity());
    }

    @Test
    void shouldGetWeaponByRarity() throws Exception {
        //given
        List<Weapon> weapons = List.of(
                Weapon.builder()
                        .damage(50)
                        .description("miecz")
                        .name("wielki miecz")
                        .rarity(Rarity.MYTHIC)
                        .build(),
                Weapon.builder()
                        .damage(60)
                        .description("Buława")
                        .name("Buzdygan")
                        .rarity(Rarity.MYTHIC)
                        .build()

        );

        weaponRepository.saveAll(weapons);


        //when
        MvcResult result = mockMvc.perform(get(GET_RARITY_WEAPON_ENDPOINT, Rarity.MYTHIC)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String jsonResponse = result.getResponse().getContentAsString();
        List<Weapon> responseList = objectMapper.readValue(
                jsonResponse,
                new TypeReference<>() {
                }
        );

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0)).isEqualTo(weapons.get(0));
        assertThat(responseList.get(1)).isEqualTo(weapons.get(1));

    }
}



