package com.seguridad.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author RODOLFO
 */
@Entity
@Table(name = "TUSUARIO")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tusuario")
    private Integer idUsuario;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "cuenta")
    private String cuenta;
    @Column(name = "password")
    private String password;
    @Column(name = "keyprivate")
    private byte[] keyprivate;
    @Column(name = "keypublic")
    private byte[] keypublic;
    
    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nombres, String apellidos, String cuenta, String password, byte[] keyprivate, byte[] keypublic) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.cuenta = cuenta;
        this.password = password;
        this.keyprivate = keyprivate;
        this.keypublic = keypublic;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
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

    public byte[] getKeyprivate() {
        return keyprivate;
    }

    public void setKeyprivate(byte[] keyprivate) {
        this.keyprivate = keyprivate;
    }

    public byte[] getKeypublic() {
        return keypublic;
    }

    public void setKeypublic(byte[] keypublic) {
        this.keypublic = keypublic;
    }
    
    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nombres=" + nombres + ", apellidos=" + apellidos + ", cuenta=" + cuenta + ", password=" + password + '}';
    }

}
