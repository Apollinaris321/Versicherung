package com.example.versicherung.Postleitzahl;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(uniqueConstraints =
        { //other constraints
                @UniqueConstraint(name = "postleitzahlKey", columnNames = { "bundesland", "land", "stadt", "bezirk", "plz" })})
public class Postleitzahl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String bundesland;
    public String land;
    public String stadt;
    public String bezirk;
    public Integer plz;

    public String getUniqueKey(){
        return this.bundesland + this.land + this.stadt + this.bezirk + this.plz.toString();
    }

    public Postleitzahl(){
    }

    public Postleitzahl(Integer plz, String bundesland, String land, String stadt, String bezirk) {
        this.bundesland = bundesland;
        this.land = land;
        this.stadt = stadt;
        this.bezirk = bezirk;
        this.plz = plz;
    }
}
