/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.gen;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoEJB;
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
public class ConverterGenerador implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ConverterGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoEJB proyectoEJB;

    /**
     * Creates a new instance of Facade
     */
    public ConverterGenerador() {
    }

    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            for (Entidad e : mySesion.getEntidadList()) {
                procesar(e, e.getTabla(), proyectoEJB.getPathConverter() + e.getTabla() + "Converter.java");
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(Entidad entidad, String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, entidad, archivo);
            }
          

            /**
             * agregar los imports
             */
            if (!Utilidades.search(ruta, "import javax.enterprise.context.RequestScoped;")) {
                Utilidades.add(ruta, "@Named", "import javax.enterprise.context.RequestScoped;", false);
            }
            if (!Utilidades.search(ruta, "import javax.inject.Inject;")) {
                Utilidades.add(ruta, "@Named", "import javax.inject.Inject;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.component.UIComponent;")) {
                Utilidades.add(ruta, "@Named", "import javax.faces.component.UIComponent;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.context.FacesContext;")) {
                Utilidades.add(ruta, "@Named", "import javax.faces.context.FacesContext;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.convert.Converter;")) {
                Utilidades.add(ruta, "@Named", "import javax.faces.convert.Converter;", false);
            }
            if (!Utilidades.search(ruta, "import javax.inject.Named;")) {
                Utilidades.add(ruta, "@Named", "import javax.inject.Named;", false);
            }
            
            if (!Utilidades.search(ruta, "import " + proyectoEJB.getPaquete() + ".entity.*;")) {
                Utilidades.add(ruta, "import javax.inject.Named;", "import " + proyectoEJB.getPaquete() + ".entity.*;", false);
            }
            if (!Utilidades.search(ruta, "import " + proyectoEJB.getPaquete() + ".ejb.*;")) {
                Utilidades.add(ruta, "import javax.inject.Named;", "import " + proyectoEJB.getPaquete() + ".ejb.*;", false);
            }
            
            /**
             * generar los metodos
             */
            if (!Utilidades.search(ruta, "public Object getAsObject")) {
                Utilidades.addBeforeEnd(ruta, "}", getAsObject(entidad, archivo));
            }
            if (!Utilidades.search(ruta, "public String getAsString(")) {
                Utilidades.addBeforeEnd(ruta, "}", getAsString(entidad, archivo));
            }

            //maximo
        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    private String getAsObject(Entidad entidad, String archivo) {
        try {
            String atributoprimario = "";
            Boolean esInteger = false;
            String entity = Utilidades.letterToLower(archivo);
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsPrimaryKey()) {
                    atributoprimario = Utilidades.letterToUpper(a.getNombre());
                    esInteger = a.getTipo().equals("Integer");
                }
            }
            String texto = "";

            texto += "    @Override" + "\r\n";
            texto += "    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {" + "\r\n";
            texto += "        " + archivo + " " + entity + " = new " + archivo + "();" + "\r\n";
            texto += "         try{" + "\r\n";
            texto += "            if(!s.equals(\"null\")){" + "\r\n";
            if (esInteger) {
                texto += "            " + entity + " = " + entity + "Facade.findById(Integer.parseInt(s));" + "\r\n";
            } else {
                texto += "            " + entity + " = " + entity + "Facade.findById(s);" + "\r\n";
            }

            texto += "              }" + "\r\n";
            texto += "           } catch (Exception e) {" + "\r\n";
            texto += "             JSFUtil.addErrorMessage(\"getAsObject()\" + e.getLocalizedMessage());" + "\r\n";
            texto += "           }" + "\r\n";
            texto += "           return " + entity+";" + "\r\n";
            texto += "           }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAsObject()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getAsString(Entidad entidad, String archivo) {
        try {
            String atributoprimario = "";
            Boolean esInteger = false;
            String entity = Utilidades.letterToLower(archivo);
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsPrimaryKey()) {
                    atributoprimario = Utilidades.letterToUpper(a.getNombre());
                    esInteger = a.getTipo().equals("Integer");
                }
            }
            String texto = "";

            texto += "    @Override" + "\r\n";
            texto += "    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {" + "\r\n";
            texto += "        String r = \"\";" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            if (o instanceof " + archivo + ") {" + "\r\n";
            texto += "                " + archivo + " " + entity + " = (" + archivo + ") o;" + "\r\n";
            texto += "                r = String.valueOf(" + entity + ".get" + atributoprimario + "());" + "\r\n";

            texto += "            }else if (o instanceof String) {" + "\r\n";
            texto += "               r = (String) o;" + "\r\n";
            texto += "            }" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"getAsString()\" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return r;" + "\r\n";
            texto += "        }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAsString()  " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * deleteAll
     *
     * @param entidad
     * @param archivo
     * @return
     */
    private Boolean crearFile(String path, Entidad entidad, String archivo) {
        try {
            String atributoprimario = "";
            Boolean esInteger = false;
            String entity = Utilidades.letterToLower(archivo);
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsPrimaryKey()) {
                  atributoprimario = Utilidades.letterToUpper(a.getNombre());
                    esInteger = a.getTipo().equals("Integer");
                }
            }
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
                    fw.write("package " + proyectoEJB.getPaquete() + ".converter;" + "\r\n");
                        fw.write("" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".entity.*;" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".ejb.*;" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".generales.JSFUtil;" + "\r\n");
                    fw.write("import javax.enterprise.context.RequestScoped;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import javax.faces.component.UIComponent;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.faces.convert.Converter;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author " +mySesion.getUsername()+ "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("@Named" + "\r\n");
                    fw.write("@RequestScoped" + "\r\n");
                    fw.write("public class " + archivo + "Converter implements Converter {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + archivo + "Facade " + entity + "Facade;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {" + "\r\n");
                    fw.write("        " + archivo + " " + entity + " = new " + archivo + "();" + "\r\n");
                    fw.write("         try{" + "\r\n");
                    fw.write("            if(!s.equals(\"null\")){" + "\r\n");
                    if (esInteger) {
                        fw.write("            " + entity + " = " + entity + "Facade.findById(Integer.parseInt(s));" + "\r\n");
                    } else {
                        fw.write("            " + entity + " = " + entity + "Facade.findById(s);" + "\r\n");
                    }

                    fw.write("              }" + "\r\n");
                    fw.write("           } catch (Exception e) {" + "\r\n");
                    fw.write("             JSFUtil.addErrorMessage(\"getAsObject()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("           }" + "\r\n");
                    fw.write("           return " + entity+";" + "\r\n");
                    fw.write("           }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {" + "\r\n");
                    fw.write("        String r = \"\";" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            if (o instanceof " + archivo + ") {" + "\r\n");
                    fw.write("                " + archivo + " " + entity + " = (" + archivo + ") o;" + "\r\n");
                    fw.write("                r = String.valueOf(" + entity + ".get" + atributoprimario + "());" + "\r\n");

                    fw.write("            }else if (o instanceof String) {" + "\r\n");
                    fw.write("               r = (String) o;" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"getAsString()\" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return r;" + "\r\n");
                    fw.write("        }" + "\r\n");
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
