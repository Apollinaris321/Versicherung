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
    KilometerleistungService kilometerleistungService;

    @PostMapping("/new")
    public ResponseEntity<?> addKilometerleistung(@RequestBody KilometerleistungDTO kilometerleistungDTO){
        if(kilometerleistungDTO.getFaktor() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kilometerleistungsfaktor muss positiv sein!");
        }
        if(kilometerleistungDTO.getVon() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kilometeleistung -von- muss positiv sein!");
        }
        if(kilometerleistungDTO.getBis() > 0 && kilometerleistungDTO.getVon() > kilometerleistungDTO.getBis()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kilometeleistung -von- muss größer sein als -bis- Wert!");
        }

        Kilometerleistung kilometerleistung = new Kilometerleistung(kilometerleistungDTO.getVon(), kilometerleistungDTO.getBis(), kilometerleistungDTO.getFaktor());
        kilometerleistungRepository.save(kilometerleistung);
        return ResponseEntity.ok().body(kilometerleistung);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKilometerleistung(@RequestParam Long id){
        Optional<Kilometerleistung> kilometerleistung = kilometerleistungRepository.findById(id);
        if(kilometerleistung.isPresent()){
            return ResponseEntity.ok().body(kilometerleistung.get());
        }else{
            return ResponseEntity.badRequest().body("Kilometerleistung with this id not found!");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllKilometerleistung(){
        List<Kilometerleistung> liste = (List<Kilometerleistung>)kilometerleistungRepository.findAll();
        return ResponseEntity.ok().body(liste);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKilometerleistung(@RequestParam Long id){
        Optional<Kilometerleistung> kilometerleistung = kilometerleistungRepository.findById(id);
        if(kilometerleistung.isPresent()){
            kilometerleistungRepository.deleteById(id);
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
