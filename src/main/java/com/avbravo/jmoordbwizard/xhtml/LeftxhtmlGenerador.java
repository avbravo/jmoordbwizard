/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.xhtml;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoJEE;
import com.avbravo.jmoordbwizard.beans.EntidadMenu;
import com.avbravo.jmoordbwizard.menu.MyMenu;
import com.avbravo.jmoordbwizard.menu.MySubmenu;
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
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class LeftxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LeftxhtmlGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {

            procesar("left.xhtml", proyectoJEE.getPathMainWebappPages() + proyectoJEE.getSeparator() + "left.xhtml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            } else {
                if (mySesion.getGenerarRemplazarMenu().equals("generar")) {
                    String ruta2 = proyectoJEE.getPathMainWebappPages() + proyectoJEE.getSeparator() + "left_copy_" + JSFUtil.generarNombreFechaMinutos() + ".xhtml";
                    Path path2 = Paths.get(ruta2);
                    //No tiene el formato, se hara una copia y se creara uno nuevo
                    Files.copy(path, path2);
                    Files.delete(path);
                    crearFile(ruta, archivo);
                }
            }

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
            String barraMenu;
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

                    fw.write("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("                xmlns:h=\"http://xmlns.jcp.org/jsf/html\"" + "\r\n");
                    fw.write("                xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"" + "\r\n");
                    fw.write("                xmlns:jsf=\"http://xmlns.jcp.org/jsf\"" + "\r\n");
                    fw.write("                xmlns:p=\"http://primefaces.org/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <div class=\"user-panel\">" + "\r\n");
                    fw.write("        <div class=\"pull-left image\">" + "\r\n");
                    fw.write("            <h:graphicImage library=\"img\" name=\"logo.png\" class=\"img-circle\" alt=\"User Image\"/>" + "\r\n");
                    fw.write("        </div>" + "\r\n");
                    fw.write("        <div class=\"pull-left info\">" + "\r\n");
                    fw.write("            <p>#{loginController.usuario.nombre}</p>" + "\r\n");
                    fw.write("            <!-- Status -->" + "\r\n");
                    fw.write("            <a href=\"#\"><i class=\"fa fa-circle text-success\"></i> </a>" + "\r\n");
                    fw.write("        </div>" + "\r\n");
                    fw.write("    </div>" + "\r\n");
                    fw.write("    <h:form id=\"left\" rendered=\"#{loginController.loggedIn}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <!-- Sidebar Menu -->" + "\r\n");
                    fw.write("        <ul class=\"sidebar-menu\">" + "\r\n");
                    fw.write("            <li class=\"header\">#{msg['menu.menu']}</li>" + "\r\n");
                    fw.write("" + "\r\n");

                    for (MyMenu m : mySesion.getMymenuList()) {
                        fw.write("            <h:panelGroup rendered=\"#{applicationMenu.menuBar" + Utilidades.letterToUpper(m.getName()) + "}\">" + "\r\n");
                        fw.write("                <li class=\"active treeview\" >" + "\r\n");
                        fw.write("                    <a href=\"#\"><i class=\"fa fa-link\"></i> <span>#{msg['menubar." + Utilidades.letterToLower(m.getName()) + "']}</span> <i class=\"fa fa-angle-left pull-right\"></i></a>" + "\r\n");
                        fw.write("                    <ul class=\"treeview-menu\" rendered=\"#{applicationMenu.menuBar" + Utilidades.letterToUpper(m.getName()) + "}\">" + "\r\n");
                        fw.write("" + "\r\n");
                        for (MySubmenu s : m.getSubmenu()) {
                            if (!s.getName().equals("")) {
                                fw.write("                        <li> " + "\r\n");
                                fw.write("                            <p:link value=\"#{msg['menu." + Utilidades.letterToLower(s.getName()) + "']}\" rendered=\"#{applicationMenu." + Utilidades.letterToLower(s.getName()) + ".menu}\" outcome=\"/pages/" + Utilidades.letterToLower(s.getName()) + "/view\"/>" + "\r\n");
                                fw.write("                        </li>" + "\r\n");

                            }
                        }
                        fw.write("   " + "\r\n");
                        fw.write("                    </ul>" + "\r\n");
                        fw.write("                </li>" + "\r\n");
                        fw.write("            </h:panelGroup>" + "\r\n");

                    }

                    fw.write("        </ul><!-- /.sidebar-menu -->" + "\r\n");
                    fw.write("    </h:form>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("</ui:composition>" + "\r\n");
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
