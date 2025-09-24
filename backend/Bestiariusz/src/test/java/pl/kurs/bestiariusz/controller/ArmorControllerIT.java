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
import pl.kurs.bestiariusz.models.Armor;
import pl.kurs.bestiariusz.repository.ArmorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    private static final String GET_ARMOR_ENDPOINT = "/armor/{id}";


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
                .name("zbroja")
                .rarity(Rarity.COMMON)
                .description("pancerz")
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
}
