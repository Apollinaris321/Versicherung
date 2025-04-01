package com.example.versicherung.kilometerleistung;

import java.util.List;

public interface IKilometerleistungService {


    public Kilometerleistung addKilometerleistung(KilometerleistungDTO kilometerleistungDTO) throws Exception;
    public Kilometerleistung getKilometerleistung(Long id);
    public List<Kilometerleistung> getAllKilometerleistung();
    public boolean deleteKilometerleistung(Long id);
    public Kilometerleistung getKilometerleistung(Double kilometerstand);
    public Double getFaktorFromKilometerstand(Double kilometerstand) throws Exception;

}
