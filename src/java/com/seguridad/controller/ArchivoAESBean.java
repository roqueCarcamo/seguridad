package com.seguridad.controller;

import com.seguridad.security.AESECB;
import com.seguridad.security.md5hash;
import com.seguridad.util.MenssagesControl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.stereotype.Controller;

/**
 *
 * @author RODOLFO
 */
@Controller
@ManagedBean
@ViewScoped
public class ArchivoAESBean implements Serializable {

    private UploadedFile file;
    private UploadedFile fileEncriptado;
    private DefaultStreamedContent fileArchivo;
    private DefaultStreamedContent fileArchivoDescriptado;
    private String key;
    private String keyDesencriptar;
    private boolean validarDesencriptacion;

    @PostConstruct
    private void init() {
        try {
            validarDesencriptacion = true;
        } catch (Exception ex) {
            MenssagesControl.mensajeError("Error en el Sistema, Contacte al Administrador del Sistema.");
            Logger.getLogger(RegistrarUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArchivoAESBean() {

    }

    public void upload() {
        byte[] testAESECB128 = "".getBytes();
        try {
            byte[] content = file.getContents();
            String sha1 = md5hash.sha1(key);
            String keyGenerada = sha1.substring(0, 16);
            testAESECB128 = AESECB.encriptar(keyGenerada, content);
            //System.out.println("Arhivo: " + file.getFileName());
            //System.err.println("Bytes: " + file.getInputstream().toString());
        } catch (IOException ex) {
            Logger.getLogger(ArchivoAESBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArchivoAESBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

        InputStream is = new ByteArrayInputStream(testAESECB128);
        fileArchivo = new DefaultStreamedContent(is, "application/octet-stream", file.getFileName());
    }

    public void uploadVerificar() {
        byte[] archivoDescriptado = "".getBytes();
        byte[] content = "".getBytes();
        try {
            content = fileEncriptado.getContents();
            String sha1 = md5hash.sha1(keyDesencriptar);
            String keyGenerada = sha1.substring(0, 16);
            archivoDescriptado = AESECB.descriptar(keyGenerada, content);
            //System.out.println("Archivo: " + fileEncriptado.getFileName());
            //System.out.println("Bytes: " + fileEncriptado.getInputstream().toString());
        } catch (IOException ex) {
            archivoDescriptado = null;
            Logger.getLogger(ArchivoAESBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            archivoDescriptado = null;
            Logger.getLogger(ArchivoAESBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        InputStream is = null ;
        if (archivoDescriptado == null) {
            validarDesencriptacion = true;
            MenssagesControl.mensajeAdvertencia("Archivo no desencriptado, llave invalida");
            is = new ByteArrayInputStream(content);
        }
        if (archivoDescriptado != null) {
            validarDesencriptacion = false;
            is = new ByteArrayInputStream(archivoDescriptado);
        }
        fileArchivoDescriptado = new DefaultStreamedContent(is, "application/octet-stream", fileEncriptado.getFileName());
    }

    public void descargarArchivoDesencriptado() {

    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DefaultStreamedContent getFileArchivo() {
        return fileArchivo;
    }

    public void setFileArchivo(DefaultStreamedContent fileArchivo) {
        this.fileArchivo = fileArchivo;
    }

    public UploadedFile getFileEncriptado() {
        return fileEncriptado;
    }

    public void setFileEncriptado(UploadedFile fileEncriptado) {
        this.fileEncriptado = fileEncriptado;
    }

    public DefaultStreamedContent getFileArchivoDescriptado() {
        return fileArchivoDescriptado;
    }

    public void setFileArchivoDescriptado(DefaultStreamedContent fileArchivoDescriptado) {
        this.fileArchivoDescriptado = fileArchivoDescriptado;
    }

    public String getKeyDesencriptar() {
        return keyDesencriptar;
    }

    public void setKeyDesencriptar(String keyDesencriptar) {
        this.keyDesencriptar = keyDesencriptar;
    }

    public boolean isValidarDesencriptacion() {
        return validarDesencriptacion;
    }

    public void setValidarDesencriptacion(boolean validarDesencriptacion) {
        this.validarDesencriptacion = validarDesencriptacion;
    }

}
