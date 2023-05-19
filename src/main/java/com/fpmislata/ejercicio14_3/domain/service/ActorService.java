package com.fpmislata.ejercicio14_3.domain.service;

import java.util.List;

import com.fpmislata.ejercicio14_3.domain.entity.Actor;

public interface ActorService {

    List<Actor> getAll();

    Actor findByImdbId(String actor_id);
    
}
