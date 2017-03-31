/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.roles;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
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
public class RolesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(RolesGenerador.class.getName());

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
            for (String s : mySesion.getRolesList()) {
             
                procesar("Rol" + s, proyectoJEE.getPathRoles() + "Rol" + s + ".java");
            }

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class " + archivo + " implements Serializable{", false);
                Utilidades.searchAdd(ruta, "@Inject", "public class " + archivo + " implements Serializable{", false);
                Utilidades.searchAdd(ruta, "ApplicationMenu applicationMenu;", "@Inject", false);

                /**
                 * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public " + archivo + "() {", constructor(archivo), "public class " + archivo + " implements Serializable{", false);
                Utilidades.addNotFoundMethod(ruta, "public void activar() {", activar(), "public class " + archivo + " implements Serializable{", false);

                /*
                 verificar si habilita  menuBar
                 */

                for (String s:mySesion.getMenubarList()) {

                    String barra = "applicationMenu.setMenuBar" + s + "(Boolean.TRUE);";
                    Utilidades.searchAdd(ruta, barra, "public void enabled() {", false);
                }

                /*
                verificar si los elementos estan en activado
                 */
                for (Entidad entidad : mySesion.getEntidadList()) {

                    String menuelemento = "applicationMenu.get" + entidad.getTabla() + "().initialize(Boolean.TRUE);";
                    Utilidades.searchAdd(ruta, menuelemento, "public void enabled() {", false);

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

//            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".menu.MenuBeans;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Inject;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.RequestScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);

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
                    fw.write("package " + proyectoJEE.getPaquete() + ".roles;" + "\r\n");
                    fw.write("" + "\r\n");
//                    fw.write("import " + proyectoJEE.getPaquete() + ".menu.MenuBeans;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("import javax.enterprise.context.RequestScoped;" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@RequestScoped" + "\r\n");
                    fw.write("public class " + archivo + " implements Serializable{" + "\r\n");
                    fw.write("private static final long serialVersionUID = 1L;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(" @Inject" + "\r\n");
                    fw.write(" ApplicationMenu applicationMenu;" + "\r\n");
                    fw.write("    /**" + "\r\n");
                    fw.write("     * Creates a new instance of " + archivo + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public " + archivo + "() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write(" public void enabled() {" + "\r\n");
                    fw.write("        /*" + "\r\n");
                    fw.write("         *barra" + "\r\n");
                    fw.write("         */" + "\r\n");
                    fw.write("        " + "\r\n");


                    for (String s: mySesion.getMenubarList()) {
                        fw.write("      applicationMenu.setMenuBar" + s + "(Boolean.TRUE);" + "\r\n");
                    }
                    fw.write("        /*" + "\r\n");
                    fw.write("         *menu" + "\r\n");
                    fw.write("         */" + "\r\n");
                    fw.write("        " + "\r\n");
                    for (Entidad entidad : mySesion.getEntidadList()) {
                        fw.write("      applicationMenu.get" + entidad.getTabla() + "().initialize(Boolean.TRUE);" + "\r\n");
                    }

                    fw.write("     " + "\r\n");
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

    private String constructor(String archivo) {
        try {

            String texto = "";

            texto += "    /**" + "\r\n";
            texto += "     * Creates a new instance of " + archivo + " \r\n";
            texto += "     */" + "\r\n";
            texto += "    public " + archivo + "() {" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("constructor()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String activar() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    /**" + "\r\n";
            texto += "     * activar" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "   public void activar() {" + "\r\n";
            texto += "        " + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("activar()  " + e.getLocalizedMessage());
        }
        return "";
    }
}
