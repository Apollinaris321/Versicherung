package com.example.versicherung.Postleitzahl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/postleitzahl")
public class PostleitzahlController {

    @Autowired
    PostleitzahlRepository postleitzahlRepository;

    //TODO single response
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Keine CSV Datei erhalten.");
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            List<Postleitzahl> postleitzahlenList = readCSVFile(reader);
            Map<String, Postleitzahl> postleitzahlMap = new HashMap<>();
            for(Postleitzahl p : postleitzahlenList){
                if(!postleitzahlMap.containsKey(p.getUniqueKey())){
                    postleitzahlMap.put(p.getUniqueKey(), p);
                }
            }
            List<Postleitzahl> uniquePostleitzahlenList = new ArrayList<>(postleitzahlMap.values());
            postleitzahlRepository.saveAll(uniquePostleitzahlenList);
            return ResponseEntity.status(HttpStatus.OK).body("Datei erfolgreich hochgeladen.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //TODO single response
    public List<Postleitzahl> readCSVFile(BufferedReader csv) {
        try {
            List<Postleitzahl> postleitzahlenList  = new ArrayList<>();
            String line;
            while ((line = csv.readLine()) != null) {
                String bundesland = line.split(",")[2].replace("\"", "").replace(" ", "");
                String land = line.split(",")[4].replace("\"", "").replace(" ", "");
                String stadt = line.split(",")[5].replace("\"", "").replace(" ", "");
                String plzString = line.split(",")[6].replace("\"", "").replace(" ", "");
                String bezirk = line.split(",")[7].replace("\"", "").replace(" ", "");

                if (plzString.equals("POSTLEITZAHL")) {
                } else {
                    Integer plz = Integer.parseInt(plzString);
                    Postleitzahl postleitzahl = new Postleitzahl(plz, bundesland, land, stadt, bezirk);
                    postleitzahlenList.add(postleitzahl);
                }
            }
            return postleitzahlenList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Postleitzahl> readCSVPath(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return readCSVFile(br);
        } catch (IOException e) {
            System.err.println("Fehler beim lesen: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
