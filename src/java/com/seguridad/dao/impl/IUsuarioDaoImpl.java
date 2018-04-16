package com.seguridad.dao.impl;

import com.seguridad.conexion.BDconexion;
import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.security.md5hash;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rodolfo
 */
@Stateless
public class IUsuarioDaoImpl implements IUsuarioDao {

    @Transactional(rollbackForClassName = "java.lang.Exception")
    @Override
    public void insert(Usuario usuario) throws Exception {
        System.out.println("Usuario Dao: " + usuario.toString());

        Connection conn = BDconexion.conexion();

        // the mysql insert statement
        String query = " insert into TUSUARIO (nombres, apellidos, cuenta, password)"
                + " values (?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, usuario.getNombres());
        preparedStmt.setString(2, usuario.getApellidos());
        preparedStmt.setString(3, usuario.getCuenta());
        preparedStmt.setString(4, md5hash.sha1(usuario.getPassword()));

        preparedStmt.execute();

        conn.close();
    }

    @Override
    public void iniciarSesion(Usuario usuario) throws Exception {
        Connection conn = BDconexion.conexion();
        
         // the mysql insert statement
        String query = " select * from TUSUARIO where cuenta = ? and password = ?";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, usuario.getCuenta());
        preparedStmt.setString(2, md5hash.sha1(usuario.getPassword()));
        ResultSet resultSet = preparedStmt.getResultSet();
        if(resultSet != null){
            System.out.println("Inicio correcto");
        }else{
            System.out.println("Inicio invalido");
        }
    }

    @Transactional(rollbackForClassName = "java.lang.Exception")
    @Override
    public List<Usuario> listAll() throws Exception {

        return new ArrayList<>();
    }

}
