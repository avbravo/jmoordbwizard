/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.information;

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
public class InformationGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(InformationGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    FechasServices fechasServices;

    // <editor-fold defaultstate="collapsed" desc="generar"> 

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
// </editor-fold>
// <editor-fold defaultstate="collapsed" desc="crearFile"> 


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
                    fw.write("...................................................." + "\r\n");
                    for(String s:mySesion.getMensajesInformacion()){
                          fw.write(s + "\r\n");
                    }
                    fw.write("...................................................." + "\r\n");
                  

                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold>

}
