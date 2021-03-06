package com.seguridad.controller;

import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.util.MenssagesControl;
import java.io.Serializable;
import java.security.KeyPairGenerator;
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

    public RegistrarUsuarioBean() {
    }

    @PostConstruct
    private void init() {
        try {
            usuario = new Usuario();
        } catch (Exception ex) {
            MenssagesControl.mensajeError("Error en el Sistema, Contacte al Administrador del Sistema.");
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
                boolean validoTamano = true;
                StringBuilder validaTamano = new StringBuilder();
                validacion.append("Los siguientes dato(s) exceden el tamaño máximo permitido: ");
                if (usuario.getNombres().length() > 200) {
                    validoTamano = false;
                    validaTamano.append(" * ").append("Nombres ");
                }
                if (usuario.getApellidos().length() > 200) {
                    validoTamano = false;
                    validaTamano.append(" * ").append("Apellidos");
                }
                if (usuario.getCuenta().length() > 100) {
                    validoTamano = false;
                    validaTamano.append(" * ").append("Cuenta");
                }

                if (usuario.getPassword().length() > 100) {
                    validoTamano = false;
                    validaTamano.append(" * ").append("Password");
                }

                if (!validoTamano) {
                    MenssagesControl.mensajeAdvertencia(validaTamano.toString());
                    return;
                }

                if (usuarioDao.verificarCuenta(usuario.getCuenta())) {
                    MenssagesControl.mensajeAdvertencia("Ya existe una cuenta registrada");
                    return;
                }

                boolean validarContrasena = usuario.getPassword().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");

                if (!validarContrasena) {
                    MenssagesControl.mensajeAdvertencia("El password debe tener como minimo 8 caracteres: una letra mayuscula, una letra minucula, un número y un cáracter");
                    return;
                }

                KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
                ParClaves generadorClaves = new ParClaves(kg);

                usuario.setKeyprivate(generadorClaves.clavePrivada());
                usuario.setKeypublic(generadorClaves.clavePublica());

                usuarioDao.insert(usuario);
                usuario = new Usuario();
                MenssagesControl.mensajeExito("Usuario registrado con éxito");
            } else {
                MenssagesControl.mensajeAdvertencia(validacion.toString());
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
