package com.seguridad.dao;

import com.seguridad.model.Usuario;
import java.util.List;

/**
 *
 * @author Rodolfo
 */
public interface IUsuarioDao {

    void insert(Usuario usuario) throws Exception;
    
    Usuario iniciarSesion(Usuario usuario) throws Exception;

    List<Usuario> listAll() throws Exception;

}
