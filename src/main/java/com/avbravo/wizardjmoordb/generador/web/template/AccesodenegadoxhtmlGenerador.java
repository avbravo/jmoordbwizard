/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.web.template;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
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
import java.util.logging.Level;
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
public class AccesodenegadoxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AccesodenegadoxhtmlGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    Rutas rutas;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("accesodenegado.xhtml", rutas.getPathMainWebapp() + rutas.getSeparator() + "accesodenegado.xhtml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            }

            /**
             * generar los metodos
             */
                       Utilidades.searchAdd(ruta, "<b:panel look=\"danger\" title=\"#{app['title.accesodenegado']}\">",  "<h:form>", false);
                       Utilidades.searchAdd(ruta, "<h:panelGrid columns=\"2\" cellpadding=\"5\">",  "<b:panel", false);
                       
                       Utilidades.searchAdd(ruta, "<p:commandButton class=\"btn btn-success\" action=\"#{loginBean.irLogin}\"  value=\"#{app['boton.return']}\" ajax=\"false\"/>",  "<h:panelGrid columns=\"2\"", false);
                       Utilidades.searchAdd(ruta, "</h:panelGrid>",  "<p:commandButton", false);

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
    private Boolean crearFile(String path, String archivo) throws IOException {
        try {

            String ruta = path;
            File file = new File(ruta);
            BufferedWriter bw;
            if (file.exists()) {
                // El fichero ya existe
            } else {
                // El fichero no existe y hay que crearlo
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.close();
//      bw.write("Acabo de crear el fichero de texto.");

                File file2 = new File(ruta);
                //Creamos un objeto para escribir caracteres en el archivo de prueba
                try (FileWriter fw = new FileWriter(file)) {

                    fw.write("<?xml version='1.0' encoding='UTF-8' ?>" + "\r\n");
                    fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + "\r\n");
                    fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("      xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://xmlns.jcp.org/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"         " + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <body>" + "\r\n");
                    fw.write("        <ui:define name=\"content\">" + "\r\n");
                    fw.write("            <f:view>" + "\r\n");
                    fw.write("                <h:form>" + "\r\n");
                    fw.write("                    <b:panel look=\"danger\" title=\"#{app['title.accesodenegado']}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");
                    fw.write("                            <p:commandButton class=\"btn btn-success\" action=\"#{loginBean.irLogin}\"  value=\"#{app['boton.return']}\" ajax=\"false\"/>" + "\r\n");
                    fw.write("                        </h:panelGrid>" + "\r\n");
                    fw.write("                    </b:panel>" + "\r\n");
                    fw.write("                </h:form>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            </f:view>" + "\r\n");
                    fw.write("        </ui:define>" + "\r\n");
                    fw.write("    </body>" + "\r\n");
                    fw.write("</html>" + "\r\n");
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

    private String leftmenu() {
        try {

            String texto = "";
            texto += "<div class=\"col-sm-3 col-md-2 sidebar\">" + "\r\n";
            texto += "      <ui:include src=\"leftmenu.xhtml\" />" + "\r\n";
            texto += "</div>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("leftmenu() " + e.getLocalizedMessage());
        }
        return "";
    }

    private String content() {
        try {

            String texto = "";
            texto += "                <div class=\"col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main\">   " + "\r\n";
            texto += "                    <ui:insert name=\"content\" />" + "\r\n";
            texto += "                </div>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("content() " + e.getLocalizedMessage());
        }
        return "";
    }

}
