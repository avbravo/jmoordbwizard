/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.sevices;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
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
import java.util.logging.Level;
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
public class ServicesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ServicesGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoEJB proyectoEJB;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
            for (Entidad e : mySesion.getEntidadList()) {
                procesar(e, e.getTabla(), proyectoEJB.getPathServices() + e.getTabla() + "Services.java");
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(Entidad entidad, String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(entidad, ruta, archivo);
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
    private Boolean crearFile(Entidad entidad, String path, String archivo) {
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
                    String nombreclase = Utilidades.letterToUpper(archivo);
                    String nombreentity = Utilidades.letterToLower(archivo);
                    fw.write("/*" + "\r\n");
                    fw.write("* To change this license header, choose License Headers in Project Properties." + "\r\n");
                    fw.write("* To change this template file, choose Tools | Templates" + "\r\n");
                    fw.write(" * and open the template in the editor." + "\r\n");
                    fw.write("*/" + "\r\n");
                    fw.write("package " + proyectoEJB.getPaquete() + ".services;" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".entity.*;" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".ejb.*;" + "\r\n");

                    fw.write("import java.time.LocalTime;" + "\r\n");
                    fw.write("import java.util.ArrayList;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import javax.ejb.Stateless;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import org.bson.Document;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Stateless" + "\r\n");
                    fw.write("public class " + archivo + "Services {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + nombreclase + "Facade " + nombreentity + "Facade;" + "\r\n");
                    fw.write("" + "\r\n");
                    entidad.getAtributosList().stream().forEach((a) -> {
                        if (a.getTipo().equals("String")) {
                            try {
                                fw.write("    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> complete" + Utilidades.letterToUpper(a.getNombre()) + "(String query) {" + "\r\n");
                                fw.write("        List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> suggestions = new ArrayList<>();" + "\r\n");
                                fw.write("           try {" + "\r\n");
                                fw.write("               query = query.trim();" + "\r\n");
                                fw.write("               if (query.length() < 1) {" + "\r\n");
                                fw.write("                   return suggestions;" + "\r\n");
                                fw.write("               }   " + "\r\n");
                                fw.write("               suggestions=  " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findRegex(\"" + Utilidades.letterToLower(a.getNombre()) + "\",query,true,new Document(\"" + Utilidades.letterToLower(a.getNombre()) +"\",1));" + "\r\n");
                                                                fw.write("" + "\r\n");
                                fw.write("           } catch (Exception e) {" + "\r\n");
                                fw.write("                    JsfUtil.errorMessage(\"complete" + Utilidades.letterToUpper(a.getNombre()) + "() \" + e.getLocalizedMessage());" + "\r\n");
                                fw.write("           }" + "\r\n");
                                fw.write("           return suggestions;" + "\r\n");
                                fw.write("    }" + "\r\n");
                            } catch (IOException ex) {
                                Logger.getLogger(ServicesGenerador.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {

                            if (a.getEsPrimaryKey() && a.getTipo().equals("Integer")) {
                                try {
                                    fw.write("    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> complete" + Utilidades.letterToUpper(a.getNombre()) + "(String query) {" + "\r\n");
                                    fw.write("        List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> suggestions = new ArrayList<>();" + "\r\n");
                                    fw.write("           try {" + "\r\n");
                                    fw.write("                query = query.trim();" + "\r\n");
                                    fw.write("                List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> list = new ArrayList<>();" + "\r\n");
                                    fw.write("                list = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n");
                                    fw.write("                 if (!list.isEmpty()) {" + "\r\n");
                                    fw.write("                      for (" + Utilidades.letterToUpper(archivo) + " p : list) {" + "\r\n");
                                    fw.write("                         if (String.valueOf(p.get" + Utilidades.letterToUpper(a.getNombre()) + "()).toLowerCase().startsWith(query.toLowerCase())) {" + "\r\n");
                                    fw.write("                            suggestions.add(p);" + "\r\n");
                                    fw.write("                         }" + "\r\n");
                                    fw.write("                       }" + "\r\n");
                                    fw.write("                    }" + "\r\n");
                                    fw.write("           } catch (Exception e) {" + "\r\n");
                                    fw.write("                    JsfUtil.errorMessage(\"complete" + Utilidades.letterToUpper(a.getNombre()) + "() \" + e.getLocalizedMessage());" + "\r\n");
                                    fw.write("           }" + "\r\n");
                                    fw.write("           return suggestions;" + "\r\n");
                                    fw.write("    }" + "\r\n");
                                } catch (IOException ex) {
                                    Logger.getLogger(ServicesGenerador.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }

                    });
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

    private String completeString(Entidad entidad, Atributos a) {
        try {

            String texto = "";
            texto += "    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> complete" + Utilidades.letterToUpper(a.getNombre()) + "(String query) {" + "\r\n";
            texto += "        List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> suggestions = new ArrayList<>();" + "\r\n";
            texto += "           try {" + "\r\n";
            texto += "               query = query.trim();" + "\r\n";
            texto += "               suggestions=  " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findBy" + Utilidades.letterToUpper(a.getNombre()) + "Like(query.toLowerCase());" + "\r\n";
            texto += "" + "\r\n";
            texto += "           } catch (Exception e) {" + "\r\n";
            texto += "                   JSFUtil.errorMessage(\"complete" + Utilidades.letterToUpper(a.getNombre()) + "() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "           }" + "\r\n";
            texto += "           return suggestions;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getUltimaFechaAnio()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String completePrimaryKeyInteger(Entidad entidad, Atributos a, String archivo) {
        try {

            String texto = "";
            texto += "    public List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> complete" + Utilidades.letterToUpper(a.getNombre()) + "(String query) {" + "\r\n";
            texto += "        List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> suggestions = new ArrayList<>();" + "\r\n";
            texto += "           try {" + "\r\n";
            texto += "                query = query.trim();" + "\r\n";
            texto += "                List<" + Utilidades.letterToUpper(entidad.getTabla()) + "> list = new ArrayList<>();" + "\r\n";
            texto += "                list = " + Utilidades.letterToLower(entidad.getTabla()) + "Facade.findAll();" + "\r\n";
            texto += "                 if (!list.isEmpty()) {" + "\r\n";
            texto += "                      for (" + Utilidades.letterToUpper(archivo) + " p : list) {" + "\r\n";
            texto += "                         if (String.valueOf(p.get" + Utilidades.letterToUpper(a.getNombre()) + "()).toLowerCase().startsWith(query.toLowerCase())) {" + "\r\n";
            texto += "                            suggestions.add(p);" + "\r\n";
            texto += "                         }" + "\r\n";
            texto += "                       }" + "\r\n";
            texto += "                    }" + "\r\n";
            texto += "           } catch (Exception e) {" + "\r\n";
            texto += "                   JSFUtil.errorMessage(\"complete" + Utilidades.letterToUpper(a.getNombre()) + "() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "           }" + "\r\n";
            texto += "           return suggestions;" + "\r\n";
            texto += "    } \r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getUltimaFechaAnio()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
