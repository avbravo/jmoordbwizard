/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.menu;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
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
public class MenuBeansGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MenuBeansGenerador.class.getName());

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

            procesar("MenuBeans.java", rutas.getPathMenu() + "MenuBeans.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class MenuBeans implements Serializable {", false);
                Utilidades.searchAdd(ruta, "@Named", "public class MenuBeans implements Serializable {", true);
                Utilidades.searchAdd(ruta, "@SessionScoped", "public class MenuBeans implements Serializable {", true);
                Utilidades.searchAdd(ruta, "@Getter", "public class MenuBeans implements Serializable {", true);
                Utilidades.searchAdd(ruta, "@Setter", "public class MenuBeans implements Serializable {", true);

                /*
                * generar los metodo habilitar todo
                 */
                Utilidades.addNotFoundMethod(ruta, "public void habilitarTodo", habilitarTodo(), "public MenuBeans() {", true);
                /*
                verificar los elementos de barramenu
                 */
                for (String s : mySesion.getMenubarList()) {
                    String barra = "private Boolean barraMenu" + s + " = false;";
                    Utilidades.searchAdd(ruta, barra, "private static final long serialVersionUID = 1L;", true);

                }
//              
                /*
               verificar los MenuElemento 
                 */

                for (Entidad entidad : mySesion.getEntidadList()) {
                    String menuelemento = "MenuElemento " + entidad.getTabla().toLowerCase() + " = new MenuElemento();";

                    Utilidades.searchAdd(ruta, menuelemento, "private static final long serialVersionUID = 1L;", true);
                }

                /*
                 verificar si habilita  barraMenu
                 */
//                for (Integer i = 0; i < mySesion.getNumeroMenuBar(); i++) {
                for (String s : mySesion.getMenubarList()) {
                    String barra = "barraMenu" + s + " = activo;";
                    Utilidades.searchAdd(ruta, barra, "public void habilitarTodo(Boolean activo) {", false);
                }

                /*
                verificar si los elementos estan en habilitartodo
                 */
                for (Entidad entidad : mySesion.getEntidadList()) {
                    String menuelemento = entidad.getTabla().toLowerCase() + ".habilitar(activo);";
                    Utilidades.searchAdd(ruta, menuelemento, "public void habilitarTodo(Boolean activo) {", false);


                }

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

            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Inject;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
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
                    fw.write("package " + mySesion.getPaquete() + ".menu;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("import lombok.Getter;" + "\r\n");
                    fw.write("import lombok.Setter;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("@Getter" + "\r\n");
                    fw.write("@Setter" + "\r\n");

                    fw.write("public class MenuBeans implements Serializable {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("    public MenuBeans() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    /*" + "\r\n");
                    fw.write("     barra de menu" + "\r\n");
                    fw.write("     */" + "\r\n");
//                    for (Integer i = 0; i < mySesion.getNumeroMenuBar(); i++) {
                    for (String s : mySesion.getMenubarList()) {
                        fw.write("    private Boolean barraMenu" + s + " = false;" + "\r\n");
                    }
                    fw.write("        " + "\r\n");
                    fw.write("    /*" + "\r\n");
                    fw.write("     elementos" + "\r\n");
                    fw.write("     */" + "\r\n");
                    for (Entidad entidad : mySesion.getEntidadList()) {
                        fw.write("   MenuElemento " + entidad.getTabla().toLowerCase() + " = new MenuElemento();" + "\r\n");
                    }
                    fw.write("        " + "\r\n");
                    fw.write("    public void habilitarTodo(Boolean activo) {" + "\r\n");
//                    for (Integer i = 0; i < mySesion.getNumeroMenuBar(); i++) {
                    for (String s : mySesion.getMenubarList()) {
                        fw.write("        barraMenu" + s + " = activo;" + "\r\n");
                    }
                    for (Entidad entidad : mySesion.getEntidadList()) {
                        fw.write("       " + entidad.getTabla().toLowerCase() + ".habilitar(activo);" + "\r\n");
                    }

                    fw.write("        " + "\r\n");
                    fw.write("    }" + "\r\n");
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

    private String habilitarTodo() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    /**" + "\r\n";
            texto += "     * habilitartodo" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "   public void habilitarTodo(Boolean activo) {" + "\r\n";
            texto += "        " + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("habilitarTodo()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
