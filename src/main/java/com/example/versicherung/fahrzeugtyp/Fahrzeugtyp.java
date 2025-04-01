package com.example.versicherung.fahrzeugtyp;

import com.example.versicherung.Versicherungspraemie.Versicherungspraemie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Fahrzeugtyp {

    @Id
    private String fahrzeugtyp;

    private Double faktor;

    @JsonIgnore
    @OneToMany(mappedBy="fahrzeugtyp")
    private Set<Versicherungspraemie> praemien;

    public Fahrzeugtyp(String fahrzeugtyp, Double faktor){
        this.fahrzeugtyp = fahrzeugtyp;
        this.faktor = faktor;
    }

    public Fahrzeugtyp() {
        this.fahrzeugtyp = "";
        this.faktor = 0d;
    }
}
