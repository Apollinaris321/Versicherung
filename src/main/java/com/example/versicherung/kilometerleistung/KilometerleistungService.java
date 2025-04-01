package com.example.versicherung.kilometerleistung;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class KilometerleistungService implements IKilometerleistungService {

    @Autowired
    private KilometerleistungRepository kilometerleistungRepository;

    public Kilometerleistung addKilometerleistung(KilometerleistungDTO kilometerleistungDTO) throws Exception {
        if(kilometerleistungDTO.getFaktor() < 0){
            throw new Exception("Kilometerleistungsfaktor muss positiv sein!");
        }
        if(kilometerleistungDTO.getVon() < 0){
            throw new Exception("Kilometeleistung -von- muss positiv sein!");
        }
        if(kilometerleistungDTO.getBis() > 0 && kilometerleistungDTO.getVon() > kilometerleistungDTO.getBis()){
            throw new Exception("Kilometeleistung -von- muss größer sein als -bis- Wert!");
        }

        Kilometerleistung kilometerleistung = new Kilometerleistung(kilometerleistungDTO.getVon(), kilometerleistungDTO.getBis(), kilometerleistungDTO.getFaktor());
        kilometerleistungRepository.save(kilometerleistung);
        return kilometerleistung;
    }

    public Kilometerleistung getKilometerleistung(Long id){
        Optional<Kilometerleistung> kilometerleistung = kilometerleistungRepository.findById(id);
        if(kilometerleistung.isPresent()){
            return kilometerleistung.get();
        }else{
            return null;
        }
    }

    public List<Kilometerleistung> getAllKilometerleistung(){
        return (List<Kilometerleistung>)kilometerleistungRepository.findAll();
    }

    public boolean deleteKilometerleistung(Long id){
        Optional<Kilometerleistung> kilometerleistung = kilometerleistungRepository.findById(id);
        if(kilometerleistung.isPresent()){
            kilometerleistungRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }


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
