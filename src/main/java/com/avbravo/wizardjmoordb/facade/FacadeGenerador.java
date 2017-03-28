/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.facade;

import com.avbravo.wizardjmoordb.JSFUtil;
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
//                  Utilidades.agregarTextoAlFinalArchivo(ruta, "Hola");
            } else {

                /**
                 *
                 */
//                if (!Utilidades.search(ruta, "@PersistenceContext")) {
//
//                    Utilidades.add(ruta, "> {", "\n@PersistenceContext(unitName = \"" + mySesion.getPersistenceContext() + "\")", false);
//                }

//                if (!Utilidades.search(ruta, "private EntityManager em;")) {
//                    Utilidades.add(ruta, "@PersistenceContext(unitName = \"" + mySesion.getPersistenceContext() + "\"", "private EntityManager em;", false);
//                }
                if (!Utilidades.search(ruta, "super(")) {
                    Utilidades.add(ruta, "> {", " public " + archivo + "Facade() {\n        super(" + archivo + ".class);\n}", false);
                }

                generarImport(ruta);
//            Stream<String> lines = Files.lines(path);
//            lines.forEach(
//                    s -> lineaEJB(s));

                /**
                 * generar los metodos
                 */
                if (!Utilidades.search(ruta, "findById(")) {
                    Utilidades.add(ruta, "private EntityManager em;", generarFindById(entidad, archivo), false);
                }
                if (!Utilidades.search(ruta, "findAll")) {
                    Utilidades.add(ruta, "private EntityManager em;", generarFindAll(entidad, archivo), false);
                }

                Utilidades.addNotFoundMethod(ruta, "public Boolean runScript(String sqlScript)", runScript(), "public class " + archivo + "Facade extends AbstractFacade<", false);
                if (!Utilidades.search(ruta, "get" + archivo + "List()")) {
                    Utilidades.add(ruta, "private EntityManager em;", generarGetList(entidad, archivo), false);
                }

//findBy
                entidad.getAtributosList().stream().forEach((a) -> {
                    Utilidades.add(ruta, "private EntityManager em;", generarFindBy(a, archivo, ruta), false);
                });
                entidad.getAtributosList().stream().forEach((a) -> {
                    if(a.getNombre().toLowerCase().equals("id")){
                        Utilidades.add(ruta, "private EntityManager em;", generarFindBy_(a, archivo, ruta), false);
                    }
                    
                });

//findByEntre fechas
                entidad.getAtributosList().stream().forEach((a) -> {
                    Utilidades.add(ruta, "private EntityManager em;", generarFindByEntre(a, archivo, ruta), false);
                });

                //maximo
                entidad.getAtributosList().stream().forEach((a) -> {
                    if (a.getEsPrimaryKey() && a.getTipo().equals("Integer")) {
                        Utilidades.add(ruta, "private EntityManager em;", generarGetMaximo(a, archivo, ruta), false);
                    }

                });
                entidad.getAtributosList().stream().forEach((a) -> {
                    if (a.getTipo().equals("String")) {
                        Utilidades.add(ruta, "private EntityManager em;", generarFindWithLike(a, archivo, ruta), false);
                    }

                });

                if (!Utilidades.search(ruta, "deleteAll()")) {
                    Utilidades.add(ruta, "private EntityManager em;", generarDeleteAll(entidad, archivo), false);
                }
            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("processEJB() " + e.getLocalizedMessage());
        }
        return true;

    }

    private void generarImport(String ruta) {
        try {
            /**
             * agregar los imports
             */
            if (!Utilidades.search(ruta, "import java.util.List;")) {
                Utilidades.add(ruta, "import javax.persistence.EntityManager;", "import java.util.List;", false);
            }
            if (!Utilidades.search(ruta, "import javax.persistence.Query;")) {
                Utilidades.add(ruta, "import javax.persistence.EntityManager;", "import javax.persistence.Query;", false);
            }
            if (!Utilidades.search(ruta, "import java.util.Date;")) {
                Utilidades.add(ruta, "import javax.persistence.EntityManager;", "import java.util.Date;", false);
            }

            if (!Utilidades.search(ruta, "import " + proyectoEJB.getPaquete() + ".entity.*;")) {
                Utilidades.add(ruta, "import javax.persistence.EntityManager;", "import " + proyectoEJB.getPaquete() + ".entity.*;", false);
            }
            Utilidades.searchAdd(ruta, "import " + proyectoEJB.getPaquete() + ".generales.JSFUtil;", "package", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarImport() " + e.getLocalizedMessage());
        }
    }

    private String generarFindAll(Entidad entidad, String archivo) {
        try {

            String texto = "";
            texto += "    @Override" + "\r\n";
            texto += "    public List<" + archivo + "> findAll() {" + "\r\n";
            texto += "          return em.createNamedQuery(\"" + archivo + ".findAll\").getResultList();" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindAll()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarGetList(Entidad entidad, String archivo) {
        try {

            String texto = "";

            texto = "    public List<" + archivo + "> get" + archivo + "List() {" + "\r\n";
            texto += "          return em.createNamedQuery(\"" + archivo + ".findAll\").getResultList();" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarGetList()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarFindById(Entidad entidad, String archivo) {
        try {
            String texto = "";
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsPrimaryKey()) {
                    texto = "    public " + archivo + " findById(" + a.getTipo() + " id) {" + "\r\n";
                    texto += "          return em.find(" + archivo + ".class, id);" + "\r\n";
                    texto += "   }" + "\r\n";
                }
            }

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindById()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarFindBy(Atributos atributos, String archivo, String ruta) {
        try {

            String texto = "";
            String columna = Utilidades.letterToUpper(atributos.getNombre());
            String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();
            if (Utilidades.search(ruta, "findBy" + columna + "(" + atributos.getTipo() + " " + atributos.getNombre() + ")")) {
                return "";
            }
            texto = "    public List<" + archivo + "> findBy" + columna + "(" + atributos.getTipo() + " " + atributos.getNombre() + ") {" + "\r\n";
            if (Utilidades.isJavaType(atributos.getTipo())) {
                texto += "          Query query = em.createNamedQuery(\"" + archivo + ".findBy" + columna + "\");" + "\r\n";
            } else {
                texto += "           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE " + primercaractertabla + "." + atributos.getNombre() + " = :" + atributos.getNombre() + "\");" + "\r\n";

            }

            texto += "          return query.setParameter(\"" + atributos.getNombre() + "\"," + atributos.getNombre() + ").getResultList();" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindBy()  " + e.getLocalizedMessage());
        }
        return "";
    }
    private String generarFindBy_(Atributos atributos, String archivo, String ruta) {
        try {

            String texto = "";
            String columna = Utilidades.letterToUpper(atributos.getNombre());
            String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();
            if (Utilidades.search(ruta, "findBy_" + columna + "(" + atributos.getTipo() + " " + atributos.getNombre() + ")")) {
                return "";
            }
            texto = "    public List<" + archivo + "> findBy_" + columna + "(" + atributos.getTipo() + " " + atributos.getNombre() + ") {" + "\r\n";
            if (Utilidades.isJavaType(atributos.getTipo())) {
                texto += "          Query query = em.createNamedQuery(\"" + archivo + ".findBy" + columna + "\");" + "\r\n";
            } else {
                texto += "           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE " + primercaractertabla + "." + atributos.getNombre() + " = :" + atributos.getNombre() + "\");" + "\r\n";

            }

            texto += "          return query.setParameter(\"" + atributos.getNombre() + "\"," + atributos.getNombre() + ").getResultList();" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindBy()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarFindByEntre(Atributos atributos, String archivo, String ruta) {
        try {

            String texto = "";
            String columna = Utilidades.letterToUpper(atributos.getNombre());
            String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();
            if (Utilidades.search(ruta, "findByEntre" + columna + "(Date desde, Date hasta) {")) {
                return "";
            }

            // generr el EntreFechas
            if (atributos.getTipo().equals("Date")) {
                texto += "" + "\r\n";
                texto += "" + "\r\n";
                texto += "    public List<" + archivo + "> findByEntre" + columna + "(Date desde, Date hasta) {" + "\r\n";
                texto += "           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE " + primercaractertabla + "." + atributos.getNombre() + " BETWEEN :desde AND :hasta\");" + "\r\n";
                texto += "           query.setParameter(\"desde\", desde);" + "\r\n";
                texto += "           query.setParameter(\"hasta\", hasta);" + "\r\n";
                texto += "          return query.getResultList();" + "\r\n";
                texto += "   }" + "\r\n";
            }
            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindBy()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarFindWithLike(Atributos atributos, String archivo, String ruta) {
        try {

            String texto = "";
            String columna = Utilidades.letterToUpper(atributos.getNombre());
            String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();
            if (Utilidades.search(ruta, "findBy" + columna + "Like(" + atributos.getTipo() + " " + atributos.getNombre() + ")")) {
                return "";
            }
            texto = "    public List<" + archivo + "> findBy" + columna + "Like(" + atributos.getTipo() + " " + atributos.getNombre() + ") {" + "\r\n";
            texto += "           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE lower(" + primercaractertabla + "." + atributos.getNombre() + ") like :" + atributos.getNombre() + "\");" + "\r\n";
            texto += "           " + atributos.getNombre() + " = \"\" + " + atributos.getNombre() + ".trim() + \"%\";" + "\r\n";

            texto += "          return query.setParameter(\"" + atributos.getNombre() + "\"," + atributos.getNombre() + ").getResultList();" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindBy()  " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * deleteAll
     *
     * @param entidad
     * @param archivo
     * @return
     */
    private String generarDeleteAll(Entidad entidad, String archivo) {
        try {

            String texto = "";

            texto = "    public void deleteAll(){" + "\r\n";
            texto += "          Query query = em.createNamedQuery(\"DELETE FROM " + archivo + "\");" + "\r\n";
            texto += "          int deleteRecords;" + "\r\n";
            texto += "          deleteRecords = query.executeUpdate();" + "\r\n";
            texto += "   }" + "\r\n";
            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarDeleteAll()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String runScript() {
        try {

            String texto = "";

            texto += "public Boolean runScript(String sqlScript) {" + "\r\n";
            texto += "" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "            Query q = em.createNativeQuery(sqlScript);" + "\r\n";
            texto += "            q.executeUpdate();" + "\r\n";
            texto += "            return true;" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "            JSFUtil.addErrorMessage(\"runScript() \" + e.getLocalizedMessage());" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return false;" + "\r\n";
            texto += "    }" + "\r\n";
            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("runScript()  " + e.getLocalizedMessage());
        }
        return "";
    }

    private String generarGetMaximo(Atributos atributos, String archivo, String ruta) {
        try {

            String texto = "";
            String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();

            if (Utilidades.search(ruta, "public Integer getMaximo()")) {
                return "";
            }
            texto = "    public Integer getMaximo() {" + "\r\n";
            texto += "        try {" + "\r\n";
            texto += "           Query query = em.createQuery(\"SELECT MAX( " + primercaractertabla + "." + atributos.getNombre() + ") FROM " + archivo + " " + primercaractertabla + "\");" + "\r\n";
            texto += "           Number result = (Number) query.getSingleResult();" + "\r\n";
            texto += "           return result.intValue();" + "\r\n";
            texto += "        } catch (Exception e) {" + "\r\n";
            texto += "        }" + "\r\n";
            texto += "        return 0;" + "\r\n";
            texto += "   }" + "\r\n";

            return texto;

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarFindBy()  " + e.getLocalizedMessage());
        }
        return "";
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
                    fw.write("import java.util.Date;" + "\r\n");
                    fw.write("import javax.ejb.Stateless;" + "\r\n");
                    fw.write("import java.util.Date;" + "\r\n");
                    fw.write("import java.util.List;" + "\r\n");
                    fw.write("import javax.persistence.Query;" + "\r\n");
                    fw.write("import javax.persistence.EntityManager;" + "\r\n");
                    fw.write("import javax.persistence.PersistenceContext;" + "\r\n");
                    fw.write("import " + proyectoEJB.getPaquete() + ".generales.JSFUtil;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author " + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Stateless" + "\r\n");
                    fw.write("public class " + archivo + "Facade extends AbstractFacade<" + archivo + "> {" + "\r\n");
                    fw.write("" + "\r\n");
//                    fw.write("    @PersistenceContext(unitName = \"" + mySesion.getPersistenceContext() + "\")" + "\r\n");
//                    fw.write("    private EntityManager em;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    protected EntityManager getEntityManager() {" + "\r\n");
                    fw.write("        return em;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public " + archivo + "Facade(){" + "\r\n");
                    fw.write("        super(" + archivo + ".class);" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("" + "\r\n");

//                    fw.write("    public List<" + archivo + "> findAll() {" + "\r\n");
//                    fw.write("          return em.createNamedQuery(\"" + archivo + ".findAll\").getResultList();" + "\r\n");
//                    fw.write("   }" + "\r\n");

                    /*
                    runScript
                    ejecuta script de mysql
                     */
                    fw.write(" public Boolean runScript(String sqlScript) {" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("        try {" + "\r\n");
                    fw.write("            Query q = em.createNativeQuery(sqlScript);" + "\r\n");
                    fw.write("            q.executeUpdate();" + "\r\n");
                    fw.write("            return true;" + "\r\n");
                    fw.write("        } catch (Exception e) {" + "\r\n");
                    fw.write("            JSFUtil.addErrorMessage(\"runScript() \" + e.getLocalizedMessage());" + "\r\n");
                    fw.write("        }" + "\r\n");
                    fw.write("        return false;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    /*
                     findById
                     */
                    for (Atributos a : entidad.getAtributosList()) {
                        if (a.getEsPrimaryKey()) {
                            fw.write("" + "\r\n");
                            fw.write("    public " + archivo + " findById(" + a.getTipo() + " id) {" + "\r\n");
                            fw.write("          return em.find(" + archivo + ".class, id);" + "\r\n");
                            fw.write("   }" + "\r\n");
                        }
                    }
                    /*
                    findAll
                     */
                    fw.write("" + "\r\n");
                    fw.write("    @Override" + "\r\n");
                    fw.write("    public List<" + archivo + "> findAll() {" + "\r\n");
                    fw.write("          return em.createNamedQuery(\"" + archivo + ".findAll\").getResultList();" + "\r\n");
                    fw.write("   }" + "\r\n");

                    /*
             get 
                     */
                    fw.write("" + "\r\n");
                    fw.write("    public List<" + archivo + "> get" + archivo + "List() {" + "\r\n");
                    fw.write("          return em.createNamedQuery(\"" + archivo + ".findAll\").getResultList();" + "\r\n");
                    fw.write("   }" + "\r\n");

//findBy
                    entidad.getAtributosList().stream().forEach((a) -> {

                        String columna = Utilidades.letterToUpper(a.getNombre());
                        String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();

                        try {
                            if (a.getNombre().toLowerCase().equals("id")) {
                                fw.write("    public List<" + archivo + "> findBy_" + columna + "(" + a.getTipo() + " " + a.getNombre() + ") {" + "\r\n");
                                if (Utilidades.isJavaType(a.getTipo())) {
                                    fw.write("          Query query = em.createNamedQuery(\"" + archivo + ".findBy" + columna + "\");" + "\r\n");
                                } else {
                                    fw.write("           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE " + primercaractertabla + "." + a.getNombre() + " = :" + a.getNombre() + "\");" + "\r\n");
                                }
                                fw.write("          return query.setParameter(\"" + a.getNombre() + "\"," + a.getNombre() + ").getResultList();" + "\r\n");
                                fw.write("   }" + "\r\n");
                            } else {
                                fw.write("    public List<" + archivo + "> findBy" + columna + "(" + a.getTipo() + " " + a.getNombre() + ") {" + "\r\n");
                                if (Utilidades.isJavaType(a.getTipo())) {
                                    fw.write("          Query query = em.createNamedQuery(\"" + archivo + ".findBy" + columna + "\");" + "\r\n");
                                } else {
                                    fw.write("           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE " + primercaractertabla + "." + a.getNombre() + " = :" + a.getNombre() + "\");" + "\r\n");
                                }
                                fw.write("          return query.setParameter(\"" + a.getNombre() + "\"," + a.getNombre() + ").getResultList();" + "\r\n");
                                fw.write("   }" + "\r\n");
                            }

                            // genera el query entre fechas
                            if (a.getTipo().equals("Date")) {
                                fw.write("    public List<" + archivo + "> findByEntre" + columna + "(Date desde, Date hasta) {" + "\r\n");
                                fw.write("           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE " + primercaractertabla + "." + a.getNombre() + " BETWEEN :desde AND :hasta\");" + "\r\n");
                                fw.write("           query.setParameter(\"desde\", desde);" + "\r\n");
                                fw.write("           query.setParameter(\"hasta\", hasta);" + "\r\n");
                                fw.write("          return query.getResultList();" + "\r\n");
                                fw.write("   }" + "\r\n");
                            }

                        } catch (IOException ex) {
                            Logger.getLogger(FacadeGenerador.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                    //maximo
                    entidad.getAtributosList().stream().forEach((a) -> {
                        if (a.getEsPrimaryKey() && a.getTipo().equals("Integer")) {

                            String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();

                            try {
                                fw.write("  " + "\r\n");
                                fw.write("    public Integer getMaximo() {" + "\r\n");
                                fw.write("        try {" + "\r\n");
                                fw.write("           Query query = em.createQuery(\"SELECT MAX( " + primercaractertabla + "." + a.getNombre() + ") FROM " + archivo + " " + primercaractertabla + "\");" + "\r\n");
                                fw.write("           Number result = (Number) query.getSingleResult();" + "\r\n");
                                fw.write("           return result.intValue();" + "\r\n");
                                fw.write("        } catch (Exception e) {" + "\r\n");
                                fw.write("        }" + "\r\n");
                                fw.write("        return 0;" + "\r\n");
                                fw.write("   }" + "\r\n");
                            } catch (IOException ex) {
                                Logger.getLogger(FacadeGenerador.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }

                    });

                    entidad.getAtributosList().stream().forEach((a) -> {
                        if (a.getTipo().equals("String")) {

                            try {
                                String columna = Utilidades.letterToUpper(a.getNombre());
                                String primercaractertabla = Utilidades.getFirstLetter(archivo).toLowerCase();

                                fw.write("    public List<" + archivo + "> findBy" + columna + "Like(" + a.getTipo() + " " + a.getNombre() + ") {" + "\r\n");
                                fw.write("           Query query = em.createQuery(\"SELECT " + primercaractertabla + " FROM " + archivo + " " + primercaractertabla + " WHERE lower(" + primercaractertabla + "." + a.getNombre() + ") like :" + a.getNombre() + "\");" + "\r\n");
                                fw.write("           " + a.getNombre() + " = \"\" + " + a.getNombre() + ".trim() + \"%\";" + "\r\n");

                                fw.write("          return query.setParameter(\"" + a.getNombre() + "\"," + a.getNombre() + ").getResultList();" + "\r\n");
                                fw.write("   }" + "\r\n");
                            } catch (IOException ex) {
                                Logger.getLogger(FacadeGenerador.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    });
                    /*
                    deleteAll
                     */
                    fw.write("    public void deleteAll(){" + "\r\n");
                    fw.write("          Query query = em.createNamedQuery(\"DELETE FROM " + archivo + "\");" + "\r\n");
                    fw.write("          int deleteRecords;" + "\r\n");
                    fw.write("          deleteRecords = query.executeUpdate();" + "\r\n");
                    fw.write("   }" + "\r\n");

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

    private Boolean escribirEJB(String path) {
        try {
            String ruta = path;
            File archivo = new File(ruta);
            BufferedWriter bw;
            if (archivo.exists()) {
                // El fichero ya existe
            } else {
                // El fichero no existe y hay que crearlo
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write("Acabo de crear el fichero de texto.");
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void lineaEJB(String s) {
//        if (s.indexOf("private") != -1) {

//        }
    }

}
