/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.datamodel;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoEJB;
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
public class DatamodelGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(DatamodelGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoEJB proyectoEJB;

    /**
     * Creates a new instance of Facade
     */
    public DatamodelGenerador() {
    }

    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            for (Entidad e : mySesion.getEntidadList()) {
                procesar(e, e.getTabla(), proyectoEJB.getPathDatamodel()+ e.getTabla() + "DataModel.java");
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(Entidad entidad, String archivo, String ruta) {
        try {
            
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, entidad, archivo);
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
    private Boolean crearFile(String path, Entidad entidad, String archivo) {
        try {
            String atributoprimario = "";
            Boolean esInteger = false;
            String entity = Utilidades.letterToLower(archivo);
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsPrimaryKey()) {
                    atributoprimario = Utilidades.letterToUpper(a.getNombre());
                    esInteger = a.getTipo().equals("Integer");
                }
            }
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
                    fw.write("package " + proyectoEJB.getPaquete() + ".datamodel;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".entity.*;" + "\r\n");
                    fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import javax.faces.model.ListDataModel;" + "\r\n");
                    fw.write("import org.primefaces.model.SelectableDataModel;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author " + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");

                    fw.write("public class " + archivo + "DataModel extends ListDataModel<" + archivo + "> implements SelectableDataModel<" + archivo + ">{" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public " + archivo + "DataModel() {" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public " + archivo + "DataModel(List<" + archivo + ">data) {" + "\r\n");
                    fw.write("        super(data);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");

                    fw.write("    public " + archivo + "  getRowData(String rowKey) {" + "\r\n");
                    fw.write("        List<" + archivo + "> " + entity + "List = (List<" + archivo + ">) getWrappedData();" + "\r\n");

                    fw.write("        for (" + archivo + " " + entity + " : " + entity + "List) {" + "\r\n");
                    fw.write("             if (" + entity + ".get" + atributoprimario + "().equals(rowKey)) {" + "\r\n");
                    fw.write("                 return " + entity + ";" + "\r\n");
                    fw.write("             }" + "\r\n");
                    fw.write("        }" + "\r\n");

                    fw.write("        return null;" + "\r\n");
                    fw.write("     }" + "\r\n");

                    fw.write("     @Override" + "\r\n");
                    fw.write("     public Object getRowKey(" + archivo + " " + entity + ") {" + "\r\n");
                    fw.write("         return " + entity + ".get" + atributoprimario + "();" + "\r\n");
                    fw.write("     }" + "\r\n");
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

}
