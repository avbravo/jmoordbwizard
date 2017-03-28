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
public class EmailValidatorGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(EmailValidatorGenerador.class.getName());

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

            procesar("EmailValidator.java", proyectoJEE.getPathUtil() + "EmailValidator.java");

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
                if (!Utilidades.search(ruta, "public void validate")) {

                    Utilidades.add(ruta, "public class EmailValidator implements Validator", validate(), false);
//            
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

            if (!Utilidades.search(ruta, "import java.util.regex.Matcher;")) {
                Utilidades.add(ruta, "package", "import java.util.regex.Matcher;", false);
            }
            if (!Utilidades.search(ruta, "import java.util.regex.Pattern;")) {
                Utilidades.add(ruta, "package", "import java.util.regex.Pattern;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.application.FacesMessage;")) {
                Utilidades.add(ruta, "package", "import javax.faces.application.FacesMessage;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.component.UIComponent;")) {
                Utilidades.add(ruta, "package", "import javax.faces.component.UIComponent;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.component.html.HtmlInputText;")) {
                Utilidades.add(ruta, "package", "import javax.faces.component.html.HtmlInputText;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.context.FacesContext;")) {
                Utilidades.add(ruta, "package", "import javax.faces.context.FacesContext;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.validator.FacesValidator;")) {
                Utilidades.add(ruta, "package", "import javax.faces.validator.FacesValidator;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.validator.Validator;")) {
                Utilidades.add(ruta, "package", "import javax.faces.validator.Validator;", false);
            }
            if (!Utilidades.search(ruta, "import javax.faces.validator.ValidatorException;")) {
                Utilidades.add(ruta, "package", "import javax.faces.validator.ValidatorException;", false);
            }
           


        } catch (Exception e) {
            JSFUtil.addErrorMessage("EmailValidatorGenerador.generarImport() " + e.getLocalizedMessage());
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
                    fw.write("import java.util.regex.Matcher;" + "\r\n");
                    fw.write("import java.util.regex.Pattern;" + "\r\n");
                    fw.write("import javax.faces.application.FacesMessage;" + "\r\n");
                    fw.write("import javax.faces.component.UIComponent;" + "\r\n");
                    fw.write("import javax.faces.component.html.HtmlInputText;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.faces.validator.FacesValidator;" + "\r\n");
                    fw.write("import javax.faces.validator.Validator;" + "\r\n");
                    fw.write("import javax.faces.validator.ValidatorException;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("@FacesValidator(\"emailValidator\")" + "\r\n");
                    fw.write("public class EmailValidator implements Validator {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public void validate(FacesContext facesContext, UIComponent uIComponent, Object value) throws ValidatorException {" + "\r\n");
                    String slash="\\";
                    fw.write("    Pattern pattern = Pattern.compile(\""+slash+slash+"w+@"+slash+slash+"w+"+slash+slash+"."+slash+slash+"w+\");" + "\r\n");
                          //   Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");
                    
                    fw.write("" + "\r\n");
                    fw.write("        Matcher matcher = pattern.matcher((CharSequence) value);" + "\r\n");
                    fw.write("        HtmlInputText htmlInputText = (HtmlInputText) uIComponent;" + "\r\n");
                    fw.write("        String label;" + "\r\n");
                    fw.write("        if (htmlInputText.getLabel() == null || htmlInputText.getLabel().trim().equals(\"\")) {" + "\r\n");
                    fw.write("            label = htmlInputText.getId();" + "\r\n");
                    fw.write("        } else {" + "\r\n");
                    fw.write("            label = htmlInputText.getLabel();" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        if (!matcher.matches()) {" + "\r\n");
                    fw.write("            FacesMessage facesMessage = new FacesMessage(label + \": no es una direccion de email valida\");" + "\r\n");
                    fw.write("            throw new ValidatorException(facesMessage);" + "\r\n");
                    fw.write("        }" + "\r\n");
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

    private String validate() {
        try {

            String texto = "";

            texto += "@Override" + "\r\n";
            texto += "public void validate(FacesContext facesContext, UIComponent uIComponent, Object value) throws ValidatorException {" + "\r\n";
            texto += "        Pattern pattern = Pattern.compile(\"\\w+@\\w+\\.\\w+\");" + "\r\n";
            texto += "" + "\r\n";
            texto += "        Matcher matcher = pattern.matcher((CharSequence) value);" + "\r\n";
            texto += "        HtmlInputText htmlInputText = (HtmlInputText) uIComponent;" + "\r\n";
            texto += "        String label;" + "\r\n";
            texto += "        if (htmlInputText.getLabel() == null || htmlInputText.getLabel().trim().equals(\"\")) {" + "\r\n";
            texto += "            label = htmlInputText.getId();" + "\r\n";
            texto += "        } else {" + "\r\n";
            texto += "            label = htmlInputText.getLabel();" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        if (!matcher.matches()) {" + "\r\n";
            texto += "            FacesMessage facesMessage = new FacesMessage(label + \": no es una direccion de email valida\");" + "\r\n";
            texto += "            throw new ValidatorException(facesMessage);" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("addErrorMessage()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
