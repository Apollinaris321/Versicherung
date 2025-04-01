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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VersicherungspraemieService implements IVersicherungspraemieService {

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

    public Versicherungspraemie handlePraemie(AnfrageDTO anfrageDTO) {
        Postleitzahl plz = postleitzahlRepository.findDistinctFirstByPlz(anfrageDTO.getPlz());
        if(plz == null){
            throw new IllegalArgumentException("Postleitzahl nicht vorhanden!");
        }
        Optional<Region> regionOptional = regionRepository.findByBundesland(plz.getBundesland());
        if(regionOptional.isEmpty()){
            throw new IllegalArgumentException("Region nicht vorhanden!");
        }
        Region region = regionOptional.get();

        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(anfrageDTO.getFahrzeugtyp());
        if(fahrzeugtypOptional.isEmpty()){
            throw new IllegalArgumentException("Fahrzeugtyp nicht vorhanden!");
        }
        Fahrzeugtyp fahrzeugtyp = fahrzeugtypOptional.get();

        Kilometerleistung km = kilometerleistungService.getKilometerleistung(anfrageDTO.getKilometerleistung());
        if(km == null){
            throw new IllegalArgumentException("Kilometerleistung nicht vorhanden!");
        }

        Versicherungspraemie vp = new Versicherungspraemie(fahrzeugtyp, region, km);
        vp.getPraemie();
        verischerungspraemieRepository.save(vp);
        return vp;
    }
}
