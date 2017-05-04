/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.utilidades;

import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.servlet.ServletContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author avbravoserver
 */
@ManagedBean
@RequestScoped
public class JSFUtil {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(JSFUtil.class.getName());

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if (msg != null && msg.length() > 0) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);

        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        LOG.warning(msg);
    }

    public static void testMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        LOG.warning(msg);
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg,
                msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    public static void addWarningMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, ""));
        LOG.warning(msg);
    }

    public static void addFatalMessage(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, ""));
        LOG.warning(msg);
    }

    public static String getRequestParameter(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);
    }

    public static Object getObjectFromRequestParameter(String requestParameterName,
            Converter converter, UIComponent component) {
        String theId = JSFUtil.getRequestParameter(requestParameterName);
        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);
    }

    public static void infoDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public static void warningDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }

    public static void fatalDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo,
                texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }

    public static void errorDialog(String titulo, String texto) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                titulo, texto);
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        LOG.warning(titulo + " " + texto);
    }

    public static java.sql.Date converterDate(java.util.Date fecha) {
        try {
            long lfecha = fecha.getTime();
            java.sql.Date dtfecha = new java.sql.Date(lfecha);
            return dtfecha;
        } catch (Exception e) {
            addErrorMessage("converterDate() " + e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Ejecuta comandos del sistema operativo
     *
     * @param comando
     * @return
     */
    public static String getPath() {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                    .getExternalContext().getContext();
            return ctx.getRealPath("/");

        } catch (Exception e) {

            addErrorMessage("getPath() " + e.getLocalizedMessage());
        }
        return null;

    }

    public static String getPathFotos() {
        try {

            String path = getPath() + "resources/fotos/";
            return path;
        } catch (Exception e) {

            addErrorMessage("getPathFotosPlagas() " + e.getLocalizedMessage());
        }
        return null;

    }

    /*
     copia un archivo generalmente cuando se usa el fileupload
     fileName: nombre del archivo a copiar
     in: Es el InputStream
     destination: ruta donde se guardara el archivo
  
     */
    public static Boolean copyFile(String fileName, InputStream in, String destination) {
        try {

            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            return true;
        } catch (IOException e) {
            JSFUtil.addErrorMessage("copyFile() " + e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * genera id
     *
     * @return returna un randomUUID automatico
     */
    public static String getUUID() {

        UUID uuid = UUID.randomUUID();
        return uuid.toString().toLowerCase();

    }

    /**
     * getExtension()
     *
     * @param texto
     * @return la extension de un nombre de archivo
     */
    public static String getExtension(String texto) {
        try {
            return texto.substring(texto.indexOf("."), texto.length());
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getExtension() " + e.getLocalizedMessage());
        }
        return "";
    }

    public String tmpDirectories() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * verifica si es un tipo de archivo Java
     *
     * @param tipo
     * @return
     */
    public static Boolean isTypeJava(String tipo) {
        try {
            switch (tipo) {
                case "Integer":
                case "Double":
                case "double":
                case "String":

                case "Date":
                    return true;
                default:
                    return false;
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("isTypeJava() " + e.getLocalizedMessage());
        }
        return false;
    }

    /**
     *
     */
    public static String fieldRelational(String tipo,String namefield, List<Entidad> list) {
        try {
//            String nameRelational = Utilidades.letterToLower(namefield);
            String nameRelational = Utilidades.letterToLower(tipo);
            String columnKeyRelational = "";
            for (Entidad entidad2 : list) {
                String name2 = Utilidades.letterToLower(entidad2.getTabla());
                if (nameRelational.equals(name2)) {
                    for (Atributos a : entidad2.getAtributosList()) {
                        if (a.getEsPrimaryKey()) {
                            columnKeyRelational = a.getNombre();
                        }
                    }
                }
            }
            return columnKeyRelational;
        } catch (Exception e) {
             JSFUtil.addErrorMessage("fieldRelational() " + e.getLocalizedMessage());
        }
        return "";
    }
    
    /**
     * genera un nombre con formato
     * año_mes_dia
     * por ejemplo: 2017_05_12
     * @return 
     */
    public static String generarNombreFecha(){
        String name="";
        try{
            LocalDate currentDate = LocalDate.now();
            name=currentDate.getYear()+"_"+currentDate.getMonth()+"_"+currentDate.getDayOfMonth();            
          } catch (Exception e) {
             JSFUtil.addErrorMessage("generarNombreFecha() " + e.getLocalizedMessage());
        }
        return name;
    }
     /**
     * genera un nombre con formato
     * año_mes_dia_hora_minutos_segundos
     * por ejemplo: 2017_05_12_10_25_12
     * @re
     */
    public static String generarNombreFechaMinutos(){
        String name="";
        try{
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();
            name=currentDate.getYear()+"_"+currentDate.getMonth()+"_"+
                    currentDate.getDayOfMonth()
                    +"_"+currentTime.getHour()+"_"+currentTime.getMinute()+"_"+currentTime.getSecond();            
          } catch (Exception e) {
             JSFUtil.addErrorMessage("generarNombreFechaMinutos() " + e.getLocalizedMessage());
        }
        return name;
    }
}
