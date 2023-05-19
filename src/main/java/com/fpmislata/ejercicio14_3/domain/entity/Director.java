package com.fpmislata.ejercicio14_3.domain.entity;

public class Director {

    private String imdb_id;
    private String name;
    private int birthYear;
    private int deathYear;

    public Director(String imdb_id, String name, int birthYear, int deathYear) {
        this.imdb_id = imdb_id;
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    @Override
    public String toString() {
        return "Director [imdb_id=" + imdb_id + ", name=" + name + ", birthYear=" + birthYear + ", deathYear="
                + deathYear + "]";
    }
}
