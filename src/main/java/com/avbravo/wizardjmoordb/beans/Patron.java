/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.beans;

/**
 *
 * @author avbravo Persona @e Pais father =Persona operator = @e son= Pais
 * level= 0 father
 */
public class Patron {

    private Entidad father;
    private String operator;
    private Entidad son;
    private Integer level;
    private String regla;

   

    public Patron() {
    }

    public Entidad getFather() {
        return father;
    }
 public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public void setFather(Entidad father) {
        this.father = father;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Entidad getSon() {
        return son;
    }

    public void setSon(Entidad son) {
        this.son = son;
    }

    public String getRegla() {
        return regla;
    }

    public void setRegla(String regla) {
        this.regla = regla;
    }

   

}
