/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.configuration;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.utilidades.FechasServices;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
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
           
         
            Utilidades.replaceWithoutLine(ruta, "<welcome-file>faces/index.xhtml</welcome-file>","<welcome-file>faces/login.xhtml</welcome-file>");
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<welcome-file>faces/login.xhtml</welcome-file>",login(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>otf</extension>",otf(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>eot</extension>",eot(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>otf</extension>",otf(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>ttf</extension>",ttf(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>woff</extension>",woff(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>svg</extension>",svg(), "</web-app>", true);
            Utilidades.addNotFoundMethodWithOutLine(ruta, "<extension>woff2</extension>",woff2(), "</web-app>", true);
          

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
   private String login() {
        try {

             
        
    
            String texto = "";
            texto += "  <welcome-file-list>" + "\r\n";
            texto += "     <welcome-file>faces/login.xhtml</welcome-file>" + "\r\n";
            texto += "  </welcome-file-list>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("eot()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String eot() {
        try {

            String texto = "";
             texto += "  <mime-mapping>" + "\r\n";
            texto += "     <extension>eot</extension>" + "\r\n";
            texto += "     <mime-type>application/vnd.ms-fontobject</mime-type>" + "\r\n";
            texto += "  </mime-mapping>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("eot()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String otf() {
        try {

            String texto = "";
             texto += "  <mime-mapping>" + "\r\n";
            texto += "     <extension>otf</extension>" + "\r\n";
            texto += "     <mime-type>font/opentype</mime-type>" + "\r\n";
            texto += "  </mime-mapping>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("otf()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String ttf() {
        try {

            String texto = "";
             texto += "  <mime-mapping>" + "\r\n";
            texto += "     <extension>ttf</extension>" + "\r\n";
            texto += "     <mime-type>application/x-font-ttf</mime-type>" + "\r\n";
            texto += "  </mime-mapping>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("ttf()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String woff() {
        try {

            String texto = "";
             texto += "  <mime-mapping>" + "\r\n";
            texto += "     <extension>woff</extension>" + "\r\n";
            texto += "     <mime-type>application/x-font-woff</mime-type>" + "\r\n";
            texto += "  </mime-mapping>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("woff()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String svg() {
        try {

            String texto = "";
             texto += "  <mime-mapping>" + "\r\n";
            texto += "     <extension>svg</extension>" + "\r\n";
            texto += "     <mime-type>image/svg+xml</mime-type>" + "\r\n";
            texto += "  </mime-mapping>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("svg()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String woff2() {
        try {

            String texto = "";
             texto += "  <mime-mapping>" + "\r\n";
            texto += "     <extension>woff2</extension>" + "\r\n";
            texto += "     <mime-type>application/x-font-woff2</mime-type>" + "\r\n";
            texto += "  </mime-mapping>" + "\r\n";
            
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("woff2()  " + e.getLocalizedMessage());
        }
        return "";
    }


    
//    private String bootsFaces_USETHEME() {
//        try {
//
//            String texto = "";
//             texto += "  <context-param>" + "\r\n";
//            texto += "     <param-name>BootsFaces_USETHEME</param-name>" + "\r\n";
//            texto += "     <param-value>default</param-value>" + "\r\n";
//            texto += "  </context-param>" + "\r\n";
//            
//            return texto;
//        } catch (Exception e) {
//            JSFUtil.addErrorMessage("bootsFaces_USETHEME()  " + e.getLocalizedMessage());
//        }
//        return "";
//    }
//    private String bootsFaces_THEME() {
//        try {
//
//            String texto = "";
//             texto += "  <context-param>" + "\r\n";
//            texto += "     <param-name>BootsFaces_THEME</param-name>" + "\r\n";
//            texto += "     <param-value>cerulean</param-value>" + "\r\n";
//            texto += "  </context-param>" + "\r\n";
//            
//            return texto;
//        } catch (Exception e) {
//            JSFUtil.addErrorMessage("bootsFaces_USETHEME()  " + e.getLocalizedMessage());
//        }
//        return "";
//    }

}
