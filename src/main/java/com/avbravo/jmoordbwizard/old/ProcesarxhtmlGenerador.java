/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.old;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoJEE;
import com.avbravo.jmoordbwizard.utilidades.Utilidades;
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
public class ProcesarxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ProcesarxhtmlGenerador.class.getName());

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

            procesar("procesar.xhtml", proyectoJEE.getPathMainWebappResourcesAvbravo()+ proyectoJEE.getSeparator()+"procesar.xhtml");

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
fw.write("    </composite:interface>" + "\r\n");
fw.write("    <composite:implementation>" + "\r\n");
fw.write("" + "\r\n");
fw.write("        <p:dialog modal=\"true\" widgetVar=\"statusDialog\" header=\"#{app['dialog.process']}\" draggable=\"true\" closable=\"false\">" + "\r\n");
fw.write("            " + "\r\n");
fw.write("         " + "\r\n");
fw.write("                <p:graphicImage value=\"/resources/imagenes/ajax_loader_blue_32.gif\" />" + "\r\n");
fw.write("            " + "\r\n");
fw.write("" + "\r\n");
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
