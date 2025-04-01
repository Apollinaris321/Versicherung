package com.example.versicherung.fahrzeugtyp;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FahrzeugtypRepository extends CrudRepository<Fahrzeugtyp, Long> {

    public Optional<Fahrzeugtyp> findByFahrzeugtyp(String fahrzeugtyp);
    public boolean existsByFahrzeugtyp(String fahrzeugtyp);
    public void deleteByFahrzeugtyp(String fahrzeugtyp);
}
