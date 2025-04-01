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

    @PostMapping("/add")
    public ResponseEntity<?> addFahrzeugtyp(@RequestBody Fahrzeugtyp fahrzeugtypDTO) {
        if(fahrzeugtypDTO.getFahrzeugtyp() == null || fahrzeugtypDTO.getFahrzeugtyp().trim().isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fahrzeugtyp darf nicht leer sein!");
        }
        if(fahrzeugtypDTO.getFaktor() == null || fahrzeugtypDTO.getFaktor() < 0) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faktor muss eine positive Zahl sein!");
        }
        if(fahrzeugtypRepository.existsByFahrzeugtyp(fahrzeugtypDTO.getFahrzeugtyp())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Fahrzeugtyp mit diesem Namen existiert bereits!");
        }

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp(fahrzeugtypDTO.getFahrzeugtyp(), fahrzeugtypDTO.getFaktor());
        fahrzeugtypRepository.save(fahrzeugtyp);
        return ResponseEntity.status(HttpStatus.CREATED).body(fahrzeugtyp);
    }

    @PutMapping("/update/{fahrzeugtypName}")
    public ResponseEntity<?> updateFahrzeugtypFaktor(@RequestBody Fahrzeugtyp fahrzeugtypDTO) {
        Optional<Fahrzeugtyp> existingFahrzeugtyp = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtypDTO.getFahrzeugtyp());
        if (existingFahrzeugtyp.isPresent()) {
            Fahrzeugtyp fahrzeugtyp = existingFahrzeugtyp.get();
            fahrzeugtyp.setFaktor(fahrzeugtypDTO.getFaktor());
            fahrzeugtypRepository.save(fahrzeugtyp);
            return ResponseEntity.ok(fahrzeugtyp);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fahrzeugtyp existiert nicht!");
        }
    }

    @GetMapping("/{fahrzeugtyp}")
    public ResponseEntity<?> getFahrzeugtyp(@PathVariable String fahrzeugtyp) {
        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtyp);
        if(fahrzeugtypOptional.isPresent()){
            return ResponseEntity.ok(fahrzeugtypOptional.get());
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fahrzeugtyp nicht gefunden!");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Fahrzeugtyp>> getAllFahrzeugtypen() {
        List<Fahrzeugtyp> fahrzeugtypen = (List<Fahrzeugtyp>) fahrzeugtypRepository.findAll();
        return ResponseEntity.ok(fahrzeugtypen);
    }

    @DeleteMapping("/{fahrzeugtyp}")
    public ResponseEntity<?> deleteFahrzeugtyp(@PathVariable String fahrzeugtyp) {
        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtyp);
        if(fahrzeugtypOptional.isPresent()){
            fahrzeugtypRepository.deleteByFahrzeugtyp(fahrzeugtyp);
            return ResponseEntity.ok().body("Fahrzeugtyp gelöscht!");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fahrzeugtyp nicht gefunden!");
        }
    }
}
