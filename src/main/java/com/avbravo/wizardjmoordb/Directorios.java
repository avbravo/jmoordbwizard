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
    ProyectoEJB proyectoEJB;
    @Inject
    ProyectoJEE proyectoJEE;

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
            if (!Utilidades.searchDirectorie(proyectoEJB.getPath() + "entity")) {
                JSFUtil.warningDialog("Mensaje", "No existe el paquete entity");
                return false;
            }
            if (!Utilidades.searchDirectorie(proyectoEJB.getPath() + "converter")) {
                Utilidades.mkdir(proyectoEJB.getPath() + "converter");
            }
            if (!Utilidades.searchDirectorie(proyectoEJB.getPath() + "datamodel")) {
                Utilidades.mkdir(proyectoEJB.getPath() + "datamodel");

            }
            if (!Utilidades.searchDirectorie(proyectoEJB.getPath() + "ejb")) {
                Utilidades.mkdir(proyectoEJB.getPath() + "ejb");
            }
            if (!Utilidades.searchDirectorie(proyectoEJB.getPath() + "provider")) {
                Utilidades.mkdir(proyectoEJB.getPath() + "provider");
            }

            if (!Utilidades.searchDirectorie(proyectoEJB.getPath() + "services")) {
                Utilidades.mkdir(proyectoEJB.getPath() + "services");
            }

            /*
           JEE
             */
            if (!Utilidades.searchDirectorie(proyectoJEE.getPath() + "controller")) {
                Utilidades.mkdir(proyectoJEE.getPath() + "controller");
            }
            if (!Utilidades.searchDirectorie(proyectoJEE.getPath() + "search")) {
                Utilidades.mkdir(proyectoJEE.getPath() + "search");
            }
            if (!Utilidades.searchDirectorie(proyectoJEE.getPath() + "interfaces")) {
                Utilidades.mkdir(proyectoJEE.getPath() + "interfaces");
            }
            if (!Utilidades.searchDirectorie(proyectoJEE.getPath() + "reportes")) {
                Utilidades.mkdir(proyectoJEE.getPath() + "reportes");
            }
            if (!Utilidades.searchDirectorie(proyectoJEE.getPath() + "util")) {
                Utilidades.mkdir(proyectoJEE.getPath() + "util");
            }

            if (!Utilidades.searchDirectorie(proyectoJEE.getPath() + "roles")) {
                Utilidades.mkdir(proyectoJEE.getPath() + "roles");
            }

            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappPages())) {
                Utilidades.mkdir(proyectoJEE.getPathMainWebappPages());
            }
            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResources())) {
                Utilidades.mkdir(proyectoJEE.getPathMainWebappResources());
            }
            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesCss())) {
                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesCss());
            }

            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesAvbravo())) {
                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesAvbravo());
            }
            /**
             * bootstrap
             */
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesBootstrap())) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesBootstrap());
//            }
//
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesBootstrap() + "css")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesBootstrap() + "css");
//            }
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesBootstrap() + "fonts")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesBootstrap() + "fonts");
//            }
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesBootstrap() + "js")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesBootstrap() + "js");
//            }
//
//            
//            
//            /*
//            Boostrapcdn
//             */
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesBootstrapcdn())) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesBootstrapcdn());
//            }
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesBootstrapcdn()+"css")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesBootstrapcdn()+"css");
//            }
//            
//            
//            /*
//            dist
//            */
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesDist())) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesDist());
//            }
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesDist()+"css")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesDist()+"css");
//            }
//            
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesDist()+"css"+proyectoEJB.getSeparator()+"skins")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesDist()+"css"+proyectoEJB.getSeparator()+"skins");
//            }
//            
//             if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesDist()+"js")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesDist()+"js");
//            }
//             
//              if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesDist()+"js"+proyectoEJB.getSeparator()+"pages")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesDist()+"js"+proyectoEJB.getSeparator()+"pages");
//            }
//            /*
//            img
//            */
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesImg())) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesImg());
//            }
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesImg()+"credit")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesImg()+"credit");
//            }
//            
//            /*
//            IonicFramework
//            */
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesIonicframework())) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesIonicframework());
//            }
//            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesIonicframework()+"css")) {
//                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesIonicframework()+"css");
//            }
//            
            /*
            Reportes
            */

            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainWebappResourcesReportes())) {
                Utilidades.mkdir(proyectoJEE.getPathMainWebappResourcesReportes());
            }

            // crear directorio para archivos properties dentro de resources
            if (!Utilidades.searchDirectorie(proyectoJEE.getPathMainResources() + proyectoJEE.getSeparator() + proyectoJEE.getPaquetePath() + proyectoJEE.getSeparator() + "properties")) {
                List<String> list = Utilidades.getListPathPaqueteFromAbsolutePath(proyectoJEE.getPath() + "/properties ");
                String directorio = "";
                for (String s : list) {
                    if (directorio.equals("")) {
                        directorio = s;
                    } else {
                        directorio += proyectoJEE.getSeparator() + s;
                    }
                    Utilidades.mkdir(proyectoJEE.getPathMainResources() + proyectoJEE.getSeparator() + directorio);
                }

            }
            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setupDirectorios() " + e.getLocalizedMessage());
        }
        return false;
    }

}
