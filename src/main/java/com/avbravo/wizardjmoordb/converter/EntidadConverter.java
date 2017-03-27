/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.converter;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Entidad;
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
public class EntidadConverter implements Converter {

    @Inject
    MySesion mySesion;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        Entidad entidad = new Entidad();
        try {
            if (!s.equals("null")) {
                for (Entidad en : mySesion.getEntidadList()) {
                    if (en.getTabla().equals(s)) {
                        entidad = en;
                    }
                }
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAsObject()" + e.getLocalizedMessage());
        }

        return entidad;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
        String r = "";
        try {
            if (o instanceof Entidad) {
                Entidad entidad = (Entidad) o;
                r = entidad.getTabla();
            } else if (o instanceof String) {
                r = (String) o;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAsString()" + e.getLocalizedMessage());

        }

        return r;
    }
}
