/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.menu;

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
public class MenuElementoGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(MenuElementoGenerador.class.getName());

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

            procesar("MenuElemento.java", rutas.getPathMenu() + "MenuElemento.java");

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

                Utilidades.searchAdd(ruta, "private static final long serialVersionUID = 1L;", "public class MenuElemento implements Serializable", false);
                Utilidades.searchAdd(ruta, "Boolean menu;", "public class MenuElemento implements Serializable", false);
                Utilidades.searchAdd(ruta, "Boolean crear;", "public class MenuElemento implements Serializable", false);
                Utilidades.searchAdd(ruta, "Boolean consultar;", "public class MenuElemento implements Serializable", false);
                Utilidades.searchAdd(ruta, "Boolean editar;", "public class MenuElemento implements Serializable", false);
                Utilidades.searchAdd(ruta, "Boolean eliminar;", "public class MenuElemento implements Serializable", false);
                Utilidades.searchAdd(ruta, "Boolean listar;", "public class MenuElemento implements Serializable", false);
 


                /*
                * generar los metodos
                 */
                Utilidades.addNotFoundMethod(ruta, "public MenuElemento()", menuElemento(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public MenuElemento(Boolean menu", menuElementoParametros(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void habilitar", habilitar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean getMenu()", getMenu(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean getCrear()", getCrear(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean getConsultar()", getConsultar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setConsultar", setConsultar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean getEditar()", getEditar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setEditar", setEditar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean getEliminar()", getEliminar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setEliminar", setEliminar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public Boolean getListar()", getListar(), "public class MenuElemento implements Serializable", false);
                Utilidades.addNotFoundMethod(ruta, "public void setListar", setListar(), "public class MenuElemento implements Serializable", false);
               
                 




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

            Utilidades.searchAdd(ruta, "import java.io.Serializable;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.enterprise.context.SessionScoped;", "package", false);
            Utilidades.searchAdd(ruta, "import javax.inject.Named;", "package", false);

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
                    fw.write("package " + mySesion.getPaquete() + ".menu;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("import java.io.Serializable;" + "\r\n");
                    fw.write("import javax.enterprise.context.SessionScoped;" + "\r\n");
                    fw.write("import javax.inject.Named;" + "\r\n");

                    fw.write("" + "\r\n");
                    fw.write("/**" + "\r\n");
                    fw.write(" *" + "\r\n");
                    fw.write(" * @author" + mySesion.getUsername() + "\r\n");
                    fw.write(" */" + "\r\n");
                    fw.write("@Named" + "\r\n");
                    fw.write("@SessionScoped" + "\r\n");
                    fw.write("public class MenuElemento implements Serializable{" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    private static final long serialVersionUID = 1L;" + "\r\n");

                    fw.write("    Boolean menu;" + "\r\n");
                    fw.write("    Boolean crear;" + "\r\n");
                    fw.write("    Boolean consultar;" + "\r\n");
                    fw.write("    Boolean editar;" + "\r\n");
                    fw.write("    Boolean eliminar;" + "\r\n");
                    fw.write("    Boolean listar;" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    /**" + "\r\n");
                    fw.write("     * Creates a new instance of MenuPojo" + "\r\n");
                    fw.write("     */" + "\r\n");
                    fw.write("    public MenuElemento() {" + "\r\n");
                    fw.write("        " + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public MenuElemento(Boolean menu, Boolean crear, Boolean consultar, Boolean editar, Boolean eliminar, Boolean listar) {" + "\r\n");
                    fw.write("        this.menu = menu;" + "\r\n");
                    fw.write("        this.crear = crear;" + "\r\n");
                    fw.write("        this.consultar = consultar;" + "\r\n");
                    fw.write("        this.editar = editar;" + "\r\n");
                    fw.write("        this.eliminar = eliminar;" + "\r\n");
                    fw.write("        this.listar = listar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("    " + "\r\n");
                    fw.write("    " + "\r\n");
                    fw.write("    public void habilitar(Boolean t){" + "\r\n");
                    fw.write("        menu=t;" + "\r\n");
                    fw.write("        crear=t;" + "\r\n");
                    fw.write("        consultar=t;" + "\r\n");
                    fw.write("        editar=t;" + "\r\n");
                    fw.write("        eliminar=t;" + "\r\n");
                    fw.write("        listar=t;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Boolean getMenu() {" + "\r\n");
                    fw.write("        return menu;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setMenu(Boolean menu) {" + "\r\n");
                    fw.write("        this.menu = menu;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Boolean getCrear() {" + "\r\n");
                    fw.write("        return crear;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setCrear(Boolean crear) {" + "\r\n");
                    fw.write("        this.crear = crear;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Boolean getConsultar() {" + "\r\n");
                    fw.write("        return consultar;" + "\r\n");
                    fw.write("    }" + "\r\n");

                    fw.write("    public void setConsultar(Boolean consultar) {" + "\r\n");
                    fw.write("        this.consultar = consultar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Boolean getEditar() {" + "\r\n");
                    fw.write("        return editar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setEditar(Boolean editar) {" + "\r\n");
                    fw.write("        this.editar = editar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Boolean getEliminar() {" + "\r\n");
                    fw.write("        return eliminar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setEliminar(Boolean eliminar) {" + "\r\n");
                    fw.write("        this.eliminar = eliminar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public Boolean getListar() {" + "\r\n");
                    fw.write("        return listar;" + "\r\n");
                    fw.write("    }" + "\r\n");
                    fw.write("" + "\r\n");
                    fw.write("    public void setListar(Boolean listar) {" + "\r\n");
                    fw.write("        this.listar = listar;" + "\r\n");
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

     private String menuElemento() {
        try {

            String texto = "";
texto += "" + "\r\n";
texto += "    /**" + "\r\n";
texto += "     * Creates a new instance of MenuPojo" + "\r\n";
texto += "     */" + "\r\n";
texto += "    public MenuElemento() {" + "\r\n";
texto += "        " + "\r\n";
texto += "    }" + "\r\n";
 return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("menuElemento()  " + e.getLocalizedMessage());
        }
        return "";
    }

 private String menuElementoParametros() {
        try {

            String texto = "";
texto += "" + "\r\n";
texto += "    public MenuElemento(Boolean menu, Boolean crear, Boolean consultar, Boolean editar, Boolean eliminar, Boolean listar) {" + "\r\n";
texto += "        this.menu = menu;" + "\r\n";
texto += "        this.crear = crear;" + "\r\n";
texto += "        this.consultar = consultar;" + "\r\n";
texto += "        this.editar = editar;" + "\r\n";
texto += "        this.eliminar = eliminar;" + "\r\n";
texto += "        this.listar = listar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "    " + "\r\n";
texto += "    " + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("menuElementoParametros()  " + e.getLocalizedMessage());
        }
        return "";
    }
 
  private String habilitar() {
        try {

            String texto = "";
texto += "    public void habilitar(Boolean t){" + "\r\n";
texto += "        menu=t;" + "\r\n";
texto += "        crear=t;" + "\r\n";
texto += "        consultar=t;" + "\r\n";
texto += "        editar=t;" + "\r\n";
texto += "        eliminar=t;" + "\r\n";
texto += "        listar=t;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("habilitar()  " + e.getLocalizedMessage());
        }
        return "";
    }
   private String getMenu() {
        try {

            String texto = "";
texto += "    public Boolean getMenu() {" + "\r\n";
texto += "        return menu;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
texto += "    public void setMenu(Boolean menu) {" + "\r\n";
texto += "        this.menu = menu;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getMenu()  " + e.getLocalizedMessage());
        }
        return "";
    }
   
    private String getCrear() {
        try {

            String texto = "";
texto += "    public Boolean getCrear() {" + "\r\n";
texto += "        return crear;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
texto += "    public void setCrear(Boolean crear) {" + "\r\n";
texto += "        this.crear = crear;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getCrear() " + e.getLocalizedMessage());
        }
        return "";
    }
    
     private String getConsultar() {
        try {

            String texto = "";
texto += "    public Boolean getConsultar() {" + "\r\n";
texto += "        return consultar;" + "\r\n";
texto += "    }" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getConsultar()  " + e.getLocalizedMessage());
        }
        return "";
    }
     
      private String setConsultar() {
        try {

            String texto = "";
texto += "    public void setConsultar(Boolean consultar) {" + "\r\n";
texto += "        this.consultar = consultar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setConsultar()  " + e.getLocalizedMessage());
        }
        return "";
    }
      
       private String getEditar() {
        try {

            String texto = "";
texto += "    public Boolean getEditar() {" + "\r\n";
texto += "        return editar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getEditar()  " + e.getLocalizedMessage());
        }
        return "";
    }
       
        private String setEditar() {
        try {

            String texto = "";
texto += "    public void setEditar(Boolean editar) {" + "\r\n";
texto += "        this.editar = editar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setEditar()  " + e.getLocalizedMessage());
        }
        return "";
    }
        
         private String getEliminar() {
        try {

            String texto = "";
texto += "    public Boolean getEliminar() {" + "\r\n";
texto += "        return eliminar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getEliminar()  " + e.getLocalizedMessage());
        }
        return "";
    }
         
          private String setEliminar() {
        try {

            String texto = "";
texto += "    public void setEliminar(Boolean eliminar) {" + "\r\n";
texto += "        this.eliminar = eliminar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setEliminar()  " + e.getLocalizedMessage());
        }
        return "";
    }
 private String getListar() {
        try {

            String texto = "";
texto += "    public Boolean getListar() {" + "\r\n";
texto += "        return listar;" + "\r\n";
texto += "    }" + "\r\n";
texto += "" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("getListar()  " + e.getLocalizedMessage());
        }
        return "";
    }
 private String setListar() {
        try {

            String texto = "";

texto += "    public void setListar(Boolean listar) {" + "\r\n";
texto += "        this.listar = listar;" + "\r\n";
texto += "    }" + "\r\n";
return texto;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("setListar()  " + e.getLocalizedMessage());
        }
        return "";
    }

}
