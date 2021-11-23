/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.actividad8;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author rene
 */
public class SHolaD {
    public static void main(String[] args){
        try{
            DatagramSocket s = new DatagramSocket(2000); //Primero se inicializa el servidor en el puerto 2000              
            System.out.println("Servidor  iniciado, esperando cliente");
            for(;;){ //Por siempre se procesan conexiones
                DatagramPacket p = new DatagramPacket(new  byte[2000],2000); //Se crea un paquete de datagrama de 2000 bytes
                s.receive(p); //Se recibe el paquete desde el servidor
                System.out.println("Datagrama  recibido desde"+p.getAddress()+":"+p.getPort());
                String msj= new String(p.getData(),0,p.getLength()); //Se crea un string con el mensaje
                System.out.println("Con  el mensaje:"+ msj);
                byte[]b = msj.getBytes("utf-8");
                DatagramPacket respuesta = new DatagramPacket(b,b.length,p.getAddress(),2001); //Creo un paquete de datagrama, apuntando al cliente en el puerto 2001 para responderle
                s.send(respuesta); //Envio la respuesta
            }//for
            //s.close();
        }catch(Exception e){
            e.printStackTrace();
        } //catch
    }//main
}
