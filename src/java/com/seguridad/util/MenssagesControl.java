package com.seguridad.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.servlet.ServletContext;

/**
 *
 * @author Rodolfo
 */
public class MenssagesControl implements Serializable {

    private String mensaje;
    private Properties properties;

    public MenssagesControl() {
        cargarArchivo();
    }

    public void mensajeExito(String mensaje) {
        mensaje = mensaje == null ? properties.getProperty("exito") : mensaje;
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, TipoMensaje.Información.toString(), mensaje));
    }

    public void mensajeError(String mensaje) {
        mensaje = mensaje == null ? properties.getProperty("error") : mensaje;
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, TipoMensaje.Error.toString(), mensaje));
    }

    public void mensajeAdvertencia(String mensaje) {
        mensaje = mensaje == null ? properties.getProperty("advertencia") : mensaje;
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, TipoMensaje.Advertencia.toString(), mensaje));
    }

    public void mensajeRequerido(String mensaje) {
        mensaje = properties.getProperty("requerido").concat(". '").concat(mensaje).concat("'");
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, TipoMensaje.Advertencia.toString(), mensaje));
    }

    private void cargarArchivo() {
        try {
            if (properties == null) {
                properties = new Properties();
                ServletContext servletContext = FacesUtil.getServletContext();
                FileInputStream archivo = new FileInputStream(servletContext.getRealPath("/WEB-INF/mensajes.properties"));
                properties.load(archivo);
            }
        } catch (IOException ex) {           
        }
    }

    public enum TipoMensaje {

        Información, Advertencia, Error;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
