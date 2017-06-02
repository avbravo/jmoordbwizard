/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.rules;

import com.avbravo.wizardjmoordb.MySesion;
import com.avbravo.wizardjmoordb.Test;
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
//            for (Entidad entidad : mySesion.getEntidadList()) {
//                Test.msg("-------------------------------------------------");
//                Test.msg("Entidad " + entidad.getTabla());
//                Test.msg("Atributos{");
//                Test.msg("Nombre         Tipo   | Embebido    List    |  Referenciado   List");
//                for (Atributos a : entidad.getAtributosList()) {
//                   Test.msg(a.getNombre() + "  " + a.getTipo() + " | " + a.getEsEmbedded()+ " "+a.getEsListEmbedded() + " |" + a.getEsReferenced()+ " " + a.getEsListReferenced());
//                }
//               Test.msg("}");
//            }

            for (Entidad entidad : mySesion.getEntidadList()) {

                Test.msg("---------------------------------------------------", false);
                Test.msg("---------------------------------------------------", false);
                Test.msg("Analizando [Entity{" + entidad.getTabla() + "}]", false);
                patronList = new ArrayList<>();
                drawList = new ArrayList<>();
                nivel = 0;
//                if (Utilidades.haveEmbeddedOrReferenced(entidad)) {
                if (Utilidades.haveEmbedded(entidad)) {

                    entidadPatron = new EntidadPatron();
                    entidadPatron.setEntidad(entidad);
                    generarPatron(entidad);
                    entidadPatron.setSize(nivel);

                } else {
                    Test.msg("{No tiene embebidos }", false);
                }
            }
            Test.msg("----------------------ANALISIS DE ENTITY TERMINADO-----------------------------", false);
            mySesion.setEntidadPatronList(entidadPatronList);
            Test.msg("================================================", true);
            Test.msg("=================IMPRIMIR EL PATRON=============", true);
            if (entidadPatronList.isEmpty()) {
                Test.msg("El patron esta vacio", true);
            } else {

                for (EntidadPatron e : entidadPatronList) {
                    Test.msg("================================================", true);
                    Test.msg("Tabla() " + e.getEntidad().getTabla() + " size() " + e.getSize(), true);

                    String space = ".....";
                    String pattern = "  ";
                    for (Patron p : e.getPatron()) {

                        pattern += "( [" + p.getLevel() + "] " + p.getFather().getTabla() + " " + p.getOperator() + " " + p.getSon().getTabla() + " )";

                        space += ".....";
                    }
                    Test.msg("pattern " + pattern, true);
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
            Test.msg("{Nivel }: " + nivel, false);
            Test.msg("-------{" + entidad.getTabla() + " }", false);
            String operador = "";
            Boolean found = false;
            for (Atributos a : entidad.getAtributosList()) {
                if (a.getEsEmbedded() || a.getEsListReferenced()) {
                    if (a.getEsEmbedded()) {
                        Test.msg("--------------Field(" + a.getNombre() + " : " + a.getTipo() + "{ @Embedded(" + a.getEsEmbedded() + " L<>: " + a.getEsListEmbedded() + " )", false);
                    } else {
                        Test.msg("--------------Field(" + a.getNombre() + " : " + a.getTipo() + "{ @Referenced( " + a.getEsReferenced() + " L<> " + a.getEsListReferenced() + " ) No se analiza", false);
                    }

                } else {
                    Test.msg("--------------Field(" + a.getNombre() + " : " + a.getTipo() + "). No se analiza", false);
                }

                operador = Utilidades.getOperator(a);
                if (operador.equals("NOVALIDO") || operador.equals("@r") || operador.equals("L<@r>")) {
                    //   Test.msg("Operador no valido no es un @e o L<@e> para analizar",false);

                } else {
                    Patron patron = new Patron();
                    found = true;
//                    if (nivel == 0) {
                    patron.setFather(entidad);
                    patron.setOperator(operador);
                    patron.setSon(Utilidades.convertStringToEntity(a.getNombre(), mySesion.getEntidadList()));
                    patron.setLevel(nivel);
                    if (entidad.getTabla().toLowerCase().equals(entidadPatron.getEntidad().getTabla().toLowerCase())) {
                        if (nivel != 0) {
                            patron.setLevel(0);
                            Test.msg("---------------->>>Cambio a nivel 0", false);
                            nivel = 0;
                        }

                    }

                    patronList.add(patron);
                    nivel++;
                    Test.msg("-------------------[Invoco recursivamente " + patron.getSon().getTabla() + "]-------------", false);
                    generarPatron(patron.getSon());
                }
            }
            if (!Utilidades.tienePatron(entidadPatron, entidadPatronList)) {

                Test.msg("....................Agrego el patron [" + entidadPatron.getEntidad().getTabla() + "]}}}}---", false);
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
