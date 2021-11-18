/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.practica1.cliente;

import java.net.Socket;

/**
 *
 * @author rene
 */
public class Cliente {
    public static void main(String[] args) throws Exception{
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SolicitarDatosDeConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SolicitarDatosDeConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SolicitarDatosDeConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SolicitarDatosDeConexion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SolicitarDatosDeConexion sddc = new SolicitarDatosDeConexion();
        sddc.setVisible(true);     
        //System.out.println("pase");
        /*Socket conexion = sddc.getConexion();
        System.out.println("Ya tengo el socket");
        ListaDeProductos ldp = new ListaDeProductos(conexion);
        ldp.cargarProductos();
        ldp.setVisible(true);*/
    }
}
