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
public class Atributos {
    private String tipo;
    private String nombre;
    private Boolean esPrimaryKey;
private Boolean esEmbedded;
private Boolean esReferenced;
private Boolean esListEmbedded;
private Boolean esListReferenced;
    public Atributos() {
    }

    public Atributos(String tipo, String nombre, Boolean esPrimaryKey, Boolean esEmbedded, Boolean esReferenced, Boolean esListEmbedded, Boolean esListReferenced) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.esPrimaryKey = esPrimaryKey;
        this.esEmbedded = esEmbedded;
        this.esReferenced = esReferenced;
        this.esListEmbedded = esListEmbedded;
        this.esListReferenced = esListReferenced;
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

    public Boolean getEsEmbedded() {
        return esEmbedded;
    }

    public void setEsEmbedded(Boolean esEmbedded) {
        this.esEmbedded = esEmbedded;
    }

    public Boolean getEsReferenced() {
        return esReferenced;
    }

    public void setEsReferenced(Boolean esReferenced) {
        this.esReferenced = esReferenced;
    }

    public Boolean getEsListEmbedded() {
        return esListEmbedded;
    }

    public void setEsListEmbedded(Boolean esListEmbedded) {
        this.esListEmbedded = esListEmbedded;
    }

    public Boolean getEsListReferenced() {
        return esListReferenced;
    }

    public void setEsListReferenced(Boolean esListReferenced) {
        this.esListReferenced = esListReferenced;
    }

   
    
    
}
