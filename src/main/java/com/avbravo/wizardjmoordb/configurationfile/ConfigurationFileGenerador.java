/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.configurationfile;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.beans.EntidadMenu;
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
public class ConfigurationFileGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ConfigurationFileGenerador.class.getName());

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

            procesar("Configuration.txt", proyectoJEE.getPathProperties() + "Configuration.txt");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            } else {
                Files.delete(path);
                crearFile(ruta, archivo);
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
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author: " + mySesion.getUsername() + "\r\n");
                    fw.write(" * Fecha: " + fechasServices.getFechaActual() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("proyecto=" + proyectoJEE.getProyecto() + "\r\n");
                    fw.write("package=" + proyectoJEE.getPaquete() + "\r\n");
                    fw.write("entidadUser=" + mySesion.getEntidadUser().getTabla() + "\r\n");
                    fw.write("atributosUsername=" + mySesion.getAtributosUsername() + "\r\n");
                    fw.write("atributosPassword=" + mySesion.getAtributosPassword() + "\r\n");
                    fw.write("atributosNombreMostrar=" + mySesion.getAtributosNombreMostrar() + "\r\n");
                    fw.write("atributosIdGrupo=" + mySesion.getAtributosIdGrupo() + "\r\n");
                    fw.write("entidadRoles=" + mySesion.getEntidadRoles().getTabla() + "\r\n");
                    fw.write("typeUserGroup=" + mySesion.getTypeUserGroup() + "\r\n");
                    fw.write("databasename=" + mySesion.getDatabasename()+ "\r\n");
                    fw.write("database=" + mySesion.getDatabase()+ "\r\n");
                    fw.write("typeUserGroupWithOutRol=" + mySesion.getTypeUserGroupWithOutRol() + "\r\n");
                    fw.write("typeUserGroupField=" + mySesion.getTypeUserGroupField() + "\r\n");
                    fw.write("typeUserGroupEntity=" + mySesion.getTypeUserGroupEntity() + "\r\n");
                    fw.write("typeUserGroupList=" + mySesion.getTypeUserGroupList() + "\r\n");

                    fw.write("opcionMenuReportes=" + mySesion.getOpcionMenuReportes() + "\r\n");
                    if (mySesion.getTypeUserGroupList()) {
                        fw.write("entidadGruposUsuariosMultiples=" + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "\r\n");
                        fw.write("atributosGrupousuarioMostrar=" + mySesion.getAtributosGrupousuarioMostrar() + "\r\n");
                    } else {
                        fw.write("entidadGruposUsuariosMultiples=\"\" \r\n");
                        fw.write("atributosGrupousuarioMostrar=\"\"\r\n");
                    }

                    fw.write("roles=" + mySesion.getRoles() + "\r\n");
                    fw.write("numeroMenuBar=" + mySesion.getNumeroMenuBar() + "\r\n");
                    fw.write("titulosMenuBar=" + mySesion.getTitulosMenuBar() + "\r\n");
                    fw.write("titulosSubMenu=" + mySesion.getTitulosSubMenu() + "\r\n");
                    fw.write("generarRemplazarMenu=" + mySesion.getGenerarRemplazarMenu() + "\r\n");

                    fw.write("addUserNameLogeado=" + mySesion.getAddUserNameLogeado() + "\r\n");
                    fw.write("addFechaSystema=" + mySesion.getAddFechaSystema() + "\r\n");
                    /*
                    autocomplete
                     */
                    fw.write("maximoAutocomplete=" + mySesion.getMaximoAutocomplete() + "\r\n");
                    fw.write("maximoAutocompleteItemLabel=" + mySesion.getMaximoAutocompleteItemLabel() + "\r\n");
                    fw.write("maximoAutocompleteItemTip=" + mySesion.getMaximoAutocompleteItemTip() + "\r\n");
                    /*
                    field for row
                     */
                    fw.write("fieldByRowView=" + mySesion.getFieldByRowView() + "\r\n");
                    /*
                    Button
                     */
                    fw.write("typeOfButton=" + mySesion.getTypeOfButton() + "\r\n");
                    /*
                    dates
                     */

                    fw.write("timeZone=" + mySesion.getTimeZone() + "\r\n");
                    fw.write("patternDate=" + mySesion.getPatternDate() + "\r\n");
                    fw.write("patternDateTime=" + mySesion.getPatternDateTime() + "\r\n");
                    fw.write("tipoCompilacionReporte=" + mySesion.getCompilarReporteaJasper()+ "\r\n");

                    /*

                     */
                    fw.write("frameworkPrimefaces=" + mySesion.getFrameworkPrimefaces() + "\r\n");
                    fw.write("frameworkBootfaces=" + mySesion.getFrameworkBootfaces() + "\r\n");
                    fw.write("frameworkMaterialprime=" + mySesion.getFrameworkMaterialprime() + "\r\n");
                    for (EntidadMenu em : mySesion.getEntidadMenuList()) {
                        fw.write(Utilidades.letterToLower(em.getEntidad()) + "_menu=" + em.getMenu() + "\r\n");
                    }

                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("ConfiguracionFileGenerador.crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("ConfiguracionFileGenerador.crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

}
