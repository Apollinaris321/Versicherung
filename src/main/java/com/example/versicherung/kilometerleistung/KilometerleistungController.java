package com.example.versicherung.kilometerleistung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/kilometerleistung")
public class KilometerleistungController {

    @Autowired
    KilometerleistungRepository kilometerleistungRepository;

    @Autowired
    IKilometerleistungService kilometerleistungService;

    @PostMapping("/new")
    public ResponseEntity<?> addKilometerleistung(@RequestBody KilometerleistungDTO kilometerleistungDTO){
        try {
            Kilometerleistung km = kilometerleistungService.addKilometerleistung(kilometerleistungDTO);
            return ResponseEntity.ok().body(km);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKilometerleistung(@RequestParam Long id){
        Kilometerleistung km = kilometerleistungService.getKilometerleistung(id);
        if(km != null){
            return ResponseEntity.ok().body(km);
        }else{
            return ResponseEntity.badRequest().body("Kilometerleistung nicht gefunden!");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllKilometerleistung(){
        List<Kilometerleistung> liste = kilometerleistungService.getAllKilometerleistung();
        return ResponseEntity.ok().body(liste);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKilometerleistung(@RequestParam Long id){
        Boolean isDeleted = kilometerleistungService.deleteKilometerleistung(id);
        if(isDeleted){
            return ResponseEntity.ok().body("Kilometerleistung deleted!");
        }else{
            return ResponseEntity.badRequest().body("Kilometerleistung with this id not found!");
        }
    }

    @GetMapping("/{kilometerstand}")
    public ResponseEntity<?> getFaktorFromKilometerstand(@RequestParam Double kilometerstand){
        try{
            Double faktor = kilometerleistungService.getFaktorFromKilometerstand(kilometerstand);
            return ResponseEntity.ok().body(faktor);
        }catch(Exception e){
            return ResponseEntity.badRequest().body("Keinen Faktor gefunden");
        }
    }
}
