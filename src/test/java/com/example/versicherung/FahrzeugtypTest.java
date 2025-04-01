package com.example.versicherung;

import com.example.versicherung.Versicherungspraemie.AnfrageDTO;
import com.example.versicherung.fahrzeugtyp.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class FahrzeugtypTest {

    @Autowired
    IFahrzeugtypService fahrzeugtypService;

    @Autowired
    FahrzeugtypController fahrzeugtypController;

    @Autowired
    FahrzeugtypRepository fahrzeugtypRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    @Transactional
    public void testGetFahrzeugtyp() throws Exception {

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(10.0);

        fahrzeugtypRepository.save(fahrzeugtyp);

        fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Bus");
        fahrzeugtyp.setFaktor(20.0);

        fahrzeugtypRepository.save(fahrzeugtyp);

        Fahrzeugtyp fahrzeugFound = fahrzeugtypService.getFahrzeugtyp("Camper");
        assertEquals( "Camper"  , fahrzeugFound.getFahrzeugtyp());
    }

    @Test
    @Transactional
    public void testGetAllFahrzeugtyp() throws Exception {

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(10.0);

        fahrzeugtypRepository.save(fahrzeugtyp);

        fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Bus");
        fahrzeugtyp.setFaktor(20.0);

        fahrzeugtypRepository.save(fahrzeugtyp);

        List<Fahrzeugtyp> fahrzeugtypList = fahrzeugtypService.getAllFahrzeugtypen();
        assertEquals(2 , fahrzeugtypList.size());
    }

    @Test
    @Transactional
    public void testUpdateFahrzeugtyp() throws Exception {

        Double faktor = 10.2;
        Double faktorUpdated = 12.2;

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(faktor);

        fahrzeugtypRepository.save(fahrzeugtyp);

        fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(faktorUpdated);

        mockMvc.perform(put("/api/fahrzeugtyp/update/" + fahrzeugtyp.getFahrzeugtyp())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fahrzeugtyp)))
                .andExpect(status().isOk());

        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtyp.getFahrzeugtyp());
        assertTrue(fahrzeugtypOptional.isPresent());
        Fahrzeugtyp fahrzeugtypUpated = fahrzeugtypOptional.get();
        assertEquals(faktorUpdated , fahrzeugtypUpated.getFaktor());
    }

    @Test
    @Transactional
    public void testFahrzeugtyp() throws Exception {

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(10.2);

        mockMvc.perform(post("/api/fahrzeugtyp/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fahrzeugtyp)))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void testFahrzeugtypFaktorNegativ() throws Exception {

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(-10.2);

        mockMvc.perform(post("/api/fahrzeugtyp/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fahrzeugtyp)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
    public void testDoppelterFahrzeuttyp() throws Exception {

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(10.2);

        mockMvc.perform(post("/api/fahrzeugtyp/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fahrzeugtyp)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/fahrzeugtyp/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fahrzeugtyp)))
                .andExpect(status().isConflict());
    }

    @Test
    @Transactional
    public void testDeleteFahrzeugtyp() throws Exception {

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtyp.setFaktor(10.0);

        fahrzeugtypRepository.save(fahrzeugtyp);

        fahrzeugtypService.deleteFahrzeugtyp(fahrzeugtyp.getFahrzeugtyp());

        Fahrzeugtyp fahrzeugFound = fahrzeugtypService.getFahrzeugtyp("Camper");
        assertNull(fahrzeugFound);
    }
}
