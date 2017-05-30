/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.rules;

import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.beans.Atributos;
import com.avbravo.wizardjmoordb.beans.Entidad;
import com.avbravo.wizardjmoordb.utilidades.JSFUtil;
import com.avbravo.wizardjmoordb.utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Named
@RequestScoped
public class RulesProcesador implements Serializable {
    // <editor-fold defaultstate="collapsed" desc="atributos">

    @Inject
    MySesion mySesion;
    List<EntidadPatron> entidadPatronList = new ArrayList<>();
    List<Patron> patronList = new ArrayList<>();
    List<Draw> drawList = new ArrayList<>();
    EntidadPatron entidadPatron = new EntidadPatron();

    Integer nivel = 0;
    // </editor-fold> 

    /**
     * Creates a new instance of RulesProcesador
     */
    public RulesProcesador() {
    }

    // <editor-fold defaultstate="collapsed" desc="cargar">
    public void cargar() {
        try {
            mySesion.setEntidadPatronList(new ArrayList<>());
            entidadPatronList = new ArrayList<>();
            for(Entidad entidad: mySesion.getEntidadList()){
                System.out.println("Entidad "+entidad.getTabla());
                System.out.println("Atributos");
for (Atributos a : entidad.getAtributosList()) {
                System.out.println("--------------(" + a.getNombre() + " : " + a.getTipo() + " @E "+ a.getEsEmbebido()+" @R " + a.getEsReferenciado()+" L<> "+a.getEsList() +" )");
}
            }
            
            for (Entidad entidad : mySesion.getEntidadList()) {
                System.out.println("_________________________________________________________________");
                System.out.println("_________________________________________________________________");
                System.out.println("________________ Analizando {" + entidad.getTabla() + "}_________");
                patronList = new ArrayList<>();
                drawList = new ArrayList<>();
                nivel = 0;
                if (Utilidades.isEmbeddedOrReferenced(entidad)) {

                    entidadPatron = new EntidadPatron();
                    entidadPatron.setEntidad(entidad);
                    generarPatron(entidad);
                    entidadPatron.setSize(nivel);

                } else {
                    System.out.println("------------{no tiene embebidos o referenciados}----------");
                }
            }
            mySesion.setEntidadPatronList(entidadPatronList);

            System.out.println("=================IMPRIMIR EL PATRON======");
            if (entidadPatronList.isEmpty()) {
                System.out.println("El patron esta vacio");
            } else {
               
                for (EntidadPatron e : entidadPatronList) {
                    System.out.println("================================================");
                    System.out.println("----Tabla() " + e.getEntidad().getTabla() + " size() " + e.getSize());
                    
                    String space = ".....";
                    String pattern="  ";
                    for (Patron p : e.getPatron()) {
                     //   System.out.println(space + " Level [" + p.getLevel()+"] "+p.getFather().getTabla() + " "+p.getOperator() + " "+p.getSon().getTabla());
                        pattern +=" [" + p.getLevel()+"] "+p.getFather().getTabla() + " "+p.getOperator() + " "+p.getSon().getTabla();
                        
                        space += ".....";
                    }
                    System.out.println("pattern "+pattern);
                }
            }

        } catch (Exception e) {
            JSFUtil.addErrorMessage("cargar() " + e.getLocalizedMessage());
        }
    }
    // </editor-fold> 

    // <editor-fold defaultstate="collapsed" desc="generarPatron"> 
    private Boolean generarPatron(Entidad entidad) {
        try {
            System.out.println("{Nivel }: " + nivel);
            System.out.println("-------{" + entidad.getTabla() + " }");
            String operador = "";
            Boolean found = false;
            for (Atributos a : entidad.getAtributosList()) {
                System.out.println("--------------(" + a.getNombre() + " : " + a.getTipo() + " @E "+ a.getEsEmbebido()+" @R " + a.getEsReferenciado()+" L<> "+a.getEsList() +" )");
                operador = Utilidades.getOperator(a);
                if (operador.equals("NOVALIDO")) {
                    // JSFUtil.addWarningMessage("El atributo "+a.getNombre() + " del entity" +entidad.getTabla()+" No tiene @E o @R ");
                } else {
                    Patron patron = new Patron();
                    found = true;
//                    if (nivel == 0) {
                    patron.setFather(entidad);
                    patron.setOperator(operador);
                    patron.setSon(Utilidades.convertStringToEntity(a.getNombre(), mySesion.getEntidadList()));
                    patron.setLevel(nivel);

                    patronList.add(patron);
                    //Verificar otro nivel

//                    } else {
//
//                    }
                    //analizar recursivamente
                    nivel++;
                    System.out.println("----------[Invoco recursivamente " + patron.getSon().getTabla()+"]-------------");
                    generarPatron(patron.getSon());
                }
            }
            if (!Utilidades.tienePatron(entidadPatron, entidadPatronList)) {
                System.out.println("---------------((((( Agrego el patron ["+entidadPatron.getEntidad().getTabla()+ "] )))))--------");
                entidadPatron.setPatron(patronList);

                entidadPatronList.add(entidadPatron);
            }

            return true;
        } catch (Exception e) {
            JSFUtil.addErrorMessage("generarPatron() " + e.getLocalizedMessage());
        }
        return false;
    }
    // </editor-fold> 
}
