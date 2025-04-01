package com.example.versicherung.fahrzeugtyp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class FahrzeugtypService implements IFahrzeugtypService {

    @Autowired
    private FahrzeugtypRepository fahrzeugtypRepository;

    public Fahrzeugtyp addFahrzeugtyp(Fahrzeugtyp fahrzeugtypDTO) {
        if(fahrzeugtypRepository.existsByFahrzeugtyp(fahrzeugtypDTO.getFahrzeugtyp())){
            return null;
        }

        Fahrzeugtyp fahrzeugtyp = new Fahrzeugtyp(fahrzeugtypDTO.getFahrzeugtyp(), fahrzeugtypDTO.getFaktor());
        try{
            fahrzeugtypRepository.save(fahrzeugtyp);
            return fahrzeugtyp;
        }catch(Exception e){
            return null;
        }
    }

    public Fahrzeugtyp updateFahrzeugtypFaktor(Fahrzeugtyp fahrzeugtypDTO) {
        Optional<Fahrzeugtyp> existingFahrzeugtyp = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtypDTO.getFahrzeugtyp());
        if (existingFahrzeugtyp.isPresent()) {
            Fahrzeugtyp fahrzeugtyp = existingFahrzeugtyp.get();
            fahrzeugtyp.setFaktor(fahrzeugtypDTO.getFaktor());
            fahrzeugtypRepository.save(fahrzeugtyp);
            return fahrzeugtyp;
        } else {
            return null;
        }
    }

    public Fahrzeugtyp getFahrzeugtyp(String fahrzeugtyp) {
        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtyp);
        return fahrzeugtypOptional.orElse(null);
    }

    public List<Fahrzeugtyp> getAllFahrzeugtypen() {
        List<Fahrzeugtyp> fahrzeugtypen = (List<Fahrzeugtyp>) fahrzeugtypRepository.findAll();
        return fahrzeugtypen;
    }

    public boolean deleteFahrzeugtyp(String fahrzeugtyp) {
        Optional<Fahrzeugtyp> fahrzeugtypOptional = fahrzeugtypRepository.findByFahrzeugtyp(fahrzeugtyp);
        if(fahrzeugtypOptional.isPresent()){
            fahrzeugtypRepository.deleteByFahrzeugtyp(fahrzeugtyp);
            return true;
        }else{
            return false;
        }
    }
}
