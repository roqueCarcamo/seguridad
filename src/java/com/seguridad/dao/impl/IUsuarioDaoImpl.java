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
import java.util.logging.Level;
import java.util.logging.Logger;
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
        // the mysql insert statement
        try (Connection conn = BDconexion.conexion()) {
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
        }
    }

    @Override
    public Usuario iniciarSesion(Usuario usuario) throws Exception {
        Usuario user;
        try (Connection conn = BDconexion.conexion()) {
            user = new Usuario();
            // the mysql insert statement
            String query = " select * from TUSUARIO where cuenta = ? and password = ? ";
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, usuario.getCuenta());
            preparedStmt.setString(2, md5hash.sha1(usuario.getPassword()));
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    user.setIdUsuario(resultSet.getInt("id_tusuario"));
                    user.setNombres(resultSet.getString("nombres"));
                    user.setApellidos(resultSet.getString("apellidos"));
                    user.setCuenta(resultSet.getString("cuenta"));
                }
                Logger.getLogger(IUsuarioDaoImpl.class.getName()).log(Level.INFO, "Inicio correcto", "Inicio correcto");
            } else {
                Logger.getLogger(IUsuarioDaoImpl.class.getName()).log(Level.SEVERE, "Inicio invalido", "Inicio invalido");
            }
        }
        return user;
    }

    @Transactional(rollbackForClassName = "java.lang.Exception")
    @Override
    public List<Usuario> listAll() throws Exception {
        List<Usuario> listaUsuarios;
        try (Connection conn = BDconexion.conexion()) {
            Usuario user = new Usuario();
            // the mysql insert statement
            String query = " select * from TUSUARIO ";
            listaUsuarios = new ArrayList<>();
            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    user = new Usuario();
                    user.setIdUsuario(resultSet.getInt("id_tusuario"));
                    user.setNombres(resultSet.getString("nombres"));
                    user.setApellidos(resultSet.getString("apellidos"));
                    user.setCuenta(resultSet.getString("cuenta"));
                    user.setPassword(resultSet.getString("password"));
                    listaUsuarios.add(user);
                }
                Logger.getLogger(IUsuarioDaoImpl.class.getName()).log(Level.INFO, "Usuarios cargados", "Usuarios cargados");
            } else {
                Logger.getLogger(IUsuarioDaoImpl.class.getName()).log(Level.INFO, "No hay usuarios", "No hay usuarios");
            }
        }
        return listaUsuarios;
    }

}
