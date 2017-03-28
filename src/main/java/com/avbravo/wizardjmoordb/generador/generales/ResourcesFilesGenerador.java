/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.generales;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
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
public class ResourcesFilesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ResourcesFilesGenerador.class.getName());

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

            procesar("ResourcesFiles.java", rutas.getPathUtil() + "ResourcesFiles.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class ResourcesFiles implements Serializable", false);
                Utilidades.searchAdd(ruta, "Locale currentLocale;", "public class ResourcesFiles implements Serializable", false);
                Utilidades.searchAdd(ruta, "ResourceBundle mrb;", "public class ResourcesFiles implements Serializable", false);
                Utilidades.searchAdd(ruta, "ResourceBundle arb;", "public class ResourcesFiles implements Serializable", false);
                Utilidades.searchAdd(ruta, "ResourceBundle merb;", "public class ResourcesFiles implements Serializable", false);
                Utilidades.searchAdd(ruta, "ResourceBundle erb;", "public class ResourcesFiles implements Serializable", false);
                Utilidades.searchAdd(ruta, "ResourceBundle frb;", "public class ResourcesFiles implements Serializable", false);

                /*
                * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public ResourcesFiles()", resourcesFiles(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void init()", init(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void saveLocale()", saveLocale(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Locale getCurrentLocal", getCurrentLocale(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setCurrentLocale", setCurrentLocale(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public ResourceBundle getMrb", getMrb(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setMrb", setMrb(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public ResourceBundle getArb()", getArb(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setArb", setArb(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String getMensajeMrb", getMensajeMrb(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public String getMensajeArb", getMensajeArb(), "public class ResourcesFiles implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public ResourceBundle getMerb", getMerb(), "public class ResourcesFiles implements Serializable", false);
  Utilidades.addNotFoundMethod(ruta, "public void setMerb", getMerb(), "public class ResourcesFiles implements Serializable", false);
  Utilidades.addNotFoundMethod(ruta, "public ResourceBundle getErb", getErb(), "public class ResourcesFiles implements Serializable", false);
  Utilidades.addNotFoundMethod(ruta, "public void setErb", setErb(), "public class ResourcesFiles implements Serializable", false);
  Utilidades.addNotFoundMethod(ruta, "public ResourceBundle getFrb", getFrb(), "public class ResourcesFiles implements Serializable", false);
  Utilidades.addNotFoundMethod(ruta, "public void setFrb", setFrb(), "public class ResourcesFiles implements Serializable", false);



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
            Utilidades.searchAdd(ruta, "import java.util.ResourceBundle;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.annotation.PostConstruct;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.FacesContext;", "package", false);
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
                    fw.write("package " + mySesion.getPaquete() + ".generales;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import java.util.Locale;" + "\r\n");
                    fw.write("import java.util.ResourceBundle;" + "\r\n");
                    fw.write("import javax.annotation.PostConstruct;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("public class ResourcesFiles implements Serializable {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");
                    fw.write("    Locale currentLocale;" + "\r\n");
                    fw.write("    ResourceBundle mrb; //for messages atributos" + "\r\n");
                    fw.write("    ResourceBundle arb; //for application" + "\r\n");
                    fw.write("    ResourceBundle merb; //menu" + "\r\n");
                    fw.write("    ResourceBundle erb;  // entity" + "\r\n");
                    fw.write("    ResourceBundle frb;  // form" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public ResourcesFiles() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @PostConstruct" + "\r\n");
                    fw.write("    public void init() {" + "\r\n");
                    fw.write("        saveLocale();" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void saveLocale() {" + "\r\n");
                    fw.write("        currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();" + "\r\n");
                    fw.write("        mrb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.messages\"," + "\r\n");
                    fw.write("                currentLocale);" + "\r\n");
                    fw.write("        arb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.application\"," + "\r\n");
                    fw.write("                currentLocale);" + "\r\n");
                    fw.write("        merb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.menu\"," + "\r\n");
                    fw.write("                currentLocale);" + "\r\n");
                    fw.write("        erb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.entity\"," + "\r\n");
                    fw.write("                currentLocale);" + "\r\n");
                    fw.write("        frb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.form\"," + "\r\n");
                    fw.write("                currentLocale);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("    public Locale getCurrentLocale() {" + "\r\n");
                    fw.write("        return currentLocale;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setCurrentLocale(Locale currentLocale) {" + "\r\n");
                    fw.write("        this.currentLocale = currentLocale;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public ResourceBundle getMrb() {" + "\r\n");
                    fw.write("        return mrb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setMrb(ResourceBundle mrb) {" + "\r\n");
                    fw.write("        this.mrb = mrb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public ResourceBundle getArb() {" + "\r\n");
                    fw.write("        return arb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setArb(ResourceBundle arb) {" + "\r\n");
                    fw.write("        this.arb = arb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    /*" + "\r\n");
                    fw.write("     *Devuelve el mensaje Mrb" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String getMensajeMrb(String mensaje) {" + "\r\n");
                    fw.write("        return mrb.getString(mensaje);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    /*" + "\r\n");
                    fw.write("     *Devuelve el mensaje Arb" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String getMensajeArb(String mensaje) {" + "\r\n");
                    fw.write("        return arb.getString(mensaje);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public ResourceBundle getMerb() {" + "\r\n");
                    fw.write("        return merb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setMerb(ResourceBundle merb) {" + "\r\n");
                    fw.write("        this.merb = merb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public ResourceBundle getErb() {" + "\r\n");
                    fw.write("        return erb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setErb(ResourceBundle erb) {" + "\r\n");
                    fw.write("        this.erb = erb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public ResourceBundle getFrb() {" + "\r\n");
                    fw.write("        return frb;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setFrb(ResourceBundle frb) {" + "\r\n");
                    fw.write("        this.frb = frb;" + "\r\n");
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

    private String resourcesFiles() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    public ResourcesFiles() {" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String init() {
        try {

            String texto = "";
            texto += "    @PostConstruct" + "\r\n";
            texto += "    public void init() {" + "\r\n";
            texto += "        saveLocale();" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("init()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String saveLocale() {
        try {

            String texto = "";
            texto += "    public void saveLocale() {" + "\r\n";
            texto += "        currentLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();" + "\r\n";
            texto += "        mrb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.messages\"," + "\r\n";
            texto += "                currentLocale);" + "\r\n";
            texto += "        arb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.application\"," + "\r\n";
            texto += "                currentLocale);" + "\r\n";
            texto += "        merb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.menu\"," + "\r\n";
            texto += "                currentLocale);" + "\r\n";
            texto += "        erb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.entity\"," + "\r\n";
            texto += "                currentLocale);" + "\r\n";
            texto += "        frb = ResourceBundle.getBundle(\""+mySesion.getPaquete()+ ".properties.form\"," + "\r\n";
            texto += "                currentLocale);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getCurrentLocale() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    public Locale getCurrentLocale() {" + "\r\n";
            texto += "        return currentLocale;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setCurrentLocale() {
        try {

            String texto = "";
            texto += "    public void setCurrentLocale(Locale currentLocale) {" + "\r\n";
            texto += "        this.currentLocale = currentLocale;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getMrb() {
        try {

            String texto = "";
            texto += "    public ResourceBundle getMrb() {" + "\r\n";
            texto += "        return mrb;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setMrb() {
        try {

            String texto = "";
            texto += "    public void setMrb(ResourceBundle mrb) {" + "\r\n";
            texto += "        this.mrb = mrb;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getArb() {
        try {

            String texto = "";
            texto += "    public ResourceBundle getArb() {" + "\r\n";
            texto += "        return arb;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setArb() {
        try {

            String texto = "";
            texto += "    public void setArb(ResourceBundle arb) {" + "\r\n";
            texto += "        this.arb = arb;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getMensajeMrb() {
        try {

            String texto = "";
            texto += "    /*" + "\r\n";
            texto += "     *Devuelve el mensaje Mrb" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "" + "\r\n";
            texto += "    public String getMensajeMrb(String mensaje) {" + "\r\n";
            texto += "        return mrb.getString(mensaje);" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getMensajeArb() {
        try {

            String texto = "";
            texto += "    /*" + "\r\n";
            texto += "     *Devuelve el mensaje Arb" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "" + "\r\n";
            texto += "    public String getMensajeArb(String mensaje) {" + "\r\n";
            texto += "        return arb.getString(mensaje);" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getMerb() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    public ResourceBundle getMerb() {" + "\r\n";
            texto += "        return merb;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }
    
    private String setMerb() {
        try {

            String texto = "";
                     texto += "    public void setMerb(ResourceBundle merb) {" + "\r\n";
                     texto += "        this.merb = merb;" + "\r\n";
                     texto += "    }" + "\r\n";
                     texto += "" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }
    
    private String getErb() {
        try {

            String texto = "";
                     texto += "    public ResourceBundle getErb() {" + "\r\n";
                     texto += "        return erb;" + "\r\n";
                     texto += "    }" + "\r\n";
                     texto += "" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }
    
     private String setErb() {
        try {

            String texto = "";
                     texto += "    public void setErb(ResourceBundle erb) {" + "\r\n";
                     texto += "        this.erb = erb;" + "\r\n";
                     texto += "    }" + "\r\n";
                     texto += "" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getFrb() {
        try {

            String texto = "";
                     texto += "    public ResourceBundle getFrb() {" + "\r\n";
                     texto += "        return frb;" + "\r\n";
                     texto += "    }" + "\r\n";
                     texto += "" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

     private String setFrb() {
        try {

            String texto = "";
                     texto += "    public void setFrb(ResourceBundle frb) {" + "\r\n";
                     texto += "        this.frb = frb;" + "\r\n";
                     texto += "    }" + "\r\n";
                return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }  
     
}
