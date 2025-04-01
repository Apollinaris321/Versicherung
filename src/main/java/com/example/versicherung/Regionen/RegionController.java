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

    @Autowired
    IRegionService regionService;

    @PostMapping("/new")
    public ResponseEntity<?> addRegion(@RequestBody Region region){
        try{
            regionService.addRegion(region);
            return ResponseEntity.ok().body(region);
        }catch(IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{bundesland}")
    public ResponseEntity<?> getRegionByBundesland(@PathVariable String bundesland){
        Region region = regionService.getRegionByBundesland(bundesland);
        if(region != null){
            return ResponseEntity.ok().body(region);
        }else{
            return ResponseEntity.badRequest().body("Bundesland nicht gefunden!");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllRegions(){
        List<Region> regions = regionService.getAllRegions();
        return ResponseEntity.ok().body(regions);
    }

    @PutMapping("/{bundesland}")
    public ResponseEntity<?> updateRegion(@RequestBody Region regionDTO){
        Region region = regionService.updateRegion(regionDTO);
        if(region != null){
            return ResponseEntity.ok().body(region);
        }else{
            return ResponseEntity.badRequest().body("Bundesland nicht gefunden!");
        }
    }

    @DeleteMapping("/{bundesland}")
    public ResponseEntity<?> deleteRegion(@PathVariable String bundesland){
        Boolean isDeleted = regionService.deleteRegion(bundesland);
        if(isDeleted){
            return ResponseEntity.ok().body("Region entfernt!");
        }else{
            return ResponseEntity.badRequest().body("Bundesland existiert nicht!");
        }
    }
}
