/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;


import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named(value = "directorios")
@RequestScoped
public class Directorios implements Serializable {

    private static final Logger LOG = Logger.getLogger(Directorios.class.getName());

    @Inject
    Rutas rutas;
    @Inject
    MySesion mySesion;

    /**
     * Creates a new instance of Directorios
     */
    public Directorios() {
    }

    /**
     * verifica los directorios de trabajo
     *
     * @return
     */
    public Boolean setupDirectorios() {
        try {
            if (!Utilidades.searchDirectorie(rutas.getPath() + "entity")) {
                JSFUtil.warningDialog("Mensaje", "No existe el paquete entity");
                return false;
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "controller")) {
                Utilidades.mkdir(rutas.getPath() + "controller");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "converter")) {
                Utilidades.mkdir(rutas.getPath() + "converter");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "ejb")) {
                Utilidades.mkdir(rutas.getPath() + "ejb");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "generales")) {
                Utilidades.mkdir(rutas.getPath() + "generales");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "interfaces")) {
                Utilidades.mkdir(rutas.getPath() + "interfaces");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "menu")) {
                Utilidades.mkdir(rutas.getPath() + "menu");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "reportes")) {
                Utilidades.mkdir(rutas.getPath() + "reportes");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "roles")) {
                Utilidades.mkdir(rutas.getPath() + "roles");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "search")) {
                Utilidades.mkdir(rutas.getPath() + "search");
            }
            if (!Utilidades.searchDirectorie(rutas.getPath() + "services")) {
                Utilidades.mkdir(rutas.getPath() + "services");
            }
            if (!Utilidades.searchDirectorie(rutas.getPathMainWebappPages())) {
                Utilidades.mkdir(rutas.getPathMainWebappPages());
            }
            if (!Utilidades.searchDirectorie(rutas.getPathMainWebappResources())) {
                Utilidades.mkdir(rutas.getPathMainWebappResources());
            }
            if (!Utilidades.searchDirectorie(rutas.getPathMainWebappResourcesCss())) {
                Utilidades.mkdir(rutas.getPathMainWebappResourcesCss());
            }
            if (!Utilidades.searchDirectorie(rutas.getPathMainWebappResourcesImagenes())) {
                Utilidades.mkdir(rutas.getPathMainWebappResourcesImagenes());
            }
            if (!Utilidades.searchDirectorie(rutas.getPathMainWebappResourcesComponentes())) {
                Utilidades.mkdir(rutas.getPathMainWebappResourcesComponentes());
            }
            
            if (!Utilidades.searchDirectorie(rutas.getPathMainWebappResourcesReportes())) {
                Utilidades.mkdir(rutas.getPathMainWebappResourcesReportes());
            }
            
            // crear directorio para archivos properties dentro de resources

            if (!Utilidades.searchDirectorie(rutas.getPathMainResources() + rutas.getSeparator() + mySesion.getPaquetePath() + rutas.getSeparator() + "properties")) {
                List<String> list = Utilidades.getListPathPaqueteFromAbsolutePath(mySesion.getPath() + "/properties ");
                String directorio = "";
                for (String s : list) {
                    if (directorio.equals("")) {
                        directorio = s;
                    } else {
                        directorio += rutas.getSeparator() + s;
                    }
                    Utilidades.mkdir(rutas.getPathMainResources() + rutas.getSeparator() + directorio);
                }

            }
            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setupDirectorios() " + e.getLocalizedMessage());
        }
        return false;
    }

}
