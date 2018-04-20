package com.seguridad.controller;

import com.seguridad.security.AESECB;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author RODOLFO
 */
@Controller
@Scope("session")
@ManagedBean
public class ArchivoAESBean implements Serializable {

    private UploadedFile file;
    private DefaultStreamedContent fileArchivo;
    private String key;

    public void upload() {
        byte[] testAESECB128  = "".getBytes();
        try {
            byte[] content = file.getContents();
            testAESECB128 = AESECB.testAESECB128(key, content);
            System.out.println("Arhivo: " + file.getFileName());
            System.err.println("Bytes: " + file.getInputstream().toString());
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
        fileArchivo = new DefaultStreamedContent(is, "", file.getFileName());
    }
    
    public void uploadVerificar() {
        byte[] testAESECB128  = "".getBytes();
        try {
            byte[] content = file.getContents();
            testAESECB128 = AESECB.descriptar(key, content);
            System.out.println("Archivo: " + file.getFileName());
            System.out.println("Bytes: " + file.getInputstream().toString());
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
        fileArchivo = new DefaultStreamedContent(is, "", file.getFileName());
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
    
    

}
