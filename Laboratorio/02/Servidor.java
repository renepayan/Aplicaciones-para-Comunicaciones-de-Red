import java.net.*;
import java.io.*;
public class Servidor {
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(7000);
            Socket cl = s.accept();
            System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
            DataInputStream dis= new DataInputStream(cl.getInputStream());   
            String nombre = dis.readUTF();
            System.out.println("Recibimos el archivo:"+nombre+" se guardara como: recibido_"+nombre); 
            long tam= dis.readLong();
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("recibido_"+nombre));
            long recibidos=0;
            int n, porcentaje;
            byte[] b = new byte[1024];
            while(recibidos < tam){
                long restantes = Math.min(1024L, (tam-recibidos));
                n = dis.read(b, 0, (int)restantes);
                dos.write(b,0,n);
                dos.flush();
                recibidos = recibidos + n;
                porcentaje = (int)(recibidos*100/tam);
            }//While
            //Aqui deserializo
            System.out.println("\n\nArchivo recibido.");
            dos.close();
            FileInputStream file = new FileInputStream("recibido_"+nombre);
            ObjectInputStream in = new ObjectInputStream(file);
            Consola recibida = (Consola)in.readObject();
            in.close();
            file.close();
            System.out.println("Objeto recibido deserializado: "+recibida.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
