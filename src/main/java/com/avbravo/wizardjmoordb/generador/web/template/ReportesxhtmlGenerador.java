/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.web.template;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
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
public class ReportesxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ReportesxhtmlGenerador.class.getName());

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
            for (Entidad entidad : mySesion.getEntidadList()) {
                String name = Utilidades.letterToLower(entidad.getTabla());

                String directorioentity = rutas.getPathMainWebappPages() + Utilidades.letterToLower(entidad.getTabla()) + rutas.getSeparator();
                procesar(name + "reportes.xhtml", directorioentity + rutas.getSeparator() + name + "reportes.xhtml", entidad);
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta, Entidad entidad) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo, entidad);
            }

//            Utilidades.searchAdd(ruta, "<b:navBar  brand=\"#{men['menu.home']}\" brandHref=\"/" + mySesion.getProyecto() + "/faces/index.xhtml\" inverse=\"true\" fixed=\"top\">", "<ui:composition>", false);
//            Utilidades.searchAdd(ruta, "<b:commandButton rendered=\"#{!loginBean.logeado}\" action=\"#{loginBean.verificarLogin}\" value=\"#{app['boton.login']}\" look=\"primary\"  iconAlign=\"right\"/>", "</div>", false);
//            Utilidades.searchAdd(ruta, "<b:label  text=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosNombreMostrar()) + "}\"  rendered=\"#{loginBean.logeado}\"/>", "<b:commandButton rendered=\"#{!loginBean.logeado}\" action=\"#{loginBean.verificarLogin}\" value=\"#{app['boton.login']}\" look=\"primary\"  iconAlign=\"right\"/>", false);
//            Utilidades.searchAdd(ruta, "<b:commandButton rendered=\"#{loginBean.logeado}\" action=\"#{loginBean.logout()}\" value=\"#{app['boton.logout']}\" look=\"success\"  iconAlign=\"right\"/>", "<b:label  text=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosNombreMostrar()) + "}\"  rendered=\"#{loginBean.logeado}\"/>", false);
//            Utilidades.searchAdd(archivo, "#{' '}", "</div>", Boolean.TRUE);
//            mySesion.getMenubarList().stream().forEach((s) -> {
//
//                Utilidades.searchAdd(ruta, "<ui:include src=\"menu" + Utilidades.letterToLower(s) + ".xhtml\"/>", "<b:navbarLinks>", Boolean.FALSE);
//
//            });
            /**
             * generar los metodos //
             */
//            Utilidades.addNotFoundMethod(ruta, "<b:inputText rendered=\"#{!loginBean.logeado}\" value=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosUsername()) + "}\" placeholder=\"#{app['login.username']}\" fieldSize=\"sm\"/>", username(), "<h:form styleClass=\"navbar-form navbar-right\">", false);
//            Utilidades.addNotFoundMethod(ruta, "<p:password rendered=\"#{!loginBean.logeado}\" value=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosPassword()) + "}\" placeholder=\"#{app['login.password']}\" />", password(), "</div>", false);
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
    private Boolean crearFile(String path, String archivo, Entidad entidad) throws IOException {
        try {
            String name = Utilidades.letterToLower(entidad.getTabla());
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
                    fw.write("                <f:view>" + "\r\n"    );
                    fw.write("" + "\r\n");
                    fw.write("               <h:form id=\"form\" prependId=\"false\" rendered=\"#{menuBeans." + name + ".listar}\">" + "\r\n");
                    fw.write("                   <p:growl  id=\"growl\" />" + "\r\n");
                    fw.write("                   <b:panel id=\"panel\" look=\"primary\" title=\"#{form['" + name + ".print']}\">" + "\r\n");
                    fw.write("                   " + "\r\n");
                    fw.write("                    <p:commandButton class=\"btn btn-primary\"" + "\r\n");
                    fw.write("                                     id=\"button_print\"" + "\r\n");
                    fw.write("                                     value=\"#{app['boton.print']}\"" + "\r\n");
                    fw.write("                             title=\"#{app['boton.print']}\"" + "\r\n");
                    fw.write("                             action=\"#{" + name + "Search.listar()}\"" + "\r\n");
                    fw.write("                             icon=\"ui-icon-print\" ajax=\"false\" />" + "\r\n");
                    fw.write("                   </b:panel>" + "\r\n");
                    fw.write("                   " + "\r\n");
                    fw.write("                    " + "\r\n");
                    fw.write("                     " + "\r\n");
                    fw.write("                    </h:form>" + "\r\n");
                    fw.write("                    <h:form rendered=\"#{!menuBeans." + name + ".listar}\">" + "\r\n");
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
