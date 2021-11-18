/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.payansoftware.apcr.practica1.servidor;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import static java.lang.Integer.max;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.payansoftware.apcr.practica1.clases.Compra;
import org.payansoftware.apcr.practica1.clases.Producto;
import org.payansoftware.apcr.practica1.servidor.intermediarios.ProductoDatos;

/**
 *
 * @author René Payán Téllez
 */
public class Servidor {
    private static final int NUM_PUERTO = 3016;
    private static final String NOMBRE_DB = "db_practica1.db";
    private static int transaccionActual = 0;
   
    private static List<Producto> cargarProductos(Conexion con){       
        List<Producto> retorno = new ArrayList<Producto>();       
        try{
           retorno = ProductoDatos.getAllProductos(con);
        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
    private static void guardarProductos(List<Producto>productos, Conexion con) throws Exception{
        for(Producto p: productos){
            try{
                ProductoDatos.updateProducto(p, con);
            }catch(Exception e){
                e.printStackTrace();
            }
        }   
    }
    private static void enviarCatalogo(DataOutputStream salida,List<Producto>productos) throws Exception{
        salida.writeInt(productos.size());        
        for(Producto p: productos){            
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(p);
            oos.flush();
            byte [] data = bos.toByteArray();
            salida.writeLong(data.length);
            //while(true){       
            int bytesEnviados = 0;    
            while(bytesEnviados<data.length){
                int offset = bytesEnviados;
                int longitud = Math.min(1024, data.length-bytesEnviados);
                salida.write(data,offset,longitud);
                bytesEnviados+=longitud;
                //System.out.println("Llevo "+bytesEnviados+" bytes enviados");
            }            
            //}
        }
    }
    private static List<Compra> recibirCarrito(DataInputStream entrada) throws Exception{
        int cuantasCompras = entrada.readInt();
        List<Compra>compras = new ArrayList<Compra>();
        System.out.println("Voy a recibir "+cuantasCompras+" compras");
        for(int i = 0; i < cuantasCompras; i++){
            long cantidadDeBytes = entrada.readLong();
            System.out.println("Esta compra tiene "+cantidadDeBytes+" bytes");
            byte[] entradaDatos = new byte[(int)cantidadDeBytes];
            int bytesRecibidos = 0;
            while(bytesRecibidos<cantidadDeBytes){
                int offset = bytesRecibidos;
                int longitud = Math.min(1024, (int)cantidadDeBytes-bytesRecibidos);
                int leidosEnEstaPasada = entrada.read(entradaDatos,offset,longitud);
                bytesRecibidos+=leidosEnEstaPasada;                   
            }                
            ByteArrayInputStream bis = new ByteArrayInputStream(entradaDatos);
            ObjectInputStream ois = new ObjectInputStream(bis);
            Compra c = (Compra)ois.readObject();
            compras.add(c);             
        }
        return compras;
    }
    private static boolean validaCompras(List<Compra> compras,List<Producto>productos){
        for(Compra c: compras){
            if(c.getIdProducto() <= 0){
                return false;
            }else{
                boolean loEncontre = false;
                for(Producto p: productos){
                    if(p.getId() == c.getIdProducto()){
                        loEncontre = true;
                        if(p.getDisponibilidad()<c.getCantidad())
                            return false;
                    }
                }
                if(!loEncontre)
                    return false;
            }
        }
        return true;
    }
    private static void actualizarProductos(List<Compra> compras,List<Producto>productos, Conexion conexion){        
        for(Compra c: compras){
            for(Producto p: productos){
               if(p.getId() == c.getIdProducto()){                  
                    p.setDisponibilidad(p.getDisponibilidad() - c.getCantidad());
                    try{
                        ProductoDatos.updateProducto(p, conexion);
                    }catch(Exception e){
                       e.printStackTrace();
                    }
                }
            }        
        }
    }
    private static String crearTicket(List<Compra>compras,List<Producto>productos) throws Exception{
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        DateTimeFormatter otroFormato = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        DecimalFormat df = new DecimalFormat("#.##");
        Document document = new Document();
        int aleatorio = ThreadLocalRandom.current().nextInt(1, 6969696 + 1);
        String fechaFea = myDateObj.format(otroFormato);
        String fechaBonita = myDateObj.format(myFormatObj);
        String nombreDocumento = "ticket"+transaccionActual+"_"+aleatorio+"_"+fechaFea+".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(nombreDocumento));
        document.open();    
        PdfPTable table = new PdfPTable(5);
        PdfPCell cell1 = new PdfPCell(new Phrase(new Chunk("Producto",FontFactory.getFont(FontFactory.TIMES_BOLD))));
        cell1.setColspan(2);
        table.addCell(cell1);
        table.addCell(new PdfPCell(new Phrase(new Chunk("Precio",FontFactory.getFont(FontFactory.TIMES_BOLD)))));
        table.addCell(new PdfPCell(new Phrase(new Chunk("Cantidad",FontFactory.getFont(FontFactory.TIMES_BOLD)))));
        table.addCell(new PdfPCell(new Phrase(new Chunk("Sub Total",FontFactory.getFont(FontFactory.TIMES_BOLD)))));        
        double total = 0;
        for(Compra c: compras){
            Producto p = null;
            for(Producto p2: productos){
                if(p2.getId() == c.getIdProducto()){
                    p = p2;
                }
            }
            if(p != null){
                Image ig = Image.getInstance(p.getFoto());
                ig.scaleAbsolute(125f, 240f);
                table.addCell(ig);
                table.addCell(p.getNombre());
                table.addCell("$"+df.format(p.getPrecio())+" MXN");
                table.addCell(c.getCantidad()+"");
                double subtotal = c.getCantidad()*p.getPrecio();
                total+=subtotal;
                table.addCell("$"+df.format(subtotal)+" MXN");
            }
        }
        PdfPCell cellTotal = new PdfPCell(new Phrase(new Chunk("Total",FontFactory.getFont(FontFactory.TIMES_BOLD))));
        cellTotal.setColspan(4);
        table.addCell(cellTotal);
        table.addCell(new PdfPCell(new Phrase(new Chunk("$"+df.format(total)+" MXN",FontFactory.getFont(FontFactory.TIMES_BOLD)))));
        //Chunk chunk = new Chunk("Producto                                 Costo Unitario           Subtotal", font);
        //Image ig = Image.getInstance();
        document.addTitle("Ticket "+transaccionActual);         
        Paragraph titulo = new Paragraph("Detalles finales de la compra "+transaccionActual, FontFactory.getFont(FontFactory.TIMES_ROMAN,25, Font.BOLD, BaseColor.BLACK));         
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Compra realizada: "+fechaBonita));
        document.add(new Paragraph("Folio: "+transaccionActual));
        document.add(new Paragraph("Identificador unico aleatorio: "+aleatorio));
        document.add(new Paragraph("Productos comprados:"));
        document.add(new Paragraph(" "));
        document.add(table);
        document.add(new Paragraph(" "));
        document.add(new Paragraph("El monto debe ser liquidado en su totalidad"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));
        Font font2 = new Font(FontFamily.TIMES_ROMAN, 10f, Font.NORMAL);   
        Paragraph copyright = new Paragraph("© 2021 - 2025, E.S.C.O.M.", FontFactory.getFont(FontFactory.TIMES_ROMAN,10, Font.NORMAL, BaseColor.BLACK));
        copyright.setAlignment(Element.ALIGN_CENTER);
        document.add(copyright);
        document.close();
        return nombreDocumento;       
    }
    private static void enviarTicket(DataOutputStream salida, String archivo) throws Exception{
        salida.writeUTF(archivo);
        File archivoAEnviar = new File(archivo);
        DataInputStream dis= new DataInputStream(new FileInputStream(archivoAEnviar));
        long tamanio = archivoAEnviar.length();
        salida.writeLong(tamanio);
        byte[] b = new byte[1024];
        long enviados = 0;
        int n;
        while(enviados < tamanio){
            n = dis.read(b);
            salida.write(b,0,n);
            salida.flush();
            enviados = enviados+n;
        }
    }
    private static void procesaCliente(Socket cliente,List<Producto>productos, Conexion conexion){
        System.out.println("Conexion desde: "+cliente.getRemoteSocketAddress());       
        try{
            DataInputStream entrada = new DataInputStream(cliente.getInputStream());
            DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
            boolean continuar = true;
            while(continuar){             
                int comando = entrada.readInt(); 
                System.out.println("Comando recibido: "+comando);
                switch(comando){
                    case 1:
                        enviarCatalogo(salida,productos);
                        break;
                    case 2:
                        List<Compra> compras = recibirCarrito(entrada);
                        if (validaCompras(compras, productos)) {
                            transaccionActual += 1;
                            actualizarProductos(compras, productos, conexion);                           
                            salida.writeInt(1);
                            String nombreArchivo = crearTicket(compras, productos);
                            enviarTicket(salida, nombreArchivo);
                        } else {
                            salida.writeInt(0);
                        }
                        break;
                    case 3:
                        continuar = false;
                        break;
                }
            }
            salida.close();
            entrada.close(); 
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try {
                cliente.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) throws Exception{
        Conexion conexion = new Conexion(NOMBRE_DB);
        conexion.conectar();
        ServerSocket servidor = new ServerSocket(NUM_PUERTO);
        List<Producto> productos = cargarProductos(conexion);        
        while(true){
            Socket cliente = servidor.accept();
            procesaCliente(cliente,productos,conexion);           
        }
        //conexion.close();
    }
}
