/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.jmoordbwizard.menu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author avbravo
 */
public class MyMenu {
 private String name;
 private List<MySubmenu> submenu= new ArrayList<>();

    public MyMenu() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MySubmenu> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<MySubmenu> submenu) {
        this.submenu = submenu;
    }
 
 
}
