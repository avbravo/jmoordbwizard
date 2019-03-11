/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.xhtml;

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
public class RigthxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RigthxhtmlGenerador.class.getName());

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

            procesar("right.xhtml", proyectoJEE.getPathMainWebappPages() + proyectoJEE.getSeparator() + "rigth.xhtml");

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
                    fw.write("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("                xmlns:h=\"http://xmlns.jcp.org/jsf/html\"" + "\r\n");
                    fw.write("                xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"" + "\r\n");
                    fw.write("                xmlns:jsf=\"http://xmlns.jcp.org/jsf\"" + "\r\n");
                    fw.write("                xmlns:p=\"http://primefaces.org/ui\">" + "\r\n");
                    fw.write("                    " + "\r\n");
                    fw.write("       <!-- Control Sidebar -->" + "\r\n");
                    fw.write("       <h:form id=\"rigth\">" + "\r\n");
                    fw.write("                    <aside class=\"control-sidebar control-sidebar-dark\">" + "\r\n");
                    fw.write("                        <!-- Create the tabs -->" + "\r\n");
                    fw.write("                        <ul class=\"nav nav-tabs nav-justified control-sidebar-tabs\">" + "\r\n");
                    fw.write("                            <li class=\"active\"><a href=\"#control-sidebar-home-tab\" data-toggle=\"tab\"><i class=\"fa fa-home\"></i></a></li>" + "\r\n");
                    fw.write("                            <li><a href=\"#control-sidebar-settings-tab\" data-toggle=\"tab\"><i class=\"fa fa-gears\"></i></a></li>" + "\r\n");
                    fw.write("                        </ul>" + "\r\n");
                    fw.write("                        <!-- Tab panes -->" + "\r\n");
                    fw.write("                        <div class=\"tab-content\">" + "\r\n");
                    fw.write("                            <!-- Home tab content -->" + "\r\n");
                    fw.write("                            <div class=\"tab-pane active\" id=\"control-sidebar-home-tab\">" + "\r\n");
                    fw.write("                                <h3 class=\"control-sidebar-heading\">Generado con WizarjMoordb</h3>" + "\r\n");
                    fw.write("                                <ul class=\"control-sidebar-menu\">" + "\r\n");
                    fw.write("                                    <li>" + "\r\n");
                    fw.write("                                        <a href=\"javascript::;\">" + "\r\n");
                    fw.write("                                            <i class=\"menu-icon fa fa-birthday-cake bg-red\"></i>" + "\r\n");
                    fw.write("                                            <div class=\"menu-info\">" + "\r\n");
                    fw.write("                                                <h4 class=\"control-sidebar-subheading\">Anuncio aqui</h4>" + "\r\n");
                    fw.write("                                                <p>KJava APi for NoSQL</p>" + "\r\n");
                    fw.write("                                            </div>" + "\r\n");
                    fw.write("                                        </a>" + "\r\n");
                    fw.write("                                    </li>" + "\r\n");
                    fw.write("                                </ul><!-- /.control-sidebar-menu -->" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <h3 class=\"control-sidebar-heading\">Tasks Progress</h3>" + "\r\n");
                    fw.write("                                <ul class=\"control-sidebar-menu\">" + "\r\n");
                    fw.write("                                    <li>" + "\r\n");
                    fw.write("                                        <a href=\"javascript::;\">" + "\r\n");
                    fw.write("                                            <h4 class=\"control-sidebar-subheading\">" + "\r\n");
                    fw.write("                                                Custom Template Design" + "\r\n");
                    fw.write("                                                <span class=\"label label-danger pull-right\">70%</span>" + "\r\n");
                    fw.write("                                            </h4>" + "\r\n");
                    fw.write("                                            <div class=\"progress progress-xxs\">" + "\r\n");
                    fw.write("                                                <div class=\"progress-bar progress-bar-danger\" style=\"width: 70%\"></div>" + "\r\n");
                    fw.write("                                            </div>" + "\r\n");
                    fw.write("                                        </a>" + "\r\n");
                    fw.write("                                    </li>" + "\r\n");
                    fw.write("                                </ul><!-- /.control-sidebar-menu -->" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            </div><!-- /.tab-pane -->" + "\r\n");
                    fw.write("                            <!-- Stats tab content -->" + "\r\n");
                    fw.write("                            <div class=\"tab-pane\" id=\"control-sidebar-stats-tab\">Stats Tab Content</div><!-- /.tab-pane -->" + "\r\n");
                    fw.write("                            <!-- Settings tab content -->" + "\r\n");
                    fw.write("                            <div class=\"tab-pane\" id=\"control-sidebar-settings-tab\">" + "\r\n");
                    fw.write("                                <form method=\"post\">" + "\r\n");
                    fw.write("                                    <h3 class=\"control-sidebar-heading\">General Settings</h3>" + "\r\n");
                    fw.write("                                    <div class=\"form-group\">" + "\r\n");
                    fw.write("                                        <label class=\"control-sidebar-subheading\">" + "\r\n");
                    fw.write("                                            Report panel usage" + "\r\n");
                    fw.write("                                            <input type=\"checkbox\" class=\"pull-right\" />" + "\r\n");
                    fw.write("                                        </label>" + "\r\n");
                    fw.write("                                        <p>" + "\r\n");
                    fw.write("                                            Some information about this general settings option" + "\r\n");
                    fw.write("                                        </p>" + "\r\n");
                    fw.write("                                    </div><!-- /.form-group -->" + "\r\n");
                    fw.write("                                </form>" + "\r\n");
                    fw.write("                            </div><!-- /.tab-pane -->" + "\r\n");
                    fw.write("                        </div>" + "\r\n");
                    fw.write("                    </aside><!-- /.control-sidebar -->" + "\r\n");
                    fw.write("    </h:form>" + "\r\n");
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
