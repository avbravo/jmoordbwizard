/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.generales;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
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
public class JSFUtilGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(EmailValidatorGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    Rutas rutas;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            procesar("JSFUtil.java", rutas.getPathUtil() + "JSFUtil.java");

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

                /**
                 * generar los metodos
                 */
                if (!Utilidades.search(ruta, "public static SelectItem[")) {
                  Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getSelectItems(),false);
                }

                if (!Utilidades.search(ruta, "public String logout()")) {

                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", logout(), false);
//            
                }
                if (!Utilidades.search(ruta, "public static void addErrorMessage(Exception ex")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", addErrorMessage(), false);
                }
                if (!Utilidades.search(ruta, "public static void addErrorMessages(List<String>")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", addErrorMessages(), false);
                }
                if (!Utilidades.search(ruta, "public static void addErrorMessage(String msg)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", addErrorMessageText(), false);
                }
                if (!Utilidades.search(ruta, "public static void testMessage(String msg)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", testMessage(), false);
                }
                if (!Utilidades.search(ruta, "public static void addSuccessMessage(String msg)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", addSuccessMessage(), false);
                }
                if (!Utilidades.search(ruta, "public static void addWarningMessage(String msg)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", addWarningMessage(), false);
                }
                if (!Utilidades.search(ruta, "public static void addFatalMessage(String msg)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", addFatalMessage(), false);
                }
                if (!Utilidades.search(ruta, "public static String getRequestParameter(String key)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getRequestParameter(), false);
                }
                if (!Utilidades.search(ruta, "public static Object getObjectFromRequestParameter(String requestParameterName")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getObjectFromRequestParameter(), false);
                }
                if (!Utilidades.search(ruta, "public static void infoDialog(String titulo")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", infoDialog(), false);
                }
                if (!Utilidades.search(ruta, "public static void warningDialog(String titulo, String texto)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", warningDialog(), false);
                }
                if (!Utilidades.search(ruta, "public static void fatalDialog(String titulo, String texto)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", fatalDialog(), false);
                }
                if (!Utilidades.search(ruta, "public static void errorDialog(String titulo, String texto)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", errorDialog(), false);
                }
                if (!Utilidades.search(ruta, "public static java.sql.Date converterDate(java.util.Date fecha)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", converterDate(), false);
                }
                if (!Utilidades.search(ruta, "public static String getUUID() {")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getUUID(), false);
                }
                if (!Utilidades.search(ruta, "public static String getExtension(String texto)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getExtension(), false);
                }
                if (!Utilidades.search(ruta, "public static Boolean copyFile(String fileName, InputStream in, String destination)")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", copyFile(), false);
                }
                if (!Utilidades.search(ruta, "public static String getPathFotos()")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getPathFotos(), false);
                }
                if (!Utilidades.search(ruta, "public static String getPath()")) {
                    Utilidades.add(ruta, "public class JSFUtil implements Serializable{", getPath(), false);
                }

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
            if (!Utilidades.search(ruta, "import javax.servlet.http.HttpSession;")) {
                Utilidades.add(ruta, "package", "import javax.servlet.http.HttpSession;", false);
            }
            if (!Utilidades.search(ruta, "import javax.inject.Named;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.inject.Named;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.model.SelectItem;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.model.SelectItem;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.convert.Converter;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.convert.Converter;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.context.FacesContext;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.context.FacesContext;", false);
            }
            if (!Utilidades.search(ruta, "import java.io.Serializable;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.io.Serializable;", false);
            }
            if (!Utilidades.search(ruta, "import java.security.MessageDigest;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.security.MessageDigest;", false);
            }
            if (!Utilidades.search(ruta, "import java.util.Iterator;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.util.Iterator;", false);
            }
            if (!Utilidades.search(ruta, "import java.util.List;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.util.List;", false);
            }
            if (!Utilidades.search(ruta, "import java.util.logging.Logger;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.util.logging.Logger;", false);
            }

            if (!Utilidades.search(ruta, "import javax.enterprise.context.RequestScoped;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.enterprise.context.RequestScoped;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.application.FacesMessage;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.application.FacesMessage;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.component.UIComponent;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.component.UIComponent;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.component.UIInput;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.component.UIInput;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.component.UISelectItem;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.faces.component.UISelectItem;", false);
            }
            if (!Utilidades.search(ruta, "import org.primefaces.context.RequestContext;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import org.primefaces.context.RequestContext;", false);
            }
            if (!Utilidades.search(ruta, "import java.io.File;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.io.File;", false);
            }
            if (!Utilidades.search(ruta, "import java.io.FileOutputStream;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.io.FileOutputStream;", false);
            }
            if (!Utilidades.search(ruta, "import java.io.InputStream;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.io.InputStream;", false);
            }
            if (!Utilidades.search(ruta, "import java.io.OutputStream;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.io.OutputStream;", false);
            }
            if (!Utilidades.search(ruta, "import java.util.UUID;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.util.UUID;", false);
            }
            if (!Utilidades.search(ruta, "import java.io.IOException;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import java.io.IOException;", false);
            }
            if (!Utilidades.search(ruta, "import javax.servlet.ServletContext;")) {
                Utilidades.add(ruta, "import javax.servlet.http.HttpSession;", "import javax.servlet.ServletContext;", false);
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("JSFUtilGenerator.generarImport() " + e.getLocalizedMessage());
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
                    fw.write("package " + mySesion.getPaquete() + ".generales;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.security.MessageDigest;" + "\r\n");
                    fw.write("import java.util.Iterator;" + "\r\n");
                    fw.write("import java.io.File;" + "\r\n");
                    fw.write("import java.io.FileOutputStream;" + "\r\n");
                    fw.write("import java.io.InputStream;" + "\r\n");
                    fw.write("import java.io.OutputStream;" + "\r\n");
                    fw.write("import java.io.IOException;" + "\r\n");
                    fw.write("import java.util.UUID;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import java.util.logging.Logger;" + "\r\n");
                    fw.write("import javax.enterprise.context.RequestScoped;" + "\r\n");
                    fw.write("import javax.faces.application.FacesMessage;" + "\r\n");
                    fw.write("import javax.faces.component.UIComponent;" + "\r\n");
                    fw.write("import javax.faces.component.UIInput;" + "\r\n");
                    fw.write("import javax.faces.component.UISelectItem;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.faces.convert.Converter;" + "\r\n");
                    fw.write("import javax.faces.model.SelectItem;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("import javax.servlet.http.HttpSession;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import javax.servlet.ServletContext;" + "\r\n");
                    fw.write("import org.primefaces.context.RequestContext;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("@Named" + "\r\n");
                    fw.write("@RequestScoped" + "\r\n");
                    fw.write("public class JSFUtil implements Serializable{" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final Logger LOG = Logger.getLogger(JSFUtil.class.getName());" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {" + "\r\n");
                    fw.write("    int size = selectOne ? entities.size() + 1 : entities.size();" + "\r\n");
                    fw.write("    SelectItem[] items = new SelectItem[size];" + "\r\n");
                    fw.write("    int i = 0;" + "\r\n");
                    fw.write("    if (selectOne) {" + "\r\n");
                    fw.write("        items[0] = new SelectItem(\"\", \"­­­\");" + "\r\n");
                    fw.write("        i++;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    for (Object x : entities) {" + "\r\n");
                    fw.write("        items[i++] = new SelectItem(x, x.toString());" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return items;" + "\r\n");
                    fw.write("}" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("public String logout() {" + "\r\n");
                    fw.write("    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);" + "\r\n");
                    fw.write("    if (session != null) {" + "\r\n");
                    fw.write("        session.invalidate();" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    return \"/index\";" + "\r\n");
                    fw.write("}" + "\r\n");

                    fw.write("public static void addErrorMessage(Exception ex, String defaultMsg) {" + "\r\n");
                    fw.write("        String msg = ex.getLocalizedMessage();" + "\r\n");
                    fw.write("        if (msg != null && msg.length() > 0) {" + "\r\n");
                    fw.write("            addErrorMessage(msg);" + "\r\n");
                    fw.write("        } else {" + "\r\n");
                    fw.write("            addErrorMessage(defaultMsg);" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void addErrorMessages(List<String> messages) {" + "\r\n");
                    fw.write("        for (String message : messages) {" + "\r\n");
                    fw.write("            addErrorMessage(message);" + "\r\n");
                    fw.write("            " + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void addErrorMessage(String msg) {" + "\r\n");
                    fw.write("        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR," + "\r\n");
                    fw.write("                msg, msg);" + "\r\n");
                    fw.write("        FacesContext.getCurrentInstance().addMessage(null, facesMsg);" + "\r\n");
                    fw.write("        LOG.warning(msg);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("     public static void testMessage(String msg) {" + "\r\n");
                    fw.write("        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR," + "\r\n");
                    fw.write("                msg, msg);" + "\r\n");
                    fw.write("        FacesContext.getCurrentInstance().addMessage(null, facesMsg);" + "\r\n");
                    fw.write("          LOG.warning(msg);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void addSuccessMessage(String msg) {" + "\r\n");
                    fw.write("        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg," + "\r\n");
                    fw.write("                msg);" + "\r\n");
                    fw.write("        FacesContext.getCurrentInstance().addMessage(\"successInfo\", facesMsg);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void addWarningMessage(String msg) {" + "\r\n");
                    fw.write("        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, \"\"));" + "\r\n");
                    fw.write("          LOG.warning(msg);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void addFatalMessage(String msg) {" + "\r\n");
                    fw.write("        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, \"\"));" + "\r\n");
                    fw.write("          LOG.warning(msg);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static String getRequestParameter(String key) {" + "\r\n");
                    fw.write("        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static Object getObjectFromRequestParameter(String requestParameterName," + "\r\n");
                    fw.write("            Converter converter, UIComponent component) {" + "\r\n");
                    fw.write("        String theId = JSFUtil.getRequestParameter(requestParameterName);" + "\r\n");
                    fw.write("        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void infoDialog(String titulo, String texto) {" + "\r\n");
                    fw.write("        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo," + "\r\n");
                    fw.write("                texto);" + "\r\n");
                    fw.write("        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void warningDialog(String titulo, String texto) {" + "\r\n");
                    fw.write("        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo," + "\r\n");
                    fw.write("                texto);" + "\r\n");
                    fw.write("        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n");
                    fw.write("          LOG.warning(titulo + \" \" +texto);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void fatalDialog(String titulo, String texto) {" + "\r\n");
                    fw.write("        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo," + "\r\n");
                    fw.write("                texto);" + "\r\n");
                    fw.write("        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n");
                    fw.write("         LOG.warning(titulo + \" \" +texto);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static void errorDialog(String titulo, String texto) {" + "\r\n");
                    fw.write("        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR," + "\r\n");
                    fw.write("                titulo, texto);" + "\r\n");
                    fw.write("        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n");
                    fw.write("         LOG.warning(titulo + \" \" +texto);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    public static java.sql.Date converterDate(java.util.Date fecha) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            long lfecha = fecha.getTime();" + "\r\n");
                    fw.write("            java.sql.Date dtfecha = new java.sql.Date(lfecha);" + "\r\n");
                    fw.write("            return dtfecha;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            addErrorMessage(\"converterDate() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("/**" + "\r\n");
                    fw.write("   genera id" + "\r\n");
                    fw.write("* " + "\r\n");
                    fw.write(" * @return returna un randomUUID automatico" + "\r\n");
                    fw.write("*/" + "\r\n");
                    fw.write("    public static String getUUID() {" + "\r\n");
                    fw.write(" " + "\r\n");
                    fw.write("        UUID uuid = UUID.randomUUID();" + "\r\n");
                    fw.write("        return  uuid.toString().toLowerCase();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write("    * getExtension()" + "\r\n");
                    fw.write("    * " + "\r\n");
                    fw.write("    * @param texto" + "\r\n");
                    fw.write("    * @return la extension de un nombre de archivo" + "\r\n");
                    fw.write("    */" + "\r\n");
                    fw.write("   public static String getExtension(String texto){" + "\r\n");
                    fw.write("       try {" + "\r\n");
                    fw.write("            return texto.substring(texto.indexOf(\".\"),texto.length());" + "\r\n");
                    fw.write("       } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"getExtension() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("       }" + "\r\n");
                    fw.write("       return \"\";" + "\r\n");
                    fw.write("   }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/*" + "\r\n");
                    fw.write("/*" + "\r\n");
                    fw.write("     copia un archivo generalmente cuando se usa el fileupload" + "\r\n");
                    fw.write("     fileName: nombre del archivo a copiar" + "\r\n");
                    fw.write("     in: Es el InputStream" + "\r\n");
                    fw.write("     destination: ruta donde se guardara el archivo" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public static Boolean copyFile(String fileName, InputStream in, String destination) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            // write the inputStream to a FileOutputStream" + "\r\n");
                    fw.write("            OutputStream out = new FileOutputStream(new File(destination + fileName));" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            int read = 0;" + "\r\n");
                    fw.write("            byte[] bytes = new byte[1024];" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            while ((read = in.read(bytes)) != -1) {" + "\r\n");
                    fw.write("                out.write(bytes, 0, read);" + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            in.close();" + "\r\n");
                    fw.write("            out.flush();" + "\r\n");
                    fw.write("            out.close();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            return true;" + "\r\n");
                    fw.write("        } catch (IOException e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"copyFile() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return false;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(" public static String getPathFotos() {" + "\r\n");
                    fw.write("        try {             " + "\r\n");
                    fw.write("        " + "\r\n");
                    fw.write("            String path = getPath() + \"resources/fotos/\";" + "\r\n");
                    fw.write(" return path;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            addErrorMessage(\"getPathFotosPlagas() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write(" /*" + "\r\n");
                    fw.write("     devuelve el path " + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public static String getPath() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()" + "\r\n");
                    fw.write("                    .getExternalContext().getContext();" + "\r\n");
                    fw.write("            return ctx.getRealPath(\"/\");" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("            addErrorMessage(\"getPath() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("" + "\r\n");
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

    private String getSelectItems() {
        try {

            String texto = "";
            texto += "public static SelectItem[] getSelectItems(List<?> entities, boolean selectOne) {" + "\r\n";
            texto += "    int size = selectOne ? entities.size() + 1 : entities.size();" + "\r\n";
            texto += "    SelectItem[] items = new SelectItem[size];" + "\r\n";
            texto += "    int i = 0;" + "\r\n";
            texto += "    if (selectOne) {" + "\r\n";
            texto += "        items[0] = new SelectItem(\"\", \"­­­\");" + "\r\n";
            texto += "        i++;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "    for (Object x : entities) {" + "\r\n";
            texto += "        items[i++] = new SelectItem(x, x.toString());" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "    return items;" + "\r\n";
            texto += "}" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getSelectItems()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String logout() {
        try {

            String texto = "";
            texto += "public String logout() {" + "\r\n";
            texto += "    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);" + "\r\n";
            texto += "    if (session != null) {" + "\r\n";
            texto += "        session.invalidate();" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "    return \"/index\";" + "\r\n";
            texto += "}" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("logout()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String addErrorMessage() {
        try {

            String texto = "";
            texto += "public static void addErrorMessage(Exception ex, String defaultMsg) {" + "\r\n";
            texto += "        String msg = ex.getLocalizedMessage();" + "\r\n";
            texto += "        if (msg != null && msg.length() > 0) {" + "\r\n";
            texto += "            addErrorMessage(msg);" + "\r\n";
            texto += "        } else {" + "\r\n";
            texto += "            addErrorMessage(defaultMsg);" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String addErrorMessages() {
        try {

            String texto = "";
            texto += "    public static void addErrorMessages(List<String> messages) {" + "\r\n";
            texto += "        for (String message : messages) {" + "\r\n";
            texto += "            addErrorMessage(message);" + "\r\n";
            texto += "            " + "\r\n";
            texto += "        }" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String addErrorMessageText() {
        try {

            String texto = "";
            texto += "    public static void addErrorMessage(String msg) {" + "\r\n";
            texto += "        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR," + "\r\n";
            texto += "                msg, msg);" + "\r\n";
            texto += "        FacesContext.getCurrentInstance().addMessage(null, facesMsg);" + "\r\n";
            texto += "        LOG.warning(msg);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String testMessage() {
        try {

            String texto = "";

            texto += "     public static void testMessage(String msg) {" + "\r\n";
            texto += "        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR," + "\r\n";
            texto += "                msg, msg);" + "\r\n";
            texto += "        FacesContext.getCurrentInstance().addMessage(null, facesMsg);" + "\r\n";
            texto += "          LOG.warning(msg);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String addSuccessMessage() {
        try {

            String texto = "";
            texto += "    public static void addSuccessMessage(String msg) {" + "\r\n";
            texto += "        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg," + "\r\n";
            texto += "                msg);" + "\r\n";
            texto += "        FacesContext.getCurrentInstance().addMessage(\"successInfo\", facesMsg);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String addWarningMessage() {
        try {

            String texto = "";
            texto += "    public static void addWarningMessage(String msg) {" + "\r\n";
            texto += "        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, \"\"));" + "\r\n";
            texto += "          LOG.warning(msg);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String addFatalMessage() {
        try {

            String texto = "";
            texto += "    public static void addFatalMessage(String msg) {" + "\r\n";
            texto += "        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, \"\"));" + "\r\n";
            texto += "          LOG.warning(msg);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getRequestParameter() {
        try {

            String texto = "";

            texto += "    public static String getRequestParameter(String key) {" + "\r\n";
            texto += "        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(key);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getObjectFromRequestParameter() {
        try {

            String texto = "";
            texto += "    public static Object getObjectFromRequestParameter(String requestParameterName," + "\r\n";
            texto += "            Converter converter, UIComponent component) {" + "\r\n";
            texto += "        String theId = JSFUtil.getRequestParameter(requestParameterName);" + "\r\n";
            texto += "        return converter.getAsObject(FacesContext.getCurrentInstance(), component, theId);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String infoDialog() {
        try {

            String texto = "";
            texto += "    public static void infoDialog(String titulo, String texto) {" + "\r\n";
            texto += "        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, titulo," + "\r\n";
            texto += "                texto);" + "\r\n";
            texto += "        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String warningDialog() {
        try {

            String texto = "";

            texto += "    public static void warningDialog(String titulo, String texto) {" + "\r\n";
            texto += "        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, titulo," + "\r\n";
            texto += "                texto);" + "\r\n";
            texto += "        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n";
            texto += "          LOG.warning(titulo + \" \" +texto);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String fatalDialog() {
        try {

            String texto = "";
            texto += "    public static void fatalDialog(String titulo, String texto) {" + "\r\n";
            texto += "        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, titulo," + "\r\n";
            texto += "                texto);" + "\r\n";
            texto += "        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n";
            texto += "         LOG.warning(titulo + \" \" +texto);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String errorDialog() {
        try {

            String texto = "";
            texto += "    public static void errorDialog(String titulo, String texto) {" + "\r\n";
            texto += "        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR," + "\r\n";
            texto += "                titulo, texto);" + "\r\n";
            texto += "        RequestContext.getCurrentInstance().showMessageInDialog(message);" + "\r\n";
            texto += "         LOG.warning(titulo + \" \" +texto);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String converterDate() {
        try {

            String texto = "";
            texto += "    public static java.sql.Date converterDate(java.util.Date fecha) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            long lfecha = fecha.getTime();" + "\r\n";
            texto += "            java.sql.Date dtfecha = new java.sql.Date(lfecha);" + "\r\n";
            texto += "            return dtfecha;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            addErrorMessage(\"converterDate() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getUUID() {
        try {

            String texto = "";

            texto += "/**" + "\r\n";
            texto += "   genera id" + "\r\n";
            texto += "* " + "\r\n";
            texto += " * @return retorna un randomUUID automatico" + "\r\n";
            texto += "*/" + "\r\n";
            texto += "    public static String getUUID() {" + "\r\n";
            texto += " " + "\r\n";
            texto += "        UUID uuid = UUID.randomUUID();" + "\r\n";
            texto += "        return  uuid.toString().toLowerCase();" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getExtension() {
        try {

            String texto = "";

            texto += "/**" + "\r\n";
            texto += "     * getExtension()" + "\r\n";
            texto += "     * " + "\r\n";
            texto += "     * @param texto" + "\r\n";
            texto += "     * @return la extension de un nombre de archivo" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "    public static String getExtension(String texto){" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "             return texto.substring(texto.indexOf(\".\"),texto.length());" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "             JSFUtil.addErrorMessage(\"getExtension() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String copyFile() {
        try {

            String texto = "";

            texto += "/*" + "\r\n";
            texto += "     copia un archivo generalmente cuando se usa el fileupload" + "\r\n";
            texto += "     fileName: nombre del archivo a copiar" + "\r\n";
            texto += "     in: Es el InputStream" + "\r\n";
            texto += "     destination: ruta donde se guardara el archivo" + "\r\n";
            texto += "  " + "\r\n";
            texto += "     */" + "\r\n";
            texto += "    public static Boolean copyFile(String fileName, InputStream in, String destination) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            // write the inputStream to a FileOutputStream" + "\r\n";
            texto += "            OutputStream out = new FileOutputStream(new File(destination + fileName));" + "\r\n";
            texto += "" + "\r\n";
            texto += "            int read = 0;" + "\r\n";
            texto += "            byte[] bytes = new byte[1024];" + "\r\n";
            texto += "" + "\r\n";
            texto += "            while ((read = in.read(bytes)) != -1) {" + "\r\n";
            texto += "                out.write(bytes, 0, read);" + "\r\n";
            texto += "            }" + "\r\n";
            texto += "" + "\r\n";
            texto += "            in.close();" + "\r\n";
            texto += "            out.flush();" + "\r\n";
            texto += "            out.close();" + "\r\n";
            texto += "" + "\r\n";
            texto += "            return true;" + "\r\n";
            texto += "        } catch (IOException e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"copyFile() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return false;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getPathFotos() {
        try {

            String texto = "";

            texto += "public static String getPathFotos() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            String path = getPath() + \"resources/plagas/\";" + "\r\n";
            texto += " return path;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "" + "\r\n";
            texto += "            addErrorMessage(\"getPathFotosPlagas() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "   return texto;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"addErrorMessage()  \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return \"\";" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }
    
   private String getPath() {
        try {

            String texto = "";

texto += "/*" + "\r\n";
texto += "     devuelve el path " + "\r\n";
texto += "     */" + "\r\n";
texto += "    public static String getPath() {" + "\r\n";
texto += "        try {" + "\r\n";
texto += "            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()" + "\r\n";
texto += "                    .getExternalContext().getContext();" + "\r\n";
texto += "            return ctx.getRealPath(\"/\");" + "\r\n";
texto += "" + "\r\n";
texto += "        } catch (Exception e) {" + "\r\n";
texto += "" + "\r\n";
texto += "            addErrorMessage(\"getPath() \" + e.getLocalizedMessage());" + "\r\n";
texto += "        }" + "\r\n";
texto += "        return null;" + "\r\n";
texto += "" + "\r\n";
texto += "    }" + "\r\n";
   return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getPath()  " + e.getLocalizedMessage());
        }
        return "";
    }

 
}
