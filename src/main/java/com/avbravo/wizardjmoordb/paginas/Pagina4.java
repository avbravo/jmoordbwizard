/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.paginas;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.beans.EntidadMenu;
import com.avbravo.wizardjmoordb.menu.MyMenu;
import com.avbravo.wizardjmoordb.menu.MySubmenu;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author avbravoserver
 */
@Named
//@RequestScoped
@SessionScoped
public class Pagina4 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    MySesion mySesion;
    List<EntidadMenu> entidadMenuList = new ArrayList<>();
    EntidadMenu selected = new EntidadMenu();
    private List<EntidadMenu> filtered = new ArrayList<>();
    private List<String> masterDetailsList = new ArrayList<>();
    private List<String> menubarList = new ArrayList<>();

    public MySesion getMySesion() {
        return mySesion;
    }

    public void setMySesion(MySesion mySesion) {
        this.mySesion = mySesion;
    }

  
    
    

    public EntidadMenu getSelected() {
        return selected;
    }

    public void setSelected(EntidadMenu selected) {
        this.selected = selected;
    }

    public List<EntidadMenu> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<EntidadMenu> filtered) {
        this.filtered = filtered;
    }

    public List<String> getMasterDetailsList() {
        return masterDetailsList;
    }

    public void setMasterDetailsList(List<String> masterDetailsList) {
        this.masterDetailsList = masterDetailsList;
    }

    public List<String> getMenubarList() {
        return menubarList;
    }

    public void setMenubarList(List<String> menubarList) {
        this.menubarList = menubarList;
    }

    public List<EntidadMenu> getEntidadMenuList() {

        return entidadMenuList;
    }

    public void setEntidadMenuList(List<EntidadMenu> entidadMenuList) {
        this.entidadMenuList = entidadMenuList;
    }



    public void onDragDrop(TreeDragDropEvent event) {
        try {

            TreeNode dragNode = event.getDragNode();

            TreeNode dropNode = event.getDropNode();

            int dropIndex = event.getDropIndex();
//            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);
//            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("onDragDrop() " + e.getLocalizedMessage());

        }

    }

    /**
     * Creates a new instance of EntityView
     */
    public Pagina4() {
    }

    @PostConstruct
    public void init() {


    }

   

//    public String show() {
//        try {
//
//            Utilidades.showTree(root2);
//            System.out.println("compuesto: " + Utilidades.componerMenu(root2));
//
//        } catch (Exception e) {
//            JSFUtil.addErrorMessage("show() " + e.getLocalizedMessage());
//        }
//        return "";
//    }

    public String irPagina5() {
        try {
            if (!mySesion.getPagina5()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }
            if (Utilidades.getNumeroHijos(mySesion.getRoot1()) > 1) {
                JSFUtil.addWarningMessage("Hay elementos del menu sin asignar..."+ Utilidades.getNumeroHijos(mySesion.getRoot1()));
                return "";
            }

            if (Utilidades.getNumeroHijos(mySesion.getRoot2()) == 0) {
                JSFUtil.addWarningMessage("No hay elementos asignados al menu");
                return "";
            }
            if (Utilidades.tieneNietos(mySesion.getRoot2())) {
                JSFUtil.addWarningMessage("Se colocaron menus dentro de otros elementos de m enu corrigalo.");
                return "";
            }

            mySesion.setTitulosSubMenu(Utilidades.componerMenuFromTreeNode(mySesion.getRoot2()));
            mySesion.setTreeNodeMenu(mySesion.getRoot2());
            mySesion.iniciarTree();
            return "pagina5.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina5() " + e.getLocalizedMessage());
        }
        return "";

    }
}
