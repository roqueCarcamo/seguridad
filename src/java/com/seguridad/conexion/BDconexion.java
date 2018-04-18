package com.seguridad.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Rodolfo
 */
public class BDconexion {

    public static void main(String[] args) {
        conexion();
    }

    public static Connection conexion() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://devel0p.co:3306/yogakrib_dbseguridad", "yogakrib_rodolfo", "U)ea9edMFRHS");
            Logger.getLogger(BDconexion.class.getName()).log(Level.INFO, "Conexion exitosa", "Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            Logger.getLogger(BDconexion.class.getName()).log(Level.SEVERE, "Error de conexion", e);
        }
        return conn;
    }
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("JDDISEGURIDAD");
            con = ds.getConnection();
            System.out.println("Conexion exitosa");
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(BDconexion.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Conexion con error");
        }
        return con;
    }
}
