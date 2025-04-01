package com.example.versicherung.kilometerleistung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KilometerleistungService {
    @Autowired
    private KilometerleistungRepository kilometerleistungRepository;


    public Kilometerleistung getKilometerleistung(Double kilometerstand){
        List<Kilometerleistung> kilometerleistungList = (List<Kilometerleistung>) kilometerleistungRepository.findAll();
        for (Kilometerleistung k : kilometerleistungList) {
            if (k.getBis() > 0) {
                if (kilometerstand >= k.getVon() && kilometerstand <= k.getBis()) {
                    return k;
                }
            } else {
                if (kilometerstand >= k.getVon()) {
                    return k;
                }
            }
        }
        return null;
    }

    public Double getFaktorFromKilometerstand(Double kilometerstand) throws Exception {
        List<Kilometerleistung> kilometerleistungList = (List<Kilometerleistung>) kilometerleistungRepository.findAll();
        for (Kilometerleistung k : kilometerleistungList) {
            if (k.getBis() > 0) {
                if (kilometerstand >= k.getVon() && kilometerstand <= k.getBis()) {
                    return k.getFaktor();
                }
            } else {
                if (kilometerstand >= k.getVon()) {
                    return k.getFaktor();
                }
            }
        }
        throw new Exception("Keinen passenden Kilometerfaktor gefunden!");
    }
}
