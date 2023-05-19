package com.fpmislata.ejercicio14_3.domain.service.impl;

import java.util.List;

import com.fpmislata.ejercicio14_3.domain.entity.Director;
import com.fpmislata.ejercicio14_3.domain.service.DirectorService;
import com.fpmislata.ejercicio14_3.persistence.DirectorRepository;
import com.fpmislata.ejercicio14_3.persistence.impl.DirectorRepositoryJDBCImpl;

public class DirectorServiceImpl implements DirectorService{

    private final DirectorRepository directorRepository = new DirectorRepositoryJDBCImpl();

    @Override
    public List<Director> getAll() {
        return directorRepository.getAll();
    }

    @Override
    public Director findByImdbId(String imdb_id) {
        return directorRepository.findByImdbId(imdb_id);
    }
    
}
