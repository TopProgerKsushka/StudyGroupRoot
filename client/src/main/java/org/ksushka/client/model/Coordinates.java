package org.ksushka.client.model;

public class Coordinates {

    public Coordinates() {}
    public Coordinates(int x, float y) {
        this.x = x;
        this.y = y;
    }
    private Integer x; //Максимальное значение поля: 594
    private Float y; //Поле не может быть null

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }
}
