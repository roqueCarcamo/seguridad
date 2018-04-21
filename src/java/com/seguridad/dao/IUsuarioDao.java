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
    
    boolean verificarCuenta(String cuenta) throws Exception;

    List<Usuario> listAll() throws Exception;
    
    public Usuario getKeyPrivate(Usuario usuario) throws Exception;
    
    public Usuario getKeyPublic(Usuario usuario) throws Exception;
}

