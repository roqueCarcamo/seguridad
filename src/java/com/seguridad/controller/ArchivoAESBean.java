/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controller;

import com.seguridad.security.AESECB;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        try {
            AESECB.testAESECB128();
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
    }
}
