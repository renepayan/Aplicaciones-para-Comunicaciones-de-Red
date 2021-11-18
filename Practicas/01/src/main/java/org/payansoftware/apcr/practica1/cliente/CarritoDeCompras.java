/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package org.payansoftware.apcr.practica1.cliente;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import org.payansoftware.apcr.practica1.clases.Compra;
import org.payansoftware.apcr.practica1.clases.Producto;

/**
 *
 * @author rene
 */
public class CarritoDeCompras extends javax.swing.JFrame {    
    private Socket conexion;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private static ArrayList<Compra>compras = new ArrayList<Compra>();
    private ListaDeProductos lista;
    private List<Producto> productos;
    public void setProductos(List<Producto> productos){
        this.productos = productos;
    }
    public static boolean buscarProducto(int idProducto){
        for(Compra c: compras){
            if(c.getIdProducto() == idProducto)
                return true;
        }
        return false;
    }
    public static int getCantidadByProducto(int idProducto){
        for(Compra c: compras){            
            if(c.getIdProducto() == idProducto){
                return c.getCantidad();
            }
        }
        return 0;
    }
    public static void eliminarDelCarrito(int idProducto){
        for(Compra c: compras){            
            if(c.getIdProducto() == idProducto){
                compras.remove(c);
                break;
            }
        }
    }
    public static void establecerCantidadEnElCarrito(int idProducto, int cantidad){
        for(Compra c: compras){            
            if(c.getIdProducto() == idProducto){
                c.setCantidad(cantidad);
                break;
            }
        }
    }
    public static void quitarDelCarrito(int idProducto){
        for(Compra c: compras){            
            if(c.getIdProducto() == idProducto){
                c.setCantidad(c.getCantidad()-1);
                break;
            }
        }
    }
    public static void agregarAlCarrito(int idProducto){
        boolean sePuede = false;
        for(Compra c: compras){            
            if(c.getIdProducto() == idProducto){
                sePuede = true;
                c.setCantidad(c.getCantidad()+1);
                break;
            }
        }
        if(!sePuede)
            compras.add(new Compra(idProducto, 1));
    }
    private Producto obtenerProducto(int id){
        for(Producto p : productos){
            if(p.getId() == id)
                return p;
        }
        return null;
    }
    public void crearTabla(){      
        DecimalFormat df = new DecimalFormat("#.##");
        DefaultTableModel model = (DefaultTableModel) tblProductos.getModel();        
        model.setRowCount(0);
        double total = 0;
        for(Compra c: compras){
            Producto p = obtenerProducto(c.getIdProducto());
            double subtotal = c.getCantidad()*p.getPrecio();
            total+=subtotal;
            model.addRow(new Object[]{p.getNombre(), "$"+df.format(p.getPrecio())+" MXN", c.getCantidad(),"$"+df.format(subtotal)+" MXN"});
        }
        model.addRow(new Object[]{"","","",""});
        model.addRow(new Object[]{"Total:","","","$"+df.format(total)+" MXN"});
    }
    /**
     * Creates new form CarritoDeCompras
     */
    public CarritoDeCompras(List<Producto> productos, ListaDeProductos lista, Socket conexion, DataOutputStream salida,DataInputStream entrada) {
        this.lista = lista;
        this.productos = productos;
        this.salida = salida;
        this.entrada = entrada;
        this.conexion = conexion;
        initComponents();        
        crearTabla();        
        tblProductos.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JTable table =(JTable) evt.getSource();
                Point point = evt.getPoint();
                int row = table.rowAtPoint(point);
                int column = table.columnAtPoint(point);
                if (evt.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    if(column == 2){
                        if(row < table.getRowCount()-2){
                            Compra compraSeleccionada = compras.get(row);
                            Producto productoSeleccionado = obtenerProducto(compraSeleccionada.getIdProducto());
                            try{
                                int cantidadActual = compraSeleccionada.getCantidad();
                                int cantidadNueva = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la nueva cantidad en el carrito", cantidadActual+""));
                                int disponibilidadCompleta = productoSeleccionado.getDisponibilidad()+cantidadActual;
                                if(cantidadNueva > 0){
                                    if(cantidadNueva <= disponibilidadCompleta){
                                        CarritoDeCompras.establecerCantidadEnElCarrito(compraSeleccionada.getIdProducto(), cantidadNueva);
                                        productoSeleccionado.setDisponibilidad(disponibilidadCompleta-cantidadNueva);
                                        crearTabla();
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
                }
            }
        });
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnPagar = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Carrito de compras");

        btnPagar.setBackground(new java.awt.Color(0, 255, 0));
        btnPagar.setText("Pagar");
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        btnRegresar.setText("Regresar a la lista de articulos");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Precio", "Cantidad", "SubTotal"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProductos);
        if (tblProductos.getColumnModel().getColumnCount() > 0) {
            tblProductos.getColumnModel().getColumn(1).setPreferredWidth(50);
            tblProductos.getColumnModel().getColumn(2).setPreferredWidth(20);
        }

        jScrollPane2.setViewportView(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegresar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPagar))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 823, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnPagar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private int enviarListaDeCompras() throws Exception{
        salida.writeInt(2);
        System.out.println("Envie el comando");
        salida.writeInt(compras.size());       
        System.out.println("Envie la cantidad de compras");
        for(Compra c: compras){                                    
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(c);
            oos.flush();
            byte [] data = bos.toByteArray();
            salida.writeLong(data.length);                        
            int bytesEnviados = 0;    
            while(bytesEnviados<data.length){
                int offset = bytesEnviados;
                int longitud = Math.min(1024, data.length-bytesEnviados);
                salida.write(data,offset,longitud);
                bytesEnviados+=longitud;              
            }                                   
        }
        int confirmacion = entrada.readInt();
        System.out.println("Recibí la confirmacion "+confirmacion);
        return confirmacion;
    }
    private void recibirTicket() throws Exception{    
        String nombre = entrada.readUTF();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Donde desea guardar el ticket");  
        fileChooser.setSelectedFile(new File(nombre));
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setFileFilter(new FileNameExtensionFilter("archivo pdf","pdf"));
        fileChooser.setAcceptAllFileFilterUsed(false);
        int userSelection = fileChooser.showSaveDialog(null); 
        File fileToSave = new File(nombre);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();             
        }
        long tamanio = entrada.readLong();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileToSave));
        long recibidos=0;
        int n;
        byte[] b = new byte[1024];
        while(recibidos < tamanio){
            long restantes = Math.min(1024L, (tamanio-recibidos));
            n = entrada.read(b, 0, (int)restantes);
            dos.write(b,0,n);
            dos.flush();
            recibidos = recibidos + n;
        }
        JOptionPane.showMessageDialog(null, "El ticket se guardo en: "+fileToSave.getAbsolutePath(), "Exito", JOptionPane.INFORMATION_MESSAGE);
    }
    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        // TODO add your handling code here:
        int confirmacion = 0;
        try{
            confirmacion = enviarListaDeCompras();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(confirmacion == 1){
            JOptionPane.showMessageDialog(null, "La compra se realizo exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            try{
                recibirTicket();
            }catch(Exception e){
                e.printStackTrace();
            }
            this.compras.clear();           
        }else{
            //Mostrar error de compra
            JOptionPane.showMessageDialog(null, "No se puede realizar la compra, posiblemente algun articulo se quedo sin existencia", "Error", JOptionPane.ERROR_MESSAGE);           
        }   
        lista.cargarProductos();
        lista.mostrarProductos();
        this.setVisible(false);
        lista.setVisible(true);        
    }//GEN-LAST:event_btnPagarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.lista.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnPagar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables
}
