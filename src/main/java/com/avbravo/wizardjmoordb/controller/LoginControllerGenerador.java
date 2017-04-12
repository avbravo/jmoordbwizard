/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.controller;

import com.avbravo.wizardjmoordb.JSFUtil;
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
                generarImport(ruta);

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "private static final Logger LOG = Logger.getLogger(LoginController.class.getName());", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "private String username;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "private String password;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "private String foto;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "private String id;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "private String key;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, "Boolean loggedIn = false;", "public class LoginController implements Serializable {", false);

                Utilidades.searchAdd(ruta, mySesion.getEntidadUser().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + " = new " + mySesion.getEntidadUser().getTabla() + "();", "public class LoginController implements Serializable {", false);
                Utilidades.searchAdd(ruta, mySesion.getEntidadRoles().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + " = new " + mySesion.getEntidadRoles().getTabla() + "();", "public class LoginController implements Serializable {", false);
                if (mySesion.getTypeUserGroupList()) {
                    Utilidades.searchAdd(ruta, mySesion.getEntidadGruposUsuariosMultiples().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + " = new " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "();", "public class LoginController implements Serializable {", false);
                }

                Utilidades.searchAddTextAndInject(ruta, "ResourcesFiles rf;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, "ValidadorRoles validadorRoles;", "public class LoginController implements Serializable {", false);
//                Utilidades.searchAddTextAndInject(ruta, "ManagementThemes managementThemes;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, mySesion.getEntidadUser().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade;", "public class LoginController implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, mySesion.getEntidadRoles().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade;", "public class LoginController implements Serializable {", false);
                if (mySesion.getTypeUserGroupList()) {
                    Utilidades.searchAddTextAndInject(ruta, mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + "Facade;", "public class LoginController implements Serializable {", false);
                }

//                for (String s : mySesion.getRolesList()) {
//                    String barra = "Rol" + s + " rol" + Utilidades.convertirLetraMayuscula(s) + ";";
//                    Utilidades.searchAddTextAndInjectBeforeNotFound(ruta, barra, "public class LoginController implements Serializable {", false);
//
//                }
                /**
                 * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public void init() {", init(), "public class LoginController implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public LoginController() {", loginController(), "public class LoginController implements Serializable {", false);
                if (!mySesion.getTypeUserGroupList()) {
//                    Utilidades.addNotFoundMethod(ruta, "public String verificarLogin() {", verificarLogin(), "public class LoginController implements Serializable {", false);
                } else {

                }

                Utilidades.addNotFoundMethod(ruta, "public String logout() {", logout(), "public class LoginController implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public String irLogin() {", irLogin(), "public class LoginController implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public String cambiarContrasena() {", changePassword(), "public class LoginController implements Serializable {", false);
//                Utilidades.addNotFoundMethod(ruta, "public String verificarLogin() {", verificarLogin(), "public class LoginController implements Serializable {", false);


                /*
                 verificar si habilita  barraMenu
                 */
                for (String s : mySesion.getRolesList()) {
                    String barra = "case " + "\"" + Utilidades.letterToUpper(s) + "\": ";
                    Utilidades.searchAddRolCaseValidadorRoles(ruta, barra, " switch (rolvalidacion) {", false, s);
                }
                /*
                default
                 */
                String barra = "default:";
                Utilidades.searchAddDefaultValidadorRoles(ruta, barra, " switch (rolvalidacion) {", false);

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    private void generarImport(String ruta) {
        try {
            /**
             * agregar los imports
             */

            Utilidades.searchAdd(ruta, "import " + proyectoEJB.getPaquete() + ".entity.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoEJB.getPaquete() + ".ejb.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".roles.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".util.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".roles.*;", "package", false);

            Utilidades.searchAdd(ruta, "import com.avbravo.avbravoutils.JsfUtil;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.logging.Logger;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.ArrayList;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.Optional;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.annotation.PostConstruct;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.ExternalContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.FacesContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.servlet.http.HttpSession;", "package", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarImport() " + e.getLocalizedMessage());
        }
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
                fw.write("" + "\r\n");
                fw.write("/**" + "\r\n");
                fw.write(" *" + "\r\n");
                fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                fw.write(" */" + "\r\n");
                fw.write("@Named" + "\r\n");
                fw.write("@SessionScoped" + "\r\n");
                fw.write("public class LoginController  implements Serializable {" + "\r\n");
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
                fw.write("    @Inject" + "\r\n");
                fw.write("    " + mySesion.getEntidadRoles().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade;" + "\r\n");
                fw.write("    " + mySesion.getEntidadRoles().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + " = new " + mySesion.getEntidadRoles().getTabla() + "();" + "\r\n");
                if (mySesion.getTypeUserGroupList()) {
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + "Facade;" + "\r\n");
                    fw.write("    " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + " = new " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "();" + "\r\n");
                }

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

                fw.write("" + "\r\n");

                fw.write("    @PostConstruct" + "\r\n");
                fw.write("    public void init() {" + "\r\n");
                fw.write("        loggedIn = false;" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public LoginController() {" + "\r\n");
                fw.write("    }" + "\r\n");

                fw.write("    public String irLogin() {" + "\r\n");
                fw.write("        return \"/faces/login\";" + "\r\n");
                fw.write("    }" + "\r\n");
                // desde aqui el login 
                doLogin();

//logout()
                fw.write("    public String logout() {" + "\r\n");
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
                fw.write("            JsfUtil.addErrorMessage(e, \"logout()\");" + "\r\n");
                fw.write("        }" + "\r\n");
                fw.write("        return \"/" + proyectoJEE.getProyecto() + "/faces/login.xhtml?faces-redirect=true\";" + "\r\n");
                fw.write("    }" + "\r\n");

                // cambiarContrasena()
                fw.write("    public String changePassword() {" + "\r\n");
                fw.write("        try {" + "\r\n");
                fw.write("            " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade.update(" + minuscula + ");" + "\r\n");
                fw.write("            JsfUtil.addSuccessMessage(rf.getMensajeArb(\"info.update\"));" + "\r\n");
                fw.write("        } catch (Exception e) {" + "\r\n");
                fw.write("            JsfUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                fw.write("        }" + "\r\n");
                fw.write("        return null;" + "\r\n");
                fw.write("    }" + "\r\n");

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

    private String init() {
        try {

            String texto = "";
            texto += "    @PostConstruct" + "\r\n";
            texto += "    public void init() {" + "\r\n";
            texto += "        loggedIn = false;" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("constructor()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String irLogin() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    public String irLogin() {" + "\r\n";
            texto += "        return \"/faces/index\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("constructor()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String changePassword() {
        try {
            String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
            String texto = "";
            texto += "    public String changePassword() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade.update(" + minuscula + ");" + "\r\n";
            texto += "            JsfUtil.addSuccessMessage(rf.getMensajeArb(\"info.update\"));" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JsfUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("changePassword()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String loginController() {
        try {

            String texto = "";
            texto += "    public LoginController() {" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("loginBean()  " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     *
     * @return
     */
    private String logout() {
        try {

            String texto = "";
            texto += "   public String logout() {" + "\r\n";
            texto += "       loggedIn = false;" + "\r\n";
            texto += "       try {" + "\r\n";
            texto += "           HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);" + "\r\n";
            texto += "           if (session != null) {" + "\r\n";
            texto += "               session.invalidate();" + "\r\n";
            texto += "           }" + "\r\n";
            texto += "           String url = (\"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml?faces-redirect=true\");" + "\r\n";
            texto += "           FacesContext fc = FacesContext.getCurrentInstance();" + "\r\n";
            texto += "           ExternalContext ec = fc.getExternalContext();" + "\r\n";
            texto += "           ec.redirect(url);" + "\r\n";
            texto += "           return \"/" + proyectoJEE.getProyecto() + "/faces/login.xhtml?faces-redirect=true\";" + "\r\n";
            texto += "       } catch (Exception e) {" + "\r\n";
            texto += "           JsfUtil.addErrorMessage(e, \"logout()\");" + "\r\n";
            texto += "       }" + "\r\n";
            texto += "       return \"/" + proyectoJEE.getProyecto() + "/faces/login.xhtml?faces-redirect=true\";" + "\r\n";
            texto += "   } " + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("logout()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String doLogin() {
        try {
            String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
            String primeraletra = Utilidades.getFirstLetter(mySesion.getEntidadUser().getTabla()).toLowerCase();
            fw.write("    public String doLogin() {" + "\r\n");
            fw.write("        try {" + "\r\n");
            fw.write("" + "\r\n");
            fw.write("            loggedIn = true;" + "\r\n");
            fw.write("            " + minuscula + "= new " + mySesion.getEntidadUser().getTabla() + "(); " + "\r\n");
            fw.write("            if (username == null || password == null) {" + "\r\n");
            fw.write("                JsfUtil.addWarningMessage(rf.getMensajeArb(\"login.usernamenotvalid\"));" + "\r\n");
            fw.write("                return null;" + "\r\n");
            fw.write("            }" + "\r\n");
            fw.write("            " + minuscula + ".set" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "(username); " + "\r\n");
            fw.write("            Optional<" + mySesion.getEntidadUser().getTabla() + "> optional = " + minuscula + "Facade.findById(" + minuscula + ");" + "\r\n");
            fw.write("            if (!optional.isPresent()) {" + "\r\n");
            fw.write("                JsfUtil.addWarningMessage(rf.getAppMessage(\"login.usernamenotvalid\"));" + "\r\n");
            fw.write("                return null;" + "\r\n");
            fw.write("            }else{ " + "\r\n");
            fw.write("               " + minuscula + " = optional.get();" + "\r\n");
            fw.write("               if (!" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(password)) {" + "\r\n");
            fw.write("                   JsfUtil.addSuccessMessage(rf.getAppMessage(\"login.passwordnotvalid\"));" + "\r\n");
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
                    fw.write("                   JsfUtil.addSuccessMessage(rf.getAppMessage(\"login.notienerolenelsistema\") + \" \" + " + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosIdGrupo()) + "());" + "\r\n");
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
                        fw.write("               if (!validadorRoles.validarRoles(" + minuscula + ".get"+Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) +"().get"+ Utilidades.letterToUpper(idroles) + "())) {" + "\r\n");
                        fw.write("                   JsfUtil.addSuccessMessage(rf.getAppMessage(\"login.notienerolenelsistema\") + \" \" + " + minuscula + ".get"+Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) +"().get"+ Utilidades.letterToUpper(idroles) + "());" + "\r\n");
                        fw.write("                   loggedIn = false;" + "\r\n");
                        fw.write("                   return \"\";" + "\r\n");
                        fw.write("                }" + "\r\n");
                        fw.write("           }" + "\r\n");

                    } else {
// cuando tiene multiples relaciones



//                            fw.write("            if (!" + primeraletra + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "())) {" + "\r\n");
//                            fw.write("                JsfUtil.addSuccessMessage(rf.getMensajeArb(\"login.passwordnotvalid\"));" + "\r\n");
//                            fw.write("                return \"\";" + "\r\n");
//                            fw.write("            }" + "\r\n");
//                            fw.write("            " + minuscula + " = " + primeraletra + ";" + "\r\n");
                        fw.write("            else{;" + "\r\n");
                        fw.write("            private rolvalido=false;" + "\r\n");
                        fw.write("            List<" + Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) + "> list = new ArrayList<>();" + "\r\n");
                        String iduser = "";
                        for (Atributos a : mySesion.getEntidadUser().getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                iduser = a.getNombre();
                            }
                        }
                        String idroles = "";
                        for (Atributos a : mySesion.getEntidadRoles().getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                idroles = a.getNombre();
                            }
                        }
                        String idgruposuario = "";
                        for (Atributos a : mySesion.getEntidadGruposUsuariosMultiples().getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                idgruposuario = a.getNombre();
                            }
                        }

                        fw.write("            " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + ".set" + Utilidades.letterToUpper(iduser) + "(" + minuscula + ");" + "\r\n");
                        fw.write("            list = " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade.findById" + Utilidades.letterToUpper(iduser) + "(" + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + ".get" + Utilidades.letterToUpper(iduser) + "());" + "\r\n");
                        fw.write("            for(" + Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) + " r: list){" + "\r\n");
                        fw.write("                if(" + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + ".get" + Utilidades.letterToUpper(idgruposuario) + "().equals(r.get" + Utilidades.letterToUpper(idgruposuario) + "().get" + Utilidades.letterToUpper(idgruposuario) + "())){" + "\r\n");
                        fw.write("                    rolvalido=Boolean.TRUE;" + "\r\n");
                        fw.write("                }" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("            if (!rolvalido) {" + "\r\n");
                        fw.write("                 JsfUtil.addSuccessMessage(rf.getMensajeArb(\"warning.notieneasignadoesterol\"));" + "\r\n");
                        fw.write("                 return \"\";" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("            if (validadorRoles.validarRoles(" + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + ".get" + Utilidades.letterToUpper(mySesion.getAtributosGrupousuarioMostrar()) + "())) {" + "\r\n");
                        fw.write("               setLogeado(Boolean.TRUE);" + "\r\n");
                        fw.write("               return \"/index\";" + "\r\n");
                        fw.write("           }" + "\r\n");
                         fw.write("       }" + "\r\n");

                    }//multiples relaciones

                }
            }

            fw.write("           loggedIn = true;" + "\r\n");
            fw.write("           foto = \"img/me.jpg\";" + "\r\n");
            fw.write("            JsfUtil.addSuccessMessage(rf.getAppMessage(\"login.welcome\") + \" \" + " + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosNombreMostrar()) + "());" + "\r\n");
            fw.write("            return \"/faces/index.xhtml?faces-redirect=true\";" + "\r\n");
            fw.write("        } catch (Exception e) {" + "\r\n");
            fw.write("            JsfUtil.addErrorMessage(e, \"doLogin()\");" + "\r\n");
            fw.write("        }" + "\r\n");
            fw.write("        return null;" + "\r\n");
            fw.write("    }" + "\r\n");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("doLogin()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
