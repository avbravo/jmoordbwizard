/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped
public class Test {
      @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
      

    public static void msg(String mensaje,Boolean toFile){
        System.out.println(mensaje);
         try {
        if(toFile){
            String ruta="/home/avbravo/NetBeansProjects/test/src/main/java/com/avbravo/test/util/Test.txt";
            String archivo = "Test.txt";
           
             Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo,mensaje);
            } else {
               Utilidades.searchAdd(ruta, mensaje, "<FIN>", true);
            } 
        }
     
        } catch (Exception e) {
            JSFUtil.addErrorMessage("msg() "+e.getLocalizedMessage());
        }
           
    }
    
     private static Boolean crearFile(String path, String archivo,String msg) {
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
                    fw.write("Mensages:" + "\r\n");
                    
                    fw.write(" " + msg);
                 fw.write("<FIN>" + "\r\n");
            
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
