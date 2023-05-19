package com.fpmislata.ejercicio14_3.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fpmislata.ejercicio14_3.db.DBUtil;
import com.fpmislata.ejercicio14_3.domain.entity.Actor;
import com.fpmislata.ejercicio14_3.domain.entity.Director;
import com.fpmislata.ejercicio14_3.domain.entity.Movie;
import com.fpmislata.ejercicio14_3.persistence.MovieRepository;

public class MovieRepositoryJDBCImpl implements MovieRepository{

    @Override
    public List<Movie> getAll(int pageNumber) {
        int quantity = 10;
        int limit = pageNumber*quantity;
        List<Movie> movies = new ArrayList<>();
        try (Connection connection = DBUtil.open()) {
            String sql = "SELECT * FROM movies LIMIT ? , ?";
            ResultSet moviesResultSet = DBUtil.select(connection, sql, List.of(limit , quantity));
            while (moviesResultSet.next()) {
                movies.add(new Movie(
                        moviesResultSet.getString("imdb_id"), 
                        moviesResultSet.getString("title"), 
                        moviesResultSet.getInt("year"), 
                        moviesResultSet.getString("image"), 
                        moviesResultSet.getInt("runtime"), 
                        moviesResultSet.getString("description")
                    )
                );
            }
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        } 
    }

    @Override
    public Movie findByImdbId(String imdb_id) {
        try (Connection connection = DBUtil.open()) {
            String sql = """ 
                    SELECT 
                        m.*, 
                        d.name AS director_name, 
                        d.birthyear AS director_birthyear, 
                        d.deathyear AS director_deathyear 
                    FROM movies m 
                    INNER JOIN directors d ON m.director_id = d.imdb_id 
                    WHERE m.imdb_id = ?
                """;
            ResultSet movieResultSet = DBUtil.select(connection, sql, List.of(imdb_id));
            if(movieResultSet.next()) {
                Movie movie = new Movie(
                    movieResultSet.getString("imdb_id"), 
                    movieResultSet.getString("title"), 
                    movieResultSet.getInt("year"), 
                    movieResultSet.getString("image"), 
                    movieResultSet.getInt("runtime"), 
                    movieResultSet.getString("description")
                );
                Director director = new Director(
                            movieResultSet.getString("director_id"), 
                            movieResultSet.getString("director_name"),
                            movieResultSet.getInt("director_birthyear"),
                            movieResultSet.getInt("director_deathyear")
                );
                movie.setDirector(director);
                return movie;
            } else {
                throw new RuntimeException("No se encuentra el recurso");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        } 

    }

    @Override
    public void insert(Movie movie) {
        try (Connection connection = DBUtil.open()) {
            String sql = """
                INSERT INTO movies (imdb_id, title, year, image, runtime, description, director_id) 
                VALUES (?, ?, ?, ?, ?, ?, ?)                    
            """;
            //No se puede utilizar List.of, ya que no permite nulos
            List<Object> params = new ArrayList<>(Arrays.asList(
                movie.getImdb_id(), 
                movie.getTitle(), 
                movie.getYear(), 
                movie.getImage(), 
                movie.getRuntime(), 
                movie.getDescription(),
                movie.getDirector().getImdb_id()
            ));
            DBUtil.insert(connection, sql, params);
            /* Introducir los actores */
            for (Actor actor : movie.getActors()) {
                sql = """
                    INSERT INTO actors_movies(movie_id, actor_id)
                    VALUES (?, ?)
                """;
                DBUtil.insert(connection, sql, List.of(movie.getImdb_id(), actor.getImdb_id()));
            }
        } catch (RuntimeException e) {
            //System.out.println(e.getClass());
            if(e.getCause().getClass().getCanonicalName().equals("java.sql.SQLIntegrityConstraintViolationException")) {
                throw new RuntimeException("Ya existe el recurso");
            }
            throw e;
            //e.printStackTrace();
        /* } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("OK");
            throw new RuntimeException("Ya existe el recurso"); 
            PROBLEMA: La excepción que se lanza es de tipo RuntimeException, en lugar de SQLIntegrityConstraintViolationException*/
        } catch (SQLException e) {
            throw new RuntimeException("Error insertando el recurso");
        } 
    }

    @Override
    public void delete(String imdb_id) {
        try (Connection connection = DBUtil.open()) {
            String sql = """
                DELETE FROM movies WHERE imdb_id = ?
            """;
            if(DBUtil.delete(connection, sql, List.of(imdb_id))==0) {
              throw new RuntimeException("No se ha encontrado el recurso");  
            };
        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando el recurso");
        }
    }

    @Override
    public void update(Movie movie) {
        try (Connection connection = DBUtil.open()) {
            String sql = """
                UPDATE movies SET
                    title = ?,
                    year = ?,
                    image = ?,
                    runtime = ?,
                    description = ?,
                    director_id = ?
                WHERE imdb_id = ?
            """;
            List<Object> params = new ArrayList<>(Arrays.asList(
                    movie.getTitle(),
                    movie.getYear(),
                    movie.getImage(),
                    movie.getRuntime(),
                    movie.getDescription(),
                    movie.getDirector().getImdb_id(),
                    movie.getImdb_id()
            ));
            DBUtil.update(connection, sql, params);
            /* Eliminar los actores */
            sql = """
                    DELETE FROM actors_movies WHERE movie_id = ? 
                """;
            DBUtil.delete(connection, sql, List.of(movie.getImdb_id()));
            /* Introducir los actores */
            for (Actor actor : movie.getActors()) {
                sql = """
                    INSERT INTO actors_movies(movie_id, actor_id)
                    VALUES (?, ?)
                """;
                DBUtil.insert(connection, sql, List.of(movie.getImdb_id(), actor.getImdb_id()));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando el recurso");
        }
    }

}
