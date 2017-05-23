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
private Boolean esEmbebido;
private Boolean esReferenciado;
private Boolean esList;
    public Atributos() {
    }

    public Atributos(String tipo, String nombre, Boolean esPrimaryKey, Boolean esEmbebido, Boolean esReferenciado, Boolean esList) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.esPrimaryKey = esPrimaryKey;
        this.esEmbebido = esEmbebido;
        this.esReferenciado = esReferenciado;
        this.esList = esList;
    }

    public Boolean getEsEmbebido() {
        return esEmbebido;
    }

    public void setEsEmbebido(Boolean esEmbebido) {
        this.esEmbebido = esEmbebido;
    }

    public Boolean getEsReferenciado() {
        return esReferenciado;
    }

    public void setEsReferenciado(Boolean esReferenciado) {
        this.esReferenciado = esReferenciado;
    }

    public Boolean getEsList() {
        return esList;
    }

    public void setEsList(Boolean esList) {
        this.esList = esList;
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
