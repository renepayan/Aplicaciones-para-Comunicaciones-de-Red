/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.laboratorio03.ejercicio1;

/**
 *
 * @author rene
 */
public class Principal {
    private static class HiloPares extends Thread{
        @Override
        public void run(){
            for(int i = 2; i <= 10; i+=2){
                System.out.println(i);
            }
        }
    }
    private static class HiloImpares implements Runnable{

        @Override
        public void run() {
            for(int i = 1; i <= 10; i+=2){
                System.out.println(i);
            }
        }
        
    }
    public static void main(String[] args) throws Exception{
        HiloPares hiloPares1 = new HiloPares();
        HiloPares hiloPares2 = new HiloPares();
        HiloImpares hiloImpares1 = new HiloImpares();
        HiloImpares hiloImpares2 = new HiloImpares();
        hiloPares1.start();
        hiloPares2.start();
        new Thread(hiloImpares1).start();
        new Thread(hiloImpares1).start();   
    }
}
