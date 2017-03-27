/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.converter;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Atributos;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class AtributosConverter implements Converter {
@Inject
MySesion mySesion;
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Atributos atributos = new Atributos();
        try {
            if(!s.equals("null")){
               atributos.setNombre(s);
               atributos.setEsPrimaryKey(false);
               atributos.setTipo("String");
            }
              
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAsObject()" + e.getLocalizedMessage());
        }

        return atributos;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Atributos) {
               Atributos atributos = (Atributos) o;
                r = atributos.getNombre();
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAsString()" + e.getLocalizedMessage());

        }

        return r;
    }
}