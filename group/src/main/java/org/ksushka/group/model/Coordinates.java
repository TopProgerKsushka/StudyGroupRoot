package org.ksushka.group.model;

public class Coordinates {
    private Integer x; //Максимальное значение поля: 594
    private Float y; //Поле не может быть null

    public Integer getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    public void setX(int x) { this.x = x; }

    public void setY(float y) { this.y = y; }
}
