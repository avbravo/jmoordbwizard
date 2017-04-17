/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.old;

import com.avbravo.wizardjmoordb.JSFUtil;
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
public class LeftmenuxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LeftmenuxhtmlGenerador.class.getName());

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

            procesar("leftmenu.xhtml", proyectoJEE.getPathMainWebapp() + proyectoJEE.getSeparator() + "leftmenu.xhtml");

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

//            Utilidades.searchAdd(ruta, "<b:dropMenu rendered=\"#{loginBean.logeado}\" value=\"#{men['menu.opciones']}\" >", "<ui:composition>", false);

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
                    fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("      xmlns:ui=\"http://java.sun.com/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://xmlns.jcp.org/jsf/html\">" + "\r\n");
                    fw.write("    <ui:composition>" + "\r\n");
                    
                    fw.write("        <b:column col-sm=\"3\" col-md=\"2\" styleClass=\"sidebar\">" + "\r\n");
                    fw.write("            <b:listLinks>" + "\r\n");
                    fw.write("                <b:navLink header=\"#{app['application.shorttitle']}\" />" + "\r\n");
                    fw.write("                <b:navLink href=\"#\" value=\"Overview\" active=\"true\"/>" + "\r\n");
                    fw.write("            </b:listLinks>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            <h:form>" + "\r\n");
                    fw.write("                <p:commandLink action=\"#{idiomas.englishAction}\" value=\"#{app['language.english']}\"" + "\r\n");
                    fw.write("                               immediate = \"true\" ajax = \"false\"/>" + "\r\n");
                    fw.write("                <p:outputLabel value=\"|\"/>" + "\r\n");
                    fw.write("                <p:commandLink  action=\"#{idiomas.spanishAction}\" value=\"#{app['language.spanish']}\"" + "\r\n");
                    fw.write("                                immediate = \"true\" ajax = \"false\"  />" + "\r\n");
                    fw.write("            </h:form> " + "\r\n");
                    fw.write("" + "\r\n");
                    
                    fw.write("        </b:column>" + "\r\n");
                    fw.write("    </ui:composition>" + "\r\n");
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
