/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import com.avbravo.wizardjmoordb.directorios.Directorios;
import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.configurationfile.ConfigurationFilerRead;
import com.avbravo.wizardjmoordb.old.EntidadGenerador;
import com.avbravo.wizardjmoordb.provider.MongoClientProviderGenerador;
import com.avbravo.wizardjmoordb.beans.Archivos;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.beans.EntidadMenu;
import com.avbravo.wizardjmoordb.datamodel.DatamodelGenerador;
import com.avbravo.wizardjmoordb.facade.FacadeGenerador;
import com.avbravo.wizardjmoordb.configurationfile.ConfigurationFileGenerador;
import com.avbravo.wizardjmoordb.converter.ConverterGenerador;
import com.avbravo.wizardjmoordb.resources.DefaultCssGenerador;
import com.avbravo.wizardjmoordb.configuration.FacesConfigXMLGenerador;
import com.avbravo.wizardjmoordb.configuration.PomXMLEJBGenerador;

import com.avbravo.wizardjmoordb.interfaces.IControllerGenerador;
import com.avbravo.wizardjmoordb.idiomas.IdiomasGenerador;
import com.avbravo.wizardjmoordb.information.InformationGenerador;
import com.avbravo.wizardjmoordb.roles.ApplicationMenuGenerador;
import com.avbravo.wizardjmoordb.messages.MessagesPropertiesGenerador;
import com.avbravo.wizardjmoordb.configuration.PomXMLGenerador;
import com.avbravo.wizardjmoordb.resourcesfiles.ResourcesFilesGenerador;
import com.avbravo.wizardjmoordb.roles.RolesGenerador;
import com.avbravo.wizardjmoordb.controller.ControllerGenerador;
import com.avbravo.wizardjmoordb.controller.LoginControllerGenerador;
import com.avbravo.wizardjmoordb.sevices.ServicesGenerador;
import com.avbravo.wizardjmoordb.resources.CssLayoutCssGenerador;
import com.avbravo.wizardjmoordb.roles.ValidadorRolesGenerador;
import com.avbravo.wizardjmoordb.componentes.AccesodenegadoxhtmlGenerador;
import com.avbravo.wizardjmoordb.old.AcercadexhtmlGenerador;
import com.avbravo.wizardjmoordb.old.CambiarpasswordxhtmlGenerador;
//import com.avbravo.wizardjmoordb.old.ContentxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.FooterxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.RigthxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.IndexxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.ViewxhtmlGenerador;

import com.avbravo.wizardjmoordb.xhtml.ListxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.TemplatexhtmlGenerador;
import com.avbravo.wizardjmoordb.configuration.WebXMLGenerador;
import com.avbravo.wizardjmoordb.componentes.ActivoxhtmlGenerador;
import com.avbravo.wizardjmoordb.provider.CouchbaseClientProviderGenerador;
import com.avbravo.wizardjmoordb.reportes.JasperDetailsGenerador;
import com.avbravo.wizardjmoordb.reportes.JasperAllGenerador;
import com.avbravo.wizardjmoordb.search.EntidadSearch;
import com.avbravo.wizardjmoordb.utilidades.Terminal;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import com.avbravo.wizardjmoordb.xhtml.LeftxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.LoginxhtmlGenerador;
import com.avbravo.wizardjmoordb.xhtml.TopxhtmlGenerador;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author avbravoserver
 */
@Named(value = "generador")
@SessionScoped
public class Generador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Generador.class.getName());
    private boolean skip;
    private Boolean generado = false;
    private TreeNode root;
    private TreeNode selectedNode;
    private Boolean proyectoValidoEJB = false;
    private Boolean proyectoValidoJEE = false;

    private String[] selectedFramework;
    private List<String> framework;
    private String tipoRepositorio;
    private String tipoGeneracion; //codigo y paginas, solo codigo, solo paginas
    private String estilo; //codigo y paginas, solo codigo, solo paginas
    
    @Inject
    EntidadSearch entidadSearch;

    @Inject
    ProyectoEJB proyectoEJB;
    @Inject
    ProyectoJEE proyectoJEE;

    @Inject
    MySesion mySesion;
    @Inject
    Directorios directorios;
    @Inject
    EntidadGenerador entidadGenerador;
    @Inject
    ConverterGenerador converterGenerador;
    String primaryKey = "";
    @Inject
    FacadeGenerador facadeGenerador;
    @Inject
    DatamodelGenerador datamodelGenerador;
    @Inject
    MongoClientProviderGenerador mongoClientProviderGenerador;
    @Inject
    CouchbaseClientProviderGenerador couchbaseClientProviderGenerador;
    @Inject
    IdiomasGenerador idiomasGenerador;
    @Inject
    ResourcesFilesGenerador resourcesFilesGenerador;
    @Inject
    ApplicationMenuGenerador menuBeansGenerador;
    @Inject
    RolesGenerador rolesGenerador;
    @Inject
    ValidadorRolesGenerador validadorRolesGenerador;
    @Inject
    LoginControllerGenerador loginControllerGenerador;
    @Inject
    ConfigurationFileGenerador configurationFileGenerador;
    @Inject
    ConfigurationFilerRead leerConfigurationFile;
    @Inject
    IControllerGenerador iControllerGenerador;

    @Inject
    PomXMLGenerador pomXMLGenerador;
    @Inject
    PomXMLEJBGenerador pomXMLEJBGenerador;

    @Inject
    ServicesGenerador servicesGenerador;
    @Inject
    ControllerGenerador searchGenerador;
    @Inject
    ControllerGenerador controllerGenerador;
    @Inject
    InformationGenerador informationGenerador;
    @Inject
    MessagesPropertiesGenerador messagesPropertiesGenerador;
//    @Inject
//    PersistenceXMLGenerador persistenceXMLGenerador;
    @Inject
    FacesConfigXMLGenerador facesConfigGenerador;
    @Inject
    WebXMLGenerador webXMLGenerador;
    @Inject
    DefaultCssGenerador defaultCssGenerador;
    @Inject
    CssLayoutCssGenerador cssLayoutCssGenerador;
    /*
    web
     */
    @Inject
    TemplatexhtmlGenerador templatexhtmlGenerador;
    @Inject
    FooterxhtmlGenerador footerxhtmlGenerador;
    @Inject
    TopxhtmlGenerador topxhtmlGenerador;
    @Inject
    RigthxhtmlGenerador rigthxhtmlGenerador;
    @Inject
    LeftxhtmlGenerador leftxhtmlGenerador;

//    @Inject
//    MenusxhtmlGenerador menusxhtmlGenerador;
//    @Inject
//    ContentxhtmlGenerador contextxhtmlGenerador;
    @Inject
    AccesodenegadoxhtmlGenerador accesodenegadoxhtmlGenerador;
    @Inject
    ActivoxhtmlGenerador activoxhtmlGenerador;
    @Inject
    IndexxhtmlGenerador indexxhtmlGenerador;
    @Inject
    LoginxhtmlGenerador loginxhtmlGenerador;

    @Inject
    JasperAllGenerador jasperGenerador;
    @Inject
    JasperDetailsGenerador jasperDetailsGenerador;
    
    @Inject
    ViewxhtmlGenerador pagexhtmlGenerador;
    @Inject
    AcercadexhtmlGenerador acercadexhtmlGenerador;
    @Inject
    CambiarpasswordxhtmlGenerador cambiarpasswordxhtmlGenerador;
    @Inject
    ListxhtmlGenerador listxhtmlGenerador;

   
    
    
    public String getEstilo() {
        return estilo;
    }

    public void setEstilo(String estilo) {
        this.estilo = estilo;
    }

    public String getTipoGeneracion() {
        return tipoGeneracion;
    }

    public void setTipoGeneracion(String tipoGeneracion) {
        this.tipoGeneracion = tipoGeneracion;
    }

    public String getTipoRepositorio() {
        return tipoRepositorio;
    }

    public void setTipoRepositorio(String tipoRepositorio) {
        this.tipoRepositorio = tipoRepositorio;
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String[] getSelectedFramework() {
        return selectedFramework;
    }

    public void setSelectedFramework(String[] selectedFramework) {
        this.selectedFramework = selectedFramework;
    }

    public List<String> getFramework() {
        return framework;
    }

    public void setFramework(List<String> framework) {
        this.framework = framework;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public MySesion getMySesion() {
        return mySesion;
    }

    public void setMySesion(MySesion mySesion) {
        this.mySesion = mySesion;
    }

    public ProyectoEJB getProyectoEJB() {
        return proyectoEJB;
    }

    public void setProyectoEJB(ProyectoEJB proyectoEJB) {
        this.proyectoEJB = proyectoEJB;
    }

    public ProyectoJEE getProyectoJEE() {
        return proyectoJEE;
    }

    /**
     * Creates a new instance of Generador
     */
    public void setProyectoJEE(ProyectoJEE proyectoJEE) {
        this.proyectoJEE = proyectoJEE;
    }

    public Boolean getGenerado() {
        return generado;
    }

    public void setGenerado(Boolean generado) {
        this.generado = generado;
    }

    public Generador() {
        generado = false;
    }

    @PostConstruct
    public void init() {
        framework = new ArrayList<String>();
        framework.add("Primefaces");
        framework.add("BootFaces");
        framework.add("MaterialPrime");

        proyectoValidoEJB = false;
        proyectoValidoJEE = false;
        generado = false;

    }

    public String clearEJB() {
        try {

            mySesion.setPagina1(false);
            mySesion.setPagina2(false);
            mySesion.setPagina3(false);
            mySesion.setPagina4(false);
            mySesion.setPagina5(false);
            mySesion.setPagina6(false);
            proyectoEJB.setPaquete("");
            proyectoEJB.setPaquetePath("");
            proyectoEJB.setPath("");
            proyectoEJB.setPathProyecto("");
            proyectoEJB.setProyecto("");

            entidadSearch.getListColumnasGrupousuario().removeAll(entidadSearch.getListColumnasGrupousuario());
            entidadSearch.getListColumnasNombreMostrar().removeAll(entidadSearch.getListColumnasNombreMostrar());
            entidadSearch.getListColumnasPassword().removeAll(entidadSearch.getListColumnasPassword());
            entidadSearch.getListColumnasUser().removeAll(entidadSearch.getListColumnasUser());
            entidadSearch.getListEntidad().removeAll(entidadSearch.getListEntidad());

            proyectoValidoEJB = false;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("clear() " + e.getLocalizedMessage());
        }

        return "";
    }

    public String clearJEE() {
        try {
            mySesion.setPagina1(false);
            mySesion.setPagina2(false);
            mySesion.setPagina3(false);
            mySesion.setPagina4(false);
            mySesion.setPagina5(false);
            mySesion.setPagina6(false);
            proyectoJEE.setPaquete("");
            proyectoJEE.setPaquetePath("");
            proyectoJEE.setPath("");
            proyectoJEE.setPathProyecto("");
            proyectoJEE.setProyecto("");

            entidadSearch.getListColumnasGrupousuario().removeAll(entidadSearch.getListColumnasGrupousuario());
            entidadSearch.getListColumnasNombreMostrar().removeAll(entidadSearch.getListColumnasNombreMostrar());
            entidadSearch.getListColumnasPassword().removeAll(entidadSearch.getListColumnasPassword());
            entidadSearch.getListColumnasUser().removeAll(entidadSearch.getListColumnasUser());
            entidadSearch.getListEntidad().removeAll(entidadSearch.getListEntidad());

            proyectoValidoJEE = false;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("clear() " + e.getLocalizedMessage());
        }

        return "";
    }

    public String onFlowProcess(FlowEvent event) {

        if (!proyectoValidoEJB) {
            JSFUtil.warningDialog("Mensaje", "Seleccione un proyecto EJB valido");
            return "tabProject";
        }
        if (!proyectoValidoJEE) {
            JSFUtil.warningDialog("Mensaje", "Seleccione un proyecto JAVA EE valido");
            return "tabProject";
        }
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public String mostrarRutaEJB() {
        try {
            if (!proyectoEJB.getPathProyecto().equals("")) {
                return "";
            }

            proyectoEJB.setPathProyecto(System.getProperty("user.home") + proyectoEJB.getSeparator() + "NetBeansProjects" + proyectoJEE.getSeparator());
            proyectoEJB.setPaquete("com." + System.getProperty("user.name") + ".");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("mostrarRutaEJB() " + e.getLocalizedMessage());
        }

        return "";
    }

    public String mostrarRutaJEE() {
        try {
            if (!proyectoJEE.getPathProyecto().equals("")) {
                return "";
            }

            proyectoJEE.setPathProyecto(System.getProperty("user.home") + proyectoJEE.getSeparator() + "NetBeansProjects" + proyectoJEE.getSeparator());
            proyectoJEE.setPaquete("com." + System.getProperty("user.name") + ".");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("mostrarRutaLocal() " + e.getLocalizedMessage());
        }

        return "";
    }

    public String showNameProjectEJB() {
        proyectoEJB.setProyecto(Utilidades.getNombreProyectoFromPath(proyectoEJB.getPathProyecto()));
        proyectoEJB.setPaquete(proyectoEJB.getPaquete() + proyectoEJB.getProyecto());
        conectarEJB();
        return "";
    }

    public String showNameProjectJEE() {
        proyectoJEE.setProyecto(Utilidades.getNombreProyectoFromPath(proyectoJEE.getPathProyecto()));
        proyectoJEE.setPaquete(proyectoJEE.getPaquete() + proyectoJEE.getProyecto());
        conectarJEE();
        return "";
    }

    public String conectarEJB() {
        try {
            mySesion.setPagina1(false);
            mySesion.setPagina2(false);
            mySesion.setPagina3(false);
            mySesion.setPagina4(false);
            mySesion.setPagina5(false);
            mySesion.setPagina6(false);
            if (Utilidades.getLastLetter(proyectoEJB.getPathProyecto()).equals(proyectoEJB.getSeparator())) {
                JSFUtil.addWarningMessage("Ingrese la ruta del proyecto EJB. No debe terminar en " + proyectoEJB.getSeparator());
                return "";
            }
            proyectoValidoEJB = false;
            if (proyectoEJB.getPathProyecto().equals("")) {
                JSFUtil.addWarningMessage("Ingrese el path  del proyecto EJB");

                proyectoEJB.setPathProyecto(System.getProperty("user.home") + proyectoEJB.getSeparator() + "NetBeansProjects" + proyectoEJB.getSeparator());

                return "";
            }
            if (proyectoEJB.getPathProyecto().equals("")) {
                JSFUtil.addWarningMessage("Ingrese el path  del proyecto");

                proyectoEJB.setPathProyecto(System.getProperty("user.home") + proyectoEJB.getSeparator() + "NetBeansProjects" + proyectoEJB.getSeparator());

                return "";
            }
            String ultimo = proyectoEJB.getPathProyecto().substring(proyectoEJB.getPathProyecto().length(), proyectoEJB.getPathProyecto().length());
            if (ultimo.equals("/")) {
                proyectoEJB.setPathProyecto(proyectoEJB.getPathProyecto().substring(0, proyectoEJB.getPathProyecto().length() - 1));
            }

            proyectoEJB.setProyecto(Utilidades.getNombreProyectoFromPath(proyectoEJB.getPathProyecto()));
            if (proyectoEJB.getPaquete().equals("")) {
                JSFUtil.addWarningMessage("Ingrese el paquete. Ejemplo com.avbravo.mipaquete");
                proyectoEJB.setPaquete("com." + System.getProperty("user.name") + "." + proyectoEJB.getProyecto());
                return "";
            }
            mySesion.setUsername(System.getProperty("user.name"));
            proyectoEJB.setPaquetePath(Utilidades.convertirPaqueteToPath(proyectoEJB.getPaquete()));
            proyectoEJB.setPath(proyectoEJB.getPathProyecto() + proyectoEJB.getSeparator() + "src" + proyectoEJB.getSeparator() + "main" + proyectoEJB.getSeparator() + "java" + proyectoEJB.getSeparator() + proyectoEJB.getPaquetePath() + proyectoEJB.getSeparator());

            proyectoEJB.setPathMainJava(proyectoEJB.getPathProyecto() + proyectoEJB.getSeparator() + "src" + proyectoEJB.getSeparator() + "main" + proyectoEJB.getSeparator() + "java");

            /*
            proyectoEJB webapps
             */
            proyectoEJB.setPathWebInf(proyectoEJB.getPathProyecto() + proyectoEJB.getSeparator() + "src" + proyectoEJB.getSeparator() + "main" + proyectoEJB.getSeparator() + "webapp" + proyectoEJB.getSeparator() + "WEB-INF" + proyectoEJB.getSeparator());
            String path = proyectoEJB.getPath();
            proyectoEJB.setPath(path);
            /*
            asigna los path
             */
            proyectoEJB.setPathEntity(proyectoEJB.getPath() + "entity" + proyectoEJB.getSeparator());
            proyectoEJB.setPathEJB(proyectoEJB.getPath() + "ejb" + proyectoEJB.getSeparator());
            proyectoEJB.setPathConverter(proyectoEJB.getPath() + "converter" + proyectoEJB.getSeparator());
            proyectoEJB.setPathProvider(proyectoEJB.getPath() + "provider" + proyectoEJB.getSeparator());
            proyectoEJB.setPathDatamodel(proyectoEJB.getPath() + "datamodel" + proyectoEJB.getSeparator());

            proyectoEJB.setPathServices(proyectoEJB.getPath() + "services" + proyectoEJB.getSeparator());
            proyectoEJB.setPathPomXML(proyectoEJB.getPathProyecto() + proyectoEJB.getSeparator());
            /*
            
             */
            mySesion.getArchivosList().removeAll(mySesion.getArchivosList());
            mySesion.getEntidadList().removeAll(mySesion.getEntidadList());
            mySesion.getEjbList().removeAll(mySesion.getEjbList());
            mySesion.getControllerList().removeAll(mySesion.getControllerList());
            mySesion.getEntidadMenuList().removeAll(mySesion.getEntidadMenuList());
            if (!readPackageEntity()) {
                JSFUtil.warningDialog("Mensaje", "No hay Entitys en el paquete " + proyectoEJB.getPaquete() + ".entity");
                return "";
            }

            processEntity();
            cargarTree();
            if (!mySesion.getEntidadList().isEmpty()) {

                leerConfigurationFile.readFile("Configuration.txt", proyectoEJB.getPathProvider() + "Configuration.txt");
                mostrarDatosDelArchivoConfiguracion();

            }

            mySesion.setPagina1(true);
            mySesion.setPagina2(true);
            mySesion.setPagina3(true);
            mySesion.setPagina4(true);
            mySesion.setPagina5(true);
            mySesion.setPagina6(true);
            proyectoValidoEJB = true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("search()" + e.getLocalizedMessage());
        }
        return "";
    }

    public String conectarJEE() {
        try {
            mySesion.setPagina1(false);
            mySesion.setPagina2(false);
            mySesion.setPagina3(false);
            mySesion.setPagina4(false);
            mySesion.setPagina5(false);
            mySesion.setPagina6(false);
            if (Utilidades.getLastLetter(proyectoJEE.getPathProyecto()).equals(proyectoJEE.getSeparator())) {
                JSFUtil.addWarningMessage("Ingrese la ruta del proyecto. No debe terminar en " + proyectoJEE.getSeparator());
                return "";
            }
            proyectoValidoJEE = false;
            if (proyectoJEE.getPathProyecto().equals("")) {
                JSFUtil.addWarningMessage("Ingrese el path  del proyecto");

                proyectoJEE.setPathProyecto(System.getProperty("user.home") + proyectoJEE.getSeparator() + "NetBeansProjects" + proyectoJEE.getSeparator());

                return "";
            }
            if (proyectoJEE.getPathProyecto().equals("")) {
                JSFUtil.addWarningMessage("Ingrese el path  del proyecto");

                proyectoJEE.setPathProyecto(System.getProperty("user.home") + proyectoJEE.getSeparator() + "NetBeansProjects" + proyectoJEE.getSeparator());

                return "";
            }
            String ultimo = proyectoJEE.getPathProyecto().substring(proyectoJEE.getPathProyecto().length(), proyectoJEE.getPathProyecto().length());
            if (ultimo.equals("/")) {
                proyectoJEE.setPathProyecto(proyectoJEE.getPathProyecto().substring(0, proyectoJEE.getPathProyecto().length() - 1));
            }

            proyectoJEE.setProyecto(Utilidades.getNombreProyectoFromPath(proyectoJEE.getPathProyecto()));
            if (proyectoJEE.getPaquete().equals("")) {
                JSFUtil.addWarningMessage("Ingrese el paquete. Ejemplo com.avbravo.mipaquete");
                proyectoJEE.setPaquete("com." + System.getProperty("user.name") + "." + proyectoJEE.getProyecto());
                return "";
            }
            mySesion.setUsername(System.getProperty("user.name"));
            proyectoJEE.setPaquetePath(Utilidades.convertirPaqueteToPath(proyectoJEE.getPaquete()));
            proyectoJEE.setPath(proyectoJEE.getPathProyecto() + proyectoJEE.getSeparator() + "src" + proyectoJEE.getSeparator() + "main" + proyectoJEE.getSeparator() + "java" + proyectoJEE.getSeparator() + proyectoJEE.getPaquetePath() + proyectoJEE.getSeparator());

            proyectoJEE.setPathMainJava(proyectoJEE.getPathProyecto() + proyectoJEE.getSeparator() + "src" + proyectoJEE.getSeparator() + "main" + proyectoJEE.getSeparator() + "java");
            proyectoJEE.setPathMainResources(proyectoJEE.getPathProyecto() + proyectoJEE.getSeparator() + "src" + proyectoJEE.getSeparator() + "main" + proyectoJEE.getSeparator() + "resources");
            proyectoJEE.setPathMainWebapp(proyectoJEE.getPathProyecto() + proyectoJEE.getSeparator() + "src" + proyectoJEE.getSeparator() + "main" + proyectoJEE.getSeparator() + "webapp");
            /*
            proyectoJEE webapps
             */
            proyectoJEE.setPathMainWebappPages(proyectoJEE.getPathMainWebapp() + proyectoJEE.getSeparator() + "pages" + proyectoJEE.getSeparator());
            proyectoJEE.setPathMainWebappResources(proyectoJEE.getPathMainWebapp() + proyectoJEE.getSeparator() + "resources");
            proyectoJEE.setPathMainWebappResourcesCss(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "css" + proyectoJEE.getSeparator());

            proyectoJEE.setPathMainWebappResourcesAvbravo(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "avbravo" + proyectoJEE.getSeparator());
            proyectoJEE.setPathMainWebappResourcesBootstrap(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "bootstrap" + proyectoJEE.getSeparator());
            proyectoJEE.setPathMainWebappResourcesBootstrapcdn(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "bootstrapcdn" + proyectoJEE.getSeparator());
            proyectoJEE.setPathMainWebappResourcesDist(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "dist" + proyectoJEE.getSeparator());
            proyectoJEE.setPathMainWebappResourcesImg(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "img" + proyectoJEE.getSeparator());
            proyectoJEE.setPathMainWebappResourcesIonicframework(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "ionicframework" + proyectoJEE.getSeparator());

            proyectoJEE.setPathMainWebappResourcesReportes(proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "reportes" + proyectoJEE.getSeparator());

            proyectoJEE.setPathWebInf(proyectoJEE.getPathProyecto() + proyectoJEE.getSeparator() + "src" + proyectoJEE.getSeparator() + "main" + proyectoJEE.getSeparator() + "webapp" + proyectoJEE.getSeparator() + "WEB-INF" + proyectoJEE.getSeparator());
            String path = proyectoJEE.getPath();
            proyectoJEE.setPath(path);
            /*
            asigna los path
             */
            proyectoJEE.setPathEntity(proyectoJEE.getPath() + "entity" + proyectoJEE.getSeparator());
            proyectoJEE.setPathController(proyectoJEE.getPath() + "controller" + proyectoJEE.getSeparator());
            proyectoJEE.setPathUtil(proyectoJEE.getPath() + "util" + proyectoJEE.getSeparator());

            proyectoJEE.setPathReportes(proyectoJEE.getPath() + "reportes" + proyectoJEE.getSeparator());

            proyectoJEE.setPathRoles(proyectoJEE.getPath() + "roles" + proyectoJEE.getSeparator());

            proyectoJEE.setPathProperties(proyectoJEE.getPathMainResources() + proyectoJEE.getSeparator() + proyectoJEE.getPaquetePath() + proyectoJEE.getSeparator() + "properties" + proyectoJEE.getSeparator());
            proyectoJEE.setPathInterfaces(proyectoJEE.getPath() + "interfaces" + proyectoJEE.getSeparator());
            proyectoJEE.setPathPomXML(proyectoJEE.getPathProyecto() + proyectoJEE.getSeparator());
            /*
            
             */
            mySesion.getArchivosList().removeAll(mySesion.getArchivosList());
            mySesion.getEntidadList().removeAll(mySesion.getEntidadList());
            mySesion.getEjbList().removeAll(mySesion.getEjbList());
            mySesion.getControllerList().removeAll(mySesion.getControllerList());
            mySesion.getEntidadMenuList().removeAll(mySesion.getEntidadMenuList());

            if (!readPackageEntity()) {
                JSFUtil.warningDialog("Mensaje", "No hay Entitys en el paquete " + proyectoJEE.getPaquete() + ".entity");

                return "";
            }

            if (!JSFUtil.isWeb(proyectoJEE.getPathWebInf() + "web.xml")) {
                return "";
            }
            processEntity();
            cargarTree();
            if (!mySesion.getEntidadList().isEmpty()) {

                leerConfigurationFile.readFile("Configuration.txt", proyectoJEE.getPathProperties() + "Configuration.txt");
                mostrarDatosDelArchivoConfiguracion();

            }

            mySesion.setPagina1(true);
            mySesion.setPagina2(true);
            mySesion.setPagina3(true);
            mySesion.setPagina4(true);
            mySesion.setPagina5(true);
            mySesion.setPagina6(true);
            proyectoValidoJEE = true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("search()" + e.getLocalizedMessage());
        }
        return "";
    }

    public String create() {
        generado = false;
        if (!proyectoValidoEJB) {
            JSFUtil.addErrorMessage("Este no es un proyecto EJB que cumple los requerimientos");
            return "";
        }
        if (!proyectoValidoJEE) {
            JSFUtil.addErrorMessage("Este no es un proyecto JAVA EE que cumple los requerimientos");
            return "";
        }

        construct(proyectoJEE.getPath());

        clearEJB();
        clearJEE();

        generado = true;
        return "";
    }

    /**
     * genero el nombre del paquete y el path del paquete
     *
     * @return
     */
    private void construct(String path) {
        try {
            //paginas 

            mySesion.getErrorList().removeAll(mySesion.getErrorList());
            mySesion.getMensajesList().removeAll(mySesion.getMensajesList());
//crea los directorios
            if (!directorios.setupDirectorios()) {
                return;
            }

            if (mySesion.getRoles().equals("") || mySesion.getRoles().indexOf(",") == -1) {
                JSFUtil.addWarningMessage("Por favor verifique los roles");
                return;
            }
            mySesion.setRolesList(Utilidades.descomponerRoles(mySesion.getRoles()));
            if (mySesion.getRolesList().isEmpty() || mySesion.getRolesList().size() == 0) {
                JSFUtil.addWarningMessage("No se han especificado los roles");
                return;
            }
            mySesion.setMenubarList(Utilidades.descomponerMenu(mySesion.getTitulosMenuBar()));
            if (mySesion.getMenubarList().isEmpty() || mySesion.getMenubarList().size() == 0) {
                JSFUtil.addWarningMessage("No se han especificado los titulos de los menus");
                return;
            }
            mySesion.setNumeroMenuBar(mySesion.getMenubarList().size());

//            if (mySesion.getEntidadUser().getTabla().equals(mySesion.getEntidadRoles().getTabla())) {
//                JSFUtil.addWarningMessage("El entity user no debe ser el mismo que el entity roles");
//            }
            if (mySesion.getAtributosUsername().equals(mySesion.getAtributosPassword())) {
                JSFUtil.addWarningMessage("El nombre del atributo para el username no debe ser el mismo que el del password");
                return;
            }
            if (!mySesion.getTypeUserGroupList()) {

                String idroles = "";

                for (Atributos a : mySesion.getEntidadRoles().getAtributosList()) {
                    if (a.getEsPrimaryKey()) {
                        idroles = a.getNombre();
                    }
                }
                Boolean tienerolrelacionado = false;
                for (Atributos a : mySesion.getEntidadUser().getAtributosList()) {
                    if (idroles.equals(a.getNombre())) {
                        tienerolrelacionado = true;
                    }
                }
//                if (!tienerolrelacionado) {
//                    JSFUtil.addWarningMessage("La entidad " + mySesion.getEntidadUser().getTabla() + " no tiene atributo relacionado con " + mySesion.getEntidadRoles().getTabla());
//                    //        return;
//                }
            } else {
                // con multiples roles
                if (mySesion.getEntidadGruposUsuariosMultiples().getTabla().equals(mySesion.getEntidadUser().getTabla())) {
                    JSFUtil.addWarningMessage("El entity de grupos usuarios no debe  ser el mismo que el entity user");
                    return;
                }

            }

            if (!mySesion.getEntidadList().isEmpty()) {
//                    cargarTree();
// crea los directorios de los reportes
                mySesion.getEntidadList().forEach((entidad) -> {
                    directorios.makeReportesDirectorios(entidad.getTabla());
                });

                jasperGenerador.generar();
                jasperDetailsGenerador.generar();
                
                if (tipoGeneracion.equals("codigopaginas") || tipoGeneracion.equals("codigo")) {

                    /*
                    generales
                     */
                    informationGenerador.generar();

                    resourcesFilesGenerador.generar();
                    idiomasGenerador.generar();
                    loginControllerGenerador.generar();
                    /*
                    menu
                     */

                    menuBeansGenerador.generar();
                    /*
                    ejb
                     */
                    facadeGenerador.generar();
                    /*
                   datamodel
                     */
                    datamodelGenerador.generar();
                    /*
                    provider
                     */
                    switch (mySesion.getDatabase().toLowerCase()) {
                        case "mongodb":
                            mongoClientProviderGenerador.generar();
                            break;
                        case "couchbase":
                            couchbaseClientProviderGenerador.generar();
                            break;
                        case "orientdb":
                            JSFUtil.addWarningMessage("Aun no soportado Database: orientdb");
                            break;

                    }

                    /*
                    converter
                     */
                    converterGenerador.generar();
                    /*
                roles
                     */
                    rolesGenerador.generar();
                    validadorRolesGenerador.generar();

                    /*
                archivo de configuracion
                     */
                    configurationFileGenerador.generar();
                    /*
                interfaces
                     */
                    iControllerGenerador.generar();


                    /*
                pom.xml
                     */
                    pomXMLEJBGenerador.generar();
                    pomXMLGenerador.generar();
                    /*
                services
                     */

                    servicesGenerador.generar();
                    /*
                search
                     */

                    searchGenerador.generar();
                    /*
                controller
                     */
                    controllerGenerador.generar();
                    /*
                properties
                     */

                    messagesPropertiesGenerador.generar();

                    /*
                Editar archivo de persistencia
                     */
//                    if (mySesion.getAddCreateTablePersitenceXML()) {
//                        persistenceXMLGenerador.generar();
//                    }
                }

                //verifica si debe generar las paginas
                if (tipoGeneracion.equals("codigopaginas") || tipoGeneracion.equals("paginas")) {
                    //Web
                    switch (estilo) {
                        case "adminlte":
                            webXMLGenerador.generar();
                            /*
stopWeb/-Inf
                             */
                            facesConfigGenerador.generar();

                            /*
                css
                             */
                            defaultCssGenerador.generar();
                            cssLayoutCssGenerador.generar();
                            /*
                   WEB
                             */
                            templatexhtmlGenerador.generar();
                            footerxhtmlGenerador.generar();
                            topxhtmlGenerador.generar();
                            rigthxhtmlGenerador.generar();
                            leftxhtmlGenerador.generar();

//                            menusxhtmlGenerador.generar();
//                            contextxhtmlGenerador.generar();
                            accesodenegadoxhtmlGenerador.generar();
                            activoxhtmlGenerador.generar();
                            indexxhtmlGenerador.generar();
                            loginxhtmlGenerador.generar();
                            /*
                generar el directorio para cada entity
                             */
                            for (Entidad entidad : mySesion.getEntidadList()) {
                                String directorioentity = proyectoJEE.getPathMainWebappPages() + Utilidades.letterToLower(entidad.getTabla()) + proyectoJEE.getSeparator();
                                if (!Utilidades.searchDirectorie(directorioentity)) {
                                    Utilidades.mkdir(directorioentity);
                                }
                            }

                            /*
                generar las paginas
                             */
                            pagexhtmlGenerador.generar();
                            listxhtmlGenerador.generar();

                            break;
                        case "primefacespremium":
                            JSFUtil.addWarningMessage("Aun no soportado Template: primefacespremium");
                            break;

                        case "materialprime":
                            JSFUtil.addWarningMessage("Aun no soportado. Template: materialprime");
                            break;
                    }

                }

                /*
                
                 */
                informationGenerador.stop();

                if (!mySesion.getAllTablesWithPrimaryKey()) {
                    JSFUtil.addWarningMessage("No todas las tablas tienen llave primaria");
                }
                JSFUtil.addSuccessMessage("Proceso terminado. Revise archivo Information.txt");
//                JSFUtil.infoDialog("Terminado", "Proceso terminado. Revise archivo Information.txt");
            } else {
                JSFUtil.addWarningMessage("No hay entitys en el directorio especificado");
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("setup()" + e.getLocalizedMessage());
        }

        //        persistenceContect= "@PersistenceContext(unitName = /" + persistenceContect+"")";
    }

    /*
    validar repositorio
     */
    public String validarRepositorio() {
        try {

            if (!mySesion.getTipoRepositorio().equals("local")) {

                proyectoEJB.setPathProyecto("https://");
            }

//        JSFUtil.addSuccessMessage("tipo "+mySesion.getTipoRepositorio() + " activo: "+ mySesion.getEsRepositorioGitMercurial());
        } catch (Exception e) {
            JSFUtil.addErrorMessage("validarRepositorio() " + e.getLocalizedMessage());
        }

        return "";
    }

    /**
     * carga todos los entity y lee sus propiedades del directorio seleccionado
     *
     * @param pathEntity
     * @return
     */
    private Boolean readPackageEntity() {

        mySesion.getArchivosList().removeAll(mySesion.getArchivosList());

        Path gitReposFolderPath = Paths.get(proyectoEJB.getPathEntity());
        if (!tieneEntitys(gitReposFolderPath)) {
            JSFUtil.addWarningMessage("Indique el paquete principal. Para buscar el directo entity");
            return false;
        }

        //gitReposFolderPath.toFile().getName();
        try (Stream<Path> foldersWithinGitReposStream = Files.list(gitReposFolderPath)) {
            List<String> elements = new ArrayList<>();
            foldersWithinGitReposStream.forEach(p -> elements.add(p.toFile().getName()));
            for (String s : elements) {
                String extension = s.substring(s.indexOf("."), s.length());
                String nombre = s.replace(extension, "").trim();
                Archivos archivos = new Archivos();
                archivos.setArchivo(nombre);
                mySesion.getArchivosList().add(archivos);
            }
            mySesion.getArchivosList().sort((p1, p2) -> p1.getArchivo().compareTo(p2.getArchivo()));
            return !mySesion.getArchivosList().isEmpty();
        } catch (Exception ioe) {
            JSFUtil.addErrorMessage("readPackageEntity()" + ioe.getLocalizedMessage());
        }
        return false;
    }

    public Boolean tieneEntitys(Path gitReposFolderPath) {
        try {
            Files.list(gitReposFolderPath);
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * carga los entitys con sus atributos
     *
     * @return
     */
    private Boolean processEntity() {
        try {
            mySesion.getArchivosList().stream().forEach((a) -> {
                entidadGenerador.readEntity(a.getArchivo(), proyectoEJB.getPathEntity() + a.getArchivo() + ".java");
            });
            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("processEntity()" + e.getLocalizedMessage());

        }

        return false;
    }

    public String cargarTree() {
        try {
            root = new DefaultTreeNode("Entity", null);

            TreeNode node0 = new DefaultTreeNode("Entity", root);

            primaryKey = "";
            mySesion.getEntidadList().stream().forEach((e) -> {
                TreeNode node00 = new DefaultTreeNode(e.getTabla(), node0);

                e.getAtributosList().stream().forEach((a) -> {
                    TreeNode node01 = new DefaultTreeNode(a.getNombre() + (a.getEsPrimaryKey() ? "<PrimaryKey>" : ""), node00);

                    node01.getChildren().add(new DefaultTreeNode(a.getTipo()));

                });

            });
        } catch (Exception e) {
            JSFUtil.addErrorMessage("cargarTree() " + e.getLocalizedMessage());
        }
        return "";
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void displaySelectedSingle() {
        if (selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void deleteNode() {
        selectedNode.getChildren().clear();
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);

        selectedNode = null;
    }

    public String crearProyecto() {
        try {
//             Terminal.runCommand(primaryKey);
            Terminal.runCommand(primaryKey);
            JSFUtil.addSuccessMessage("ejecutado");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearProyecto() " + e.getLocalizedMessage());
        }
        return "";
    }

    /*
    
     */
    public String entidadSeleccionada() {
//            nivel2.setIdnivel2(nivel2.getIdnivel1().getIdnivel1());
        return null;

    }

    public String entidadMultiplesRoles() {

        return "";
    }

    private void mostrarDatosDelArchivoConfiguracion() {
        try {

            //procesa las entidades de user, roles y grupos usuarios que vienen 
            //del archivo de configuracion para cargarle todos los elemetos del entity correspondiente
            if (mySesion.getEntidadUser().getTabla() == null || mySesion.getEntidadUser().getTabla().equals("")) {

            } else {
                for (Entidad entidad : mySesion.getEntidadList()) {

                    if (entidad.getTabla().equals(mySesion.getEntidadUser().getTabla())) {
                        mySesion.setEntidadUser(entidad);

                        entidadSearch.onChangeEntidadUser();
                    }
                }
            }
            if (mySesion.getEntidadRoles().getTabla() == null || mySesion.getEntidadRoles().getTabla().equals("")) {
            } else {
                for (Entidad entidad : mySesion.getEntidadList()) {
                    if (entidad.getTabla().equals(mySesion.getEntidadRoles().getTabla())) {
                        mySesion.setEntidadRoles(entidad);
                    }
                }
            }
            if (mySesion.getEntidadGruposUsuariosMultiples().getTabla() == null || mySesion.getEntidadGruposUsuariosMultiples().getTabla().equals("")) {
            } else {
                for (Entidad entidad : mySesion.getEntidadList()) {
                    if (entidad.getTabla().equals(mySesion.getEntidadGruposUsuariosMultiples().getTabla())) {
                        mySesion.setEntidadGruposUsuariosMultiples(entidad);
                        entidadSearch.onChangeEntidadGrupousuarios();
                    }
                }
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("mostrarDatosDelArchivoConfiguracion()" + e.getLocalizedMessage());
        }
    }

    /**
     *
     * @return
     */
    public String onChangeTipoGrupo() {
        try {

            mySesion.setTypeUserGroupWithOutRol(false);
            mySesion.setTypeUserGroupField(false);
            mySesion.setTypeUserGroupEntity(false);
            mySesion.setTypeUserGroupList(false);
            switch (mySesion.getTypeUserGroup()) {
                case "Without Roles":
                    mySesion.setTypeUserGroupWithOutRol(true);
                    break;
                case "Field":
                    mySesion.setTypeUserGroupField(true);
                    break;
                case "Entity":
                    mySesion.setTypeUserGroupEntity(true);
                    break;
                case "List":
                    mySesion.setTypeUserGroupList(true);
                    break;
                default:
                    JSFUtil.addWarningMessage("No se ha indicado un tipo correcto de grupo " + mySesion.getTypeUserGroup());

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("changeTipoGrupo()" + e.getLocalizedMessage());
        }
        return "";
    }

    public String irPagina1() {
        try {

            if (!mySesion.getPagina1()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }

            return "index.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina1() " + e.getLocalizedMessage());
        }
        return "";

    }

    public String irPagina2() {
        try {
            if (!JSFUtil.isWeb(proyectoJEE.getPathWebInf() + "web.xml")) {
                JSFUtil.addWarningMessage("No es un proyecto web valido" + proyectoJEE.getPathWebInf());
                System.out.println("path " + proyectoJEE.getPathWebInf() + "web.xml");
                return "";
            }
            if (!mySesion.getPagina2()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }
            generado = false;
            return "pagina2.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina2() " + e.getLocalizedMessage());
        }
        return "";

    }

    public String irPagina3() {
        try {

            if (!mySesion.getPagina3()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }
            return "pagina3.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina3() " + e.getLocalizedMessage());
        }
        return "";

    }

    public String irPagina4() {
        try {

            mySesion.setMenubarList(Utilidades.descomponerMenu(mySesion.getTitulosMenuBar()));

            if (mySesion.getMenubarList().isEmpty() || mySesion.getMenubarList().size() == 0) {
                JSFUtil.addWarningMessage("No se han especificado los titulos de los menus");
                return "";
            }
            if (!mySesion.getPagina4()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }

            mySesion.getEntidadMenuList().removeAll(mySesion.getEntidadMenuList());
            for (Entidad entidad : mySesion.getEntidadList()) {
                EntidadMenu em = new EntidadMenu();
                em.setEntidad(entidad.getTabla());
                mySesion.getMasterDetailsList().add(entidad.getTabla());
                //
                for (String s : mySesion.getMenubarList()) {
                    em.setMenu(s);
                }
                mySesion.getEntidadMenuList().add(em);

            }
            mySesion.iniciarTree();
            return "pagina4.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina4() " + e.getLocalizedMessage());
        }
        return "";

    }

    public String irPagina5() {
        try {

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

    public String irPagina6() {
        try {

            if (!mySesion.getPagina6()) {
                JSFUtil.addWarningMessage("No se puede avanzar a la siguiente pagina");
                return "";
            }
            return "pagina6.xhtml";
        } catch (Exception e) {
            JSFUtil.addErrorMessage("irPagina6() " + e.getLocalizedMessage());
        }
        return "";

    }
}
