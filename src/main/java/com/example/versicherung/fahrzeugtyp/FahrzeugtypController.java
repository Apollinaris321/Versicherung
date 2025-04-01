package com.example.versicherung.fahrzeugtyp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/fahrzeugtyp")
public class FahrzeugtypController {

    @Autowired
    private FahrzeugtypRepository fahrzeugtypRepository;

    @Autowired
    IFahrzeugtypService fahrzeugtypService;

    @PostMapping("/add")
    public ResponseEntity<?> addFahrzeugtyp(@RequestBody Fahrzeugtyp fahrzeugtypDTO) {
        if(fahrzeugtypDTO.getFahrzeugtyp() == null || fahrzeugtypDTO.getFahrzeugtyp().trim().isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fahrzeugtyp darf nicht leer sein!");
        }
        if(fahrzeugtypDTO.getFaktor() == null || fahrzeugtypDTO.getFaktor() < 0) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faktor muss eine positive Zahl sein!");
        }

        Fahrzeugtyp fahrzeugtyp = fahrzeugtypService.addFahrzeugtyp(fahrzeugtypDTO);
        if(fahrzeugtyp != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(fahrzeugtyp);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Farzeugtyp konnte nicht gespeichert werden!");
        }
    }

    @PutMapping("/update/{fahrzeugtypName}")
    public ResponseEntity<?> updateFahrzeugtypFaktor(@RequestBody Fahrzeugtyp fahrzeugtypDTO) {
        if(fahrzeugtypDTO.getFahrzeugtyp() == null || fahrzeugtypDTO.getFahrzeugtyp().trim().isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fahrzeugtyp darf nicht leer sein!");
        }
        if(fahrzeugtypDTO.getFaktor() == null || fahrzeugtypDTO.getFaktor() < 0) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faktor muss eine positive Zahl sein!");
        }

        Fahrzeugtyp fahrzeugtyp =  fahrzeugtypService.updateFahrzeugtypFaktor(fahrzeugtypDTO);
        if (fahrzeugtyp != null) {
            return ResponseEntity.ok(fahrzeugtyp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fahrzeugtyp existiert nicht!");
        }
    }

    @GetMapping("/{fahrzeugtyp}")
    public ResponseEntity<?> getFahrzeugtyp(@PathVariable String fahrzeugtypName) {
        Fahrzeugtyp fahrzeugtyp =  fahrzeugtypService.getFahrzeugtyp(fahrzeugtypName);
        if(fahrzeugtyp != null){
            return ResponseEntity.ok(fahrzeugtyp);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fahrzeugtyp nicht gefunden!");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Fahrzeugtyp>> getAllFahrzeugtypen() {
        List<Fahrzeugtyp> fahrzeugtypen = fahrzeugtypService.getAllFahrzeugtypen();
        return ResponseEntity.ok(fahrzeugtypen);
    }

    @DeleteMapping("/{fahrzeugtyp}")
    public ResponseEntity<?> deleteFahrzeugtyp(@PathVariable String fahrzeugtyp) {
        if(fahrzeugtypService.deleteFahrzeugtyp(fahrzeugtyp)){
            return ResponseEntity.ok().body("Fahrzeugtyp gelöscht!");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fahrzeugtyp nicht gefunden!");
        }
    }
}
