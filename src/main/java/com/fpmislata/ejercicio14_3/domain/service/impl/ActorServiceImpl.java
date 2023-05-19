package com.fpmislata.ejercicio14_3.domain.service.impl;

import java.util.List;

import com.fpmislata.ejercicio14_3.domain.entity.Actor;
import com.fpmislata.ejercicio14_3.domain.service.ActorService;
import com.fpmislata.ejercicio14_3.persistence.ActorRepository;
import com.fpmislata.ejercicio14_3.persistence.impl.ActorRepositoryJDBCImpl;

public class ActorServiceImpl implements ActorService{

    private final ActorRepository actorRepository = new ActorRepositoryJDBCImpl();

    @Override
    public List<Actor> getAll() {
        return actorRepository.getAll();
    }

    @Override
    public Actor findByImdbId(String actor_id) {
        return actorRepository.findByImdbId(actor_id);
    }

    
}
