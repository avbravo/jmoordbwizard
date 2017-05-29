/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.rules;

import com.avbravo.wizardjmoordb.beans.EntidadPatron;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped

public class Rules implements Serializable {

    private static final long serialVersionUID = 1L;

    public Rules() {

    }
    
    public String patron(EntidadPatron entidadPatron){
        try {
            switch(entidadPatron.getSecuencia()){
                case "@e":
                    return "["+entidadPatron.getPatron().get(0).getFather() + "TV{["+entidadPatron.getPatron().get(0).getSon()+ "]}]";
               
                default:
                    break;
               
            }
            
        } catch (Exception e) {
        }
        return "";
    } 

}
