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
public class Roles {
    private String nombre;
    private String rol;

    public Roles() {
    }

    public Roles(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
