package Exposicion;
import java.rmi.*; //De aqui extraigo la clase Naming, usare su metodo estatico bind
public class Servidor{ //Clase servidor, esta es la clase que utilizare para recibir las peticiones
    public static void main(String[] args) throws Exception{
        String url = "rmi://localhost/EjemploRMI"; //El url del RMI, aqui se incluye el nombre con el que se identifican los metodos
        ClaseRMI obj = new ClaseRMI(); //Instancia del objeto donde se implementan los metodos, en el cliente solo se usara la interfaz 
        Naming.bind(url, obj); //Asigno al nombre del espacio los metodos
    }
}