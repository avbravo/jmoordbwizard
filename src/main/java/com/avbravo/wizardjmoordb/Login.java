/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {
private String username;
private String password;
@Inject
Generador generador;
    /**
     * Creates a new instance of Login
     */
    public Login() {
    }
    
    public String validarAcceso(){
        try {
            if(username.equals("avbravo") && password.equals("denver")){
                generador.mostrarRutaEJB();
                return "index.xhml";
            }
            else{
                JSFUtil.addWarningMessage("Acceso denegado");
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("validarAcceso() "+e.getLocalizedMessage());
        }
        return "";
    }
    
}
