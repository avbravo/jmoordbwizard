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
    Rutas rutas;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("template.xhtml", rutas.getPathMainWebapp()+ rutas.getSeparator()+ "template.xhtml");

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
            Utilidades.addNotFoundMethod(ruta, "<f:facet name=\"first\">", first(), "<h:head>", false);
            Utilidades.addNotFoundMethod(ruta, "<f:facet name=\"last\">", last(), "</f:facet>", false);
            Utilidades.addNotFoundMethod(ruta, ".ui-growl {", uigrowl(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".sub-header {", subheader(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".navbar-fixed-top {", navbarfixedtop(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".sidebar {", sidebar(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, "@media (min-width: 768px) {", media(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".nav-list {", navlist(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".nav-list > li > a {", navlistli(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".nav-list > .active > a, .nav-sidebar > .active >", navlistactive(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".main {", main(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, "@media (min-width: 768px) {", mediamin(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".main .page-header {", mainpageheader(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".placeholders {", placeholders(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".placeholders h4 {", placeholdersh4(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".placeholder {", placeholder(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".placeholder img {", placeholderimg(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu {", dropdownsubmenu(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu>.dropdown-menu {", dropdownsubmenudropdownmenu(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu:hover>.dropdown-menu {", dropdownsubmenuhoverdropdownmenu(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu>a:after {", dropdownsubmenuaafter(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu:hover>a:after {", dropdownsubmenuhoveraafter(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu.pull-left {", dropdownsubmenupullleft(), "</style>", true);
            Utilidades.addNotFoundMethod(ruta, ".dropdown-submenu.pull-left>.dropdown-men{", dropdownsubmenupullleftdropdownmen(), "</style>", true);
            Utilidades.searchAddWithoutLine(ruta, "<ui:include src=\"header.xhtml\" />", "</h:head>", false);
            Utilidades.searchAddWithoutLine(ruta, "<ui:include src=\"content.xhtml\" />", "</h:head>", false);
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

                    fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\r\n");
                    fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" " + "\r\n");
                    fw.write("    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + "\r\n");
                    fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://java.sun.com/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://java.sun.com/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:ui=\"http://java.sun.com/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <h:head>" + "\r\n");
                    fw.write("        <f:facet name=\"first\">" + "\r\n");
                    fw.write("            <f:view locale=\"#{idiomas.locale}\"></f:view>" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.messages\" var=\"msg\" />" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.application\" var=\"app\" />" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.menu\" var=\"men\" />" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.entity\" var=\"ent\" />" + "\r\n");
                    fw.write("            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.form\" var=\"form\" />" + "\r\n");
                    fw.write("            <title><h:outputText value=\"#{app['application.title']}\"/></title>" + "\r\n");
                    fw.write("        </f:facet>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <f:facet name=\"last\">" + "\r\n");
                    fw.write("        <link rel=\"stylesheet\" type=\"text/css\" href=\"#{facesContext.externalContext.requestContextPath}/resources/css/bootstrap.css\" />" + "\r\n");
                    fw.write("        </f:facet>" + "\r\n");
                    fw.write("                 <title>" + mySesion.getProyecto() + "</title>" + "\r\n");
                    fw.write("        <meta name=\"author\" content=\"#{app['application.title']}\"></meta>" + "\r\n");
                    fw.write("        <style type=\"text/css\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .ui-growl {" + "\r\n");
                    fw.write("                right: 50%;" + "\r\n");
                    fw.write("                top: 20%;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .sub-header {" + "\r\n");
                    fw.write("                border-bottom: 1px solid #eee;" + "\r\n");
                    fw.write("                padding-bottom: 10px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .navbar-fixed-top {" + "\r\n");
                    fw.write("                border: 0 none;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .sidebar {" + "\r\n");
                    fw.write("                display: none;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            @media (min-width: 768px) {" + "\r\n");
                    fw.write("                .sidebar {" + "\r\n");
                    fw.write("                    background-color: #f5f5f5;" + "\r\n");
                    fw.write("                    border-right: 1px solid #eee;" + "\r\n");
                    fw.write("                    bottom: 0;" + "\r\n");
                    fw.write("                    display: block;" + "\r\n");
                    fw.write("                    left: 0;" + "\r\n");
                    fw.write("                    overflow-x: hidden;" + "\r\n");
                    fw.write("                    overflow-y: auto;" + "\r\n");
                    fw.write("                    padding: 20px;" + "\r\n");
                    fw.write("                    position: fixed;" + "\r\n");
                    fw.write("                    top: 51px;" + "\r\n");
                    fw.write("                    z-index: 1000;" + "\r\n");
                    fw.write("                }" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .nav-list {" + "\r\n");
                    fw.write("                margin-bottom: 20px;" + "\r\n");
                    fw.write("                margin-left: -20px;" + "\r\n");
                    fw.write("                margin-right: -21px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .nav-list > li > a {" + "\r\n");
                    fw.write("                padding-left: 20px;" + "\r\n");
                    fw.write("                padding-right: 20px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .nav-list > .active > a, .nav-sidebar > .active > a:hover, .nav-sidebar > .active > a:focus {" + "\r\n");
                    fw.write("                background-color: #428bca;" + "\r\n");
                    fw.write("                color: #fff;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .main {" + "\r\n");
                    fw.write("                padding: 20px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            @media (min-width: 768px) {" + "\r\n");
                    fw.write("                .main {" + "\r\n");
                    fw.write("                    padding-left: 40px;" + "\r\n");
                    fw.write("                    padding-right: 40px;" + "\r\n");
                    fw.write("                }" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .main .page-header {" + "\r\n");
                    fw.write("                margin-top: 0;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .placeholders {" + "\r\n");
                    fw.write("                margin-bottom: 30px;" + "\r\n");
                    fw.write("                text-align: center;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .placeholders h4 {" + "\r\n");
                    fw.write("                margin-bottom: 0;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .placeholder {" + "\r\n");
                    fw.write("                margin-bottom: 20px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            .placeholder img {" + "\r\n");
                    fw.write("                border-radius: 50%;" + "\r\n");
                    fw.write("                display: inline-block;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu {" + "\r\n");
                    fw.write("                position: relative;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu>.dropdown-menu {" + "\r\n");
                    fw.write("                top: 0;" + "\r\n");
                    fw.write("                left: 100%;" + "\r\n");
                    fw.write("                margin-top: -6px;" + "\r\n");
                    fw.write("                margin-left: -1px;" + "\r\n");
                    fw.write("                -webkit-border-radius: 0 6px 6px 6px;" + "\r\n");
                    fw.write("                -moz-border-radius: 0 6px 6px;" + "\r\n");
                    fw.write("                border-radius: 0 6px 6px 6px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu:hover>.dropdown-menu {" + "\r\n");
                    fw.write("                display: block;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu>a:after {" + "\r\n");
                    fw.write("                display: block;" + "\r\n");
                    fw.write("                content: \" \";" + "\r\n");
                    fw.write("                float: right;" + "\r\n");
                    fw.write("                width: 0;" + "\r\n");
                    fw.write("                height: 0;" + "\r\n");
                    fw.write("                border-color: transparent;" + "\r\n");
                    fw.write("                border-style: solid;" + "\r\n");
                    fw.write("                border-width: 5px 0 5px 5px;" + "\r\n");
                    fw.write("                border-left-color: #ccc;" + "\r\n");
                    fw.write("                margin-top: 5px;" + "\r\n");
                    fw.write("                margin-right: -10px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu:hover>a:after {" + "\r\n");
                    fw.write("                border-left-color: #fff;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu.pull-left {" + "\r\n");
                    fw.write("                float: none;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            .dropdown-submenu.pull-left>.dropdown-men{" + "\r\n");
                    fw.write("                left: -100%;" + "\r\n");
                    fw.write("                margin-left: 10px;" + "\r\n");
                    fw.write("                -webkit-border-radius: 6px 0 6px 6px;" + "\r\n");
                    fw.write("                -moz-border-radius: 6px 0 6px 6px;" + "\r\n");
                    fw.write("                border-radius: 6px 0 6px 6px;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("        </style>" + "\r\n");
                    fw.write("    </h:head>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <h:body style=\"padding-top: 50px; padding-bottom: 20px;\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <ui:include src=\"header.xhtml\" />" + "\r\n");
                    fw.write("        <ui:include src=\"content.xhtml\" />  " + "\r\n");
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

    private String uigrowl() {
        try {

            String texto = "";
            texto += "            .ui-growl {" + "\r\n";
            texto += "                right: 50%;" + "\r\n";
            texto += "                top: 20%;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("uigrowl()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String first() {
        try {

            String texto = "";
            texto += "        <f:facet name=\"first\">" + "\r\n";
            texto += "            <f:view locale=\"#{idiomas.locale}\"></f:view>" + "\r\n";
            texto += "            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.messages\" var=\"msg\" />" + "\r\n";
            texto += "            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.application\" var=\"app\" />" + "\r\n";
            texto += "            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.menu\" var=\"men\" />" + "\r\n";
            texto += "            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.entity\" var=\"ent\" />" + "\r\n";
            texto += "            <f:loadBundle basename=\"" + mySesion.getPaquete() + ".properties.form\" var=\"form\" />" + "\r\n";
            texto += "            <title><h:outputText value=\"#{app['application.title']}\"/></title>" + "\r\n";
            texto += "        </f:facet>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("first()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String last() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "        <f:facet name=\"last\">" + "\r\n";
            texto += "        <link rel=\"stylesheet\" type=\"text/css\" href=\"#{facesContext.externalContext.requestContextPath}/resources/css/bootstrap.css\" />" + "\r\n";
            texto += "        </f:facet>" + "\r\n";
            texto += "          <title>" + mySesion.getProyecto() + "</title>" + "\r\n";
            texto += "          <meta name=\"author\" content=\"#{app['application.title']}\"></meta>" + "\r\n";
            texto += "        <style type=\"text/css\">" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("last()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String subheader() {
        try {

            String texto = "";
            texto += "            .sub-header {" + "\r\n";
            texto += "                border-bottom: 1px solid #eee;" + "\r\n";
            texto += "                padding-bottom: 10px;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("subheader()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String navbarfixedtop() {
        try {

            String texto = "";
            texto += "            .navbar-fixed-top {" + "\r\n";
            texto += "                border: 0 none;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("navbarfixedtop()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String sidebar() {
        try {

            String texto = "";
            texto += "            .sidebar {" + "\r\n";
            texto += "                display: none;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("sidebar()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String media() {
        try {

            String texto = "";
            texto += "            @media (min-width: 768px) {" + "\r\n";
            texto += "                .sidebar {" + "\r\n";
            texto += "                    background-color: #f5f5f5;" + "\r\n";
            texto += "                    border-right: 1px solid #eee;" + "\r\n";
            texto += "                    bottom: 0;" + "\r\n";
            texto += "                    display: block;" + "\r\n";
            texto += "                    left: 0;" + "\r\n";
            texto += "                    overflow-x: hidden;" + "\r\n";
            texto += "                    overflow-y: auto;" + "\r\n";
            texto += "                    padding: 20px;" + "\r\n";
            texto += "                    position: fixed;" + "\r\n";
            texto += "                    top: 51px;" + "\r\n";
            texto += "                    z-index: 1000;" + "\r\n";
            texto += "                }" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("media()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String navlist() {
        try {

            String texto = "";
            texto += "            .nav-list {" + "\r\n";
            texto += "                margin-bottom: 20px;" + "\r\n";
            texto += "                margin-left: -20px;" + "\r\n";
            texto += "                margin-right: -21px;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("navlist()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String navlistli() {
        try {

            String texto = "";
            texto += "            .nav-list > li > a {" + "\r\n";
            texto += "                padding-left: 20px;" + "\r\n";
            texto += "                padding-right: 20px;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("navlistli()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String navlistactive() {
        try {

            String texto = "";
            texto += "            .nav-list > .active > a, .nav-sidebar > .active > a:hover, .nav-sidebar > .active > a:focus {" + "\r\n";
            texto += "                background-color: #428bca;" + "\r\n";
            texto += "                color: #fff;" + "\r\n";
            texto += "            }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("navlistactive()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String main() {
        try {

            String texto = "";
            texto += "            .main {" + "\r\n";
            texto += "                padding: 20px;" + "\r\n";
            texto += "            }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("main()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String mediamin() {
        try {

            String texto = "";
            texto += "            @media (min-width: 768px) {" + "\r\n";
            texto += "                .main {" + "\r\n";
            texto += "                    padding-left: 40px;" + "\r\n";
            texto += "                    padding-right: 40px;" + "\r\n";
            texto += "                }" + "\r\n";
            texto += "            }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("mediamin(  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String mainpageheader() {
        try {

            String texto = "";
            texto += "            .main .page-header {" + "\r\n";
            texto += "                margin-top: 0;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("mainpageheader()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String placeholders() {
        try {

            String texto = "";
            texto += "            .placeholders {" + "\r\n";
            texto += "                margin-bottom: 30px;" + "\r\n";
            texto += "                text-align: center;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("placeholders(  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String placeholdersh4() {
        try {

            String texto = "";
            texto += "            .placeholders h4 {" + "\r\n";
            texto += "                margin-bottom: 0;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("placeholdersh4()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String placeholder() {
        try {

            String texto = "";
            texto += "            .placeholder {" + "\r\n";
            texto += "                margin-bottom: 20px;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("placeholder()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String placeholderimg() {
        try {

            String texto = "";
            texto += "            .placeholder img {" + "\r\n";
            texto += "                border-radius: 50%;" + "\r\n";
            texto += "                display: inline-block;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("placeholderimg()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenu() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu {" + "\r\n";
            texto += "                position: relative;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenu()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenudropdownmenu() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu>.dropdown-menu {" + "\r\n";
            texto += "                top: 0;" + "\r\n";
            texto += "                left: 100%;" + "\r\n";
            texto += "                margin-top: -6px;" + "\r\n";
            texto += "                margin-left: -1px;" + "\r\n";
            texto += "                -webkit-border-radius: 0 6px 6px 6px;" + "\r\n";
            texto += "                -moz-border-radius: 0 6px 6px;" + "\r\n";
            texto += "                border-radius: 0 6px 6px 6px;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenudropdownmenu()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenuhoverdropdownmenu() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu:hover>.dropdown-menu {" + "\r\n";
            texto += "                display: block;" + "\r\n";
            texto += "            }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenudropdownmenu()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenuaafter() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu>a:after {" + "\r\n";
            texto += "                display: block;" + "\r\n";
            texto += "                content: \" \";" + "\r\n";
            texto += "                float: right;" + "\r\n";
            texto += "                width: 0;" + "\r\n";
            texto += "                height: 0;" + "\r\n";
            texto += "                border-color: transparent;" + "\r\n";
            texto += "                border-style: solid;" + "\r\n";
            texto += "                border-width: 5px 0 5px 5px;" + "\r\n";
            texto += "                border-left-color: #ccc;" + "\r\n";
            texto += "                margin-top: 5px;" + "\r\n";
            texto += "                margin-right: -10px;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenudropdownmenu()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenuhoveraafter() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu:hover>a:after {" + "\r\n";
            texto += "                border-left-color: #fff;" + "\r\n";
            texto += "            }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenudropdownmenu()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenupullleft() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu.pull-left {" + "\r\n";
            texto += "                float: none;" + "\r\n";
            texto += "            }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenudropdownmenu()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String dropdownsubmenupullleftdropdownmen() {
        try {

            String texto = "";
            texto += "            .dropdown-submenu.pull-left>.dropdown-men{" + "\r\n";
            texto += "                left: -100%;" + "\r\n";
            texto += "                margin-left: 10px;" + "\r\n";
            texto += "                -webkit-border-radius: 6px 0 6px 6px;" + "\r\n";
            texto += "                -moz-border-radius: 6px 0 6px 6px;" + "\r\n";
            texto += "                border-radius: 6px 0 6px 6px;" + "\r\n";
            texto += "            }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dropdownsubmenupullleftdropdownmen()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
