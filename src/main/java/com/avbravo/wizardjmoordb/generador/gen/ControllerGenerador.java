/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.gen;

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
            } else {
                generarImport(ruta);
                //con @Inject
                Utilidades.searchAddTextAndInject(ruta, "ResourcesFiles rf;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.searchAddTextAndInject(ruta, "GestorImpresion gestorImpresion;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.searchAddTextAndInject(ruta, "LoginBean loginBean;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.searchAddTextAndInject(ruta, "FechasServices fechasServices;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.searchAddTextAndInject(ruta, Utilidades.letterToUpper(entidad.getTabla()) + "Facade " + Utilidades.letterToLower(entidad.getTabla()) + "Facade;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.searchAddTextAndInject(ruta, Utilidades.letterToUpper(entidad.getTabla()) + "Services " + Utilidades.letterToLower(entidad.getTabla()) + "Services;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                //Estos sin Inject
                String titulo = "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {";
                String services = Utilidades.letterToUpper(entidad.getTabla()) + " " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();";

                Utilidades.searchAdd(ruta, services, titulo, false);
                Utilidades.searchAdd(ruta, Utilidades.letterToUpper(entidad.getTabla()) + " selected " + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();", titulo, false);

                Utilidades.searchAdd(ruta, "@Named", "public class", true);
                Utilidades.searchAdd(ruta, "@Getter", "public class", true);
                Utilidades.searchAdd(ruta, "@Setter", "public class", true);
                Utilidades.searchAdd(ruta, "@SessionScoped", "public class", true);
                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class", false);
                Utilidades.searchAdd(ruta, "private Boolean nuevoregistro = false;", "public class", false);
                Utilidades.searchAdd(ruta, "private Boolean encontrado = false;", "public class", false);
                Utilidades.searchAdd(ruta, "Boolean desactivar = true;", "public class", false);

                /*
                metodos
                 */
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
                    //Revisar los services relacionados
                    if(!Utilidades.isJavaType(a.getTipo())){
                       Utilidades.searchAddTextAndInject(ruta, Utilidades.letterToUpper(a.getTipo()) + "Services " + Utilidades.letterToLower(a.getTipo()) + "Services;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false); 
                    }
                    
                
                             

                }
                Utilidades.addNotFoundMethod(ruta, "public void init() {", init(), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String buscar() {", buscar(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String prepararNew() {", prepararNew(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String save() {", save(entidad, isIntegerKey, columnKey, columnDate, tieneUsername), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String edit() {", edit(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String delete() {", delete(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String imprimir() {", imprimir(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String imprimirTodos() {", imprimirTodos(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public Integer contador() {", contador(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String habilitarConsultar() {", habilitarConsultar(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public Integer getIdSiguiente() {", getIdSiguiente(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public void handleSelect(SelectEvent event) {", handleSelect(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public void reset() {", reset(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public void query() {", query(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);
                Utilidades.addNotFoundMethod(ruta, "public String invocarEditar(Object t) {", invocarEditar(entidad, isIntegerKey, columnKey), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {", false);

                /**
                 * generar los metodos
                 */
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    private void generarImport(String ruta) {
        try {
            /**
             * agregar los imports
             */

            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".entity.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".ejb.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".generales.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".interfaces.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".services.*;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.ArrayList;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.HashMap;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.logging.Logger;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.annotation.PostConstruct;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Inject;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
            Utilidades.searchAdd(ruta, "import org.primefaces.event.CellEditEvent;", "package", false);
            Utilidades.searchAdd(ruta, "import org.primefaces.event.SelectEvent;", "package", false);
            Utilidades.searchAdd(ruta, "import org.primefaces.context.RequestContext;", "package", false);
            Utilidades.searchAdd(ruta, "import lombok.Getter;", "package", false);
            Utilidades.searchAdd(ruta, "import lombok.Setter;", "package", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarImport() " + e.getLocalizedMessage());
        }
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

                    fw.write("import " + proyectoJEE.getPaquete() + ".entity.*; " + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".ejb.*; " + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".generales.*; " + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".interfaces.*; " + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".services.*; " + "\r\n");
                    fw.write("import java.util.ArrayList; " + "\r\n");
                    fw.write("import java.io.Serializable; " + "\r\n");
                    fw.write("import java.util.HashMap; " + "\r\n");
                    fw.write("import java.util.List; " + "\r\n");
                    fw.write("import java.util.logging.Logger; " + "\r\n");
                    fw.write("import javax.annotation.PostConstruct; " + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped; " + "\r\n");
                    fw.write("import javax.inject.Inject; " + "\r\n");
                    fw.write("import javax.inject.Named; " + "\r\n");
                    fw.write("import org.primefaces.event.CellEditEvent; " + "\r\n");
                    fw.write("import org.primefaces.event.SelectEvent; " + "\r\n");
                    fw.write("import org.primefaces.context.RequestContext; " + "\r\n");
                    fw.write("import lombok.Getter;" + "\r\n");
                    fw.write("import lombok.Setter;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@Getter" + "\r\n");
                    fw.write("@Setter" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Controller implements Serializable, IController {" + "\r\n");
                    fw.write("private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("" + "\r\n");

                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ResourcesFiles rf;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    GestorImpresion gestorImpresion;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    LoginBean loginBean;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    FechasServices fechasServices;" + "\r\n");
                    fw.write("    private Boolean nuevoregistro = false;" + "\r\n");
                    fw.write("    private Boolean encontrado = false;" + "\r\n");
                    fw.write("    Boolean desactivar = true;" + "\r\n");

                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + "Facade " + Utilidades.letterToLower(entidad.getTabla()) + "Facade; \r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + "Services " + Utilidades.letterToLower(entidad.getTabla()) + "Services;" + "\r\n");

                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + " " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + " selected  = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("" + "\r\n");

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
                        if (!Utilidades.isJavaType(a.getTipo())) {
                            fw.write("    @Inject" + "\r\n");
                            fw.write("    " + Utilidades.letterToUpper(a.getTipo()) + "Services " + Utilidades.letterToLower(a.getTipo()) + "Services;" + "\r\n");
                        }

                    }

//Services relacionados
                    //init()
                      fw.write("" + "\r\n");
                        fw.write("" + "\r\n");
                    fw.write("    @PostConstruct" + "\r\n");
                    fw.write("    public void init() {" + "\r\n");
                    fw.write("         desactivar = true;" + "\r\n");
                    fw.write("         nuevoregistro = false;" + "\r\n");
                    fw.write("    }" + "\r\n");
//buscar()
                    fw.write("   @Override" + "\r\n");
                    fw.write("    public String buscar() {" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("        " + Utilidades.letterToLower(entidad.getTabla()) + " = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.find(" + Utilidades.letterToLower(entidad.getTabla()) + ".get" + Utilidades.letterToUpper(columnKey) + "());" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        if (" + Utilidades.letterToLower(entidad.getTabla()) + " == null) {" + "\r\n");
                    fw.write("            encontrado = false;" + "\r\n");
                    fw.write("            JSFUtil.addWarningMessage(rf.getMensajeArb(\"warning.noexiste\"));" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("        } else {" + "\r\n");
                    fw.write("            encontrado = true;" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    //prepararNew()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String prepararNew() {" + "\r\n");
                    fw.write("        desactivar = false;" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            nuevoregistro = true;" + "\r\n");
                    fw.write("            encontrado = false;" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    if (isIntegerKey) {
                        fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnKey) + "(" + Utilidades.letterToLower(entidad.getTabla()) + "Facade.getMaximo() + 1);" + "\r\n");
                    }

                    fw.write("" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    //save
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String save() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            if (" + Utilidades.letterToLower(entidad.getTabla()) + "Facade.find(" + Utilidades.letterToLower(entidad.getTabla()) + ".get" + Utilidades.letterToUpper(columnKey) + "()) != null) {" + "\r\n");
                    fw.write("                JSFUtil.warningDialog(rf.getMensajeArb(\"info.message\"), rf.getMensajeArb(\"warning.idexist\"));" + "\r\n");
                    fw.write("                return null;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    //genero la fecha
                    if (mySesion.getAddFechaSystema() && !columnDate.equals("")) {
                        fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnDate) + "(fechasServices.getFechaActual());" + "\r\n");
                    }
                    //genero el username desde el login
                    if (mySesion.getAddUserNameLogeado() && tieneUsername) {
                        fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "(loginBean.getUsuarios());" + "\r\n");
                    }

                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.create(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n");
                    fw.write("            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.save\"));" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("            this.nuevoregistro = false;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    //edit
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String edit() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.edit(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n");
                    fw.write("            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.update\"));" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //delete()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String delete() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.remove(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n");
                    fw.write("            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.delete\"));" + "\r\n");
                    fw.write("            encontrado = false;" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //imprimir
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String imprimir() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> list = new ArrayList<>();" + "\r\n");
                    fw.write("            list.add(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n");
                    fw.write("            String ruta = \"/resources/reportes/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".jasper\";" + "\r\n");
                    fw.write("            HashMap parameters = new HashMap();" + "\r\n");
                    fw.write("            //parameters.put(key,value )" + "\r\n");
                    fw.write("            gestorImpresion.imprimir(list, ruta, parameters);" + "\r\n");
                    fw.write("        } catch (Exception ex) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"imprimir() \" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //imprimirTodos()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String imprimirTodos() {" + "\r\n");
                    fw.write("        String ruta = \"/resources/reportes/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".jasper\";" + "\r\n");
                    fw.write("        HashMap parameters = new HashMap();" + "\r\n");
                    fw.write("        gestorImpresion.imprimir(" + Utilidades.letterToLower(entidad.getTabla()) + "Facade.get" + Utilidades.letterToUpper(entidad.getTabla()) + "List(), ruta, parameters);" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //contador
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public Integer contador() {" + "\r\n");
                    fw.write("        return " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.count();" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String habilitarConsultar() {" + "\r\n");
                    fw.write("        desactivar = true;" + "\r\n");
                    String simbolo = isIntegerKey ? "0" : "\"\"";
                    fw.write("        " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnKey) + "(" + simbolo + ");" + "\r\n");
                    fw.write("        this.nuevoregistro = false;" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public Integer getIdSiguiente() {" + "\r\n");
                    if (isIntegerKey) {
                        fw.write("       return " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.getMaximo() + 1;" + "\r\n");
                    } else {
                        fw.write("        throw new UnsupportedOperationException(\"Not supported yet.\"); //To change body of generated methods, choose Tools | Templates." + "\r\n");
                    }

                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void handleSelect(SelectEvent event) {" + "\r\n");
                    fw.write("        " + Utilidades.letterToLower(entidad.getTabla()) + " = selected;" + "\r\n");
                    fw.write("        this.nuevoregistro = false;" + "\r\n");
                    fw.write("        encontrado = true;" + "\r\n");
                    fw.write("        desactivar = false;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void reset() {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        RequestContext.getCurrentInstance().reset(\"form:panel\");" + "\r\n");
                    fw.write("        prepararNew();" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void query() {" + "\r\n");
                    fw.write("        nuevoregistro = false;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String invocarEditar(Object t) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + " = (" + Utilidades.letterToUpper(entidad.getTabla()) + ") t;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            this.selected = " + Utilidades.letterToLower(entidad.getTabla()) + ";" + "\r\n");
                    fw.write("            encontrado = true;" + "\r\n");
                    fw.write("            desactivar = false;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"invocarEditar() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        return \"/faces/pages/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".xhtml\";" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
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

    private String init() {
        try {

            String texto = "";
            texto += "    @PostConstruct" + "\r\n";
            texto += "    public void init() {" + "\r\n";
            texto += "         desactivar = true;" + "\r\n";
            texto += "         nuevoregistro = false;" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("init()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String buscar(Entidad entidad) {
        try {

            String texto = "";
            //buscar()
            texto += "   @Override" + "\r\n";
            texto += "    public String buscar() {" + "\r\n";
            texto += "" + "\r\n";
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
            texto += "        " + Utilidades.letterToLower(entidad.getTabla()) + " = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.find(" + Utilidades.letterToLower(entidad.getTabla()) + ".get" + Utilidades.letterToUpper(columnKey) + "());" + "\r\n";
            texto += "" + "\r\n";
            texto += "        if (" + Utilidades.letterToLower(entidad.getTabla()) + " == null) {" + "\r\n";
            texto += "            encontrado = false;" + "\r\n";
            texto += "            JSFUtil.addWarningMessage(rf.getMensajeArb(\"warning.noexiste\"));" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n";
            texto += "        } else {" + "\r\n";
            texto += "            encontrado = true;" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("buscar()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String prepararNew(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
//prepararNew()
            texto += "    @Override" + "\r\n";
            texto += "    public String prepararNew() {" + "\r\n";
            texto += "        desactivar = false;" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            nuevoregistro = true;" + "\r\n";
            texto += "            encontrado = false;" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n";
            if (isIntegerKey) {
                texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnKey) + "(" + Utilidades.letterToLower(entidad.getTabla()) + "Facade.getMaximo() + 1);" + "\r\n";
            }

            texto += "" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("prepararNew() " + e.getLocalizedMessage());
        }
        return "";
    }

    private String save(Entidad entidad, Boolean isIntegerKey, String columnKey, String columnDate, Boolean tieneUsername) {
        try {

            String texto = "";
            //save
            texto += "    @Override" + "\r\n";
            texto += "    public String save() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            if (" + Utilidades.letterToLower(entidad.getTabla()) + "Facade.find(" + Utilidades.letterToLower(entidad.getTabla()) + ".get" + Utilidades.letterToUpper(columnKey) + "()) != null) {" + "\r\n";
            texto += "                JSFUtil.warningDialog(rf.getMensajeArb(\"info.message\"), rf.getMensajeArb(\"warning.idexist\"));" + "\r\n";
            texto += "                return null;" + "\r\n";
            texto += "            }" + "\r\n";
            //genero la fecha
            if (mySesion.getAddFechaSystema() && !columnDate.equals("")) {
                texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnDate) + "(fechasServices.getFechaActual());" + "\r\n";
            }
            //genero el username desde el login
            if (mySesion.getAddUserNameLogeado() && tieneUsername) {
                texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "(loginBean.getUsuarios());" + "\r\n";
            }

            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.create(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n";
            texto += "            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.save\"));" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n";
            texto += "            this.nuevoregistro = false;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("save()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String edit(Entidad entidad) {
        try {

            String texto = "";
            //edit
            texto += "    @Override" + "\r\n";
            texto += "    public String edit() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.edit(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n";
            texto += "            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.update\"));" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("edit()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String delete(Entidad entidad) {
        try {

            String texto = "";
            //delete()
            texto += "    @Override" + "\r\n";
            texto += "    public String delete() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.remove(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n";
            texto += "            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.delete\"));" + "\r\n";
            texto += "            encontrado = false;" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("delete()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String imprimir(Entidad entidad) {
        try {

            String texto = "";
            //imprimir
            texto += "    @Override" + "\r\n";
            texto += "    public String imprimir() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> list = new ArrayList<>();" + "\r\n";
            texto += "            list.add(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n";
            texto += "            String ruta = \"/resources/reportes/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".jasper\";" + "\r\n";
            texto += "            HashMap parameters = new HashMap();" + "\r\n";
            texto += "            //parameters.put(key,value )" + "\r\n";
            texto += "            gestorImpresion.imprimir(list, ruta, parameters);" + "\r\n";
            texto += "        } catch (Exception ex) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"imprimir() \" + ex.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("imprimir()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String imprimirTodos(Entidad entidad) {
        try {

            String texto = "";
            //imprimir
            //imprimirTodos()
            texto += "    @Override" + "\r\n";
            texto += "    public String imprimirTodos() {" + "\r\n";
            texto += "        String ruta = \"/resources/reportes/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".jasper\";" + "\r\n";
            texto += "        HashMap parameters = new HashMap();" + "\r\n";
            texto += "        gestorImpresion.imprimir(" + Utilidades.letterToLower(entidad.getTabla()) + "Facade.get" + Utilidades.letterToUpper(entidad.getTabla()) + "List(), ruta, parameters);" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("edit()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String contador(Entidad entidad) {
        try {

            String texto = "";
            //contador
            texto += "    @Override" + "\r\n";
            texto += "    public Integer contador() {" + "\r\n";
            texto += "        return " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.count();" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("contador()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String habilitarConsultar(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public String habilitarConsultar() {" + "\r\n";
            texto += "        desactivar = true;" + "\r\n";
            String simbolo = isIntegerKey ? "0" : "\"\"";
            texto += "        " + Utilidades.letterToLower(entidad.getTabla()) + ".set" + Utilidades.letterToUpper(columnKey) + "(" + simbolo + ");" + "\r\n";
            texto += "        this.nuevoregistro = false;" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("habilitarConsultar()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getIdSiguiente(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
            //getIdSiguiente()
            texto += "    @Override" + "\r\n";
            texto += "    public Integer getIdSiguiente() {" + "\r\n";
            if (isIntegerKey) {
                texto += "       return " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.getMaximo() + 1;" + "\r\n";
            } else {
                texto += "        throw new UnsupportedOperationException(\"Not supported yet.\"); //To change body of generated methods, choose Tools | Templates." + "\r\n";
            }

            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getIdSiguiente()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String handleSelect(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
            //handleSelect
            texto += "    @Override" + "\r\n";
            texto += "    public void handleSelect(SelectEvent event) {" + "\r\n";
            texto += "        " + Utilidades.letterToLower(entidad.getTabla()) + " = selected;" + "\r\n";
            texto += "        this.nuevoregistro = false;" + "\r\n";
            texto += "        encontrado = true;" + "\r\n";
            texto += "        desactivar = false;" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("handleSelect()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String reset(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
            //reset()
            texto += "    @Override" + "\r\n";
            texto += "    public void reset() {" + "\r\n";
            texto += "" + "\r\n";
            texto += "        RequestContext.getCurrentInstance().reset(\"form:panel\");" + "\r\n";
            texto += "        prepararNew();" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("reset()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String query(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
            //query()
            texto += "    @Override" + "\r\n";
            texto += "    public void query() {" + "\r\n";
            texto += "        nuevoregistro = false;" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("query()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String invocarEditar(Entidad entidad, Boolean isIntegerKey, String columnKey) {
        try {

            String texto = "";
            //invocarEditar
            texto += "    @Override" + "\r\n";
            texto += "    public String invocarEditar(Object t) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + " = (" + Utilidades.letterToUpper(entidad.getTabla()) + ") t;" + "\r\n";
            texto += "" + "\r\n";
            texto += "            this.selected = " + Utilidades.letterToLower(entidad.getTabla()) + ";" + "\r\n";
            texto += "            encontrado = true;" + "\r\n";
            texto += "            desactivar = false;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"invocarEditar() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "" + "\r\n";
            texto += "        return \"/faces/pages/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".xhtml\";" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("invocarEditar()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
