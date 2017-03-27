/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.beans;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avbravoserver
 */
public class Entidad {
    private String tabla;
   List<Atributos> atributosList = new ArrayList<>();

    public Entidad() {
    }

   
   
    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public List<Atributos> getAtributosList() {
        return atributosList;
    }

    public void setAtributosList(List<Atributos> atributosList) {
        this.atributosList = atributosList;
    }

    


}
