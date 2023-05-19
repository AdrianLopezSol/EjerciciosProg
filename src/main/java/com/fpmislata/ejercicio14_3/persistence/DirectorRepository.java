package com.fpmislata.ejercicio14_3.persistence;

import java.util.List;

import com.fpmislata.ejercicio14_3.domain.entity.Director;

public interface DirectorRepository {

    List<Director> getAll();

    Director findByImdbId(String imdb_id);
    
}
