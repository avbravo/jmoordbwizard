/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.provider;

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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravoserver
 */
@Named
@RequestScoped
public class CouchbaseClientProviderGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CouchbaseClientProviderGenerador.class.getName());
    FileWriter fw;
    @Inject
    MySesion mySesion;
    @Inject
    ProyectoEJB proyectoEJB;

    /**
     * Creates a new instance of Facade
     */
    public CouchbaseClientProviderGenerador() {
    }

    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
            verificateFile();

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    /**
     * verifica si existe el archivo abstractFacade
     *
     * @return
     */
    private Boolean verificateFile() {
        try {
            String ruta = proyectoEJB.getPathProvider() + "CouchbaseClientProvider.java";
            if (!Utilidades.existeArchivo(ruta)) {
                createFile(ruta, "CouchbaseClientProvider");
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("verificateFile() " + e.getLocalizedMessage());
        }
        return false;
    }

    /**
     * crea el archivo AbstractFacade
     *
     * @return
     */
    private Boolean createFile(String path, String archivo) {
        try {

            String ruta = path;
            File file = new File(ruta);
            BufferedWriter bw;
            if (file.exists()) {
                // El fichero ya existe
            } else {
                // El fichero no existe y hay que crearlo
                bw = new BufferedWriter(new FileWriter(ruta));
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
                    fw.write("package " + proyectoEJB.getPaquete() + ".provider;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import com.avbravo.avbravoutils.JsfUtil;" + "\r\n");

                    fw.write("import com.couchbase.client.java.Cluster;" + "\r\n");
                    fw.write("import com.couchbase.client.java.CouchbaseCluster;" + "\r\n");
                    fw.write("import com.couchbase.client.java.env.CouchbaseEnvironment;" + "\r\n");
                    fw.write("import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;" + "\r\n");
                    fw.write("import java.util.logging.Logger;" + "\r\n");
                    fw.write("import javax.annotation.PostConstruct;" + "\r\n");
                    fw.write("import javax.ejb.ConcurrencyManagement;" + "\r\n");
                    fw.write("import javax.ejb.ConcurrencyManagementType;" + "\r\n");
                    fw.write("import javax.ejb.Lock;" + "\r\n");
                    fw.write("import javax.ejb.LockType;" + "\r\n");
                    fw.write("import javax.ejb.Singleton;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Singleton" + "\r\n");
                    fw.write("@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("public class CouchbaseClientProvider {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final Logger LOG = Logger.getLogger(CouchbaseClientProvider.class.getName());" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("    private Cluster cluster = null;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    @Lock(LockType.READ)" + "\r\n");
                    fw.write("    public Cluster getCluster(){" + "\r\n");
                    fw.write("        return cluster;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    @PostConstruct" + "\r\n");
                    fw.write("    public void init() {" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("           CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()" + "\r\n");
                    fw.write("                         .connectTimeout(10000) //10000ms = 10s, default is 5s;" + "\r\n");
                    fw.write("                         .build();" + "\r\n");
                    fw.write("          cluster = CouchbaseCluster.create(\"localhost\");" + "\r\n");
                    fw.write("       } catch (Exception e) {" + "\r\n");
                    fw.write("           JsfUtil.addErrorMessage(\"init() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("           LOG.warning(\"CouchbaseClientProvider init() \"+e.getLocalizedMessage());" + "\r\n");
                    fw.write("       }" + "\r\n");
                    fw.write("   }" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("" + "\r\n");

                    fw.write("}" + "\r\n");
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("createFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("createFile() " + e.getLocalizedMessage());
        }
        return false;
    }

}
