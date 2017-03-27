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
public class AcercadexhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(AcercadexhtmlGenerador.class.getName());

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

            procesar("acercade.xhtml", rutas.getPathMainWebappPages() + rutas.getSeparator()+ "acercade"+ rutas.getSeparator()+"acercade.xhtml");

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
                    fw.write("      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <body>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <ui:composition template=\"./../../template.xhtml\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            <ui:define name=\"content\">" + "\r\n");
                    fw.write("                <f:view> " + "\r\n");
                    fw.write("                    <h:form id=\"form\" prependId=\"false\" rendered=\"#{loginBean.logeado}\">" + "\r\n");
                    fw.write("                        <p:growl  id=\"growl\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <b:panel id=\"panel\" look=\"primary\" title=\"#{form['acercade.records']}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            <h:panelGrid columns=\"1\" cellpadding=\"5\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.title']}: #{app['application.title']}\"  />" + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.shorttitle']}: #{app['application.shorttitle']}\"  />" + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.version']}: #{app['application.version']}\"  />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.desarrolladopor']}: #{app['empresa.empresa']}\"  /> " + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.pais']}: #{app['empresa.pais']}\"  /> " + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.provincia']}: #{app['empresa.provincia']}\"  /> " + "\r\n");
                    fw.write("                                <p:outputLabel value=\"#{app['label.email']}: #{app['empresa.email']}\"  /> " + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            </h:panelGrid>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        </b:panel>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                    </h:form>" + "\r\n");
                    fw.write("                    <h:form rendered=\"#{!loginBean.logeado}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <b:panel look=\"danger\" title=\"#{app['title.accesodenegado']}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-success\" action=\"#{loginBean.irLogin}\"" + "\r\n");
                    fw.write("                                                 value=\"#{app['boton.return']}\" ajax=\"false\"/>" + "\r\n");
                    fw.write("                            </h:panelGrid>" + "\r\n");
                    fw.write("                        </b:panel>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                    </h:form>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                </f:view>" + "\r\n");
                    fw.write("            </ui:define>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        </ui:composition>" + "\r\n");
                    fw.write("" + "\r\n");
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

}
