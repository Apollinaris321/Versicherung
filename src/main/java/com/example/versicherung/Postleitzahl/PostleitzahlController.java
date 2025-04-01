package com.example.versicherung.Postleitzahl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    IPostleitzahlService postleitzahlService;

    //TODO single response
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Keine CSV Datei erhalten.");
        }
        try{
            List<Postleitzahl> postleitzahlen = postleitzahlService.uploadCsvFile(file);
            if(postleitzahlen != null){
                return ResponseEntity.status(HttpStatus.OK).body("Datei erfolgreich hochgeladen.");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Daten nicht eingelesen!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fehler beim einlesen!");
        }
    }
}
