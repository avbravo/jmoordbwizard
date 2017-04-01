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
public class ValidadorRolesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ValidadorRolesGenerador.class.getName());

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
            
            }
            procesar("ValidadorRoles", proyectoJEE.getPathRoles() + "ValidadorRoles.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class ValidadorRoles implements Serializable{", false);
                Utilidades.searchAdd(ruta, "@Inject", "public class ValidadorRoles implements Serializable{", false);

                Utilidades.searchAddTextAndInject(ruta, "ApplicationMenu applicationMenu;", "public class ValidadorRoles implements Serializable{", false);
                Utilidades.searchAddTextAndInject(ruta, "ResourcesFiles rf;", "public class ValidadorRoles implements Serializable{", false);

                for (String s : mySesion.getRolesList()) {
                    String barra = "Rol" + s + " rol" + Utilidades.letterToUpper(s) + ";";
                    Utilidades.searchAddTextAndInject(ruta, barra, "public class ValidadorRoles implements Serializable{", false);

                }
                /**
                 * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public ValidadorRoles() {", constructor(archivo), "public class ValidadorRoles implements Serializable{", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean validarRoles(String rolvalidacion) {", validarRoles(), "public class ValidadorRoles implements Serializable{", false);

                /*
                 verificar si habilita  barraMenu
                 */
                for (String s : mySesion.getRolesList()) {
                    String barra = "case " + "\"" + Utilidades.letterToLower(s) + "\": ";
                    Utilidades.searchAddRolCaseValidadorRoles(ruta, barra, " switch (rolvalidacion) {", false, s);
                }
                /*
                default
                */
 String barra = "default:";
                    Utilidades.searchAddDefaultValidadorRoles(ruta, barra, " switch (rolvalidacion) {", false);

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
            Utilidades.searchAdd(ruta, "import com.avbravo.avbravoutils.JsfUtil;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".util.ResourcesFiles;", "package", false);
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
                    fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".util.ResourcesFiles;" + "\r\n");
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
                    fw.write("public class ValidadorRoles implements Serializable{" + "\r\n");
                    fw.write("private static final long serialVersionUID = 1L;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(" @Inject" + "\r\n");
                    fw.write(" ApplicationMenu applicationMenu;" + "\r\n");
                    fw.write(" @Inject" + "\r\n");
                    fw.write(" ResourcesFiles rf;" + "\r\n");

                    for (String s : mySesion.getRolesList()) {
                        fw.write(" @Inject" + "\r\n");
                        fw.write(" Rol" + s + " rol" + s + ";" + "\r\n");

                    }
                    fw.write(" public Boolean validarRoles(String rolvalidacion) {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        rolvalidacion = rolvalidacion.toLowerCase();" + "\r\n");
                    fw.write("        Boolean ok = Boolean.TRUE;" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            switch (rolvalidacion) {" + "\r\n");
                    for (String s : mySesion.getRolesList()) {
                        fw.write("            case " + "\"" + Utilidades.letterToLower(s) + "\": " + "\r\n");
                        fw.write("                rol" + s + ".enabled();" + "\r\n");
                        fw.write("                 break;" + "\r\n");

                    }

                    fw.write("            default:" + "\r\n");
                    fw.write("                applicationMenu.enabledAll(false);" + "\r\n");
                    fw.write("                ok = Boolean.FALSE;" + "\r\n");
                    fw.write("                JsfUtil.warningDialog(rf.getAppMessage(\"warning.title\")," + "\r\n");
                    fw.write("                        rf.getAppMessage(\"info.sinrolasignado\"));" + "\r\n");
                    fw.write("             }" + "\r\n");
                    fw.write("         } catch (Exception e) {" + "\r\n");
                    fw.write("             JsfUtil.addErrorMessage(\"validarRoles() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("         }" + "\r\n");
                    fw.write("         return ok;" + "\r\n");
                    fw.write("     }" + "\r\n");
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
            texto += "    public ValidadorRoles() {" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("constructor()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String validarRoles() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    /**" + "\r\n";
            texto += "     * validarRoles" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "   public Boolean validarRoles(String rolvalidacion) {" + "\r\n";
            texto += "       Boolean ok = Boolean.TRUE;" + "\r\n";
            texto += "       try {" + "\r\n";
            texto += "            switch (rolvalidacion) {" + "\r\n";
            texto += "                default:" + "\r\n";
            texto += "                    applicationMenu.enbaledAll(false);" + "\r\n";
            texto += "                    ok = Boolean.FALSE;" + "\r\n";
            texto += "                    JsfUtil.warningDialog(rf.getMensajeArb(\"warning.title\")," + "\r\n";
            texto += "                            rf.getMensajeArb(\"info.sinrolasignado\"));" + "\r\n";
            texto += "             }" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JsfUtil.addErrorMessage(\"validarRoles() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return ok;" + "\r\n";
            texto += "}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("validarRoles()  " + e.getLocalizedMessage());
        }
        return "";
    }
}
