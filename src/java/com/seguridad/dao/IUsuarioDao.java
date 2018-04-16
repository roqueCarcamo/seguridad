package com.seguridad.dao;

import com.seguridad.model.Usuario;
import java.util.List;

/**
 *
 * @author Pedro
 */
public interface IUsuarioDao {

    void insert(Usuario usuario) throws Exception;

    List<Usuario> listAll() throws Exception;

}
