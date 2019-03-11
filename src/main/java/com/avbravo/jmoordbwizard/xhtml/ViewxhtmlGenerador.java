/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.xhtml;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoJEE;
import com.avbravo.jmoordbwizard.beans.Atributos;
import com.avbravo.jmoordbwizard.beans.Entidad;
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

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class ViewxhtmlGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ViewxhtmlGenerador.class.getName());
    FileWriter fw;
    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    Boolean esEmbedded = false;
    Boolean esEmbeddedList = false;
    Boolean esReferenced = false;
    Boolean esRefefencedList = false;
    Integer nivel = -1;
    String nameEntityMain ="";
    // <editor-fold defaultstate="collapsed" desc="get/set">

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
// </editor-fold> 

    /**
     * Creates a new instance of Facade
     */
    // <editor-fold defaultstate="collapsed" desc="generar">  
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
            for (Entidad entidad : mySesion.getEntidadList()) {
                String name = Utilidades.letterToLower(entidad.getTabla());

                String directorioentity = proyectoJEE.getPathMainWebappPages() + Utilidades.letterToLower(entidad.getTabla()) + proyectoJEE.getSeparator();

                procesar("view.xhtml", directorioentity + proyectoJEE.getSeparator() + "view.xhtml", entidad);
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="procesar">  

    private Boolean procesar(String archivo, String ruta, Entidad entidad) {
        try {
            setNivel(-1);
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo, entidad);
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
    // <editor-fold defaultstate="collapsed" desc="crearFile">  
    private Boolean crearFile(String path, String archivo, Entidad entidad) throws IOException {
        try {

            String name = Utilidades.letterToLower(entidad.getTabla());
            this.nameEntityMain = Utilidades.letterToLower(entidad.getTabla());
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
                try {
                    fw = new FileWriter(file);
                    encabezado(name);
                    setNivel(-1);
                    autocompletePK(name, entidad);
                    body(name, entidad, "");

                    terminal(name);
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="body"> 

    private void body(String name, Entidad entidad, String nameFather) {
        try {
            esEmbedded = false;
            esEmbeddedList=false;
            esReferenced=false;
            esRefefencedList=false;
            // autocomplete es el campo llave
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

            }
//getFieldByRowView()
            Integer contador = 0;
            Integer fieldsAgregados = 0;
            Integer contadorfieldByRowView = 0;
            for (Atributos atr : entidad.getAtributosList()) {

                if (!atr.getEsPrimaryKey()) {
                    contador++;
                    fieldsAgregados++;
                    if (contador == 1) {
                        fw.write("                            <div class=\"form-group row\" >" + "\r\n");
                    }
                    String columna = Utilidades.letterToLower(atr.getNombre());
                    contadorfieldByRowView++;
                    switch (atr.getTipo()) {
                        case "Integer":
                        case "Double":
                        case "double":
                        case "String":
                            inputText(name, columna, nameFather);
                            break;

                        case "Date":
                            calendar(name, columna,nameFather);
                            break;
                        default:
                            //tipo relacionado
                            // si es el username del loging
                            if (atr.getTipo().equals(mySesion.getEntidadUser().getTabla()) && mySesion.getAddUserNameLogeado()) {
                                //aqui no se genera nada

                            } else {
                                //generar el autocomplete del componente
                                selecOneMenu(atr, name, columna);

                            }

                    }

                }
                if (contador.equals(mySesion.getFieldByRowView())) {
                      
                    fw.write("                            </div>" + "\r\n");
                    contador = 0;
                }
                //verifica si existe algun embedded o referenced
                if (atr.getEsEmbedded()) {
                    esEmbedded = true;
                    if (atr.getEsListEmbedded()) {
                        esEmbeddedList = true;
                    }
                } else {
                    if (atr.getEsReferenced()) {
                        esReferenced = true;
                        if (atr.getEsListReferenced()) {
                            esRefefencedList = true;
                        }
                    }
                }

            } //for

//            if (Utilidades.isImpar(fieldsAgregados)) {
//                System.out.println("=====================================");
//                System.out.println("---->  "+ name+"-------> es impar "+fieldsAgregados);
//                System.out.println("=====================================");
//                fw.write("                        </div> " + "\r\n");
//            }

            //Dibujar los tabview
            if (esEmbedded || esRefefencedList) {
                tabView(entidad);

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("body() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="tabView">  

    private void tabView(Entidad entidad) {
        try {
            Integer c = 0;
            fw.write("			   <div class=\"row\"  style=\"padding-left: 15px;padding-right: 15px; margin-bottom:10px\">" + "\r\n");
            fw.write("                                <div class=\"col-md-12\" >" + "\r\n");
            fw.write("                                    <ul class=\"nav nav-tabs\" role=\"tablist\">" + "\r\n");
            //Aqui recorrer y colocar los form-tab-2

            //pagina 3
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsEmbedded()|| (a.getEsReferenced()&& a.getEsListReferenced())) {
                    if (c == 0) {
                        fw.write("                                        <li class=\"active\"><a href=\"#form-tab-" + Utilidades.letterToLower(a.getNombre()) + "\" class=\"form-tab\" role=\"tab\" data-toggle=\"tab\">#{msg['tab." + Utilidades.letterToLower(a.getNombre()) + "']}</a></li>" + "\r\n");
                    } else {
                        fw.write("                                        <li><a href=\"#form-tab-" + Utilidades.letterToLower(a.getNombre()) + "\" class=\"form-tab\" role=\"tab\" data-toggle=\"tab\">#{msg['tab." + Utilidades.letterToLower(a.getNombre()) + "']}</a></li>" + "\r\n");
                    }
                    c++;
                }
            }

            fw.write("                                    </ul>" + "\r\n");
            fw.write("                                    <div class=\"tab-content\">" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("" + "\r\n");
            // dibujar los paneles
            c = 0;
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsEmbedded()|| (a.getEsReferenced()&& a.getEsListReferenced())) {
                    if (c == 0) {
                        fw.write("                                        <div class=\"tab-pane active\" id=\"form-tab-" + Utilidades.letterToLower(a.getNombre()) + "\" style=\"padding: 20px;\">" + "\r\n");
                        tabContenido(a, entidad);
                        fw.write("                                        </div>" + "\r\n");
                    } else {
                        fw.write("                                        <div class=\"tab-pane active\" id=\"form-tab-" + Utilidades.letterToLower(a.getNombre()) + "\" style=\"padding: 20px;\">" + "\r\n");
                        tabContenido(a, entidad);
                        fw.write("                                        </div>" + "\r\n");
                    }
                    c++;
                }
            }

            fw.write("" + "\r\n");
            fw.write("                                    </div>" + "\r\n");//content
            fw.write("                                </div>" + "\r\n");//col md-12
            fw.write("                            </div>" + "\r\n");//class row
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tabView() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="tabContenido">  

    private void tabContenido(Atributos a, Entidad entidad) {
        try {

            if (a.getEsEmbedded()) {
                if (!a.getEsListEmbedded()) {
                    tabContenidoEmbeddedSimple(a, entidad);
                } else {
                    if (a.getEsListEmbedded()) {
                        tabContenidoEmbeddedList(a, entidad);
                    }
                }
            } else {
                if (a.getEsReferenced()&& a.getEsListReferenced()) {
                    tabContenidoReferencedList(a, entidad);
                }
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tabContenido() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="tabContenidoEmbeddedSimple"> 

    private void tabContenidoEmbeddedSimple(Atributos a, Entidad entidad) {
        try {
            String nameRelational = Utilidades.letterToLower(a.getTipo());
            Entidad entidadRelational = new Entidad();
            String columnKeyRelational = "";
            for (Entidad entidad2 : mySesion.getEntidadList()) {
                String name2 = Utilidades.letterToLower(entidad2.getTabla());
                if (nameRelational.equals(name2)) {
                    entidadRelational = entidad2;
                }
            }

            //-----
            String name = Utilidades.letterToLower(entidadRelational.getTabla());
            String nameFather = Utilidades.letterToLower(entidad.getTabla());
            inputTextPKEmbedded(name, entidadRelational, nameFather);
            body(name, entidadRelational, nameFather);
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tabContenidoEmbeddedSimple() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="tabContenidoEmbeddedList"> 
    private void tabContenidoEmbeddedList(Atributos a, Entidad entidad) {
        try {
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tabContenidoEmbeddedSimple() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="tabContenidoReferencedList"> 
    private void tabContenidoReferencedList(Atributos a, Entidad entidad) {
        try {
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tabContenidoReferencedList() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 
    // <editor-fold defaultstate="collapsed" desc="inputText">  

    private void inputText(String name, String columna, String nameFather) {
        try {
            fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
            fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
            fw.write("" + "\r\n");
            if (nameFather.isEmpty() || nameFather.equals("")) {
                fw.write("                                    <p:inputText rendered=\"#{" + name + "Controller.writable}\" id=\"" + columna + "\" class=\"fullWidth\" value=\"#{" + name + "Controller." + name + "." + columna + "}\"" + "\r\n");
            } else {
                fw.write("                                    <p:inputText rendered=\"#{" + this.nameEntityMain + "Controller.writable}\" id=\""+nameFather+"_" + columna + "\" class=\"fullWidth\" value=\"#{" + this.nameEntityMain + "Controller." + nameFather + "." + name + "." + columna + "}\"" + "\r\n");
            }

            fw.write("                                                 placeholder=\"#{msg['field." + columna + "']}\"  maxlength=\"55\" " + "\r\n");
            fw.write("                                                 required=\"true\" requiredMessage=\"#{msg['field." + columna + "']} #{app['info.required']}\"/>" + "\r\n");
            fw.write("                                </div>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("inputText() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="calendar">  

    private void calendar(String name, String columna,String nameFather) {
        try {
            fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
            fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
            fw.write("" + "\r\n");
            if(nameFather.isEmpty() || nameFather.equals("")){
                fw.write("                                    <p:calendar rendered=\"#{" + name + "Controller.writable}\" id=\"" + columna + "\" class=\"fullWidth\" value=\"#{" + name + "Controller." + name + "." + columna + "}\"" + "\r\n");
            }else{
                fw.write("                                    <p:calendar rendered=\"#{" + nameFather + "Controller.writable}\" id=\"" +nameFather+"_"+ columna + "\" class=\"fullWidth\" value=\"#{" + name + "Controller." + name + "." + columna + "}\"" + "\r\n");
            }
            
            fw.write("                                                 placeholder=\"#{msg['field." + columna + "']}\"  maxlength=\"55\" " + "\r\n");
            if (!mySesion.getTimeZone().equals("")) {
                fw.write("                                                 timeZone=\"" + mySesion.getTimeZone() + "\"" + "\r\n");
            }
            if (!mySesion.getPatternDate().equals("")) {
                fw.write("                                                 pattern=\"" + mySesion.getPatternDate() + "\"" + "\r\n");

            }           
                                                
            fw.write("                                                 selectOtherMonths=\"true\"" + "\r\n");
            fw.write("                                                navigator=\"true\"" + "\r\n");
            fw.write("                                                 required=\"true\" requiredMessage=\"#{msg['field." + columna + "']} #{app['info.required']}\"/>" + "\r\n");
            fw.write("                                </div>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("inputText() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 

    /**
     * Referenciados con isList == false
     */
// <editor-fold defaultstate="collapsed" desc="selectOneMenu">  
    private void selecOneMenu(Atributos atr, String name, String columna) {
        try {
            //Solo crea el selectOneMenu para los referenciados que no sean un List
            if (atr.getEsReferenced()&& !atr.getEsListReferenced()) {

                String nameRelational = Utilidades.letterToLower(atr.getTipo());
                String columnKeyRelational = "";
                for (Entidad entidad2 : mySesion.getEntidadList()) {
                    String name2 = Utilidades.letterToLower(entidad2.getTabla());
                    if (nameRelational.equals(name2)) {
                        for (Atributos a : entidad2.getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                columnKeyRelational = a.getNombre();
                            }
                        }
                    }
                }
                fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
                fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                                    <b:selectOneMenu rendered=\"#{" + name + "Controller.writable}\"" + "\r\n");
                fw.write("                                                     class=\"fullWidth required ui-selectonemenu-label ui-inputfield ui-corner-all\"" + "\r\n");
                fw.write("                                                     id=\"" + columna + "\" value=\"#{" + name + "Controller." + name + "." + columna + "}\"  " + "\r\n");
                fw.write("                                                  required=\"true\"   requiredMessage=\"#{msg['field." + columnKeyRelational + "']} #{app['info.required']}\"" + "\r\n");
                fw.write("                                                     >" + "\r\n");
                fw.write("                                        <!-- TODO: update below reference to list of available items-->" + "\r\n");
                fw.write("                                        <f:selectItem itemLabel=\"#{" + name + "Controller." + name + "." + columna + "}\" " + "\r\n");
                fw.write("                                                      itemValue=\"#{" + name + "Controller." + name + "." + columna + "}\"/>" + "\r\n");
                fw.write("                                        <f:selectItems value=\"#{" + name + "Controller." + columna + "List}\"" + "\r\n");
                fw.write("                                                       var=\"item\"" + "\r\n");
                fw.write("                                                       itemValue=\"#{item}\"" + "\r\n");

                fw.write("                                                       itemLabel=\"#{item." + columnKeyRelational + "}\"" + "\r\n");
                fw.write("                                                       />" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("                                    </b:selectOneMenu>" + "\r\n");

                fw.write("                                </div>" + "\r\n");
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("selecOneMenu() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="terminal">  
    private void terminal(String name) {
        try {
            fw.write("                    </div> " + "\r\n");
            fw.write("                    </h:panelGroup>" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                    <p:confirmDialog global=\"true\" showEffect=\"fade\" hideEffect=\"explode\">" + "\r\n");
            fw.write("                        <p:commandButton value=\"#{app['button.yes']}\" type=\"button\" styleClass=\"ui-confirmdialog-yes\" icon=\"ui-icon-check\" />" + "\r\n");
            fw.write("                        <p:commandButton value=\"#{app['button.no']}\" type=\"button\" styleClass=\"ui-confirmdialog-no\" icon=\"ui-icon-close\" />" + "\r\n");
            fw.write("                    </p:confirmDialog>" + "\r\n");
            fw.write("                </h:form>" + "\r\n");
            fw.write("                <avbravo:accesodenegado renderedcondition=\"#{!loginController.loggedIn or !applicationMenu." + name + ".query}\" />" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("            </ui:define>" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        </ui:composition>" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    </body>" + "\r\n");
            fw.write("</html>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("terminal() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="encabezado">  
    private void encabezado(String name) {
        try {

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
            fw.write("                    #{msg['titleview." + name + "']}" + "\r\n");
            fw.write("                    <small> </small>" + "\r\n");
            fw.write("                </h1>" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("            </ui:define>" + "\r\n");
            fw.write("            <!--" + "\r\n");
            fw.write("          Contenido" + "\r\n");
            fw.write("            -->" + "\r\n");
            fw.write("            <ui:define name=\"content\">" + "\r\n");
            fw.write("                <h:form id=\"form\"  rendered=\"#{loginController.loggedIn and applicationMenu." + name + ".query}\"    onkeypress=\"if (event.keyCode == 13) { return false; }\"  >" + "\r\n");
            fw.write("                    <h:panelGroup id=\"content\" layout=\"block\">           " + "\r\n");
            fw.write("                        <p:messages id=\"msgs\"/>" + "\r\n");
            fw.write("                        <div class=\"row form-header-2\" style=\"padding-top: 5px;\">" + "\r\n");
            fw.write("                            <div class=\"col-md-12 record-status-buttons\">   " + "\r\n");
            fw.write("                          " + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                                <p:commandButton  icon=\"fa fa-plus\" class=\"btnn btnn-primary\" immediate=\"true\"" + "\r\n");
            fw.write("                                                  title=\"#{app['button.new']}\"  value=\"#{app['button.new']}\" " + "\r\n");
            fw.write("                                                  action=\"#{" + name + "Controller.prepareNew()}\" update=\":form:content\" /> " + "\r\n");
            fw.write("                                <p:commandButton class=\"btnn btnn-primary\" immediate=\"true\" icon=\"fa fa-folder-open-o\" title=\"#{app['button.query']}\" value=\"#{app['button.query']}\"  " + "\r\n");
            fw.write("                                                 action=\"#{" + name + "Controller.open()}\" update=\":form:content :form:autocomplete\" /> " + "\r\n");
            fw.write("                                <p:commandButton rendered=\"#{" + name + "Controller.writable and !" + name + "Controller.found and applicationMenu." + name + ".create}\"" + "\r\n");
            fw.write("                                                 class=\"btnn btnn-success\" icon=\"fa fa-floppy-o\" title=\"#{app['button.save']}\"" + "\r\n");
            fw.write("                                                 value=\"#{app['button.save']}\"  action=\"#{" + name + "Controller.save()}\"  update=\":form:content\" /> " + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                                <p:commandButton rendered=\"#{" + name + "Controller.found and applicationMenu." + name + ".edit}\" " + "\r\n");
            fw.write("                                                 icon=\"fa fa-edit Fs14 White\" class=\"btnn btnn-success\" title=\"#{app['button.edit']}\"  value=\"#{app['button.edit']}\" process=\":form:content\" action=\"#{" + name + "Controller.edit()}\" update=\":form:content\" /> " + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                                <p:commandButton rendered=\"#{" + name + "Controller.found and applicationMenu." + name + ".delete}\" " + "\r\n");
            fw.write("                                                 icon=\"fa fa-trash-o\" class=\"btnn btnn-danger\" title=\"#{app['button.delete']}\" value=\"#{app['button.delete']}\" process=\":form:content\" action=\"#{" + name + "Controller.delete()}\"  update=\":form:content\" > " + "\r\n");
            fw.write("                                    <p:confirm header=\"#{app['dialog.delete']}\" message=\"#{app['info.doyouwantdelete']}\" icon=\"ui-icon-alert\" />" + "\r\n");
            fw.write("                                </p:commandButton>" + "\r\n");
            fw.write("                                <p:commandButton rendered=\"#{" + name + "Controller.found}\" " + "\r\n");
            fw.write("                                                 class=\"btnn btnn-primary\" icon=\"fa fa-print\" title=\"#{app['button.print']}\" value=\"#{app['button.print']}\" process=\":form:content\" action=\"#{" + name + "Controller.print()}\" " + "\r\n");
            fw.write("                                                 ajax=\"false\" " + "\r\n");
            fw.write("                                                 update=\":form:content\" /> " + "\r\n");
            fw.write("" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                                <p:link class=\"fa fa-search btnn btnn-primary\" rendered=\"#{applicationMenu." + name + ".list}\" title=\"#{app['button.search']}\" value=\"#{app['button.search']}\" outcome=\"/pages/" + name + "/list\"/>" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                            </div>" + "\r\n");
            fw.write("                        </div>" + "\r\n");
            fw.write("                        <div class=\"row form-body\">" + "\r\n");
            fw.write("" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }

    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="autocompletePK">  
    private void autocompletePK(String name, Entidad entidad) {
        try {
            setNivel(getNivel() + 1);
            for (Atributos atributos : entidad.getAtributosList()) {
                if (atributos.getEsPrimaryKey()) {
                    String columna = Utilidades.letterToLower(atributos.getNombre());

                    fw.write("                           <div class=\"form-group row\">" + "\r\n");
                    fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label \" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
                    fw.write("                                    <p:remoteCommand  update=\":form:content\"" + "\r\n");
                    fw.write("                                                      name=\"remote" + columna + "\" actionListener=\"#{" + name + "Controller.verifyNew()}\"/>" + "\r\n");
                    fw.write("                                    <p:inputText id=\"name\" rendered=\"#{!" + name + "Controller.forsearch}\" " + "\r\n");
                    fw.write("                                                 placeholder=\"#{app['info.ingresenuevodato']}\"" + "\r\n");
                    fw.write("                                                 value=\"#{" + name + "Controller." + name + "." + columna + "}\" " + "\r\n");
                    fw.write("                                                 class=\"required form-name-input fullWidth\" " + "\r\n");
                    fw.write("                                                 onkeypress=\"if (event.keyCode == 13) {" + "\r\n");
                    fw.write("                                                             remote" + columna + "();" + "\r\n");
                    fw.write("                                                             return false;" + "\r\n");
                    fw.write("                                                         }\"" + "\r\n");
                    fw.write("                                                 />" + "\r\n");
                    fw.write("                    " + "\r\n");
                    fw.write("                                    <p:autoComplete rendered=\"#{" + name + "Controller.forsearch}\" scrollHeight=\"250\"   dropdown=\"false\"  size=\"45\"  " + "\r\n");
                    fw.write("                                                    id=\"autocomplete\"" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                                    placeholder=\"#{app['info.ingresedatobuscar']}\"" + "\r\n");
                    fw.write("                                                    emptyMessage=\"#{app['info.nohayregistros']}\" " + "\r\n");
                    fw.write("                                                    title=\"#{app['info.by']} #{msg['field." + columna + "']}\"" + "\r\n");
                    fw.write("                                                    label=\"#{app['info.by']} #{msg['field." + columna + "']}\"" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                                                    alt=\"#{app['info.searchby']} #{msg['field." + columna + "']}\"" + "\r\n");
                    fw.write("                                                    value=\"#{" + name + "Controller." + name + "Selected}\"  " + "\r\n");
                    fw.write("                                                    completeMethod=\"#{" + name + "Controller." + name + "Services.complete" + Utilidades.letterToUpper(columna) + "}\"  " + "\r\n");
                    fw.write("                                                    var=\"p\"" + "\r\n");
                    //iemlabel
                    String itemLabel = "itemLabel=\"#{p." + columna + "}";
                    Integer contadorAgregados = 0;
                    itemLabel += " \"";
                    fw.write("                                                         " + itemLabel + "\r\n");

                    fw.write("                                                    itemValue=\"#{p}\" forceSelection=\"true\"> " + "\r\n");

                    fw.write("                                        <f:converter binding=\"#{" + name + "Converter}\"/>" + "\r\n");
                    fw.write("                                        <p:ajax event=\"itemSelect\" listener=\"#{" + name + "Controller.query}\"" + "\r\n");
                    fw.write("                                                update=\" :form:msgs,:form:content\" />  " + "\r\n");
                    fw.write("                                        <f:facet name=\"itemtip\">" + "\r\n");
                    fw.write("                                            <h:panelGrid columns=\"1\" cellpadding=\"5\">" + "\r\n");
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
                    fw.write("                                            </h:panelGrid>" + "\r\n");
                    fw.write("                                        </f:facet>" + "\r\n");
                    fw.write("                                    </p:autoComplete>   " + "\r\n");
                    fw.write("                                </div>" + "\r\n");
                    fw.write("                            </div>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    break;
                }//primaryKey

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("autocompletePK() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="inputTextPKEmbedded">  
    private void inputTextPKEmbedded(String name, Entidad entidad, String nameFather) {
        try {
            String columna = "";
            for (Atributos atributos : entidad.getAtributosList()) {
                if (atributos.getEsPrimaryKey()) {
                    columna = Utilidades.letterToLower(atributos.getNombre());
                }
            }
            fw.write("                           <div class=\"form-group row\">" + "\r\n");
            fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
            fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                                    <p:inputText rendered=\"#{" + nameEntityMain + "Controller.writable}\" id=\"" + name + "_" + columna + "\" class=\"fullWidth\" value=\"#{" + nameEntityMain+ "Controller." + nameFather + "." + name + "." + columna + "}\"" + "\r\n");
            fw.write("                                                 placeholder=\"#{msg['field." + columna + "']}\"  maxlength=\"55\" " + "\r\n");
            fw.write("                                                 required=\"true\" requiredMessage=\"#{msg['field." + columna + "']} #{app['info.required']}\"/>" + "\r\n");
            fw.write("                                </div>" + "\r\n");
            fw.write("                            </div>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("inputText() " + e.getLocalizedMessage());
        }
    }// </editor-fold> 
}
