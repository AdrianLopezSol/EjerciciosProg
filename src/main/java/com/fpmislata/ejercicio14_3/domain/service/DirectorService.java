package com.fpmislata.ejercicio14_3.domain.service;

import java.util.List;

import com.fpmislata.ejercicio14_3.domain.entity.Director;

public interface DirectorService {

    List<Director> getAll();

    Director findByImdbId(String imdb_id);
    

}
