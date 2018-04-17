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
        System.out.println("Usuario: " + usuario.toString());
        try {
            usuario = usuarioDao.iniciarSesion(usuario);
        } catch (Exception ex) {
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (usuario.getIdUsuario() != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("usuario", usuario);
            return "/main.xhtml?faces-redirect=true";
        } else {
            menssagesControl = new MenssagesControl();
            menssagesControl.mensajeError("Usuario o Contraseña Incorrectas");
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.INFO, "Inicio sesion invalido", "Inicio sesion invalido");
            return "/login.xhtml?faces-redirect=true";
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
