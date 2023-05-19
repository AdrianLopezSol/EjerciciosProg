package com.fpmislata.ejercicio14_3.domain.service;

import java.util.List;

import com.fpmislata.ejercicio14_3.domain.entity.Movie;

public interface MovieService {
 
    public List<Movie> getAll(int pageNumber);
    public Movie findByImdbId(String imdb_id);
    public void insert(Movie movie);
    public void delete(String imdb_id);
    void update(Movie movie);

}
