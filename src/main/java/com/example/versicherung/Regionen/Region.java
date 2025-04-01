package com.example.versicherung.Regionen;

import com.example.versicherung.Versicherungspraemie.Versicherungspraemie;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Region {
    @Id
    private String bundesland;
    private Double faktor;

    @JsonIgnore
    @OneToMany(mappedBy="region")
    private Set<Versicherungspraemie> praemien;

    public Region(String bundesland, Double faktor){
        this.bundesland = bundesland;
        this.faktor = faktor;
    }

    public Region(){
        this.bundesland = "";
        this.faktor = 0.0;
    }
}
