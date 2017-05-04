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

    private TreeNode root1;

    private TreeNode root2;

    private TreeNode selectedNode1;

    private TreeNode selectedNode2;
    List<String> agregadosList = new ArrayList<>();

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
       // init();
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
        try {

            agregadosList = new ArrayList<>();
            root1 = new DefaultTreeNode("Root", null);
            root2 = new DefaultTreeNode("Root2", null);
            TreeNode nodeDisponibles = new DefaultTreeNode("Disponibles", root1);

            if (mySesion.getMymenuList().isEmpty()) {
                //Primera vez no hay elementos de menu
                mySesion.getEntidadList().forEach((entidad) -> {
                    TreeNode nodeentidad = new DefaultTreeNode(entidad.getTabla(), nodeDisponibles);
                });
//root2
                mySesion.getMenubarList().forEach((s) -> {
                    TreeNode items = new DefaultTreeNode(s, root2);
                });
            } else {
                /*
                Crea lista root2 con los menu bar
                 */

                List<TreeNode> treeNodeList = new ArrayList<>();
                mySesion.getMenubarList().forEach((s) -> {
                    TreeNode items = new DefaultTreeNode(s, root2);
                    treeNodeList.add(items);
                });
                /*
                /Agrego los entitys al root disponibles que no estan en el menu previo
                 */
                mySesion.getEntidadList().stream().filter((entidad) -> (!searchSubmenu(entidad.getTabla()))).filter((entidad) -> (esSubmenuAgregado(entidad.getTabla()))).map((entidad) -> {
                    TreeNode nodeentidad = new DefaultTreeNode(entidad.getTabla(), nodeDisponibles);
                    return entidad;
                }).forEachOrdered((entidad) -> {
                    agregadosList.add(entidad.getTabla());
                });


                /*
                Verificar los menus que se quitaron y agrega estos elementos como disponibles
                
                 */
                for (MyMenu m : mySesion.getMymenuList()) {
                    if (!searchMenu(m.getName())) {
                        m.getSubmenu().stream().filter((s) -> (!esSubmenuAgregado(s.getName()))).map((s) -> {
                            TreeNode nodeentidad = new DefaultTreeNode(s.getName(), nodeDisponibles);
                            return s;
                        }).forEachOrdered((s) -> {
                            agregadosList.add(s.getName());
                        });
                    }
                }

                /*
                Agregarlo al menu respectico
                 */
                for (MyMenu m : mySesion.getMymenuList()) {
                    
                    if (!m.getSubmenu().isEmpty()) {
                        

                        for (MySubmenu s : m.getSubmenu()) {

                            if (!s.getName().equals("")) {

                                if (!esSubmenuAgregado(s.getName())) {
                                    for (TreeNode n : treeNodeList) {
                                        if (m.getName().equals(n.getData().toString())) {
                                            TreeNode nodeentidad = new DefaultTreeNode(s.getName(), n);
                                        }
                                    }
                                }
                            }
                        }


                    }
                }
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getEntidadMenuList() " + e.getLocalizedMessage());
        }

    }

    /*
    verifica si se agrego
     */
    private Boolean esSubmenuAgregado(String submenu) {
        try {
            if (agregadosList.stream().anyMatch((s) -> (s.equals(submenu)))) {
                return true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getEntidadMenuList() " + e.getLocalizedMessage());
        }
        return false;
    }

    /**
     *
     * @param texto
     * @return
     */
    private Boolean searchSubmenu(String texto) {
        try {
            System.out.println("Test---> searchSubmenu " + texto);
            if (mySesion.getMymenuList().stream().anyMatch((m) -> (m.getSubmenu().stream().anyMatch((s) -> (texto.equals(s.getName())))))) {
                return true;
            } //comprobar los elementos de menus
        } catch (Exception e) {
            JSFUtil.addErrorMessage("searchSubmenu() " + e.getLocalizedMessage());
        }
        return false;
    }

    /*
    verifica si existe ese menu definido
     */
    private Boolean searchMenu(String menubar) {
        try {
            if (mySesion.getMenubarList().stream().anyMatch((s) -> (s.equals(menubar)))) {
                return true;
            }
            return false;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("searchMenu() " + e.getLocalizedMessage());
        }
        return false;
    }

    public String show() {
        try {

            Utilidades.showTree(root2);
            System.out.println("compuesto: " + Utilidades.componerMenu(root2));

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
            if (Utilidades.getNumeroHijos(root1) > 1) {
                JSFUtil.addWarningMessage("Hay elementos del menu sin asignar..."+ Utilidades.getNumeroHijos(root1));
                return "";
            }

            if (Utilidades.getNumeroHijos(root2) == 0) {
                JSFUtil.addWarningMessage("No hay elementos asignados al menu");
                return "";
            }
            if (Utilidades.tieneNietos(root2)) {
                JSFUtil.addWarningMessage("Se colocaron menus dentro de otros elementos de m enu corrigalo.");
                return "";
            }

            mySesion.setTitulosSubMenu(Utilidades.componerMenu(root2));
            mySesion.setTreeNodeMenu(root2);
            return "pagina5.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina5() " + e.getLocalizedMessage());
        }
        return "";

    }
}
