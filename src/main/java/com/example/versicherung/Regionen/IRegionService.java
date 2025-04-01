package com.example.versicherung.Regionen;

import java.util.List;

public interface IRegionService {

    public Region addRegion(Region region);
    public Region getRegionByBundesland(String bundesland);
    public List<Region> getAllRegions();
    public Region updateRegion( Region regionDTO);
    public boolean deleteRegion(String bundesland);

}
