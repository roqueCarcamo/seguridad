/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controller;

import com.seguridad.dao.IUsuarioDao;
import com.seguridad.model.Usuario;
import com.seguridad.util.MenssagesControl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import javax.ejb.EJB;
import org.apache.poi.util.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author josueoviedo
 */
@ManagedBean
@ViewScoped
public class ArchivoFirmaDigitalBean implements Serializable {

    private UploadedFile fileDocument;
    
    private Usuario usuario;
    private StreamedContent keypub, keypriv;
    
    private DefaultStreamedContent documentEncryp;
    
    private String decodeFirma = "";

    @EJB
    private IUsuarioDao usuarioDao;
    private MenssagesControl menssagesControl;
    
    @PostConstruct
    private void init() {
        try {
            
            usuario = new Usuario();
            privateKeyForView();
            publicKeyForView();
            
        } catch (Exception ex) {
            menssagesControl = new MenssagesControl();
            menssagesControl.mensajeError("Error en el Sistema, Contacte al Administrador del Sistema.");
            Logger.getLogger(LoginUsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public StreamedContent getKeypub() {
        return keypub;
    }

    public void setKeypub(StreamedContent keypub) {
        this.keypub = keypub;
    }

    public StreamedContent getKeypriv() {
        return keypriv;
    }

    public void setKeypriv(StreamedContent keypriv) {
        this.keypriv = keypriv;
    }

    public UploadedFile getFileDocument() {
        return fileDocument;
    }

    public void setFileDocument(UploadedFile fileDocument) {
        this.fileDocument = fileDocument;
    }

    public StreamedContent getDocumentEncryp() {
        return documentEncryp;
    }

    public String getDecodeFirma() {
        return decodeFirma;
    }

    public void setDecodeFirma(String decodeFirma) {
        this.decodeFirma = decodeFirma;
    }

    
    
    public void uploadEncryp() {
        try {
            System.out.println("Arhivo D: " + fileDocument.getFileName());
            System.out.println("Bytes D: " + fileDocument.getInputstream().toString());
        } catch (IOException ex) {
            Logger.getLogger(ArchivoAESBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ArchivoAESBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fileDocument != null) {
            FacesMessage message = new FacesMessage("Succesful", fileDocument.getFileName() +  " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            
            try {
                
                byte[] document = IOUtils.toByteArray(fileDocument.getInputstream());
                KeyFactory kf = KeyFactory.getInstance("RSA");
                PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(usuario.getKeyprivate()));
                
                FirmarDoc firmarDoc = new FirmarDoc(privateKey, document);
                
                
                documentEncryp = new DefaultStreamedContent(new ByteArrayInputStream(firmarDoc.obtenerFirma()), "", "Firma");
                
                
                
            } catch (Exception e) {
                e.printStackTrace();
                FacesMessage messageE = new FacesMessage("Error", e.getMessage());
                FacesContext.getCurrentInstance().addMessage(null, messageE);
            }
            
        }else{
            FacesMessage message = new FacesMessage("Error", fileDocument.getFileName() + " is NOT uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public void privateKeyForView() throws Exception{
        Usuario user = usuarioDao.getKeyPrivate(usuario);
        usuario.setKeyprivate(user.getKeyprivate());
        System.out.println(user.getKeyprivate());
        //ByteArrayInputStream in = new ByteArrayInputStream(usuario.getKeyprivate());
        //ObjectInputStream is = new ObjectInputStream(in);
        //KeyFactory kf = KeyFactory.getInstance("RSA");
        //PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(usuario.getKeyprivate()));
        
        keypriv = new DefaultStreamedContent(new ByteArrayInputStream(user.getKeyprivate()), "", "PrivateKey");
    }
    
    public void publicKeyForView() throws Exception{
        Usuario user = usuarioDao.getKeyPublic(usuario);
        usuario.setKeypublic(user.getKeypublic());
        System.out.println(user.getKeypublic());
        //ByteArrayInputStream in = new ByteArrayInputStream(usuario.getKeyprivate());
        //ObjectInputStream is = new ObjectInputStream(in);
        //KeyFactory kf = KeyFactory.getInstance("RSA");
        //PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(usuario.getKeypublic()));
        
        keypub = new DefaultStreamedContent(new ByteArrayInputStream(user.getKeypublic()), "", "PublicKey");
    }
   
}


