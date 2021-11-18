import java.io.Serializable;

public class Consola implements Serializable{
    private transient int anioLanzamiento;
    private transient boolean estaPrendida;
    private double precio;
    private float teraFlops;
    private String nombre;
    private transient char tier;
    public Consola(int anioLanzamiento, boolean estaPrendida, double precio, float teraFlops, String nombre, char tier){
        this.anioLanzamiento = anioLanzamiento;
        this.estaPrendida = estaPrendida;
        this.precio = precio;
        this.teraFlops = teraFlops;
        this.nombre = nombre;
        this.tier = tier;
    }
    public int getAnioLanzamiento(){
        return this.anioLanzamiento;
    }
    public void setAnioLanzamiento(int anioLanzamiento){
        this.anioLanzamiento = anioLanzamiento;
    }

    public boolean isEstaPrendida(){
        return this.estaPrendida;
    }
    public void setEstaPrendida(boolean estaPrendida){
        this.estaPrendida = estaPrendida;
    }

    public double getPrecio(){
        return this.precio;
    }
    public void setPrecio(double precio){
        this.precio = precio;
    }

    public float getTeraFlops(){
        return this.teraFlops;
    }
    public void setTeraFlops(float teraFlops){
        this.teraFlops = teraFlops;
    }

    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public char getTier(){
        return this.tier;
    }
    public void setTier(char tier){
        this.tier = tier;
    }
    @Override
    public String toString(){
        return "Nombre: "+this.nombre+"\nPrecio: "+this.precio+"\nEsta prendida: "+(this.estaPrendida?"Si":"No")+"\nTeraFlops de potencia: "+this.teraFlops+"\nGrado: "+this.tier;
    }
}
