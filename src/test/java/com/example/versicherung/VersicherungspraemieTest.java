package com.example.versicherung;

import com.example.versicherung.Postleitzahl.Postleitzahl;
import com.example.versicherung.Postleitzahl.PostleitzahlRepository;
import com.example.versicherung.Regionen.Region;
import com.example.versicherung.Regionen.RegionRepository;
import com.example.versicherung.Versicherungspraemie.AnfrageDTO;
import com.example.versicherung.fahrzeugtyp.Fahrzeugtyp;
import com.example.versicherung.fahrzeugtyp.FahrzeugtypController;
import com.example.versicherung.fahrzeugtyp.FahrzeugtypRepository;
import com.example.versicherung.fahrzeugtyp.IFahrzeugtypService;
import com.example.versicherung.kilometerleistung.Kilometerleistung;
import com.example.versicherung.kilometerleistung.KilometerleistungRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VersicherungspraemieTest {
    @Autowired
    IFahrzeugtypService fahrzeugtypService;

    @Autowired
    FahrzeugtypController fahrzeugtypController;

    @Autowired
    FahrzeugtypRepository fahrzeugtypRepository;

    @Autowired
    KilometerleistungRepository kilometerleistungRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    PostleitzahlRepository postleitzahlRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testSubmit(){
        preloadDB();

        AnfrageDTO anfrageDTO = new AnfrageDTO();

        anfrageDTO.setFahrzeugtyp("Camper");
        anfrageDTO.setPlz("58540");
        anfrageDTO.setKilometerleistung(1002);

        try {
            mockMvc.perform(post("/api/praemie/submit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(anfrageDTO)))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSubmitFahrzeugtypExistiertNicht(){
        preloadDB();

        AnfrageDTO anfrageDTO = new AnfrageDTO();

        anfrageDTO.setFahrzeugtyp("Camperer");
        anfrageDTO.setPlz("58540");
        anfrageDTO.setKilometerleistung(1002);

        try {
            mockMvc.perform(post("/api/praemie/submit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(anfrageDTO)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSubmitNegativeKilometerleistung(){

        preloadDB();

        AnfrageDTO anfrageDTO = new AnfrageDTO();

        anfrageDTO.setFahrzeugtyp("Camper");
        anfrageDTO.setPlz("58540");
        anfrageDTO.setKilometerleistung(-1002);


        try {
            mockMvc.perform(post("/api/praemie/submit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(anfrageDTO)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSubmitPlzZuLang(){

        AnfrageDTO anfrageDTO = new AnfrageDTO();

        anfrageDTO.setFahrzeugtyp("Camper");
        anfrageDTO.setPlz("0123456");
        anfrageDTO.setKilometerleistung(1002);

        try {
            mockMvc.perform(post("/api/praemie/submit")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(anfrageDTO)))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void preloadDB(){

        Kilometerleistung km = new Kilometerleistung(0, 5000, 2.0);
        kilometerleistungRepository.save(km);

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp();
        fahrzeugtyp.setFaktor(10.0);
        fahrzeugtyp.setFahrzeugtyp("Camper");
        fahrzeugtypRepository.save(fahrzeugtyp);

        Postleitzahl postleitzahl = new Postleitzahl();
        postleitzahl.setPlz("58540");
        postleitzahl.setLand("DE");
        postleitzahl.setStadt("Meinerzhagen");
        postleitzahl.setBundesland("Nordrhein-Westfalen");
        postleitzahl.setBezirk("MärkischerKreis");
        postleitzahlRepository.save(postleitzahl);

        Region region = new Region();
        region.setBundesland("Nordrhein-Westfalen");
        region.setFaktor(1.0);
        regionRepository.save(region);
    }
}
