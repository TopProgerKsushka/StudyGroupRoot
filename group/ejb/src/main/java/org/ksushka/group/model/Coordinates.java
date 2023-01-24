package org.ksushka.group.model;

import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Максимальное значение поля: 594
    private Float y; //Поле не может быть null

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
