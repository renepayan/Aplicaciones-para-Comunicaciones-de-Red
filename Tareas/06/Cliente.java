/**
 * @author Payán Téllez René 3CV16
 * @email rpayant1500@alumno.ipn.mx
 * @create date 19/10/2021 17:26
 * @modify date 19/10/2021 
 * @desc [Cliente echo, utilizando como referencia el ejemplo 1 visto en clase]
 */

 /**
 * Importacion de paquetes
 */
import java.net.*;
import java.io.*;


public class Cliente{
    public static void main(String[] args){
        try{
            BufferedReader brConsola = new BufferedReader(new InputStreamReader(System.in));                //Creo el buffer para poder leer la consola
            System.out.print("Escriba la direccion del servidor: ");                                        //Imprimo el mensaje para recibir la direccion del servidor
            String host = brConsola.readLine();                                                             //Leo la terminal para obtener el host
            System.out.print("\n\nEscriba el numero de puerto: ");                                          //Imprimo el mensaje para recibir el puerto del servidor
            int puerto = Integer.parseInt(brConsola.readLine());                                            //Leo la consola para obtener el puerto y lo convierto a entero
            Socket cl = new Socket(host, puerto);                                                           //Creo el socket cliente y lo conecto al servidor
            BufferedReader brServidor = new BufferedReader(new InputStreamReader(cl.getInputStream()));     //Obtengo el flujo de entrada (Servidor -> Cliente)
            String mensaje = brServidor.readLine();                                                         //Leo el mensaje del servidor
            System.out.println("Recibimos un mensaje desde el servidor");
            System.out.println("Mensaje: "+mensaje);                                                        //Imprimo el mensaje            
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));                 //Obtengo el flujo de salida (Cliente -> Servidor)
            pw.println(mensaje);                                                                            //Envio el mensaje al buffer
            pw.flush();                                                                                     //Envio el buffer al cliente
            pw.close();                                                                                     //Cierro el flujo de salida
            brServidor.close();                                                                             //Cierro el flujo de entrada
            cl.close();                                                                                     //Cierro el socket
            brConsola.close();                                                                              //Cierro el buffer de la consola
        }catch(Exception e){
            e.printStackTrace();                           //Imprimo la excepcion
        }
    }
}