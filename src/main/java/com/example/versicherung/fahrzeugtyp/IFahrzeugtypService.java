package com.example.versicherung.fahrzeugtyp;

import java.util.List;

public interface IFahrzeugtypService {

    public Fahrzeugtyp addFahrzeugtyp(Fahrzeugtyp fahrzeugtypDTO);

    public Fahrzeugtyp updateFahrzeugtypFaktor(Fahrzeugtyp fahrzeugtypDTO);

    public Fahrzeugtyp getFahrzeugtyp(String fahrzeugtyp);

    public List<Fahrzeugtyp> getAllFahrzeugtypen();

    public boolean deleteFahrzeugtyp(String fahrzeugtyp);
}
