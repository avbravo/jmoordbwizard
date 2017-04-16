/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.old;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
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
import java.util.logging.Level;
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
public class FooterxhtmlGenerador1 implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(FooterxhtmlGenerador1.class.getName());

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

            procesar("footer.xhtml", proyectoJEE.getPathMainWebappPages() + proyectoJEE.getSeparator() + "footer.xhtml");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
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
    private Boolean crearFile(String path, String archivo) throws IOException {
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

                    fw.write("<ui:composition xmlns=\"http://www.w3.org/1999/xhtml\"" + "\r\n");
                    fw.write("      xmlns:ui=\"http://java.sun.com/jsf/facelets\"" + "\r\n");
                    fw.write("      xmlns:h=\"http://xmlns.jcp.org/jsf/html\"" + "\r\n");
                    fw.write("      xmlns:b=\"http://bootsfaces.net/ui\"" + "\r\n");
                    fw.write("      xmlns:f=\"http://xmlns.jcp.org/jsf/core\"" + "\r\n");
                    fw.write("      xmlns:p=\"http://primefaces.org/ui\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    <ui:composition>" + "\r\n");
                    fw.write("           <b:navBar  brand=\"#{men['menu.home']}\" brandHref=\"/" + proyectoJEE.getProyecto() + "/faces/index.xhtml\" inverse=\"true\" fixed=\"top\">" + "\r\n");
                    fw.write("            <b:navbarLinks>" + "\r\n");
                    mySesion.getMenubarList().stream().forEach((s) -> {
                        try {
                            fw.write("       <ui:include src=\"menu" + Utilidades.letterToLower(s) + ".xhtml\"/>" + "\r\n");
                        } catch (IOException ex) {
                            Logger.getLogger(FooterxhtmlGenerador1.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });
                    fw.write("                <ui:include src=\"menuoptions.xhtml\"/>" + "\r\n");
                    fw.write("            </b:navbarLinks>" + "\r\n");
                    
                    fw.write("            <h:form id=\"formheader\" prependId=\"false\" styleClass=\"navbar-form navbar-right\">" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("                <p:growl id=\"growlheader\"/>" + "\r\n");
                    fw.write("                <div class=\"form-group\">" + "\r\n");
                    fw.write("                    <b:inputText rendered=\"#{!loginBean.logeado}\" value=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosUsername()) + "}\" placeholder=\"#{app['login.username']}\" fieldSize=\"sm\"/>" + "\r\n");
                    fw.write("                </div>" + "\r\n");
                    fw.write("                <div class=\"form-group\">" + "\r\n");
                    fw.write("                    <p:password rendered=\"#{!loginBean.logeado}\" value=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosPassword()) + "}\" placeholder=\"#{app['login.password']}\" />" + "\r\n");
                    fw.write("                </div> " + "\r\n");
                    if(mySesion.getTypeUserGroupList()){
                         fw.write("                <div class=\"form-group\">" + "\r\n");

                    fw.write("                                <p:selectOneMenu rendered=\"#{!loginBean.logeado}\"   " + "\r\n");
                    fw.write("                                              filter=\"true\" filterMatchMode=\"startsWith\" effect=\"fade\"" + "\r\n");
                    fw.write("                                              value=\"#{loginBean."+ Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla())+"}\"" + "\r\n");
                    fw.write("                                              required=\"true\" requiredMessage=\"#{msg." + Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla())+"} #{app['info.required']}\">" + "\r\n");
                    fw.write("                                   <!-- TODO: update below reference to list of available items-->" + "\r\n");
                    fw.write("                                   <f:converter binding=\"#{"+ Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla())+"Converter}\" />" + "\r\n");
                    fw.write("                                   <f:selectItems value=\"#{"+ Utilidades.letterToLower(mySesion.getEntidadGruposUsuariosMultiples().getTabla())+"Search.items}\"" + "\r\n");
                    fw.write("                                                  var=\"p\"" + "\r\n");
                    fw.write("                                                  itemValue=\"#{p}\"" + "\r\n");
                    fw.write("                                                  itemLabel=\"#{p."+ Utilidades.letterToLower(mySesion.getAtributosGrupousuarioMostrar())+"}\"/>" + "\r\n");
                    fw.write("                                 </p:selectOneMenu>" + "\r\n");

                    fw.write("                </div> " + "\r\n");
                    }
                   

                    fw.write("                #{' '}" + "\r\n");
                    
                    fw.write("                <b:commandButton rendered=\"#{!loginBean.logeado}\" action=\"#{loginBean.verificarLogin}\" value=\"#{app['boton.login']}\" update=\":formheader:growlheader\" look=\"primary\"  iconAlign=\"right\"/>" + "\r\n");
                    fw.write("                <b:label  text=\"#{loginBean." + Utilidades.letterToLower(mySesion.getEntidadUser().getTabla()) + "." + Utilidades.letterToLower(mySesion.getAtributosNombreMostrar()) + "}\"  rendered=\"#{loginBean.logeado}\"/>" + "\r\n");
                    fw.write("                <b:commandButton rendered=\"#{loginBean.logeado}\" action=\"#{loginBean.logout()}\" value=\"#{app['boton.logout']}\" look=\"success\"  iconAlign=\"right\"/>" + "\r\n");
                    fw.write("            </h:form>" + "\r\n");
                    fw.write("        </b:navBar>" + "\r\n");
                    fw.write("    </ui:composition>" + "\r\n");
                    fw.write("</html>" + "\r\n");
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

  

}
