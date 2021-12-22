/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.laboratorio03.ejercicio02;
import static java.lang.Double.max;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
/**
 *
 * @author rene
 */
public class Principal {
    public static class HiloVector extends Thread{
        private Vector vector;
        public HiloVector(Vector vector){
            this.vector = vector;
        }
        @Override
        public void run(){
            long suma = 0;
            long sumaCuadrados = 0;
            for(Object a: this.vector){
                int b = (int)a;
                suma+=b;
                sumaCuadrados+=(long)(b*b);                
            }
            System.out.println("La suma de los elementos del vector: "+suma);
            System.out.println("La suma de los cuadrados de los elementos del vector: "+sumaCuadrados);
            System.out.println("El promedio de los elementos del vector: "+(suma/this.vector.size()));
        }
    }
    public static void main(String[] args) throws Exception{
        Vector vector1 = new Vector();
        Vector vector2 = new Vector();
        int longitud1 = ThreadLocalRandom.current().nextInt(0   ,1000  + 1);
        int longitud2 = ThreadLocalRandom.current().nextInt(0   ,1000  + 1);
        for(int i = 0; i < longitud1; i++){
            int aleatorio = ThreadLocalRandom.current().nextInt(-1000, 1000 + 1);
            vector1.add(aleatorio);
        }
        for(int i = 0; i < longitud2; i++){
            int aleatorio = ThreadLocalRandom.current().nextInt(-1000, 1000 + 1);
            vector2.add(aleatorio);
        }
        HiloVector hilo1 = new HiloVector(vector1);
        HiloVector hilo2 = new HiloVector(vector2);
        hilo1.start();
        hilo2.start();
    }  
}
