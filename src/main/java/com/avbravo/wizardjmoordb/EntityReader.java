/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb;

import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Embedded;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.beans.Referenced;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class EntityReader implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String DEFAULT_CHARSET = "UTF-8";
    @Inject
    MySesion mySesion;
    Entidad entidad = new Entidad();
    List<Atributos> atributosList = new ArrayList<>();
    List<Referenced> referencedList = new ArrayList<>();
    List<Embedded> embeddedList = new ArrayList<>();
    private Boolean terminaReferenced = true;
    private Boolean terminaEmbedded = true;

    private String campoId = "";
    Integer fila = 0;
    Integer rowId = 0;
    Integer contador = 0;
    private Boolean detener = false;

    /**
     * Creates a new instance of ProcesarEntity
     */
    public EntityReader() {
    }

    public Boolean readEntity(String archivo, String ruta) {
        try {

            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {

                JSFUtil.addWarningMessage("No existe el archivo " + ruta);

                return false;
            }
            /**
             *
             */
            entidad = new Entidad();
            entidad.setTabla(archivo);
            atributosList = new ArrayList<>();
            referencedList = new ArrayList<>();
            embeddedList = new ArrayList<>();

            fila = 0;
            rowId = 0;
            campoId = "";
            // buscar el campo que es primaryKey
            if (!searchId(path)) {
                mySesion.setAllTablesWithPrimaryKey(false);
                mySesion.getMensajesList().add(archivo + " No tiene Primary Key");
            }
            // procesar los atributos
            Files.lines(path, Charset.forName(DEFAULT_CHARSET)).forEach(line -> {

                linea(line);
                lineReferenced(line);
                lineEmbedded(line);
            });
            Boolean esEmbebido;
            Boolean esList = false;
            Boolean esReferenciado;
            Integer contador = 0;
            /**
             * Se indica si el atributo es Embebido o referenciado
             */
            for (Atributos a : atributosList) {
                atributosList.get(contador).setEsReferenciado(false);
                if (!embeddedList.isEmpty()) {
                    esEmbebido = false;
                    esList = false;
                    for (Embedded e : embeddedList) {
                        if (a.getNombre().equals(e.getField())) {
                            esEmbebido = true;
                            esList = e.getEsList();
                            break;
                        }
                    }
                    atributosList.get(contador).setEsEmbebido(esEmbebido);
                    atributosList.get(contador).setEsList(esList);
                } else {
                    atributosList.get(contador).setEsEmbebido(false);
                    atributosList.get(contador).setEsList(false);
                }
                contador++;
            }
            /*
            Referenced
             */
            contador=0;
            for (Atributos a : atributosList) {
                atributosList.get(contador).setEsReferenciado(false);
                if (!referencedList.isEmpty()) {
                    esReferenciado = false;
                    esList = false;
                    for (Referenced r : referencedList) {
                        if (a.getNombre().equals(r.getField())) {
                            esReferenciado = true;
                            esList = r.getEsList();
                            break;
                        }
                    }
                    atributosList.get(contador).setEsReferenciado(esReferenciado);
                    atributosList.get(contador).setEsList(esList);
                } else {
                    atributosList.get(contador).setEsReferenciado(false);

                }
                contador++;
            }

            entidad.setAtributosList(atributosList);
            entidad.setEmbeddedList(embeddedList);
            entidad.setReferencedList(referencedList);
            mySesion.getEntidadList().add(entidad);
        } catch (Exception e) {
            JSFUtil.addErrorMessage("readEntity() " + e.getLocalizedMessage());

        }
        return true;

    }

    /*
    se establece el tipo de datos
     */
    private void linea(String s) {
        try {
            Atributos atributos = new Atributos();
            if (s.indexOf("private static final long serialVersionUID = 1L;") != -1 || s.indexOf("private Collection") != -1) {
                //no se toma en cuenta
            } else if (s.indexOf("@Id") != -1) {
                //id
            } else if (s.indexOf("private") != -1) {
                s = s.replace("private", "");
                s = s.replace(";", "");
                s = s.trim();
                String[] splited = s.split("\\s");
//                atributos.setTipo(splited[0]);
                atributos.setTipo(Utilidades.mysqlToJava(splited[0]));

                atributos.setNombre(splited[1]);
                atributos.setEsPrimaryKey(atributos.getNombre().equals(campoId));

                atributosList.add(atributos);

            } else {
                if (s.indexOf("@Referenced") != -1) {

                } else {
                    if (s.indexOf("@Embedded") != -1) {

                    }
                }
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("EntidadGenerador.linea() " + e.getLocalizedMessage());
        }
        //private static final long serialVersionUID = 1L;
        //private Collection

    }

    private Boolean searchId(Path path) {
        try {

            Files.lines(path, Charset.forName(DEFAULT_CHARSET)).forEach(line -> {
                fila++;
                if (line.indexOf("@Id") != -1) {
                    rowId = fila;
                }
            });

            if (rowId != 0) {
                if (!searchNameFieldId(path)) {
                    JSFUtil.addWarningMessage("No se encontre el campo llave del entity");
                    return false;
                }
                return true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("searchId() " + e.getLocalizedMessage());
        }
        //private static final long serialVersionUID = 1L;
        //private Collection
        return false;
    }

    private Boolean searchNameFieldId(Path path) {
        try {
            contador = 0;
            detener = false;
            campoId = "";
            Files.lines(path, Charset.forName(DEFAULT_CHARSET)).forEach(line -> {
                contador++;
                if (!detener) {
                    if (contador >= rowId + 1) {
                        if (line.indexOf("private") != -1) {
                            line = line.replace("private", "");
                            line = line.replace(";", "");
                            line = line.trim();
                            String[] splited = line.split("\\s");
                            campoId = splited[1];
                            detener = true;
                        }
                    }
                }

            });

            if (!campoId.equals("")) {
                return true;
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("searchNameFieldId() " + e.getLocalizedMessage());
        }
        //private static final long serialVersionUID = 1L;
        //private Collection
        return false;
    }

    private void lineReferenced(String s) {
        try {

            if (terminaReferenced) {
                Referenced referenced = new Referenced();
                // aqui es la linea referenciada
//                System.out.println("Descomponer esta linea " + s);
                if (s.indexOf("List") != -1) {
                    referenced.setEsList(true);
                    s = s.replace("private List<", "");
                    s = s.replace(">", "");
                } else {
                    referenced.setEsList(false);
                    s = s.replace("private", "");

                }

                s = s.replace(";", "");
                s = s.trim();
                String[] splited = s.split("\\s");
                referenced.setType(splited[0]);
                referenced.setField(splited[1]);
                referencedList.add(referenced);

                terminaReferenced = false;
            } else {
                if (s.indexOf("@Referenced") != -1 && s.indexOf(")") != -1) {
                    terminaReferenced = true;
                } else {
                    if (s.indexOf("@Referenced") != -1 && s.indexOf(")") == -1) {
                    } else {
                        if (s.indexOf(")") != -1 && (s.indexOf("return") == -1 && s.indexOf("public") == -1)) {
                            terminaReferenced = true;
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("EntidadGenerador.linea() " + e.getLocalizedMessage());
        }

    }

    private void lineEmbedded(String s) {
        try {

            if (terminaEmbedded) {
                Embedded embedded = new Embedded();

                if (s.indexOf("List") != -1) {
                    embedded.setEsList(true);
                    s = s.replace("private List<", "");
                    s = s.replace(">", "");
                } else {
                    embedded.setEsList(false);
                    s = s.replace("private", "");
                }

                s = s.replace(";", "");
                s = s.trim();
                String[] splited = s.split("\\s");
                embedded.setType(splited[0]);
                embedded.setField(splited[1]);
                embeddedList.add(embedded);

                terminaEmbedded = false;
            } else {
                if (s.indexOf("@Embedded") != -1) {
                    terminaEmbedded = true;
                }

            }
        } catch (Exception e) {
            System.out.println("EntidadGenerador.lineEmbedded() " + e.getLocalizedMessage());
        }

    }
}
