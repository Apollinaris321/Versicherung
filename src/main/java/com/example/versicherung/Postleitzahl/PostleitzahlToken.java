package com.example.versicherung.Postleitzahl;

import lombok.Getter;

@Getter
public enum PostleitzahlToken{
    BUNDESLAND(2),
    LAND(4),
    STADT(5),
    PLZ(6),
    BEZIRK(7);

    @Getter
    private final int index;

    PostleitzahlToken(int index){
        this.index = index;
    }
}
