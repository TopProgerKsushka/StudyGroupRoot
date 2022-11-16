package org.ksushka.client.model;

public enum Semester {
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SEVENTH(7);

    private final int n;

    Semester(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }
}
