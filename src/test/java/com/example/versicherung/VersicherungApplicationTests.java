package com.example.versicherung;

import com.example.versicherung.Zulassungsstelle.ZulassungsstelleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VersicherungApplicationTests {

    @Test
    void contextLoads() {
        ZulassungsstelleService zulassungsstelleService = new ZulassungsstelleService();
        zulassungsstelleService.readCSV("C:/Users/blura/Desktop/versicherung/postcodes.csv");
    }

}
