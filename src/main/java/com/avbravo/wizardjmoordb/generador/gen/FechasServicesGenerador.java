/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.gen;

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
public class FechasServicesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(FechasServicesGenerador.class.getName());

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

            procesar("FechasServices.java", proyectoJEE.getPathServices() + "FechasServices.java");

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
                Utilidades.addNotFoundMethod(ruta, "public java.sql.Date converterDate(java.util.Date fecha) {", converterDate(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public java.util.Date getFechaActual() {", getFechaActual(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Integer getAnioActual() {", getAnioActual(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Integer getMesActual() {", getMesActual(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Integer getMesDeUnaFecha(Date date) {", getMesDeUnaFecha(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Integer getAnioDeUnaFecha(Date date) {", getAnioDeUnaFecha(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Integer getDiaDeUnaFecha(Date date) {", getDiaDeUnaFecha(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Integer getDiaActual() {", getDiaActual(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Date getPrimeraFechaAnio() {", getPrimeraFechaAnio(), "public class FechasServices {", false);

                Utilidades.addNotFoundMethod(ruta, "public Date getUltimaFechaAnio() {", getUltimaFechaAnio(), "public class FechasServices {", false);
                Utilidades.addNotFoundMethod(ruta, "public LocalTime getTiempo() {", getTiempo(), "public class FechasServices {", false);
                Utilidades.addNotFoundMethod(ruta, "public Integer getDiaDeSemana(Date date) {", getDiaDeSemana(), "public class FechasServices {", false);
                Utilidades.addNotFoundMethod(ruta, "public Integer diasEntreFechas(Date fechaMayor, Date fechaMenor) {", diasEntreFechas(), "public class FechasServices {", false);

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

            Utilidades.searchAdd(ruta, "import " + proyectoJEE.getPaquete() + ".generales.JSFUtil;", "package", false);
            Utilidades.searchAdd(ruta, "import java.time.LocalDate;", "package", false);
            Utilidades.searchAdd(ruta, "import java.time.LocalTime;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.Calendar;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.Date;", "package", false);
            Utilidades.searchAdd(ruta, "import java.util.logging.Logger;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.ejb.Stateless;", "package", false);

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
                    fw.write("package " + proyectoJEE.getPaquete() + ".services;" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("import " + proyectoJEE.getPaquete() + ".generales.JSFUtil;" + "\r\n");
                    fw.write("import java.time.LocalDate;" + "\r\n");
                    fw.write("import java.time.LocalTime;" + "\r\n");
                    fw.write("import java.util.Calendar;" + "\r\n");
                    fw.write("import java.util.Date;" + "\r\n");
                    fw.write("import java.util.logging.Logger;" + "\r\n");
                    fw.write("import javax.ejb.Stateless;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Stateless" + "\r\n");
                    fw.write("public class FechasServices {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public java.sql.Date converterDate(java.util.Date fecha) {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            long lfecha = fecha.getTime();" + "\r\n");
                    fw.write("            java.sql.Date dtfecha = new java.sql.Date(lfecha);" + "\r\n");
                    fw.write("            return dtfecha;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"converterDate() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return null;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public java.util.Date getFechaActual() {" + "\r\n");
                    fw.write("        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n");
                    fw.write("        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n");
                    fw.write("        return new java.sql.Date(mydate.getTime());" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer getAnioActual() {" + "\r\n");
                    fw.write("        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n");
                    fw.write("        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n");
                    fw.write("        return ca.get(Calendar.YEAR);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer getMesActual() {" + "\r\n");
                    fw.write("        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n");
                    fw.write("        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n");
                    fw.write("        return ca.get(Calendar.MONTH);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer getMesDeUnaFecha(Date date) {" + "\r\n");
                    fw.write("        Calendar calendar = Calendar.getInstance();" + "\r\n");
                    fw.write("        calendar.setTime(date);" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        int anio = calendar.get(Calendar.YEAR);" + "\r\n");
                    fw.write("        int mes = calendar.get(Calendar.MONTH) + 1;" + "\r\n");
                    fw.write("        int dia = calendar.get(Calendar.DAY_OF_MONTH);" + "\r\n");
                    fw.write("        return mes;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer getAnioDeUnaFecha(Date date) {" + "\r\n");
                    fw.write("        Calendar calendar = Calendar.getInstance();" + "\r\n");
                    fw.write("        calendar.setTime(date);" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        int anio = calendar.get(Calendar.YEAR);" + "\r\n");
                    fw.write("        int mes = calendar.get(Calendar.MONTH) + 1;" + "\r\n");
                    fw.write("        int dia = calendar.get(Calendar.DAY_OF_MONTH);" + "\r\n");
                    fw.write("        return anio;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer getDiaDeUnaFecha(Date date) {" + "\r\n");
                    fw.write("        Calendar calendar = Calendar.getInstance();" + "\r\n");
                    fw.write("        calendar.setTime(date);" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        int anio = calendar.get(Calendar.YEAR);" + "\r\n");
                    fw.write("        int mes = calendar.get(Calendar.MONTH) + 1;" + "\r\n");
                    fw.write("        int dia = calendar.get(Calendar.DAY_OF_MONTH);" + "\r\n");
                    fw.write("        return dia;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Integer getDiaActual() {" + "\r\n");
                    fw.write("        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n");
                    fw.write("        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n");
                    fw.write("        return ca.get(Calendar.DATE);" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("    /**" + "\r\n");
                    fw.write("     * devuelve el numero de dia de la semana. domingo=1" + "\r\n");
                    fw.write("     *" + "\r\n");
                    fw.write("     * @return" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("  public Integer getDiaDeSemana(Date date) {" + "\r\n");
                    fw.write("        Calendar calendar = Calendar.getInstance();" + "\r\n");
                    fw.write("        calendar.setTime(date);" + "\r\n");
                    fw.write("        " + "\r\n");
                    fw.write("        int dia = calendar.get(Calendar.DAY_OF_WEEK);" + "\r\n");
                    fw.write("        return dia;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    /**" + "\r\n");
                    fw.write("     * devuelve la primera fecha del año" + "\r\n");
                    fw.write("     *" + "\r\n");
                    fw.write("     * @return" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public Date getPrimeraFechaAnio() {" + "\r\n");
                    fw.write("        LocalDate now = LocalDate.now();//# 2015-11-23" + "\r\n");
                    fw.write("        Integer year = now.getYear();" + "\r\n");
                    fw.write("        Integer month = 1;" + "\r\n");
                    fw.write("        Integer day = 1;" + "\r\n");
                    fw.write("        LocalDate firstDay = LocalDate.of(year, month, day);" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        Date date = java.sql.Date.valueOf(firstDay);" + "\r\n");
                    fw.write("        return date;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    /**" + "\r\n");
                    fw.write("     * devuelve la ultima fecha del año" + "\r\n");
                    fw.write("     *" + "\r\n");
                    fw.write("     * @return" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public Date getUltimaFechaAnio() {" + "\r\n");
                    fw.write("        LocalDate now = LocalDate.now();//# 2015-11-23" + "\r\n");
                    fw.write("        Integer year = now.getYear();" + "\r\n");
                    fw.write("        Integer month = 12;" + "\r\n");
                    fw.write("        Integer day = 31;" + "\r\n");
                    fw.write("        LocalDate firstDay = LocalDate.of(year, month, day);" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        Date date = java.sql.Date.valueOf(firstDay);" + "\r\n");
                    fw.write("        return date;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    /**" + "\r\n");
                    fw.write("     * devuelve la hora,minutos, segundos actual" + "\r\n");
                    fw.write("     *" + "\r\n");
                    fw.write("     * @return" + "\r\n");
                    fw.write("     */" + "\r\n");

                    fw.write("    public LocalTime getTiempo() {" + "\r\n");
                    fw.write("        LocalTime now = LocalTime.now();" + "\r\n");
                    fw.write("        //Integer hora = now.getHour();" + "\r\n");
                    fw.write("        //Integer minutos = now.getMinute();" + "\r\n");
                    fw.write("        //Integer segundos = now.getSecond();" + "\r\n");
                    fw.write("        return now;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    /**" + "\r\n");
                    fw.write("     * devuelve la cantidad de dias entre dos fechas" + "\r\n");
                    fw.write("     *" + "\r\n");
                    fw.write("     * @return" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("  public Integer diasEntreFechas(Date fechaMayor, Date fechaMenor) {" + "\r\n");
                    fw.write("       int d=0;" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("             long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);" + "\r\n");
                    fw.write("        d = (int) dias;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("             JSFUtil.addErrorMessage(\"diasEntreFechas() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("       " + "\r\n");
                    fw.write("        return d;" + "\r\n");
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

    private String converterDate() {
        try {

            String texto = "";
            texto += "    public java.sql.Date converterDate(java.util.Date fecha) {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            long lfecha = fecha.getTime();" + "\r\n";
            texto += "            java.sql.Date dtfecha = new java.sql.Date(lfecha);" + "\r\n";
            texto += "            return dtfecha;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"converterDate() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return null;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("converterDate()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getFechaActual() {
        try {

            String texto = "";

            texto += "    public java.util.Date getFechaActual() {" + "\r\n";
            texto += "        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n";
            texto += "        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n";
            texto += "        return new java.sql.Date(mydate.getTime());" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getFechaActual()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getAnioActual() {
        try {

            String texto = "";
            texto += "    public Integer getAnioActual() {" + "\r\n";
            texto += "        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n";
            texto += "        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n";
            texto += "        return ca.get(Calendar.YEAR);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAnioActual()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getMesActual() {
        try {

            String texto = "";
            texto += "    public Integer getMesActual() {" + "\r\n";
            texto += "        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n";
            texto += "        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n";
            texto += "        return ca.get(Calendar.MONTH);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getMesActual()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getMesDeUnaFecha() {
        try {

            String texto = "";
            texto += "    public Integer getMesDeUnaFecha(Date date) {" + "\r\n";
            texto += "        Calendar calendar = Calendar.getInstance();" + "\r\n";
            texto += "        calendar.setTime(date);" + "\r\n";
            texto += "" + "\r\n";
            texto += "        int anio = calendar.get(Calendar.YEAR);" + "\r\n";
            texto += "        int mes = calendar.get(Calendar.MONTH) + 1;" + "\r\n";
            texto += "        int dia = calendar.get(Calendar.DAY_OF_MONTH);" + "\r\n";
            texto += "        return mes;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getMesDeUnaFecha()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getAnioDeUnaFecha() {
        try {

            String texto = "";
            texto += "    public Integer getAnioDeUnaFecha(Date date) {" + "\r\n";
            texto += "        Calendar calendar = Calendar.getInstance();" + "\r\n";
            texto += "        calendar.setTime(date);" + "\r\n";
            texto += "" + "\r\n";
            texto += "        int anio = calendar.get(Calendar.YEAR);" + "\r\n";
            texto += "        int mes = calendar.get(Calendar.MONTH) + 1;" + "\r\n";
            texto += "        int dia = calendar.get(Calendar.DAY_OF_MONTH);" + "\r\n";
            texto += "        return anio;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getAnioDeUnaFecha()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getDiaDeUnaFecha() {
        try {

            String texto = "";
            texto += "    public Integer getDiaDeUnaFecha(Date date) {" + "\r\n";
            texto += "        Calendar calendar = Calendar.getInstance();" + "\r\n";
            texto += "        calendar.setTime(date);" + "\r\n";
            texto += "" + "\r\n";
            texto += "        int anio = calendar.get(Calendar.YEAR);" + "\r\n";
            texto += "        int mes = calendar.get(Calendar.MONTH) + 1;" + "\r\n";
            texto += "        int dia = calendar.get(Calendar.DAY_OF_MONTH);" + "\r\n";
            texto += "        return dia;" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDiaDeUnaFecha()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getDiaActual() {
        try {

            String texto = "";
            texto += "    public Integer getDiaActual() {" + "\r\n";
            texto += "        java.util.Calendar ca = java.util.Calendar.getInstance();" + "\r\n";
            texto += "        java.sql.Date mydate = new java.sql.Date(ca.getTimeInMillis());" + "\r\n";
            texto += "        return ca.get(Calendar.DATE);" + "\r\n";
            texto += "    }" + "\r\n";
            texto += "" + "\r\n";
            texto += "    /**" + "\r\n";
            texto += "     * devuelve la primera fecha del año" + "\r\n";
            texto += "     *" + "\r\n";
            texto += "     * @return" + "\r\n";
            texto += "     */" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDiaActual()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getPrimeraFechaAnio() {
        try {

            String texto = "";
            texto += "    public Date getPrimeraFechaAnio() {" + "\r\n";
            texto += "        LocalDate now = LocalDate.now();//# 2015-11-23" + "\r\n";
            texto += "        Integer year = now.getYear();" + "\r\n";
            texto += "        Integer month = 1;" + "\r\n";
            texto += "        Integer day = 1;" + "\r\n";
            texto += "        LocalDate firstDay = LocalDate.of(year, month, day);" + "\r\n";
            texto += "" + "\r\n";
            texto += "        Date date = java.sql.Date.valueOf(firstDay);" + "\r\n";
            texto += "        return date;" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getPrimeraFechaAnio()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getUltimaFechaAnio() {
        try {

            String texto = "";
            texto += "" + "\r\n";
            texto += "    /**" + "\r\n";
            texto += "     * devuelve la ultima fecha del año" + "\r\n";
            texto += "     *" + "\r\n";
            texto += "     * @return" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "    public Date getUltimaFechaAnio() {" + "\r\n";
            texto += "        LocalDate now = LocalDate.now();//# 2015-11-23" + "\r\n";
            texto += "        Integer year = now.getYear();" + "\r\n";
            texto += "        Integer month = 12;" + "\r\n";
            texto += "        Integer day = 31;" + "\r\n";
            texto += "        LocalDate firstDay = LocalDate.of(year, month, day);" + "\r\n";
            texto += "" + "\r\n";
            texto += "        Date date = java.sql.Date.valueOf(firstDay);" + "\r\n";
            texto += "        return date;" + "\r\n";
            texto += "" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getUltimaFechaAnio()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getTiempo() {
        try {
            String texto = "";
            texto += "    public LocalTime getTiempo() {" + "\r\n";
            texto += "        LocalTime now = LocalTime.now();" + "\r\n";
            texto += "        //Integer hora = now.getHour();" + "\r\n";
            texto += "        //Integer minutos = now.getMinute();" + "\r\n";
            texto += "        //Integer segundos = now.getSecond();" + "\r\n";
            texto += "        return now;" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getUltimaFechaAnio()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String getDiaDeSemana() {
        try {
            String texto = "";
            texto += "    /**" + "\r\n";
            texto += "     * devuelve el numero de dia de la semana. domingo=1" + "\r\n";
            texto += "     *" + "\r\n";
            texto += "     * @return" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "  public Integer getDiaDeSemana(Date date) {" + "\r\n";
            texto += "        Calendar calendar = Calendar.getInstance();" + "\r\n";
            texto += "        calendar.setTime(date);" + "\r\n";
            texto += "        " + "\r\n";
            texto += "        int dia = calendar.get(Calendar.DAY_OF_WEEK);" + "\r\n";
            texto += "        return dia;" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDiaDeSemana()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String diasEntreFechas() {
        try {
            String texto = "";
            texto += "    /**" + "\r\n";
            texto += "     * devuelve la cantidad de dias entre dos fechas" + "\r\n";
            texto += "     *" + "\r\n";
            texto += "     * @return" + "\r\n";
            texto += "     */" + "\r\n";
            texto += "  public Integer diasEntreFechas(Date fechaMayor, Date fechaMenor) {" + "\r\n";
            texto += "       int d=0;" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "             long diferenciaEn_ms = fechaMayor.getTime() - fechaMenor.getTime();" + "\r\n";
            texto += "" + "\r\n";
            texto += "        long dias = diferenciaEn_ms / (1000 * 60 * 60 * 24);" + "\r\n";
            texto += "        d = (int) dias;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "             JSFUtil.addErrorMessage(\"diasEntreFechas() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "       " + "\r\n";
            texto += "        return d;" + "\r\n";
            texto += "    }" + "\r\n";

            return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("diasEntreFechas() " + e.getLocalizedMessage());
        }
        return "";
    }

}
