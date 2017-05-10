/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.reportes;

import com.avbravo.wizardjmoordb.xhtml.*;
import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
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
public class JasperGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(JasperGenerador.class.getName());

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

                 String directorioreport  = proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator()+"reportes"+ proyectoJEE.getSeparator() + name + proyectoJEE.getSeparator();
           //     String directorioentity = proyectoJEE.getPathMainWebappPages() + Utilidades.letterToLower(entidad.getTabla()) + proyectoJEE.getSeparator();
//                procesar(name + ".xhtml", directorioentity + proyectoJEE.getSeparator() + name + ".xhtml", entidad);
                procesar(name+".jrxml", directorioreport+ proyectoJEE.getSeparator() + name+".jrxml", entidad);
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
                    fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\r\n");
                    fw.write("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"report name\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"63ad1a18-4d4e-44e1-97f8-c1744418d899\">" + "\r\n");
                    fw.write("<property name=\"ireport.zoom\" value=\"1.0\"/>" + "\r\n");
                    fw.write("<property name=\"ireport.x\" value=\"0\"/>" + "\r\n");
                    fw.write("<property name=\"ireport.y\" value=\"144\"/>" + "\r\n");
                    fw.write("<property name=\"ireport.y\" value=\"144\"/>" + "\r\n");
                    
	
	
                    fw.write("" + "\r\n");

                    // autocomplete es el campo llave
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
                            fw.write("                                                    completeMethod=\"#{" + name + "Controller." + name + "Services.complete"+Utilidades.letterToUpper(columna)+"}\"  " + "\r\n");
                            fw.write("                                                    var=\"p\"" + "\r\n");
                            //iemlabel
                            String itemLabel = "itemLabel=\"#{p." + columna + "}";
                            Integer contadorAgregados = 0;
                            //Aqui agrega atributos al itemLabel del autocomplete
//                            for (Atributos atr : entidad.getAtributosList()) {
//                                contadorAgregados++;
//                                if (!atr.getNombre().equals(columna)) {
//                                    if (contadorAgregados <= mySesion.getMaximoAutocompleteItemLabel()) {
//                                        itemLabel += " " + "#{p." + atr.getNombre() + "}";
//                                    }
//
//                                }
//
//                            }
                            itemLabel += " \"" ;
                            fw.write("                                                         " + itemLabel + "\r\n");

                            //--itemlabel
                            //  fw.write("                                                    itemLabel=\"#{p." + columna + "}\"" + "\r\n");
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
//                            fw.write("                                                <h:outputText value=\"#{p." + columna + "}\" />" + "\r\n");
//                            fw.write("                                                <h:outputText value=\"#{p.maximo}\" />" + "\r\n");
                            fw.write("                                            </h:panelGrid>" + "\r\n");
                            fw.write("                                        </f:facet>" + "\r\n");
                            fw.write("                                    </p:autoComplete>   " + "\r\n");
                            fw.write("                                </div>" + "\r\n");
                            fw.write("                            </div>" + "\r\n");
                            fw.write("" + "\r\n");
                            fw.write("" + "\r\n");
                            fw.write("" + "\r\n");

                        }//primaryKey

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
                                    fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
                                    fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
                                    fw.write("" + "\r\n");
                                    fw.write("                                    <p:inputText rendered=\"#{" + name + "Controller.writable}\" id=\"" + columna + "\" class=\"fullWidth\" value=\"#{" + name + "Controller." + name + "." + columna + "}\"" + "\r\n");
                                    fw.write("                                                 placeholder=\"#{msg['field." + columna + "']}\"  maxlength=\"55\" " + "\r\n");
                                    fw.write("                                                 required=\"true\" requiredMessage=\"#{msg['field." + columna + "']} #{app['info.required']}\"/>" + "\r\n");
                                    fw.write("                                </div>" + "\r\n");
                                    break;

                                case "Date":
                                    fw.write("                                <p:outputLabel class=\"col-xs-2 col-form-label\" value=\"#{msg['field." + columna + "']}\"/>" + "\r\n");
                                    fw.write("                                <div class=\"col-xs-4\">" + "\r\n");
                                    fw.write("" + "\r\n");
                                    fw.write("                                    <p:calendar rendered=\"#{" + name + "Controller.writable}\" id=\"" + columna + "\" class=\"fullWidth\" value=\"#{" + name + "Controller." + name + "." + columna + "}\"" + "\r\n");
                                    fw.write("                                                 placeholder=\"#{msg['field." + columna + "']}\"  maxlength=\"55\" " + "\r\n");
                                    if (!mySesion.getTimeZone().equals("")) {
                                        fw.write("                                                 timeZone=" + mySesion.getTimeZone() + "" + "\r\n");
                                    }
                                    if (!mySesion.getPatternDate().equals("")) {
                                        fw.write("                                                 pattern=" + mySesion.getPatternDate() + "" + "\r\n");
                                    }
                                    fw.write("                                                 required=\"true\" requiredMessage=\"#{msg['field." + columna + "']} #{app['info.required']}\"/>" + "\r\n");
                                    fw.write("                                </div>" + "\r\n");
                                    break;
                                default:
                                    //tipo relacionado
                                    // si es el username del loging
                                    if (atr.getTipo().equals(mySesion.getEntidadUser().getTabla()) && mySesion.getAddUserNameLogeado()) {
                                        //aqui no se genera nada

                                    } else {
                                        //generar el autocomplete del componente
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

                            }

                        }
                        if (contador.equals(mySesion.getFieldByRowView())) {
                            fw.write("                            </div>" + "\r\n");
                            contador = 0;
                        }
                    } //for
                    //si es impar la cantidad de datos y el numero de registros debe agregarse un dixv
//                    if ((fieldsAgregados.intValue() % 2 != 0 && mySesion.getFieldByRowView() % 2 == 0) || (fieldsAgregados.intValue() % 2 == 0 && mySesion.getFieldByRowView() % 2 != 0)) {
//                        fw.write("                       </div>" + "\r\n");
//
//                    }
                 
                    fw.write("</jasperReport>" + "\r\n");
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
