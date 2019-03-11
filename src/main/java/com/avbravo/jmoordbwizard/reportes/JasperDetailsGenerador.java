/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.reportes;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoJEE;
import com.avbravo.jmoordbwizard.beans.Atributos;
import com.avbravo.jmoordbwizard.beans.Entidad;
import com.avbravo.jmoordbwizard.utilidades.Utilidades;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import net.sf.jasperreports.engine.JasperCompileManager;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class JasperDetailsGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(JasperDetailsGenerador.class.getName());
    Integer x[] = {0, 110, 270, 375}; // son las x para los titulos
    Integer width = 100;
    Integer height = 20;
    Integer fontSize = 9;
    Integer detailsBandheight = 125;

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    JasperSubReportGenerador jasperSubReportGenerador;

    /**
     * Creates a new instance of Facade
     */
    // <editor-fold defaultstate="collapsed" desc="generar">  
    public void generar() {
        try {

            //recorrer el entity para verificar que existan todos los EJB
            mySesion.getEntidadList().forEach((entidad) -> {
                String name = Utilidades.letterToLower(entidad.getTabla());
                String directorioreport = proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "reportes" + proyectoJEE.getSeparator() + name + proyectoJEE.getSeparator();
                procesar("details" + ".jrxml", directorioreport + proyectoJEE.getSeparator() + "details" + ".jrxml", entidad, directorioreport + "details" + ".jasper");
            });

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }// </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="procesar">  
    private Boolean procesar(String archivo, String ruta, Entidad entidad, String pathJasper) {
        try {
            String name = Utilidades.letterToLower(entidad.getTabla());
            for (Atributos atributos : entidad.getAtributosList()) {
                if (Utilidades.esTipoList(atributos.getTipo())) {
                    
//crear subreportes
                    //String nameRelational = Utilidades.letterToLower(atributos.getTipo());
                    String nameRelational = Utilidades.convertiraNombreEntityElTipoList(atributos.getTipo()).trim();

                    mySesion.getEntidadList().stream().filter((entidad2) -> (nameRelational.equals(entidad2.getTabla().trim()))).forEachOrdered((entidad2) -> {
                        String directorioreport = proyectoJEE.getPathMainWebappResources() + proyectoJEE.getSeparator() + "reportes" + proyectoJEE.getSeparator() + name + proyectoJEE.getSeparator();
                        jasperSubReportGenerador.generar(entidad2, directorioreport);
                    });

                }
            }

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
                    fw.write("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"report name\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" uuid=\"63ad1a18-4d4e-44e1-97f8-c1744418d899\">" + "\r\n");
                    fw.write("        <property name=\"ireport.zoom\" value=\"1.0\"/>" + "\r\n");
                    fw.write("        <property name=\"ireport.x\" value=\"0\"/>" + "\r\n");
                    fw.write("        <property name=\"ireport.y\" value=\"144\"/>" + "\r\n");
                    if (Utilidades.numerodeList(entidad) > 0) {
                        fw.write("	<parameter name=\"SUBREPORT_DIR\" class=\"java.lang.String\" isForPrompting=\"false\">" + "\r\n");
                        fw.write("		<defaultValueExpression><![CDATA[$P{SUBREPORT_DIR}]]></defaultValueExpression>" + "\r\n");
                        fw.write("	</parameter>" + "\r\n");
                    }

                    fw.write("" + "\r\n");

                    for (Atributos atributos : entidad.getAtributosList()) {

                        name = atributos.getNombre().toLowerCase();
                        switch (atributos.getTipo()) {

                            case "Integer":
                            case "int":
                                fw.write("        <field name=\"" + name + "\" class=\"java.lang.Number\"/>" + "\r\n");
                                break;
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

                    } //for

                    fw.write("        <background>" + "\r\n");
                    fw.write("		<band splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("	</background>" + "\r\n");
                    fw.write("	<title>" + "\r\n");
                    fw.write("		<band height=\"9\" splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("	</title>" + "\r\n");
                    fw.write("      <pageHeader>" + "\r\n");
                    fw.write("		<band height=\"55\" splitType=\"Stretch\">" + "\r\n");
                    fw.write("			<staticText>" + "\r\n");
                    fw.write("				<reportElement x=\"240\" y=\"18\" width=\"63\" height=\"20\" uuid=\"9e43e6b2-7c78-4094-bd32-bbce7a7ec79c\"/>" + "\r\n");
                    fw.write("				<textElement>" + "\r\n");
                    fw.write("					<font isBold=\"true\"/>" + "\r\n");
                    fw.write("				</textElement>" + "\r\n");
                    fw.write("				<text><![CDATA[" + entidad.getTabla().toUpperCase() + "]]></text>" + "\r\n");
                    fw.write("			</staticText>" + "\r\n");
                    fw.write("                        <staticText>" + "\r\n");
                    fw.write("				<reportElement x=\"373\" y=\"18\" width=\"34\" height=\"20\" uuid=\"14f76f42-77f2-4c4c-9596-6ec59fba0e85\"/>" + "\r\n");
                    fw.write("				<textElement>" + "\r\n");
                    fw.write("					<font isBold=\"true\"/>" + "\r\n");
                    fw.write("				</textElement>" + "\r\n");
                    fw.write("				<text><![CDATA[Fecha:]]></text>" + "\r\n");
                    fw.write("			</staticText>" + "\r\n");
                    fw.write("			<textField pattern=\"yyyy\">" + "\r\n");
                    fw.write("				<reportElement x=\"411\" y=\"18\" width=\"100\" height=\"20\" uuid=\"ac376dee-b970-4156-8eeb-e121ce7a91a3\"/>" + "\r\n");
                    fw.write("				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>" + "\r\n");
                    fw.write("			</textField>" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("		</band>" + "\r\n");
                    fw.write("	</pageHeader>" + "\r\n");
                    fw.write("" + "\r\n");
                    /**
                     * columnHeader
                     */
                    fw.write("	<columnHeader>" + "\r\n");
                    fw.write("	    <band height=\"29\" splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("	</columnHeader>" + "\r\n");
                    Integer contador = 0;

//Calcular el tamaño del height
                    Integer tamano = entidad.getAtributosList().size();

                    Integer numeroList = Utilidades.numerodeList(entidad);
                    tamano -= numeroList;

                    if (Utilidades.isImpar(tamano)) {
                        tamano++;
                    }
                    tamano = tamano / 2;
                    Integer heightSubreportes =mySesion.getSubReporteAltoRow() * numeroList;
                    detailsBandheight = (tamano *  mySesion.getReportesAltoRow()) + heightSubreportes;
                    if (detailsBandheight > mySesion.getReporteMaximoBandaDetails()) {
                        detailsBandheight = mySesion.getReporteMaximoBandaDetails();
                    }

                    //Detalle
                    fw.write("	<detail>" + "\r\n");
                    fw.write("	    <band height=\"" + detailsBandheight + "\" splitType=\"Stretch\">" + "\r\n");
                    contador = 0;
                    Integer y = 0;
                    for (Atributos atributos : entidad.getAtributosList()) {
                        if (contador == 4) {

                            contador = 0;
                            y += 25;
                        }

                        if (Utilidades.esTipoList(atributos.getTipo())) {
                            //Es una lista
//                            y += 25;
//                            fw.write("                        <subreport>" + "\r\n");
//                            fw.write("				<reportElement x=\"0\" y=\"" + y + "\" width=\"525\" height=\"75\" uuid=\"" + Utilidades.generateUniqueID() + "\"/>" + "\r\n");
//                            fw.write("				<dataSourceExpression><![CDATA[new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{" + atributos.getNombre().toLowerCase() + "})]]></dataSourceExpression>" + "\r\n");
//                            fw.write("				<subreportExpression><![CDATA[$P{SUBREPORT_DIR} + \"" + atributos.getNombre().toLowerCase() + "_subreport.jasper\"]]></subreportExpression>" + "\r\n");
//                            fw.write("			</subreport>" + "\r\n");
                        } else {
                            fw.write("            <staticText>" + "\r\n");
                            fw.write("                <reportElement x=\"" + x[contador] + "\" y=\"" + y + "\" width=\"" + this.width + "\" height=\"" + this.height + "\" uuid=\"" + Utilidades.generateUniqueID() + "\"/>" + "\r\n");
                            fw.write("                <text><![CDATA[" + Utilidades.letterToUpper(atributos.getNombre()) + "]]></text>" + "\r\n");
                            fw.write("            </staticText>" + "\r\n");

                            if (atributos.getTipo().equals("Date")) {
                                fw.write("            <textField pattern=\"" + mySesion.getPatternDate() + "\">" + "\r\n");
                            } else {
                                if (atributos.getTipo().equals("Double")) {
                                    fw.write("            <textField pattern=\"###0.00\">" + "\r\n");
                                } else {
                                    fw.write("            <textField>" + "\r\n");
                                }

                            }

                            fw.write("                <reportElement x=\"" + x[contador + 1] + "\" y=\"" + y + "\" width=\"" + this.width + "\" height=\"" + this.height + "\" uuid=\"" + Utilidades.generateUniqueID() + "\"/>" + "\r\n");
                            fw.write("                <textElement>" + "\r\n");
                            fw.write("                    <font size=\"" + this.fontSize + "\"/>" + "\r\n");
                            fw.write("                </textElement>" + "\r\n");

                            if (!Utilidades.esTipoPojo(atributos.getTipo())) {

                                fw.write("                <textFieldExpression><![CDATA[$F{" + atributos.getNombre().toLowerCase() + "}]]></textFieldExpression>" + "\r\n");

                            } else {
                                fw.write("                <textFieldExpression><![CDATA[$F{" + atributos.getNombre().toLowerCase() + "}.toString()]]></textFieldExpression>" + "\r\n");
                            }

                            fw.write("            </textField>" + "\r\n");
                        }

                        contador += 2;

                    }//
Integer row=26;
                    for (Atributos atributos : entidad.getAtributosList()) {
                        //Es una lista
                          if (Utilidades.esTipoList(atributos.getTipo())) {
                               y += row;
                        fw.write("                        <subreport>" + "\r\n");
                        fw.write("				<reportElement x=\"0\" y=\"" + y + "\" width=\"525\" height=\"75\" uuid=\"" + Utilidades.generateUniqueID() + "\"/>" + "\r\n");
                        fw.write("				<dataSourceExpression><![CDATA[new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{" + atributos.getNombre().toLowerCase() + "})]]></dataSourceExpression>" + "\r\n");
                        fw.write("				<subreportExpression><![CDATA[$P{SUBREPORT_DIR} + \"" + atributos.getNombre().toLowerCase() + "_subreport.jasper\"]]></subreportExpression>" + "\r\n");
                        fw.write("			</subreport>" + "\r\n");
                        row = 80;
                          }
                       
                    }

                    fw.write("	    </band>" + "\r\n");
                    fw.write("	</detail>" + "\r\n");

                    //Summary
                    fw.write("    <columnFooter>" + "\r\n");
                    fw.write("        <band height=\"45\" splitType=\"Stretch\"/>" + "\r\n");
                    fw.write("    </columnFooter>" + "\r\n");
                    fw.write("    <pageFooter>" + "\r\n");
                    fw.write("        <band height=\"54\" splitType=\"Stretch\">" + "\r\n");
                    fw.write("            <staticText>" + "\r\n");
                    fw.write("                <reportElement x=\"407\" y=\"17\" width=\"36\" height=\"20\" uuid=\"3d031e27-b1b6-47c8-bee2-2105f7a564b2\"/>" + "\r\n");
                    fw.write("                <textElement>" + "\r\n");
                    fw.write("                    <font isBold=\"true\"/>" + "\r\n");
                    fw.write("                </textElement>" + "\r\n");
                    fw.write("                <text><![CDATA[Pag.:]]></text>" + "\r\n");
                    fw.write("            </staticText>" + "\r\n");
                    fw.write("            <textField>" + "\r\n");
                    fw.write("                <reportElement x=\"446\" y=\"17\" width=\"100\" height=\"20\" uuid=\"e0da8ee5-bb5c-4545-8272-3c5e0c2c00eb\"/>" + "\r\n");
                    fw.write("                <textFieldExpression><![CDATA[$V{PAGE_NUMBER}]]></textFieldExpression>" + "\r\n");
                    fw.write("            </textField>" + "\r\n");
                    fw.write("        </band>" + "\r\n");
                    fw.write("    </pageFooter>" + "\r\n");
                    fw.write("    <summary>" + "\r\n");
                    fw.write("        <band height=\"42\" splitType=\"Stretch\"/>" + "\r\n");
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
// <editor-fold defaultstate="collapsed" desc="esTipoList"> 

}
