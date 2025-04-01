package com.example.versicherung.kilometerleistung;

import com.example.versicherung.Versicherungspraemie.Versicherungspraemie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kilometerleistung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy="km")
    private Set<Versicherungspraemie> praemien;

    private int von;
    private int bis;
    private Double faktor;

    public Kilometerleistung(int von, int bis, Double faktor){
        this.von = von;
        this.bis = bis;
        this.faktor = faktor;
    }

    //TODO function that checks if a value falls into this kilometerleistung
}
