package com.seguridad.controller;

import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.security.md5hash;
import com.seguridad.util.MenssagesControl;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Rodolfo
 */
@ManagedBean
@SessionScoped
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

    public String onIniciar() {
        try {
            boolean valido = true;
            StringBuilder validacion = new StringBuilder();
            validacion.append("Los dato(s) con * son requerido(s): ");
            if (usuario.getCuenta().isEmpty()) {
                validacion.append(" ").append("Cuenta");
                valido = false;
            }
            if (usuario.getPassword().isEmpty()) {
                validacion.append(" ").append("Password");
                valido = false;
            }
            if (valido) {
                usuario.setPassword(md5hash.sha1(usuario.getPassword()));
                usuario = usuarioDao.iniciarSesion(usuario);
            } else {
                menssagesControl = new MenssagesControl();
                menssagesControl.mensajeAdvertencia(validacion.toString());
                return "";
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuario.getIdUsuario() != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("usuario", usuario);
            return "/view/main.xhtml?faces-redirect=true";
        } else {
            menssagesControl = new MenssagesControl();
            menssagesControl.mensajeError("Usuario o Contraseña Incorrectas");
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.INFO, "Inicio sesion invalido", "Inicio sesion invalido");
            return "";
        }
    }

    public String logout() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if (session != null) {
            usuario = new Usuario();
            session.invalidate();
        }
        return "/login?faces-redirect=true";
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
