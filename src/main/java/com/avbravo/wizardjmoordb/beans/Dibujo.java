/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.beans;

/**
 *
 * @author avbravo
 * Genera en cada elemento un caracter
 * [
 *  Persona
 * TV{
 * [
 * Pais
 * ]
 * }
 * ]
 * 
 */
public class Dibujo {
    private String draw;

    public Dibujo() {
    }

    public Dibujo(String draw) {
        this.draw = draw;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }
    
    
    
    
}
