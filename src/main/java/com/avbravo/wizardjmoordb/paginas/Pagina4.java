/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.paginas;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.beans.EntidadMenu;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
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

    private TreeNode root1;

    private TreeNode root2;

    private TreeNode selectedNode1;

    private TreeNode selectedNode2;

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

    public TreeNode getRoot1() {
        return root1;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public TreeNode getSelectedNode1() {
        return selectedNode1;
    }

    public void setSelectedNode1(TreeNode selectedNode1) {
        this.selectedNode1 = selectedNode1;
    }

    public TreeNode getSelectedNode2() {
        return selectedNode2;
    }

    public void setSelectedNode2(TreeNode selectedNode2) {
        this.selectedNode2 = selectedNode2;
    }

    public void onDragDrop(TreeDragDropEvent event) {
        try {

            TreeNode dragNode = event.getDragNode();

            TreeNode dropNode = event.getDropNode();

            int dropIndex = event.getDropIndex();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dragged " + dragNode.getData(), "Dropped on " + dropNode.getData() + " at " + dropIndex);
            FacesContext.getCurrentInstance().addMessage(null, message);

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
        try {
            root1 = new DefaultTreeNode("Root", null);
            TreeNode node0 = new DefaultTreeNode("Sin Asignar", root1);
            mySesion.getEntidadList().forEach((entidad) -> {
                TreeNode nodeentidad = new DefaultTreeNode(entidad.getTabla(), node0);
            });

            root2 = new DefaultTreeNode("Root2", null);

            mySesion.getMenubarList().forEach((s) -> {
                TreeNode items = new DefaultTreeNode(s, root2);
            });

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getEntidadMenuList() " + e.getLocalizedMessage());
        }

    }

    public String show() {
        try {
       
             Utilidades.showTree(root2);
            System.out.println("compuesto: "+ Utilidades.componerMenu(root2));
                     
        } catch (Exception e) {
            JSFUtil.addErrorMessage("show() " + e.getLocalizedMessage());
        }
        return "";
    }

    public String irPagina5() {
        try {
            if (!mySesion.getPagina5()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }
            if(Utilidades.getNumeroHijos(root1) > 1){
               JSFUtil.addWarningMessage("Hay elementos del menu sin asignar...");
                return ""; 
            }
            
            if(Utilidades.getNumeroHijos(root2) == 0){
               JSFUtil.addWarningMessage("No hay elementos asignados al menu");
                return ""; 
            }
            if(Utilidades.tieneNietos(root2)){
              JSFUtil.addWarningMessage("Se colocaron menus dentro de otros elementos de m enu corrigalo.");
                return "";   
            }
            
            mySesion.setTitulosSubMenu( Utilidades.componerMenu(root2));
            mySesion.setTreeNodeMenu(root2);
            return "pagina5.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina5() " + e.getLocalizedMessage());
        }
        return "";

    }
}
