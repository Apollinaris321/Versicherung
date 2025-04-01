package com.example.versicherung.Postleitzahl;

import org.springframework.data.repository.CrudRepository;

public interface PostleitzahlRepository extends CrudRepository<Postleitzahl, Long> {
    public Postleitzahl findDistinctFirstByPlz(String plz);
}
