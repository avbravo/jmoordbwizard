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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author avbravoserver
 */
@Named
@ViewScoped
public class EntityView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    MySesion mySesion;
    List<EntidadMenu> entidadMenuList = new ArrayList<>();
    EntidadMenu selected = new EntidadMenu();
    private List<EntidadMenu> filtered = new ArrayList<>();
    private List<String> masterDetailsList =  new ArrayList<>();
    private List<String> menubarList = new ArrayList<>();

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

    
  

    
    
    
    /**
     * Creates a new instance of EntityView
     */
    public EntityView() {
    }

     @PostConstruct
    public void init() {
        JSFUtil.addWarningMessage("test->init()");
         System.out.println("++++++++++++++++++++++++++++ init() entityView");
             try {
            entidadMenuList.removeAll(entidadMenuList);
            masterDetailsList.removeAll(masterDetailsList);
            
            masterDetailsList.add("<Create>");
            masterDetailsList.add("<No Create >");
            menubarList.removeAll(menubarList);
            menubarList = mySesion.getMenubarList();
            for (Entidad entidad : mySesion.getEntidadList()) {
                EntidadMenu em = new EntidadMenu();
                em.setEntidad(entidad.getTabla());
                masterDetailsList.add(entidad.getTabla());
                //
                for (String s : mySesion.getMenubarList()) {
                    em.setMenu(s);
                }
                em.setMasterdetails("<Create>");
                entidadMenuList.add(em);

            }
//            mySesion.setEntidadMenuList(entidadMenuList);
//      
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getEntidadMenuList() " + e.getLocalizedMessage());
        }
      

    }
    
    
    
    public void onCellEdit(CellEditEvent event) {

        try {
            
            System.out.println("####### onCellEdit()");
            System.out.println("getRowIndex() "+event.getRowIndex());
            
//RequestContext.getCurrentInstance().execute("PF('detalleDialogVar').show();");
            selected = entidadMenuList.get(event.getRowIndex());
            System.out.println(selected.getEntidad() +"*** "+selected.getMenu() + " ---- "+selected.getMasterdetails());
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();
            System.out.println("--->voy a ver los valores");
            if(oldValue == null){
                System.out.println("oldValue null");
            }else{
                System.out.println("oldValue "+oldValue.toString() );
            }
            if(newValue == null){
                System.out.println("newValue null");
            }else{
                        System.out.println( " new Value "+newValue.toString());
            }
//            System.out.println("oldValue "+oldValue.toString() + " new Value "+newValue.toString());
// JSFUtil.infoDialog("Texto","oldValue "+oldValue.toString() + " new Value "+newValue.toString());
        // entidadMenuList.get(event.getRowIndex()).setMenu(selected.getMenu());
            System.out.println("===========termino onCellEdit ");

        } catch (Exception e) {
            System.out.println("error onCellEdit() "+e.getLocalizedMessage());
            JSFUtil.addErrorMessage("onCellEdit()" + e.getLocalizedMessage());
        }

    }
    
     public String irPagina5() {
        try {
for(EntidadMenu em: entidadMenuList){
    System.out.println("-------------------------------");
    System.out.println(em.getEntidad() +" : " +em.getMenu() + " --> "+em.getMasterdetails());
     
}
            if (!mySesion.getPagina5()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }
            return "pagina5.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina5() " + e.getLocalizedMessage());
        }
        return "";

    }
}
