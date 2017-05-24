/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.controller;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoEJB;
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
public class ControllerGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ControllerGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    ProyectoEJB proyectoEJB;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
            for (Entidad e : mySesion.getEntidadList()) {
                procesar(e, e.getTabla(), proyectoJEE.getPathController() + e.getTabla() + "Controller.java");
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(Entidad entidad, String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(entidad, ruta, archivo);
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
    private Boolean crearFile(Entidad entidad, String path, String archivo) {
        try {

            String ruta = path;
            File file = new File(ruta);
            BufferedWriter bw;
            if (file.exists()) {
                // El fichero ya existe
            } else {
                String nameClass = Utilidades.letterToUpper(entidad.getTabla());
                String nameEntity = Utilidades.letterToLower(entidad.getTabla());
                String primaryKey = "";
                String primaryKeyUpper = "";
                Boolean esInteger = false;
                String entity = Utilidades.letterToLower(archivo);
                for (Atributos a : entidad.getAtributosList()) {
                    if (a.getEsPrimaryKey()) {
                        primaryKey = a.getNombre();
                        esInteger = a.getTipo().equals("Integer");
                    }
                }
                primaryKeyUpper = Utilidades.letterToUpper(primaryKey);
                // El fichero no existe y hay que crearlo
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.close();
//      bw.write("Acabo de crear el fichero de texto.");

                File file2 = new File(ruta);
                //Creamos un objeto para escribir caracteres en el archivo de prueba
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write("/*" + "\r\n");
                    fw.write("* To change this license header, choose License Headers in Project Properties." + "\r\n");
                    fw.write("* To change this template file, choose Tools | Templates" + "\r\n");
                    fw.write(" * and open the template in the editor." + "\r\n");
                    fw.write("*/" + "\r\n");
                    fw.write("package " + proyectoJEE.getPaquete() + ".controller;" + "\r\n");
                    fw.write("" + "\r\n");
fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"imports\">" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".entity.*; " + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".ejb.*; " + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".datamodel.*; " + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".services.*; " + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".interfaces.*; " + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".util.*; " + "\r\n");
                    fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");
                    fw.write("import com.avbravo.avbravoutils.printer.Printer;" + "\r\n");
                    fw.write("import java.util.ArrayList; " + "\r\n");
                    fw.write("import java.io.Serializable; " + "\r\n");
                    fw.write("import java.util.HashMap; " + "\r\n");
                    fw.write("import java.util.List; " + "\r\n");
                    fw.write("import java.util.logging.Logger; " + "\r\n");
                    fw.write("import java.util.Optional; " + "\r\n");
                    fw.write("import javax.annotation.PostConstruct; " + "\r\n");
                    fw.write("import javax.faces.view.ViewScoped; " + "\r\n");
                    fw.write("import javax.inject.Inject; " + "\r\n");
                    fw.write("import javax.inject.Named; " + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import org.primefaces.context.RequestContext; " + "\r\n");
                    fw.write("import org.primefaces.event.SelectEvent;" + "\r\n");
fw.write("    // </editor-fold>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@ViewScoped" + "\r\n");
                    fw.write("public class " + nameClass + "Controller implements Serializable, IController  {" + "\r\n");
                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"fields\">" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");
                    fw.write("    private Boolean found = false;" + "\r\n");
                    fw.write("    private Boolean forsearch = false;" + "\r\n");
                    fw.write("    private Boolean writable = false;" + "\r\n");
                    fw.write("    //DataModel" + "\r\n");
                    fw.write("    private " + nameClass + "DataModel " + nameEntity + "DataModel;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    //Entity" + "\r\n");

                    fw.write("    " + nameClass + " " + nameEntity + ";" + "\r\n");
                    fw.write("    " + nameClass + " " + nameEntity + "Selected;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    //List" + "\r\n");
                    fw.write("     List<" + nameClass + "> " + nameEntity + "List = new ArrayList<>();" + "\r\n");
                    fw.write("     List<" + nameClass + "> " + nameEntity + "Filtered = new ArrayList<>();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    //Facade" + "\r\n");
                    fw.write("     @Inject" + "\r\n");
                    fw.write("     " + nameClass + "Facade " + nameEntity + "Facade;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    //Services" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + nameClass + "Services " + nameEntity + "Services;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ResourcesFiles rf;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    Printer printer;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    LoginController loginController;" + "\r\n");

                    fw.write("    //List of Relations" + "\r\n");

                    for (Atributos a : entidad.getAtributosList()) {
                        if (!Utilidades.isJavaType(a.getTipo())) {
                            if (Utilidades.esTipoList(a.getTipo())) {
                                fw.write("    List<" + Utilidades.letterToUpper(a.getNombre()) + "> " + a.getNombre() + "List;" + "\r\n");
                            } else {
                                fw.write("    List<" + a.getTipo() + "> " + a.getNombre() + "List;" + "\r\n");
                            }
                        }
                    }

                    fw.write("    //Facade of Relations" + "\r\n");

                    for (Atributos a : entidad.getAtributosList()) {
                        if (!Utilidades.isJavaType(a.getTipo())) {
                            fw.write("    @Inject" + "\r\n");
                            if (Utilidades.esTipoList(a.getTipo())) {
                                fw.write("    " + Utilidades.letterToUpper(a.getNombre()) + "Facade " + a.getNombre() + "Facade;" + "\r\n");
                            } else {
                                fw.write("    " + a.getTipo() + "Facade " + a.getNombre() + "Facade;" + "\r\n");
                            }

                        }
                    }
                    fw.write("    // </editor-fold>" + "\r\n");
//set/get
                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"getter/setter\">" + "\r\n");
                    for (Atributos a : entidad.getAtributosList()) {
                        if (!Utilidades.isJavaType(a.getTipo())) {
                            if (Utilidades.esTipoList(a.getTipo())) {
                                fw.write("    public void set" + Utilidades.letterToUpper(a.getNombre()) + "List(List<" + Utilidades.letterToUpper(a.getNombre()) + "> " + a.getNombre() + "List){" + "\r\n");
                                fw.write("        this." + a.getNombre() + "List =" + a.getNombre() + "List;" + "\r\n");
                                fw.write("    }" + "\r\n");
                            } else {
                                fw.write("    public void set" + a.getTipo() + "List(List<" + a.getTipo() + "> " + a.getNombre() + "List){" + "\r\n");
                                fw.write("        this." + a.getNombre() + "List =" + a.getNombre() + "List;" + "\r\n");
                                fw.write("    }" + "\r\n");
                            }

                        }
                    }

                    for (Atributos a : entidad.getAtributosList()) {
                        if (!Utilidades.isJavaType(a.getTipo())) {
                            fw.write("    public List<" + Utilidades.letterToUpper(a.getNombre()) + "> get" + Utilidades.letterToUpper(a.getNombre()) + "List(){" + "\r\n");
                            fw.write("        try{" + "\r\n");
                            fw.write("           " + a.getNombre() + "List =" + a.getNombre() + "Facade.findAll();" + "\r\n");
                            fw.write("       } catch (Exception e) {" + "\r\n");
                            fw.write("         JsfUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                            fw.write("       }" + "\r\n");
                            fw.write("       return " + a.getNombre() + "List;" + "\r\n");
                            fw.write("   }" + "\r\n");
                        }
                    }

                    fw.write("    public " + nameClass + "Services get" + nameClass + "Services() {" + "\r\n");
                    fw.write("        return " + nameEntity + "Services;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void set" + nameClass + "Services(" + nameClass + "Services " + nameEntity + "Services) {" + "\r\n");
                    fw.write("        this." + nameEntity + "Services = " + nameEntity + "Services;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public List<" + nameClass + "> get" + nameClass + "List() {" + "\r\n");
                    fw.write("        return " + nameEntity + "List;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void set" + nameClass + "List(List<" + nameClass + "> " + nameEntity + "List) {" + "\r\n");
                    fw.write("        this." + nameEntity + "List = " + nameEntity + "List;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public List<" + nameClass + "> get" + nameClass + "Filtered() {" + "\r\n");
                    fw.write("        return " + nameEntity + "Filtered;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void set" + nameClass + "Filtered(List<" + nameClass + "> " + nameEntity + "Filtered) {" + "\r\n");
                    fw.write("        this." + nameEntity + "Filtered = " + nameEntity + "Filtered;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public " + nameClass + " get" + nameClass + "() {" + "\r\n");
                    fw.write("        return " + nameEntity + ";" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void set" + nameClass + "(" + nameClass + " " + nameEntity + ") {" + "\r\n");
                    fw.write("        this." + nameEntity + " = " + nameEntity + ";" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public " + nameClass + " get" + nameClass + "Selected() {" + "\r\n");
                    fw.write("        return " + nameEntity + "Selected;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void set" + nameClass + "Selected(" + nameClass + " " + nameEntity + "Selected) {" + "\r\n");
                    fw.write("        this." + nameEntity + "Selected = " + nameEntity + "Selected;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public " + nameClass + "DataModel get" + nameClass + "DataModel() {" + "\r\n");
                    fw.write("        return " + nameEntity + "DataModel;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void set" + nameClass + "DataModel(" + nameClass + "DataModel " + nameEntity + "DataModel) {" + "\r\n");
                    fw.write("        this." + nameEntity + "DataModel = " + nameEntity + "DataModel;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public Boolean getFound() {" + "\r\n");
                    fw.write("        return found;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void setFound(Boolean found) {" + "\r\n");
                    fw.write("        this.found = found;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public Boolean getForsearch() {" + "\r\n");
                    fw.write("        return forsearch;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void setForsearch(Boolean forsearch) {" + "\r\n");
                    fw.write("        this.forsearch = forsearch;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public Boolean getWritable() {" + "\r\n");
                    fw.write("        return writable;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void setWritable(Boolean writable) {" + "\r\n");
                    fw.write("        this.writable = writable;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    // </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"constructor\">" + "\r\n");
                    fw.write("    public " + nameClass + "Controller() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    // </editor-fold>" + "\r\n");

                    //init
                    /**
                     * Creates a new instance of " + nameClass + "Controller
                     */
                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"init\">" + "\r\n");
                    fw.write("    @PostConstruct" + "\r\n");
                    fw.write("    public void init() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            found = false;" + "\r\n");
                    fw.write("            forsearch = false;" + "\r\n");
                    fw.write("            " + nameEntity + "List = new ArrayList<>();" + "\r\n");
                    fw.write("            " + nameEntity + "Filtered = new ArrayList<>();" + "\r\n");
                    fw.write("            " + nameEntity + " = new " + nameClass + "();" + "\r\n");
                    fw.write("            " + nameEntity + "Filtered = " + nameEntity + "List;" + "\r\n");
                    fw.write("            " + nameEntity + "DataModel = new " + nameClass + "DataModel(" + nameEntity + "List);" + "\r\n");
                    fw.write("            String " + primaryKey + " = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(\"" + primaryKey + "\");" + "\r\n");
                    fw.write("            if (" + primaryKey + " != null) {" + "\r\n");
                    fw.write("                Optional<" + nameClass + "> optional = " + nameEntity + "Facade.find(\"" + primaryKey + "\", " + primaryKey + ");" + "\r\n");
                    fw.write("                if (optional.isPresent()) {" + "\r\n");
                    fw.write("                    " + nameEntity + " = optional.get();" + "\r\n");
                    fw.write("                    " + nameEntity + "Selected = " + nameEntity + ";" + "\r\n");
                    fw.write("                    found = true;" + "\r\n");
                    fw.write("                    forsearch = true;" + "\r\n");
                    fw.write("                    writable = true;" + "\r\n");
                    fw.write("                    RequestContext.getCurrentInstance().update(\":form:content\");" + "\r\n");
                    fw.write("                }" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JsfUtil.addErrorMessage(\"init() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"reset\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void reset() {" + "\r\n");
                    fw.write("        found = false;" + "\r\n");
                    fw.write("        RequestContext.getCurrentInstance().reset(\":form:content\");" + "\r\n");
                    fw.write("        prepareNew();" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"prepareNew\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String prepareNew() {" + "\r\n");
                    fw.write("        " + nameEntity + " = new " + nameClass + "();" + "\r\n");
                    fw.write("        found = false;" + "\r\n");
                    fw.write("        forsearch = false;" + "\r\n");
                    fw.write("        writable = false;" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"prepareEdit\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String prepareEdit() {" + "\r\n");
                    fw.write("        return \"/pages/" + nameEntity + "/view.xhtml\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"open\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String open() {" + "\r\n");
                    fw.write("        found = false;" + "\r\n");
                    fw.write("        forsearch = true;" + "\r\n");
                    fw.write("        writable = false;" + "\r\n");
                    fw.write("        " + nameEntity + " = new " + nameClass + "();" + "\r\n");
                    fw.write("        " + nameEntity + "Selected = new " + nameClass + "();" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"showAll\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String showAll() {" + "\r\n");
                    fw.write("    try {" + "\r\n");
                    fw.write("        " + nameEntity + "List = new ArrayList<>();" + "\r\n");
                    fw.write("        " + nameEntity + "Filtered = new ArrayList<>();" + "\r\n");
                    fw.write("        " + nameEntity + "List = " + nameEntity + "Facade.findAll();" + "\r\n");
                    fw.write("        " + nameEntity + "Filtered = " + nameEntity + "List;" + "\r\n");
                    fw.write("        " + nameEntity + "DataModel = new " + nameClass + "DataModel(" + nameEntity + "List);" + "\r\n");
                    fw.write("        found = false;" + "\r\n");
                    fw.write("    } catch (Exception e) {" + "\r\n");
                    fw.write("        JsfUtil.addErrorMessage(\"showAll()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"query\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String query() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("        writable = true;" + "\r\n");
                    fw.write("        " + nameEntity + " = " + nameEntity + "Selected;" + "\r\n");
                    fw.write("        found = true;" + "\r\n");
                    fw.write("    } catch (Exception e) {" + "\r\n");
                    fw.write("        JsfUtil.addErrorMessage(\"query()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"verifyNew\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String verifyNew() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("        writable = false;" + "\r\n");
                    fw.write("        found = false;" + "\r\n");
                    fw.write("        forsearch = false;" + "\r\n");
                    fw.write("        Optional<" + nameClass + "> optional = " + nameEntity + "Facade.findById(" + nameEntity + ");" + "\r\n");
                    fw.write("        if (optional.isPresent()) {" + "\r\n");
                    fw.write("            " + nameEntity + " = optional.get();" + "\r\n");
                    fw.write("            " + nameEntity + "Selected = " + nameEntity + ";" + "\r\n");
                    fw.write("            writable = true;" + "\r\n");
                    fw.write("            found = true;" + "\r\n");
                    fw.write("            forsearch = true;" + "\r\n");
                    fw.write("            return \"\";" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        writable = true;" + "\r\n");
                    fw.write("    } catch (Exception e) {" + "\r\n");
                    fw.write("        JsfUtil.addErrorMessage(\"verifyNew()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"save\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String save() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("        Optional<" + nameClass + "> optional = " + nameEntity + "Facade.findById(" + nameEntity + ");" + "\r\n");
                    fw.write("        if (optional.isPresent()) {" + "\r\n");
                    fw.write("            JsfUtil.warningDialog(rf.getAppMessage(\"info.message\"), rf.getAppMessage(\"warning.idexist\"));" + "\r\n");
                    fw.write("            return null;" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        if (" + nameEntity + "Facade.save(" + nameEntity + ")) {" + "\r\n");
                    fw.write("            JsfUtil.addSuccessMessage(rf.getAppMessage(\"info.save\"));" + "\r\n");
                    fw.write("            reset();" + "\r\n");
                    fw.write("        } else {" + "\r\n");
                    fw.write("            JsfUtil.addSuccessMessage(\"save() \" + " + nameEntity + "Facade.getException().toString());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("      " + "\r\n");
                    fw.write("    } catch (Exception e) {" + "\r\n");
                    fw.write("        JsfUtil.addErrorMessage(\"save()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"edit\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String edit() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("             " + nameEntity + "Facade.update(" + nameEntity + ");" + "\r\n");
                    fw.write("        JsfUtil.addSuccessMessage(rf.getAppMessage(\"info.update\"));" + "\r\n");
                    fw.write("    } catch (Exception e) {" + "\r\n");
                    fw.write("        JsfUtil.addErrorMessage(\"edit()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"delete\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String delete() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            if (" + nameEntity + "Facade.delete(\"" + primaryKey + "\", " + nameEntity + ".get" + primaryKeyUpper + "())) {" + "\r\n");
                    fw.write("               JsfUtil.addSuccessMessage(rf.getAppMessage(\"info.delete\"));" + "\r\n");
                    fw.write("               " + nameEntity + " = new " + nameClass + "();" + "\r\n");
                    fw.write("               found = false;" + "\r\n");
                    fw.write("               reset();" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JsfUtil.addErrorMessage(\"delete() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"delete\">" + "\r\n");
                    fw.write("    public String delete(" + nameClass + " " + nameEntity + ") {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            if (" + nameEntity + "Facade.delete(\"" + primaryKey + "\", " + nameEntity + ".get" + primaryKeyUpper + "())) {" + "\r\n");
                    fw.write("                JsfUtil.addSuccessMessage(rf.getAppMessage(\"info.delete\"));" + "\r\n");
                    fw.write("                " + nameEntity + " = new " + nameClass + "();" + "\r\n");
                    fw.write("                found = false;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            showAll();" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JsfUtil.addErrorMessage(\"delete() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"/pages/" + nameEntity + "/list.xhtml\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"remove\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String remove() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + nameEntity + " = " + nameEntity + "Selected;" + "\r\n");
                    fw.write("            if (" + nameEntity + "Facade.delete(\"" + primaryKey + "\", " + nameEntity + ".get" + primaryKeyUpper + "())) {" + "\r\n");
                    fw.write("                " + nameEntity + "List.remove(" + nameEntity + ");" + "\r\n");
                    fw.write("                " + nameEntity + "Filtered = " + nameEntity + "List;" + "\r\n");
                    fw.write("                " + nameEntity + "DataModel = new " + nameClass + "DataModel(" + nameEntity + "List);" + "\r\n");
                    fw.write("                " + nameEntity + " = new " + nameClass + "();" + "\r\n");
                    fw.write("                " + nameEntity + "Selected = new " + nameClass + "();" + "\r\n");

                    fw.write("                found = false;" + "\r\n");
                    fw.write("                JsfUtil.addSuccessMessage(rf.getAppMessage(\"info.delete\"));" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JsfUtil.addErrorMessage(\"remove()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"deleteAll\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String deleteAll() {" + "\r\n");
                    fw.write("        if (" + nameEntity + "Facade.deleteAll() != 0) {" + "\r\n");
                    fw.write("            JsfUtil.addSuccessMessage(rf.getAppMessage(\"info.delete\"));" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"print\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String print() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            List<" + nameClass + "> list = new ArrayList<>();" + "\r\n");
                    fw.write("            list.add(" + nameEntity + ");" + "\r\n");
//                    fw.write("            String ruta = \"/resources/reportes/" + nameEntity + "/" + nameEntity + ".jasper\";" + "\r\n");
                    fw.write("            String ruta = \"/resources/reportes/" + nameEntity + "/" + "details.jasper\";" + "\r\n");
                    fw.write("            HashMap parameters = new HashMap();" + "\r\n");

                    if (Utilidades.numerodeList(entidad) > 0) {
                        fw.write("            String subReportPath = \"/resources/reportes/" + nameEntity + "/\";" + "\r\n");
                        fw.write("            String reportsDirPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(subReportPath);" + "\r\n");
                        fw.write("            parameters.put(\"SUBREPORT_DIR\", reportsDirPath + \"/\");" + "\r\n");
                    }
                    fw.write("            // parameters.put(\"P_parametro\", \"valor\");" + "\r\n");
                    fw.write("            printer.imprimir(list, ruta, parameters);" + "\r\n");
                    fw.write("        } catch (Exception ex) {" + "\r\n");
                    fw.write("            JsfUtil.addErrorMessage(\"imprimir() \" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("    return null;" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"printAll\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String printAll() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            List<" + nameClass + "> list = new ArrayList<>();" + "\r\n");
                    fw.write("            if (" + nameEntity + "Filtered.isEmpty()) {" + "\r\n");
                    fw.write("                list = " + nameEntity + "List;" + "\r\n");
                    fw.write("            } else {" + "\r\n");
                    fw.write("                list = " + nameEntity + "Filtered;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            String ruta = \"/resources/reportes/" + nameEntity + "/" + "all.jasper\";" + "\r\n");
                    fw.write("            HashMap parameters = new HashMap();" + "\r\n");
                    fw.write("            // parameters.put(\"P_parametro\", \"valor\");" + "\r\n");
                    fw.write("            printer.imprimir(list, ruta, parameters);" + "\r\n");
                    fw.write("         } catch (Exception ex) {" + "\r\n");
                    fw.write("             JsfUtil.addErrorMessage(\"imprimir() \" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("         }" + "\r\n");
                    fw.write("         return null;" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");

                    fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"handleSelect\">" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void handleSelect(SelectEvent event) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + nameEntity + "List.removeAll(" + nameEntity + "List);" + "\r\n");
                    fw.write("            " + nameEntity + "List.add(" + nameEntity + "Selected);" + "\r\n");
                    fw.write("            " + nameEntity + "Filtered = " + nameEntity + "List;" + "\r\n");
                    fw.write("            " + nameEntity + "DataModel = new " + nameClass + "DataModel(" + nameEntity + "List);" + "\r\n");
                    fw.write("        } catch (Exception ex) {" + "\r\n");
                    fw.write("            JsfUtil.addErrorMessage(\"handleSelect() \" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("    }// </editor-fold>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("}" + "\r\n");
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
