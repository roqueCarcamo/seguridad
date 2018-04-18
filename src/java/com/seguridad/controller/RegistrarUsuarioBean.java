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
public class RegistrarUsuarioBean implements Serializable {

    private Usuario usuario;

    @EJB
    private IUsuarioDao usuarioDao;
    private MenssagesControl menssagesControl;

    public RegistrarUsuarioBean() {
    }

    @PostConstruct
    private void init() {
        try {
            usuario = new Usuario();
        } catch (Exception ex) {
            menssagesControl = new MenssagesControl();
            menssagesControl.mensajeError("Error en el Sistema, Contacte al Administrador del Sistema.");
            Logger.getLogger(RegistrarUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onGuardar() {
        try {
            boolean valido = true;
            StringBuilder validacion = new StringBuilder();
            validacion.append("Los dato(s) con * son requerido(s): ");
            if (usuario.getNombres().isEmpty()) {
                validacion.append(" * ").append("Nombres ");
                valido = false;
            }
            if (usuario.getApellidos().isEmpty()) {
                validacion.append(" * ").append("Apellidos");
                valido = false;
            }
            if (usuario.getCuenta().isEmpty()) {
                validacion.append(" * ").append("Cuenta");
                valido = false;
            }
            if (usuario.getPassword().isEmpty()) {
                validacion.append(" * ").append("Password");
                valido = false;
            }
            if (valido) {
                usuarioDao.insert(usuario);
                usuario = new Usuario();
            } else {
                menssagesControl = new MenssagesControl();
                menssagesControl.mensajeAdvertencia(validacion.toString());
            }
        } catch (Exception ex) {
            usuario = new Usuario();
            Logger.getLogger(RegistrarUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
