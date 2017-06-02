/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.controller;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoEJB;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
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
public class LoginControllerGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LoginControllerGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    @Inject
    ProyectoEJB proyectoEJB;
    FileWriter fw;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("LoginController", proyectoJEE.getPathController() + "LoginController.java");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

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

    }

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
                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"imports\">" + "\r\n");
                fw.write("import " + proyectoEJB.getPaquete() + ".entity.*;" + "\r\n");
                fw.write("import " + proyectoEJB.getPaquete() + ".ejb.*;" + "\r\n");
                fw.write("import " + proyectoJEE.getPaquete() + ".util.*;" + "\r\n");
                fw.write("import " + proyectoJEE.getPaquete() + ".roles.*;" + "\r\n");

                fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");
                fw.write("import javax.inject.Inject;" + "\r\n");
                fw.write("import java.util.logging.Logger;" + "\r\n");
                fw.write("import javax.inject.Named;" + "\r\n");
                fw.write("import java.io.Serializable;" + "\r\n");
                fw.write("import java.util.ArrayList;" + "\r\n");
                fw.write("import java.util.List;" + "\r\n");
                fw.write("import java.util.Optional;" + "\r\n");
                fw.write("import javax.annotation.PostConstruct;" + "\r\n");
                fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                fw.write("import javax.faces.context.ExternalContext;" + "\r\n");
                fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                fw.write("import javax.servlet.http.HttpSession;" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");

                fw.write("" + "\r\n");
                fw.write("/**" + "\r\n");
                fw.write(" *" + "\r\n");
                fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                fw.write(" */" + "\r\n");
                fw.write("@Named" + "\r\n");
                fw.write("@SessionScoped" + "\r\n");
                fw.write("public class LoginController  implements Serializable {" + "\r\n");
                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"fields\">" + "\r\n");
                fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");
                fw.write("    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());" + "\r\n");
                fw.write("" + "\r\n");

                fw.write("    @Inject" + "\r\n");
                fw.write("    ResourcesFiles rf;" + "\r\n");
                fw.write("    @Inject" + "\r\n");
                fw.write("    ValidadorRoles validadorRoles;" + "\r\n");
                fw.write("    Boolean loggedIn = false;" + "\r\n");
                fw.write("    private String username;" + "\r\n");
                fw.write("    private String password;" + "\r\n");
                fw.write("    private String foto;" + "\r\n");
                fw.write("    private String id;" + "\r\n");
                fw.write("    private String key;" + "\r\n");
                fw.write("    @Inject" + "\r\n");
                fw.write("    " + mySesion.getEntidadUser().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade;" + "\r\n");
                fw.write("    " + mySesion.getEntidadUser().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + " = new " + mySesion.getEntidadUser().getTabla() + "();" + "\r\n");

                if (mySesion.getTypeUserGroupEntity()) {
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + mySesion.getEntidadRoles().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade;" + "\r\n");
                    fw.write("    " + mySesion.getEntidadRoles().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + " = new " + mySesion.getEntidadRoles().getTabla() + "();" + "\r\n");
                }

                if (mySesion.getTypeUserGroupList()) {
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + "Facade;" + "\r\n");
                    fw.write("    " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + " = new " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "();" + "\r\n");
                }
                fw.write("    // </editor-fold>" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"getter/setter\">" + "\r\n");
                fw.write("    public String getId() {" + "\r\n");
                fw.write("    return id;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public void setId(String id) {" + "\r\n");
                fw.write("        this.id = id;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public String getKey() {" + "\r\n");
                fw.write("        return key;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public void setKey(String key) {" + "\r\n");
                fw.write("        this.key = key;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public Usuario getUsuario() {" + "\r\n");
                fw.write("        return usuario;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public void setUsuario(Usuario usuario) {" + "\r\n");
                fw.write("        this.usuario = usuario;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public String getUsername() {" + "\r\n");
                fw.write("        return username;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public void setUsername(String username) {" + "\r\n");
                fw.write("        this.username = username;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public String getPassword() {" + "\r\n");
                fw.write("        return password;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public void setPassword(String password) {" + "\r\n");
                fw.write("        this.password = password;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public Boolean getLoggedIn() {" + "\r\n");
                fw.write("        return loggedIn;" + "\r\n");
                fw.write("     }" + "\r\n");

                fw.write("    public void setLoggedIn(Boolean loggedIn) {" + "\r\n");
                fw.write("        this.loggedIn = loggedIn;" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");
                fw.write("" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"init\">" + "\r\n");
                fw.write("    @PostConstruct" + "\r\n");
                fw.write("    public void init() {" + "\r\n");
                fw.write("        loggedIn = false;" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"constructor\">" + "\r\n");
                fw.write("    public LoginController() {" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");

                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"irLogin\">" + "\r\n");
                fw.write("    public String irLogin() {" + "\r\n");
                fw.write("        return \"/faces/login\";" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");
                // desde aqui el login 
                doLogin();

//logout()
                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"doLogout\">" + "\r\n");
                fw.write("    public String doLogout() {" + "\r\n");
                fw.write("        loggedIn = false;" + "\r\n");

                fw.write("        try {" + "\r\n");
                fw.write("            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);" + "\r\n");
                fw.write("            if (session != null) {" + "\r\n");
                fw.write("                session.invalidate();" + "\r\n");
                fw.write("            }" + "\r\n");
                fw.write("            String url = (\"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml?faces-redirect=true\");" + "\r\n");
                fw.write("            FacesContext fc = FacesContext.getCurrentInstance();" + "\r\n");
                fw.write("            ExternalContext ec = fc.getExternalContext();" + "\r\n");
                fw.write("            ec.redirect(url);" + "\r\n");
                fw.write("            return \"/" + proyectoJEE.getProyecto() + "/faces/login.xhtml?faces-redirect=true\";" + "\r\n");
                fw.write("        } catch (Exception e) {" + "\r\n");
                fw.write("            JsfUtil.errorMessage(e, \"logout()\");" + "\r\n");
                fw.write("        }" + "\r\n");
                fw.write("        return \"/" + proyectoJEE.getProyecto() + "/faces/login.xhtml?faces-redirect=true\";" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");
                // cambiarContrasena()
                fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"changePassword\">" + "\r\n");
                fw.write("    public String changePassword() {" + "\r\n");
                fw.write("        try {" + "\r\n");
                fw.write("            " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade.update(" + minuscula + ");" + "\r\n");
                fw.write("            JsfUtil.successMessage(rf.getAppMessage(\"info.update\"));" + "\r\n");
                fw.write("        } catch (Exception e) {" + "\r\n");
                fw.write("            JsfUtil.errorMessage(e.getLocalizedMessage());" + "\r\n");
                fw.write("        }" + "\r\n");
                fw.write("        return null;" + "\r\n");
                fw.write("    }" + "\r\n");
                fw.write("    // </editor-fold>" + "\r\n");
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

    /**
     *
     * @return
     */
    private String doLogin() {
        try {
            String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
            String primeraletra = Utilidades.getFirstLetter(mySesion.getEntidadUser().getTabla()).toLowerCase();
            fw.write("// <editor-fold defaultstate=\"collapsed\" desc=\"doLogin\">" + "\r\n");
            fw.write("    public String doLogin() {" + "\r\n");
            fw.write("        try {" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("            loggedIn = true;" + "\r\n");
            fw.write("            " + minuscula + "= new " + mySesion.getEntidadUser().getTabla() + "(); " + "\r\n");
            fw.write("            if (username == null || password == null) {" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"login.usernamenotvalid\"));" + "\r\n");
            fw.write("                return null;" + "\r\n");
            fw.write("            }" + "\r\n");
            fw.write("            " + minuscula + ".set" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "(username); " + "\r\n");
            fw.write("            Optional<" + mySesion.getEntidadUser().getTabla() + "> optional = " + minuscula + "Facade.findById(" + minuscula + ");" + "\r\n");
            fw.write("            if (!optional.isPresent()) {" + "\r\n");
            fw.write("                JsfUtil.warningMessage(rf.getAppMessage(\"login.usernamenotvalid\"));" + "\r\n");
            fw.write("                return null;" + "\r\n");
            fw.write("            }else{ " + "\r\n");
            fw.write("               " + minuscula + " = optional.get();" + "\r\n");
            fw.write("               if (!" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(password)) {" + "\r\n");
            fw.write("                   JsfUtil.successMessage(rf.getAppMessage(\"login.passwordnotvalid\"));" + "\r\n");
            fw.write("                   return \"\";" + "\r\n");
            fw.write("               }" + "\r\n");
   
            /*
                No tiene rol
             */
            if (mySesion.getTypeUserGroupWithOutRol()) {
                // No tiene grupo para validar roles
                fw.write("// No tiene roles , por favor verifique los privilegios para acceso" + "\r\n");
                fw.write("           }" + "\r\n");

            } else {

                if (mySesion.getTypeUserGroupField()) {

                    fw.write("               if (!validadorRoles.validarRoles(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosIdGrupo()) + "())) {" + "\r\n");
                    fw.write("                   JsfUtil.successMessage(rf.getAppMessage(\"login.notienerolenelsistema\") + \" \" + " + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosIdGrupo()) + "());" + "\r\n");
                    fw.write("                   loggedIn = false;" + "\r\n");
                    fw.write("                   return \"\";" + "\r\n");
                    fw.write("                }" + "\r\n");
                    fw.write("           }" + "\r\n");

                } else {
                    if (mySesion.getTypeUserGroupEntity()) {

                        String idroles = "";
                        for (Atributos a : mySesion.getEntidadRoles().getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                idroles = a.getNombre();
                            }
                        }
                        fw.write("               if (!validadorRoles.validarRoles(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) + "().get" + Utilidades.letterToUpper(idroles) + "())) {" + "\r\n");
                        fw.write("                   JsfUtil.successMessage(rf.getAppMessage(\"login.notienerolenelsistema\") + \" \" + " + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) + "().get" + Utilidades.letterToUpper(idroles) + "());" + "\r\n");
                        fw.write("                   loggedIn = false;" + "\r\n");
                        fw.write("                   return \"\";" + "\r\n");
                        fw.write("                }" + "\r\n");
                        fw.write("           }" + "\r\n");

                    } else {
// cuando tiene multiples relaciones

                        String idroles = "";
                        for (Atributos a : mySesion.getEntidadRoles().getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                idroles = a.getNombre();
                            }
                        }
                        fw.write("// Recuerde que el List<> para los roles " + "\r\n");
                        fw.write("// 1.Defina un combo en el formulario de login con los roles" + "\r\n");
                        fw.write("// 2.Haga el binding con el entity roles que definio en LoginController" + "\r\n");

                        fw.write("               if (!validadorRoles.validarRoles(" + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + ".get" + Utilidades.letterToUpper(idroles) + "())) {" + "\r\n");
                        fw.write("                   JsfUtil.successMessage(rf.getAppMessage(\"login.notienerolenelsistema\") + \" \" + " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + ".get" + Utilidades.letterToUpper(idroles) + "());" + "\r\n");
                        fw.write("                   loggedIn = false;" + "\r\n");
                        fw.write("                   return \"\";" + "\r\n");
                        fw.write("                }" + "\r\n");
                        fw.write("           }" + "\r\n");
                 

                    }//multiples relaciones

                }
            }

            fw.write("           loggedIn = true;" + "\r\n");
            fw.write("           foto = \"img/me.jpg\";" + "\r\n");
            fw.write("            JsfUtil.successMessage(rf.getAppMessage(\"login.welcome\") + \" \" + " + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosNombreMostrar()) + "());" + "\r\n");
            fw.write("            return \"/faces/index.xhtml?faces-redirect=true\";" + "\r\n");
            fw.write("        } catch (Exception e) {" + "\r\n");
            fw.write("            JsfUtil.errorMessage(e, \"doLogin()\");" + "\r\n");
            fw.write("        }" + "\r\n");
            fw.write("        return null;" + "\r\n");
            fw.write("    }" + "\r\n");
            fw.write("    // </editor-fold>" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("doLogin()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
