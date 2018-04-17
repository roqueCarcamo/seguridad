package com.seguridad.controller;

import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.util.MenssagesControl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class ConsultarUsuariosBean implements Serializable {

    @EJB
    private IUsuarioDao usuarioDao;
    private MenssagesControl menssagesControl;
    private List<Usuario> listaUsuarios;

    public ConsultarUsuariosBean() {
    }

    @PostConstruct
    private void init() {
        try {
            listaUsuarios = new ArrayList<>();
            consultarUsuarios();
        } catch (Exception ex) {
            menssagesControl = new MenssagesControl();
            menssagesControl.mensajeError("Error en el Sistema, Contacte al administrador del sistema.");
            Logger.getLogger(ConsultarUsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarUsuarios() {
        try {
            listaUsuarios = usuarioDao.listAll();
        } catch (Exception ex) {
            Logger.getLogger(ConsultarUsuariosBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

}
