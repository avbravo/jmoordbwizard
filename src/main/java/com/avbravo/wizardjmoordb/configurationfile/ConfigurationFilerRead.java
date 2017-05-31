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
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@SessionScoped
public class ConfigurationFilerRead implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ConfigurationFilerRead.class.getName());

    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    MySesion mySesion;

    /**
     * Creates a new instance of Generador
     */
    public ConfigurationFilerRead() {
    }

    @PostConstruct
    public void init() {

    }

    public Boolean readFile(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
//No existe el archivo de configuracion es la primera vez que se ejecuta
//o fue eliminado, al final del proceso se generara nuevamente
//no es necesario mostar el mensaje
                // JSFUtil.addWarningMessage("No existe el archivo " + ruta);
//               mySesion.getMensajesList().add("No existe el archivo de configuracion");

                return false;
            }
            /**
             *
             */
            mySesion.getEntidadMenuList().removeAll(mySesion.getEntidadMenuList());
            Stream<String> lines = Files.lines(path);
          
            lines.forEach(
                    s -> linea(s));

        } catch (Exception e) {
            JSFUtil.addErrorMessage("LeerConfigurationFile.readFile() " + e.getLocalizedMessage());

        }
        return true;

    }

    /*
      linea(String s)
     */
    private void linea(String s) {
        try {

            s = s.trim();
            if (s.indexOf("=") == -1) {
                return;
            }
            String r = s.substring(0, s.indexOf("="));
            switch (r) {
                case "proyecto":
                    proyectoJEE.setProyecto(descomponer(s, r));
                    break;
                case "package":
                    proyectoJEE.setPaquete(descomponer(s, r));
                    break;
                case "databasename":
                   mySesion.setDatabasename(descomponer(s, r));;
                    break;
case "database":
                   mySesion.setDatabase(descomponer(s, r));;
                    break;
              
                case "entidadUser":
                    mySesion.getEntidadUser().setTabla(descomponer(s, r));
                    break;
                case "atributosUsername":
                    mySesion.setAtributosUsername(descomponer(s, r));
                    break;
                case "atributosPassword":
                    mySesion.setAtributosPassword(descomponer(s, r));
                    break;
                case "atributosNombreMostrar":
                    mySesion.setAtributosNombreMostrar(descomponer(s, r));
                    break;
                case "atributosIdGrupo":
                    mySesion.setAtributosIdGrupo(descomponer(s, r));
                    break;
                case "entidadRoles":
                    mySesion.getEntidadRoles().setTabla(descomponer(s, r));
                    break;

                case "typeUserGroup":
                    mySesion.setTypeUserGroup(descomponer(s, r));
                    break;
                case "typeUserGroupWithOutRol":
                    mySesion.setTypeUserGroupWithOutRol(descomponer(s, r).equals("true"));
                    break;
                case "typeUserGroupField":
                    mySesion.setTypeUserGroupField(descomponer(s, r).equals("true"));
                    break;

                case "typeUserGroupEntity":
                    mySesion.setTypeUserGroupEntity(descomponer(s, r).equals("true"));
                    break;
                case "typeUserGroupList":
                    mySesion.setTypeUserGroupList(descomponer(s, r).equals("true"));
                    break;

                case "entidadGruposUsuariosMultiples":
                    mySesion.getEntidadGruposUsuariosMultiples().setTabla(descomponer(s, r));
                    break;
                case "atributosGrupousuarioMostrar":
                    mySesion.setAtributosGrupousuarioMostrar(descomponer(s, r));
                    break;
                case "roles":
                    mySesion.setRoles(descomponer(s, r));
                    break;
                case "numeroMenuBar":
                    mySesion.setNumeroMenuBar(Integer.parseInt(descomponer(s, r)));
                    break;
                case "titulosMenuBar":
                    mySesion.setTitulosMenuBar(descomponer(s, r));
                    break;
                case "generarRemplazarMenu":
                    mySesion.setGenerarRemplazarMenu(descomponer(s, r));
                    break;
                case "titulosSubMenu":
                    mySesion.setTitulosSubMenu(descomponer(s, r));
                    
                    mySesion.setMymenuList(Utilidades.descomponerMenuString(mySesion.getTitulosSubMenu()));
                    break;
                case "addUserNameLogeado":
                    mySesion.setAddUserNameLogeado(descomponer(s, r).equals("true"));
                    break;
                case "addFechaSystema":
                    mySesion.setAddFechaSystema(descomponer(s, r).equals("true"));
                    break;
                case "maximoAutocomplete":
                    mySesion.setMaximoAutocomplete(Integer.parseInt(descomponer(s, r)));
                    break;
                case "maximoAutocompleteItemLabel":
                    mySesion.setMaximoAutocompleteItemLabel(Integer.parseInt(descomponer(s, r)));
                    break;
                case "maximoAutocompleteItemTip":
                    mySesion.setMaximoAutocompleteItemTip(Integer.parseInt(descomponer(s, r)));
                    break;
                case "fieldByRowView":
                    mySesion.setFieldByRowView(Integer.parseInt(descomponer(s, r)));
                    break;

                    /*
                    date
                    */
                case "timeZone":
                    mySesion.setTimeZone(descomponer(s, r));
                    break;
                case "patternDate":
                    mySesion.setPatternDate(descomponer(s, r));
                    break;
                case "patternDateTime":
                    mySesion.setPatternDateTime(descomponer(s, r));
                    break;
                    
                case "typeOfButton":
                    mySesion.setTypeOfButton(descomponer(s, r));
                    break;
                case "tipoCompilacionReporte":
                    mySesion.setCompilarReporteaJasper(descomponer(s, r));
                    break;
                    
                
                    /*
                    
                    */
                case "frameworkPrimefaces":
                    mySesion.setFrameworkPrimefaces(descomponer(s, r).equals("true"));
                    break;
                
                    
                    
                    
                case "frameworkBootfaces":
                    mySesion.setFrameworkBootfaces(descomponer(s, r).equals("true"));
                    break;
                case "frameworkMaterialprime":
                    mySesion.setFrameworkMaterialprime(descomponer(s, r).equals("true"));
                    break;
                case "opcionMenuReportes":
                    mySesion.setOpcionMenuReportes(descomponer(s, r));
                    break;
                default:
                    //puede que sea una opcion del menu que llevane
                    //cargamos el List con las opciones del menu
                    if (r.indexOf("_menu") == -1) {
                        return;
                    }
                    String r2 = r.substring(0, r.indexOf("_menu"));
                    EntidadMenu em = new EntidadMenu();
                    em.setEntidad(r2);
                    em.setMenu(descomponer(s, r2));
                    mySesion.getEntidadMenuList().add(em);
                    break;

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("LeerConfiguracion().linea() " + e.getLocalizedMessage());
        }

    }

    private String descomponer(String linea, String search) {
        String texto = "";
        search = search + "=";
        try {
            if (linea.indexOf(search) != -1) {
                texto = linea.replace(search, "").trim();
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("descomponer() " + e.getLocalizedMessage());
        }
        return texto.trim();
    }
}
