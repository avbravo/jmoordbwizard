/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.resources;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
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
public class DefaultCssGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(DefaultCssGenerador.class.getName());

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

            procesar("default.css", proyectoJEE.getPathMainWebappResourcesCss()+ "default.css");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {
            System.out.println("===> "+ruta);
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            }

            /**
             * generar los metodos
             */
            Utilidades.addNotFoundMethod(ruta, "body {", body(), "/*defaultcss*/", false);
            Utilidades.addNotFoundMethod(ruta, "h1 {", h1(), "/*defaultcss*/", false);
            Utilidades.addNotFoundMethod(ruta, "a:link, a:visited {", alink(), "/*defaultcss*/", false);
            Utilidades.addNotFoundMethod(ruta, "a:link:hover, a:visited:hover  {", alinkhover(), "/*defaultcss*/", false);

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
                    fw.write("/*defaultcss*/" + "\r\n");
                    fw.write("body {" + "\r\n");
                    fw.write("    background-color: #ffffff;" + "\r\n");
                    fw.write("    font-size: 12px;" + "\r\n");
                    fw.write("    font-family: Verdana, \"Verdana CE\",  Arial, \"Arial CE\", \"Lucida Grande CE\", lucida, \"Helvetica CE\", sans-serif;" + "\r\n");
                    fw.write("    color: #000000;  " + "\r\n");
                    fw.write("    margin: 10px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("h1 {" + "\r\n");
                    fw.write("    font-family: Arial, \"Arial CE\", \"Lucida Grande CE\", lucida, \"Helvetica CE\", sans-serif;" + "\r\n");
                    fw.write("    border-bottom: 1px solid #AFAFAF; " + "\r\n");
                    fw.write("    font-size:  16px;" + "\r\n");
                    fw.write("    font-weight: bold;" + "\r\n");
                    fw.write("    margin: 0px;" + "\r\n");
                    fw.write("    padding: 0px;" + "\r\n");
                    fw.write("    color: #D20005;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("a:link, a:visited {" + "\r\n");
                    fw.write("  color: #045491;" + "\r\n");
                    fw.write("  font-weight : bold;" + "\r\n");
                    fw.write("  text-decoration: none;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("a:link:hover, a:visited:hover  {" + "\r\n");
                    fw.write("  color: #045491;" + "\r\n");
                    fw.write("  font-weight : bold;" + "\r\n");
                    fw.write("  text-decoration : underline;" + "\r\n");
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

    private String body() {
        try {

            String texto = "";
            texto += "body {" + "\r\n";
            texto += "    background-color: #ffffff;" + "\r\n";
            texto += "    font-size: 12px;" + "\r\n";
            texto += "    font-family: Verdana, \"Verdana CE\",  Arial, \"Arial CE\", \"Lucida Grande CE\", lucida, \"Helvetica CE\", sans-serif;" + "\r\n";
            texto += "    color: #000000;  " + "\r\n";
            texto += "    margin: 10px;" + "\r\n";
            texto += "}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("body()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String h1() {
        try {

            String texto = "";
            texto += "h1 {" + "\r\n";
            texto += "    font-family: Arial, \"Arial CE\", \"Lucida Grande CE\", lucida, \"Helvetica CE\", sans-serif;" + "\r\n";
            texto += "    border-bottom: 1px solid #AFAFAF; " + "\r\n";
            texto += "    font-size:  16px;" + "\r\n";
            texto += "    font-weight: bold;" + "\r\n";
            texto += "    margin: 0px;" + "\r\n";
            texto += "    padding: 0px;" + "\r\n";
            texto += "    color: #D20005;" + "\r\n";
            texto += "}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("h1()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String alink() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "a:link, a:visited {" + "\r\n";
            texto += "  color: #045491;" + "\r\n";
            texto += "  font-weight : bold;" + "\r\n";
            texto += "  text-decoration: none;" + "\r\n";
            texto += "}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("h1()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String alinkhover() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "a:link:hover, a:visited:hover  {" + "\r\n";
            texto += "  color: #045491;" + "\r\n";
            texto += "  font-weight : bold;" + "\r\n";
            texto += "  text-decoration : underline;" + "\r\n";
            texto += "}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("h1()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
