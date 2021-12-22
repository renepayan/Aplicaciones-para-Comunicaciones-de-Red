
import java.rmi.Naming; //Se utilizaran el metodo lookup para establecer conexion con el servidor
public class Cliente {
    public static void main(String[] args) throws Exception{
        String url = "rmi://"+args[0]+"/EjemploRMI"; //Defino la URI a la cual se accesara
        InterfazRMI r = (InterfazRMI)Naming.lookup(url); //"Establezco" conexion con el servidor RMI
        System.out.println("Primero invoco el metodo remoto saludar");
        r.saludar(); //Ejecuto el metodo saludo
        System.out.println("Ahora el metodo remoto sumar");
        System.out.println(r.sumar(10,1)); //Ejecuto el metodo remoto de la suma
        System.out.println("Ahora el metodo remoto restar");
        System.out.println(r.restar(10,1)); //Ejecuto el metodo remoto de la resta
        System.out.println("Ahora el metodo remoto leerArchivo");
        System.out.println(r.leerArchivo()); //Ejecuto el metodo remoto que lee un archivo

    }
}
