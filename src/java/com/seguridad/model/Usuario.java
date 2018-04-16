package com.seguridad.model;

import java.io.Serializable;

/**
 *
 * @author RODOLFO
 */
public class Usuario implements Serializable {

    private int idUsuario;
    private String nombres;
    private String apellidos;
    private String cuenta;
    private String password;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombres, String apellidos, String cuenta, String password) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cuenta = cuenta;
        this.password = password;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombres=" + nombres + ", apellidos=" + apellidos + ", cuenta=" + cuenta + ", password=" + password + '}';
    }

}
