package com.example.versicherung.Versicherungspraemie;

import com.example.versicherung.Regionen.Region;
import com.example.versicherung.fahrzeugtyp.Fahrzeugtyp;
import com.example.versicherung.kilometerleistung.Kilometerleistung;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Versicherungspraemie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fahrzeugtyp", nullable = false)
    private Fahrzeugtyp fahrzeugtyp;

    @ManyToOne
    @JoinColumn(name = "bundesland", nullable = false)
    private Region region;

    @ManyToOne
    @JoinColumn(name = "kilometerleistung_id", nullable = false)
    private Kilometerleistung km;

    private Double praemie;

    private Date berechnetAm;

    public Versicherungspraemie(Fahrzeugtyp fahrzeugtyp, Region region, Kilometerleistung km){
        this.fahrzeugtyp = fahrzeugtyp;
        this.region = region;
        this.km = km;

        this.berechnetAm = new Date();

        this.praemie = praemieBerechnen();
    }

    public Double praemieBerechnen(){
        return this.fahrzeugtyp.getFaktor() * this.region.getFaktor() * this.km.getFaktor();
    }
}
