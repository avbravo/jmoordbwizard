/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.xml;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.utilidades.FechasServices;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class WebXMLGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(WebXMLGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    FechasServices fechasServices;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
            
            procesar("web.xml", proyectoJEE.getPathWebInf() + "web.xml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {
        
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                JSFUtil.addWarningMessage("No existe el archivo web.xml");
                return false;
            }
            //application
           
         
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<param-name>BootsFaces_USETHEME</param-name>", bootsFaces_USETHEME(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<param-name>BootsFaces_THEME</param-name>", bootsFaces_USETHEME(), "</web-app>", true);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

   

    /**
     * deleteAll
     *
     * @param entidad
     * @param archivo
     * @return
     */
  

    
    private String bootsFaces_USETHEME() {
        try {

            String texto = "";
             texto += "  <context-param>" + "\r\n";
            texto += "     <param-name>BootsFaces_USETHEME</param-name>" + "\r\n";
            texto += "     <param-value>default</param-value>" + "\r\n";
            texto += "  </context-param>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("bootsFaces_USETHEME()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String bootsFaces_THEME() {
        try {

            String texto = "";
             texto += "  <context-param>" + "\r\n";
            texto += "     <param-name>BootsFaces_THEME</param-name>" + "\r\n";
            texto += "     <param-value>cerulean</param-value>" + "\r\n";
            texto += "  </context-param>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("bootsFaces_USETHEME()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
