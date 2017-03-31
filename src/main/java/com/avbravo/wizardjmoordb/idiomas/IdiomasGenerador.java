/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.idiomas;

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
public class IdiomasGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(IdiomasGenerador.class.getName());

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

            procesar("Idiomas.java", proyectoJEE.getPathUtil() + "Idiomas.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class Idiomas implements", false);
                Utilidades.searchAdd(ruta, "@Inject", "public class Idiomas implements", false);
                Utilidades.searchAdd(ruta, "ResourcesFiles rf;", "@Inject", false);
                Utilidades.searchAdd(ruta, "private String locale = Locale.getDefault().getDisplayLanguage();", "public class Idiomas implements", false);
                /**
                 * generar los metodos
                 */

                Utilidades.addNotFoundMethod(ruta, "public Idiomas", idiomas(), "public class Idiomas implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setLocale", setLocale(), "public class Idiomas implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public synchronized String getLocale", getLocale(), "public class Idiomas implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String englishAction()", englishAction(), "public class Idiomas implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String spanishAction()", spanishAction(), "public class Idiomas implements Serializable", false);

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
            Utilidades.searchAdd(ruta, "import java.util.Locale;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.FacesContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Inject;", "package", false);
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
                    fw.write("package " + proyectoJEE.getPaquete() + ".util;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import java.util.Locale;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("public class Idiomas implements Serializable {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ResourcesFiles rf;" + "\r\n");
                    fw.write("    private String locale = Locale.getDefault().getDisplayLanguage();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setLocale(String locale) {" + "\r\n");
                    fw.write("        this.locale = locale;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public synchronized String getLocale() {" + "\r\n");
                    fw.write("        return locale;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public synchronized String changeLanguage() {" + "\r\n");
                    fw.write("        return \"changed\";" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Idiomas() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String englishAction() {" + "\r\n");
                    fw.write("        FacesContext context = FacesContext.getCurrentInstance();" + "\r\n");
                    fw.write("        context.getViewRoot().setLocale(Locale.ENGLISH);" + "\r\n");
                    fw.write("        this.locale = \"en\";" + "\r\n");
                    fw.write("        rf.saveLocale();" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String spanishAction() {" + "\r\n");
                    fw.write("        FacesContext context = FacesContext.getCurrentInstance();" + "\r\n");
                    fw.write("        context.getViewRoot().setLocale(new Locale(\"es\"));" + "\r\n");
                    fw.write("        this.locale = \"es\";" + "\r\n");
                    fw.write("        rf.saveLocale();" + "\r\n");
                    fw.write("        return null;" + "\r\n");
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

    private String idiomas() {
        try {

            String texto = "";

            texto += "  public Idiomas() {" + "\r\n";
            texto += "  }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("convertToEntityAttribute()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setLocale() {
        try {

            String texto = "";

            texto += "  public void setLocale(String locale) {" + "\r\n";
            texto += "     this.locale = locale;" + "\r\n";
            texto += "  }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("convertToEntityAttribute()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getLocale() {
        try {

            String texto = "";

            texto += "  public synchronized String getLocale() {" + "\r\n";
            texto += "     return locale;" + "\r\n";
            texto += "  }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getLocale()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String changeLanguage() {
        try {

            String texto = "";

            texto += "  public synchronized String changeLanguage() {" + "\r\n";
            texto += "     return \"changed\";" + "\r\n";
            texto += "  }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("changeLanguage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String englishAction() {
        try {

            String texto = "";

            texto += "   public String englishAction() {" + "\r\n";
            texto += "        FacesContext context = FacesContext.getCurrentInstance();" + "\r\n";
            texto += "        context.getViewRoot().setLocale(Locale.ENGLISH);" + "\r\n";
            texto += "        this.locale = \"en\";" + "\r\n";
            texto += "        rf.saveLocale();" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("englishAction()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String spanishAction() {
        try {

            String texto = "";

            texto += "    public String spanishAction() {" + "\r\n";
            texto += "        FacesContext context = FacesContext.getCurrentInstance();" + "\r\n";
            texto += "        context.getViewRoot().setLocale(new Locale(\"es\"));" + "\r\n";
            texto += "        this.locale = \"es\";" + "\r\n";
            texto += "        rf.saveLocale();" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("spanishAction()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
