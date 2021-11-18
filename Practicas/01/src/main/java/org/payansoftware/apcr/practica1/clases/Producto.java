/*
    CREATE TABLE productos(id_producto INTEGER PRIMARY KEY,nombre TEXT NOT NULL,descripcion TEXT NOT NULL,foto BLOB NOT NULL,disponibilidad INTEGER NOT NULL,precio REAL NOT NULL);
 */
package org.payansoftware.apcr.practica1.clases;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import org.payansoftware.apcr.practica1.cliente.CarritoDeCompras;

/**
 *
 * @author rene
 * CREATE TABLE productos(
 *  id_producto INTEGER PRIMARY KEY,
 *  nombre TEXT NOT NULL,
 *  descripcion TEXT NOT NULL,
 *  foto BLOB NOT NULL,
 *  disponibilidad INTEGER NOT NULL,
 *  precio REAL NOT NULL
 * );
 */
public class Producto implements Serializable{
    private int id;
    private String nombre;
    private String descripcion;
    private byte[] foto;
    private int disponibilidad;
    private double precio;
    public Producto(int id, String nombre, String descripcion, byte[] foto, int disponibilidad, double precio){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.disponibilidad = disponibilidad;
        this.precio = precio;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public JPanel toJPanel(boolean estaEnLista){
        JPanel respuesta = new JPanel();        
        respuesta.setSize(150,150);
        respuesta.setBorder(BorderFactory.createLineBorder(Color.black));
        respuesta.setLayout(new BoxLayout(respuesta, BoxLayout.PAGE_AXIS));
        JLabel titulo = new JLabel(this.nombre, SwingConstants.CENTER);
        //titulo.setAlignmentX(respuesta.CENTER_ALIGNMENT);
        Font newLabelFont=new Font(titulo.getFont().getName(),Font.BOLD,titulo.getFont().getSize()+5);
        titulo.setFont(newLabelFont);
        titulo.setSize(titulo.getSize().width, 150);
        respuesta.add(titulo);        
        ByteArrayInputStream bis = new ByteArrayInputStream(this.foto);        
        try{           
            BufferedImage bi = ImageIO.read(bis);                  
            JLabel lblImg = new JLabel(new ImageIcon(Util.getScaledImage(bi,125,240)),  SwingConstants.CENTER);                        
            //lblImg.setAlignmentX(respuesta.CENTER_ALIGNMENT);
            respuesta.add(lblImg);            
        }catch(Exception e){           
            e.printStackTrace();
        }
        respuesta.add(new JLabel("Precio: $"+this.precio+"MXN"));
        respuesta.add(new JLabel("Disponibilidad: "+this.disponibilidad));
        JTextArea xta = new JTextArea(7,3);
        xta.setText(this.descripcion);        
        xta.setEditable(false);        
        JScrollPane scroll = new JScrollPane (xta, 
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        respuesta.add(scroll);        
        JPanel panelBotones = new JPanel();
        JButton boton = new JButton("Agregar al carrito");
        JLabel cantidadDeProducto = new JLabel("0");
        JButton agregarUno = new JButton("+");        
        JButton restarUno = new JButton("-");  
        JButton botonEliminar = new JButton("Eliminar del carrito");                
        cantidadDeProducto.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() >= 2){
                        try{
                            int cantidadActual = CarritoDeCompras.getCantidadByProducto(id);
                            int cantidadNueva = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la nueva cantidad en el carrito", cantidadActual+""));
                            int disponibilidadCompleta = disponibilidad+cantidadActual;
                            if(cantidadNueva > 0){
                                if(cantidadNueva <= disponibilidadCompleta){
                                    CarritoDeCompras.establecerCantidadEnElCarrito(id, cantidadNueva);
                                    disponibilidad = disponibilidadCompleta-cantidadNueva;                                    
                                    if (disponibilidad == 0) 
                                        agregarUno.setEnabled(false);
                                    else
                                        agregarUno.setEnabled(true);   
                                    
                                    if (cantidadNueva > 1) 
                                        restarUno.setEnabled(true);
                                    else
                                        restarUno.setEnabled(false);  
                                    cantidadDeProducto.setText(""+cantidadNueva);
                                }else{
                                    JOptionPane.showMessageDialog(null, "No se puede cambiar la cantidad, la nueva cantidad supera la existencia", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }else{
                                JOptionPane.showMessageDialog(null, "No se puede añadir el producto al carrito, el valor debe ser mayor que 0", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }catch(Exception ex){
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "No se pudo cambiar la cantidad, el valor introducido no es un número", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }                        
                }
            });
        boton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {        
                if(disponibilidad >= 1){
                    respuesta.setBackground(Color.green);
                    CarritoDeCompras.agregarAlCarrito(id);
                    disponibilidad -= 1;
                    panelBotones.setVisible(true);
                    panelBotones.setEnabled(true);
                    boton.setVisible(false);
                    boton.setEnabled(false);
                    cantidadDeProducto.setText("1");
                    if (disponibilidad == 0) 
                        agregarUno.setEnabled(false);
                    else
                        agregarUno.setEnabled(true);                  
                    restarUno.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "El producto se añadio al carrito", "Exito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No se puede añadir el producto al carrito, no hay mas existencia", "Error", JOptionPane.ERROR_MESSAGE);
                }                             
            }            
        });        
        agregarUno.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { 
                if(disponibilidad >= 1){
                    disponibilidad-=1;
                    CarritoDeCompras.agregarAlCarrito(id);
                    if(disponibilidad == 0)
                        agregarUno.setEnabled(false);
                    else
                        agregarUno.setEnabled(true);
                    int cantidad = CarritoDeCompras.getCantidadByProducto(id);
                    cantidadDeProducto.setText(cantidad+"");
                    if(cantidad > 1)
                        restarUno.setEnabled(true);
                    else
                        restarUno.setEnabled(false);
                }else{
                    JOptionPane.showMessageDialog(null, "No se puede añadir el producto al carrito, no hay mas existencia", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        restarUno.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                CarritoDeCompras.quitarDelCarrito(id);       
                disponibilidad+=1;                
                int cantidad = CarritoDeCompras.getCantidadByProducto(id);
                cantidadDeProducto.setText(cantidad+"");               
                if(disponibilidad >= 1)
                    agregarUno.setEnabled(true);
                else
                    agregarUno.setEnabled(false);
                if(cantidad > 1)
                    restarUno.setEnabled(true);
                else
                    restarUno.setEnabled(false);                
            }
        });
        botonEliminar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidad = CarritoDeCompras.getCantidadByProducto(id);                
                CarritoDeCompras.eliminarDelCarrito(id);
                disponibilidad+=cantidad;                
                panelBotones.setEnabled(false);
                panelBotones.setVisible(false);
                boton.setVisible(true);    
                boton.setEnabled(true);
                respuesta.setBackground(new Color(214,217,223));
                JOptionPane.showMessageDialog(null, "El producto se elimino del carrito", "Exito", JOptionPane.INFORMATION_MESSAGE);                                                                                
            }
        });
        respuesta.add(boton);        
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.PAGE_AXIS));        
        botonEliminar.setBackground(Color.red);    
        botonEliminar.setAlignmentX(panelBotones.CENTER_ALIGNMENT);
        panelBotones.add(botonEliminar);
        JPanel panelEditar = new JPanel(); 
        panelEditar.add(restarUno);
        panelEditar.add(cantidadDeProducto);
        panelEditar.add(agregarUno);        
        panelBotones.add(panelEditar);
        respuesta.add(panelBotones);
        if(estaEnLista){
            boton.setVisible(false);    
            boton.setEnabled(false);
            panelBotones.setVisible(true);
            panelBotones.setEnabled(true);
            respuesta.setBackground(Color.green);
        }else{
            panelBotones.setVisible(false);
            panelBotones.setEnabled(false);
            boton.setVisible(true);
            boton.setEnabled(true);
        }
        if(!estaEnLista && disponibilidad == 0){            
            boton.setEnabled(false);
            respuesta.setBackground(Color.gray);
        }                
        respuesta.setVisible(true);
        respuesta.setSize(50, 50);
        return respuesta;
    }
}