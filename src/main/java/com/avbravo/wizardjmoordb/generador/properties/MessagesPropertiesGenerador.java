/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.properties;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
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
public class MessagesPropertiesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MessagesPropertiesGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    Rutas rutas;
    @Inject
    FechasServices fechasServices;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
  
            procesar("messages.properties", rutas.getPathProperties() + "messages.properties");
            procesar("messages_en.properties", rutas.getPathProperties() + "messages_en.properties");
            procesar("messages_es.properties", rutas.getPathProperties() + "messages_es.properties");

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
            Utilidades.searchAdd(ruta,"username=Username", "# and open the template in the editor.", false);    
            for(Entidad entidad:mySesion.getEntidadList()){
                for(Atributos atributos:entidad.getAtributosList()){
                 Utilidades.searchAdd(ruta, Utilidades.letterToLower(atributos.getNombre())+"=" + Utilidades.letterToUpper(atributos.getNombre()), "# and open the template in the editor.", false);    
                }
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
                    fw.write("# To change this license header, choose License Headers in Project Properties." + "\r\n");
                    fw.write("# To change this template file, choose Tools | Templates" + "\r\n");
                    fw.write("# and open the template in the editor." + "\r\n");
                    fw.write("msg.username=Username" + "\r\n");
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("EntityPropertiesGenerador.crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("EntityPropertiesGenerador.crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

}
