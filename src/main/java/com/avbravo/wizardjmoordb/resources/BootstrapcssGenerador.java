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
public class BootstrapcssGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(BootstrapcssGenerador.class.getName());

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

            procesar("cssLayout.css", proyectoJEE.getPathMainWebappResourcesCss()+ "cssLayout.css");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            }

            /**
             * generar los metodos
             */
            Utilidades.addNotFoundMethod(ruta, "#top {", top(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, "#bottom {", bottom(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, "#left {", left(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, "#right {", right(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, ".center_content {", center_content(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, ".left_content {", left_content(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, ".right_content {", right_content(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, "#top a:link, #top a:visited {",alink(), "/*csslayout*/", false);
            Utilidades.addNotFoundMethod(ruta, "#top a:link:hover, #top a:visited:hover  {",alinkhover(), "/*csslayout*/", false);
           

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
                    fw.write("/*csslayout*/" + "\r\n");
                    fw.write("#top {" + "\r\n");
                    fw.write("    position: relative;" + "\r\n");
                    fw.write("    background-color: #036fab;" + "\r\n");
                    fw.write("    color: white;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("    margin: 0px 0px 10px 0px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("#bottom {" + "\r\n");
                    fw.write("    position: relative;" + "\r\n");
                    fw.write("    background-color: #c2dfef;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("    margin: 10px 0px 0px 0px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("#left {" + "\r\n");
                    fw.write("    float: left;" + "\r\n");
                    fw.write("    background-color: #ece3a5;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("    width: 150px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("#right {" + "\r\n");
                    fw.write("    float: right;" + "\r\n");
                    fw.write("    background-color: #ece3a5;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("    width: 150px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(".center_content {" + "\r\n");
                    fw.write("    position: relative;" + "\r\n");
                    fw.write("    background-color: #dddddd;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(".left_content {" + "\r\n");
                    fw.write("    background-color: #dddddd;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("    margin-left: 170px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(".right_content {" + "\r\n");
                    fw.write("    background-color: #dddddd;" + "\r\n");
                    fw.write("    padding: 5px;" + "\r\n");
                    fw.write("    margin: 0px 170px 0px 170px;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("#top a:link, #top a:visited {" + "\r\n");
                    fw.write("  color: white;" + "\r\n");
                    fw.write("  font-weight : bold;" + "\r\n");
                    fw.write("  text-decoration: none;" + "\r\n");
                    fw.write("}" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("#top a:link:hover, #top a:visited:hover  {" + "\r\n");
                    fw.write("  color: black;" + "\r\n");
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

    private String top() {
        try {

            String texto = "";
          texto +="#top {" + "\r\n";
                    texto +="    position: relative;" + "\r\n";
                    texto +="    background-color: #036fab;" + "\r\n";
                    texto +="    color: white;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="    margin: 0px 0px 10px 0px;" + "\r\n";
                    texto +="}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("top()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String bottom() {
        try {

            String texto = "";
          texto +="" + "\r\n";
                    texto +="#bottom {" + "\r\n";
                    texto +="    position: relative;" + "\r\n";
                    texto +="    background-color: #c2dfef;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="    margin: 10px 0px 0px 0px;" + "\r\n";
                    texto +="}" + "\r\n";
                    texto +="" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("bottom()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String left() {
        try {

            String texto = "";
          texto +="#left {" + "\r\n";
                    texto +="    float: left;" + "\r\n";
                    texto +="    background-color: #ece3a5;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="    width: 150px;" + "\r\n";
                    texto +="}" + "\r\n";
                    texto +="" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("left()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String right() {
        try {

            String texto = "";
          texto +="#right {" + "\r\n";
                    texto +="    float: right;" + "\r\n";
                    texto +="    background-color: #ece3a5;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="    width: 150px;" + "\r\n";
                    texto +="}" + "\r\n";
                    texto +="" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("right()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String center_content() {
        try {

            String texto = "";
         texto +=".center_content {" + "\r\n";
                    texto +="    position: relative;" + "\r\n";
                    texto +="    background-color: #dddddd;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("center_content()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String left_content() {
        try {

            String texto = "";
         texto +="" + "\r\n";
                    texto +=".left_content {" + "\r\n";
                    texto +="    background-color: #dddddd;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="    margin-left: 170px;" + "\r\n";
                    texto +="}" + "\r\n";
                    texto +="" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("center_content()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String right_content() {
        try {

            String texto = "";
texto +=".right_content {" + "\r\n";
                    texto +="    background-color: #dddddd;" + "\r\n";
                    texto +="    padding: 5px;" + "\r\n";
                    texto +="    margin: 0px 170px 0px 170px;" + "\r\n";
                    texto +="}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("center_content()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String alink() {
        try {

            String texto = "";
texto +="" + "\r\n";
                    texto +="#top a:link, #top a:visited {" + "\r\n";
                    texto +="  color: white;" + "\r\n";
                    texto +="  font-weight : bold;" + "\r\n";
                    texto +="  text-decoration: none;" + "\r\n";
                    texto +="}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("center_content()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String alinkhover() {
        try {

            String texto = "";
     texto +="" + "\r\n";
                    texto +="#top a:link:hover, #top a:visited:hover  {" + "\r\n";
                    texto +="  color: black;" + "\r\n";
                    texto +="  font-weight : bold;" + "\r\n";
                    texto +="  text-decoration : underline;" + "\r\n";
                    texto +="}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("center_content()  " + e.getLocalizedMessage());
        }
        return "";
    }

  
}
