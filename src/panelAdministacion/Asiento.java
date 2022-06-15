package panelAdministacion;

import java.io.Serializable;

//Almacenaremos objetos asiento en el archivo Funciones.bin
public class Asiento implements Serializable{
    
    //Atributos
    private String nombre;
    private boolean disponible;
    
    //Constructor
    public Asiento(String nombre, boolean disponible) {
        this.nombre = nombre;
        this.disponible = disponible;
    }
    
    //Metodos set
    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    //Metodos get
    public String getNombre() {
        return nombre;
    }

    public boolean getDisponible() {
        return disponible;
    }
    
}
