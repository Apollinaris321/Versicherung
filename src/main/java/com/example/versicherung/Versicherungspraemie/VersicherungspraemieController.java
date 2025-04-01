package com.example.versicherung.Versicherungspraemie;

import com.example.versicherung.Postleitzahl.Postleitzahl;
import com.example.versicherung.Postleitzahl.PostleitzahlRepository;
import com.example.versicherung.Regionen.Region;
import com.example.versicherung.Regionen.RegionRepository;
import com.example.versicherung.fahrzeugtyp.Fahrzeugtyp;
import com.example.versicherung.fahrzeugtyp.FahrzeugtypRepository;
import com.example.versicherung.kilometerleistung.Kilometerleistung;
import com.example.versicherung.kilometerleistung.KilometerleistungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/praemie")
public class VersicherungspraemieController {

    @Autowired
    IVersicherungspraemieService versicherungspraemieService;

    @PostMapping("/submit")
    public ResponseEntity<?> handlePraemie(@RequestBody AnfrageDTO anfrageDTO) {
        if(!anfrageDTO.getPlz().matches("\\d{5}")){
            return ResponseEntity.badRequest().body("Plz nicht valide!");
        }
        try{
            Versicherungspraemie vp = versicherungspraemieService.handlePraemie(anfrageDTO);
            return ResponseEntity.ok().body(vp);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
