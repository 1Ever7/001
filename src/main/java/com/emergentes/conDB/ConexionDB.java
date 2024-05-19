package com.emergentes.conDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://localhost:5433/hospital";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
         try{
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, PASSWORD);
         }catch(SQLException e){
         System.err.println( e.getMessage() );
      }catch(ClassNotFoundException e){
         System.err.println( e.getMessage() );
      }
        return null;
    }
     public void desconectar(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
