/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.utilidades;

import com.avbravo.jmoordbwizard.beans.Atributos;
import com.avbravo.jmoordbwizard.beans.Entidad;
import com.avbravo.jmoordbwizard.menu.MyMenu;
import com.avbravo.jmoordbwizard.menu.MySubmenu;
import static com.avbravo.jmoordbwizard.entity.EntityReader.DEFAULT_CHARSET;
import com.avbravo.jmoordbwizard.rules.EntidadPatron;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.primefaces.model.TreeNode;

/**
 *
 * @author avbravoserver
 */
public class Utilidades {

    private static final Logger LOG = Logger.getLogger(Utilidades.class.getName());
    private static String persistenceunit = "";

    // <editor-fold defaultstate="collapsed" desc="mkdir">
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
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="searchDirectorie"> 
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
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="deleteDirectory"> 

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
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="eliminarDirectorio"> 

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
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="letterToUpper"> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 


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
    }// </editor-fold> 

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
    }// </editor-fold> 

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

                    oldtext += textoNuevo + "\r\n";

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
    }// </editor-fold> 

    public static boolean replaceWithoutLine(String rutaArchivo, String search, String textoNuevo) {
        try {

            File file = new File(rutaArchivo);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "\r\n", oldtext = "";
            boolean encontrado = false;
            while ((line = reader.readLine()) != null) {
                if (line.indexOf(search) != -1) {

                    oldtext += textoNuevo + "\r\n";

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

    public static DefaultTableModel limpiarModelo(DefaultTableModel modelo) {
        if (modelo != null) {
            while (modelo.getRowCount() > 0) {
                modelo.removeRow(0);
            }
        }
        return modelo;
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 

    /**
     * Obtiente el nombre de la entidad de persistencia
     *
     */
    public static String getPersistenceUnit(String ruta) {
        try {
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {

                JSFUtil.addWarningMessage("No existe el archivo persistence.xml en este proyecto");

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
    }// </editor-fold> 

    public static String getFirstLetter(String texto) {
        try {

            return texto = texto.substring(0, 1);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getFirstLetter" + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold> 

    /*
    devuelve el ultimo caracter
     */
    public static String getLastLetter(String texto) {
        try {
            if (texto.length() <= 0) {
                return "";
            }

            return texto = texto.substring(texto.length() - 1, texto.length());

        } catch (Exception e) {
            JSFUtil.addErrorMessage("getLastLetter()" + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 
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
    }// </editor-fold> 

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
    }// </editor-fold> 

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
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="isJavaType"> 

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
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="mysqlToJava"> 
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
                    tipojava = "byte[]";
                    break;
                case "BigInteger":
                    tipojava = "BigInteger";
                    break;
                case "Float":
                    tipojava = "Float";
                case "Short":
                    tipojava = "Short";
                case "Character":
                    tipojava = "Character";
                case "Boolean":
                    tipojava = "Boolean";
                case "Object":
                    tipojava = "Object";
                    break;
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("mysqlToJava() " + e.getLocalizedMessage());
        }
        return tipojava;
    }// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="getNumeroHijos"> 
    /**
     * getNumeroHijos devuelve el numero de hijos que tiene el Tree usado con
     * los menus
     *
     * @return
     */
    public static Integer getNumeroHijos(TreeNode root) {
        Integer contador = 0;
        try {

            for (TreeNode r : root.getChildren()) {
                contador = r.getChildren().stream().map((_item) -> 1).reduce(contador, Integer::sum);

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getNumeroHijos() " + e.getLocalizedMessage());
        }
        return contador;
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="tieneNietos"> 
    /**
     * verifica los nietos(padre-hijo-nietos)
     *
     * @param root
     * @return
     */
    public static Boolean tieneNietos(TreeNode root) {
        Boolean found = false;
        try {

            for (TreeNode r : root.getChildren()) {
                for (TreeNode r1 : r.getChildren()) {
                    if (r1.getChildCount() > 0) {
                        found = true;
                        break;
                    }
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tieneNietos() " + e.getLocalizedMessage());
        }
        return found;
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="showTree"> 
    /**
     * muestra el tree
     *
     * @param root
     * @return
     */
    public static String showTree(TreeNode root) {
        try {

            System.out.println("*******************************************");
            System.out.println("        root2");
            System.out.println(" # de Menus .getChildCount() " + root.getChildCount());

            System.out.println("*******************************************");

            for (TreeNode r : root.getChildren()) {
                System.out.println("=========================================");
                System.out.println(" Menu " + r.getData());

                for (TreeNode r2 : r.getChildren()) {

                    System.out.println("--------->Submenu  " + r2.getData());
                }

            }
            System.out.println("=======================================");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("show() " + e.getLocalizedMessage());
        }
        return "";
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="componerMenuFromTreeNode"> 
    /**
     * compone un menu con la estructura { Registros:[Paises]}{
     * Reportes:[Personas]}{ File:[Usuario]} ({Reportes:[Paises]} {File:[]} Menu
     * {} Submenu[] Registros: Personas,) Usuario Reportes: Paises File:
     * <<No tiene nada>>
     *
     * @param root
     * @return
     */
    public static String componerMenuFromTreeNode(TreeNode root) {
        String menu = "";
        try {

            for (TreeNode r : root.getChildren()) {
                menu += "{ " + r.getData() + ":[";
                Integer contador = 0;
                for (TreeNode r2 : r.getChildren()) {
                    if (contador > 0) {
                        menu += " , ";
                    }

                    menu += r2.getData();
                    contador++;
                }
                menu += "]}";
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("componerMenuFromTreeNode() " + e.getLocalizedMessage());
        }
        return menu;
    }// </editor-fold> 

    /**
     *
     * @param menu
     * @return
     */
// <editor-fold defaultstate="collapsed" desc="descomponerMenuString">   
    public static List<MyMenu> descomponerMenuString(String menu) {
        List<MyMenu> listMymenu = new ArrayList<>();
        try {

            System.out.println("" + menu);
            String[] submenu = menu.split("]}");
            for (String s : submenu) {

                String titulo = s.replace("{", "").trim();
                titulo = s.replace("{", "").trim().substring(0, titulo.indexOf(":"));
                MyMenu myMenu = new MyMenu();
                myMenu.setName(titulo);
                /*
                los items
                 */
                List<MySubmenu> listMySubmenu = new ArrayList<>();
                String r = s.substring(s.indexOf("[") + 1);

                String[] items = r.split(",");
                for (String i : items) {

                    MySubmenu ms = new MySubmenu();
                    ms.setName(i.trim());
                    listMySubmenu.add(ms);
                }
                myMenu.setSubmenu(listMySubmenu);
                listMymenu.add(myMenu);
            }
        } catch (Exception e) {
            System.out.println("descomponer() " + e.getLocalizedMessage());
        }
        return listMymenu;
    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="generateUniqueID">   

    public static String generateUniqueID() {
        String strValue = "";
        UUID idUnique = UUID.randomUUID();
        strValue = idUnique.toString();
        return strValue.toUpperCase();
    }// </editor-fold>   
// <editor-fold defaultstate="collapsed" desc="numerodeList">

    public static Integer numerodeList(Entidad entidad) {
        Integer numero = 0;
        try {
            numero = entidad.getAtributosList().stream().filter((atributos) -> (esTipoList(atributos.getTipo()))).map((_item) -> 1).reduce(numero, Integer::sum);
        } catch (Exception e) {
            JSFUtil.addErrorMessage("numerodeList() " + e.getLocalizedMessage());
        }
        return numero;
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="esTipoList">
    public static Boolean esTipoList(String tipo) {
        Boolean es = false;
        if (tipo.indexOf("List<") == -1) {
            return false;
        }
        return true;

    }// </editor-fold> 
// <editor-fold defaultstate="collapsed" desc="convertiraNombreEntityElTipoList">

    public static String convertiraNombreEntityElTipoList(String tipo) {
        Boolean es = false;
        if (tipo.indexOf("List<") == -1) {
            return tipo;
        }
        tipo = tipo.replace("List<", " ");
        tipo = tipo.replace(">", "");
        return tipo;

    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="gesTipoPojo">
    public static Boolean esTipoPojo(String tipo) {
        switch (tipo) {

            case "Integer":
            case "int":

            case "Double":
            case "double":

            case "String":
            case "Character":

            case "Date":
            case "Timestamp":
            case "Time":
            case "Boolean":
            case "BigInteger":
            case "Long":
            case "long":
            case "byte[]":
            case "Float":
            case "Short":
            case "InputStream":
            case "Collection":
            case "List":
                return false;
            case "Object":
            default:
                return true;
        }
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="isImpar">
    public static Boolean isImpar(int iNumero) {
        if (iNumero % 2 != 0) {
            return true;
        } else {
            return false;
        }
    }// </editor-fold> 

// <editor-fold defaultstate="collapsed" desc="haveEmbeddedOrReferenced">
    public static Boolean haveEmbeddedOrReferenced(Entidad entidad) {
        Boolean found = false;
        try {
            if (entidad.getAtributosList().stream().anyMatch((a) -> (a.getEsEmbedded() || a.getEsReferenced()))) {
                return true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("haveEmbeddedOrReferenced() " + e.getLocalizedMessage());
        }
        return found;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="haveEmbedded">
    public static Boolean haveEmbedded(Entidad entidad) {
        Boolean found = false;
        try {
            if (entidad.getAtributosList().stream().anyMatch((a) -> (a.getEsEmbedded()))) {
                return true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("haveEmbedded() " + e.getLocalizedMessage());
        }
        return found;
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="getOperator">
    /**
     * Devuelve el operador de una relacion pór Ejemplo Persona{ cedula,
     * nombre,@Embedded Pais pais} Devuelve: @e Los valores a retornar son @e,
     * L<@e>, @r, L<@r>, NOVALIDO
     *
     * @param a
     * @return
     */
    public static String getOperator(Atributos a) {
        String operador = "";
        try {
            if (a.getEsEmbedded()) {
                if (a.getEsListEmbedded()) {
                    operador = "L<@e>";
                } else {
                    operador = "@e";
                }
            } else {
                if (a.getEsReferenced()) {
                    if (a.getEsListReferenced()) {
                        operador = "L<@r>";
                    } else {
                        operador = "@r";
                    }
                } else {
                    operador = "NOVALIDO";
                }
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getOperator " + e.getLocalizedMessage());
        }
        return operador;
    }
// </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="convertStringToEntity">
    /**
     * Convierte un String a una Entidad Se pasa el nombre de la entidad como
     * una cadena el lo busca en la lista de entity al encontrarlo devuelve la
     * entidad correspondiente
     *
     * @param name
     * @param entidadList
     * @return
     */
    public static Entidad convertStringToEntity(String name, List<Entidad> entidadList) {

        try {
            name = Utilidades.letterToLower(name);
            for (Entidad entidad : entidadList) {
                if (name.equals(Utilidades.letterToLower(entidad.getTabla()))) {
                    return entidad;
                }
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("convertStringToEntity() " + e.getLocalizedMessage());
        }
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tienePatron">
    public static Boolean tienePatron(EntidadPatron entidadPatron, List<EntidadPatron> entidadPatronList) {
        Boolean found = false;
        try {
            if (entidadPatronList.isEmpty()) {
                return false;
            }
            if (entidadPatronList.stream().anyMatch((ep) -> (ep.getEntidad().equals(entidadPatron.getEntidad())))) {
                return true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("tienePatron() " + e.getLocalizedMessage());
        }
        return found;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="isLineFinishReferenced(">
    public static Boolean isLineFinishReferenced(String s) {
        Boolean finish = false;
        try {
            if (s.indexOf("@Referenced") != -1 && s.indexOf(")") != -1) {
                finish = true;
            } else {
                if (s.indexOf("@Referenced") != -1 && s.indexOf(")") == -1) {

                } else {
                    if (s.indexOf(")") != -1 && (s.indexOf("return") == -1 && s.indexOf("public") == -1)) {
                        finish = true;
                    }
                }

            }
        } catch (Exception e) {
        }
        return finish;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="isLineFinishEmbedded">

    public static Boolean isLineEmbedded(String s) {
        Boolean finish = false;
        try {
            if (s.indexOf("@Embedded") != -1) {
                finish = true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("isLineFinishEmbedded() " + e.getLocalizedMessage());
        }
        return finish;
    }
    // </editor-fold>
// <editor-fold defaultstate="collapsed" desc="getterMacAddress()">

    public static String getMacAddress() {
        InetAddress ip;
        String macAddress = "";
        try {

            ip = InetAddress.getLocalHost();
            System.out.println("Current IP address : " + ip.getHostAddress());

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            macAddress = sb.toString();

        } catch (UnknownHostException e) {
JSFUtil.addErrorMessage("getMacAddress() " + e.getLocalizedMessage());
            e.printStackTrace();

        } catch (SocketException e) {
JSFUtil.addErrorMessage("getMacAddress() " + e.getLocalizedMessage());
            e.printStackTrace();

        }
        return macAddress;
    }
// </editor-fold>
}
