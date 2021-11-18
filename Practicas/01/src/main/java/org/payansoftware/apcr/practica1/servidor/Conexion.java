/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.practica1.servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author rene
 */
public class Conexion {
    private String nombreDB;
    private Connection conexion;
    public Conexion(String nombreDB){
        this.nombreDB = nombreDB;
    }
    public void conectar(){
        try {
            this.conexion = DriverManager.getConnection("jdbc:sqlite:"+this.nombreDB);
            if (this.conexion!=null) {
                System.out.println("Conectado");
            }
        }catch (SQLException ex) {
            System.err.println("No se ha podido conectar a la base de datos\n"+ex.getMessage());
        }  
    }
    public void close(){
        try {
            this.conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }    
    public Connection getConexion(){
        return this.conexion;
    }
}
