/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.generador.properties;

import com.avbravo.wizardjmoordb.JSFUtil;
import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.ProyectoJEE;
import com.avbravo.wizardjmoordb.utilidades.FechasServices;
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
public class ApplicationPropertiesGenerador implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ApplicationPropertiesGenerador.class.getName());

    @Inject
    MySesion mySesion;
    @Inject
    ProyectoJEE proyectoJEE;
    @Inject
    FechasServices fechasServices;

    /**
     * Creates a new instance of Facade
     */
    public void generar() {
        try {
            //recorrer el entity para verificar que existan todos los EJB
          
            procesar("application.properties", proyectoJEE.getPathProperties() + "application.properties");
            procesar("application_en.properties", proyectoJEE.getPathProperties() + "application_en.properties");
            procesar("application_es.properties", proyectoJEE.getPathProperties() + "application_es.properties");

        } catch (Exception e) {
            JSFUtil.addErrorMessage("generar() " + e.getLocalizedMessage());

        }
    }

    private Boolean procesar(String archivo, String ruta) {
        try {
   
            Path path = Paths.get(ruta);
            if (Files.notExists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
                crearFile(ruta, archivo);
            }
            //application
            Utilidades.searchAdd(ruta, "application.title=" + proyectoJEE.getProyecto(), "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "application.shorttitle=" + proyectoJEE.getProyecto(), "application.title=", false);
            Utilidades.searchAdd(ruta, "application.footer=" + proyectoJEE.getProyecto(), "application.title=", false);
            Utilidades.searchAdd(ruta, "application.version=0.0.1", "application.shorttitle=", false);
            Utilidades.searchAdd(ruta, "label.title=Titulo","# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.shorttitle=Titulo", "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.footer=Footer", "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.version=Version", "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.desarrolladopor=Desarrollador", "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.pais=Pais", "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.provincia=Provincia", "# and open the template in the editor.", false);
            Utilidades.searchAdd(ruta, "label.email=Email", "# and open the template in the editor.", false);
            botones(ruta);
             login(ruta);
             info(ruta);
             warning(ruta);
             dialog(ruta);
            title(ruta);
            language(ruta);
            error(ruta);
            label(ruta);
            estatus(ruta);
            empresa(ruta);
            generales(ruta);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("procesar() " + e.getLocalizedMessage());
        }
        return true;

    }

    private Boolean botones(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#botones -start-", "application.version=", false);
            Utilidades.searchAdd(ruta, "#botones -end-", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.cerrar=Cerrar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.changetheme=Cambiar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.new=Nuevo", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.save=Guardar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.delete=Eliminar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.update=Actualizar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.ayuda=Ayuda", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.filter=Filtrar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.search=Buscar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.home=Inicio", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.query=Consultar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.clear=Limpiar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.yes=Si", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.no=No", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.list=Listar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.print=Imprimir", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.login=Login", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.logout=Salir", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.menu=Menu", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.return=Regresar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.detalle=Detalle", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.add=Agregar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.monitor=Monitor", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.calculadora=Calcular", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.comment=Comentario", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.free=Liberar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.view=Ver", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.addfila=Agregar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.printall=imprimir todos", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.finalizar=Finalizar", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.historial=Historial", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.runscript=Run  script", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.all=Todos", "#botones -start-", false);
            Utilidades.searchAdd(ruta, "boton.showall=Mostrar todo", "#botones -start-", false);

        } catch (Exception e) {
            JSFUtil.addErrorMessage(" Botones() " + e.getLocalizedMessage());
        }
        return true;
    }

    private Boolean login(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#login -start-", "#botones -end-", false);
            Utilidades.searchAdd(ruta, "#login -end-", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.username=Username", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.password=Password", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.passwordnotvalid=Password no es correcto", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.usernamenotvalid=Nombre de usuario no valido", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.inactive=Usuario inactivo", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.logout=Salir", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.accesodenegado=Acceso Denegado", "#login -start-", false);
            Utilidades.searchAdd(ruta, "login.accesodenegadoDetalle=No cuenta con los privilegios para accesar esta pagina", "#login -start-", false);
        } catch (Exception e) {
            JSFUtil.addErrorMessage(" Botones() " + e.getLocalizedMessage());
        }
        return true;
    }

    
     private Boolean info(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#info -start-", "#login -end-", false);
             Utilidades.searchAdd(ruta, "#info -end-", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.message=Mensaje", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.save=Guardado exitosamente", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.uso=Esta en uso", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.baja=Esta de baja", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.theme=Tema", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.required=es requerido", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.update=Editado exitosamente", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.delete=Eliminado", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.notnull=No nulo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.sinrolasignado=No tiene rol asignado", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.title=Informacion", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.doyouwantdelete=Desea eliminarlo?", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.valormayormaximo=El valor no debe ser mayor que el valor maximo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.valormenorminimo=El valor no debe ser menor que el minimo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.minimomayor=El valor minimo no debe ser superior al valor mayor", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.mayormenorminimo=El valor maximo no debe ser menor que el valor minimo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.valornegativo=El valor no debe ser negativo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.maximonegativo=El valor maximo no debe ser negativo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.minimonegativo=El valor minimo no debe ser negativo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.repetirpassword=Repita el password", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.usuariologeado=Usuario logeado", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.desde=Desde", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.hasta=Hasta", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.a=a", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.yaexisteenlalista=Este registro ya existe en la lista", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.doyouwantdeleteall=Desea eliminar todos los registros?", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.selectall=Seleccionar Todo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.undoselectall=Deseleccionar todo", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.nohayregistros=No hay registros", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.procesoterminado=Proceso terminado", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.incompleto=Incompleto", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.mes=Mes", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.mes=Mes", "#info -start-", false);
             Utilidades.searchAdd(ruta, "info.nota=Nota:", "#info -start-", false);
              } catch (Exception e) {
            JSFUtil.addErrorMessage(" Info() " + e.getLocalizedMessage());
        }
        return true;
    } 
     
       private Boolean warning(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#warning -start-", "#info -end-", false);
             Utilidades.searchAdd(ruta, "#warning -end-", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.title=Advertencia", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.idnotexist=No existe un registro con ese id", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.idexist=Existe registro con ese id", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.noexiste=El registro no existe", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.nohayregistrosparaimprimir=No hay registros con esa condicion para imprimir", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.nohayregistros=No hay registros para guardar", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.seleccionetipo=Seleccione tipo", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.noestarango=No esta en el rango permitido", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.a=a", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.notieneprivilegiosdedesarrollador=Solo un desarrollador puede agregar,actualizar o eliminar desarrolladores", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.nohayregistrosimprimir=No hay registros para imprimir", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.comentariovacio=El comentario no debe estar vacio", "#warning -start-", false);
             Utilidades.searchAdd(ruta, "warning.notieneasignadoesterol=No tiene ese rol asignado", "#warning -start-", false);
             } catch (Exception e) {
            JSFUtil.addErrorMessage(" Warning() " + e.getLocalizedMessage());
        }
        return true;
    } 
       
       
        private Boolean dialog(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#dialog -start-", "#warning -end-", false);
             Utilidades.searchAdd(ruta, "#dialog -end-", "#dialog -start-", false);
             Utilidades.searchAdd(ruta, "dialog.edit=Editar", "#dialog -start-", false);
             Utilidades.searchAdd(ruta, "dialog.search=Busqueda", "#dialog -start-", false);
             Utilidades.searchAdd(ruta, "dialog.delete=Eliminar", "#dialog -start-", false);
             Utilidades.searchAdd(ruta, "dialog.process=Procesando...", "#dialog -start-", false);
             Utilidades.searchAdd(ruta, "dialog.process=Procesando...", "#dialog -start-", false);
             } catch (Exception e) {
            JSFUtil.addErrorMessage(" Dialog() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean title(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#title -start-", "#dialog -end-", false);
             Utilidades.searchAdd(ruta, "#title -end-", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.new=Nuevo", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.print=Imprimir", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.edit=Editar", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.search=Buscar", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.delete=Eliminar", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.list=Listar", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.options=Opciones", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.insert=Insertar", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.addtodatatable=Agregar a la tabla", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.searchregimen=Buscar regimen", "#title -start-", false);
             Utilidades.searchAdd(ruta, "titulo.tienemensajes=Mensaje", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.accesodenegado=Acceso denegado", "#title -start-", false);
             Utilidades.searchAdd(ruta, "title.accesodenegado=Acceso denegado", "#title -start-", false);
             
             } catch (Exception e) {
            JSFUtil.addErrorMessage(" Title() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean language(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#language -start-", "#title -end-", false);
             Utilidades.searchAdd(ruta, "#language -end-", "#language -start-", false);
             Utilidades.searchAdd(ruta, "language.spanish=Espanol", "#title -start-", false);
             Utilidades.searchAdd(ruta, "language.english=Ingles", "#title -start-", false);
             Utilidades.searchAdd(ruta, "language.language=Idiomas", "#title -start-", false);
            
            
             
             } catch (Exception e) {
            JSFUtil.addErrorMessage("language() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean error(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#error -start-", "#language -end-", false);
             Utilidades.searchAdd(ruta, "#error -end-", "#error -start-", false);
             Utilidades.searchAdd(ruta, "error.title=Error", "#error -start-", false);
             
            
             
             } catch (Exception e) {
            JSFUtil.addErrorMessage("error() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean label(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#label -start-", "#error -end-", false);
             Utilidades.searchAdd(ruta, "#label -end-", "#label -start-", false);
             Utilidades.searchAdd(ruta, "label.similar=similar", "#label -start-", false);
             Utilidades.searchAdd(ruta, "label.nota=Nota", "#label -start-", false);
             Utilidades.searchAdd(ruta, "label.searchallfield=Buscar en todos campos", "#label -start-", false);
             Utilidades.searchAdd(ruta, "label.busqueda=Busqueda", "#label -start-", false);

             
            
             
             } catch (Exception e) {
            JSFUtil.addErrorMessage("label() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean estatus(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#estatus -start-", "#label -end-", false);
             Utilidades.searchAdd(ruta, "#estatus -end-", "#estatus -start-", false);
             Utilidades.searchAdd(ruta, "estatus.inicial=Inicial", "#estatus -start-", false);
             Utilidades.searchAdd(ruta, "estatus.usado=Usado", "#estatus -start-", false);
             Utilidades.searchAdd(ruta, "estatus.procesando=Procesando", "#estatus -start-", false);
           

             } catch (Exception e) {
            JSFUtil.addErrorMessage("estatus() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean empresa(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#empresa -start-", "#estatus -end-", false);
             Utilidades.searchAdd(ruta, "#empresa -end-", "#empresa -start-", false);
             Utilidades.searchAdd(ruta, "empresa.email=empresa@empresa.com", "#empresa -start-", false);
             Utilidades.searchAdd(ruta, "empresa.empresa=Empresa", "#empresa -start-", false);
             Utilidades.searchAdd(ruta, "empresa.pais=Pais", "#empresa -start-", false);
             Utilidades.searchAdd(ruta, "empresa.provincia=Provincia", "#empresa -start-", false);
             Utilidades.searchAdd(ruta, "empresa.telefono=(507)-", "#empresa -start-", false);
             
           

             } catch (Exception e) {
            JSFUtil.addErrorMessage("empresa() " + e.getLocalizedMessage());
        }
        return true;
    } 
        private Boolean generales(String ruta) {
        try {
            Utilidades.searchAdd(ruta, "#generales -start-", "#empresa -end-", false);
             Utilidades.searchAdd(ruta, "#generales -end-", "#generales -start-", false);
             Utilidades.searchAdd(ruta, "selectonemenu.select=Seleccione uno", "#generales -start-", false);
             Utilidades.searchAdd(ruta, "tab.notificaciones=Notificaciones", "#generales -start-", false);
             Utilidades.searchAdd(ruta, "tab.pendientes=Pendientes", "#generales -start-", false);
             Utilidades.searchAdd(ruta, "version=Version", "#generales -start-", false);
             Utilidades.searchAdd(ruta, "cambiarpassword.records=Cambiar Password", "#generales -start-", false);
             
           

             } catch (Exception e) {
            JSFUtil.addErrorMessage("generales() " + e.getLocalizedMessage());
        }
        return true;
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
                    fw.write("# To change this license header, choose License Headers in Project Properties." + "\r\n");
                    fw.write("# To change this template file, choose Tools | Templates" + "\r\n");
                    fw.write("# and open the template in the editor." + "\r\n");
                    fw.write("application.title=" + proyectoJEE.getProyecto() + "\r\n");
                    fw.close();

                } catch (IOException ex) {
                    JSFUtil.addErrorMessage("ApplicationPropertiesGenerador.crearFile() " + ex.getLocalizedMessage());
                }

            }
        } catch (Exception e) {
            JSFUtil.addErrorMessage("ApplicationPropertiesGenerador..crearFile() " + e.getLocalizedMessage());
        }
        return false;
    }

}
