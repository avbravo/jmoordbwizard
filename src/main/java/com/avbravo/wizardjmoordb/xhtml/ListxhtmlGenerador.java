/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.xhtml;

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
public class ListxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ListxhtmlGenerador.class.getName());

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
//                procesar(name + "search.xhtml", directorioentity + proyectoJEE.getSeparator() + name + "search.xhtml", entidad);
                procesar("list.xhtml", directorioentity + proyectoJEE.getSeparator() + "list.xhtml", entidad);
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
                    fw.write("      xmlns:ui=\"http://java.sun.com/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://java.sun.com/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:jsf=\"http://xmlns.jcp.org/jsf\"" + "\r\n");
                    fw.write("      xmlns:avbravo=\"http://xmlns.jcp.org/jsf/composite/avbravo\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <body>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        <ui:composition template=\"./../../WEB-INF/template.xhtml\">" + "\r\n");
                    fw.write("            <ui:define name=\"top\">" + "\r\n");
                    fw.write("                <h1>" + "\r\n");
                    fw.write("                    #{msg['titlelist." + name + "']}" + "\r\n");
                    fw.write("                    <small> </small>" + "\r\n");
                    fw.write("                </h1>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            </ui:define>" + "\r\n");
                    fw.write("            <!--" + "\r\n");
                    fw.write("          Contenido" + "\r\n");
                    fw.write("            -->" + "\r\n");
                    fw.write("            <ui:define name=\"content\">" + "\r\n");
                    fw.write("                <h:form id=\"form\" rendered=\"#{loginController.loggedIn and applicationMenu." + name + ".list}\">" + "\r\n");
                    fw.write("                    <h:panelGroup id=\"content\" layout=\"block\"> " + "\r\n");
                    fw.write("                        <p:messages id=\"msgs\" autoUpdate=\"true\"/>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <!--" + "\r\n");
                    fw.write("                      header del formulario" + "\r\n");
                    fw.write("                        -->" + "\r\n");
                    fw.write("                        <div class=\"row form-header-2\" style=\"padding-top: 5px;\">" + "\r\n");
                    fw.write("                            <div class=\"col-md-12 record-status-buttons\">   " + "\r\n");
                    fw.write("                                <p:link  class=\"fa fa-file-text-o btnn btnn-primary\" title=\"#{app['button.records']}\" value=\"#{app['button.records']}\" outcome=\"/pages/" + name + "/view\" />" + "\r\n");
                    fw.write("                                <p:commandButton icon=\"fa fa-print\" style=\"max-width:160px;\" class=\"btnn btnn-primary\" title=\"#{app['button.print']}\" " + "\r\n");
                    fw.write("                                                 value=\"#{app['button.print']}\" process=\":form:content\"" + "\r\n");
                    fw.write("                                                 action=\"#{" + name + "Controller.printAll()}\" " + "\r\n");
                    fw.write("                                                 ajax=\"false\"" + "\r\n");
                    fw.write("                                                 update=\":form:content\" /> " + "\r\n");
                    fw.write("                                <p:remoteCommand  update=\":form:content :form:content\"" + "\r\n");
                    fw.write("                                                  name=\"remoteshowall\" />" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btnn btnn-primary\"  icon=\"fa fa-table\"  title=\"#{app['button.showall']}\" value=\"#{app['button.showall']}\"" + "\r\n");
                    fw.write("                                                 oncomplete=\"remoteshowall();\"" + "\r\n");
                    fw.write("                                                 action=\"#{" + name + "Controller.showAll()}\"  update=\":form:dataTable \" /> " + "\r\n");
                    fw.write("" + "\r\n");

                    // autocomplete para todos los campos
                    Integer maximoAutocomplete = 0;
                    Integer maximoAutocompleteItemLabel = 0;
                    Integer maximoAutocompleteItemTip = 0;
                    Integer contador = 0;
                    for (Atributos atributos : entidad.getAtributosList()) {

                        if (maximoAutocomplete <= mySesion.getMaximoAutocomplete()) {
                            String columna = Utilidades.letterToLower(atributos.getNombre());

                            if (atributos.getTipo().equals("String") || atributos.getTipo().equals("Integer")) {
                                maximoAutocomplete++;
                                contador++;
                                if (contador == 1) {
                                    fw.write("                                <div class=\"form-group row\">" + "\r\n");
                                }
//                                else {
//                                    if (contador > 2) {
//                                        contador = 1;
//                                    }
//                                }

                                fw.write("                                    <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{app['info.by']} #{msg['field." + columna + "']}\"/>" + "\r\n");
                                fw.write("                                    <div class=\"col-xs-4\">" + "\r\n");
                                fw.write("                                        <p:autoComplete  scrollHeight=\"250\"   dropdown=\"false\"  size=\"45\"  " + "\r\n");
                                fw.write("                                                         emptyMessage=\"#{app['info.nohayregistros']}\" " + "\r\n");
                                fw.write("                                                         title=\"#{app['info.by']} #{msg['field." + columna + "']}\"" + "\r\n");
                                fw.write("                                                         label=\"#{app['info.by']} #{msg['field." + columna + "']}\"" + "\r\n");
                                fw.write("" + "\r\n");
                                fw.write("                                                         alt=\"#{app['info.searchby']} #{msg['field." + columna + "']}\"" + "\r\n");
                                fw.write("                                                         value=\"#{" + name + "Controller." + name + "Selected}\"  " + "\r\n");
                                fw.write("                                                         completeMethod=\"#{" + name + "Controller." + name + "Services.complete" + Utilidades.letterToUpper(columna) + "}\"  " + "\r\n");
                                fw.write("                                                         var=\"p\"" + "\r\n");
                                String itemLabel = "itemLabel=\"#{p." + columna + "}";
                                Integer contadorAgregados = 0;
                                for (Atributos atr : entidad.getAtributosList()) {
                                    contadorAgregados++;
                                    if (!atr.getNombre().equals(columna)) {
                                        //Para agregar solo tres campos para no sobrecargal
                                        if (contadorAgregados <= mySesion.getMaximoAutocompleteItemLabel()) {
                                            itemLabel += " " + "#{p." + atr.getNombre() + "}";
                                        }

                                    }

                                }
                                itemLabel += " \"" + " />";
                                fw.write("                                                         " + itemLabel + "\r\n");
                                //fw.write("                                                         itemLabel=\"#{p.cedula} #{p.nombre} #{p.apellido} \" " + "\r\n");

                                fw.write("                                                         itemValue=\"#{p}\" forceSelection=\"true\"> " + "\r\n");
                                fw.write("                                            <f:converter binding=\"#{" + name + "Converter}\"/>" + "\r\n");
                                fw.write("                                            <p:ajax event=\"itemSelect\" listener=\"#{" + name + "Controller.handleSelect}\"" + "\r\n");
                                fw.write("                                                    update=\" :form:msgs,:form:dataTable\" />  " + "\r\n");
                                fw.write("                                            <f:facet name=\"itemtip\">" + "\r\n");
                                fw.write("                                                <h:panelGrid columns=\"1\" cellpadding=\"5\">" + "\r\n");
                                fw.write("                                                    <h:outputText value=\"#{msg['field." + columna + "']} #{p." + columna + "}\" />" + "\r\n");
                                contadorAgregados = 0;
                                for (Atributos atr : entidad.getAtributosList()) {
                                    contadorAgregados++;
                                    if (!atr.getNombre().equals(columna)) {
                                        if (contadorAgregados <= mySesion.getMaximoAutocompleteItemTip()) {
                                            fw.write("                                                 <h:outputText value=\"#{msg['field." + atr.getNombre() + "']} #{p." + atr.getNombre() + "}\" />" + "\r\n");
                                        }

                                    }

                                }
                                fw.write("                                                           " + "\r\n");
                                fw.write("                                                </h:panelGrid>" + "\r\n");
                                fw.write("                                            </f:facet>" + "\r\n");
                                fw.write("                                        </p:autoComplete>   " + "\r\n");
                                fw.write("                                    </div>" + "\r\n");

                            }
                            if (contador == 2) {
                                fw.write("                                   </div>" + "\r\n");
                                contador = 0;
                            }
                        }
                    }//for
//                    fw.write("                                       </div>" + "\r\n");
                    fw.write("                                 </div>" + "\r\n");
                    fw.write("                            </div>" + "\r\n");
                    fw.write("                       </div>" + "\r\n");
                    fw.write("                        <!--" + "\r\n");
                    fw.write("                        body" + "\r\n");
                    fw.write("                        -->" + "\r\n");
                    fw.write("                        <div class=\"row form-body\">" + "\r\n");

                    fw.write("                            <p:dataTable id=\"dataTable\" var=\"item\" " + "\r\n");
                    fw.write("                                         value=\"#{" + name + "Controller." + name + "DataModel}\"" + "\r\n");
                    fw.write("                                         selectionMode=\"single\" " + "\r\n");
                    fw.write("                                         widgetVar=\"widgetDataTable\"" + "\r\n");
                    fw.write("                                         selection=\"#{" + name + "Controller." + name + "Selected}\"" + "\r\n");
                    fw.write("                                         filteredValue=\"#{" + name + "Controller." + name + "Filtered}\"" + "\r\n");
                    fw.write("                                         rowKey=\"#{item." + columnKey + "}\"" + "\r\n");
                    fw.write("                                         rows=\"15\" " + "\r\n");
                    fw.write("                                         paginator=\"true\" paginatorTemplate=\"{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}\"" + "\r\n");
                    fw.write("                                         rowsPerPageTemplate=\"5,10,15\" emptyMessage=\"#{app['info.datatableempty']}\" reflow=\"true\">" + "\r\n");
                    fw.write("                                <f:facet name=\"header\">" + "\r\n");
                    fw.write("                                    <p:outputPanel>" + "\r\n");
                    fw.write("                                        <div class=\"searchLoader\">" + "\r\n");
                    fw.write("                                 <p:graphicImage  name=\"/img/search-loader.gif\"/>" + "\r\n");
                    fw.write("                                        </div>" + "\r\n");
                    fw.write("                                        <input type=\"text\" jsf:id=\"globalFilter\" jsf:onkeyup=\"PF('widgetDataTable').filter()\" class=\"search\" placeholder=\"#{app['button.search']}\"/>" + "\r\n");
                    fw.write("                                    </p:outputPanel>" + "\r\n");
                    fw.write("                                </f:facet>" + "\r\n");
                    fw.write("                                <p:ajax event=\"rowSelect\" update=\":form:" + name + "Detaill\" oncomplete=\"PF('" + name + "Dialog').show()\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    //campos
                    for (Atributos atributos : entidad.getAtributosList()) {
                        String columna = Utilidades.letterToLower(atributos.getNombre());
                        fw.write("                                <p:column headerText=\"#{msg['field." + columna + "']}\" filterBy=\"#{item." + columna + "}\"" + "\r\n");
                        fw.write("                                          sortBy=\"#{item." + columna + "}\"  filterMatchMode=\"contains\" >" + "\r\n");
                        fw.write("                                    <h:outputText value=\"#{item." + columna + "}\" />" + "\r\n");
                        fw.write("                                </p:column>" + "\r\n");
                    }

                    fw.write("" + "\r\n");
                    fw.write("                            </p:dataTable>" + "\r\n");
                    fw.write("                            <p:dialog header=\"#{msg['header." + name + "selected']}\" widgetVar=\"" + name + "Dialog\"  width=\"290\" height=\"200\"" + "\r\n");
                    fw.write("                                      modal=\"true\" showEffect=\"fade\" hideEffect=\"fade\" resizable=\"false\" responsive=\"true\">" + "\r\n");
                    fw.write("                                <p:outputPanel id=\"" + name + "Detaill\" style=\"text-align:center;\">" + "\r\n");
                    fw.write("                                    <p:panelGrid  columns=\"2\" rendered=\"#{not empty " + name + "Controller." + name + "Selected}\" layout=\"grid\" styleClass=\"ui-panelgrid-blank\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                        <p:outputLabel value=\"#{msg['field." + columnKey+ "']}\" style=\"font-weight: bold\"/>" + "\r\n");
                    fw.write("                                        <p:outputLabel value=\"#{" + name + "Controller." + name + "Selected." + columnKey+ "}\" />" + "\r\n");
                    fw.write("                                        <p:commandButton id=\"btnedit\" value=\"#{app['button.edit']}\"" + "\r\n");
                    fw.write("                                                         class=\"btnn btnn-primary\"" + "\r\n");
                    fw.write("                                                         rendered=\"#{applicationMenu." + name + ".edit}\"" + "\r\n");
                    fw.write("                                                         icon=\"fa fa-edit Fs14 White\"  " + "\r\n");
                    fw.write("                                                         oncomplete=\"PF('" + name + "Dialog').hide()\"" + "\r\n");
                    fw.write("                                                         title=\"#{app['button.edit']}\"" + "\r\n");
                    fw.write("                                                         update=\":form:msgs\"" + "\r\n");
                    fw.write("                                                         action=\"#{" + name + "Controller.prepareEdit()}\"" + "\r\n");
                    fw.write("                                                         >" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                            <f:param name=\"" + columnKey+ "\" value=\"#{" + name + "Controller." + name + "Selected." + columnKey+ "}\"/>" + "\r\n");
                    fw.write("                                        </p:commandButton>" + "\r\n");
                    fw.write("                                        <p:commandButton class=\"btnn btnn-danger\" value=\"#{app['button.delete']}\" " + "\r\n");
                    fw.write("                                                         rendered=\"#{applicationMenu." + name + ".delete}\"" + "\r\n");
                    fw.write("                                                         icon=\"fa fa-trash-o\"" + "\r\n");
                    fw.write("                                                         process=\":form:content :form:dataTable\" action=\"#{" + name + "Controller.remove}\"  update=\":form:content\" > " + "\r\n");
                    fw.write("                                            <p:confirm header=\"#{app['dialog.delete']}\" message=\"#{app['info.doyouwantdelete']}\" icon=\"ui-icon-alert\" />" + "\r\n");
                    fw.write("                                        </p:commandButton>" + "\r\n");
                    fw.write("                                    </p:panelGrid>" + "\r\n");
                    fw.write("                                </p:outputPanel>" + "\r\n");
                    fw.write("                            </p:dialog>" + "\r\n");
                    fw.write("                        </div>" + "\r\n");
                    fw.write("                    </h:panelGroup>" + "\r\n");
                    fw.write("                    <p:confirmDialog severity=\"alert\" global=\"true\" showEffect=\"fade\" hideEffect=\"explode\">" + "\r\n");
                    fw.write("                        <p:commandButton  class=\"btnn btnn-primary\" value=\"#{app['button.yes']}\" type=\"button\" styleClass=\"ui-confirmdialog-yes\" icon=\"ui-icon-check\" />" + "\r\n");
                    fw.write("                        <p:commandButton class=\"btnn btnn-primary\" value=\"#{app['button.no']}\" type=\"button\" styleClass=\"ui-confirmdialog-no\" icon=\"ui-icon-close\" />" + "\r\n");
                    fw.write("                    </p:confirmDialog>" + "\r\n");
                    fw.write("                </h:form>" + "\r\n");
                    fw.write("                <avbravo:accesodenegado renderedcondition=\"#{!loginController.loggedIn or !applicationMenu." + name + ".list}\" />" + "\r\n");
                    fw.write("            </ui:define>" + "\r\n");
                    fw.write("        </ui:composition>" + "\r\n");
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
