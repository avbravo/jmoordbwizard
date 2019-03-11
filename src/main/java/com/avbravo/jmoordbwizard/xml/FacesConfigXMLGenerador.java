/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.xml;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoJEE;
import com.avbravo.jmoordbwizard.utilidades.FechasServices;
import com.avbravo.jmoordbwizard.utilidades.Utilidades;
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
public class FacesConfigXMLGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(FacesConfigXMLGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    FechasServices fechasServices;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("faces-config.xml", proyectoJEE.getPathWebInf() + "faces-config.xml");

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
            //application
            Utilidades.searchAddWithoutLine(ruta, "<application>", "xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_2.xsd\">", false);
            Utilidades.searchAddWithoutLine(ruta, "<resource-handler>org.omnifaces.resourcehandler.CombinedResourceHandler</resource-handler>", "<application>", false);
            Utilidades.searchAddWithoutLine(ruta, "</application>", "</faces-config>", true);
            if (mySesion.getSecurityHttpSession().equals("si")) {
                Utilidades.addNotFoundMethodWithOutLine(ruta, "<exception-handler-factory>com.avbravo.avbravoutils.exceptions.MyExceptionHandlerFactory</exception-handler-factory>", myExceptionHandlerFactory(), "</application>", true);
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    // <editor-fold defaultstate="collapsed" desc="nombre_metodo"> 
    private String myExceptionHandlerFactory() {
        try {

            String texto = "";
            texto += "  <factory>" + "\r\n";
            texto += "     <exception-handler-factory>com.avbravo.avbravoutils.exceptions.MyExceptionHandlerFactory</exception-handler-factory>" + "\r\n";
            texto += "  </factory>" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("myExceptionHandlerFactory() " + e.getLocalizedMessage());
        }
        return "";
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="crearFile"> 
    /**
     *
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
                    fw.write("<?xml version='1.0' encoding='UTF-8'?>" + "\r\n");
                    fw.write("<faces-config version=\"2.2\"" + "\r\n");
                    fw.write("              xmlns=\"http://xmlns.jcp.org/xml/ns/javaee\"" + "\r\n");
                    fw.write("              xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" + "\r\n");
                    fw.write("              xsi:schemaLocation=\"http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-facesconfig_2_2.xsd\">" + "\r\n");
                    fw.write("    <application>" + "\r\n");
                    fw.write("        <resource-handler>org.omnifaces.resourcehandler.CombinedResourceHandler</resource-handler>" + "\r\n");
                    fw.write("         <factory>" + "\r\n");
                    fw.write("            <exception-handler-factory>com.avbravo.avbravoutils.exceptions.MyExceptionHandlerFactory</exception-handler-factory>" + "\r\n");
                    fw.write("        </factory>" + "\r\n");
                    fw.write("    </application>" + "\r\n");
                    fw.write("</faces-config>" + "\r\n");

                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("FacesConfigGeneradorcrearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("FacesConfigGeneradorcrearFile() " + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold>

}
