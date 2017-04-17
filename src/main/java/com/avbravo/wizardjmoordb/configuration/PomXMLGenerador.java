/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.configuration;

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
public class PomXMLGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(PomXMLGenerador.class.getName());

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
            procesar("pom.xml", proyectoJEE.getPathPomXML() + "pom.xml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                JSFUtil.addWarningMessage("No existe el archivo pom.xml");
                return false;
            } else {
                Utilidades.addDependencies(ruta, "<artifactId>javaee-web-api</artifactId>", javaeewebapi(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<groupId>org.primefaces</groupId>", primefaces(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>poi</artifactId>", poi(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>poi-ooxml</artifactId>",poiooxml(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>commons-fileupload</artifactId>", fileupload(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>itext</artifactId>", itext(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>jasperreports</artifactId>", jasperreports(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>jfreechart</artifactId>", jfreechart(), "<dependencies>", false);
//                Utilidades.addDependencies(ruta, "<groupId>org.projectlombok</groupId>", projectlombok(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>omnifaces</artifactId>", omnifaces(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>commons-lang</artifactId>", commonslang(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>javaee-web-api</artifactId>", javaeewebapi(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<artifactId>bootsfaces</artifactId>", bootsfaces(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<groupId>org.webjars</groupId>", webjars(), "<dependencies>", false);
                Utilidades.addDependencies(ruta, "<finalName>" + proyectoJEE.getProyecto() + "</finalName>", finalName(), "<build>", false);
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    private String primefaces() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "            <groupId>org.primefaces</groupId>" + "\r\n";
            texto += "            <artifactId>primefaces</artifactId>" + "\r\n";
            texto += "            <version>6.0</version>" + "\r\n";
            texto += "            <type>jar</type>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("org.primefaces()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String poiooxml() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "            <groupId>org.apache.poi</groupId>" + "\r\n";
            texto += "            <artifactId>poi-ooxml</artifactId>" + "\r\n";
            texto += "            <version>3.12</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("org.primefaces()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String poi() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "            <groupId>org.apache.poi</groupId>" + "\r\n";
            texto += "            <artifactId>poi</artifactId>" + "\r\n";
            texto += "            <version>3.12</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("org.primefaces()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String javaeewebapi() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "            <groupId>javax</groupId>" + "\r\n";
            texto += "            <artifactId>javaee-web-api</artifactId>" + "\r\n";
            texto += "            <version>7.0</version>" + "\r\n";
            texto += "            <scope>provided</scope>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("org.primefaces()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String fileupload() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "            <groupId>commons-fileupload</groupId>" + "\r\n";
            texto += "            <artifactId>commons-fileupload</artifactId>" + "\r\n";
            texto += "           <version>1.3</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("fileupload()  " + e.getLocalizedMessage());
        }
        return "";
    }



    private String itext() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>com.lowagie</groupId>" + "\r\n";
            texto += "             <artifactId>itext</artifactId>" + "\r\n";
            texto += "            <version>2.1.7</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("itext()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String jasperreports() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>net.sf.jasperreports</groupId>" + "\r\n";
            texto += "             <artifactId>jasperreports</artifactId>" + "\r\n";
            texto += "            <version>5.5.0</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("jasperreports()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String jfreechart() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>jfree</groupId>" + "\r\n";
            texto += "             <artifactId>jfreechart</artifactId>" + "\r\n";
            texto += "             <version>1.0.13</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("jfreechart()  " + e.getLocalizedMessage());
        }
        return "";
    }

//    private String projectlombok() {
//        try {
//
//            String texto = "";
//            texto += "        <dependency>" + "\r\n";
//            texto += "             <groupId>org.projectlombok</groupId>" + "\r\n";
//            texto += "             <artifactId>lombok</artifactId>" + "\r\n";
//            texto += "             <version>1.16.6</version>" + "\r\n";
//            texto += "        </dependency>" + "\r\n";
//            return texto;
//        } catch (Exception e) {
//            JSFUtil.addErrorMessage("projectlombok()  " + e.getLocalizedMessage());
//        }
//        return "";
//    }

    private String omnifaces() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>org.omnifaces</groupId>" + "\r\n";
            texto += "             <artifactId>omnifaces</artifactId>" + "\r\n";
            texto += "             <version>2.2</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("projectlombok()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String commonslang() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>commons-lang</groupId>" + "\r\n";
            texto += "             <artifactId>commons-lang</artifactId>" + "\r\n";
            texto += "             <version>2.6</version>" + "\r\n";
            texto += "              <type>jar</type>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("commonslang()  " + e.getLocalizedMessage());
        }
        return "";
    }

   

    private String bootsfaces() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>net.bootsfaces</groupId>" + "\r\n";
            texto += "             <artifactId>bootsfaces</artifactId>" + "\r\n";
            texto += "             <version>0.9.1</version>" + "\r\n";
            texto += "             <scope>compile</scope>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("bootsfaces() " + e.getLocalizedMessage());
        }
        return "";
    }

    private String webjars() {
        try {

            String texto = "";
            texto += "        <dependency>" + "\r\n";
            texto += "             <groupId>org.webjars</groupId>" + "\r\n";
            texto += "             <artifactId>font-awesome</artifactId>" + "\r\n";
            texto += "             <version>4.6.3</version>" + "\r\n";
            texto += "        </dependency>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("webjars() " + e.getLocalizedMessage());
        }
        return "";
    }

    private String finalName() {
        try {

            String texto = "";
            texto += "        <finalName>" + proyectoJEE.getProyecto() + "</finalName>" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("finalName()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
