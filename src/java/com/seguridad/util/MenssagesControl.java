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
    private static Properties properties;

    public MenssagesControl() {
        cargarArchivo();
    }

    public static void mensajeExito(String mensaje) {
        cargarArchivo();
        mensaje = mensaje == null ? properties.getProperty("exito") : mensaje;
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_INFO, TipoMensaje.Información.toString(), mensaje));
    }

    public static void mensajeError(String mensaje) {
        cargarArchivo();
        mensaje = mensaje == null ? properties.getProperty("error") : mensaje;
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_FATAL, TipoMensaje.Error.toString(), mensaje));
    }

    public static void mensajeAdvertencia(String mensaje) {
        cargarArchivo();
        mensaje = mensaje == null ? properties.getProperty("advertencia") : mensaje;
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, TipoMensaje.Advertencia.toString(), mensaje));
    }

    public static void mensajeRequerido(String mensaje) {
        cargarArchivo();
        mensaje = properties.getProperty("requerido").concat(". '").concat(mensaje).concat("'");
        FacesUtil.getFacesContext().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_WARN, TipoMensaje.Advertencia.toString(), mensaje));
    }

    private static void cargarArchivo() {
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
