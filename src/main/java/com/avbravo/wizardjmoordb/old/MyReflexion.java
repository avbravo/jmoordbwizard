/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.old;


import com.avbravo.wizardjmoordb.Login;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author avbravoserver
 */
public class MyReflexion {
    //Obtener Array con los Fields(Campos/Atributos)
    //de la Clase

    public static void lector() {
        Field[] fields = Login.class.getDeclaredFields();

        //Recorrer cada uno de los Campos en el Array
        //e imprimir su Nombre
        for (Field f : fields) {
            System.out.println(f.getName());
        }
    }

    public static void lectorValor(Login dhc) {
        try {

            Field[] fields = Login.class.getDeclaredFields();

            //Recorrer cada uno de los Campos en el Array
            //e imprimir su Nombre
            for (Field f : fields) {
                String field = f.getName();
                System.out.println("field "+f.getName() + " "+f.getType());
                if(!f.getName().equals("serialVersionUID")){
                     Method getter = dhc.getClass().getMethod("get" + String.valueOf(field.charAt(0)).toUpperCase()
                        + field.substring(1));
                System.out.println("Nombre Metodo "+getter);
               Object value = getter.invoke(dhc, new Object[0]);

               System.out.println(value);
                }
               
             
            }

        } catch (Exception e) {
            System.out.println("Error "+e);
        }

    }
}
