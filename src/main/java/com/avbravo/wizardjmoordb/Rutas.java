/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author avbravoserver
 */
@Named(value = "rutas")
@SessionScoped
public class Rutas implements Serializable {

    private static final long serialVersionUID = 1L;
    private String separator = java.nio.file.FileSystems.getDefault().getSeparator();
    private String path = "";
    private String paquete = "";
    
    /**
     * EJB
     */
        private String pathConverter = path + "converter" + separator;
        private String pathDatamodel = path + "datamodel" + separator;
        private String pathEJB = path + "ejb" + separator;
        private String pathEntity = path + "entity" + separator;
        private String pathProvider = path + "provider" + separator;
        private String pathServices = path + "services" + separator;
        
        
        /**
         * JEE
         */
    
    private String pathController = path + "controller" + separator;
    private String pathMenu = path + "menu" + separator;
    private String pathRoles = path + "roles" + separator;
    private String pathGenerales = path + "util" + separator;

/**
 * 
 */
    
    private String persistenceContext;
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

    private String pathSearch = "";
    private String pathReportes = "";

    public String getPathGenerales() {
        return pathGenerales;
    }

    public void setPathGenerales(String pathGenerales) {
        this.pathGenerales = pathGenerales;
    }

    public String getPathDatamodel() {
        return pathDatamodel;
    }

    public void setPathDatamodel(String pathDatamodel) {
        this.pathDatamodel = pathDatamodel;
    }

    public String getPathProvider() {
        return pathProvider;
    }

    public void setPathProvider(String pathProvider) {
        this.pathProvider = pathProvider;
    }

    public String getPathMainWebappPages() {
        return pathMainWebappPages;
    }

    public void setPathMainWebappPages(String pathMainWebappPages) {
        this.pathMainWebappPages = pathMainWebappPages;
    }

    public String getPathMainWebappResourcesReportes() {
        return pathMainWebappResourcesReportes;
    }

    public void setPathMainWebappResourcesReportes(String pathMainWebappResourcesReportes) {
        this.pathMainWebappResourcesReportes = pathMainWebappResourcesReportes;
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

    public String getPathServices() {
        return pathServices;
    }

    public void setPathServices(String pathServices) {
        this.pathServices = pathServices;
    }

    public String getPathSearch() {
        return pathSearch;
    }

    public void setPathSearch(String pathSearch) {
        this.pathSearch = pathSearch;
    }

    public String getPathReportes() {
        return pathReportes;
    }

    public void setPathReportes(String pathReportes) {
        this.pathReportes = pathReportes;
    }

    public String getPathPomXML() {
        return pathPomXML;
    }

    public void setPathPomXML(String pathPomXML) {
        this.pathPomXML = pathPomXML;
    }

//    public String getPathPomXML1() {
//        return pathPomXM;
//    }
//
//    public void setPathPomXML(String pathPomXML) {
//        this.pathPomXML = pathPomXML;
//    }
    public String getPathInterfaces() {
        return pathInterfaces;
    }

    public void setPathInterfaces(String pathInterfaces) {
        this.pathInterfaces = pathInterfaces;
    }

    public String getPathProperties() {
        return pathProperties;
    }

    public void setPathProperties(String pathProperties) {
        this.pathProperties = pathProperties;
    }

    public String getPathRoles() {
        return pathRoles;
    }

    public void setPathRoles(String pathRoles) {
        this.pathRoles = pathRoles;
    }

    /**
     * Creates a new instance of Rutas
     */
    public Rutas() {
    }

    public String getPathMenu() {
        return pathMenu;
    }

    public void setPathMenu(String pathMenu) {
        this.pathMenu = pathMenu;
    }

    public String getPathConverter() {
        return pathConverter;
    }

    public void setPathConverter(String pathConverter) {
        this.pathConverter = pathConverter;
    }

    public String getPathMainJava() {
        return pathMainJava;
    }

    public void setPathMainJava(String pathMainJava) {
        this.pathMainJava = pathMainJava;
    }

    public String getPathMainWebapp() {
        return pathMainWebapp;
    }

    public void setPathMainWebapp(String pathMainWebapp) {
        this.pathMainWebapp = pathMainWebapp;
    }

    public String getPathMainResources() {
        return pathMainResources;
    }

    public void setPathMainResources(String pathMainResources) {
        this.pathMainResources = pathMainResources;
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

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public String getPathEJB() {
        return pathEJB;
    }

    public void setPathEJB(String pathEJB) {
        this.pathEJB = pathEJB;
    }

    public String getPersistenceContext() {
        return persistenceContext;
    }

    public void setPersistenceContext(String persistenceContext) {
        this.persistenceContext = persistenceContext;
    }

}
