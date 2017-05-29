/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.beans;

/**
 *
 * @author avbravo
 * Genera en cada elemento un caracter
 * [
 *  Persona
 * TV{
 * [
 * Pais
 * ]
 * }
 * ]
 * 
 */
public class Draw {
    private Object key;
    private String type;
    private Integer level;

    public Draw() {
        
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

   
}
