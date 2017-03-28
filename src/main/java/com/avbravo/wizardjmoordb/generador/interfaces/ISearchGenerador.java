/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.interfaces;

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
public class ISearchGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ISearchGenerador.class.getName());

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

            procesar("ISearch.java", proyectoJEE.getPathInterfaces() + "ISearch.java");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            } else {
                generarImport(ruta);

                Utilidades.searchAdd(ruta, "public String clear();", "public interface ISearch <T> {", false);
                Utilidades.searchAdd(ruta, "public void iniciar();", "public interface ISearch <T> {", false);
                Utilidades.searchAdd(ruta, "public void iniciar(String value);", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public String showAll();", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public String delete();", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public String changeItems();", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public List<T> getItems();", "public interface ISearch <T> {", false);

                
                

                Utilidades.searchAdd(ruta, "public List<T> getItemsCollection();", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public String imprimirTodos();", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "//carga todos los registros e invoca imprimir", "public interface ISearch <T> {", false);
                Utilidades.searchAdd(ruta, "public String listar();", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public void onCellEdit(CellEditEvent event);", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public void handleSelect(SelectEvent event);", "public interface ISearch <T> {", false);

                Utilidades.searchAdd(ruta, "public String delete(T t);", "public interface ISearch <T> {", false);
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

            Utilidades.searchAdd(ruta, "import org.primefaces.event.SelectEvent;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
            Utilidades.searchAdd(ruta, "import org.primefaces.event.CellEditEvent;", "package", false);

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
    private Boolean crearFile(String path, String archivo) {
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
                    fw.write("package " + proyectoJEE.getPaquete() + ".interfaces;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import org.primefaces.event.CellEditEvent;" + "\r\n");
                    fw.write("import org.primefaces.event.SelectEvent;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("public interface ISearch <T> {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String clear();" + "\r\n");
                    fw.write("    public void iniciar();" + "\r\n");
                    fw.write("    public void iniciar(String value);" + "\r\n");
                    fw.write("    public String showAll();" + "\r\n");
                    fw.write("    public String delete();" + "\r\n");
                    fw.write("    public String changeItems();" + "\r\n");
                    fw.write("    public List<T> getItems();" + "\r\n");
//                    fw.write("    public List<T> getItemsEntity();" + "\r\n");
                     fw.write("    public List<T> getItemsCollection();" + "\r\n");
                    fw.write("    public String imprimirTodos();" + "\r\n");
                    fw.write("    //carga todos los registros e invoca imprimir" + "\r\n");
                    fw.write("    public String listar();" + "\r\n");
                    fw.write("    public void onCellEdit(CellEditEvent event);" + "\r\n");
                    fw.write("    public void handleSelect(SelectEvent event);" + "\r\n");
                    fw.write("    public String delete(T t);" + "\r\n");
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

}
