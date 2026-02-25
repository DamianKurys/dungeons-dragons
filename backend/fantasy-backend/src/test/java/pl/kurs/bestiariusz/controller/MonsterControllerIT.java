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
import pl.kurs.bestiariusz.commands.CreateMonsterCommand;
import pl.kurs.bestiariusz.commands.UpdateMonsterCommand;
import pl.kurs.bestiariusz.enums.Region;
import pl.kurs.bestiariusz.models.Monster;
import pl.kurs.bestiariusz.repository.MonsterRepository;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class MonsterControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MonsterRepository monsterRepository;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0")
            .withDatabaseName("fantasy-universe")
            .withUsername("root")
            .withPassword("root");

    private static final String GET_ALL_MONSTERS_ENDPOINT = "/monster/region/{region}";
    private static final String GET_MONSTER_ENDPOINT = "/monster/{id}";
    private static final String CREATE_NONSTER_ENDPOINT = "/monster/create";
    private static final String UPDATE_NONSTER_ENDPOINT = "/monster/update/{id}";

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl.auto", () -> "create-drop");
    }

    @BeforeEach
    void cleanDatabase() {
        monsterRepository.deleteAll();
    }

    @Test
    void shouldGetMonsters() throws Exception {
        //given
        Monster monster = Monster.builder()
                .name("Lewiatan")
                .level("10")
                .boss(true)
                .build();
        monsterRepository.save(monster);

        Long monsterId = monster.getId();
        //when
        MvcResult mvcResult = mockMvc.perform(get(GET_MONSTER_ENDPOINT, monsterId)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(status().isOk())
                .andReturn();

        Monster monster1 = objectMapper.readValue
                (mvcResult.getResponse().getContentAsString(), Monster.class);

        //then
        assertThat(monster1.getName()).isEqualTo(monster.getName());
        assertThat(monster1.getLevel()).isEqualTo(monster.getLevel());
        assertThat(monster1.isBoss()).isEqualTo(monster.isBoss());
    }

    @Test
    void shouldAddMonster() throws Exception {
        //given
        CreateMonsterCommand monster = CreateMonsterCommand.builder()
                .name("Lewiatan")
                .level("10")
                .boss(true)
                .build();

        String body = objectMapper.writeValueAsString(monster); //serializacja obiektu na JSON(String)
        // HTTP zawiera json jako string

        //when
        MvcResult result = mockMvc.perform(post(CREATE_NONSTER_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Monster created sucessfully");
    }

    @Test
    void shouldGetAllMonstersByRegion() throws Exception {
        //given
        List<Monster> monsters = List.of(
                Monster.builder()
                        .name("Lewiatan")
                        .level("10")
                        .region(Region.FEROXAR)
                        .boss(true)
                        .build(),

                Monster.builder()
                        .name("Ogr")
                        .level("2")
                        .region(Region.FEROXAR)
                        .boss(false)
                        .build()
        );
        monsterRepository.saveAll(monsters); //przy pobieraniu listy lub elementu,
        // przygotowujemy dane w bazie za pomoca repozytorium

        //when

        MvcResult mvcResult = mockMvc.perform(get(GET_ALL_MONSTERS_ENDPOINT, Region.FEROXAR)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<Monster> responseList = objectMapper.readValue(contentAsString,
                new TypeReference<>() {
                    //deserializacja JSON(String), na liste nowych obiektow
                });

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0).getRegion()).isEqualTo(Region.FEROXAR);
        assertThat(responseList.get(1).getRegion()).isEqualTo(Region.FEROXAR);
        assertThat(responseList.get(0)).isEqualTo(monsters.get(0));
        assertThat(responseList.get(1)).isEqualTo(monsters.get(1));
        //aby poprawnie porownac musimy dodac equals i hash code - wtedy porownujemy
        //obiekty, inaczej Object porownuje referencje do nich
    }

    @Test
    void shouldUpdateMonster() throws Exception {
        //given
        Monster monster = Monster.builder()
                .name("Lewiatan")
                .level("10")
                .boss(true)
                .build();
        monsterRepository.save(monster);

        UpdateMonsterCommand updateMonsterCommand = UpdateMonsterCommand.builder()
                .name("Lewiatan")
                .level("10")
                .boss(true)
                .build();
        String object = objectMapper.writeValueAsString(updateMonsterCommand);
        //when
        MvcResult result = mockMvc.perform(put(UPDATE_NONSTER_ENDPOINT, monster.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(object)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        //then
        Monster updatedMonster = objectMapper.readValue(result.getResponse().getContentAsString(), Monster.class);
        assertThat(updatedMonster.getName()).isEqualTo(monster.getName());
        assertThat(updatedMonster.getLevel()).isEqualTo(monster.getLevel());
        assertThat(updatedMonster.isBoss()).isEqualTo(monster.isBoss());
    }

    @Test
    void shouldGetRegions() throws Exception {

        //when
        MvcResult result = mockMvc.perform(get("/monster/regions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        Region[] response = objectMapper.readValue(contentAsString, Region[].class);

        assertThat(response).containsExactlyInAnyOrder(Region.values());
    }

    @Test
    void shouldFilterMonsterAndBossByRegion() throws Exception {
        //given
        Monster bossInFeroxar = Monster.builder()
                .name("Lewiatan")
                .region(Region.FEROXAR)
                .boss(true)
                .build();

        Monster normalInFeroxar = Monster.builder()
                .name("Ogr")
                .region(Region.FEROXAR)
                .boss(false)
                .build();

        Monster bossInNorden = Monster.builder()
                .name("Smok")
                .region(Region.AEN_ELLE)
                .boss(true)
                .build();

        monsterRepository.saveAll(List.of
                (bossInFeroxar, normalInFeroxar));
        //when
        MvcResult result = mockMvc.perform(get("/monster")
                        .param("region", Region.FEROXAR.name())
                        .param("boss", "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = result.getResponse().getContentAsString();
        List<Monster> monsters = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(monsters).hasSize(1);
        assertThat(monsters.getFirst()).isEqualTo(bossInFeroxar);
        assertThat(monsters.getFirst().isBoss()).isTrue();
        assertThat(monsters.getFirst().getRegion()).isEqualTo(Region.FEROXAR);

    }

    @Test
    void shouldReturnBossesFilteredByRegion() throws Exception {
        // given
        Monster bossFeroxar = Monster.builder()
                .name("Lewiatan")
                .region(Region.FEROXAR)
                .boss(true)
                .build();

        Monster bossNorden = Monster.builder()
                .name("Smok")
                .region(Region.VENTIRO)
                .boss(true)
                .build();

        Monster normalFeroxar = Monster.builder()
                .name("Goblin")
                .region(Region.FEROXAR)
                .boss(false)
                .build();

        monsterRepository.saveAll(List.of(bossFeroxar, bossNorden, normalFeroxar));

        // when
        MvcResult result = mockMvc.perform(get("/monster/bosses")
                        .param("region", Region.FEROXAR.name())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String json = result.getResponse().getContentAsString();
        List<Monster> response = objectMapper.readValue(json, new TypeReference<>() {
        });

        assertThat(response).hasSize(1);
        assertThat(response.getFirst().getRegion()).isEqualTo(Region.FEROXAR);
        assertThat(response.getFirst().isBoss()).isTrue();
    }

}
