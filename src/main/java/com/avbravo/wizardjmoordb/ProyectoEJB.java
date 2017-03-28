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
public class ProyectoEJB  implements Serializable {
   private static final long serialVersionUID = 1L;

    private String path = "";
    private String proyecto = "";
    private String proyectoPlaceHolder = "";
    private String paquete = "";
    private String paquetePath = "";

    private String separator = java.nio.file.FileSystems.getDefault().getSeparator();

    /**
     * EJB
     */
    private String pathConverter = path + "converter" + separator;
    private String pathDatamodel = path + "datamodel" + separator;
    private String pathEJB = path + "ejb" + separator;
    private String pathEntity = path + "entity" + separator;
    private String pathProvider = path + "provider" + separator;
    private String pathServices = path + "services" + separator;
    private String pathProyecto;


    /**
     *
     */
//    private String persistenceContext;
    private String pathWebInf = "";
    private String pathMetaInf = "";
    private String pathMainJava = "";

    private String pathPomXML = "";

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

    public String getPathConverter() {
        return pathConverter;
    }

    public void setPathConverter(String pathConverter) {
        this.pathConverter = pathConverter;
    }

    public String getPathDatamodel() {
        return pathDatamodel;
    }

    public void setPathDatamodel(String pathDatamodel) {
        this.pathDatamodel = pathDatamodel;
    }

    public String getPathEJB() {
        return pathEJB;
    }

    public void setPathEJB(String pathEJB) {
        this.pathEJB = pathEJB;
    }

    public String getPathEntity() {
        return pathEntity;
    }

    public void setPathEntity(String pathEntity) {
        this.pathEntity = pathEntity;
    }

    public String getPathProvider() {
        return pathProvider;
    }

    public void setPathProvider(String pathProvider) {
        this.pathProvider = pathProvider;
    }

    public String getPathServices() {
        return pathServices;
    }

    public void setPathServices(String pathServices) {
        this.pathServices = pathServices;
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

    public String getPathPomXML() {
        return pathPomXML;
    }

    public void setPathPomXML(String pathPomXML) {
        this.pathPomXML = pathPomXML;
    }

    

}
