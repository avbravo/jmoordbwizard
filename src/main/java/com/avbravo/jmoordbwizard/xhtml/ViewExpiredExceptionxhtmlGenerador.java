/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.xhtml;

// <editor-fold defaultstate="collapsed" desc="imports"> 
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
// </editor-fold>

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class ViewExpiredExceptionxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ViewExpiredExceptionxhtmlGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    FileWriter fw;
    // <editor-fold defaultstate="collapsed" desc="generar"> 

    /**
     *
     */
    public void generar() {
        try {

            procesar("viewExpiredException.xhtml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="procesar"> 
    private Boolean procesar(String archivo) {
        try {
            String ruta = proyectoJEE.getPathMainWebapp() + proyectoJEE.getSeparator() + archivo;
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }// </editor-fold>

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
//                try (FileWriter fw = new FileWriter(file)) {
                fw = new FileWriter(file);
                fw.write("<!DOCTYPE html>" + "\r\n");
                fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                fw.write("      xmlns:ui=\"http://java.sun.com/jsf/facelets\"" + "\r\n");
                fw.write("      xmlns:f=\"http://java.sun.com/jsf/core\"" + "\r\n");
                fw.write("      xmlns:h=\"http://java.sun.com/jsf/html\"" + "\r\n");
                fw.write("      xmlns:b=\"http://bootsfaces.net/ui\"" + "\r\n");
                fw.write("      xmlns:p=\"http://primefaces.org/ui\">" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("    <h:head>" + "\r\n");
                fw.write("        <f:facet name=\"first\">" + "\r\n");
                fw.write("            <f:view locale=\"#{idiomas.locale}\"></f:view>" + "\r\n");
                fw.write("            <f:loadBundle basename=\"" + proyectoJEE.getPaquete() + ".properties.messages\" var=\"msg\" />" + "\r\n");
                fw.write("            <f:loadBundle basename=\"com.avbravoutils.properties.application\" var=\"app\" />" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("        </f:facet>" + "\r\n");
                fw.write("        <title>#{msg['application.title']}</title>" + "\r\n");
                fw.write("        <!-- Bootstrap core CSS -->" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                fw.write("              href=\"#{request.contextPath}/resources/bootstrap/css/bootstrap.min.css\" />" + "\r\n");
                fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                fw.write("              href=\"#{request.contextPath}/resources/dist/css/AdminLTE.min.css\" />" + "\r\n");
                fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                fw.write("              href=\"#{request.contextPath}/resources/dist/css/skins/skin-blue.min.css\" />" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                fw.write("              href=\"#{request.contextPath}/resources/bootstrapcdn/css/font-awesome.min.css\" />" + "\r\n");
                fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                fw.write("              href=\"#{request.contextPath}/resources/ionicframework/css/ionicons.min.css\" />" + "\r\n");
                fw.write("        <h:outputStylesheet library=\"css\" name=\"bootstrap-reset.css\"/>" + "\r\n");
                fw.write("        <h:outputStylesheet library=\"webjars\" name=\"font-awesome/4.6.3/css/font-awesome-jsf.css\" />" + "\r\n");
                fw.write("        <h:outputStylesheet library=\"css\" name=\"style-responsive.css\"/>" + "\r\n");
                fw.write("        <h:outputStylesheet library=\"css\" name=\"pfcrud.css\"/>" + "\r\n");
                fw.write("        <h:outputStylesheet library=\"css\" name=\"style.css\"/>" + "\r\n");
                fw.write("        <style type=\"text/css\">" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("            .ui-growl {" + "\r\n");
                fw.write("                right: 50%;" + "\r\n");
                fw.write("                top: 20%;" + "\r\n");
                fw.write("            }" + "\r\n");
                fw.write("        </style>" + "\r\n");
                fw.write("    </h:head>" + "\r\n");
                fw.write("    <h:body class=\"hold-transition skin-blue sidebar-mini\">" + "\r\n");
                fw.write("        <f:view >" + "\r\n");
                fw.write("            <div class=\"wrapper\">" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                <!-- Main Header -->" + "\r\n");
                fw.write("                <header class=\"main-header\">" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                    <ui:include src=\"/pages/top.xhtml\"/>" + "\r\n");
                fw.write("                </header>" + "\r\n");
                fw.write("                <!-- Left side column. contains the logo and sidebar -->" + "\r\n");
                fw.write("                <aside class=\"main-sidebar\">" + "\r\n");
                fw.write("                    <!-- sidebar: style can be found in sidebar.less -->" + "\r\n");
                fw.write("                    <section class=\"sidebar\">" + "\r\n");
                fw.write("                        <ui:include src=\"/pages/left.xhtml\"/>" + "\r\n");
                fw.write("                    </section>" + "\r\n");
                fw.write("                    <!-- /.sidebar -->" + "\r\n");
                fw.write("                </aside>" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                <!-- Content Wrapper. Contains page content -->" + "\r\n");
                fw.write("                <div class=\"content-wrapper\">" + "\r\n");
                fw.write("                    <!-- Content Header (Page header) -->" + "\r\n");
                fw.write("                    <section class=\"content-header\">" + "\r\n");
                fw.write("                        <p:growl id=\"growl\"/>" + "\r\n");
                fw.write("                        <h1>" + "\r\n");
                fw.write("                            #{app['title.sessionexpirada']}" + "\r\n");
                fw.write("                            <small>Optional description</small>" + "\r\n");
                fw.write("                        </h1>" + "\r\n");
                fw.write("                        <ol class=\"breadcrumb\">" + "\r\n");
                fw.write("                            <li><a href=\"#\"><i class=\"fa fa-dashboard\"></i> Level</a></li>" + "\r\n");
                fw.write("                            <li class=\"active\">Here</li>" + "\r\n");
                fw.write("                        </ol>" + "\r\n");
                fw.write("                    </section>" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                    <!-- Main content -->" + "\r\n");
                fw.write("                    <section class=\"content\" >" + "\r\n");
                fw.write("                    <!--<h:form id=\"form\" rendered=\"#{loginController.loggedIn}\">-->" + "\r\n");
                fw.write("                        <h:form id=\"form\" >" + "\r\n");
                fw.write("                            <p:messages id=\"growl\"/>" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                            <div class=\"row form-body\">" + "\r\n");
                fw.write("                                <p:growl id=\"msgs\" widgetVar=\"growl\"/>" + "\r\n");
                fw.write("                                <div class=\"form-group row\">" + "\r\n");
                fw.write("                                    <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{app['info.sesionhaexpirado']}\"/>" + "\r\n");
                fw.write("                                </div>" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("" + "\r\n");
                //Botones
                fw.write("                                <div class=\"form-group row\">" + "\r\n");
                fw.write("                                    <div class=\"col-xs-4\">" + "\r\n");
                fw.write("                                        <p:link value=\"#{app['button.home']}\"   outcome=\"index\"/>" + "\r\n");
                fw.write("                                    </div>" + "\r\n");
                fw.write("                                </div>" + "\r\n");

                fw.write("" + "\r\n");
                fw.write("                            </div>" + "\r\n");
                fw.write("                        </h:form>" + "\r\n");
                fw.write("                    </section><!-- /.content -->" + "\r\n");
                fw.write("                </div><!-- /.content-wrapper -->" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                <!-- Main Footer -->" + "\r\n");
                fw.write("                <ui:include src=\"/pages/footer.xhtml\"/>" + "\r\n");
                fw.write("                <ui:include src=\"/pages/rigth.xhtml\"/>" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                <!-- Add the sidebar's background. This div must be placed" + "\r\n");
                fw.write("                immediately after the control sidebar -->" + "\r\n");
                fw.write("                <div class=\"control-sidebar-bg\"></div>" + "\r\n");
                fw.write("            </div><!-- ./wrapper -->" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("            <!-- REQUIRED JS SCRIPTS -->" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("            <script type=\"text/javascript\" src=\"#{request.contextPath}/resources/bootstrap/js/bootstrap.min.js\"></script> <!-- Correio -->" + "\r\n");
                fw.write("            <script type=\"text/javascript\" src=\"#{request.contextPath}/resources/dist/js/app.min.js\"></script> <!-- Correio -->" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("            <br />" + "\r\n");
                fw.write("        </f:view>" + "\r\n");
                fw.write("    </h:body>" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("</html>" + "\r\n");
                fw.close();

//                } catch (IOException ex) {
//                    JSFUtil.addErrorMessage("crearFile() " + ex.getLocalizedMessage());
//                }
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

}
