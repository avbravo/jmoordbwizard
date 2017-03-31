/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@SessionScoped
public class ProyectoJEE  implements Serializable {
   private static final long serialVersionUID = 1L;
    private String persistenceContext = "";
   private String path = "";
    private String proyecto = "";
    private String proyectoPlaceHolder = "";
    private String paquete = "";
    private String paquetePath = "";

    private String separator = java.nio.file.FileSystems.getDefault().getSeparator();


    /**
     * EJB
     */
//    private String pathConverter = path + "converter" + separator;
//    private String pathDatamodel = path + "datamodel" + separator;
//    private String pathEJB = path + "ejb" + separator;
    private String pathEntity = path + "entity" + separator;
//    private String pathProvider = path + "provider" + separator;
    
    private String pathProyecto;

    /**
     * JEE
     */
    private String pathController = path + "controller" + separator;
    //private String pathMenu = path + "menu" + separator;
    private String pathRoles = path + "roles" + separator;
    private String pathUtil = path + "util" + separator;

    /**
     *
     */

    private String pathWebInf = "";
    private String pathMetaInf = "";
    private String pathMainJava = "";
    private String pathMainResources = "";
    private String pathMainWebapp = "";
    private String pathMainWebappPages = "";
    private String pathMainWebappResources = "";
    private String pathMainWebappResourcesCss = "";
    private String pathMainWebappResourcesImagenes = "";
    private String pathMainWebappResourcesComponentes = "";
    private String pathMainWebappResourcesReportes = "";

    private String pathProperties = "";
    private String pathInterfaces = "";
    private String pathPomXML = "";

    
    private String pathReportes = "";

    public String getPathProyecto() {
        return pathProyecto;
    }

    public void setPathProyecto(String pathProyecto) {
        this.pathProyecto = pathProyecto;
    }

    
    
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getProyectoPlaceHolder() {
        return proyectoPlaceHolder;
    }

    public void setProyectoPlaceHolder(String proyectoPlaceHolder) {
        this.proyectoPlaceHolder = proyectoPlaceHolder;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getPaquetePath() {
        return paquetePath;
    }

    public void setPaquetePath(String paquetePath) {
        this.paquetePath = paquetePath;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

   
    

    public String getPathEntity() {
        return pathEntity;
    }

    public void setPathEntity(String pathEntity) {
        this.pathEntity = pathEntity;
    }

    

    public String getPathController() {
        return pathController;
    }

    public void setPathController(String pathController) {
        this.pathController = pathController;
    }

   

    public String getPathRoles() {
        return pathRoles;
    }

    public void setPathRoles(String pathRoles) {
        this.pathRoles = pathRoles;
    }

    public String getPathUtil() {
        return pathUtil;
    }

    public void setPathUtil(String pathUtil) {
        this.pathUtil = pathUtil;
    }

    public String getPersistenceContext() {
        return persistenceContext;
    }

    public void setPersistenceContext(String persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

    public String getPathWebInf() {
        return pathWebInf;
    }

    public void setPathWebInf(String pathWebInf) {
        this.pathWebInf = pathWebInf;
    }

    public String getPathMetaInf() {
        return pathMetaInf;
    }

    public void setPathMetaInf(String pathMetaInf) {
        this.pathMetaInf = pathMetaInf;
    }

    public String getPathMainJava() {
        return pathMainJava;
    }

    public void setPathMainJava(String pathMainJava) {
        this.pathMainJava = pathMainJava;
    }

    public String getPathMainResources() {
        return pathMainResources;
    }

    public void setPathMainResources(String pathMainResources) {
        this.pathMainResources = pathMainResources;
    }

    public String getPathMainWebapp() {
        return pathMainWebapp;
    }

    public void setPathMainWebapp(String pathMainWebapp) {
        this.pathMainWebapp = pathMainWebapp;
    }

    public String getPathMainWebappPages() {
        return pathMainWebappPages;
    }

    public void setPathMainWebappPages(String pathMainWebappPages) {
        this.pathMainWebappPages = pathMainWebappPages;
    }

    public String getPathMainWebappResources() {
        return pathMainWebappResources;
    }

    public void setPathMainWebappResources(String pathMainWebappResources) {
        this.pathMainWebappResources = pathMainWebappResources;
    }

    public String getPathMainWebappResourcesCss() {
        return pathMainWebappResourcesCss;
    }

    public void setPathMainWebappResourcesCss(String pathMainWebappResourcesCss) {
        this.pathMainWebappResourcesCss = pathMainWebappResourcesCss;
    }

    public String getPathMainWebappResourcesImagenes() {
        return pathMainWebappResourcesImagenes;
    }

    public void setPathMainWebappResourcesImagenes(String pathMainWebappResourcesImagenes) {
        this.pathMainWebappResourcesImagenes = pathMainWebappResourcesImagenes;
    }

    public String getPathMainWebappResourcesComponentes() {
        return pathMainWebappResourcesComponentes;
    }

    public void setPathMainWebappResourcesComponentes(String pathMainWebappResourcesComponentes) {
        this.pathMainWebappResourcesComponentes = pathMainWebappResourcesComponentes;
    }

    public String getPathMainWebappResourcesReportes() {
        return pathMainWebappResourcesReportes;
    }

    public void setPathMainWebappResourcesReportes(String pathMainWebappResourcesReportes) {
        this.pathMainWebappResourcesReportes = pathMainWebappResourcesReportes;
    }

    public String getPathProperties() {
        return pathProperties;
    }

    public void setPathProperties(String pathProperties) {
        this.pathProperties = pathProperties;
    }

    public String getPathInterfaces() {
        return pathInterfaces;
    }

    public void setPathInterfaces(String pathInterfaces) {
        this.pathInterfaces = pathInterfaces;
    }

    public String getPathPomXML() {
        return pathPomXML;
    }

    public void setPathPomXML(String pathPomXML) {
        this.pathPomXML = pathPomXML;
    }

   

    public String getPathReportes() {
        return pathReportes;
    }

    public void setPathReportes(String pathReportes) {
        this.pathReportes = pathReportes;
    }

   
   
}
