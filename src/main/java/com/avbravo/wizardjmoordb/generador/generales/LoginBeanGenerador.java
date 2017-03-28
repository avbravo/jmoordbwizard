/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.generales;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
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
public class LoginBeanGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(LoginBeanGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("LoginBean", proyectoJEE.getPathUtil() + "LoginBean.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAdd(ruta, "String tema;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAdd(ruta, "Boolean logeado = false;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAdd(ruta, "Boolean rolvalido = false;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAdd(ruta, mySesion.getEntidadUser().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + " = new " + mySesion.getEntidadUser().getTabla() + "();", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAdd(ruta, mySesion.getEntidadRoles().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + " = new " + mySesion.getEntidadRoles().getTabla() + "();", "public class LoginBean implements Serializable {", false);
                if (mySesion.getMultiplesRoles()) {
                    Utilidades.searchAdd(ruta, mySesion.getEntidadGruposUsuariosMultiples().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + " = new " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "();", "public class LoginBean implements Serializable {", false);
                }

                Utilidades.searchAddTextAndInject(ruta, "MenuBeans menuBeans;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, "ResourcesFiles rf;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, "ValidadorRoles validadorRoles;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, "ManagementThemes managementThemes;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, mySesion.getEntidadUser().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade;", "public class LoginBean implements Serializable {", false);
                Utilidades.searchAddTextAndInject(ruta, mySesion.getEntidadRoles().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade;", "public class LoginBean implements Serializable {", false);
                if (mySesion.getMultiplesRoles()) {
                    Utilidades.searchAddTextAndInject(ruta, mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + "Facade;", "public class LoginBean implements Serializable {", false);
                }

//                for (String s : mySesion.getRolesList()) {
//                    String barra = "Rol" + s + " rol" + Utilidades.convertirLetraMayuscula(s) + ";";
//                    Utilidades.searchAddTextAndInjectBeforeNotFound(ruta, barra, "public class LoginBean implements Serializable {", false);
//
//                }
                /**
                 * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public void init() {", init(), "public class LoginBean implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public LoginBean() {", loginBean(), "public class LoginBean implements Serializable {", false);
                if (!mySesion.getMultiplesRoles()) {
                    Utilidades.addNotFoundMethod(ruta, "public String verificarLogin() {", verificarLogin(), "public class LoginBean implements Serializable {", false);
                } else {

                }

                Utilidades.addNotFoundMethod(ruta, "public String logout() {", logout(), "public class LoginBean implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public String irLogin() {", irLogin(), "public class LoginBean implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public void irInicio() {", irInicioVoid(), "public class LoginBean implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public String cambiarContrasena() {", cambiarContrasena(), "public class LoginBean implements Serializable {", false);
                Utilidades.addNotFoundMethod(ruta, "public String verificarLogin() {", verificarLogin(), "public class LoginBean implements Serializable {", false);


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

            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".entity.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".ejb.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".roles.*;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".menu.MenuBeans;", "package", false);
            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".generales.*;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.ArrayList;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.annotation.PostConstruct;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.ExternalContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.FacesContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.servlet.http.HttpSession;", "package", false);
            Utilidades.searchAdd(ruta, "import lombok.Getter;", "package", false);
            Utilidades.searchAdd(ruta, "import lombok.Setter;", "package", false);

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
//      bw.write("Acabo de crear el fichero de texto.");

                File file2 = new File(ruta);
                //Creamos un objeto para escribir caracteres en el archivo de prueba
                try (FileWriter fw = new FileWriter(file)) {
                    fw.write("/*" + "\r\n");
                    fw.write("* To change this license header, choose License Headers in Project Properties." + "\r\n");
                    fw.write("* To change this template file, choose Tools | Templates" + "\r\n");
                    fw.write(" * and open the template in the editor." + "\r\n");
                    fw.write("*/" + "\r\n");
                    fw.write("package " + proyectoJEE.getPaquete() + ".generales;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".entity.*;" + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".ejb.*;" + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".menu.MenuBeans;" + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".roles.*;" + "\r\n");
                    fw.write("import " + proyectoJEE.getPaquete() + ".generales.*;" + "\r\n");
                    fw.write("import javax.inject.Inject;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import java.util.ArrayList;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import javax.annotation.PostConstruct;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.faces.context.ExternalContext;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.servlet.http.HttpSession;" + "\r\n");
                    fw.write("import lombok.Getter;" + "\r\n");
                    fw.write("import lombok.Setter;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("@Getter" + "\r\n");
                    fw.write("@Setter" + "\r\n");
                    fw.write("public class LoginBean implements Serializable {" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    MenuBeans menuBeans;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ResourcesFiles rf;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ValidadorRoles validadorRoles;" + "\r\n");
                    fw.write("    Boolean logeado = false;" + "\r\n");
                    fw.write("    Boolean rolvalido = false;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    ManagementThemes managementThemes;" + "\r\n");
                    fw.write("    String tema;" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + mySesion.getEntidadUser().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade;" + "\r\n");
                    fw.write("    " + mySesion.getEntidadUser().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + " = new " + mySesion.getEntidadUser().getTabla() + "();" + "\r\n");
                    fw.write("    @Inject" + "\r\n");
                    fw.write("    " + mySesion.getEntidadRoles().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade;" + "\r\n");
                    fw.write("    " + mySesion.getEntidadRoles().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + " = new " + mySesion.getEntidadRoles().getTabla() + "();" + "\r\n");
                    if (mySesion.getMultiplesRoles()) {
                        fw.write("    @Inject" + "\r\n");
                        fw.write("    " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "Facade " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + "Facade;" + "\r\n");
                        fw.write("    " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + " " + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + " = new " + mySesion.getEntidadGruposUsuariosMultiples().getTabla() + "();" + "\r\n");
                    }

                    fw.write("" + "\r\n");

                    fw.write("    @PostConstruct" + "\r\n");
                    fw.write("    public void init() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    public LoginBean() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
                    String primeraletra = Utilidades.getFirstLetter(mySesion.getEntidadUser().getTabla()).toLowerCase();

                    if (!mySesion.getMultiplesRoles()) {

                        fw.write("    public String verificarLogin() {" + "\r\n");
                        fw.write("        try {" + "\r\n");
                        fw.write("" + "\r\n");
                        fw.write("            menuBeans.habilitarTodo(false);" + "\r\n");
                        fw.write("            setLogeado(Boolean.FALSE);" + "\r\n");
                        fw.write("            " + mySesion.getEntidadUser().getTabla() + " " + primeraletra + " = " + minuscula + "Facade.find(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "()); " + "\r\n");
                        fw.write("            if (u == null) {" + "\r\n");
                        fw.write("                JSFUtil.addWarningMessage(rf.getMensajeArb(\"login.usernamenotvalid\"));" + "\r\n");
                        fw.write("                return null;" + "\r\n");
                        fw.write("            }" + "\r\n");

                        fw.write("            if (!" + primeraletra + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "())) {" + "\r\n");
                        fw.write("                JSFUtil.addSuccessMessage(rf.getMensajeArb(\"login.passwordnotvalid\"));" + "\r\n");
                        fw.write("                return \"\";" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("            " + minuscula + " = " + primeraletra + ";" + "\r\n");
                        fw.write("            setLogeado(Boolean.TRUE);" + "\r\n");
                        String idroles = "";
                        for (Atributos a : mySesion.getEntidadRoles().getAtributosList()) {
                            if (a.getEsPrimaryKey()) {
                                idroles = a.getNombre();
                            }
                        }
                        fw.write("            if (validadorRoles.validarRoles(" + minuscula + ".get" + Utilidades.letterToUpper(idroles) + "().get" + Utilidades.letterToUpper(idroles) + "())) {" + "\r\n");
                        fw.write("                return \"/index\";" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("" + "\r\n");
                        fw.write("        } catch (Exception e) {" + "\r\n");
                        fw.write("            JSFUtil.addErrorMessage(e, \"verificarLogin()\");" + "\r\n");
                        fw.write("        }" + "\r\n");
                        fw.write("        return null;" + "\r\n");
                        fw.write("    }" + "\r\n");

                    } else {
// cuando tiene multiples relaciones
                        fw.write("    public String verificarLogin() {" + "\r\n");
                        fw.write("        try {" + "\r\n");
                        fw.write("" + "\r\n");
                        fw.write("            menuBeans.habilitarTodo(false);" + "\r\n");
                        fw.write("            setLogeado(Boolean.FALSE);" + "\r\n");
                        fw.write("            " + mySesion.getEntidadUser().getTabla() + " " + primeraletra + " = " + minuscula + "Facade.find(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "()); " + "\r\n");
                        fw.write("            if (u == null) {" + "\r\n");
                        fw.write("                JSFUtil.addWarningMessage(rf.getMensajeArb(\"login.usernamenotvalid\"));" + "\r\n");
                        fw.write("                return null;" + "\r\n");
                        fw.write("            }" + "\r\n");

                        fw.write("            if (!" + primeraletra + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "())) {" + "\r\n");
                        fw.write("                JSFUtil.addSuccessMessage(rf.getMensajeArb(\"login.passwordnotvalid\"));" + "\r\n");
                        fw.write("                return \"\";" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("            " + minuscula + " = " + primeraletra + ";" + "\r\n");
                       
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
                        fw.write("            rolvalido =Boolean.FALSE;" + "\r\n");
                       
                        fw.write("            " +Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla())+ ".set" + Utilidades.letterToUpper(iduser) + "(" + minuscula + ");" + "\r\n");
                        fw.write("            list = " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade.findBy" + Utilidades.letterToUpper(iduser) + "(" + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + ".get" + Utilidades.letterToUpper(iduser) + "());" + "\r\n");
                        fw.write("            for("+ Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla())+" r: list){" + "\r\n");
                        fw.write("                if(" + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + ".get" + Utilidades.letterToUpper(idgruposuario) + "().equals(r.get" + Utilidades.letterToUpper(idgruposuario) + "().get" + Utilidades.letterToUpper(idgruposuario) + "())){" + "\r\n");
                        fw.write("                    rolvalido=Boolean.TRUE;" + "\r\n");
                        fw.write("                }" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("            if (!rolvalido) {" + "\r\n");
                        fw.write("                 JSFUtil.addSuccessMessage(rf.getMensajeArb(\"warning.notieneasignadoesterol\"));" + "\r\n");
                        fw.write("                 return \"\";" + "\r\n");
                        fw.write("            }" + "\r\n");
                        fw.write("            if (validadorRoles.validarRoles(" + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + ".get" + Utilidades.letterToUpper(mySesion.getAtributosGrupousuarioMostrar()) + "())) {" + "\r\n");
                         fw.write("               setLogeado(Boolean.TRUE);" + "\r\n");
                        fw.write("               return \"/index\";" + "\r\n");
                        fw.write("           }" + "\r\n");
                        fw.write("" + "\r\n");
                        fw.write("        } catch (Exception e) {" + "\r\n");
                        fw.write("            JSFUtil.addErrorMessage(e, \"verificarLogin()\");" + "\r\n");
                        fw.write("        }" + "\r\n");
                        fw.write("        return null;" + "\r\n");
                        fw.write("    }" + "\r\n");
                    }//multiples relaciones

//logout()
                    fw.write("    public String logout() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);" + "\r\n");
                    fw.write("            if (session != null) {" + "\r\n");
                    fw.write("                session.invalidate();" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            String url = (\"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml?faces-redirect=true\");" + "\r\n");
                    fw.write("            FacesContext fc = FacesContext.getCurrentInstance();" + "\r\n");
                    fw.write("            ExternalContext ec = fc.getExternalContext();" + "\r\n");
                    fw.write("            try {" + "\r\n");
                    fw.write("                ec.redirect(url);" + "\r\n");
                    fw.write("            } catch (Exception ex) {" + "\r\n");
                    fw.write("                JSFUtil.addErrorMessage(ex.getLocalizedMessage());" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("            return \"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml?faces-redirect=true\";" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e, \"logout()\");" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    //irLogin()
                    fw.write("" + "\r\n");
                    fw.write("    public String irLogin() {" + "\r\n");
                    fw.write("        return \"/faces/index\";" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    //irInicio()
                    fw.write("    public void irInicio() {" + "\r\n");
                    fw.write("        FacesContext ctx = FacesContext.getCurrentInstance();" + "\r\n");
                    fw.write("        ExternalContext extContext = ctx.getExternalContext();" + "\r\n");
                    fw.write("        String url" + "\r\n");
                    fw.write("                = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx," + "\r\n");
                    fw.write("                        \"/index.xhtml\"));" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            extContext.redirect(url);" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    // cambiarContrasena()
                    fw.write("    public String cambiarContrasena() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade.edit(" + minuscula + ");" + "\r\n");
                    fw.write("            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.update\"));" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("}" + "\r\n");
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("crearFile() " + ex.getLocalizedMessage());
                }

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

    private String cambiarContrasena() {
        try {
            String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
            String texto = "";
            texto += "    public String cambiarContrasena() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            " + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "Facade.edit(" + minuscula + ");" + "\r\n";
            texto += "            JSFUtil.addSuccessMessage(rf.getMensajeArb(\"info.update\"));" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("constructor()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String irInicioVoid() {
        try {

            String texto = "";
            texto += "    public void irInicio() {" + "\r\n";
            texto += "        FacesContext ctx = FacesContext.getCurrentInstance();" + "\r\n";
            texto += "        ExternalContext extContext = ctx.getExternalContext();" + "\r\n";
            texto += "        String url" + "\r\n";
            texto += "                = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx," + "\r\n";
            texto += "                        \"/index.xhtml\"));" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            extContext.redirect(url);" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("constructor()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String loginBean() {
        try {

            String texto = "";
            texto += "    public LoginBean() {" + "\r\n";
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
            texto += "       try {" + "\r\n";
            texto += "           HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);" + "\r\n";
            texto += "           if (session != null) {" + "\r\n";
            texto += "               session.invalidate();" + "\r\n";
            texto += "           }" + "\r\n";
            texto += "           String url = (\"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml?faces-redirect=true\");" + "\r\n";
            texto += "           FacesContext fc = FacesContext.getCurrentInstance();" + "\r\n";
            texto += "           ExternalContext ec = fc.getExternalContext();" + "\r\n";
            texto += "           try {" + "\r\n";
            texto += "               ec.redirect(url);" + "\r\n";
            texto += "           } catch (Exception ex) {" + "\r\n";
            texto += "               JSFUtil.addErrorMessage(ex.getLocalizedMessage());" + "\r\n";
            texto += "           }" + "\r\n";
            texto += "           return \"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml?faces-redirect=true\";" + "\r\n";
            texto += "       } catch (Exception e) {" + "\r\n";
            texto += "           JSFUtil.addErrorMessage(e, \"logout()\");" + "\r\n";
            texto += "       }" + "\r\n";
            texto += "       return null;" + "\r\n";
            texto += "   } " + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("logout()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String verificarLogin() {
        try {
            String minuscula = Utilidades.letterToLower(mySesion.getEntidadUser().getTabla());
            String primeraletra = Utilidades.getFirstLetter(mySesion.getEntidadUser().getTabla()).toLowerCase();
            String texto = "";

            if (!mySesion.getMultiplesRoles()) {

                texto += "    public String verificarLogin() {" + "\r\n";
                texto += "        try {" + "\r\n";
                texto += "" + "\r\n";
                texto += "            menuBeans.habilitarTodo(false);" + "\r\n";
                texto += "            setLogeado(Boolean.FALSE);" + "\r\n";
                texto += "            " + mySesion.getEntidadUser().getTabla() + " " + primeraletra + " = " + minuscula + "Facade.find(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "()); " + "\r\n";
                texto += "            if (u == null) {" + "\r\n";
                texto += "                JSFUtil.addWarningMessage(rf.getMensajeArb(\"login.usernamenotvalid\"));" + "\r\n";
                texto += "                return null;" + "\r\n";
                texto += "            }" + "\r\n";

                texto += "            if (!" + primeraletra + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "())) {" + "\r\n";
                texto += "                JSFUtil.addSuccessMessage(rf.getMensajeArb(\"login.passwordnotvalid\"));" + "\r\n";
                texto += "                return \"\";" + "\r\n";
                texto += "            }" + "\r\n";
                texto += "            " + minuscula + " = " + primeraletra + ";" + "\r\n";
                texto += "            setLogeado(Boolean.TRUE);" + "\r\n";
                String idroles = "";
                for (Atributos a : mySesion.getEntidadRoles().getAtributosList()) {
                    if (a.getEsPrimaryKey()) {
                        idroles = a.getNombre();
                    }
                }
                texto += "            if (validadorRoles.validarRoles(" + minuscula + ".get" + Utilidades.letterToUpper(idroles) + "().get" + Utilidades.letterToUpper(idroles) + "())) {" + "\r\n";
                texto += "                return \"/index\";" + "\r\n";
                texto += "            }" + "\r\n";
                texto += "" + "\r\n";
                texto += "        } catch (Exception e) {" + "\r\n";
                texto += "            JSFUtil.addErrorMessage(e, \"verificarLogin()\");" + "\r\n";
                texto += "        }" + "\r\n";
                texto += "        return null;" + "\r\n";
                texto += "    }" + "\r\n";

            } else {
// cuando tiene multiples relaciones
                texto += "    public String verificarLogin() {" + "\r\n";
                texto += "        try {" + "\r\n";
                texto += "" + "\r\n";
                texto += "            menuBeans.habilitarTodo(false);" + "\r\n";
                texto += "            setLogeado(Boolean.FALSE);" + "\r\n";
                texto += "            " + mySesion.getEntidadUser().getTabla() + " " + primeraletra + " = " + minuscula + "Facade.find(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosUsername()) + "()); " + "\r\n";
                texto += "            if (u == null) {" + "\r\n";
                texto += "                JSFUtil.addWarningMessage(rf.getMensajeArb(\"login.usernamenotvalid\"));" + "\r\n";
                texto += "                return null;" + "\r\n";
                texto += "            }" + "\r\n";

                texto += "            if (!" + primeraletra + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "().equals(" + minuscula + ".get" + Utilidades.letterToUpper(mySesion.getAtributosPassword()) + "())) {" + "\r\n";
                texto += "                JSFUtil.addSuccessMessage(rf.getMensajeArb(\"login.passwordnotvalid\"));" + "\r\n";
                texto += "                return \"\";" + "\r\n";
                texto += "            }" + "\r\n";
                texto += "            " + minuscula + " = " + primeraletra + ";" + "\r\n";
                texto += "            setLogeado(Boolean.TRUE);" + "\r\n";
                texto += "            List<" + Utilidades.letterToUpper(mySesion.getEntidadRoles().getTabla()) + "> list = new ArrayList<>();" + "\r\n";
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
                texto += "            rolvalido =Boolean.FALSE;" + "\r\n";
                texto += "            list = " + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + "Facade.findBy" + Utilidades.letterToUpper(iduser) + "(" + Utilidades.letterToLower(mySesion.getEntidadRoles().getTabla()) + ".get" + Utilidades.letterToUpper(iduser) + "());" + "\r\n";
                texto += "            for(Roles r: list){" + "\r\n";

                texto += "                if(" + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + ".get" + Utilidades.letterToUpper(idgruposuario) + "().equals(r.get" + Utilidades.letterToUpper(idgruposuario) + "().get" + Utilidades.letterToUpper(idgruposuario) + "())){" + "\r\n";
                texto += "                    rolvalido=Boolean.TRUE;" + "\r\n";
                texto += "                }" + "\r\n";
                texto += "            }" + "\r\n";
                texto += "             if (!rolvalido) {" + "\r\n";
                texto += "                 JSFUtil.addSuccessMessage(rf.getMensajeArb(\"warning.notieneasignadoesterol\"));" + "\r\n";
                texto += "                 return \"\";" + "\r\n";
                texto += "             }" + "\r\n";
                texto += "           if (validadorRoles.validarRoles(" + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla()) + ".get" + Utilidades.letterToUpper(mySesion.getAtributosGrupousuarioMostrar()) + "())) {" + "\r\n";
                texto += "               return \"/index\";" + "\r\n";
                texto += "           }" + "\r\n";
                texto += "" + "\r\n";
                texto += "        } catch (Exception e) {" + "\r\n";
                texto += "            JSFUtil.addErrorMessage(e, \"verificarLogin()\");" + "\r\n";
                texto += "        }" + "\r\n";
                texto += "        return null;" + "\r\n";
                texto += "    }" + "\r\n";
            }//multiples relaciones

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("verificarLogin()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
