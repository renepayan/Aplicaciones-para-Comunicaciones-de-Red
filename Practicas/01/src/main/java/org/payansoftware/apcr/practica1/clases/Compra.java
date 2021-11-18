/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.practica1.clases;

import java.io.Serializable;

/**
 *
 * @author rene
 */
public class Compra implements Serializable{
    private int idProducto;
    private int cantidad;

    public Compra(int idProducto, int cantidad){
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
