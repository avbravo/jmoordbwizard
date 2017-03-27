/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.properties;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
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
public class MenuPropertiesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MenuPropertiesGenerador.class.getName());

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
         
            procesar("menu.properties", rutas.getPathProperties() + "menu.properties");
            procesar("menu_en.properties", rutas.getPathProperties() + "menu_en.properties");
            procesar("menu_es.properties", rutas.getPathProperties() + "menu_es.properties");

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
             Utilidades.searchAdd(ruta, "menu.home=Inicio", "# and open the template in the editor.", false);
             Utilidades.searchAdd(ruta, "menu.acercade= Acerca de", "# and open the template in the editor.", false);
             Utilidades.searchAdd(ruta, "menu.login=Login", "# and open the template in the editor.", false);
             Utilidades.searchAdd(ruta, "menu.logout=Logout", "# and open the template in the editor.", false);
             Utilidades.searchAdd(ruta, "menu.ayuda=Ayuda", "# and open the template in the editor.", false);
             Utilidades.searchAdd(ruta, "menu.cambiarcontrasena=Cambiar contrasena", "# and open the template in the editor.", false);
             Utilidades.searchAdd(ruta, "menu.opciones=Opciones", "# and open the template in the editor.", false);
        for(String s:mySesion.getMenubarList()){
            Utilidades.searchAdd(ruta, "menubar."+Utilidades.letterToLower(s)+"=" + s, "# and open the template in the editor.", false);
        }
            mySesion.getEntidadList().stream().forEach((entity) -> {
                Utilidades.searchAdd(ruta, "menu."+Utilidades.letterToLower(entity.getTabla())+"=" + entity.getTabla(), "# and open the template in the editor.", false);
            });
          
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