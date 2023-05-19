package com.fpmislata.ejercicio14_3.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fpmislata.ejercicio14_3.db.DBUtil;
import com.fpmislata.ejercicio14_3.domain.entity.Director;
import com.fpmislata.ejercicio14_3.persistence.DirectorRepository;

public class DirectorRepositoryJDBCImpl implements DirectorRepository{

    @Override
    public List<Director> getAll() {
        List<Director> directors = new ArrayList<>();
        try (Connection connection = DBUtil.open()) {
            String sql = "SELECT * FROM directors";
            ResultSet directorsResultSet = DBUtil.select(connection, sql, null);
            while (directorsResultSet.next()) {
                directors.add(new Director(
                        directorsResultSet.getString("imdb_id"), 
                        directorsResultSet.getString("name"), 
                        directorsResultSet.getInt("birthYear"), 
                        directorsResultSet.getInt("deathYear")
                    )
                );
            }
            return directors;
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        } 
    }

    @Override
    public Director findByImdbId(String imdb_id) {
        try (Connection connection = DBUtil.open()) {
            String sql = """ 
                    SELECT 
                        *
                    FROM directors 
                    WHERE imdb_id = ?
                """;
            ResultSet directorResultSet = DBUtil.select(connection, sql, List.of(imdb_id));
            if(directorResultSet.next()) {
                Director director = new Director(
                    directorResultSet.getString("imdb_id"), 
                    directorResultSet.getString("name"), 
                    directorResultSet.getInt("birthYear"), 
                    directorResultSet.getInt("deathYear")
                );
                return director;
            } else {
                throw new RuntimeException("No se encuentra el director");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Problemas al construir la entidad");
        } 
    }
    
}
