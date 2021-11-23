/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.actividad8;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 *
 * @author rene
 */
public class CHolaD {
    public static class Trabajador extends Thread{ //Creo una clase para recibir los mensajes del servidor
        @Override
        public void run(){
            try{
                DatagramSocket s = new DatagramSocket(2001); //Creo el datagrama servidor
                for(;;){
                     DatagramPacket p = new DatagramPacket(new  byte[2000],2000); //Se crea un paquete de datagrama de 2000 bytes
                    s.receive(p); //Se recibe el paquete desde el servidor
                    System.out.println("Datagrama  recibido desde"+p.getAddress()+":"+p.getPort());
                    String msj= new String(p.getData(),0,p.getLength()); //Se crea un string con el mensaje
                    System.out.println("Con  el mensaje:"+ msj);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        try{
            DatagramSocket cl = new DatagramSocket(); //Creo el cliente de datagrama           
            Trabajador t = new Trabajador();
            t.start();
            System.out.print("Cliente  iniciado, escriba un mensaje de saludo:");
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in)); //Preparo la lectura de la consola
            String mensaje = br.readLine(); //Leo una linea desde la consola                        
            byte[] b = mensaje.getBytes("utf-8"); //obtengo los bytes del string
            String dst= "127.0.0.1"; //la ip de destino
            int pto = 2000; //Puerto de destino
            long bytesRestantes = b.length;
            int byteDeInicioActual = 0;
            int tamanio = 0;
            while(bytesRestantes > 0){                
                 DatagramPacket p = new DatagramPacket(b,byteDeInicioActual,tamanio,InetAddress.getByName(dst),pto); //Creo un paquete de datagrama, le pongo los bytes del mensaje y el destino
                 cl.send(p); //Lo envio con el socket
                 bytesRestantes-=tamanio; //Le resto lo que ya envie
                 byteDeInicioActual+=tamanio; //Le sumo lo que envie al offset
                 tamanio = Math.min(5, (int)bytesRestantes); //Obtengo el tamanio del siguiente chunk   
            }
            cl.close(); //Cerrar el datagrama
            t.join();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
