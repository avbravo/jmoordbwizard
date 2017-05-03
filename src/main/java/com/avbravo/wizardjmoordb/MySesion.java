/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import com.avbravo.wizardjmoordb.beans.Archivos;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.beans.EntidadMenu;
import com.avbravo.wizardjmoordb.menu.MyMenu;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author avbravoserver
 */
@Named(value = "mySesion")
@SessionScoped
public class MySesion implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username = "";
    private String database;
    private String databasename;
    private TreeNode treeNodeMenu;

    /*
    menu
     */
    private Integer numeroMenuBar = 3; // numero de menu bar para el menu
    private String titulosMenuBar = "Registros,Reportes,Ayuda"; //"Nombres de los elementos de la barra
    private String titulosSubMenu = ""; //"Nombres de los elementos de la barra
    private List<String> menubarList = new ArrayList<>(); //
    private List<MyMenu> mymenuList = new ArrayList<>();
    //opcion  para el menu de reportes
    private String opcionMenuReportes = "";
    /*
    roles
     */
    //es la cadena de roles separa por guiones
    private String roles = "Administrador, Anonimo,Test";
    private List<String> rolesList = new ArrayList<>();

    private List<Archivos> archivosList = new ArrayList<>();
    private List<Entidad> entidadList = new ArrayList<>();
    private List<Entidad> ejbList = new ArrayList<>();
    private List<Archivos> controllerList = new ArrayList<>();
    private List<Archivos> searchList = new ArrayList<>();

    private Boolean frameworkPrimefaces = true;
    private Boolean frameworkBootfaces = false;
    private Boolean frameworkMaterialprime = false;
    /*
    login
     */
    Entidad entidadUser = new Entidad();
    Entidad entidadRoles = new Entidad();
    Entidad entidadGruposUsuariosMultiples = new Entidad();
    private String atributosUsername = "";
    private String atributosPassword = "";
    private String atributosNombreMostrar = "";
    private String atributosIdGrupo = "";

    // es la columna del grupo de usuario que se muestra
    private String atributosGrupousuarioMostrar = "";

//    private Boolean multiplesRoles = false;
//    
//    private Boolean unsoloRol =false;
    //agrega el usuario logeado en los metodos para el crud 
    private Boolean addUserNameLogeado = false;
    //agrega la fecha para las enity del sistema en los crud
    private Boolean addFechaSystema = false;

    //registra los mensajes generales y de error 
    // para escribirlos en el archivo Information.txt
    private List<String> errorList = new ArrayList<>();
    private List<String> mensajesList = new ArrayList<>();
    /*
    
     */
    private Boolean allTablesWithPrimaryKey = true;
    /*
    repositorio local,git,mercurial
     */

    private String tipoRepositorio = "";
    private String userRepositorio = "";
    private String passwordRepositorio = "";

    /*
    paginas
     */
    private Boolean pagina1 = false;
    private Boolean pagina2 = false;
    private Boolean pagina3 = false;
    private Boolean pagina4 = false;
    private Boolean pagina5 = false;
    private Boolean pagina6 = false;
    /**
     * User Group
     */
    private String typeUserGroup = "Without Roles";
    private Boolean typeUserGroupWithOutRol = false;
    private Boolean typeUserGroupField = false;
    private Boolean typeUserGroupEntity = false;
    private Boolean typeUserGroupList = false;

    /**
     * Date GMT+2
     */
    private String timeZone = "";
    private String patternDate = "dd/MM/yyyy";
    private String patternDateTime = "HH:mm dd/MM/yyyy";
    /*
    Button
    definir los valores: Texto,Iconos, Texto e Iconos
     */
    private String typeOfButton = "Texto";
    //

    /**
     * View Numero de elementos por fila en un formulario View
     */
    private Integer fieldByRowView = 1;

    /**
     * Autocomplete
     */
    private Integer maximoAutocomplete = 4;
    private Integer maximoAutocompleteItemLabel = 3;
    private Integer maximoAutocompleteItemTip = 5;

    //almacena las opciones del menu donde se indica a que menu se debe asignar los entity
    List<EntidadMenu> entidadMenuList = new ArrayList<>();

    private List<String> masterDetailsList = new ArrayList<>();

    public List<MyMenu> getMymenuList() {
        return mymenuList;
    }

    public void setMymenuList(List<MyMenu> mymenuList) {
        this.mymenuList = mymenuList;
    }
    
    

    //elemento de menu que sera almacenado

    public String getTitulosSubMenu() {
        return titulosSubMenu;
    }

    public void setTitulosSubMenu(String titulosSubMenu) {
        this.titulosSubMenu = titulosSubMenu;
    }
    

  
    
    
    
    public MySesion() {
        treeNodeMenu = new DefaultTreeNode("Root2", null);
    }

    public TreeNode getTreeNodeMenu() {
        return treeNodeMenu;
    }

    public void setTreeNodeMenu(TreeNode treeNodeMenu) {
        this.treeNodeMenu = treeNodeMenu;
    }

    public String getTypeOfButton() {
        return typeOfButton;
    }

    public void setTypeOfButton(String typeOfButton) {
        this.typeOfButton = typeOfButton;
    }

    public String getPatternDate() {
        return patternDate;
    }

    public void setPatternDate(String patternDate) {
        this.patternDate = patternDate;
    }

    public String getPatternDateTime() {
        return patternDateTime;
    }

    public void setPatternDateTime(String patternDateTime) {
        this.patternDateTime = patternDateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Integer getFieldByRowView() {
        return fieldByRowView;
    }

    public void setFieldByRowView(Integer fieldByRowView) {
        this.fieldByRowView = fieldByRowView;
    }

    public Integer getMaximoAutocomplete() {
        return maximoAutocomplete;
    }

    public void setMaximoAutocomplete(Integer maximoAutocomplete) {
        this.maximoAutocomplete = maximoAutocomplete;
    }

    public Integer getMaximoAutocompleteItemLabel() {
        return maximoAutocompleteItemLabel;
    }

    public void setMaximoAutocompleteItemLabel(Integer maximoAutocompleteItemLabel) {
        this.maximoAutocompleteItemLabel = maximoAutocompleteItemLabel;
    }

    public Integer getMaximoAutocompleteItemTip() {
        return maximoAutocompleteItemTip;
    }

    public void setMaximoAutocompleteItemTip(Integer maximoAutocompleteItemTip) {
        this.maximoAutocompleteItemTip = maximoAutocompleteItemTip;
    }

    public String getTypeUserGroup() {
        return typeUserGroup;
    }

    public void setTypeUserGroup(String typeUserGroup) {
        this.typeUserGroup = typeUserGroup;
    }

    public Boolean getTypeUserGroupWithOutRol() {
        return typeUserGroupWithOutRol;
    }

    public void setTypeUserGroupWithOutRol(Boolean typeUserGroupWithOutRol) {
        this.typeUserGroupWithOutRol = typeUserGroupWithOutRol;
    }

    public Boolean getTypeUserGroupField() {
        return typeUserGroupField;
    }

    public void setTypeUserGroupField(Boolean typeUserGroupField) {
        this.typeUserGroupField = typeUserGroupField;
    }

    public Boolean getTypeUserGroupEntity() {
        return typeUserGroupEntity;
    }

    public void setTypeUserGroupEntity(Boolean typeUserGroupEntity) {
        this.typeUserGroupEntity = typeUserGroupEntity;
    }

    public Boolean getTypeUserGroupList() {
        return typeUserGroupList;
    }

    public void setTypeUserGroupList(Boolean typeUserGroupList) {
        this.typeUserGroupList = typeUserGroupList;
    }

    public String getAtributosIdGrupo() {
        return atributosIdGrupo;
    }

    public void setAtributosIdGrupo(String atributosIdGrupo) {
        this.atributosIdGrupo = atributosIdGrupo;
    }

    public String getDatabasename() {
        return databasename;
    }

    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumeroMenuBar() {
        return numeroMenuBar;
    }

    public void setNumeroMenuBar(Integer numeroMenuBar) {
        this.numeroMenuBar = numeroMenuBar;
    }

    public String getTitulosMenuBar() {
        return titulosMenuBar;
    }

    public void setTitulosMenuBar(String titulosMenuBar) {
        this.titulosMenuBar = titulosMenuBar;
    }

    public List<String> getMenubarList() {
        return menubarList;
    }

    public void setMenubarList(List<String> menubarList) {
        this.menubarList = menubarList;
    }

    public String getOpcionMenuReportes() {
        return opcionMenuReportes;
    }

    public void setOpcionMenuReportes(String opcionMenuReportes) {
        this.opcionMenuReportes = opcionMenuReportes;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }

    public List<Archivos> getArchivosList() {
        return archivosList;
    }

    public void setArchivosList(List<Archivos> archivosList) {
        this.archivosList = archivosList;
    }

    public List<Entidad> getEntidadList() {
        return entidadList;
    }

    public void setEntidadList(List<Entidad> entidadList) {
        this.entidadList = entidadList;
    }

    public List<Entidad> getEjbList() {
        return ejbList;
    }

    public void setEjbList(List<Entidad> ejbList) {
        this.ejbList = ejbList;
    }

    public List<Archivos> getControllerList() {
        return controllerList;
    }

    public void setControllerList(List<Archivos> controllerList) {
        this.controllerList = controllerList;
    }

    public List<Archivos> getSearchList() {
        return searchList;
    }

    public void setSearchList(List<Archivos> searchList) {
        this.searchList = searchList;
    }

    public Boolean getFrameworkPrimefaces() {
        return frameworkPrimefaces;
    }

    public void setFrameworkPrimefaces(Boolean frameworkPrimefaces) {
        this.frameworkPrimefaces = frameworkPrimefaces;
    }

    public Boolean getFrameworkBootfaces() {
        return frameworkBootfaces;
    }

    public void setFrameworkBootfaces(Boolean frameworkBootfaces) {
        this.frameworkBootfaces = frameworkBootfaces;
    }

    public Boolean getFrameworkMaterialprime() {
        return frameworkMaterialprime;
    }

    public void setFrameworkMaterialprime(Boolean frameworkMaterialprime) {
        this.frameworkMaterialprime = frameworkMaterialprime;
    }

    public Entidad getEntidadUser() {
        return entidadUser;
    }

    public void setEntidadUser(Entidad entidadUser) {
        this.entidadUser = entidadUser;
    }

    public Entidad getEntidadRoles() {
        return entidadRoles;
    }

    public void setEntidadRoles(Entidad entidadRoles) {
        this.entidadRoles = entidadRoles;
    }

    public Entidad getEntidadGruposUsuariosMultiples() {
        return entidadGruposUsuariosMultiples;
    }

    public void setEntidadGruposUsuariosMultiples(Entidad entidadGruposUsuariosMultiples) {
        this.entidadGruposUsuariosMultiples = entidadGruposUsuariosMultiples;
    }

    public String getAtributosUsername() {
        return atributosUsername;
    }

    public void setAtributosUsername(String atributosUsername) {
        this.atributosUsername = atributosUsername;
    }

    public String getAtributosPassword() {
        return atributosPassword;
    }

    public void setAtributosPassword(String atributosPassword) {
        this.atributosPassword = atributosPassword;
    }

    public String getAtributosNombreMostrar() {
        return atributosNombreMostrar;
    }

    public void setAtributosNombreMostrar(String atributosNombreMostrar) {
        this.atributosNombreMostrar = atributosNombreMostrar;
    }

    public String getAtributosGrupousuarioMostrar() {
        return atributosGrupousuarioMostrar;
    }

    public void setAtributosGrupousuarioMostrar(String atributosGrupousuarioMostrar) {
        this.atributosGrupousuarioMostrar = atributosGrupousuarioMostrar;
    }

    public Boolean getAddUserNameLogeado() {
        return addUserNameLogeado;
    }

    public void setAddUserNameLogeado(Boolean addUserNameLogeado) {
        this.addUserNameLogeado = addUserNameLogeado;
    }

    public Boolean getAddFechaSystema() {
        return addFechaSystema;
    }

    public void setAddFechaSystema(Boolean addFechaSystema) {
        this.addFechaSystema = addFechaSystema;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public List<String> getMensajesList() {
        return mensajesList;
    }

    public void setMensajesList(List<String> mensajesList) {
        this.mensajesList = mensajesList;
    }

    public Boolean getAllTablesWithPrimaryKey() {
        return allTablesWithPrimaryKey;
    }

    public void setAllTablesWithPrimaryKey(Boolean allTablesWithPrimaryKey) {
        this.allTablesWithPrimaryKey = allTablesWithPrimaryKey;
    }

    public String getTipoRepositorio() {
        return tipoRepositorio;
    }

    public void setTipoRepositorio(String tipoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
    }

    public String getUserRepositorio() {
        return userRepositorio;
    }

    public void setUserRepositorio(String userRepositorio) {
        this.userRepositorio = userRepositorio;
    }

    public String getPasswordRepositorio() {
        return passwordRepositorio;
    }

    public void setPasswordRepositorio(String passwordRepositorio) {
        this.passwordRepositorio = passwordRepositorio;
    }

    public Boolean getPagina1() {
        return pagina1;
    }

    public void setPagina1(Boolean pagina1) {
        this.pagina1 = pagina1;
    }

    public Boolean getPagina2() {
        return pagina2;
    }

    public void setPagina2(Boolean pagina2) {
        this.pagina2 = pagina2;
    }

    public Boolean getPagina3() {
        return pagina3;
    }

    public void setPagina3(Boolean pagina3) {
        this.pagina3 = pagina3;
    }

    public Boolean getPagina4() {
        return pagina4;
    }

    public void setPagina4(Boolean pagina4) {
        this.pagina4 = pagina4;
    }

    public Boolean getPagina5() {
        return pagina5;
    }

    public void setPagina5(Boolean pagina5) {
        this.pagina5 = pagina5;
    }

    public Boolean getPagina6() {
        return pagina6;
    }

    public void setPagina6(Boolean pagina6) {
        this.pagina6 = pagina6;
    }

    public List<EntidadMenu> getEntidadMenuList() {
        return entidadMenuList;
    }

    public void setEntidadMenuList(List<EntidadMenu> entidadMenuList) {
        this.entidadMenuList = entidadMenuList;
    }

    public List<String> getMasterDetailsList() {
        return masterDetailsList;
    }

    public void setMasterDetailsList(List<String> masterDetailsList) {
        this.masterDetailsList = masterDetailsList;
    }

}
