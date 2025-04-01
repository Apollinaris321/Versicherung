package com.example.versicherung.Postleitzahl;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostleitzahlService implements IPostleitzahlService {

    @Autowired
    PostleitzahlRepository postleitzahlRepository;

    public List<Postleitzahl> uploadCsvFile(MultipartFile file) {
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
            return uniquePostleitzahlenList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String parseCSVLine(String line, PostleitzahlToken token , String splitter){
        String[] words = line.split(splitter);
        if(words.length >= token.getIndex()){
            throw new IllegalArgumentException("CSV Line invalid");
        }
        return words[token.getIndex()].replace("\"", "").trim();
    }

    public List<Postleitzahl> readCSVFile(BufferedReader csv) {
        try {
            List<Postleitzahl> postleitzahlenList  = new ArrayList<>();
            String line;
            while ((line = csv.readLine()) != null) {
                String bundesland = parseCSVLine(line, PostleitzahlToken.BUNDESLAND, ",");
                String land = parseCSVLine(line, PostleitzahlToken.LAND, ",");
                String stadt = parseCSVLine(line, PostleitzahlToken.STADT, ",");
                String plz = parseCSVLine(line, PostleitzahlToken.PLZ, ",");
                String bezirk = parseCSVLine(line, PostleitzahlToken.BEZIRK, ",");

                if (plz.equals("POSTLEITZAHL")) {
                } else {
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
