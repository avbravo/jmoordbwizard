/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.interfaces;

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
import org.primefaces.event.SelectEvent;

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
    ProyectoJEE proyectoJEE;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("IController.java", proyectoJEE.getPathInterfaces()+ "IController.java");

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
 
                Utilidades.searchAdd(ruta, "public String open();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String prepareNew();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String verifyNew();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public void reset();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String showAll();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String save();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String query();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String edit();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String remove();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String delete();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String deleteAll();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String print();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String printAll();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public String prepareEdit();", "public interface IController <T> {", false);
                Utilidades.searchAdd(ruta, "public void handleSelect(SelectEvent event);", "public interface IController <T> {", false);
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
                    fw.write("package " + proyectoJEE.getPaquete() + ".interfaces;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import org.primefaces.event.SelectEvent;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("public interface IController <T> {" + "\r\n");
                    fw.write("" + "\r\n");
                     fw.write("    public String open();" + "\r\n");

    fw.write("    public String prepareNew();" + "\r\n");

    fw.write("    public String verifyNew();" + "\r\n");

    fw.write("    public void reset();" + "\r\n");

    fw.write("    public String showAll();" + "\r\n");

    fw.write("    public String save();" + "\r\n");

    fw.write("    public String query();" + "\r\n");

    fw.write("    public String edit();" + "\r\n");

    fw.write("    public String remove();" + "\r\n");

    fw.write("    public String delete();" + "\r\n");

    fw.write("    public String deleteAll();" + "\r\n");

    fw.write("    public String print();" + "\r\n");

    fw.write("    public String printAll();" + "\r\n");

    fw.write("    public String prepareEdit();" + "\r\n");

    fw.write("    public void handleSelect(SelectEvent event);" + "\r\n");
                    
                   
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
