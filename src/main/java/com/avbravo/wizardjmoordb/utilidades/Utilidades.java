/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.utilidades;

import com.avbravo.wizardjmoordb.JSFUtil;
import static com.avbravo.wizardjmoordb.generador.gen.EntidadGenerador.DEFAULT_CHARSET;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author avbravoserver
 */
public class Utilidades {

    private static final Logger LOG = Logger.getLogger(Utilidades.class.getName());
    private static String persistenceunit = "";

    public static boolean mkdir(String ruta) {
        try {
            File file = new File(ruta);
            if (file.exists()) {
                //existe
                return true;
            } else {
                                boolean creado = file.mkdir();
                if (creado == true) {
                    // se creo exitosamente
                    return true;
                } else {
                    // no se pudo crear
                    return false;
                }
            }
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("crearDirectorio() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static boolean searchDirectorie(String ruta) {
        try {
            File file = new File(ruta);
            if (file.exists()) {
                //existe
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("encontrarDirectorio() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public void deleteDirectory(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
    }

    public boolean eliminarDirectorio(String ruta) {
        try {
            File file = new File(ruta);
            if (!file.exists()) {
                //existe
                return false;
            } else {
                deleteDirectory(file);
                return true;
            }
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("eliminarDirectorio() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static String letterToUpper(String texto) {

        try {

            texto = texto.trim();
            int largo = texto.length();
            if (largo <= 0) {
                return texto;
            }
            String letra = texto.substring(0, 1);

            texto = letra.toUpperCase() + texto.substring(1);
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("convertirLetraMayuscula() " + ex.getLocalizedMessage());
        }
        return texto;
    }

    /**
     * ConvertirLetraMinuscula
     *
     * @param s_cadena
     * @param caracter
     * @return
     */
    public static String letterToLower(String texto) {

        try {

            texto = texto.trim();
            int largo = texto.length();
            if (largo <= 0) {
                return texto;
            }
            String letra = texto.substring(0, 1);

            texto = letra.toLowerCase() + texto.substring(1);
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("convertirLetraMinuscula() " + ex.getLocalizedMessage());
        }
        return texto;
    }

    /**
     * inserta texto antes de la } que cierra el archivo
     *
     * @param rutaArchivo
     * @param search
     * @param textoInsertar
     * @param antes
     * @return
     */
    public static boolean addBeforeEnd(String rutaArchivo, String search, String textoInsertar) {
        try {
            if (textoInsertar.equals("")) {
                //   JSFUtil.addErrorMessage(" Texto a insertar vacio");
                // LOG.info("texto a insertar vacio <ruta> : "+rutaArchivo);
                return false;
            }
            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";
            boolean encontrado = false;
            Integer contador = 0;
            Integer fila = 0;
            Integer filacierraarchivo = 0;
            // cuenta las  que tiene el archivo la ultima es la que indica

            while ((line = reader.readLine()) != null) {
                fila++;
                if (line.indexOf(search) != -1) {
                    contador++;
                    filacierraarchivo = fila;
                }
            }

            if (contador == 0) {
                JSFUtil.addErrorMessage(" No tiene { que cierra el archivo");
                JSFUtil.addErrorMessage(" No tiene { que cierra el archivo");
                return false;
            }
            reader.close();
            /**
             *
             */
            BufferedReader reader2 = new BufferedReader(new FileReader(file));
            Integer f = 0;
            while ((line = reader2.readLine()) != null) {
                f++;
//                LOG.info("--->f (" + f + ")  " + line);
                if (f < filacierraarchivo) {
                    oldtext += line + "\r\n";
                } else if (f == filacierraarchivo) {
                    oldtext += textoInsertar + "\r\n" + line + "\r\n";
                }

                //insertarlo antes
            }

            FileWriter writer = new FileWriter(rutaArchivo);
            writer.write(oldtext);
            writer.close();

            return true;

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("insertarTextoAntesFinalArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /*
     * Actualiza el archivo una cadena con la que le especifiquemos ejemplo
     * ActualizarTextoArchivo("/home/avbravo/Documentos/etiquetas.properties",
     * "nombre", "name"); Actualiza en el archivo nombre por name
     */
    public static boolean update(String rutaArchivo, String search, String reemplazo) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";

            while ((line = reader.readLine()) != null) {

                oldtext += line + "\r\n";

            }
            reader.close();

            if (oldtext.indexOf(search) != -1) {
                String newtext = oldtext.replaceAll(search, reemplazo);

                FileWriter writer = new FileWriter(rutaArchivo);
                writer.write(newtext);
                writer.close();

                return true;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("actualizaTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /*
    busca texto en un archivo
     */
    public static boolean search(String rutaArchivo, String search) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    encontrado = true;
                }
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("encontrarTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static boolean buscaryAgregarSiNoExiste_Old(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(textoInsertar) != -1) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                add(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /*
    busca si no l0o encuentra lo agrega
     */
    public static boolean searchAdd(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                textoInsertar = textoInsertar.trim();
                if (line.length() >= textoInsertar.length()) {

                    if (line.substring(0, textoInsertar.length()).equals(textoInsertar)) {
                        encontrado = true;
                    }
                }

            }
            if (!encontrado) {
                add(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }
    /*
    agrega sin introducir nueva linea
    */
    public static boolean searchAddWithoutLine(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                textoInsertar = textoInsertar.trim();
                if (line.length() >= textoInsertar.length()) {

                    if (line.substring(0, textoInsertar.length()).equals(textoInsertar)) {
                        encontrado = true;
                    }
                }

            }
            if (!encontrado) {
                addWithoutLine(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }
    /*
    busca si lo encuentra lo reemplaza
     */
    public static boolean searchReplace(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                textoInsertar = textoInsertar.trim();
                if (line.length() >= textoInsertar.length()) {

                    if (line.substring(0, textoInsertar.length()).equals(textoInsertar)) {
                        encontrado = true;
                    }
                }

            }
            if (!encontrado) {
                add(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /*
    se usa para validador de roles
     */
    public static boolean searchAddRolCaseValidadorRoles(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes, String rol) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                textoInsertar = textoInsertar.trim();
                if (line.length() >= textoInsertar.length()) {

                    if (line.substring(0, textoInsertar.length()).equals(textoInsertar)) {
                        encontrado = true;
                    }
                }

            }
            if (!encontrado) {
//                textoInsertar+="\r\n"+"case " + "\"" + Utilidades.convertirLetraMayuscula(rol) + "\": " + "\r\n";
                textoInsertar += "\r\n" + "                rol" + Utilidades.letterToUpper(rol) + ".activar();" + "\r\n";
                textoInsertar += "                 break;" + "\r\n";
                add(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static boolean searchAddDefaultValidadorRoles(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                textoInsertar = textoInsertar.trim();
                if (line.length() >= textoInsertar.length()) {

                    if (line.substring(0, textoInsertar.length()).equals(textoInsertar)) {
                        encontrado = true;
                    }
                }

            }
            if (!encontrado) {
//                textoInsertar+="\r\n"+"case " + "\"" + Utilidades.convertirLetraMayuscula(rol) + "\": " + "\r\n";
                textoInsertar += "\r\n" + "                    menuBeans.habilitarTodo(false);" + "\r\n";
                textoInsertar += "                    ok = Boolean.FALSE;" + "\r\n";
                textoInsertar += "                    JSFUtil.warningDialog(rf.getMensajeArb(\"warning.title\")," + "\r\n";
                textoInsertar += "                            rf.getMensajeArb(\"info.sinrolasignado\"));" + "\r\n";
                add(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /*
    agrega un @Inject antes de la linea que se le pasa se usa para objetos con @Inject
    por Ejemplo
    @Inject
    U
     */
    public static boolean searchAddTextAndInject(String rutaArchivo, String textoInsertar, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                textoInsertar = textoInsertar.trim();
                if (line.length() >= textoInsertar.length()) {

                    if (line.substring(0, textoInsertar.length()).equals(textoInsertar)) {
                        encontrado = true;
                    }
                }

            }

            if (!encontrado) {
                add(rutaArchivo, textoBaseUbicar, textoInsertar, antes);
                add(rutaArchivo, textoInsertar, "@Inject", true);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExiste() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /**
     * agrega un metodo si este no es encontrado
     *
     * @param rutaArchivo
     * @param titulometodo
     * @param textoMetodo
     * @param textoBaseUbicar
     * @param antes
     * @return
     */
    public static boolean addNotFoundMethod(String rutaArchivo, String titulometodo, String textoMetodo, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(titulometodo) != -1) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                add(rutaArchivo, textoBaseUbicar, textoMetodo, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExisteMetodo() " + ex.getLocalizedMessage());
        }
        return false;
    }
    public static boolean addNotFoundMethodWithOutLine(String rutaArchivo, String titulometodo, String textoMetodo, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(titulometodo) != -1) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                addWithoutLine(rutaArchivo, textoBaseUbicar, textoMetodo, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExisteMetodo() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static boolean addDependencies(String rutaArchivo, String titulometodo, String textoMetodo, String textoBaseUbicar, Boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(titulometodo) != -1) {
                    encontrado = true;
                }
            }
            if (!encontrado) {
                insertarTextoArchivoPom(rutaArchivo, textoBaseUbicar, textoMetodo, antes);
            }
            return encontrado;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("buscaryAgregarSiNoExisteMetodo() " + ex.getLocalizedMessage());
        }
        return false;
    }


    /*
     * Inserta texto en el archivo antes o despues de la linea donde se
     * encuentre la cadena search el parametro antes = true : indica que se
     * insertara antes antes = false : indica que se insertara despues
     * InsertarTextoArchivo("/home/avbravo/Documentos/etiquetas.properties",
     * "name", "email=\"@ww\"", false)
     */
    public static boolean add(String rutaArchivo, String search, String textoInsertar, boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "\r\n", oldtext = "\r\n";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    if (antes) {
                        //insertarlo antes
                        oldtext += textoInsertar + "\r\n" + line + "\r\n";
                    } else {
                        //insertar despues
                        oldtext += line + "\r\n" + textoInsertar + "\r\n";
                    }

                    encontrado = true;

                } else if (!line.equals("")) {
                    oldtext += line + "\r\n";
                }

            }
            reader.close();

            if (encontrado) {
                FileWriter writer = new FileWriter(rutaArchivo);
                writer.write(oldtext);
                writer.close();

                return true;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("insertarTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }
    public static boolean addWithoutLine(String rutaArchivo, String search, String textoInsertar, boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "\r\n", oldtext = "";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    if (antes) {
                        //insertarlo antes
                        oldtext += textoInsertar + "\r\n" + line + "\r\n";
                    } else {
                        //insertar despues
                        oldtext += line + "\r\n" + textoInsertar + "\r\n";
                    }

                    encontrado = true;

                } else if (!line.equals("")) {
                    oldtext += line + "\r\n";
                }

            }
            reader.close();

            if (encontrado) {
                FileWriter writer = new FileWriter(rutaArchivo);
                writer.write(oldtext);
                writer.close();

                return true;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("insertarTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }
    /*
     * reemplaza el texto en el archivo
     * InsertarTextoArchivo("/home/avbravo/Documentos/etiquetas.properties",
     * "name", "email=\"@ww\"", false)
     */
    public static boolean replace(String rutaArchivo, String search, String textoNuevo) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "\r\n", oldtext = "\r\n";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    
                  
                        oldtext += textoNuevo + "\r\n" ;
                  

                    encontrado = true;

                } else if (!line.equals("")) {
                    oldtext += line + "\r\n";
                }

            }
            reader.close();

            if (encontrado) {
                FileWriter writer = new FileWriter(rutaArchivo);
                writer.write(oldtext);
                writer.close();

                return true;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("insertarTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }
    public static boolean replaceWithoutLine(String rutaArchivo, String search, String textoNuevo) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "\r\n", oldtext = "";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    
                  
                        oldtext += textoNuevo + "\r\n" ;
                  

                    encontrado = true;

                } else if (!line.equals("")) {
                    oldtext += line + "\r\n";
                }

            }
            reader.close();

            if (encontrado) {
                FileWriter writer = new FileWriter(rutaArchivo);
                writer.write(oldtext);
                writer.close();

                return true;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("insertarTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static boolean insertarTextoArchivoPom(String rutaArchivo, String search, String textoInsertar, boolean antes) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "\r\n", oldtext = "";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    if (antes) {
                        //insertarlo antes
                        oldtext += textoInsertar + "\r\n" + line + "\r\n";
                    } else {
                        //insertar despues
                        oldtext += line + "\r\n" + textoInsertar + "\r\n";
                    }

                    encontrado = true;

                } else if (!line.equals("")) {
                    oldtext += line + "\r\n";
                }

            }
            reader.close();

            if (encontrado) {
                FileWriter writer = new FileWriter(rutaArchivo);
                writer.write(oldtext);
                writer.close();

                return true;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("insertarTextoArchivo() " + ex.getLocalizedMessage());
        }
        return false;
    }

    /**
     * inserta texto antes de la } que cierra el archivo
     *
     * @param rutaArchivo
     * @param search
     * @param textoInsertar
     * @param antes
     * @return
     */
//    public static boolean insertarTextoAntesFinalArchivo(String rutaArchivo, String search, String textoInsertar) {
//        try {
//            LOG.info("=============================");
//            LOG.info("rutaArchivo" + rutaArchivo);
//            File file = new File(rutaArchivo);
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line = "", oldtext = "";
//            boolean encontrado = false;
//            Integer contador = 0;
//            Integer fila = 0;
//            Integer filacierraarchivo = 0;
//            // cuenta las  que tiene el archivo la ultima es la que indica
//
//            while ((line = reader.readLine()) != null) {
//                fila++;
//                if (line.indexOf(search) != -1) {
//                    contador++;
//                    filacierraarchivo = fila;
//                }
//            }
//            LOG.info("contador "+contador + " fila "+fila + " filacierre"+filacierraarchivo);
//            if (contador == 0) {
//               JSFUtil.addErrorMessage(" No tiene { que cierra el archivo");
//                return false;
//            }
//reader.close();
///**
// * 
// */
//   BufferedReader reader2 = new BufferedReader(new FileReader(file));
//            Integer f = 0;
//            while ((line = reader2.readLine()) != null) {
//                f++;
//                LOG.info("--->f ("+ f + ")  "+line);
//                if (f < filacierraarchivo) {
//                    oldtext += line + "\r\n";
//                } else if (f == filacierraarchivo) {
//                    oldtext += textoInsertar + "\r\n" + line + "\r\n";
//                } 
//
//                //insertarlo antes
//            }
//            
//
//            FileWriter writer = new FileWriter(rutaArchivo);
//            writer.write(oldtext);
//            writer.close();
//
//            return true;
//
//        } catch (Exception ex) {
//           JSFUtil.addErrorMessage("insertarTextoAntesFinalArchivo() " + ex.getLocalizedMessage());
//        }
//        return false;
//    }

    /*
     * DescomponerCadena
     */
    public static ArrayList<String> descomponerCadena(String texto, String separador) {
        ArrayList<String> lista = new ArrayList<String>();
        try {
            lista.removeAll(lista);
            StringTokenizer tk = new StringTokenizer(texto, separador); // Cambia aquí el separador
            while (tk.hasMoreTokens()) {

                lista.add(tk.nextToken());
            }
        } catch (Exception ex) {

            JSFUtil.addErrorMessage("descomponerCadena() " + ex.getLocalizedMessage());
        }
        return lista;
    }

    /*
     * nombre de columna
     */
    public static String nombreColumna(String texto) {
        try {

            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return "";
                }
                if (texto.indexOf(")") == -1) {
                    return "";
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf("nombre=") != -1) {
                } else {
                }
                if (texto.indexOf(",isNoNulo=") != -1) {
                } else {
                }
                texto = texto.substring(texto.indexOf("nombre=") + 7, texto.indexOf(",isNoNulo="));
                texto = texto.replace("\"", "");
                texto = texto.trim();
                return texto;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("nombreColumna() " + ex.getLocalizedMessage());

        }
        return "";
    }

    /*
     *
     */
    public static Boolean isNoNuloColumna(String texto) {
        try {

            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return false;
                }
                if (texto.indexOf(")") == -1) {
                    return false;
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf(",isNoNulo=") != -1) {
                } else {
                }
                if (texto.indexOf(", tipo =") != -1) {
                } else {
                }
                texto = texto.substring(texto.indexOf(",isNoNulo=") + 10, texto.indexOf(", tipo ="));
                texto = texto.replace("\"", "");
                if (texto.equals("true")) {
                    return true;
                }
                return false;
            }

        } catch (Exception ex) {
            JSFUtil.addErrorMessage("isNoNuloColumna() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static String tipoColumna(String texto) {
        try {
            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return "";
                }
                if (texto.indexOf(")") == -1) {
                    return "";
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf(", tipo =") != -1) {
                } else {
                }
                if (texto.indexOf(", mysql=") != -1) {
                } else {
                }
                texto = texto.substring(texto.indexOf(", tipo =") + 8, texto.indexOf(", mysql="));
                texto = texto.replace("\"", "");
                texto = texto.trim();
                return texto;
            }

        } catch (Exception ex) {

            JSFUtil.addErrorMessage("tipoColumna() " + ex.getLocalizedMessage());
        }
        return "";
    }

    public static String mysqlColumna(String texto) {
        try {

            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return "";
                }
                if (texto.indexOf(")") == -1) {
                    return "";
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf(", mysql=") != -1) {
                } else {
                }
                if (texto.indexOf(",tamano =") != -1) {
                } else {
                }
                texto = texto.substring(texto.indexOf(", mysql=") + 8, texto.indexOf(",tamano ="));
                texto = texto.replace("\"", "");
                texto = texto.trim();
                return texto;
            }

        } catch (Exception ex) {

            JSFUtil.addErrorMessage("mysqlColumna() " + ex.getLocalizedMessage());
        }
        return "";
    }

    public static Integer tamanoColumna(String texto) {
        try {
            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return 0;
                }
                if (texto.indexOf(")") == -1) {
                    return 0;
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf(",tamano =") != -1) {
                } else {
                }
                if (texto.indexOf(", digitosDecimales=") != -1) {
                } else {
                }
//                texto = texto.substring(texto.indexOf(",tamano =") + 8, texto.indexOf(", digitosDecimales="));
                texto = texto.substring(texto.indexOf(",tamano =") + 9, texto.indexOf(", digitosDecimales="));
                texto = texto.replace("\"", "");
                return Integer.parseInt(texto);
            }

        } catch (Exception ex) {

            JSFUtil.addErrorMessage("tamanoColumna()\ntexto: " + texto + "\n" + ex.getLocalizedMessage());

        }
        return 0;
    }

    public static Integer digitosDecimalesColumnas(String texto) {
        try {

            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return 0;
                }
                if (texto.indexOf(")") == -1) {
                    return 0;
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf(", digitosDecimales=") != -1) {
                } else {
                }
                if (texto.indexOf(", comentario=") != -1) {
                } else {
                }
                texto = texto.substring(texto.indexOf(", digitosDecimales=") + 19, texto.indexOf(", comentario="));
                texto = texto.replace("\"", "");
                return Integer.parseInt(texto);
            }

        } catch (Exception ex) {

            JSFUtil.addErrorMessage("digitosDecimalesColumnas()" + ex.getLocalizedMessage());
        }
        return 0;
    }

    public static String comentarioColumna(String texto) {
        try {

            if (texto.indexOf("@Columna(") != - 1) {
                texto.replace("@Columna(", "");
                if (texto.indexOf("(") == -1) {
                    return "";
                }
                if (texto.indexOf(")") == -1) {
                    return "";
                }
                texto = texto.substring(texto.indexOf("("), texto.indexOf(")"));
                if (texto.indexOf(", comentario=") != -1) {
                } else {
                }
                if (texto.indexOf(", is_autoincrementable=") != -1) {
                } else {
                }
                texto = texto.substring(texto.indexOf(", comentario=") + 13, texto.indexOf(", is_autoincrementable="));
                texto = texto.replace("\"", "");
                texto = texto.trim();
                return texto;
            }

        } catch (Exception ex) {

            JSFUtil.addErrorMessage("comentarioColumna() " + ex.getLocalizedMessage());
        }
        return "";
    }

    public static boolean actualizaCssLayout(String rutaArchivo) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "", oldtext = "";
            boolean control = false;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.equals("#top a:link, #top a:visited {")) {
                    control = true;
                } else if (control) {
                    if (line.equals("color: white;")) {
                        line = "color: blue;";
                        control = false;
                    }
                }
                oldtext += line + "\r\n";

            }
            reader.close();

            FileWriter writer = new FileWriter(rutaArchivo);
            writer.write(oldtext);
            writer.close();

            return true;

        } catch (Exception ex) {

            JSFUtil.addErrorMessage("actualizaCssLayout()() " + ex.getLocalizedMessage());
        }
        return false;
    }

    public static String getLineaArchivo(String rutaArchivo, String search) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String cadena = "";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {
                    encontrado = true;
                    cadena = line;
                }
            }
            return cadena;
        } catch (Exception ex) {
            JSFUtil.addErrorMessage("Utilidades.getLineaArchivo() " + ex.getLocalizedMessage());

        }
        return "";
    }

    public static DefaultTableModel limpiarModelo(DefaultTableModel modelo) {
        if (modelo != null) {
            while (modelo.getRowCount() > 0) {
                modelo.removeRow(0);
            }
        }
        return modelo;
    }

    public static String cortarTexto(String texto) {
        try {

            Integer pos = texto.indexOf("=");
            if (pos != -1) {
                return texto.substring(pos + 1, texto.length());
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("cortarText() " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * devuelve el nombre del paquete desde un path
     *
     * @return
     */
    public static String getNombrePaqueteFromPath(String ruta) {
        try {

            ruta = ruta.substring(ruta.indexOf("java/") + 5, ruta.length());
            ruta = ruta.replace("/", ".");

            if (ruta.substring(ruta.length() - 1, ruta.length()).equals(".")) {
                ruta = ruta.substring(0, ruta.length() - 1);
            }

            return ruta;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getNombrePaqueteFromPath()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * devuelve el nombre del paquete desde un path
     *
     * @return
     */
    public static String convertirPaqueteToPath(String paquete) {
        try {

//            ruta = ruta.substring(ruta.indexOf("java/") + 5, ruta.length());
            paquete = paquete.replace(".", "/");
            return paquete;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getNombrePaqueteFromPath()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * obtiene el nombre del paquete y devuelve el nombre del proyecto
     *
     * @param nombrepaquete
     * @return
     */
    public static String getNombreProyectoFromPath(String nombrepaquete) {
        try {

            nombrepaquete = nombrepaquete.substring(nombrepaquete.lastIndexOf("/") + 1, nombrepaquete.length());

            return nombrepaquete;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getNombreProyectoFromPath()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * devuelve el path del paquete quitando los path de otros directorios por
     * ejemplo /home/usuario/proyecto/src/main/java/com/avbravo/mimaterial/
     * devuelve /com/avbravo/mimaterial
     *
     * @return
     */
    public static String getPathPaqueteFromAbsolutePath(String ruta) {
        try {

            ruta = ruta.substring(ruta.indexOf("java/") + 5, ruta.length());

            ruta = ruta.substring(0, ruta.length() - 1);

            return ruta;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getPathPaqueteFromAbsolutePath()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * devuelve el list con los directorios path del paquete quitando los path
     * de otros directorios por ejemplo
     * /home/usuario/proyecto/src/main/java/com/avbravo/mimaterial/ devuelve
     * list<String> (0)-> com (1)-> avbravo (2)-> mimaterial
     *
     * @return
     */
    public static List<String> getListPathPaqueteFromAbsolutePath(String ruta) {
        List<String> list = new ArrayList<>();
        try {

            ruta = ruta.substring(ruta.indexOf("java/") + 5, ruta.length());

            ruta = ruta.substring(0, ruta.length() - 1);
            String[] splited = ruta.split("/");
            for (String s : splited) {
                LOG.info("descomponiendo " + s);
                list.add(s);
            }
//            for (String s : list) {
//                JSFUtil.addErrorMessage("(*) " + s);
//            }
            return list;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getPathPaqueteFromAbsolutePath()" + e.getLocalizedMessage());
        }
        return list;
    }

    /**
     * recibe una cadena con roles separados por coma
     * Administrador,Test,Desarrollador y devuelve un list<String> en el que
     * cada elemento representa un rol
     *
     * @param roles
     * @return
     */
    public static List<String> descomponerRoles(String roles) {
        List<String> list = new ArrayList<>();
        try {
//le quito los espacios en blanco
            roles = roles.replaceAll(" ", "");
            String[] splited = roles.split(",");
            for (String s : splited) {

                list.add(s);
            }

            return list;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("descomponerRoles()" + e.getLocalizedMessage());
        }
        return list;
    }

    public static List<String> descomponerMenu(String roles) {
        List<String> list = new ArrayList<>();
        try {
//le quito los espacios en blanco
            roles = roles.replaceAll(" ", "");
            String[] splited = roles.split(",");
            for (String s : splited) {

                list.add(s);
            }

            return list;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("descomponerMenu()" + e.getLocalizedMessage());
        }
        return list;
    }

    /**
     * Obtiente el nombre de la entidad de persistencia
     *
     */
    public static String getPersistenceUnit(String ruta) {
        try {
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {

                JSFUtil.addWarningMessage("No existe el archivo persistence.xml en este proyecto" );
               
                return "";
            }
            String name = "";
            Files.lines(path, Charset.forName(DEFAULT_CHARSET)).forEach(line -> {
//           JSFUtil.addErrorMessage("Caracter number por line: " + line.length());
                if (line.indexOf("<persistence-unit name") != -1) {
                    line = line.replace("\"", "");
                    line = line.replace("<persistence-unit name=", "");
                    line = line.replace("transaction-type=JTA>", "");
                    line = line.trim();
                    persistenceunit = line;

                }
            });

            return persistenceunit;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getPersistenceUnith()" + e.getLocalizedMessage());
        }
        return "";
    }

    public static String getFirstLetter(String texto) {
        try {

            return texto = texto.substring(0, 1);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getFirstLetter" + e.getLocalizedMessage());
        }
        return "";
    }
    /*
    devuelve el ultimo caracter
    */
    public static String getLastLetter(String texto) {
        try {
if(texto.length()<=0){
    return "";
}

           
            return texto = texto.substring(texto.length()-1, texto.length());

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getLastLetter()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * verifica si existe el archivo
     *
     * @param ruta
     * @return
     */
    public static Boolean existeArchivo(String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                return false;
            }
            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("existeArchivo() " + e.getLocalizedMessage());
        }
        return false;
    }

    /**
     *
     * @param ruta
     * @return
     */
    public static String getDirectorioMetaInf(String ruta) {
        try {
            String separator = java.nio.file.FileSystems.getDefault().getSeparator();
            ruta = ruta.substring(0, ruta.indexOf("java/"));
            ruta += "resources" + separator + "META-INF";

            return ruta;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDirectorioMetaInf()" + e.getLocalizedMessage());
        }
        return "";
    }
//    public static String getDirectorioWebInf(String ruta) {
//        try {
//            String separator = java.nio.file.FileSystems.getDefault().getSeparator();
//            ruta = ruta.substring(0, ruta.indexOf("java/"));
//            ruta += "resources" + separator + "WEB-INF";
//
//            return ruta;
//
//        } catch (Exception e) {
//            JSFUtil.addErrorMessage("getDirectorioWebInf()" + e.getLocalizedMessage());
//        }
//        return "";
//    }

    /**
     *
     * @param ruta
     * @return
     */
    public static String getDirectorioMainJava(String ruta) {
        try {
            String separator = java.nio.file.FileSystems.getDefault().getSeparator();
            ruta = ruta.substring(0, ruta.indexOf("java/"));
            ruta += "resources" + separator;

            return ruta;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDirectorioMainJava()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     *
     * @param ruta
     * @return
     */
    public static String getDirectorioMainResources(String ruta) {
        try {
            String separator = java.nio.file.FileSystems.getDefault().getSeparator();
            ruta = ruta.substring(0, ruta.indexOf("java/"));
            ruta += "resources" + separator;

            return ruta;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDirectorioMainResources()" + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     *
     * @param ruta
     * @return
     */
    public static String getDirectorioMainWebapp(String ruta) {
        try {
            String separator = java.nio.file.FileSystems.getDefault().getSeparator();
            ruta = ruta.substring(0, ruta.indexOf("java/"));
            ruta += "webapp" + separator;

            return ruta;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getDirectorioMainWebapp()" + e.getLocalizedMessage());
        }
        return "";
    }

    public static Boolean isJavaType(String tipo) {
        try {
            //Object son para tipos POLYGON Y OTROS DATOS
            tipo = tipo.trim();
            switch (tipo) {
                case "String":
                case "Integer":
                case "int":
                case "Double":
                case "double":
                case "Date":
                case "Long":
                case "long":
                case "byte[]":
                case "BigInteger":
                case "Float":
                case "Short":
                case "Character":
                case "Boolean":
                case "Object":
                    return true;
                default:
                    return false;

            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("isJavaType()" + e.getLocalizedMessage());
        }
        return false;
    }

    /*
    convierte de mysql a java
     */
    public static String mysqlToJava(String tipomysql) {
        String tipojava = tipomysql;
        try {
            switch (tipomysql) {
                case "Integer":
                case "int":
                    tipojava = "Integer";
                    break;
                case "Double":
                case "double":
                    tipojava = "Double";
                    break;
                case "Long":
                case "long":
                    tipojava = "Long";
                    break;
                case "byte[]":
                    tipojava ="byte[]";
                    break;
                case "BigInteger":
                    tipojava="BigInteger";
                    break;
                case "Float":
                    tipojava="Float";
                case "Short":
                    tipojava="Short";
                case "Character":
                    tipojava="Character";
                case "Boolean":
                    tipojava="Boolean";
                case "Object":
                    tipojava="Object";
                    break;
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("mysqlToJava() " + e.getLocalizedMessage());
        }
        return tipojava;
    }

}
