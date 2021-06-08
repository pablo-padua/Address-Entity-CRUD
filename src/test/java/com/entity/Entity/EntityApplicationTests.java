package com.entity.Entity;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class EntityApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AddressEntity())
                .build();
    }

    @Test
    public void checkCRUD() throws Exception {
        AddressEntity addressEntity = new AddressEntity("Rua Das Alamedas", 11L, "Bairro Sao Joao", "Sao Paulo", "Estado de Sao Paulo", "Brazil", "VS1LC");
        addressEntity.setId(1L);
        this.mockMvc.perform(post("/api/address/create-hardcoded"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        this.mockMvc.perform(put("/api/address/update-number/1?number=5"))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("Rua Das Alamedas", addressEntity.getStreetName());
        Assertions.assertEquals("Bairro Sao Joao", addressEntity.getNeighbourhood());
        Assertions.assertEquals("Estado de Sao Paulo", addressEntity.getState());
        Assertions.assertNotEquals("Brasil", addressEntity.getCountry());

        addressEntity.setNumber(12L);
        Assertions.assertEquals(12, addressEntity.getNumber());


        this.mockMvc.perform(get("/api/address/1"))
                .andExpect(status().isOk())
                .andReturn();

        this.mockMvc.perform(delete("/api/address/1"))
                .andExpect((ResultMatcher) content().string("Address with id: 1 was deleted"))
                .andReturn();
    }


}
