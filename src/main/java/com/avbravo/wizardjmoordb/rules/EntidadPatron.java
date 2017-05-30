/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.rules;

import com.avbravo.wizardjmoordb.beans.Entidad;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avbravo
 * List<Patron> son los patrones por nivel
 * Persona @e Pais
 * List<Patron> 
 * Entidad= Persona
 * Las secuencias son las reglas filtradas con solo @ que indican cual sera la regla a aplicar
 * Secuencia= @e,
 * Secuentia:@e @e
 * Secuencia:@e L<@e>,
 * 
 */
public class EntidadPatron {
    private Entidad entidad;
    private List<Patron> patron = new ArrayList<>();
    private List<Draw> draw = new ArrayList<>();
        
    private Integer size;

    public EntidadPatron() {
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public List<Patron> getPatron() {
        return patron;
    }

    public void setPatron(List<Patron> patron) {
        this.patron = patron;
    }

    public List<Draw> getDraw() {
        return draw;
    }

    public void setDraw(List<Draw> draw) {
        this.draw = draw;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

   
}
