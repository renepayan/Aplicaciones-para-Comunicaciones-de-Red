import java.net.*;
import java.io.*;
public class ServidorArchivo{
    public static void main(String[] args){
        try{
            ServerSocket s = new ServerSocket(7000);
            for(;;){
                Socket cl = s.accept();
                System.out.println("Conexión establecida desde"+cl.getInetAddress()+":"+cl.getPort());
                DataInputStream dis= new DataInputStream(cl.getInputStream());    
                int cantidadDeArchivos;
                cantidadDeArchivos = dis.readInt();
                System.out.println("Se recibiran "+cantidadDeArchivos+" archivos");  
                for(int i = 0; i < cantidadDeArchivos;i++){          
                    byte[] b = new byte[1024];
                    String nombre = dis.readUTF();
                    System.out.println("Recibimos el archivo:"+nombre+ "("+(i+1)+"/"+cantidadDeArchivos+")");
                    long tam= dis.readLong();
                    DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
                    long recibidos=0;
                    int n, porcentaje;
                    while(recibidos < tam){
                        long restantes = Math.min(1024L, (tam-recibidos));
                        n = dis.read(b, 0, (int)restantes);
                        dos.write(b,0,n);
                        dos.flush();
                        recibidos = recibidos + n;
                        porcentaje = (int)(recibidos*100/tam);
                    }//While
                    System.out.println("\n\nArchivo recibido.");
                    dos.close();
                }
                dis.close();
                cl.close();
            }
        }catch(Exception e){
            e.printStackTrace();
        }//catch
    }
}