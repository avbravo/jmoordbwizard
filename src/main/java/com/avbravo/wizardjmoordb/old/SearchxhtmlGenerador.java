/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.old;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
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
public class SearchxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(SearchxhtmlGenerador.class.getName());

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
            for (Entidad entidad : mySesion.getEntidadList()) {
                String name = Utilidades.letterToLower(entidad.getTabla());

                String directorioentity = proyectoJEE.getPathMainWebappPages() + Utilidades.letterToLower(entidad.getTabla()) + proyectoJEE.getSeparator();
                procesar(name + "search.xhtml", directorioentity + proyectoJEE.getSeparator() + name + "search.xhtml", entidad);
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
//
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
             * generar los metodos
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
            String nameUpper = Utilidades.letterToUpper(entidad.getTabla());
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

                    String columnKey = "";
                    String columnDate = "";
                    Boolean tieneUsername = false;
                    Boolean isIntegerKey = false;
                    for (Atributos a : entidad.getAtributosList()) {
                        if (a.getEsPrimaryKey()) {
                            columnKey = a.getNombre();
                            isIntegerKey = a.getTipo().equals("Integer");
                        } else if (a.getTipo().equals("Date")) {
                            columnDate = a.getNombre();
                        }
                        if (a.getTipo().equals(mySesion.getEntidadUser().getTabla())) {
                            tieneUsername = true;
                        }
                        // Genera los services para los objetos relacionados
                    }
                    fw.write("<?xml version='1.0' encoding='UTF-8' ?>" + "\r\n");
                    fw.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" + "\r\n");
                    fw.write("<html xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("      xmlns:ui=\"http://xmlns.jcp.org/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://xmlns.jcp.org/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:componentes=\"http://xmlns.jcp.org/jsf/composite/componentes\"" + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <body>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <ui:composition template=\"./../../template.xhtml\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            <ui:define name=\"content\">" + "\r\n");
                    fw.write("                <f:view>" + "\r\n");
                    fw.write("                    <h:form id=\"form\" prependId=\"false\" rendered=\"#{menuBeans." + name + ".listar}\">" + "\r\n");
                    fw.write("                        <p:messages id=\"growl\" autoUpdate=\"true\"/>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <b:panel id=\"panel\" look=\"primary\" title=\"#{form['" + name + ".search']}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");

                    // autocomplete para todos los campos
                    for (Atributos atributos : entidad.getAtributosList()) {
                        String columna = Utilidades.letterToLower(atributos.getNombre());
 if (atributos.getTipo().equals("String") ||atributos.getTipo().equals("Integer") ) {
                               
                        fw.write("                                <p:outputLabel   value=\"#{msg." + columna + "}\"  />" + "\r\n");
                        fw.write("                                <p:autoComplete  id=\"" + columna + "\"  scrollHeight=\"250\"   dropdown=\"true\"    size=\"45\" " + "\r\n");
                        fw.write("                                                 value=\"#{" + name + "Search.selected}\"  " + "\r\n");
                        fw.write("                                                 completeMethod=\"#{" + name + "Search." + name + "Services.complete" + Utilidades.letterToUpper(columna) + "}\"  " + "\r\n");
                        fw.write("                                                 var=\"p\"  " + "\r\n");
                        fw.write("                                                 itemLabel=\"#{p." + columna + "}\"  itemValue=\"#{p}\"" + "\r\n");
                        fw.write("                                                 forceSelection=\"true\"> " + "\r\n");
                        fw.write("                                    <f:converter binding=\"#{" + name + "Converter}\" />" + "\r\n");
                        fw.write("                                    <p:ajax event=\"itemSelect\" listener=\"#{" + name + "Search.handleSelect}\" update=\":form:panel, :form:datatable" + nameUpper + ",:form:growl\" />  " + "\r\n");
                        fw.write("                                    <f:facet name=\"itemtip\">" + "\r\n");
                        fw.write("                                        <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");
                        for (Atributos atr : entidad.getAtributosList()) {
                            fw.write("                                           <h:outputText value=\"#{p." + atr.getNombre() + "}\" />" + "\r\n");
                        }

                        fw.write("                                        </h:panelGrid>" + "\r\n");
                        fw.write("                                    </f:facet>" + "\r\n");
                        fw.write("                                </p:autoComplete> " + "\r\n");
 }
                    }
                    fw.write("                            </h:panelGrid>" + "\r\n");
                    //datatable
                    
                    fw.write("                            <p:dataTable  id=\"datatable" + nameUpper + "\" " + "\r\n");
                    fw.write("                                          tableStyleClass=\"table table-striped table-hover dt-responsive\"" + "\r\n");
                    fw.write("                                          rows=\"7\" value=\"#{" + name + "Search." + name + "List}\" " + "\r\n");
                    fw.write("                                          var=\"item\"" + "\r\n");
                    fw.write("                                          paginator=\"true\"" + "\r\n");
                    fw.write("                                          paginatorTemplate=\"{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}\"" + "\r\n");
                    fw.write("                                          rowsPerPageTemplate=\"5,10,15\"" + "\r\n");
//                    fw.write("                                          rowKey=\"#{item." + columnKey + "}\"" + "\r\n");
//                    fw.write("                                          selectionMode=\"single\"" + "\r\n");
//                    fw.write("                                          selection=\"#{" + name + "Search.selected}\"" + "\r\n");
//                    fw.write("                                          filteredValue=\"#{" + name + "Search.filtered}\" " + "\r\n");
                    fw.write("                                          widgetVar=\"" + name + "Table\"" + "\r\n");
                    fw.write("                                          >" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    //campos
                    for (Atributos atributos : entidad.getAtributosList()) {

                        String columna = Utilidades.letterToLower(atributos.getNombre());
                        fw.write("                                <p:column filterBy=\"#{item." + columna + "}\" sortBy=\"#{item." + columna + "}\">" + "\r\n");
                        fw.write("                                    <f:facet name=\"header\">" + "\r\n");
                        fw.write("                                        <p:outputLabel   value=\"#{msg." + columna + "}\"/>" + "\r\n");
                        fw.write("                                    </f:facet>" + "\r\n");
                        fw.write("                                    <p:outputLabel  value=\"#{item." + columna + "}\"/>" + "\r\n");
                        fw.write("                                </p:column>" + "\r\n");
                    }

                    fw.write("                                <p:column >  " + "\r\n");
                    fw.write("                                    <p:commandButton   icon=\"ui-icon-pencil\" action=\"#{" + name + "Controller.invocarEditar(item)}\" update=\":form:growl\"/> " + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                </p:column>" + "\r\n");
                    fw.write("                                <p:column>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                    <p:commandButton icon=\"ui-icon-trash\" actionListener=\"#{" + name + "Search.delete(item)}\" update=\":form:growl,:form:datatable" + nameUpper + "\" >" + "\r\n");
                    fw.write("                                        <p:confirm header=\"#{app['dialog.delete']}\" message=\"#{app['info.doyouwantdelete']}\" icon=\"ui-icon-alert\" />" + "\r\n");
                    fw.write("                                    </p:commandButton>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                </p:column>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            </p:dataTable>" + "\r\n");
                    fw.write("                            <f:facet name=\"footer\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-primary\" value=\"#{app['boton.return']}\"  action=\"" + name + "\"/>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton  " + "\r\n");
                    fw.write("                                    class=\"btn btn-primary\" " + "\r\n");
                    fw.write("                                    value=\"#{app['boton.showall']}\"" + "\r\n");
                    fw.write("                                    icon=\"ui-icon-gear\"" + "\r\n");
                    fw.write("                                    title=\"#{app['boton.showall']}\"" + "\r\n");
                    fw.write("                                    onclick=\"PF('statusDialog').show();\"" + "\r\n");
                    fw.write("                                    onsuccess=\"PF('statusDialog').hide();\"" + "\r\n");
                    fw.write("                                    update =\"datatable" + nameUpper + ",growl\" action=\"#{" + name + "Search.showAll()}\"/>" + "\r\n");
                    fw.write("                            </f:facet>" + "\r\n");
                    fw.write("                        </b:panel>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <componentes:procesar/>" + "\r\n");
                    fw.write("                        <p:confirmDialog global=\"true\" showEffect=\"fade\" hideEffect=\"explode\">" + "\r\n");
                    fw.write("                            <p:commandButton value=\"#{app['boton.yes']}\" type=\"button\" styleClass=\"ui-confirmdialog-yes\" icon=\"ui-icon-check\" />" + "\r\n");
                    fw.write("                            <p:commandButton value=\"#{app['boton.no']}\" type=\"button\" styleClass=\"ui-confirmdialog-no\" icon=\"ui-icon-close\" />" + "\r\n");
                    fw.write("                        </p:confirmDialog>" + "\r\n");
                    fw.write("                    </h:form>" + "\r\n");
                    fw.write("                    <h:form rendered=\"#{!menuBeans." + name + ".listar}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <b:panel look=\"danger\" title=\"#{app['title.accesodenegado']}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-success\" action=\"#{loginBean.irLogin}\"" + "\r\n");
                    fw.write("                                                 value=\"#{app['boton.return']}\" ajax=\"false\"/>" + "\r\n");
                    fw.write("                            </h:panelGrid>" + "\r\n");
                    fw.write("                        </b:panel>" + "\r\n");
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

    private String username() {
        try {

            String texto = "";
            texto += "                <div class=\"form-group\">" + "\r\n";
            texto += "                    <b:inputText rendered=\"#{!loginBean.logeado}\" value=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosUsername()) + "}\" placeholder=\"#{app['login.username']}\" fieldSize=\"sm\"/>" + "\r\n";
            texto += "                </div>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("username()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String password() {
        try {

            String texto = "";
            texto += "                <div class=\"form-group\">" + "\r\n";
            texto += "<p:password rendered=\"#{!loginBean.logeado}\" value=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosPassword()) + "}\" placeholder=\"#{app['login.password']}\" />" + "\r\n";
            texto += "                </div>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("password()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
