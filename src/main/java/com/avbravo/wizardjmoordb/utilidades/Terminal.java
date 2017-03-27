/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.wizardjmoordb.utilidades;

import com.avbravo.wizardjmoordb.JSFUtil;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author avbravoserver
 */
public class Terminal {

    public static String runCommand(String comando) {
        try {

            String cmd = "mkdir a"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd);

        } catch (Exception e) {
            JSFUtil.addErrorMessage("runCommand() " + e.getLocalizedMessage());
        }
        return "";
    }

    /**
     * Ejecuta comandos y devuelve mensajes del sistema operativo
     *
     * @param comando
     * @return
     */
    public static DataInputStream runCommandMessagesOs(String comando) {
        DataInputStream dis2 = null;
        BufferedInputStream bufferedinputstream = null;

        try {

            Process process = Runtime.getRuntime().exec("mkdir a");
            InputStream inputstream = process.getInputStream();
            bufferedinputstream = new BufferedInputStream(inputstream);
            dis2 = new DataInputStream(bufferedinputstream);

         try(DataInputStream dis=new DataInputStream(bufferedinputstream);){
 
            //Leemos un numero y lo mostramos
//            System.out.println(dis.readInt());
 
            //Leemos una cadena y lo mostramos
            System.out.println(dis.readUTF());
 
            //Leemos un numero y lo mostramos
//            System.out.println(dis.readInt());
 
        }catch(IOException e){
            System.out.println("Error E/S");
        }
//leer el contenido
//            while (dis.available() != 0) {
//                System.out.println(dis.readLine());
//            }
            JSFUtil.addSuccessMessage("Runinnng");
        } catch (Exception e) {
            JSFUtil.addErrorMessage("runCommandMessagesOS() " + e.getLocalizedMessage());
        } finally {
            try {

                bufferedinputstream.close();
                dis2.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return dis2;
    }

}
