/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.web.template;

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
public class TemplatexhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TemplatexhtmlGenerador.class.getName());

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

//            procesar("template.xhtml", proyectoJEE.getPathMainWebapp()+ proyectoJEE.getSeparator()+ "template.xhtml");
            procesar("template.xhtml", proyectoJEE.getPathWebInf() + proyectoJEE.getSeparator() + "template.xhtml");

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

                    fw.write("<!DOCTYPE html>" + "\r\n");
                    fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://java.sun.com/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://java.sun.com/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:ui=\"http://java.sun.com/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <h:head>" + "\r\n");
                    fw.write("        <f:facet name=\"first\">" + "\r\n");
                    fw.write("            <f:view locale=\"#{idiomas.locale}\"></f:view>" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + proyectoJEE.getPaquete() + ".properties.messages\" var=\"msg\" />" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + "com.avbravoutils.properties.application\" var=\"app\" />" + "\r\n");
                    fw.write("        </f:facet>" + "\r\n");
                    fw.write("        <title><h:outputText value=\"#{app['application.title']}\"/></title>" + "\r\n");

                    fw.write("        <!-- Bootstrap core CSS -->" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                    fw.write("              href=\"#{request.contextPath}/resources/bootstrap/css/bootstrap.min.css\" />" + "\r\n");
                    fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                    fw.write("              href=\"#{request.contextPath}/resources/dist/css/AdminLTE.min.css\" />" + "\r\n");
                    fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                    fw.write("              href=\"#{request.contextPath}/resources/dist/css/skins/skin-blue.min.css\" />" + "\r\n");

                    fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                    fw.write("              href=\"#{request.contextPath}/resources/bootstrapcdn/css/font-awesome.min.css\" />" + "\r\n");
                    fw.write("        <link rel=\"stylesheet\" type=\"text/css\"" + "\r\n");
                    fw.write("              href=\"#{request.contextPath}/resources/ionicframework/css/ionicons.min.css\" />" + "\r\n");
                    fw.write("        <h:outputStylesheet library=\"css\" name=\"bootstrap-reset.css\"/>" + "\r\n");
                    fw.write("        <h:outputStylesheet library=\"webjars\" name=\"font-awesome/4.6.3/css/font-awesome-jsf.css\" />" + "\r\n");
                    fw.write("        <h:outputStylesheet library=\"css\" name=\"style-responsive.css\"/>" + "\r\n");
                    fw.write("        <h:outputStylesheet library=\"css\" name=\"pfcrud.css\"/>" + "\r\n");
                    fw.write("        <h:outputStylesheet library=\"css\" name=\"style.css\"/>" + "\r\n");

                    fw.write("    </h:head>" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    <h:body class=\"hold-transition skin-blue sidebar-mini\">" + "\r\n");
                    fw.write("         <f:view >" + "\r\n");
                    fw.write("            <div class=\"wrapper\">" + "\r\n");
                    fw.write("                <!-- Main Header -->" + "\r\n");
                    fw.write("                <header class=\"main-header\">" + "\r\n");
                    fw.write("                    <ui:include src=\"/pages/top.xhtml\"/> " + "\r\n");
                    fw.write("                </header>" + "\r\n");
                    fw.write("               <!-- Left side column. contains the logo and sidebar -->" + "\r\n");
                    fw.write("               <aside class=\"main-sidebar\">" + "\r\n");
                    fw.write("                    <!-- sidebar: style can be found in sidebar.less -->" + "\r\n");
                    fw.write("                    <section class=\"sidebar\">" + "\r\n");
                    fw.write("                         <ui:include src=\"/pages/left.xhtml\"/>" + "\r\n");
                    fw.write("                    </section>" + "\r\n");
                    fw.write("                    <!-- /.sidebar -->" + "\r\n");
                    fw.write("              </aside>" + "\r\n");
                    fw.write("              <!-- Content Wrapper. Contains page content -->" + "\r\n");
                    fw.write("              <div class=\"content-wrapper\">" + "\r\n");
                    fw.write("                   <!-- Content Header (Page header) -->" + "\r\n");
                    fw.write("                   <section class=\"content-header\">" + "\r\n");
                    fw.write("                        <ui:insert name=\"top\">" + "\r\n");
                    fw.write("                             top" + "\r\n");
                    fw.write("                        </ui:insert> " + "\r\n");
                    fw.write("                   </section>" + "\r\n");
                    fw.write("                   <!-- Main content -->" + "\r\n");
                    fw.write("                   <section class=\"content\">" + "\r\n");
                    fw.write("                        <ui:insert name=\"content\">" + "\r\n");
                    fw.write("                             content" + "\r\n");
                    fw.write("                        </ui:insert>" + "\r\n");
                    fw.write("                        <div id=\"bottom\">" + "\r\n");
                    fw.write("                            <ui:insert name=\"bottom\">" + "\r\n");
                    fw.write("                            </ui:insert>" + "\r\n");
                    fw.write("                        </div>" + "\r\n");
                    fw.write("                   </section><!-- /.content -->" + "\r\n");
                    fw.write("              </div><!-- /.content-wrapper -->" + "\r\n");
                    fw.write("              <!-- Main Footer -->" + "\r\n");
                    fw.write("              <ui:include src=\"/pages/footer.xhtml\"/> " + "\r\n");
                    fw.write("              <ui:include src=\"/pages/rigth.xhtml\"/> " + "\r\n");
                    fw.write("              <!-- Add the sidebar's background. This div must be placed" + "\r\n");
                    fw.write("                   immediately after the control sidebar -->" + "\r\n");
                    fw.write("              <div class=\"control-sidebar-bg\"></div>" + "\r\n");
                    fw.write("            </div><!-- ./wrapper -->" + "\r\n");
                    fw.write("            <!-- REQUIRED JS SCRIPTS -->" + "\r\n");
                    fw.write("            <script type=\"text/javascript\" src=\"#{request.contextPath}/resources/bootstrap/js/bootstrap.min.js\"></script> <!-- Correio -->" + "\r\n");
                    fw.write("            <script type=\"text/javascript\" src=\"#{request.contextPath}/resources/dist/js/app.min.js\"></script> <!-- Correio -->" + "\r\n");
                    fw.write("            <br />" + "\r\n");
                    fw.write("         </f:view>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    </h:body>" + "\r\n");
                    fw.write("</html>    " + "\r\n");
                    fw.write("" + "\r\n");
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
