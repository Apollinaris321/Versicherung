package com.example.versicherung.Regionen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    @Autowired
    RegionRepository regionRepository;

    @PostMapping("/new")
    public ResponseEntity<?> addRegion(@RequestBody Region region){
        if(region.getFaktor() < 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Regionfaktor muss positiv sein!");
        }
        if(region.getBundesland().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Kein Bundesland angegeben!");
        }

        regionRepository.save(region);
        return ResponseEntity.ok().body(region);
    }

    @GetMapping("/{bundesland}")
    public ResponseEntity<?> getRegionByBundesland(@PathVariable String bundesland){
        Optional<Region> region = regionRepository.findByBundesland(bundesland);
        if(region.isPresent()){
            return ResponseEntity.ok().body(region.get());
        }else{
            return ResponseEntity.badRequest().body("Bundesland nicht gefunden!");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRegions(){
        List<Region> regions = (List<Region>)regionRepository.findAll();
        return ResponseEntity.ok().body(regions);
    }

    @PutMapping("/{bundesland}")
    public ResponseEntity<?> updateRegion(@RequestBody Region regionDTO){
        Optional<Region> regionOptional = regionRepository.findByBundesland(regionDTO.getBundesland());
        if(regionOptional.isPresent()){
            Region region = regionOptional.get();
            region.setFaktor(regionDTO.getFaktor());
            regionRepository.save(region);
            return ResponseEntity.ok().body(region);
        }else{
            return ResponseEntity.badRequest().body("Bundesland nicht gefunden!");
        }
    }

    @DeleteMapping("/{bundesland}")
    public ResponseEntity<?> deleteRegion(@PathVariable String bundesland){
        Optional<Region> regionOptional = regionRepository.findByBundesland(bundesland);
        if(regionOptional.isPresent()){
            regionRepository.deleteById(bundesland);
            return ResponseEntity.ok().body("Region entfernt!");
        }else{
            return ResponseEntity.badRequest().body("Bundesland existiert nicht!");
        }
    }
}
