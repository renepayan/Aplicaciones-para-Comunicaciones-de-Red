/**
 * @author Payán Téllez René 3CV16
 * @email rpayant1500@alumno.ipn.mx
 * @create date 19/10/2021 17:25
 * @modify date 19/10/2021 
 * @desc [Servidor echo, utilizando como referencia el ejemplo 1 visto en clase]
 */
/**
 * Importacion de paquetes
 */
import java.net.*;
import java.io.*;

public class Servidor {
    /**
     * Esta funcion retorna procesa a un cliente
     * @param {Socket} [cliente] El socket del cliente a procesar
     */
    private static void procesaCliente(Socket cliente) throws Exception{
        System.out.println("Conexion establecida desde "+cliente.getInetAddress()+":"+cliente.getPort());   //Imprimo los datos de la conexion
        String mensaje = "René Payán Téllez";                                                               //Creo el mensaje a enviar al cliente
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(cliente.getOutputStream()));                //Obtengo el flujo de salida (Servidor -> Cliente)
        pw.println(mensaje);                                                                                //Envio el mensaje al buffer
        pw.flush();                                                                                         //Envio el buffer al cliente
        BufferedReader br = new BufferedReader(new InputStreamReader(cliente.getInputStream()));            //Obtengo el flujo de entrada (Cliente -> Servidor)
        String mensajeEntrante = br.readLine();                                                             //Leo el mensaje del cliente
        System.out.println("Mensaje desde el cliente: "+mensajeEntrante);                                   //Imprimo el mensaje
        br.close();                                                                                         //Cierro el flujo de entrada
        pw.close();                                                                                         //Cierro el flujo de salida
        cliente.close();                                                                                    //Cierro el socket
    }
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(1234);        //Creo el socket servidor
            while(true){                                    //Por la eternidad proceso clientes
                System.out.println("Esperando un cliente"); //Imprimo el mensaje de esperando un cliente en consola
                Socket cliente = s.accept();                //Acepto al cliente
                procesaCliente(cliente);                    //Cuando acepto al cliente, llamo a una funcion que lo procese
            }

        }catch(Exception e){
            e.printStackTrace();                           //Imprimo la excepcion
        }
    }    
}
