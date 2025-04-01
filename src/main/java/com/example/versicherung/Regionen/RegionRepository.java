package com.example.versicherung.Regionen;

import org.springframework.data.repository.CrudRepository;

import javax.sql.rowset.CachedRowSet;
import java.util.Optional;

public interface RegionRepository extends CrudRepository<Region, String> {
    public Optional<Region> findByBundesland(String bundesland);

    public boolean existsByBundesland(String bundesland);
}
