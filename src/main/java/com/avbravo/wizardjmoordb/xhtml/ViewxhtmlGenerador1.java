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
public class ViewxhtmlGenerador1 implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ViewxhtmlGenerador1.class.getName());

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
//                procesar(name + ".xhtml", directorioentity + proyectoJEE.getSeparator() + name + ".xhtml", entidad);
                procesar("view.xhtml", directorioentity + proyectoJEE.getSeparator() + "view.xhtml", entidad);
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
                    fw.write("                <f:view>" + "\r\n");
                    fw.write("                    <h:form id=\"form\" prependId=\"false\" rendered=\"#{menuBeans." + name + ".consultar}\">" + "\r\n");
                    fw.write("                        <p:growl  id=\"growl\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <b:panel id=\"panel\" look=\"primary\" title=\"#{form['" + name + ".records']}\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");
                    // autocomplete es el campo llave

                    for (Atributos atributos : entidad.getAtributosList()) {
                        if (atributos.getEsPrimaryKey()) {
                            String columna = Utilidades.letterToLower(atributos.getNombre());

                            fw.write("                                <p:outputLabel  rendered=\"#{!" + name + "Controller.nuevoregistro}\" value=\"#{msg." + columna + "}\" for=\"" + columna + "  \" />" + "\r\n");
                            fw.write("                                <p:autoComplete  id=\"" + columna + "\"  scrollHeight=\"250\"   dropdown=\"true\"    size=\"45\" " + "\r\n");
                            fw.write("                                                 rendered=\"#{!" + name + "Controller.nuevoregistro}\" " + "\r\n");
                            fw.write("                                                 value=\"#{" + name + "Controller.selected}\"  " + "\r\n");
                            fw.write("                                                 completeMethod=\"#{" + name + "Controller." + name + "Services.complete" + Utilidades.letterToUpper(columna) + "}\"  " + "\r\n");
                            fw.write("                                                 var=\"p\"  " + "\r\n");
                            fw.write("                                                 itemLabel=\"#{p." + columna + "}\"  itemValue=\"#{p}\"" + "\r\n");
                            fw.write("                                                 forceSelection=\"true\"> " + "\r\n");
                            fw.write("                                    <f:converter binding=\"#{" + name + "Converter}\" />" + "\r\n");
                            fw.write("                                    <p:ajax event=\"itemSelect\" listener=\"#{" + name + "Controller.handleSelect}\" update=\":form:panel,:form:growl\" />  " + "\r\n");
                            fw.write("                                    <f:facet name=\"itemtip\">" + "\r\n");
                            fw.write("                                        <h:panelGrid columns=\"2\" cellpadding=\"5\">" + "\r\n");
                            for (Atributos atr : entidad.getAtributosList()) {
                                fw.write("                                           <h:outputText value=\"#{p." + atr.getNombre() + "}\" />" + "\r\n");
                            }

                            fw.write("                                        </h:panelGrid>" + "\r\n");
                            fw.write("                                    </f:facet>" + "\r\n");
                            fw.write("                                </p:autoComplete> " + "\r\n");

                            //genera el campo para ingresar el valor si es String
                            //de otro modo indica que es Integer autoincrementable no es necesario generarlo
                            if (atributos.getTipo().equals("String")) {
                                fw.write("                                <p:outputLabel  rendered=\"#{" + name + "Controller.nuevoregistro}\" value=\"#{msg." + columna + "}\" for=\"" + columna + "1\" />" + "\r\n");
                                fw.write("                                <b:inputText rendered=\"#{" + name + "Controller.nuevoregistro}\" class=\"form-control\" id=\"" + columna + "1\"  value=\"#{" + name + "Controller." + name + "." + columna + "}\" title=\"#{msg." + columna + "}\" required=\"true\" " + "\r\n");
                                fw.write("                                              requiredMessage=\"#{msg." + columna + "} #{app['info.required']}\"/> " + "\r\n");
                            }

                        }

                    }
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

                    for (Atributos atr : entidad.getAtributosList()) {
                        if (!atr.getEsPrimaryKey()) {
                            String columna = Utilidades.letterToLower(atr.getNombre());
//                            if (mySesion.getAddFechaSystema() && !columnDate.equals("")) {
//                                fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnDate) + "(fechasServices.getFechaActual());" + "\r\n");
//                            }
//                            //genero el username desde el login
//                            if (mySesion.getAddUserNameLogeado() && tieneUsername) {
//                                fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "(loginBean.getUsuarios());" + "\r\n");
//                            }

                            switch (atr.getTipo()) {
                                case "String":
                                    fw.write("" + "\r\n");
                                    fw.write("                               <h:outputText value=\"#{msg." + atr.getNombre() + "}\" />" + "\r\n");
                                    fw.write("" + "\r\n");
                                    fw.write("                                <b:inputText  class=\"form-control\" id=\"" + columna + "\"  value=\"#{" + name + "Controller." + name + "." + columna + "}\" title=\"#{msg." + columna + "}\" required=\"true\" " + "\r\n");
                                    fw.write("                                              requiredMessage=\"#{msg." + columna + "} #{app['info.required']}\"/> " + "\r\n");

                                    break;
                                case "Integer":
                                case "Double":
                                case "double":
                                    fw.write("" + "\r\n");
                                    fw.write("                               <h:outputText value=\"#{msg." + atr.getNombre() + "}\" />" + "\r\n");
                                    fw.write("" + "\r\n");
                                    fw.write("                                <b:inputText  class=\"form-control\" id=\"" + columna + "\"  value=\"#{" + name + "Controller." + name + "." + columna + "}\" title=\"#{msg." + columna + "}\" required=\"true\" " + "\r\n");
                                    fw.write("                                              requiredMessage=\"#{msg." + columna + "} #{app['info.required']}\"/> " + "\r\n");

                                    break;
                                case "Date":
                                    fw.write("" + "\r\n");
                                    fw.write("                               <h:outputText value=\"#{msg." + atr.getNombre() + "}\" />" + "\r\n");
                                    fw.write("" + "\r\n");
                                    
                                    fw.write("                               <p:calendar value=\"#{" + name + "Controller." + name + "." + columna + "}\" id=\"" + columna + "\" size=\"10\" pattern=\"dd/MM/yyyy\" required=\"false\"/> " + "\r\n");
                                    break;
                                default:
                                    //tipo relacionado
                                    // si es el username del loging
                                    if (atr.getTipo().equals(mySesion.getEntidadUser().getTabla()) && mySesion.getAddUserNameLogeado()) {
                                        //aqui no se genera nada

                                    } else {
                                        //generar el autocomplete del componente
                                        String nameRelational = Utilidades.letterToLower(atr.getTipo());
                                        fw.write("" + "\r\n");
                                        fw.write("                               <h:outputText value=\"#{msg." + atr.getNombre() + "}\" />" + "\r\n");
                                        fw.write("" + "\r\n");
                                        fw.write("                               <p:selectOneMenu  id=\"" + columna + "\" " + "\r\n");
                                        fw.write("                                              filter=\"true\" filterMatchMode=\"startsWith\" effect=\"fade\"" + "\r\n");
                                        fw.write("                                              value=\"#{" + name + "Controller." + name + "." + columna + "}\"" + "\r\n");
                                        fw.write("                                              required=\"true\" requiredMessage=\"#{msg." + columna + "} #{app['info.required']}\">" + "\r\n");
                                        fw.write("                                   <!-- TODO: update below reference to list of available items-->" + "\r\n");
                                        fw.write("                                   <f:converter binding=\"#{" + nameRelational + "Converter}\" />" + "\r\n");
                                        fw.write("                                   <f:selectItems value=\"#{" + nameRelational + "Search.items}\"" + "\r\n");
                                        fw.write("                                                  var=\"p\"" + "\r\n");
                                        fw.write("                                                  itemValue=\"#{p}\"" + "\r\n");
                                        fw.write("                                                  itemLabel=\"#{p." + columna + "}\"/>" + "\r\n");
                                        fw.write("                                 </p:selectOneMenu>" + "\r\n");
                                    }

                            }

                        }

                    }

//                    fw.write("" + "\r\n");
//                    fw.write("                                <p:outputLabel value=\"#{msg.activo}\" for=\"activo\" />" + "\r\n");
//                    fw.write("                                <b:selectOneMenu id=\"activo\" value=\"#{nivel1Controller.nivel1.activo}\"                     " + "\r\n");
//                    fw.write("                                                 required=\"true\"" + "\r\n");
//                    fw.write("                                                 requiredMessage=\"#{msg.activo} #{app['info.notnull']}\">" + "\r\n");
//                    fw.write("                                    <f:selectItem itemLabel=\"#{app['boton.yes']}\" itemValue=\"si\" />" + "\r\n");
//                    fw.write("                                    <f:selectItem itemLabel=\"#{app['boton.no']}\" itemValue=\"no\" />" + "\r\n");
//                    fw.write("                                </b:selectOneMenu>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                            </h:panelGrid>" + "\r\n");
                    fw.write("                            <f:facet class=\"panel-footer\" name=\"footer\">" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-primary\" value=\"#{app['boton.new']}\" update=\"panel\" process=\"@this\"" + "\r\n");
                    fw.write("                                                 actionListener=\"#{" + name + "Controller.reset}\"  style=\"margin-right:20px;\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton  value=\"#{app['boton.query']}\" class=\"btn btn-primary\" icon=\"ui-icon-refresh\" update=\"panel\" " + "\r\n");
                    fw.write("                                                  process=\"@this\" actionListener=\"#{" + name + "Controller.query()}\"  style=\"margin-right:20px;\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:button outcome=\"" + name + "search\" class=\"btn btn-primary\" value=\"#{app['boton.search']}\"/>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton  value=\"#{app['boton.update']}\" action=\"#{" + name + "Controller.edit()}\" class=\"btn btn-primary\" " + "\r\n");
                    fw.write("                                                  rendered=\"#{" + name + "Controller.encontrado and menuBeans." + name + ".editar}\"" + "\r\n");
                    fw.write("                                                  id=\"button_edit\"  update=\"growl\"    icon=\"ui-icon-pencil\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-primary\" id=\"button_print\"  " + "\r\n");
                    fw.write("                                                 value=\"#{app['boton.print']}\"" + "\r\n");
                    fw.write("                                                 action=\"#{" + name + "Controller.imprimir()}\"" + "\r\n");
                    fw.write("                                                 rendered=\"#{" + name + "Controller.encontrado}\"" + "\r\n");
                    fw.write("                                                 icon=\"ui-icon-print\" ajax=\"false\" />" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-primary\" id=\"button_save\"" + "\r\n");
                    fw.write("                                                 value=\"#{app['boton.save']}\"" + "\r\n");
                    fw.write("                                                 icon=\"ui-icon-disk\"" + "\r\n");
                    fw.write("                                                 update=\"form,growl\"" + "\r\n");
                    fw.write("                                                 rendered=\"#{" + name + "Controller.nuevoregistro and menuBeans." + name + ".crear}\"" + "\r\n");
                    fw.write("                                                 title=\"#{app['boton.save']}\"" + "\r\n");
                    fw.write("                                                 action=\"#{" + name + "Controller.save()}\"/>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <p:commandButton class=\"btn btn-primary\"" + "\r\n");
                    fw.write("                                                 icon=\"ui-icon-trash\"" + "\r\n");
                    fw.write("                                                 update=\"form, growl\"" + "\r\n");
                    fw.write("                                                 value=\"#{app['boton.delete']}\"" + "\r\n");
                    fw.write("                                                 rendered=\"#{" + name + "Controller.encontrado and menuBeans." + name + ".eliminar}\"" + "\r\n");
                    fw.write("                                                 actionListener=\"#{" + name + "Controller.delete}\"  >" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                    <p:confirm header=\"#{app['dialog.delete']}\" message=\"#{app['info.doyouwantdelete']}\" icon=\"ui-icon-alert\" />" + "\r\n");
                    fw.write("                                </p:commandButton>" + "\r\n");
                    fw.write("                            </f:facet>" + "\r\n");
                    fw.write("                        </b:panel>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                        <p:confirmDialog global=\"true\" showEffect=\"fade\" hideEffect=\"explode\">" + "\r\n");
                    fw.write("                            <p:commandButton value=\"#{app['boton.yes']}\" type=\"button\" styleClass=\"ui-confirmdialog-yes\" icon=\"ui-icon-check\" />" + "\r\n");
                    fw.write("                            <p:commandButton value=\"#{app['boton.no']}\" type=\"button\" styleClass=\"ui-confirmdialog-no\" icon=\"ui-icon-close\" />" + "\r\n");
                    fw.write("                        </p:confirmDialog>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                    </h:form>" + "\r\n");
                    fw.write("                    <h:form rendered=\"#{!menuBeans." + name + ".consultar}\">" + "\r\n");
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
