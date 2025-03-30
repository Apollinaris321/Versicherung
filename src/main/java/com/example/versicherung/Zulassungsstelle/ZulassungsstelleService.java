package com.example.versicherung.Zulassungsstelle;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZulassungsstelleService {

    // public String bundesland;
    // public Integer plz;
    // public Float regionFaktor;

    public  ZulassungsstelleService(){

    }

    public Map<String, Integer> readCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            Map<String, Integer> inventurMap = new HashMap<>();
            String line;
            while ((line = br.readLine()) != null) {
                String bundesland = line.split(",")[2];
                String plz = line.split(",")[6];
                System.out.println("bundesland: " + bundesland + ", plz: " + plz);
                //inventurMap.put(bun, Integer.parseInt(stückzahl));
            }
            return inventurMap;
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
