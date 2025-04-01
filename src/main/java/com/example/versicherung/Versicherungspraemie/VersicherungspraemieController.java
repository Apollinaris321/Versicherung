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
    VersicherungspraemienRepository verischerungspraemieRepository;

    @Autowired
    FahrzeugtypRepository fahrzeugtypRepository;

    @Autowired
    PostleitzahlRepository postleitzahlRepository;

    @Autowired
    RegionRepository regionRepository;

    @Autowired
    KilometerleistungService kilometerleistungService;

    @PostMapping("/submit")
    public ResponseEntity<?> handlePraemie(@RequestBody AnfrageDTO anfrageDTO) {

        Postleitzahl plz = postleitzahlRepository.findDistinctFirstByPlz(anfrageDTO.getPlz());
        Optional<Region> regionOptional = regionRepository.findByBundesland(plz.getBundesland());
        if(regionOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Region nicht vorhanden!");
        }
        Region region = regionOptional.get();

        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(anfrageDTO.getFahrzeugtyp());
        if(fahrzeugtypOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Fahrzeugtyp nicht vorhanden!");
        }
        Fahrzeugtyp fahrzeugtyp = fahrzeugtypOptional.get();

        Kilometerleistung km = kilometerleistungService.getKilometerleistung(anfrageDTO.getKilometerleistung());
        if(km == null){
            return ResponseEntity.badRequest().body("Kilometerleistung nicht vorhanden!");
        }

        Versicherungspraemie vp = new Versicherungspraemie(fahrzeugtyp, region, km);
        Double praemie = vp.getPraemie();

        verischerungspraemieRepository.save(vp);

        return ResponseEntity.ok().body(praemie);
    }
}
