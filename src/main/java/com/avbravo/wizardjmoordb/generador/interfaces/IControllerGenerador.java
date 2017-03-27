/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.interfaces;

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
public class IControllerGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(IControllerGenerador.class.getName());

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

            procesar("IController.java", rutas.getPathInterfaces()+ "IController.java");

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

                Utilidades.searchAdd(ruta, "public String buscar();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String prepararNew();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String save();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String edit();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String delete();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String imprimir();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String imprimirTodos();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public Integer contador();", "public interface IController <T> {", false);
//                Utilidades.searchAdd(ruta, "public String elementoSeleccionado();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String habilitarConsultar();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public Integer getIdSiguiente();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public void handleSelect(SelectEvent event);", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public void reset();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public void query();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String invocarEditar(T t);", "public interface IController <T> {", false);
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
                    fw.write("package " + mySesion.getPaquete() + ".interfaces;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import org.primefaces.event.SelectEvent;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("public interface IController <T> {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String buscar();" + "\r\n");
                    fw.write("    public String prepararNew();" + "\r\n");
                    fw.write("    public String save();" + "\r\n");
                    fw.write("    public String edit();" + "\r\n");
                    fw.write("    public String delete();" + "\r\n");
                    fw.write("    public String imprimir();" + "\r\n");
                    fw.write("    public String imprimirTodos();" + "\r\n");
                    fw.write("    public Integer contador();" + "\r\n");
//                    fw.write("    public String elementoSeleccionado();" + "\r\n");
                    fw.write("    public String habilitarConsultar();" + "\r\n");
                    fw.write("    public Integer getIdSiguiente();" + "\r\n");
                    fw.write("    public void handleSelect(SelectEvent event);" + "\r\n");
                    fw.write("    public void reset();" + "\r\n");
                    fw.write("    public void query();" + "\r\n");
                    fw.write("    public String invocarEditar(T t);" + "\r\n");
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
