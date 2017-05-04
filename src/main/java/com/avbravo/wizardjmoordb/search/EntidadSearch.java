/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.search;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@ViewScoped
public class EntidadSearch implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(EntidadSearch.class.getName());
    
    
    @Inject
    MySesion mySesion;
    List<Entidad> listEntidad = new ArrayList<>();
    List<String> listColumnas = new ArrayList<>();
    List<String> listColumnasUser = new ArrayList<>();
    List<String> listColumnasPassword = new ArrayList<>();
    List<String> listIdGroup = new ArrayList<>();
    List<String> listColumnasNombreMostrar = new ArrayList<>();
    
     List<String> listColumnasGrupousuario = new ArrayList<>();

    public List<String> getListIdGroup() {
        return listIdGroup;
    }

    public void setListIdGroup(List<String> listIdGroup) {
        this.listIdGroup = listIdGroup;
    }

     
     
     
    public List<String> getListColumnasGrupousuario() {
        return listColumnasGrupousuario;
    }

    public void setListColumnasGrupousuario(List<String> listColumnasGrupousuario) {
        this.listColumnasGrupousuario = listColumnasGrupousuario;
    }

     
     
    public List<String> getListColumnasUser() {
        return listColumnasUser;
    }

    public void setListColumnasUser(List<String> listColumnasUser) {
        this.listColumnasUser = listColumnasUser;
    }

    public List<String> getListColumnasPassword() {
        return listColumnasPassword;
    }

    public void setListColumnasPassword(List<String> listColumnasPassword) {
        this.listColumnasPassword = listColumnasPassword;
    }

    public List<String> getListColumnasNombreMostrar() {
        return listColumnasNombreMostrar;
    }

    public void setListColumnasNombreMostrar(List<String> listColumnasNombreMostrar) {
        this.listColumnasNombreMostrar = listColumnasNombreMostrar;
    }

    /**
     * Creates a new instance of EntidadSearch
     */
    public EntidadSearch() {
    }

    public List<Entidad> getListEntidad() {
        return mySesion.getEntidadList();
    }

    public void setListEntidad(List<Entidad> listEntidad) {
        this.listEntidad = listEntidad;
    }

    
    @PostConstruct
    public void init() {
        onChangeEntidadUser();
    }
    public String onChangeEntidadUser() {
        try {
           
           
            listColumnas = new ArrayList<>();
            listIdGroup = new ArrayList<>();
            for (Atributos a : mySesion.getEntidadUser().getAtributosList()) {
                if (a.getTipo().equals("String")) {
                    listColumnas.add(a.getNombre());
                    
                }
            }
            listColumnasUser = listColumnas;
            listColumnasPassword = listColumnas;
            listColumnasNombreMostrar = listColumnas;
            listIdGroup = listColumnas;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("onChangeEntidadUser() " + e.getLocalizedMessage());
        }
        return "";

    }
    
   
    public String onChangeEntidadGrupousuarios() {
        try {

            listColumnasGrupousuario = new ArrayList<>();
            for (Atributos a : mySesion.getEntidadGruposUsuariosMultiples().getAtributosList()) {                
                if (a.getTipo().equals("String")) {
                    listColumnasGrupousuario.add(a.getNombre());
                }
            }
                    
        } catch (Exception e) {
            JSFUtil.addErrorMessage("entidadGrupousuarioSeleccionada() " + e.getLocalizedMessage());
        }
        return null;

    }

    public String atributosUserNameSeleccionado() {
        try {

//            JSFUtil.addWarningMessage("--->" + mySesion.getAtributosUsername());
//            listColumnasPassword = listColumnas;
//            listColumnasNombreMostrar = listColumnas;
//            listColumnasPassword.remove(mySesion.getAtributosUsername());
//            listColumnasNombreMostrar.remove(mySesion.getAtributosUsername());
        } catch (Exception e) {
            JSFUtil.addErrorMessage("atributosUserNamerSeleccionado() " + e.getLocalizedMessage());
        }
        return null;

    }

    public String atributosPasswordSeleccionado() {
        try {
//             listColumnasNombreMostrar = listColumnas;
//            listColumnasNombreMostrar.remove(mySesion.getAtributosPassword());
//          listColumnasNombreMostrar.remove(mySesion.getAtributosUsername());
        } catch (Exception e) {
            JSFUtil.addErrorMessage("entidadUserSeleccionada() " + e.getLocalizedMessage());
        }
        return null;

    }

    public String atributosNombreMostrarSeleccionado() {
        try {

//            listColumnasUser = mySesion.getEntidadUser().getAtributosList();
//            listColumnasPassword = mySesion.getEntidadUser().getAtributosList();
//            listColumnasNombreMostrar = mySesion.getEntidadUser().getAtributosList();
        } catch (Exception e) {
            JSFUtil.addErrorMessage("entidadUserSeleccionada() " + e.getLocalizedMessage());
        }
        return null;

    }
//    public String atributosNombreMostrarSeleccionado() {
//        try {
//
////            listColumnasUser = mySesion.getEntidadUser().getAtributosList();
////            listColumnasPassword = mySesion.getEntidadUser().getAtributosList();
////            listColumnasNombreMostrar = mySesion.getEntidadUser().getAtributosList();
//        } catch (Exception e) {
//            JSFUtil.addErrorMessage("entidadUserSeleccionada() " + e.getLocalizedMessage());
//        }
//        return null;
//
//    }
}
