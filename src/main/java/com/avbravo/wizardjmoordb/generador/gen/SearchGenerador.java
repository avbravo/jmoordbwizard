/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.gen;

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
public class SearchGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(SearchGenerador.class.getName());

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
            for (Entidad e : mySesion.getEntidadList()) {
                procesar(e, e.getTabla(), rutas.getPathSearch() + e.getTabla() + "Search.java");
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
                Utilidades.searchAddTextAndInject(ruta, "ResourcesFiles rf;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.searchAddTextAndInject(ruta, "GestorImpresion gestorImpresion;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.searchAddTextAndInject(ruta, "LoginBean loginBean;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.searchAddTextAndInject(ruta, Utilidades.letterToUpper(entidad.getTabla()) + "Facade " + Utilidades.letterToLower(entidad.getTabla()) + "Facade;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.searchAddTextAndInject(ruta, Utilidades.letterToUpper(entidad.getTabla()) + "Services " + Utilidades.letterToLower(entidad.getTabla()) + "Services;", "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                //Estos sin Inject
                String titulo = "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {";
                String services = Utilidades.letterToUpper(entidad.getTabla()) + " " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();";

                Utilidades.searchAdd(ruta, services, titulo, false);
                Utilidades.searchAdd(ruta, Utilidades.letterToUpper(entidad.getTabla()) + " selected " + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();", titulo, false);
                Utilidades.searchAdd(ruta, "private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> filtered  = new ArrayList<>();", titulo, false);
                Utilidades.searchAdd(ruta, "private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> items = new ArrayList<>();", titulo, false);
                Utilidades.searchAdd(ruta, "private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> itemsCollection = new ArrayList<>();", titulo, false);
                Utilidades.searchAdd(ruta, "private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> " + Utilidades.letterToLower(entidad.getTabla()) + "List = new ArrayList<>();", titulo, false);
                Utilidades.searchAdd(ruta, "@Named", "public class", true);
                Utilidades.searchAdd(ruta, "@Getter", "public class", true);
                Utilidades.searchAdd(ruta, "@Setter", "public class", true);
                Utilidades.searchAdd(ruta, "@ViewScoped", "public class", true);
                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class", false);

                /*
                metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public void init() {", init(), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String clear() {", clear(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public void iniciar() {", iniciar(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public void iniciar(String value) {", iniciarParametro(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String showAll() {", showAll(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String listar() {", listar(), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String delete() {", delete(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> getItems() {", getItems(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String changeItems() {", changeItems(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> getItemsCollection() {", getItemsCollection(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String imprimirTodos() {", imprimirTodos(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public void onCellEdit(CellEditEvent event) {", onCellEdit(), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public void handleSelect(SelectEvent event) {", handleSelect(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);
                Utilidades.addNotFoundMethod(ruta, "public String delete(Object t) {", deleteObject(entidad), "public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {", false);

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

            Utilidades.searchAdd(ruta, "import " + mySesion.getPaquete() + ".entity.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + mySesion.getPaquete() + ".ejb.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + mySesion.getPaquete() + ".generales.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + mySesion.getPaquete() + ".interfaces.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + mySesion.getPaquete() + ".services.*;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.ArrayList;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.HashMap;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.logging.Logger;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.annotation.PostConstruct;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.view.ViewScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Inject;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
            Utilidades.searchAdd(ruta, "import org.primefaces.event.CellEditEvent;", "package", false);
            Utilidades.searchAdd(ruta, "import org.primefaces.event.SelectEvent;", "package", false);
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
                    fw.write("package " + mySesion.getPaquete() + ".search;" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("import " + mySesion.getPaquete() + ".entity.*; " + "\r\n");
                    fw.write("import " + mySesion.getPaquete() + ".ejb.*; " + "\r\n");
                    fw.write("import " + mySesion.getPaquete() + ".generales.*; " + "\r\n");
                    fw.write("import " + mySesion.getPaquete() + ".interfaces.*; " + "\r\n");
                    fw.write("import " + mySesion.getPaquete() + ".services.*; " + "\r\n");
                    fw.write("import java.util.ArrayList; " + "\r\n");
                    fw.write("import java.io.Serializable; " + "\r\n");
                    fw.write("import java.util.HashMap; " + "\r\n");
                    fw.write("import java.util.List; " + "\r\n");
                    fw.write("import java.util.logging.Logger; " + "\r\n");
                    fw.write("import javax.annotation.PostConstruct; " + "\r\n");
                    fw.write("import javax.faces.view.ViewScoped; " + "\r\n");
                    fw.write("import javax.inject.Inject; " + "\r\n");
                    fw.write("import javax.inject.Named; " + "\r\n");
                    fw.write("import org.primefaces.event.CellEditEvent; " + "\r\n");
                    fw.write("import org.primefaces.event.SelectEvent; " + "\r\n");
                    fw.write("import lombok.Getter;" + "\r\n");
                    fw.write("import lombok.Setter;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@ViewScoped" + "\r\n");
                    fw.write("@Getter" + "\r\n");
                    fw.write("@Setter" + "\r\n");
                    fw.write("public class " + Utilidades.letterToUpper(entidad.getTabla()) + "Search implements Serializable, ISearch {" + "\r\n");
                    fw.write("private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + "Facade " + Utilidades.letterToLower(entidad.getTabla()) + "Facade; \r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ResourcesFiles rf;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    GestorImpresion gestorImpresion;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    LoginBean loginBean;" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + " " + Utilidades.letterToLower(entidad.getTabla()) + " = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + " selected  = new " + Utilidades.letterToUpper(entidad.getTabla()) + "();" + "\r\n");
                    fw.write("    private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> filtered  = new ArrayList<>(); " + "\r\n");
                    fw.write("    private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> " + Utilidades.letterToLower(entidad.getTabla()) + "List = new ArrayList<>(); " + "\r\n");
                    fw.write("    private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> items = new ArrayList<>(); " + "\r\n");
                    fw.write("    private List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> itemsCollection = new ArrayList<>(); " + "\r\n");

                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + Utilidades.letterToUpper(entidad.getTabla()) + "Services " + Utilidades.letterToUpper(entidad.getTabla()) + "Services;" + "\r\n");
                    fw.write("" + "\r\n");
                    //init
                    fw.write("    @PostConstruct" + "\r\n");
                    fw.write("    public void init() {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");

                    //clear
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String clear() {" + "\r\n");
                    fw.write("        " + Utilidades.letterToLower(entidad.getTabla()) + "List = new ArrayList<>();" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    //iniciar
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void iniciar() {" + "\r\n");
                    fw.write("        " + Utilidades.letterToLower(entidad.getTabla()) + "List = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //iniciar(String value)
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void iniciar(String value) {" + "\r\n");
                    fw.write("    // utilice un parametro y un findBy si desea cargar registros en base a una condicion " + "\r\n");
                    fw.write("        " + Utilidades.letterToLower(entidad.getTabla()) + "List = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //showAll()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String showAll() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "List = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");;
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }" + "\r\n");
//delete()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String delete() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.remove(selected);" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "List.remove(selected);" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //getItems()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> getItems() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
//                    fw.write("            if (items == null) {" + "\r\n");
                    fw.write("                items = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n");
//                    fw.write("            }" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return items;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //changeItems()
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String changeItems() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            items = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"changeItems() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return \"\";" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> getItemsCollection() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("             //si desea obtener datos de una coleccion indiquela en esta seccion" + "\r\n");
                    fw.write("            //itemsCollection = new ArrayList(" + Utilidades.letterToLower(entidad.getTabla()) + ".getOtherEntityCollection());" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return itemsCollection;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    //

//imprimirTodos
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String imprimirTodos() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            String ruta = \"/resources/reportes/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".jasper\";" + "\r\n");
                    fw.write("            HashMap parameters = new HashMap();" + "\r\n");
                    fw.write("            //defina los parametros en el reporte y asigne valores desde aqui" + "\r\n");
                    fw.write("            //parameters.put(key, value);" + "\r\n");
                    fw.write("            gestorImpresion.imprimir(" + Utilidades.letterToLower(entidad.getTabla()) + "List, ruta, parameters);" + "\r\n");
                    fw.write("        } catch (Exception ex) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"imprimir() \" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String listar() {" + "\r\n");
                    fw.write("        showAll();" + "\r\n");
                    fw.write("        imprimirTodos();" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void onCellEdit(CellEditEvent event) {" + "\r\n");
                    fw.write("        throw new UnsupportedOperationException(\"Not supported yet.\");" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void handleSelect(SelectEvent event) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "List.removeAll(" + Utilidades.letterToLower(entidad.getTabla()) + "List);" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "List.add(selected);" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        } catch (Exception ex) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"handleSelect() \" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String delete(Object t) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + " = (" + Utilidades.letterToUpper(entidad.getTabla()) + ") t;" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "List.remove(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.remove(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n");
                    fw.write("        } catch (Exception ex) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"delete()\" + ex.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        return null;" + "\r\n");
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
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("init()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String clear(Entidad entidad) {
        try {

            String texto = "";
            //clear
            texto += "    @Override" + "\r\n";
            texto += "    public String clear() {" + "\r\n";
            texto += "        " + Utilidades.letterToLower(entidad.getTabla()) + "List = new ArrayList<>();" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("clear()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String iniciar(Entidad entidad) {
        try {

            String texto = "";
            //iniciar
            texto += "    @Override" + "\r\n";
            texto += "    public void iniciar() {" + "\r\n";
            texto += "        " + Utilidades.letterToLower(entidad.getTabla()) + "List = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("iniciar()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String deleteObject(Entidad entidad) {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public String delete(Object t) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + " = (" + Utilidades.letterToUpper(entidad.getTabla()) + ") t;" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "List.remove(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.remove(" + Utilidades.letterToLower(entidad.getTabla()) + ");" + "\r\n";
            texto += "        } catch (Exception ex) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"delete()\" + ex.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("deleteObject()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String handleSelect(Entidad entidad) {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public void handleSelect(SelectEvent event) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "List.removeAll(" + Utilidades.letterToLower(entidad.getTabla()) + "List);" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "List.add(selected);" + "\r\n";
            texto += "" + "\r\n";
            texto += "        } catch (Exception ex) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"handleSelect() \" + ex.getLocalizedMessage());" + "\r\n";
            texto += "" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("handleSelect()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String onCellEdit() {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public void onCellEdit(CellEditEvent event) {" + "\r\n";
            texto += "        throw new UnsupportedOperationException(\"Not supported yet.\");" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("onCellEdit()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String listar() {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public String listar() {" + "\r\n";
            texto += "        showAll();" + "\r\n";
            texto += "        imprimirTodos();" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("listar()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String imprimirTodos(Entidad entidad) {
        try {

            String texto = "";
//imprimirTodos
            texto += "    @Override" + "\r\n";
            texto += "    public String imprimirTodos() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            String ruta = \"/resources/reportes/" + Utilidades.letterToLower(entidad.getTabla()) + "/" + Utilidades.letterToLower(entidad.getTabla()) + ".jasper\";" + "\r\n";
            texto += "            HashMap parameters = new HashMap();" + "\r\n";
            texto += "            //defina los parametros en el reporte y asigne valores desde aqui" + "\r\n";
            texto += "            //parameters.put(key, value);" + "\r\n";
            texto += "            gestorImpresion.imprimir(" + Utilidades.letterToLower(entidad.getTabla()) + "List, ruta, parameters);" + "\r\n";
            texto += "        } catch (Exception ex) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"imprimir() \" + ex.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("imprimirTodos()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getItemsCollection(Entidad entidad) {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> getItemsCollection() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "             //si desea obtener datos de una coleccion indiquela en esta seccion" + "\r\n";
            texto += "            //itemsCollection = new ArrayList(" + Utilidades.letterToLower(entidad.getTabla()) + ".getOtherEntityCollection());" + "\r\n";
            texto += "" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return itemsCollection;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getItemsCollection()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String changeItems(Entidad entidad) {
        try {

            String texto = "";
            //changeItems()
            texto += "    @Override" + "\r\n";
            texto += "    public String changeItems() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            items = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n";
            texto += "" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"changeItems() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("changeItems()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getItems(Entidad entidad) {
        try {

            String texto = "";
            //getItems()
            texto += "    @Override" + "\r\n";
            texto += "    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> getItems() {" + "\r\n";
            texto += "        try {" + "\r\n";
//                    texto +="            if (items == null) {" + "\r\n";
            texto += "                items = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n";
//                    texto +="            }" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return items;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getItems()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String iniciarParametro(Entidad entidad) {
        try {

            String texto = "";
            //iniciar(String value)
            texto += "    @Override" + "\r\n";
            texto += "    public void iniciar(String value) {" + "\r\n";
            texto += "    // utilice un parametro y un findBy si desea cargar registros en base a una condicion " + "\r\n";
            texto += "        " + Utilidades.letterToLower(entidad.getTabla()) + "List = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("iniciarParametro()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String showAll(Entidad entidad) {
        try {

            String texto = "";
            //showAll()
            texto += "    @Override" + "\r\n";
            texto += "    public String showAll() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "List = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n";
            texto += "" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";;
            texto += "        }" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("showAll()  " + e.getLocalizedMessage());
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
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.remove(selected);" + "\r\n";
            texto += "            " + Utilidades.letterToLower(entidad.getTabla()) + "List.remove(selected);" + "\r\n";
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
}
