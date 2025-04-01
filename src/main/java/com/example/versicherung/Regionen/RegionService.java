package com.example.versicherung.Regionen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService implements IRegionService {

    @Autowired
    RegionRepository regionRepository;

    public Region addRegion(@RequestBody Region region) throws IllegalArgumentException{
        if(region.getFaktor() < 0){
            throw new IllegalArgumentException("Regionfaktor muss positiv sein!");
        }
        if(region.getBundesland().isEmpty()){
            throw new IllegalArgumentException("Kein Bundesland angegeben!");
        }
        regionRepository.save(region);
        return region;
    }

    public Region getRegionByBundesland(String bundesland){
        Optional<Region> region = regionRepository.findByBundesland(bundesland);
        if(region.isPresent()){
            return region.get();
        }else{
            return null;
        }
    }

    public List<Region> getAllRegions(){
        return (List<Region>)regionRepository.findAll();
    }

    public Region updateRegion(Region regionDTO){
        Optional<Region> regionOptional = regionRepository.findByBundesland(regionDTO.getBundesland());
        if(regionOptional.isPresent()){
            Region region = regionOptional.get();
            region.setFaktor(regionDTO.getFaktor());
            regionRepository.save(region);
            return region;
        }else{
            return null;
        }
    }

    public boolean deleteRegion(String bundesland){
        Optional<Region> regionOptional = regionRepository.findByBundesland(bundesland);
        if(regionOptional.isPresent()){
            regionRepository.deleteById(bundesland);
            return true;
        }else{
            return false;
        }
    }
}
