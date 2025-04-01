package com.example.versicherung.kilometerleistung;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KilometerleistungDTO {
    private int von;
    private int bis;
    private Double faktor;
}
