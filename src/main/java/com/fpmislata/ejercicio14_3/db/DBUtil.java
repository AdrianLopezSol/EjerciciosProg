package com.fpmislata.ejercicio14_3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DBUtil {
    
    public static Connection open(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/imdb", 
                "root", 
                "root"
            );
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static void close(Connection connection) {
        try {
            System.out.println("Cerrando la conexión con la base de datos...");

            connection.close();

            System.out.println("Estado de la conexión: " + connection.isValid(0));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet select(Connection connection, String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return preparedStatement.executeQuery();            
        } catch (Exception e) {
            throw new RuntimeException("Error al ejecutar la sentencia: " + sql);
        }
    }

    public static int insert(Connection connection, String sql, List<Object> values) {    
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static int update(Connection connection, String sql, List<Object> values) {    
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            int numRows = preparedStatement.executeUpdate();
            return numRows;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static PreparedStatement setParameters(Connection connection, String sql, List<Object> values){
        try {
            PreparedStatement preparedStatement =  connection.prepareStatement(sql);
            if(values != null) {
                for(int i=0;i<values.size();i++) {
                    Object value = values.get(i);
                    preparedStatement.setObject(i+1,value);
                }
            }    
            return preparedStatement;                        
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int delete(Connection connection, String sql, List<Object> values) {
        try {
            PreparedStatement preparedStatement = setParameters(connection, sql, values);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
