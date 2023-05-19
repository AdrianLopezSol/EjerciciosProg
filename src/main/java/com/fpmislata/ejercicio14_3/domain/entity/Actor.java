package com.fpmislata.ejercicio14_3.domain.entity;

import java.util.Objects;

public class Actor {

    private String imdb_id;
    private String name;
    private int birthYear;
    private int deathYear;

    public Actor(String imdb_id, String name, int birthYear, int deathYear) {
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
        return "Actor [imdb_id=" + imdb_id + ", name=" + name + ", birthYear=" + birthYear + ", deathYear=" + deathYear
                + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(imdb_id, actor.imdb_id);
    }
}
