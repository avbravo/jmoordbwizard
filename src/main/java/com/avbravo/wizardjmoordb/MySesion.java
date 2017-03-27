/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import com.avbravo.wizardjmoordb.beans.Archivos;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.beans.EntidadMenu;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named(value = "mySesion")
@SessionScoped
public class MySesion implements Serializable {
   private static final long serialVersionUID = 1L;
    @Inject
    Rutas rutas;
    private String persistenceContext = "";
    private String paquete = "";
    private String paqueteWeb = "";
    private String paquetePath = "";
    private String paquetePathWeb = "";
    private String username = "";
    private String usernameWeb = "";
    private String proyecto = "";
    private String proyectoWeb = "";
    private String proyectoPlaceHolder = "";
    private String proyectoPlaceHolderWeb = "";
    private String pathProyecto = "";
    private String pathProyectoWeb = "";
    private String path = "";
    private String pathWeb = "";
    /*
    menu
     */
    private Integer numeroMenuBar = 5; // numero de menu bar para el menu
    private String titulosMenuBar = "Archivo, Editar,Reportes,Depurar,Ayuda"; //"Nombres de los elementos de la barra
    private List<String> menubarList = new ArrayList<>(); //
    //opcion  para el menu de reportes
    private String opcionMenuReportes="";
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
    private Boolean frameworkBootfaces =false;
    private Boolean frameworkMaterialprime=false;
    /*
    login
     */
    Entidad entidadUser = new Entidad();
    Entidad entidadRoles = new Entidad();
    Entidad entidadGruposUsuariosMultiples = new Entidad();
    private String atributosUsername = "";
    private String atributosPassword = "";
    private String atributosNombreMostrar = "";
    // es la columna del grupo de usuario que se muestra
    private String atributosGrupousuarioMostrar = "";

    private Boolean multiplesRoles = false;
    //agrega el usuario logeado en los metodos para el crud 
    private Boolean addUserNameLogeado=false;
    //agrega la fecha para las enity del sistema en los crud
    private Boolean addFechaSystema=false;

    //registra los mensajes generales y de error 
    // para escribirlos en el archivo Information.txt
    private List<String> errorList= new ArrayList<>();
    private List<String> mensajesList = new ArrayList<>();
/*
    
    */
    private Boolean allTablesWithPrimaryKey=true;
    /*
    repositorio local,git,mercurial
    */
    private Boolean esRepositorioGitMercurial=false;
    private Boolean esRepositorioGitMercurialWeb=false;
    private String tipoRepositorio="";
    private String userRepositorio="";
    private String passwordRepositorio="";

    /*
    paginas
    */
    private Boolean pagina1=false;
    private Boolean pagina2=false;
    private Boolean pagina3 =false;
    private Boolean pagina4 =false;
    private Boolean pagina5=false;
    private Boolean pagina6 =false;
/*
    
    */
    private Boolean addCreateTablePersitenceXML=false;
    
    /*
    
    */
    //almacena las opciones del menu donde se indica a que menu se debe asignar los entity
    List<EntidadMenu> entidadMenuList = new ArrayList<>();
     private List<String> masterDetailsList =  new ArrayList<>();

    public Boolean getEsRepositorioGitMercurialWeb() {
        return esRepositorioGitMercurialWeb;
    }

    public void setEsRepositorioGitMercurialWeb(Boolean esRepositorioGitMercurialWeb) {
        this.esRepositorioGitMercurialWeb = esRepositorioGitMercurialWeb;
    }

    public String getPaqueteWeb() {
        return paqueteWeb;
    }

    public void setPaqueteWeb(String paqueteWeb) {
        this.paqueteWeb = paqueteWeb;
    }

    public String getPaquetePathWeb() {
        return paquetePathWeb;
    }

    public void setPaquetePathWeb(String paquetePathWeb) {
        this.paquetePathWeb = paquetePathWeb;
    }

    public String getPathWeb() {
        return pathWeb;
    }

    public void setPathWeb(String pathWeb) {
        this.pathWeb = pathWeb;
    }

    public String getUsernameWeb() {
        return usernameWeb;
    }

    public void setUsernameWeb(String usernameWeb) {
        this.usernameWeb = usernameWeb;
    }

    public String getProyectoWeb() {
        return proyectoWeb;
    }

    public void setProyectoWeb(String proyectoWeb) {
        this.proyectoWeb = proyectoWeb;
    }

    public String getProyectoPlaceHolderWeb() {
        return proyectoPlaceHolderWeb;
    }

    public void setProyectoPlaceHolderWeb(String proyectoPlaceHolderWeb) {
        this.proyectoPlaceHolderWeb = proyectoPlaceHolderWeb;
    }

     
     
     
     
     
    public String getOpcionMenuReportes() {
        return opcionMenuReportes;
    }

    public void setOpcionMenuReportes(String opcionMenuReportes) {
        this.opcionMenuReportes = opcionMenuReportes;
    }

    public List<String> getMasterDetailsList() {
        return masterDetailsList;
    }

    public void setMasterDetailsList(List<String> masterDetailsList) {
        this.masterDetailsList = masterDetailsList;
    }

    
    
    
    
    public List<EntidadMenu> getEntidadMenuList() {
        return entidadMenuList;
    }

    public void setEntidadMenuList(List<EntidadMenu> entidadMenuList) {
        this.entidadMenuList = entidadMenuList;
    }

    public String getPathProyectoWeb() {
        return pathProyectoWeb;
    }

    public void setPathProyectoWeb(String pathProyectoWeb) {
        this.pathProyectoWeb = pathProyectoWeb;
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

    
    
    
    
    public Boolean getAddCreateTablePersitenceXML() {
        return addCreateTablePersitenceXML;
    }

    public void setAddCreateTablePersitenceXML(Boolean addCreateTablePersitenceXML) {
        this.addCreateTablePersitenceXML = addCreateTablePersitenceXML;
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
    
    
    public Boolean getEsRepositorioGitMercurial() {
        return esRepositorioGitMercurial;
    }

    public void setEsRepositorioGitMercurial(Boolean esRepositorioGitMercurial) {
        this.esRepositorioGitMercurial = esRepositorioGitMercurial;
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
    
    

    public String getTipoRepositorio() {
        return tipoRepositorio;
    }

    public void setTipoRepositorio(String tipoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
    }
    

    public Boolean getAllTablesWithPrimaryKey() {
        return allTablesWithPrimaryKey;
    }

    public void setAllTablesWithPrimaryKey(Boolean allTablesWithPrimaryKey) {
        this.allTablesWithPrimaryKey = allTablesWithPrimaryKey;
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

    
    
    
    public List<String> getMenubarList() {
        return menubarList;
    }

    public void setMenubarList(List<String> menubarList) {
        this.menubarList = menubarList;
    }

    
    
    
    public String getTitulosMenuBar() {
        return titulosMenuBar;
    }

    public void setTitulosMenuBar(String titulosMenuBar) {
        this.titulosMenuBar = titulosMenuBar;
    }

    public String getAtributosGrupousuarioMostrar() {
        return atributosGrupousuarioMostrar;
    }

    public void setAtributosGrupousuarioMostrar(String atributosGrupousuarioMostrar) {
        this.atributosGrupousuarioMostrar = atributosGrupousuarioMostrar;
    }

    public Boolean getMultiplesRoles() {
        return multiplesRoles;
    }

    public void setMultiplesRoles(Boolean multiplesRoles) {
        this.multiplesRoles = multiplesRoles;
    }

    public Entidad getEntidadGruposUsuariosMultiples() {
        return entidadGruposUsuariosMultiples;
    }

    public void setEntidadGruposUsuariosMultiples(Entidad entidadGruposUsuariosMultiples) {
        this.entidadGruposUsuariosMultiples = entidadGruposUsuariosMultiples;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Integer getNumeroMenuBar() {
        return numeroMenuBar;
    }

    public void setNumeroMenuBar(Integer numeroMenuBar) {
        this.numeroMenuBar = numeroMenuBar;
    }

    /**
     * Creates a new instance of MySesion
     */
    public MySesion() {
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

    public String getPathProyecto() {
        return pathProyecto;
    }

    public void setPathProyecto(String pathProyecto) {
        this.pathProyecto = pathProyecto;
    }

    public String getProyectoPlaceHolder() {
        return proyectoPlaceHolder;
    }

    public void setProyectoPlaceHolder(String proyectoPlaceHolder) {
        this.proyectoPlaceHolder = proyectoPlaceHolder;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPaquetePath() {
        return paquetePath;
    }

    public void setPaquetePath(String paquetePath) {
        this.paquetePath = paquetePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPaquete() {
        return paquete;
    }

    public void setPaquete(String paquete) {
        this.paquete = paquete;
    }

    public String getPersistenceContext() {
        return persistenceContext;
    }

    public void setPersistenceContext(String persistenceContext) {
        this.persistenceContext = persistenceContext;
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

}
