package org.payansoftware.apcr.practica1.servidor.intermediarios;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.payansoftware.apcr.practica1.clases.Producto;
import org.payansoftware.apcr.practica1.servidor.Conexion;

/**
 *
 * @author rene
 */
public class ProductoDatos {
    static Producto getProductoById(int id, Conexion conexion)throws Exception{
        Producto retorno = null;
        try{
           PreparedStatement pstSelect = conexion.getConexion().prepareStatement("SELECT id_producto, nombre, descripcion, foto, disponibilidad, precio FROM productos WHERE id_producto = ? LIMIT 1");
           pstSelect.setInt(1, id);
           ResultSet rs = pstSelect.executeQuery();
           while(rs.next()){
               retorno = new Producto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBytes(4),rs.getInt(5),rs.getDouble(6));
           }
           rs.close();
           pstSelect.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
    public static List<Producto>getAllProductos(Conexion conexion)throws Exception{
        List<Producto>retorno = new ArrayList<Producto>();
        try{
           PreparedStatement pstSelect = conexion.getConexion().prepareStatement("SELECT id_producto, nombre, descripcion, foto, disponibilidad, precio FROM productos");
           ResultSet rs = pstSelect.executeQuery();
           while(rs.next()){
               retorno.add(new Producto(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getBytes(4),rs.getInt(5),rs.getDouble(6)));
           }
           rs.close();
           pstSelect.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return retorno;
    }
    public static void updateProducto(Producto producto, Conexion conexion)throws Exception{
        PreparedStatement pstmUpdate = conexion.getConexion().prepareStatement("UPDATE productos SET nombre = ?, descripcion = ?, foto = ?, disponibilidad = ?, precio = ? WHERE id_producto = ?");
        pstmUpdate.setString(1, producto.getNombre());
        pstmUpdate.setString(2, producto.getDescripcion());
        pstmUpdate.setBytes(3, producto.getFoto());
        pstmUpdate.setInt(4, producto.getDisponibilidad());
        pstmUpdate.setDouble(5,producto.getPrecio());
        pstmUpdate.setInt(6, producto.getId());
        pstmUpdate.executeUpdate();
        pstmUpdate.close();
    }
    public static void insertProducto(Producto producto, Conexion conexion) throws Exception{
        PreparedStatement pstmInsert = conexion.getConexion().prepareStatement("INSERT INTO productos(nombre, descripcion, foto, disponibilidad, precio) VALUES (?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);        
        pstmInsert.setString(1, producto.getNombre());
        pstmInsert.setString(2, producto.getDescripcion());
        pstmInsert.setBytes(3, producto.getFoto());
        pstmInsert.setInt(4, producto.getDisponibilidad());
        pstmInsert.setDouble(5, producto.getPrecio());
        pstmInsert.executeUpdate();        
        ResultSet rsId = pstmInsert.getGeneratedKeys();
        
        if (rsId.next()) {
            producto.setId((int) rsId.getInt(1));
        }
        rsId.close();
        pstmInsert.close();
    }
}
