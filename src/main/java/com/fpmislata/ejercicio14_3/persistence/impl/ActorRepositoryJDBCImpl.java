package com.fpmislata.ejercicio14_3.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fpmislata.ejercicio14_3.db.DBUtil;
import com.fpmislata.ejercicio14_3.domain.entity.Actor;
import com.fpmislata.ejercicio14_3.persistence.ActorRepository;

public class ActorRepositoryJDBCImpl implements ActorRepository {

    @Override
    public List<Actor> findByMovieImdbId(String imdb_id) {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBUtil.open()) {
            String sql = """
                        SELECT
                            a.*
                        FROM actors a
                        INNER JOIN actors_movies am ON a.imdb_id = am.actor_id
                        INNER JOIN movies m ON am.movie_id = m.imdb_id
                        WHERE m.imdb_id = ?
                    """;
            ResultSet actorsResultSet = DBUtil.select(connection, sql, List.of(imdb_id));
            while (actorsResultSet.next()) {
                Actor actor = new Actor(
                        actorsResultSet.getString("imdb_id"),
                        actorsResultSet.getString("name"),
                        actorsResultSet.getInt("birthYear"),
                        actorsResultSet.getInt("deathYear"));
                actors.add(actor);
            }
            return actors;
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        }

    }

    @Override
    public List<Actor> getAll() {
        List<Actor> actors = new ArrayList<>();
        try (Connection connection = DBUtil.open()) {
            String sql = "SELECT * FROM actors";
            ResultSet actorsResultSet = DBUtil.select(connection, sql, null);
            while (actorsResultSet.next()) {
                actors.add(new Actor(
                        actorsResultSet.getString("imdb_id"),
                        actorsResultSet.getString("name"),
                        actorsResultSet.getInt("birthYear"),
                        actorsResultSet.getInt("deathYear")));
            }
            return actors;
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        }
    }

    @Override
    public Actor findByImdbId(String actor_id) {
        try (Connection connection = DBUtil.open()) {
            String sql = """
                        SELECT
                            *
                        FROM actors
                        WHERE imdb_id = ?
                    """;
            ResultSet actorResultSet = DBUtil.select(connection, sql, List.of(actor_id));
            if (actorResultSet.next()) {
                Actor actor = new Actor(
                        actorResultSet.getString("imdb_id"),
                        actorResultSet.getString("name"),
                        actorResultSet.getInt("birthYear"),
                        actorResultSet.getInt("deathYear"));
                return actor;
            } else {
                throw new RuntimeException("No se encuentra el recurso");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        }
    }

    public List<String> findActor_idByMovieId(String movie_id) {
        List<String> actors_id = new ArrayList<>();
        try (Connection connection = DBUtil.open()) {
            String sql = """
                        SELECT
                            actor_id
                        FROM actors_movies
                        WHERE movie_id = ?
                    """;
            ResultSet actorResultSet = DBUtil.select(connection, sql, List.of(movie_id));
            while (actorResultSet.next()) {
                actors_id.add(actorResultSet.getString("actor_id"));
            }
            return actors_id;
        } catch (SQLException e) {
            throw new RuntimeException("No se ha podido acceder a la id");
        }
    }

    public List<Actor> findAllByImdbId(List<String> actors_id) {
        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < actors_id.size(); i++) {
            try (Connection connection = DBUtil.open()) {
                String sql = "SELECT * FROM actors WHERE imdb_id = ?";
                ResultSet actorsResultSet = DBUtil.select(connection, sql, List.of(actors_id.get(i)));
                actorsResultSet.next();
                actors.add(new Actor(
                        actorsResultSet.getString("imdb_id"),
                        actorsResultSet.getString("name"),
                        actorsResultSet.getInt("birthYear"),
                        actorsResultSet.getInt("deathYear")));
            } catch (SQLException e) {
                throw new RuntimeException("Problemas al construir la entidad");
            }
        }
        return actors;
    }

}
