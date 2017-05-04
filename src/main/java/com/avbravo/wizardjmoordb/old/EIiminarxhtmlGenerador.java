/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.old;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
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
public class EIiminarxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(EIiminarxhtmlGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("eliminar.xhtml", proyectoJEE.getPathMainWebappResourcesAvbravo()+ proyectoJEE.getSeparator() + "eliminar.xhtml");

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
                    fw.write("      xmlns:h=\"http://xmlns.jcp.org/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://java.sun.com/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\"" + "\r\n");
                    fw.write("      xmlns:composite=\"http://java.sun.com/jsf/composite\">" + "\r\n");
                    fw.write("    <composite:interface >" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <composite:attribute name=\"contenedor\" />" + "\r\n");
                    fw.write("            <composite:attribute name=\"buttonYesAction\" " + "\r\n");
                    fw.write("                             method-signature=\"java.lang.String action()\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    </composite:interface>" + "\r\n");
                    fw.write("    <composite:implementation>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <p:dialog id=\"confirmDialog\" modal=\"false\" header=\"#{app['dialog.delete']}\"" + "\r\n");
                    fw.write("                  height=\"50\"  width=\"140\" " + "\r\n");
                    fw.write("                  widgetVar=\"confirmationwidgetVar\">" + "\r\n");
                    fw.write("            <h:panelGrid columns=\"2\" id=\"paneldelete\">" + "\r\n");
                    fw.write("                <p:outputLabel value=\"#{app['info.doyouwantdelete']}\"/>" + "\r\n");
                    fw.write("                <p:spacer/>" + "\r\n");
                    fw.write("            </h:panelGrid>" + "\r\n");
                    fw.write("            <f:facet name=\"footer\">" + "\r\n");
                    fw.write("                <p:commandButton id=\"removeButton\" value=\"#{app['boton.yes']}\"" + "\r\n");
                    fw.write("                                 update=\"#{cc.attrs.contenedor},:form:growl,:form:barra:toolbar\"" + "\r\n");
                    fw.write("                                 oncomplete=\"PF('confirmationwidgetVar').hide();\"" + "\r\n");
                    fw.write("                                 icon=\"ui-icon-check\"" + "\r\n");
                    fw.write("                                 actionListener=\"#{cc.attrs.buttonYesAction}\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                <p:commandButton id=\"cancelButton\" value=\"#{app['boton.no']}\"" + "\r\n");
                    fw.write("                                 icon=\"ui-icon-close\"" + "\r\n");
                    fw.write("                                 onclick=\"PF('confirmationwidgetVar').hide();\" type=\"button\" />" + "\r\n");
                    fw.write("            </f:facet>" + "\r\n");
                    fw.write("        </p:dialog>" + "\r\n");
                    fw.write("    </composite:implementation>" + "\r\n");
                    fw.write("" + "\r\n");
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
