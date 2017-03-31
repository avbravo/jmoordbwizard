/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.information;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.utilidades.FechasServices;
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
public class InformationGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(InformationGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    FechasServices fechasServices;

    /**
     * Creates a new instance of Facade
     */
    public Boolean generar() {
        try {
            String ruta = proyectoJEE.getPathUtil() + "Information.txt";
            String archivo = "Information.txt";
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            } else {
                Files.delete(path);
                crearFile(ruta, archivo);
            }
            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("start() " + e.getLocalizedMessage());
        }
        return false;
    }

    public Boolean stop() {
        try {
            String ruta = proyectoJEE.getPathUtil() + "Information.txt";
            String archivo = "Information.txt";
            String mensajes = "\r\n"+"\r\n"+"========================================" + "\r\n";
            mensajes += "         Mensajes     " + "\r\n";

            mensajes = mySesion.getMensajesList().stream().map((s) -> s + "\r\n").reduce(mensajes, String::concat);

            Utilidades.add(ruta, "------fin----", mensajes, true);

            String errores ="\r\n"+"\r\n"+ "========================================" + "\r\n";
            errores += "         Errores     " + "\r\n";
            errores = mySesion.getErrorList().stream().map((s) -> s + "\r\n").reduce(errores, String::concat);
            Utilidades.add(ruta, "------fin----", errores, true);
            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("stop() " + e.getLocalizedMessage());
        }
        return false;
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
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author: " + mySesion.getUsername() + "\r\n");
                    fw.write(" * Fecha: " + fechasServices.getFechaActual() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write(" " + "\r\n");
                    fw.write(" " + "\r\n");
                    fw.write("====================================================" + "\r\n");
                    fw.write("proyecto=" + proyectoJEE.getProyecto() + "\r\n");
                    fw.write("package=" + proyectoJEE.getPaquete() + "\r\n");
                    fw.write("Hora de inicio: " + fechasServices.getTiempo() + "\r\n");
                    fw.write(" Resultados de la generacion" + "\r\n");
                    fw.write("====================================================" + "\r\n");
                    fw.write("------fin----" + "\r\n");

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
