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
import pl.kurs.bestiariusz.commands.CreateArmorCommand;
import pl.kurs.bestiariusz.commands.UpdateArmorCommand;
import pl.kurs.bestiariusz.enums.Rarity;
import pl.kurs.bestiariusz.models.Armor;
import pl.kurs.bestiariusz.repository.ArmorRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class ArmorControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ArmorRepository armorRepository;

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.0")
            .withDatabaseName("fantasy-universe")
            .withUsername("root")
            .withPassword("root");

    private static final String GET_ALL_ARMORS_ENDPOINT = "/armor";
    private static final String GET_ARMOR_ENDPOINT = "/armor/{id}";
    private static final String CREATE_ARMOR_ENDPOINT = "/armor/create";
    private static final String UPDATE_ARMOR_ENDPOINT = "/armor/update/{id}";
    private static final String GET_RARITY_ARMOR_ENDPOINT = "armor/rarity/{rarity}";


    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.jpa.hibernate.ddl.auto", () -> "create-drop");
    }

    @BeforeEach
    void cleanDatabase() {
        armorRepository.deleteAll();
    }

    @Test
    void shouldGetArmor() throws Exception {
        //given
        Armor armor = Armor.builder()
                .name("leather armor")
                .rarity(Rarity.COMMON)
                .description("armor")
                .build();
        armorRepository.save(armor);

        Long armorId = armor.getId();

        //when
        MvcResult mvcResult = mockMvc.perform(get(GET_ARMOR_ENDPOINT, armorId)
                        .header(HttpHeaders.CONTENT_TYPE, "application/json"))
                .andExpect(status().isOk())
                .andReturn();

        Armor armor1 = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Armor.class);

        //then
        assertThat(armor1.getName()).isEqualTo(armor.getName());
        assertThat(armor1.getRarity()).isEqualTo(armor.getRarity());
        assertThat(armor1.getDescription()).isEqualTo(armor.getDescription());
    }

    @Test
    void shouldAddArmor() throws Exception {
        //given
        CreateArmorCommand armor = CreateArmorCommand.builder()
                .name("dragonscale plate")
                .rarity(Rarity.MYTHIC)
                .description("armor")
                .build();

        String body = objectMapper.writeValueAsString(armor);
        //when
        MvcResult result = mockMvc.perform(post(CREATE_ARMOR_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Armor created sucessfully");
    }

    @Test
    void shouldGetAllArmors() throws Exception {
        //given
        List<Armor> armorList = List.of(
                Armor.builder()
                        .name("Leather armor")
                        .rarity(Rarity.COMMON)
                        .description("armor")
                        .build(),

                Armor.builder()
                        .name("Leather armor")
                        .rarity(Rarity.COMMON)
                        .description("armor")
                        .build()
        );

        armorRepository.saveAll(armorList);

        //when
        MvcResult result = mockMvc.perform(get(GET_ALL_ARMORS_ENDPOINT)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String contentAsString = result.getResponse().getContentAsString();
        List<Armor> responseList = objectMapper.readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0)).isEqualTo(armorList.get(0));
        assertThat(responseList.get(1)).isEqualTo(armorList.get(1));
    }

    @Test
    void shouldGetArmorByRarity() throws Exception {
        //given
        List<Armor> armorList = List.of(
                Armor.builder()
                        .name("Leather armor")
                        .rarity(Rarity.COMMON)
                        .description("armor")
                        .build(),

                Armor.builder()
                        .name("Leather armor")
                        .rarity(Rarity.COMMON)
                        .description("armor")
                        .build()
        );
        armorRepository.saveAll(armorList);


        //when
        MvcResult result = mockMvc.perform(get(GET_RARITY_ARMOR_ENDPOINT, Rarity.COMMON)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        //then
        String jsonResponse = result.getResponse().getContentAsString();

        List<Armor> responseList = objectMapper.readValue(
                jsonResponse,
                new TypeReference<>() {}
        );

        assertThat(responseList).hasSize(2);
        assertThat(responseList.get(0)).isEqualTo(armorList.get(0));
        assertThat(responseList.get(1)).isEqualTo(armorList.get(1));

    }

    @Test
    void shouldUpdateArmor() throws Exception {
        //given
        Armor armor = Armor.builder()
                .name("Leather armor")
                .rarity(Rarity.COMMON)
                .description("armor")
                .build();
        armorRepository.save(armor);

        UpdateArmorCommand armorCommand = UpdateArmorCommand.builder()
                .name("Leather armor")
                .rarity(Rarity.COMMON)
                .description("armor")
                .build();

        //when

        String jsonCommand = objectMapper.writeValueAsString(armorCommand);
        MvcResult result = mockMvc.perform(put(UPDATE_ARMOR_ENDPOINT, armor.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCommand)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //then
        Armor updatedArmor = objectMapper.readValue(result.getResponse().getContentAsString(),Armor.class);

        assertThat(updatedArmor.getName()).isEqualTo(armor.getName());
        assertThat(updatedArmor.getRarity()).isEqualTo(armor.getRarity());
        assertThat(updatedArmor.getDescription()).isEqualTo(armor.getDescription());
    }
}

