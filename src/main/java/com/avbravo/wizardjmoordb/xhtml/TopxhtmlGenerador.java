/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.xhtml;

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
public class TopxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TopxhtmlGenerador.class.getName());

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

            procesar("top.xhtml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo) {
        try {
            String ruta = proyectoJEE.getPathMainWebappPages() + proyectoJEE.getSeparator() + archivo;
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
                    fw.write("                     <!-- Logo -->" + "\r\n");
                    fw.write("                     <h:form id=\"top\">" + "\r\n");
                    fw.write("                         <p:growl id=\"growltop\" />" + "\r\n");
                    fw.write("                        <a href=\"/siu/faces/index.xhtml\" class=\"logo\">" + "\r\n");
                    fw.write("                            <!-- mini logo for sidebar mini 50x50 pixels -->" + "\r\n");
                    fw.write("                            <span class=\"logo-mini\"><b>#{msg['application.sigla']}</b></span>" + "\r\n");
                    fw.write("                            <!-- logo for regular state and mobile devices -->" + "\r\n");
                    fw.write("                            <span class=\"logo-lg\"><b>#{msg['application.title']}</b></span>" + "\r\n");
                    fw.write("                        </a>" + "\r\n");
                    fw.write("        <!-- Header Navbar -->" + "\r\n");
                    fw.write("                        <nav class=\"navbar navbar-static-top\" role=\"navigation\">" + "\r\n");
                    fw.write("                            <!-- Sidebar toggle button-->" + "\r\n");
                    fw.write("                            <a href=\"#\" class=\"sidebar-toggle\" data-toggle=\"offcanvas\" role=\"button\">" + "\r\n");
                    fw.write("                                <span class=\"sr-only\">Toggle navigation</span>" + "\r\n");
                    fw.write("                            </a>" + "\r\n");
                    fw.write("                            <!-- Navbar Right Menu -->" + "\r\n");
                    fw.write("                            <div class=\"navbar-custom-menu\">" + "\r\n");
                    fw.write("                                <ul class=\"nav navbar-nav\">" + "\r\n");
                    fw.write("                                    <!-- Messages: style can be found in dropdown.less-->" + "\r\n");
                    fw.write("                                    <li class=\"dropdown messages-menu\">" + "\r\n");
                    fw.write("                                        <!-- Menu toggle button -->" + "\r\n");
                    fw.write("                                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">" + "\r\n");
                    fw.write("                                            <i class=\"fa fa-envelope-o\"></i>" + "\r\n");
                    fw.write("                                            <span class=\"label label-success\">4</span>" + "\r\n");
                    fw.write("                                        </a>" + "\r\n");
                    fw.write("                                        <ul class=\"dropdown-menu\">" + "\r\n");
                    fw.write("                                            <li class=\"header\">You have 4 messages</li>" + "\r\n");
                    fw.write("                                            <li>" + "\r\n");
                    fw.write("                                                <!-- inner menu: contains the messages -->" + "\r\n");
                    fw.write("                                                <ul class=\"menu\">" + "\r\n");
                    fw.write("                                                    <li><!-- start message -->" + "\r\n");
                    fw.write("                                                        <a href=\"#\">" + "\r\n");
                    fw.write("                                                            <div class=\"pull-left\">" + "\r\n");
                    fw.write("                                                                <!-- User Image -->" + "\r\n");
                    fw.write("                                                                <h:graphicImage library=\"img\" name=\"logo.png\" class=\"img-circle\" alt=\"User Image\"/>" + "\r\n");
                    fw.write("                                                            </div>" + "\r\n");
                    fw.write("                                                            <!-- Message title and timestamp -->" + "\r\n");
                    fw.write("                                                            <h4>" + "\r\n");
                    fw.write("                                                                Support Team" + "\r\n");
                    fw.write("                                                                <small><i class=\"fa fa-clock-o\"></i> 5 mins</small>" + "\r\n");
                    fw.write("                                                            </h4>" + "\r\n");
                    fw.write("                                                            <!-- The message -->" + "\r\n");
                    fw.write("                                                            <p>Why not buy a new awesome theme?</p>" + "\r\n");
                    fw.write("                                                        </a>" + "\r\n");
                    fw.write("                                                    </li><!-- end message -->" + "\r\n");
                    fw.write("                                                </ul><!-- /.menu -->" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                            <li class=\"footer\"><a href=\"#\">See All Messages</a></li>" + "\r\n");
                    fw.write("                                        </ul>" + "\r\n");
                    fw.write("                                    </li><!-- /.messages-menu -->" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                    <!-- Notifications Menu -->" + "\r\n");
                    fw.write("                                    <li class=\"dropdown notifications-menu\">" + "\r\n");
                    fw.write("                                        <!-- Menu toggle button -->" + "\r\n");
                    fw.write("                                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">" + "\r\n");
                    fw.write("                                            <i class=\"fa fa-bell-o\"></i>" + "\r\n");
                    fw.write("                                            <span class=\"label label-warning\">10</span>" + "\r\n");
                    fw.write("                                        </a>" + "\r\n");
                    fw.write("                                        <ul class=\"dropdown-menu\">" + "\r\n");
                    fw.write("                                            <li class=\"header\">You have 10 notifications</li>" + "\r\n");
                    fw.write("                                            <li>" + "\r\n");
                    fw.write("                                                <!-- Inner Menu: contains the notifications -->" + "\r\n");
                    fw.write("                                                <ul class=\"menu\">" + "\r\n");
                    fw.write("                                                    <li><!-- start notification -->" + "\r\n");
                    fw.write("                                                        <a href=\"#\">" + "\r\n");
                    fw.write("                                                            <i class=\"fa fa-users text-aqua\"></i> 5 new members joined today" + "\r\n");
                    fw.write("                                                        </a>" + "\r\n");
                    fw.write("                                                    </li><!-- end notification -->" + "\r\n");
                    fw.write("                                                </ul>" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                            <li class=\"footer\"><a href=\"#\">View all</a></li>" + "\r\n");
                    fw.write("                                        </ul>" + "\r\n");
                    fw.write("                                    </li>" + "\r\n");
                    fw.write("                                    <!-- Tasks Menu -->" + "\r\n");
                    fw.write("                                    <li class=\"dropdown tasks-menu\">" + "\r\n");
                    fw.write("                                        <!-- Menu Toggle Button -->" + "\r\n");
                    fw.write("                                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">" + "\r\n");
                    fw.write("                                            <i class=\"fa fa-flag-o\"></i>" + "\r\n");
                    fw.write("                                            <span class=\"label label-danger\">9</span>" + "\r\n");
                    fw.write("                                        </a>" + "\r\n");
                    fw.write("                                        <ul class=\"dropdown-menu\">" + "\r\n");
                    fw.write("                                            <li class=\"header\">You have 9 tasks</li>" + "\r\n");
                    fw.write("                                            <li>" + "\r\n");
                    fw.write("                                                <!-- Inner menu: contains the tasks -->" + "\r\n");
                    fw.write("                                                <ul class=\"menu\">" + "\r\n");
                    fw.write("                                                    <li><!-- Task item -->" + "\r\n");
                    fw.write("                                                        <a href=\"#\">" + "\r\n");
                    fw.write("                                                            <!-- Task title and progress text -->" + "\r\n");
                    fw.write("                                                            <h3>" + "\r\n");
                    fw.write("                                                                Design some buttons" + "\r\n");
                    fw.write("                                                                <small class=\"pull-right\">20%</small>" + "\r\n");
                    fw.write("                                                            </h3>" + "\r\n");
                    fw.write("                                                            <!-- The progress bar -->" + "\r\n");
                    fw.write("                                                            <div class=\"progress xs\">" + "\r\n");
                    fw.write("                                                                <!-- Change the css width attribute to simulate progress -->" + "\r\n");
                    fw.write("                                                                <div class=\"progress-bar progress-bar-aqua\" style=\"width: 20%\" role=\"progressbar\" aria-valuenow=\"20\" aria-valuemin=\"0\" aria-valuemax=\"100\">" + "\r\n");
                    fw.write("                                                                    <span class=\"sr-only\">20% Complete</span>" + "\r\n");
                    fw.write("                                                                </div>" + "\r\n");
                    fw.write("                                                            </div>" + "\r\n");
                    fw.write("                                                        </a>" + "\r\n");
                    fw.write("                                                    </li><!-- end task item -->" + "\r\n");
                    fw.write("                                                </ul>" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                            <li class=\"footer\">" + "\r\n");
                    fw.write("                                                <a href=\"#\">View all tasks</a>" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                        </ul>" + "\r\n");
                    fw.write("                                    </li>" + "\r\n");
                    fw.write("                                    <!-- User Account Menu -->" + "\r\n");
                    fw.write("                                    <li class=\"dropdown user user-menu\">" + "\r\n");
                    fw.write("                                        <!-- Menu Toggle Button -->" + "\r\n");
                    fw.write("                                        <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">" + "\r\n");
                    fw.write("                                            <!-- The user image in the navbar-->" + "\r\n");
                    fw.write("                                            <h:graphicImage library=\"img\" name=\"logo.png\" class=\"user-image\" alt=\"User Image\"/>" + "\r\n");
                    fw.write("                                            <!-- hidden-xs hides the username on small devices so only the image appears. -->" + "\r\n");
                    fw.write("                                            <span class=\"hidden-xs\">#{loginController.usuario.nombre}</span>" + "\r\n");
                    fw.write("                                        </a>" + "\r\n");
                    fw.write("                                        <ul class=\"dropdown-menu\">" + "\r\n");
                    fw.write("                                            <!-- The user image in the menu -->" + "\r\n");
                    fw.write("                                            <li class=\"user-header\">" + "\r\n");
                    fw.write("                                                <h:graphicImage library=\"img\" name=\"logo.png\" class=\"img-circle\" alt=\"User Image\"/>" + "\r\n");
                    fw.write("                                                <p>" + "\r\n");
                    fw.write("                                                    #{loginController.usuario.nombre}" + "\r\n");
                    fw.write("                                                    <!--<small>Member since Nov. 2012</small>-->" + "\r\n");
                    fw.write("                                                </p>" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                            <!-- Menu Body -->" + "\r\n");
                    fw.write("                                            <li class=\"user-body\">" + "\r\n");
                    fw.write("                                                <!--                                                <div class=\"col-xs-4 text-center\">" + "\r\n");
                    fw.write("                                                                                                    <a href=\"#\">Followers</a>" + "\r\n");
                    fw.write("                                                                                                </div>" + "\r\n");
                    fw.write("                                                                                                <div class=\"col-xs-4 text-center\">" + "\r\n");
                    fw.write("                                                                                                    <a href=\"#\">Sales</a>" + "\r\n");
                    fw.write("                                                                                                </div>" + "\r\n");
                    fw.write("                                                                                                <div class=\"col-xs-4 text-center\">" + "\r\n");
                    fw.write("                                                                                                    <a href=\"#\">Friends</a>" + "\r\n");
                    fw.write("                                                                                                </div>-->" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                            <!-- Menu Footer-->" + "\r\n");
                    fw.write("                                            <li class=\"user-footer\">" + "\r\n");
                    fw.write("                                                <!--                                                <div class=\"pull-left\">" + "\r\n");
                    fw.write("                                                                                                    <a href=\"#\" class=\"btn btn-default btn-flat\">Profile</a>" + "\r\n");
                    fw.write("                                                                                                </div>-->" + "\r\n");
                    fw.write("                                                <div class=\"pull-right\">" + "\r\n");
                    fw.write("                                                    <!--<a href=\"#\" class=\"btn btn-default btn-flat\">Sign out</a>-->" + "\r\n");
                    fw.write("                                                    <p:commandButton class=\"btnn btnn-primary login-btn\" value=\"#{app['button.logout']}\" action=\"#{loginController.doLogout()}\" " + "\r\n");
                    fw.write("                                                                     />" + "\r\n");
                    fw.write("                                                </div>" + "\r\n");
                    fw.write("                                            </li>" + "\r\n");
                    fw.write("                                        </ul>" + "\r\n");
                    fw.write("                                    </li>" + "\r\n");
                    fw.write("                                    <!-- Control Sidebar Toggle Button -->" + "\r\n");
                    fw.write("                                    <li>" + "\r\n");
                    fw.write("                                        <a href=\"#\" data-toggle=\"control-sidebar\"><i class=\"fa fa-gears\"></i></a>" + "\r\n");
                    fw.write("                                    </li>" + "\r\n");
                    fw.write("                                </ul>" + "\r\n");
                    fw.write("                            </div>" + "\r\n");
                    fw.write("                        </nav>" + "\r\n");
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
