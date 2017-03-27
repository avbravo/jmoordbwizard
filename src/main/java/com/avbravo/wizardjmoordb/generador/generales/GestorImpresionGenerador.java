/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.generales;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Rutas;
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
public class GestorImpresionGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(GestorImpresionGenerador.class.getName());

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

            procesar("GestorImpresion.java", rutas.getPathGenerales() + "GestorImpresion.java");

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

                Utilidades.searchAdd(ruta, "JRBeanCollectionDataSource ds;", "public class GestorImpresion", false);
                Utilidades.searchAdd(ruta, "FacesContext facesContext;", "public class GestorImpresion", false);
                Utilidades.searchAdd(ruta, "ServletContext scontext;", "public class GestorImpresion", false);
                Utilidades.searchAdd(ruta, "JasperPrint jasperPrint;", "public class GestorImpresion", false);

                /**
                 * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public GestorImpresion", gestorImpresion(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "public String imprimir(List<?> t", imprimir(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "public String pdf(List<?> t", pdf(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "public String docx(List<?>", docx(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "public String xlsx(List<?> t,", xlsx(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "public String odt(List<?> t", odt(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "public String ppt(List<?> t", ppt(), "public class GestorImpresion ", false);
                Utilidades.addNotFoundMethod(ruta, "private void preparar(List<?> t", preparar(), "public class GestorImpresion ", false);


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
            Utilidades.searchAdd(ruta, "import com.lowagie.text.pdf.codec.Base64;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.ByteArrayOutputStream;", "package", false);
            Utilidades.searchAdd(ruta, "import java.io.IOException;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.HashMap;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.List;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.RequestScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.faces.context.FacesContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.servlet.ServletContext;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.servlet.ServletOutputStream;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.servlet.http.HttpServletResponse;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.JRException;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.JRExporterParameter;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.JasperExportManager;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.JasperFillManager;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.JasperPrint;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.export.JRPdfExporter;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.export.oasis.JROdtExporter;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;", "package", false);
            Utilidades.searchAdd(ruta, "import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;", "package", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("GestorImpresionGenerador.generarImport() " + e.getLocalizedMessage());
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
                    fw.write("import java.io.ByteArrayOutputStream;" + "\r\n");
                    fw.write("import java.io.IOException;" + "\r\n");
                    fw.write("import java.util.HashMap;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import javax.enterprise.context.RequestScoped;" + "\r\n");
                    fw.write("import javax.faces.context.FacesContext;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");
                    fw.write("import javax.servlet.ServletContext;" + "\r\n");
                    fw.write("import javax.servlet.ServletOutputStream;" + "\r\n");
                    fw.write("import javax.servlet.http.HttpServletResponse;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.JRException;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.JRExporterParameter;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.JasperExportManager;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.JasperFillManager;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.JasperPrint;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.export.JRPdfExporter;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.export.oasis.JROdtExporter;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;" + "\r\n");
                    fw.write("import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@RequestScoped" + "\r\n");
                    fw.write("public class GestorImpresion {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    JRBeanCollectionDataSource ds;" + "\r\n");
                    fw.write("    FacesContext facesContext;" + "\r\n");
                    fw.write("    ServletContext scontext;" + "\r\n");
                    fw.write("    JasperPrint jasperPrint;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("public GestorImpresion() {" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("  public String imprimir(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("         " + "\r\n");
                    fw.write("            preparar(t, ruta, hashmap);" + "\r\n");
                    fw.write("            ByteArrayOutputStream baos = new ByteArrayOutputStream();" + "\r\n");
                    fw.write("            JRPdfExporter exporter = new JRPdfExporter();" + "\r\n");
                    fw.write("            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n");
                    fw.write("           exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);" + "\r\n");
                    fw.write("            exporter.exportReport();" + "\r\n");
                    fw.write("            byte[] bytes = baos.toByteArray();" + "\r\n");
                    fw.write("            if (bytes != null && bytes.length > 0) {" + "\r\n");
                    fw.write("       " + "\r\n");
                    fw.write("                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();" + "\r\n");
                    fw.write("                response.setContentType(\"application/pdf\");" + "\r\n");
                    fw.write("                response.setHeader(\"Contentdisposition\", \"inline; filename=\\\"relatorioPorData.pdf\\\"\");" + "\r\n");
                    fw.write("                response.setContentLength(bytes.length);" + "\r\n");
                    fw.write("                ServletOutputStream outputStream = response.getOutputStream();" + "\r\n");
                    fw.write("                outputStream.write(bytes, 0, bytes.length);" + "\r\n");
                    fw.write("                outputStream.flush();" + "\r\n");
                    fw.write("                outputStream.close();" + "\r\n");
                    fw.write("        " + "\r\n");
                    fw.write("            }" + "\r\n");
                    fw.write("        } catch (JRException | IOException  e ) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String pdf(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            preparar(t, ruta, hashmap);" + "\r\n");
                    fw.write("            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n");
                    fw.write("            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n");
                    fw.write("                    \"attachment;filename=report.pdf\");" + "\r\n");
                    fw.write("           ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n");
                    fw.write("            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);" + "\r\n");
                    fw.write("            FacesContext.getCurrentInstance().responseComplete();" + "\r\n");
                    fw.write("        } catch (JRException | IOException e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String docx(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("       try {" + "\r\n");
                    fw.write("            preparar(t, ruta, hashmap);" + "\r\n");
                    fw.write("            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n");
                    fw.write("            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n");
                    fw.write("                    \"attachment;filename=report.docx\");" + "\r\n");
                    fw.write("            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n");
                    fw.write("            JRDocxExporter docxExporter = new JRDocxExporter();" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n");
                    fw.write("                    servletOutputStream);" + "\r\n");
                    fw.write("            docxExporter.exportReport();" + "\r\n");
                    fw.write("            FacesContext.getCurrentInstance().responseComplete();" + "\r\n");
                    fw.write("        } catch (JRException | IOException e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String xlsx(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            preparar(t, ruta, hashmap);" + "\r\n");
                    fw.write("            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n");
                    fw.write("            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n");
                    fw.write("                    \"attachment;filename=report.xlsx\");" + "\r\n");
                    fw.write("            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n");
                    fw.write("            JRXlsxExporter docxExporter = new JRXlsxExporter();" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n");
                    fw.write("                    servletOutputStream);" + "\r\n");
                    fw.write("            docxExporter.exportReport();" + "\r\n");
                    fw.write("            FacesContext.getCurrentInstance().responseComplete();" + "\r\n");
                    fw.write("        } catch (JRException | IOException e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String odt(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            preparar(t, ruta, hashmap);" + "\r\n");
                    fw.write("            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n");
                    fw.write("            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n");
                    fw.write("                    \"attachment;filename=report.odt\");" + "\r\n");
                    fw.write("            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n");
                    fw.write("            JROdtExporter docxExporter = new JROdtExporter();" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n");
                    fw.write("                    servletOutputStream);" + "\r\n");
                    fw.write("            docxExporter.exportReport();" + "\r\n");
                    fw.write("            FacesContext.getCurrentInstance().responseComplete();" + "\r\n");
                    fw.write("        } catch (JRException | IOException e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public String ppt(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            preparar(t, ruta, hashmap);" + "\r\n");
                    fw.write("            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n");
                    fw.write("            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n");
                    fw.write("                    \"attachment;filename=report.pptx\");" + "\r\n");
                    fw.write("            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n");
                    fw.write("            JRPptxExporter docxExporter = new JRPptxExporter();" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n");
                    fw.write("            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n");
                    fw.write("                    servletOutputStream);" + "\r\n");
                    fw.write("            docxExporter.exportReport();" + "\r\n");
                    fw.write("            FacesContext.getCurrentInstance().responseComplete();" + "\r\n");
                    fw.write("        } catch (JRException | IOException e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private void preparar(List<?> t, String ruta, HashMap hashmap) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            ds = new JRBeanCollectionDataSource(t);" + "\r\n");
                    fw.write("            facesContext = FacesContext.getCurrentInstance();" + "\r\n");
                    fw.write("            facesContext.responseComplete();" + "\r\n");
                    fw.write("            scontext = (ServletContext) facesContext.getExternalContext().getContext();" + "\r\n");
                    fw.write("            jasperPrint = JasperFillManager.fillReport(scontext.getRealPath(ruta), hashmap, ds);" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n");
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

    private String gestorImpresion() {
        try {

            String texto = "";
            texto += "public GestorImpresion() {" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("gestorImpresion()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String imprimir() {
        try {

            String texto = "";
            texto += "  public String imprimir(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "         " + "\r\n";
            texto += "            preparar(t, ruta, hashmap);" + "\r\n";
            texto += "            ByteArrayOutputStream baos = new ByteArrayOutputStream();" + "\r\n";
            texto += "            JRPdfExporter exporter = new JRPdfExporter();" + "\r\n";
            texto += "            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n";
            texto += "           exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);" + "\r\n";
            texto += "            exporter.exportReport();" + "\r\n";
            texto += "            byte[] bytes = baos.toByteArray();" + "\r\n";
            texto += "            if (bytes != null && bytes.length > 0) {" + "\r\n";
            texto += "       " + "\r\n";
            texto += "                HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();" + "\r\n";
            texto += "                response.setContentType(\"application/pdf\");" + "\r\n";
            texto += "                response.setHeader(\"Contentdisposition\", \"inline; filename=\\\"relatorioPorData.pdf\\\"\");" + "\r\n";
            texto += "                response.setContentLength(bytes.length);" + "\r\n";
            texto += "                ServletOutputStream outputStream = response.getOutputStream();" + "\r\n";
            texto += "                outputStream.write(bytes, 0, bytes.length);" + "\r\n";
            texto += "                outputStream.flush();" + "\r\n";
            texto += "                outputStream.close();" + "\r\n";
            texto += "        " + "\r\n";
            texto += "            }" + "\r\n";
            texto += "        } catch (JRException | IOException  e ) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("imprimir()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String pdf() {
        try {

            String texto = "";
            texto += "    public String pdf(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            preparar(t, ruta, hashmap);" + "\r\n";
            texto += "            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n";
            texto += "            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n";
            texto += "                    \"attachment;filename=report.pdf\");" + "\r\n";
            texto += "           ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n";
            texto += "            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);" + "\r\n";
            texto += "            FacesContext.getCurrentInstance().responseComplete();" + "\r\n";
            texto += "        } catch (JRException | IOException e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("gestorImpresion()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String docx() {
        try {

            String texto = "";
            texto += "    public String docx(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "       try {" + "\r\n";
            texto += "            preparar(t, ruta, hashmap);" + "\r\n";
            texto += "            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n";
            texto += "            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n";
            texto += "                    \"attachment;filename=report.docx\");" + "\r\n";
            texto += "            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n";
            texto += "            JRDocxExporter docxExporter = new JRDocxExporter();" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n";
            texto += "                    servletOutputStream);" + "\r\n";
            texto += "            docxExporter.exportReport();" + "\r\n";
            texto += "            FacesContext.getCurrentInstance().responseComplete();" + "\r\n";
            texto += "        } catch (JRException | IOException e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("docx()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String xlsx() {
        try {

            String texto = "";

            texto += "    public String xlsx(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            preparar(t, ruta, hashmap);" + "\r\n";
            texto += "            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n";
            texto += "            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n";
            texto += "                    \"attachment;filename=report.xlsx\");" + "\r\n";
            texto += "            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n";
            texto += "            JRXlsxExporter docxExporter = new JRXlsxExporter();" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n";
            texto += "                    servletOutputStream);" + "\r\n";
            texto += "            docxExporter.exportReport();" + "\r\n";
            texto += "            FacesContext.getCurrentInstance().responseComplete();" + "\r\n";
            texto += "        } catch (JRException | IOException e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("xlsx()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String odt() {
        try {

            String texto = "";
            texto += "    public String odt(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            preparar(t, ruta, hashmap);" + "\r\n";
            texto += "            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n";
            texto += "            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n";
            texto += "                    \"attachment;filename=report.odt\");" + "\r\n";
            texto += "            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n";
            texto += "            JROdtExporter docxExporter = new JROdtExporter();" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n";
            texto += "                    servletOutputStream);" + "\r\n";
            texto += "            docxExporter.exportReport();" + "\r\n";
            texto += "            FacesContext.getCurrentInstance().responseComplete();" + "\r\n";
            texto += "        } catch (JRException | IOException e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("odt()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String ppt() {
        try {

            String texto = "";
            texto += "    public String ppt(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            preparar(t, ruta, hashmap);" + "\r\n";
            texto += "            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();" + "\r\n";
            texto += "            httpServletResponse.addHeader(\"Contentdisposition\"," + "\r\n";
            texto += "                    \"attachment;filename=report.pptx\");" + "\r\n";
            texto += "            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();" + "\r\n";
            texto += "            JRPptxExporter docxExporter = new JRPptxExporter();" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);" + "\r\n";
            texto += "            docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM," + "\r\n";
            texto += "                    servletOutputStream);" + "\r\n";
            texto += "            docxExporter.exportReport();" + "\r\n";
            texto += "            FacesContext.getCurrentInstance().responseComplete();" + "\r\n";
            texto += "        } catch (JRException | IOException e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("odt()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String preparar() {
        try {

            String texto = "";
            texto += "    private void preparar(List<?> t, String ruta, HashMap hashmap) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            ds = new JRBeanCollectionDataSource(t);" + "\r\n";
            texto += "            facesContext = FacesContext.getCurrentInstance();" + "\r\n";
            texto += "            facesContext.responseComplete();" + "\r\n";
            texto += "            scontext = (ServletContext) facesContext.getExternalContext().getContext();" + "\r\n";
            texto += "            jasperPrint = JasperFillManager.fillReport(scontext.getRealPath(ruta), hashmap, ds);" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(e.getMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("odt()  " + e.getLocalizedMessage());
        }
        return "";
    }
}
