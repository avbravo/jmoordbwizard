/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.controller;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoEJB;
import com.avbravo.jmoordbwizard.ProyectoJEE;
import com.avbravo.jmoordbwizard.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class SessionControllerGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(SessionControllerGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    @Inject
    ProyectoEJB proyectoEJB;
    FileWriter fw;

    // <editor-fold defaultstate="collapsed" desc="generar"> 

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("SessionController", proyectoJEE.getPathController() + "SessionController.java");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="procesar"> 
    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            } else {

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }    // </editor-fold>
    

    // <editor-fold defaultstate="collapsed" desc="creaarFile"> 
    /**
     * deleteAll
     *
     * @param entidad
     * @param archivo
     * @return
     */
    private Boolean crearFile(String path, String archivo) {
        try {

            String ruta = path;
            File file = new File(ruta);
            BufferedWriter bw;
            if (file.exists()) {
                // El fichero ya existe
            } else {
                // El fichero no existe y hay que crearlo
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.close();

                File file2 = new File(ruta);
                //                try (FileWriter fw = new FileWriter(file)) {
                fw = new FileWriter(file);
                fw.write("/*" + "\r\n");
                String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
                fw.write("* To change this license header, choose License Headers in Project Properties." + "\r\n");
                fw.write("* To change this template file, choose Tools | Templates" + "\r\n");
                fw.write(" * and open the template in the editor." + "\r\n");
                fw.write("*/" + "\r\n");
                fw.write("package " + proyectoJEE.getPaquete() + ".controller;" + "\r\n");
                fw.write("" + "\r\n");
                importaciones();
                fw.write("" + "\r\n");
                fw.write("/**" + "\r\n");
                fw.write(" *" + "\r\n");
                fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                fw.write(" */" + "\r\n");
                fw.write("@Named" + "\r\n");
                fw.write("@ViewScoped" + "\r\n");
                fw.write("public class SessionController  implements Serializable, SecurityInterface  {" + "\r\n");
                field();
                setget();
                fw.write("" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"init\">" + "\r\n");
                fw.write("    @PostConstruct" + "\r\n");
                fw.write("    public void init() {" + "\r\n");
                fw.write("        showAllSessions();" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"destroy\">" + "\r\n");
                fw.write("    @PreDestroy" + "\r\n");
                fw.write("    public void destroy() {" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"constructor\">" + "\r\n");
                fw.write("    public SessionController() {" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");

                showAllSessions();
                killAllSessions();
                cancelSelectedSession();
                dateOfExpiration();
                timeOfCreation();
                timeOfConnection();
                lastConnection();

                fw.write("" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("" + "\r\n");
                fw.write("" + "\r\n");

                fw.write("}" + "\r\n");
                fw.close();

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

    // <editor-fold defaultstate="collapsed" desc="field"> 
    private void field() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"atributos\"> " + "\r\n");
            fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");
            fw.write("    private Integer segundosRefresh = 3;" + "\r\n");
            fw.write("   @Inject" + "\r\n");
            fw.write("    ResourcesFiles rf;" + "\r\n");
            fw.write("    @Inject" + "\r\n");
            fw.write("    LoginController loginController;" + "\r\n");
            fw.write("    private BrowserSession browserSessionSelecction = new BrowserSession();" + "\r\n");
            fw.write("    List<BrowserSession> browserSessionsList = new ArrayList<>();" + "\r\n");
            fw.write("    List<BrowserSession> browserSessionsFilterList = new ArrayList<>();" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("field() " + e.getLocalizedMessage());
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="setget"> 

    private void setget() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"getter/setter\">" + "\r\n");

            fw.write("    public Integer getSegundosRefresh() {" + "\r\n");
            fw.write("        return segundosRefresh;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public void setSegundosRefresh(Integer segundosRefresh) {" + "\r\n");
            fw.write("        this.segundosRefresh = segundosRefresh;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public BrowserSession getBrowserSessionSelecction() {" + "\r\n");
            fw.write("        return browserSessionSelecction;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public void setBrowserSessionSelecction(BrowserSession browserSessionSelecction) {" + "\r\n");
            fw.write("        this.browserSessionSelecction = browserSessionSelecction;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public List<BrowserSession> getBrowserSessionsList() {" + "\r\n");
            fw.write("        return browserSessionsList;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public void setBrowserSessionsList(List<BrowserSession> browserSessionsList) {" + "\r\n");
            fw.write("        this.browserSessionsList = browserSessionsList;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public List<BrowserSession> getBrowserSessionsFilterList() {" + "\r\n");
            fw.write("        return browserSessionsFilterList;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public void setBrowserSessionsFilterList(List<BrowserSession> browserSessionsFilterList) {" + "\r\n");
            fw.write("        this.browserSessionsFilterList = browserSessionsFilterList;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setget() " + e.getLocalizedMessage());
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="importaciones"> 
    private void importaciones() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"imports\">" + "\r\n");
//            fw.write("import " + proyectoEJB.getPaquete() + ".entity.*;" + "\r\n");
//            fw.write("import " + proyectoEJB.getPaquete() + ".ejb.*;" + "\r\n");
            fw.write("import " + proyectoJEE.getPaquete() + ".util.*;" + "\r\n");
//            fw.write("import " + proyectoJEE.getPaquete() + ".roles.*;" + "\r\n");

            fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");
            fw.write("import com.avbravo.avbravosecurity.SecurityInterface;" + "\r\n");
            fw.write("import javax.inject.Inject;" + "\r\n");
            fw.write("import com.avbravo.avbravosecurity.BrowserSession;" + "\r\n");
            fw.write("import javax.faces.view.ViewScoped;" + "\r\n");
//            fw.write("import org.primefaces.context.RequestContext;" + "\r\n");
//            fw.write("import com.avbravo.avbravoutils.email.ManagerEmail;" + "\r\n");
            fw.write("import java.util.logging.Logger;" + "\r\n");
            fw.write("import javax.inject.Named;" + "\r\n");
            fw.write("import java.io.Serializable;" + "\r\n");
            fw.write("import java.util.ArrayList;" + "\r\n");
            fw.write("import java.util.Date;" + "\r\n");
            fw.write("import java.util.List;" + "\r\n");
//            fw.write("import java.util.Optional;" + "\r\n");
            fw.write("import javax.annotation.PostConstruct;" + "\r\n");
            fw.write("import javax.annotation.PreDestroy;" + "\r\n");
//            fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
//            fw.write("import javax.faces.context.ExternalContext;" + "\r\n");
//            fw.write("import javax.faces.context.FacesContext;" + "\r\n");
            fw.write("import javax.servlet.http.HttpSession;" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("importaciones() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="showAllSessions"> 
    private void showAllSessions() {
        try {
            //logout()
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"showAllSessions\">" + "\r\n");
            fw.write("   public String showAllSessions() {" + "\r\n");
            fw.write("        try {" + "\r\n");
            fw.write("            browserSessionsList = allBrowserSessionList();" + "\r\n");
            fw.write("            browserSessionsFilterList = browserSessionsList;" + "\r\n");
            fw.write("            if (browserSessionsList.isEmpty()) {" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"warning.nosessionsarerecorded\"));" + "\r\n");
            fw.write("            }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        } catch (Exception e) {" + "\r\n");
            fw.write("            JsfUtil.errorMessage(\"showAllSessions() \" + e.getLocalizedMessage());" + "\r\n");
            fw.write("        }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        return \"\";" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("showAllSessions() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="killAllSessions"> 

    private void killAllSessions() {
        try {
            //logout()
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"killAllSessions\">" + "\r\n");
            fw.write("  public String killAllSessions() {" + "\r\n");
            fw.write("        try {" + "\r\n");
            fw.write("            if (cancelAllSesion()) {" + "\r\n");
            fw.write("                showAllSessions();" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"information.allsessiondelete\"));" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("            } else {" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"warning.notdeleteallsession\"));" + "\r\n");
            fw.write("            }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        } catch (Exception e) {" + "\r\n");
            fw.write("            JsfUtil.errorMessage(\"KillAllSessions() \" + e.getLocalizedMessage());" + "\r\n");
            fw.write("        }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        return \"\";" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("killAllSessions() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="cancelSelectedSession"> 

    private void cancelSelectedSession() {
        try {

            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"cancelSelectedSession\">" + "\r\n");
            fw.write(" public String cancelSelectedSession(BrowserSession browserSesssion) {" + "\r\n");
            fw.write("        try {" + "\r\n");
            fw.write("            if (loginController.getUsername().equals(browserSesssion.getUsername())) {" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"session.autodestruccionnopermitida\"));" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("                return \"\";" + "\r\n");
            fw.write("            }" + "\r\n");
            fw.write("            if (inactiveSession(browserSesssion)) {" + "\r\n");
            fw.write("                JsfUtil.successMessage(rf.getAppMessage(\"session.cancelada\"));" + "\r\n");
            fw.write("                browserSessionsList = allBrowserSessionList();" + "\r\n");
            fw.write("                browserSessionsFilterList = browserSessionsList;" + "\r\n");
            fw.write("            } else {" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"session.nosecancelo\"));" + "\r\n");
            fw.write("            }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        } catch (Exception e) {" + "\r\n");
            fw.write("            JsfUtil.errorMessage(\"cancelSession() \" + e.getLocalizedMessage());" + "\r\n");
            fw.write("        }" + "\r\n");
            fw.write("        return \"\";" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("killAllSessions() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="dateofEpirtation"> 
    private void dateOfExpiration() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"dateofEpirtation\"> " + "\r\n");
            fw.write("    public Date dateOfExpiration(HttpSession session) {" + "\r\n");
            fw.write("        return getDateTiemExpiration(session);" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("// </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("dateOfExpiration() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="secondsForInactivity"> 
    private void secondsForInactivity() {
        try {
            fw.write("" + "\r\n");
            fw.write("    // <editor-fold defaultstate=\"collapsed\" desc=\"secondsForInactivity\"> " + "\r\n");
            fw.write("    public String secondsForInactivity(HttpSession session) {" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        return JsfUtil.milisegundosToTiempoString(milisegundosForInactivate(session));" + "\r\n");
            fw.write("    }// </editor-fold>" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("secondsForInactivity() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="timeOfCreation"> 
    private void timeOfCreation() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"timeOfCreation\"> " + "\r\n");
            fw.write("    public Date timeOfCreation(HttpSession session) {" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("        return new Date(session.getCreationTime());" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("timeOfCreation() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="timeOfConnection"> 
    private void timeOfConnection() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"timeOfConnection\"> " + "\r\n");
            fw.write("    public String timeOfConnection(HttpSession session) {" + "\r\n");
            fw.write("        try {" + "\r\n");
            fw.write("            return JsfUtil.milisegundosToTiempoString(miliSecondsOfConnection(session));" + "\r\n");
            fw.write("        } catch (Exception e) {" + "\r\n");
            fw.write("            JsfUtil.errorMessage(\"timeOfConecction() \" + e.getLocalizedMessage());" + "\r\n");
            fw.write("        }" + "\r\n");
            fw.write("        return \"\";" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("timeOfConnection() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="lastConnection"> 
    private void lastConnection() {
        try {
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"lastConnection\"> " + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    public Date lastConnection(HttpSession session) {" + "\r\n");
            fw.write("        //return JsfUtil.milisegundosToTiempoString(session.getLastAccessedTime());" + "\r\n");
            fw.write("        return new Date(session.getLastAccessedTime());" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("lastConnection() " + e.getLocalizedMessage());
        }
    }
// </editor-fold>

}
