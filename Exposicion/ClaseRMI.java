/**
 * @author René Payán Téllez
 * Esta es la clase que contiene la implementacion de los metodos remotos a ser invocados
 */
import java.rmi.RemoteException; //Los metodos pueden tener Throws de RemoteException
import java.io.File;  // Clase archivo para el metodo leerArchivo
import java.io.FileNotFoundException;  // Excepcion de archivo no encontrado
import java.util.Scanner; // Clase Scanner para poder leer archivos
import java.rmi.server.UnicastRemoteObject; //Clase UnicastRemoteObject, necesaria para poder hacer el bind

public class ClaseRMI extends UnicastRemoteObject implements InterfazRMI{
    public ClaseRMI() throws RemoteException{
        super();
    }    
    /**
     * Este metodo saluda al usuario desde RMI
     * @throws RemoteException Error al invocar el metodo remoto
     */
    @Override
    public void saludar() throws RemoteException{
        System.out.println("Hola mundo");
    }
    /**
     * Esta funcion suma dos enteros de 64 bits
     * @param a primer entero de 64bits a sumar
     * @param b segundo entero de 64 bits a sumar
     * @return La suma de ambos enteros de 64 bits
     * @throws RemoteException Error al invocar el metodo remoto
     */
    @Override
    public long sumar(long a, long b) throws RemoteException{
        return a+b;
    }  
    /**
     * Esta funcion resta dos enteros de 64 bits
     * @param a Primer entero de 64 bits
     * @param b Segundo entero de 64 bits
     * @return La resta de ambos enteros
     * @throws RemoteException Error al invocar el metodo remoto
     */
    @Override
    public long restar(long a, long b) throws RemoteException{
        return a-b;
    }
    /**
     * Esta funcion abre el archivo "archivoDePrueba.txt" y retorna su contenido
     * @return El contenido del archivo, archivo de prueba
     * @throws RemoteException Error al invocar el metodo remoto
     */
    @Override
    public String leerArchivo() throws RemoteException{
        String retorno = "";
        try {
            File myObj = new File("archivoDePrueba.txt");
            Scanner myReader = new Scanner(myObj);            
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              retorno+=data;              
            }
            myReader.close();
        }catch (FileNotFoundException e) {
            System.out.println("Error leyendo el archivo");         
        }
        return retorno;
    }
}