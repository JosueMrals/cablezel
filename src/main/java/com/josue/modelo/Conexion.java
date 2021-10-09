package com.josue.modelo;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josh
 */
public class Conexion {
    
    private Connection conexion;
    private Statement st;
    
    public Conexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CABLEZEL", "JosueAdmin", "admin");
            st = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (Exception ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public ResultSet CONSULTAR (String sql) throws SQLException {
        System.out.println(sql);
        return st.executeQuery(sql);
        
    }
    
    public int GUARDAR (String sql) throws SQLException {
        return st.executeUpdate(sql);
    }
    
    public void CERRAR () {
        try {
            conexion.close( );
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
