/**
 * @author René Payán Téllez
 * En esta interfaz se definen los metodos remotos que pueden ser invocados via RMI
 */
import java.rmi.Remote; //La interface tiene que extender de la clase Remote
import java.rmi.RemoteException; //Los metodos pueden tener Throws de RemoteException

public interface InterfazRMI extends Remote{
    //Aqui inician los metodos que se podran utilizar en el cliente RMI
    /**
     * Este metodo saluda al usuario desde RMI
     * @throws RemoteException Error al invocar el metodo remoto
     */
    public void saludar() throws RemoteException;
    /**
     * Esta funcion suma dos enteros de 64 bits
     * @param a primer entero de 64bits a sumar
     * @param b segundo entero de 64 bits a sumar
     * @return La suma de ambos enteros de 64 bits
     * @throws RemoteException Error al invocar el metodo remoto
     */
    public long sumar(long a, long b) throws RemoteException;    
    /**
     * Esta funcion resta dos enteros de 64 bits
     * @param a Primer entero de 64 bits
     * @param b Segundo entero de 64 bits
     * @return La resta de ambos enteros
     * @throws RemoteException Error al invocar el metodo remoto
     */
    public long restar(long a, long b) throws RemoteException;
    /**
     * Esta funcion abre el archivo "archivoDePrueba.txt" y retorna su contenido
     * @return El contenido del archivo, archivo de prueba
     * @throws RemoteException Error al invocar el metodo remoto
     */
    public String leerArchivo() throws RemoteException;
}