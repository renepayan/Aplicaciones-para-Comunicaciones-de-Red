import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.io.*;
public class Cliente {
    public static void main(String[] args){
        Consola ps5 = new Consola(2020, true, 14.00d, 8.5f, "PlayStation 5", 'A');
        System.out.println("Objeto: "+ps5.toString());
        try{
            FileOutputStream file = new FileOutputStream("ps5.dat");
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(ps5);
            out.close();
            file.close();
            System.out.println("El objeto se serializo");
            System.out.println("Objeto: "+ps5.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        try{
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
            System.out.printf("Escriba la dirección del servidor:");
            String host = br.readLine();
            System.out.printf("\n\nEscriba el puerto:");
            int pto = Integer.parseInt(br.readLine());
            Socket cl = new Socket(host, pto);
            File f = new File("ps5.dat");
            String archivo = f.getAbsolutePath(); //Dirección
            String nombre = f.getName(); //Nombre
            System.out.printf("\n\nEnviando archivo: %s",nombre);
            long tam= f.length();  //Tamaño                    
            DataInputStream dis= new DataInputStream(new FileInputStream(archivo));
            DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
            dos.writeUTF(nombre);
            dos.flush();               
            dos.writeLong(tam);
            dos.flush();
            byte[] b = new byte[1024];
            long enviados = 0;
            int porcentaje, n;
            while(enviados < tam){
                n = dis.read(b);
                dos.write(b,0,n);
                dos.flush();
                enviados = enviados+n;
                porcentaje = (int)(enviados*100/tam);
                System.out.print("Enviado: "+porcentaje+"%\r");
            }//While
            System.out.println("\n\nArchivo enviado");
            dis.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}