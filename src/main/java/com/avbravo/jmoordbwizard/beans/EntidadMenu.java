/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.beans;

/**
 *
 * @author avbravoserver
 */
public class EntidadMenu {
    private String entidad;
    private String menu;
    private String masterdetails;

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public EntidadMenu() {
    }

    public String getMasterdetails() {
        return masterdetails;
    }

    public void setMasterdetails(String masterdetails) {
        this.masterdetails = masterdetails;
    }

    public EntidadMenu(String entidad, String menu, String masterdetails) {
        this.entidad = entidad;
        this.menu = menu;
        this.masterdetails = masterdetails;
    }
    
    

    
    
}
