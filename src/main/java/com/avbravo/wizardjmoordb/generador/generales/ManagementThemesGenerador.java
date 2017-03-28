/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.generales;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
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
public class ManagementThemesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ManagementThemesGenerador.class.getName());

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

            procesar("ManagementThemes.java", proyectoJEE.getPathUtil() + "ManagementThemes.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class ManagementThemes implements Serializable", false);
                Utilidades.searchAdd(ruta, "private String tema = \"aristo\";", "public class ManagementThemes implements Serializable", false);
                Utilidades.searchAdd(ruta, "private String temaPredeterminado = \"aristo\";", "public class ManagementThemes implements Serializable", false);
                Utilidades.searchAdd(ruta, "private Map<String, String> themes;", "public class ManagementThemes implements Serializable", false);


                /*
                * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public Map<String, String> getThemes()", getThemes(), "public class ManagementThemes implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String getTemaPredeterminado()", getTemaPredeterminado(), "public class ManagementThemes implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setTemaPredeterminado", setTemaPredeterminado(), "public class ManagementThemes implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setThemes", setThemes(), "public class ManagementThemes implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String getTema()", getTema(), "public class ManagementThemes implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setTema(", setTema(), "public class ManagementThemes implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String cambiar()", cambiar(), "public class ManagementThemes implements Serializable", false);

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
            Utilidades.searchAdd(ruta, "import java.util.Map;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.TreeMap;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);

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
                    fw.write("package " + proyectoJEE.getPaquete() + ".generales;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import java.util.Map;" + "\r\n");
                    fw.write("import java.util.TreeMap;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("public class ManagementThemes implements Serializable {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("  private String tema = \"aristo\";" + "\r\n");
                    fw.write("    private String temaPredeterminado = \"aristo\";" + "\r\n");
                    fw.write("    private Map<String, String> themes;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Map<String, String> getThemes() {" + "\r\n");
                    fw.write("        themes = new TreeMap<String, String>();" + "\r\n");
                    fw.write("        themes.put(\"afterdark\", \"afterdark\");" + "\r\n");
                    fw.write("        themes.put(\"afternoon\", \"afternoon\");" + "\r\n");
                    fw.write("        themes.put(\"afterwork\", \"afterwork\");" + "\r\n");
                    fw.write("        themes.put(\"aristo\", \"aristo\");" + "\r\n");
                    fw.write("        themes.put(\"black-tie\", \"black-tie\");" + "\r\n");
                    fw.write("         themes.put(\"bootstrap\",\"bootstrap\");" + "\r\n");
                    fw.write("        themes.put(\"blitzer\", \"blitzer\");" + "\r\n");
                    fw.write("        themes.put(\"bluesky\", \"bluesky\");" + "\r\n");
                    fw.write("        themes.put(\"casablanca\", \"casablanca\");" + "\r\n");
                    fw.write("        themes.put(\"cupertino\", \"cupertino\");" + "\r\n");
                    fw.write("        themes.put(\"cruze\", \"cruze\");" + "\r\n");
                    fw.write("        themes.put(\"dark-hive\", \"dark-hive\");" + "\r\n");
                    fw.write("        themes.put(\"dot-luv\", \"dot-luv\");" + "\r\n");
                    fw.write("        themes.put(\"eggplant\", \"eggplant\");" + "\r\n");
                    fw.write("        themes.put(\"excite-bike\", \"excite-bike\");" + "\r\n");
                    fw.write("        themes.put(\"flick\", \"flick\");" + "\r\n");
                    fw.write("        themes.put(\"glass-x\", \"glass-x\");" + "\r\n");
                    fw.write("        themes.put(\"home\", \"home\");" + "\r\n");
                    fw.write("        themes.put(\"hot-sneaks\", \"hot-sneaks\");" + "\r\n");
                    fw.write("        themes.put(\"humanity\", \"humanity\");" + "\r\n");
                    fw.write("        themes.put(\"le-frog\", \"le-frog\");" + "\r\n");
                    fw.write("        themes.put(\"midnight\", \"midnight\");" + "\r\n");
                    fw.write("        themes.put(\"mint-choc\", \"mint-choc\");" + "\r\n");
                    fw.write("        themes.put(\"none\", \"none\");" + "\r\n");
                    fw.write("        themes.put(\"overcast\", \"overcast\");" + "\r\n");
                    fw.write("        themes.put(\"pepper-grinder\", \"pepper-grinder\");" + "\r\n");
                    fw.write("        themes.put(\"redmond\", \"redmond\");" + "\r\n");
                    fw.write("        themes.put(\"rocket\", \"rocket\");" + "\r\n");
                    fw.write("        themes.put(\"sam\", \"sam\");" + "\r\n");
                    fw.write("        themes.put(\"smoothness\", \"smoothness\");" + "\r\n");
                    fw.write("        themes.put(\"south-street\", \"south-street\");" + "\r\n");
                    fw.write("        themes.put(\"start\", \"start\");" + "\r\n");
                    fw.write("        themes.put(\"sunny\", \"sunny\");" + "\r\n");
                    fw.write("        themes.put(\"swanky-purse\", \"swanky-purse\");" + "\r\n");
                    fw.write("        themes.put(\"trontastic\", \"trontastic\");" + "\r\n");
                    fw.write("        themes.put(\"trontastic\", \"trontastic\");" + "\r\n");
                    fw.write("        themes.put(\"trontastic\", \"trontastic\");" + "\r\n");
                    fw.write("        themes.put(\"ui-darkness\", \"ui-darkness\");" + "\r\n");
                    fw.write("        themes.put(\"ui-lightness\", \"ui-lightness\");" + "\r\n");
                    fw.write("        themes.put(\"vader\", \"vader\");" + "\r\n");
                    fw.write("        return themes;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String getTemaPredeterminado() {" + "\r\n");
                    fw.write("        return temaPredeterminado;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setTemaPredeterminado(String temaPredeterminado) {" + "\r\n");
                    fw.write("        this.temaPredeterminado = temaPredeterminado;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setThemes(Map<String, String> themes) {" + "\r\n");
                    fw.write("        this.themes = themes;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String getTema() {" + "\r\n");
                    fw.write("        return tema;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setTema(String tema) {" + "\r\n");
                    fw.write("        this.tema = tema;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String cambiar() {" + "\r\n");
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

    private String getThemes() {
        try {

            String texto = "";
            texto += "    public Map<String, String> getThemes() {" + "\r\n";
            texto += "        themes = new TreeMap<String, String>();" + "\r\n";
            texto += "        themes.put(\"afterdark\", \"afterdark\");" + "\r\n";
            texto += "        themes.put(\"afternoon\", \"afternoon\");" + "\r\n";
            texto += "        themes.put(\"afterwork\", \"afterwork\");" + "\r\n";
            texto += "        themes.put(\"aristo\", \"aristo\");" + "\r\n";
            texto += "        themes.put(\"black-tie\", \"black-tie\");" + "\r\n";
            texto += "         themes.put(\"bootstrap\",\"bootstrap\");" + "\r\n";
            texto += "        themes.put(\"blitzer\", \"blitzer\");" + "\r\n";
            texto += "        themes.put(\"bluesky\", \"bluesky\");" + "\r\n";
            texto += "        themes.put(\"casablanca\", \"casablanca\");" + "\r\n";
            texto += "        themes.put(\"cupertino\", \"cupertino\");" + "\r\n";
            texto += "        themes.put(\"cruze\", \"cruze\");" + "\r\n";
            texto += "        themes.put(\"dark-hive\", \"dark-hive\");" + "\r\n";
            texto += "        themes.put(\"dot-luv\", \"dot-luv\");" + "\r\n";
            texto += "        themes.put(\"eggplant\", \"eggplant\");" + "\r\n";
            texto += "        themes.put(\"excite-bike\", \"excite-bike\");" + "\r\n";
            texto += "        themes.put(\"flick\", \"flick\");" + "\r\n";
            texto += "        themes.put(\"glass-x\", \"glass-x\");" + "\r\n";
            texto += "        themes.put(\"home\", \"home\");" + "\r\n";
            texto += "        themes.put(\"hot-sneaks\", \"hot-sneaks\");" + "\r\n";
            texto += "        themes.put(\"humanity\", \"humanity\");" + "\r\n";
            texto += "        themes.put(\"le-frog\", \"le-frog\");" + "\r\n";
            texto += "        themes.put(\"midnight\", \"midnight\");" + "\r\n";
            texto += "        themes.put(\"mint-choc\", \"mint-choc\");" + "\r\n";
            texto += "        themes.put(\"none\", \"none\");" + "\r\n";
            texto += "        themes.put(\"overcast\", \"overcast\");" + "\r\n";
            texto += "        themes.put(\"pepper-grinder\", \"pepper-grinder\");" + "\r\n";
            texto += "        themes.put(\"redmond\", \"redmond\");" + "\r\n";
            texto += "        themes.put(\"rocket\", \"rocket\");" + "\r\n";
            texto += "        themes.put(\"sam\", \"sam\");" + "\r\n";
            texto += "        themes.put(\"smoothness\", \"smoothness\");" + "\r\n";
            texto += "        themes.put(\"south-street\", \"south-street\");" + "\r\n";
            texto += "        themes.put(\"start\", \"start\");" + "\r\n";
            texto += "        themes.put(\"sunny\", \"sunny\");" + "\r\n";
            texto += "        themes.put(\"swanky-purse\", \"swanky-purse\");" + "\r\n";
            texto += "        themes.put(\"trontastic\", \"trontastic\");" + "\r\n";
            texto += "        themes.put(\"trontastic\", \"trontastic\");" + "\r\n";
            texto += "        themes.put(\"trontastic\", \"trontastic\");" + "\r\n";
            texto += "        themes.put(\"ui-darkness\", \"ui-darkness\");" + "\r\n";
            texto += "        themes.put(\"ui-lightness\", \"ui-lightness\");" + "\r\n";
            texto += "        themes.put(\"vader\", \"vader\");" + "\r\n";
            texto += "        return themes;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getThemes()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getTemaPredeterminado() {
        try {

            String texto = "";
            texto += "    public String getTemaPredeterminado() {" + "\r\n";
            texto += "        return temaPredeterminado;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getTemaPredeterminado()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setTemaPredeterminado() {
        try {

            String texto = "";
            texto += "    public void setTemaPredeterminado(String temaPredeterminado) {" + "\r\n";
            texto += "        this.temaPredeterminado = temaPredeterminado;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setTemaPredeterminado()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setThemes() {
        try {

            String texto = "";
            texto += "    public void setThemes(Map<String, String> themes) {" + "\r\n";
            texto += "        this.themes = themes;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setThemes()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getTema() {
        try {

            String texto = "";
            texto += "    public String getTema() {" + "\r\n";
            texto += "        return tema;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getTema()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setTema() {
        try {

            String texto = "";
            texto += "    public void setTema(String tema) {" + "\r\n";
            texto += "        this.tema = tema;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setTema()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String cambiar() {
        try {

            String texto = "";
            texto += "    public String cambiar() {" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("cambiar()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
