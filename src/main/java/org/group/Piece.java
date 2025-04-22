package org.group;

import java.io.Serializable;

public class Piece implements Serializable {
    private String color;

    public Piece(String color){
        this.color = color;
    }

    public String getColor(){
        return color;
    }
}
