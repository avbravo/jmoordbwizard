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
public class ApplicationMenuGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ApplicationMenuGenerador.class.getName());

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

            procesar("ApplicationMenu.java", proyectoJEE.getPathRoles() + "ApplicationMenu.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class ApplicationMenu implements Serializable {", false);
                Utilidades.searchAdd(ruta, "@Named", "public class MenuBeans implements Serializable {", true);
                Utilidades.searchAdd(ruta, "@SessionScoped", "public class ApplicationMenu implements Serializable {", true);


                /*
                * generar los metodo habilitar todo
                 */
                Utilidades.addNotFoundMethod(ruta, "public void enabledAll", enabledAll(), "public ApplicationMenu() {", true);
                /*
                verificar los elementos de barramenu
                 */
                for (String s : mySesion.getMenubarList()) {
                    String barra = "private Boolean menuBar" + s + " = false;";
                    Utilidades.searchAdd(ruta, barra, "private static final long serialVersionUID = 1L;", false);
                    if (!Utilidades.search(ruta, "public void setMenuBar" + s + "(Boolean menuBar" + s + "){")) {

                        Utilidades.searchAdd(ruta, generarSetMenuBar(s), "private static final long serialVersionUID = 1L;", false);
                    }

                    if (!Utilidades.search(ruta, "public Boolean getMenuBar" + s + "(){")) {

                        Utilidades.searchAdd(ruta, generarGetMenuBar(s), "private static final long serialVersionUID = 1L;", false);
                    }

                }
//              
                /*
               verificar los MenuElemento 
                 */

                for (Entidad entidad : mySesion.getEntidadList()) {
                    String menuelemento = "MenuElement " + entidad.getTabla().toLowerCase() + " = new MenuElement();";
                    Utilidades.searchAdd(ruta, menuelemento, "private static final long serialVersionUID = 1L;", false);
                    if (!Utilidades.search(ruta, "public MenuElement get" + Utilidades.letterToUpper(entidad.getTabla()) + "(){")) {
                        Utilidades.searchAdd(ruta, generarGet(entidad), "private static final long serialVersionUID = 1L;", false);
                    }
                    if (!Utilidades.search(ruta, "public void set" + Utilidades.letterToUpper(entidad.getTabla()) + "(MenuElement " + entidad.getTabla().toLowerCase() + "){")) {
                        Utilidades.searchAdd(ruta, generarSet(entidad), "private static final long serialVersionUID = 1L;", false);
                    }
                }

                /*
                 verificar si habilita  barraMenu
                 */
//                for (Integer i = 0; i < mySesion.getNumeroMenuBar(); i++) {
                for (String s : mySesion.getMenubarList()) {
                    String barra = "menuBar" + s + " = activo;";
                    Utilidades.searchAdd(ruta, barra, "public void enabledAll(Boolean activo) {", false);
                }

                /*
                verificar si los elementos estan en habilitartodo
                 */
                for (Entidad entidad : mySesion.getEntidadList()) {
                    String menuelemento = entidad.getTabla().toLowerCase() + ".initialize(activo);";
                    Utilidades.searchAdd(ruta, menuelemento, "public void enabledAll(Boolean activo) {", false);

                }

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
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import com.avbravo.avbravoutils.menu.MenuElement;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");

                    fw.write("public class ApplicationMenu implements Serializable {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("    public ApplicationMenu() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    /*" + "\r\n");
                    fw.write("     barra de menu" + "\r\n");
                    fw.write("     */" + "\r\n");
//                    for (Integer i = 0; i < mySesion.getNumeroMenuBar(); i++) {
                    for (String s : mySesion.getMenubarList()) {
                        fw.write("    private Boolean menuBar" + s + " = false;" + "\r\n");
                    }
                    fw.write("        " + "\r\n");
                    fw.write("    /*" + "\r\n");
                    fw.write("     elementos" + "\r\n");
                    fw.write("     */" + "\r\n");
                    for (Entidad entidad : mySesion.getEntidadList()) {
                        fw.write("   MenuElement " + entidad.getTabla().toLowerCase() + " = new MenuElement();" + "\r\n");
                    }
                    fw.write("        " + "\r\n");
                    fw.write("    public void enabledAll(Boolean activo) {" + "\r\n");
//                    for (Integer i = 0; i < mySesion.getNumeroMenuBar(); i++) {
                    for (String s : mySesion.getMenubarList()) {
                        fw.write("        menuBar" + s + " = activo;" + "\r\n");
                    }
                    for (Entidad entidad : mySesion.getEntidadList()) {
                        fw.write("       " + entidad.getTabla().toLowerCase() + ".initialize(activo);" + "\r\n");
                    }

                    fw.write("        " + "\r\n");
                    fw.write("    }" + "\r\n");
                    //getset menubar
                    for (String s : mySesion.getMenubarList()) {

                        fw.write("    public Boolean getMenuBar" + s + "(){" + "\r\n");
                        fw.write("        return menuBar" + s + ";" + "\r\n");
                        fw.write("    }" + "\r\n");

                        fw.write("    public void setMenuBar" + s + "(Boolean menuBar" + s + "){" + "\r\n");
                        fw.write("        this.menuBar" + s + "= menuBar" + s + ";" + "\r\n");
                        fw.write("    }" + "\r\n");

                    }
                    //getset menuElement
                    for (Entidad entidad : mySesion.getEntidadList()) {
                        fw.write("   public MenuElement get" + Utilidades.letterToUpper(entidad.getTabla()) + "(){" + "\r\n");
                        fw.write("       return " + entidad.getTabla().toLowerCase() + ";" + "\r\n");
                        fw.write("   }" + "\r\n");

                        fw.write("   public void set" + Utilidades.letterToUpper(entidad.getTabla()) + "(MenuElement " + entidad.getTabla().toLowerCase() + "){" + "\r\n");
                        fw.write("       this." + entidad.getTabla().toLowerCase() + "= " + entidad.getTabla().toLowerCase() + ";" + "\r\n");
                        fw.write("   }" + "\r\n");
                    }

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

    private String enabledAll() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    /**" + "\r\n";
            texto += "     * enabledAll" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "   public void enabledAll(Boolean activo) {" + "\r\n";
            texto += "        " + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("enabledAll()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private void generarImport(String ruta) {
        try {
            /**
             * agregar los imports
             */

            Utilidades.searchAdd(ruta, "import com.avbravo.avbravoutils.menu.MenuElement;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Inject;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarImport() " + e.getLocalizedMessage());
        }
    }

    /**
     * genera el get
     *
     * @param entidad
     * @return
     */
    private String generarGet(Entidad entidad) {
        try {
            String texto = "";
            texto += "" + "\r\n";
            texto += "   public MenuElement get" + Utilidades.letterToUpper(entidad.getTabla()) + "(){" + "\r\n";
            texto += "       return " + entidad.getTabla().toLowerCase() + ";" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("validarRoles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     *
     * @param entidad
     * @return
     */
    private String generarSet(Entidad entidad) {
        try {
            String texto = "";
            texto += "" + "\r\n";

            texto += "   public void set" + Utilidades.letterToUpper(entidad.getTabla()) + "(MenuElement " + entidad.getTabla().toLowerCase() + "){" + "\r\n";
            texto += "       this." + entidad.getTabla().toLowerCase() + "= " + entidad.getTabla().toLowerCase() + ";" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("validarRoles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarGetMenuBar(String s) {
        try {
            String texto = "";
            texto += "" + "\r\n";

            texto += "    public Boolean getMenuBar" + s + "(){" + "\r\n";
            texto += "        return menuBar" + s + ";" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarGetMenuBar()  " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     *
     * @param entidad
     * @return
     */
    private String generarSetMenuBar(String s) {
        try {
            String texto = "";
            texto += "" + "\r\n";

            texto += "    public void setMenuBar" + s + "(Boolean menuBar" + s + "){" + "\r\n";
            texto += "        this.menuBar" + s + "= menuBar" + s + ";" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarSetMenuBar()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
