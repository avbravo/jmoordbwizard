/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.facade;

import com.avbravo.jmoordbwizard.utilidades.JSFUtil;
import com.avbravo.jmoordbwizard.MySesion;
import com.avbravo.jmoordbwizard.ProyectoEJB;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class FacadeGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(FacadeGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoEJB proyectoEJB;

    /**
     * Creates a new instance of Facade
     */
    public FacadeGenerador() {
    }

    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
            mySesion.getEntidadList().forEach((e) -> {
                procesar(e, e.getTabla(), proyectoEJB.getPathEJB() + e.getTabla() + "Facade.java");
            });
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
            JSFUtil.addErrorMessage("processEJB() " + e.getLocalizedMessage());
        }
        return true;

    }

    private Boolean crearFile(String path, Entidad entidad, String archivo) {
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
                    fw.write("package " + proyectoEJB.getPaquete() + ".ejb;" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".entity.*;" + "\r\n");
                    fw.write("import javax.ejb.Stateless;" + "\r\n");
                    fw.write("import javax.ejb.EJB;" + "\r\n");

                    switch (mySesion.getDatabase().toLowerCase()) {
                        case "mongodb":
                            fw.write("import com.avbravo.ejbjmoordb.mongodb.facade.AbstractFacade;" + "\r\n");

                            fw.write("import " + proyectoEJB.getPaquete() + ".provider.MongoClientProvider;" + "\r\n");
                            fw.write("import com.mongodb.MongoClient;" + "\r\n");
                            break;
                        case "couchbase":

                            fw.write("import " + proyectoEJB.getPaquete() + ".provider.CouchbaseClientProvider;" + "\r\n");
                            fw.write("import com.avbravo.jmoordb.facade.CouchbaseAbstractFacade;" + "\r\n");
                            fw.write("import com.couchbase.client.java.Cluster;" + "\r\n");
                            break;
                        case "orientdb":
                            break;
                    }

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author " + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Stateless" + "\r\n");

                    switch (mySesion.getDatabase().toLowerCase()) {
                        case "mongodb":
                            fw.write("public class " + archivo + "Facade extends AbstractFacade<" + archivo + "> {" + "\r\n");
                            fw.write("" + "\r\n");
                            fw.write("    @EJB" + "\r\n");
                            fw.write("    MongoClientProvider mongoClientProvider;" + "\r\n");
                            fw.write("    @Override" + "\r\n");
                            fw.write("    protected MongoClient getMongoClient() {" + "\r\n");
                            fw.write("       return mongoClientProvider.getMongoClient();" + "\r\n");
                            fw.write("    }" + "\r\n");
                            fw.write("    public " + archivo + "Facade(){" + "\r\n");
                            fw.write("        super(" + archivo + ".class,\"" + mySesion.getDatabasename() + "\",\"" + Utilidades.letterToLower(archivo) + "\");" + "\r\n");
                            fw.write("    }" + "\r\n");
                            break;
                        case "couchbase":
                            fw.write("public class " + archivo + "Facade extends CouchbaseAbstractFacade<" + archivo + "> {" + "\r\n");
                            fw.write("" + "\r\n");
                            fw.write("    @EJB" + "\r\n");
                            fw.write("    CouchbaseClientProvider couchbseclientProvider;" + "\r\n");
                            fw.write("    @Override" + "\r\n");
                            fw.write("    protected Cluster getCluster() {" + "\r\n");
                            fw.write("      return couchbseclientProvider.getCluster();" + "\r\n");
                            fw.write("    }" + "\r\n");
                            fw.write("    public " + archivo + "Facade(){" + "\r\n");
                            fw.write("        super(" + archivo + ".class,\"" + Utilidades.letterToLower(archivo) + "\",\"" + Utilidades.letterToLower(archivo) + "\");" + "\r\n");
                            fw.write("    }" + "\r\n");
                            break;
                        case "orientdb":
                            break;
                    }

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public Object findById(String key, String value) {" + "\r\n");
                    fw.write("       return search(key,value); " + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    @Override" + "\r\n");
                    fw.write("    public Object findById(String key, Integer value) {" + "\r\n");
                    fw.write("        return search(key,value);" + "\r\n");
                    fw.write("    }" + "\r\n");

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
