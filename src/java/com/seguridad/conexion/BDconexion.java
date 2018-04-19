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

    public static Connection getConnection() {
        Connection con = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("JDDISEGURIDAD");
            con = ds.getConnection();
            Logger.getLogger(BDconexion.class.getName()).log(Level.INFO, "Conexion exitosa", "Conexion exitosa");
        } catch (NamingException | SQLException ex) {
            Logger.getLogger(BDconexion.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(BDconexion.class.getName()).log(Level.SEVERE, "Error de conexion", ex);
        }
        return con;
    }
}
