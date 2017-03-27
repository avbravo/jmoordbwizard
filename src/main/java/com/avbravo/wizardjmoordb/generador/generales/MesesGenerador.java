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
public class MesesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MesesGenerador.class.getName());

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

            procesar("Meses.java", rutas.getPathGenerales() + "Meses.java");

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

                Utilidades.searchAdd(ruta, "private List<String> listMeses = new ArrayList<>();", "public class Meses implements Serializable", false);


                /*
                * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public Meses()", meses(), "public class Meses implements Serializable", false);
 Utilidades.addNotFoundMethod(ruta, "public List<String> getListMeses()", getListMeses(), "public class Meses implements Serializable", false);
 Utilidades.addNotFoundMethod(ruta, "public void setListMeses", setListMeses(), "public class Meses implements Serializable", false);
 Utilidades.addNotFoundMethod(ruta, "public Integer numeroMes", numeroMes(), "public class Meses implements Serializable", false);
 Utilidades.addNotFoundMethod(ruta, "public String getNombreMes",getNombreMes(), "public class Meses implements Serializable", false);


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
            Utilidades.searchAdd(ruta, "import java.util.ArrayList;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
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
                    fw.write("package " + mySesion.getPaquete() + ".generales;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import java.util.ArrayList;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("public class Meses implements Serializable {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("private List<String> listMeses = new ArrayList<>();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    /**" + "\r\n");
                    fw.write("     * Creates a new instance of Meses" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public Meses() {" + "\r\n");
                    fw.write("        listMeses.add(\"Enero\");" + "\r\n");
                    fw.write("        listMeses.add(\"Febrero\");" + "\r\n");
                    fw.write("        listMeses.add(\"Marzo\");" + "\r\n");
                    fw.write("        listMeses.add(\"Abril\");" + "\r\n");
                    fw.write("        listMeses.add(\"Mayo\");" + "\r\n");
                    fw.write("        listMeses.add(\"Junio\");" + "\r\n");
                    fw.write("        listMeses.add(\"Julio\");" + "\r\n");
                    fw.write("        listMeses.add(\"Agosto\");" + "\r\n");
                    fw.write("        listMeses.add(\"Septiembre\");" + "\r\n");
                    fw.write("        listMeses.add(\"Octubre\");" + "\r\n");
                    fw.write("        listMeses.add(\"Noviembre\");" + "\r\n");
                    fw.write("        listMeses.add(\"Diciembre\");" + "\r\n");

                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("   public List<String> getListMeses() {" + "\r\n");
                    fw.write("        return listMeses;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setListMeses(List<String> listMeses) {" + "\r\n");
                    fw.write("        this.listMeses = listMeses;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer numeroMes(String mes) {" + "\r\n");
                    fw.write("        Integer i = -1;" + "\r\n");
                    fw.write("        for (String l : listMeses) {" + "\r\n");
                    fw.write("            i++;" + "\r\n");
                    fw.write("            if (l.equals(mes)) {" + "\r\n");
                    fw.write("                return i +1;" + "\r\n");
                    fw.write("            } " + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        return -1;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    public String getNombreMes(Integer mes){" + "\r\n");
                    fw.write("        return listMeses.get(mes);" + "\r\n");
                    fw.write("        " + "\r\n");
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

    private String meses() {
        try {

            String texto = "";
            texto += "    public Meses() {" + "\r\n";
            texto += "        listMeses.add(\"Enero\");" + "\r\n";
            texto += "        listMeses.add(\"Febrero\");" + "\r\n";
            texto += "        listMeses.add(\"Marzo\");" + "\r\n";
            texto += "        listMeses.add(\"Abril\");" + "\r\n";
            texto += "        listMeses.add(\"Mayo\");" + "\r\n";
            texto += "        listMeses.add(\"Junio\");" + "\r\n";
            texto += "        listMeses.add(\"Julio\");" + "\r\n";
            texto += "        listMeses.add(\"Agosto\");" + "\r\n";
            texto += "        listMeses.add(\"Septiembre\");" + "\r\n";
            texto += "        listMeses.add(\"Octubre\");" + "\r\n";
            texto += "        listMeses.add(\"Noviembre\");" + "\r\n";
            texto += "        listMeses.add(\"Diciembre\");" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getListMeses() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "   public List<String> getListMeses() {" + "\r\n";
            texto += "        return listMeses;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String setListMeses() {
        try {

            String texto = "";
            texto += "    public void setListMeses(List<String> listMeses) {" + "\r\n";
            texto += "        this.listMeses = listMeses;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }
    
    private String numeroMes() {
        try {

            String texto = "";
                    texto += "    public Integer numeroMes(String mes) {" + "\r\n";
                    texto += "        Integer i = -1;" + "\r\n";
                    texto += "        for (String l : listMeses) {" + "\r\n";
                    texto += "            i++;" + "\r\n";
                    texto += "            if (l.equals(mes)) {" + "\r\n";
                    texto += "                return i +1;" + "\r\n";
                    texto += "            } " + "\r\n";
                    texto += "        }" + "\r\n";
                    texto += "" + "\r\n";
                    texto += "        return -1;" + "\r\n";
                    texto += "" + "\r\n";
                    texto += "    }" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }
    
     private String getNombreMes() {
        try {

            String texto = "";

                    texto += "    public String getNombreMes(Integer mes){" + "\r\n";
                    texto += "        return listMeses.get(mes);" + "\r\n";
                    texto += "        " + "\r\n";
                    texto += "    }" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("resourcesFiles()  " + e.getLocalizedMessage());
        }
        return "";
    }
}
