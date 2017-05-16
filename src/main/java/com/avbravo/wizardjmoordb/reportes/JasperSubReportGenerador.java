/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.reportes;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class JasperSubReportGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(JasperSubReportGenerador.class.getName());
    Integer x[] = {0, 111, 222, 333, 444}; // son las x para los titulos
    Integer width = 100;
    Integer height = 20;
    Integer fontSize = 9;

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;

    /**
     * Creates a new instance of Facade
     */
    // <editor-fold defaultstate="collapsed" desc="generar">  
    public void generar(Entidad entidad, String directorioreport) {
        try {
            //recorrer el entity para verificar que existan todos los EJB

            String name = Utilidades.letterToLower(entidad.getTabla());

            procesar(entidad.getTabla() + "_subreport" + ".jrxml", directorioreport + proyectoJEE.getSeparator() + entidad.getTabla().toLowerCase() + "_subreport" + ".jrxml", entidad, directorioreport + entidad.getTabla().toLowerCase() + "_subreport" + ".jasper");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }// </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="procesar">  
    private Boolean procesar(String archivo, String ruta, Entidad entidad, String pathJasper) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo, entidad);
                if (mySesion.getCompilarReporteaJasper().equals("si")) {
                    createJasper(ruta, pathJasper);
                }

            } else {
                if (mySesion.getCompilarReporteaJasper().equals("si")) {
                    Path pathJas = Paths.get(pathJasper);
                    if (Files.notExists(pathJas, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                        createJasper(ruta, pathJasper);
                    }
                }

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }
// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="createJasper">  
    private Boolean createJasper(String reportSource, String pathJasper) {
        try {

            JasperCompileManager.compileReportToFile(reportSource, pathJasper);

            return true;
        } catch (Exception e) {

            JSFUtil.addErrorMessage("createJasper() " + e.getLocalizedMessage());
        }
        return false;
    }// </editor-fold> 

    /**
     *
     * @param path
     * @param archivo
     * @param entidad
     * @return
     * @throws IOException
     */
    // <editor-fold defaultstate="collapsed" desc="crearFile"> 
    private Boolean crearFile(String path, String archivo, Entidad entidad) throws IOException {
        try {
            String name = Utilidades.letterToLower(entidad.getTabla());
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
                    fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\r\n");
                    fw.write("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"report name\" pageWidth=\"802\" pageHeight=\"555\" columnWidth=\"802\" leftMargin=\"0\" rightMargin=\"0\" topMargin=\"0\" bottomMargin=\"0\""
                            + " uuid=\"" + Utilidades.generateUniqueID() + "\" >" + "\r\n");
                    fw.write("        <property name=\"ireport.zoom\" value=\"1.0\"/>" + "\r\n");
                    fw.write("        <property name=\"ireport.x\" value=\"0\"/>" + "\r\n");
                    fw.write("        <property name=\"ireport.y\" value=\"0\"/>" + "\r\n");

                    fw.write("" + "\r\n");

                    for (Atributos atributos : entidad.getAtributosList()) {

                        name = atributos.getNombre().toLowerCase();
                        switch (atributos.getTipo()) {

                            case "Integer":
                            case "int":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Number\"/>" + "\r\n");
                            case "Double":
                            case "double":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Number\"/>" + "\r\n");
                                break;
                            case "String":
                            case "Character":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.String\"/>" + "\r\n");
                                break;

                            case "Date":
                                fw.write("        <field name=\"" + name + "\" class=\"java.util.Date\"/>" + "\r\n");
                                break;
                            case "Timestamp":
                                fw.write("        <field name=\"" + name + "\" class=\"java.sql.Timestamp\"/>" + "\r\n");
                                break;
                            case "Time":
                                fw.write("        <field name=\"" + name + "\" class=\"java.sql.Time\"/>" + "\r\n");
                                break;
                            case "Boolean":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Boolean\"/>" + "\r\n");
                                break;
                            case "BigInteger":
                            case "Long":
                            case "long":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Long\"/>" + "\r\n");
                                break;
                            case "byte[]":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Byte\"/>" + "\r\n");
                                break;
                            case "Float":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Float\"/>" + "\r\n");
                                break;

                            case "Short":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Short\"/>" + "\r\n");
                                break;
                            case "InputStream":
                                fw.write("        <field name=\"" + name + "\" class=\"java.io.InputStream\"/>" + "\r\n");
                                break;
                            case "Collection":
                                fw.write("        <field name=\"" + name + "\" class=\"java.util.Collection\"/>" + "\r\n");
                                break;
                            case "List":
                                fw.write("        <field name=\"" + name + "\" class=\"java.util.List\"/>" + "\r\n");
                                break;

                            case "Object":

                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Object\"/>" + "\r\n");
                                break;
                            default:
                                if (Utilidades.esTipoList(atributos.getTipo())) {
                                    //Es una lista
                                    fw.write("        <field name=\"" + name + "\" class=\"java.util.List\"/>" + "\r\n");
                                } else {
                                    fw.write("        <field name=\"" + name + "\" class=\"java.lang.Object\"/>" + "\r\n");
                                }

                        }

////getFieldByRowView()
//                            if (contador.equals(mySesion.getFieldByRowView())) {
//                                fw.write("                            </div>" + "\r\n");
//                                contador = 0;
//                            }
                    } //for
                    //si es impar la cantidad de datos y el numero de registros debe agregarse un dixv
//                    if ((fieldsAgregados.intValue() % 2 != 0 && mySesion.getFieldByRowView() % 2 == 0) || (fieldsAgregados.intValue() % 2 == 0 && mySesion.getFieldByRowView() % 2 != 0)) {
//                        fw.write("                       </div>" + "\r\n");
//
//                    }
                    fw.write("        <background>" + "\r\n");
                    fw.write("		<band splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("	</background>" + "\r\n");
                    fw.write("	<title>" + "\r\n");

fw.write("               <band height=\"25\" splitType=\"Stretch\">" + "\r\n");
fw.write("			<staticText>" + "\r\n");
fw.write("				<reportElement x=\"0\" y=\"0\" width=\"145\" height=\"25\" uuid=\"" + Utilidades.generateUniqueID() +"\"/>" + "\r\n");
fw.write("				<textElement>" + "\r\n");
fw.write("					<font size=\"12\" isBold=\"true\"/>" + "\r\n");
fw.write("				</textElement>" + "\r\n");
fw.write("				<text><![CDATA[" + entidad.getTabla().toUpperCase() +" ]]></text>" + "\r\n");
fw.write("			</staticText>" + "\r\n");
fw.write("		</band>" + "\r\n");
                    fw.write("	</title>" + "\r\n");
                    fw.write("      <pageHeader>" + "\r\n");
                    fw.write("		<band height=\"3\" splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("	</pageHeader>" + "\r\n");
                    fw.write("" + "\r\n");
                    /**
                     * columnHeader
                     */
                    fw.write("	<columnHeader>" + "\r\n");
                    fw.write("	    <band height=\"22\" splitType=\"Stretch\">" + "\r\n");

                    Integer contador = 0;

                    for (Atributos atributos : entidad.getAtributosList()) {
                        if (contador < 5) {
                            fw.write("            <staticText>" + "\r\n");
                            fw.write("                <reportElement x=\"" + x[contador] + "\" y=\"0\" width=\"" + this.width + "\" height=\"" + this.height + "\" uuid=\"" + Utilidades.generateUniqueID() + "\"/>" + "\r\n");
                            fw.write("                <text><![CDATA[" + Utilidades.letterToUpper(atributos.getNombre()) + "]]></text>" + "\r\n");
                            fw.write("            </staticText>" + "\r\n");
                            contador++;
                        }
                    }

                    fw.write("	    </band>" + "\r\n");
                    fw.write("	</columnHeader>" + "\r\n");
                    //Detalle
                    fw.write("	<detail>" + "\r\n");
                    fw.write("	    <band height=\"23\" splitType=\"Stretch\">" + "\r\n");
                    contador = 0;
                    for (Atributos atributos : entidad.getAtributosList()) {
                        if (contador < 5) {
                            if (atributos.getTipo().equals("Date")) {
                                fw.write("            <textField pattern=\"" + mySesion.getPatternDate() + "\">" + "\r\n");
                            } else {
                                if (atributos.getTipo().equals("Double")) {
                                    fw.write("            <textField pattern=\"###0.00\">" + "\r\n");
                                } else {
                                    fw.write("            <textField>" + "\r\n");
                                }

                            }

                            fw.write("                <reportElement x=\"" + x[contador] + "\" y=\"0\" width=\"" + this.width + "\" height=\"" + this.height + "\" uuid=\"" + Utilidades.generateUniqueID() + "\"/>" + "\r\n");
                            fw.write("                <textElement>" + "\r\n");
                            fw.write("                    <font size=\"" + this.fontSize + "\"/>" + "\r\n");
                            fw.write("                </textElement>" + "\r\n");
                            if (Utilidades.esTipoList(atributos.getTipo())) {
                                //Es una lista
                            }
                            if (!Utilidades.esTipoPojo(atributos.getTipo())) {

                                fw.write("                <textFieldExpression><![CDATA[$F{" + atributos.getNombre().toLowerCase() + "}]]></textFieldExpression>" + "\r\n");

                            } else {
                                fw.write("                <textFieldExpression><![CDATA[$F{" + atributos.getNombre().toLowerCase() + "}.toString()]]></textFieldExpression>" + "\r\n");
                            }

                            fw.write("            </textField>" + "\r\n");
                            contador++;
                        }
                    }

                    fw.write("	    </band>" + "\r\n");
                    fw.write("	</detail>" + "\r\n");

                    //Summary
                    fw.write("    <columnFooter>" + "\r\n");
                    fw.write("        <band height=\"2\" splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("    </columnFooter>" + "\r\n");

                    fw.write("    <summary>" + "\r\n");
                    fw.write("                <band height=\"18\" splitType=\"Stretch\">" + "\r\n");
                    fw.write("			<line>" + "\r\n");
                    fw.write("				<reportElement x=\"5\" y=\"8\" width=\"639\" height=\"1\" uuid=\"b6239680-0b79-41ce-a0aa-055c09eda3ed\"/>" + "\r\n");
                    fw.write("			</line>" + "\r\n");
                    fw.write("		</band>" + "\r\n");
                    fw.write("    </summary>" + "\r\n");
                    fw.write("</jasperReport>" + "\r\n");
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
// </editor-fold>

}
