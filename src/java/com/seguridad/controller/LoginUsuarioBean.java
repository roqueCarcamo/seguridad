package com.seguridad.controller;

import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.util.MenssagesControl;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Rodolfo
 */
@ManagedBean
@ViewScoped
public class LoginUsuarioBean implements Serializable {

    private Usuario usuario;

    @EJB
    private IUsuarioDao usuarioDao;
    private MenssagesControl menssagesControl;

    public LoginUsuarioBean() {
    }

    @PostConstruct
    private void init() {
        try {
            usuario = new Usuario();
        } catch (Exception ex) {
            menssagesControl = new MenssagesControl();
            menssagesControl.mensajeError("Error en el Sistema, Contacte al Administrador del Sistema.");
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void onIniciar(){
        System.out.println("Usuario: " + usuario.toString());
        try {
            usuarioDao.iniciarSesion(usuario);
            //usuario = new Usuario();
        } catch (Exception ex) {
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
