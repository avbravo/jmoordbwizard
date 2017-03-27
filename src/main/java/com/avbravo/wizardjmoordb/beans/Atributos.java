/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.beans;

/**
 *
 * @author avbravoserver
 */
public class Atributos {
     private String tipo;
    private String nombre;
    private Boolean esPrimaryKey;

    public Atributos() {
    }

    public Atributos(String tipo, String nombre, Boolean esPrimaryKey) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.esPrimaryKey = esPrimaryKey;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getEsPrimaryKey() {
        return esPrimaryKey;
    }

    public void setEsPrimaryKey(Boolean esPrimaryKey) {
        this.esPrimaryKey = esPrimaryKey;
    }

    
    
}
