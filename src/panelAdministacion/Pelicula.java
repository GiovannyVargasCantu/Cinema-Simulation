package panelAdministacion;

import acceso_a_la_aplicacion.Acceso;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

//Clase Pelicula que se almacenará los objetos en archivos binarios
public class Pelicula implements Serializable{
    
    //Atributos
    protected String titulo; //Titulo de la Pelicula
    protected String idioma; // Subtitulada o Doblada a español latino
    
    //Constructor pelicula
    public Pelicula(String titulo, String idioma) {
        this.titulo = titulo;
        this.idioma = idioma;
    }
    
    //Metodos get
    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }
    
    //Metodos set
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }
    
    //Modifica la informacion de la pelicula, sobreescribiendola en archivos
    public static void modificar(){
        Scanner entrada = new Scanner(System.in);
        int opcion;
        String titulo;
        String idioma = null;
        String newtittle = null;
        String newtipe = null;
        System.out.println("");
        int op6 =1;
        Pelicula aux;
        
        File file = new File("Peliculas.bin");
        //Verifica que exista la pelicula o si contiene bytes dentro
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna pelicula en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        //Verifica si existe al menos un objeto en nuestro archivo
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna pelicula en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        Acceso.iterar();
        System.out.println(Acceso.ANSI_YELLOW+" Modificar pelicula");
        System.out.println("---------------------"+Acceso.ANSI_RESET);
            do{
                Pelicula.verInformacion();
                System.out.print("\n\nElige una pelicula: ");  op6 = entrada.nextInt();
                aux = Funcion.elegirPelicula(op6);
                if(op6<=0 || aux==null){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Modificar pelicula");
                    System.out.println("---------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No eligio una pelicula"+Acceso.ANSI_RESET);
                }
                
            }while(op6<=0 || aux==null);
            titulo = aux.titulo;
            idioma = aux.idioma;
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+" Modificar pelicula");
            System.out.println("---------------------"+Acceso.ANSI_RESET);
            //Pregunta lo que quieres modificar
        do{
            System.out.println("Que desea modificar?");
            System.out.println("\n1) Titulo");
            System.out.println("2) Tipo");
            System.out.println("3) Eliminar la pelicula");
            System.out.print("Ingrese una opcion: ");
            opcion = entrada.nextInt();
            entrada.nextLine();
            int op2;
            switch(opcion){
                case 1:
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Cambiar nombre de la pelicula");
                    System.out.println("--------------------------------"+Acceso.ANSI_RESET);
                    do{
                        System.out.print("Ingrese el nuevo nombre de la pelicula: ");
                        newtittle = entrada.nextLine();
                        
                        if(Pelicula.confirmarNombre(titulo)){
                            if(newtittle.equals(titulo)){
                                Acceso.iterar();
                                System.out.println(Acceso.ANSI_YELLOW+" Cambiar nombre de la pelicula");
                                System.out.println("--------------------------------"+Acceso.ANSI_RESET);
                                System.out.println(Acceso.ANSI_RED+"Ya tiene este nombre la pelicula"+Acceso.ANSI_RESET);
                                continue;
                            }
                            if(confirmarPelicula(newtittle, aux.idioma)){
                                Acceso.iterar();
                                System.out.println(Acceso.ANSI_YELLOW+" Cambiar nombre de la pelicula");
                                System.out.println("--------------------------------"+Acceso.ANSI_RESET);
                                System.out.println(Acceso.ANSI_RED+"Ya existe está pelicula"+Acceso.ANSI_RESET);
                                continue;
                            }
                        }
                        break;
                    }while(true);
                    
                break;
                case 2:
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"   Cambiar tipo de pelicula");
                    System.out.println("--------------------------------"+Acceso.ANSI_RESET);
                    do{
                        System.out.println("1. Subtitulada");
                        System.out.println("2. Doblada");
                        System.out.print("Ingrese el tipo de pelicula: ");
                        op2 = entrada.nextInt();
                        switch(op2){
                            case 1:
                                newtipe = "Subtitulada";
                                break;
                            case 2: 
                                newtipe = "Doblada";
                                break;
                            default:
                                Acceso.iterar();
                                System.out.println(Acceso.ANSI_YELLOW+" Cambiar nombre de la pelicula");
                                System.out.println("--------------------------------"+Acceso.ANSI_RESET);
                                System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                        }
                        if(confirmarPelicula(titulo, newtipe)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"   Cambiar tipo de pelicula");
                            System.out.println("--------------------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Ya existe esté idioma"+Acceso.ANSI_RESET);
                        }
                    }while(op2<1 || op2>2 || confirmarPelicula(titulo, newtipe));
                    
                    
                    break;
                case 3:
                         
                    break;
                default:
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Modificar pelicula");
                    System.out.println("---------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No seleccionó una opción"+Acceso.ANSI_RESET);
            }
        }while(opcion<1 || opcion>3);
        Pelicula objetoPelicula;
        try {
            
            FileOutputStream archivo = new FileOutputStream("Peliculasaux.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Peliculas.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetoPelicula= (Pelicula)lectura.readObject();
                if(objetoPelicula.titulo.equals(titulo)){
                    switch(opcion){
                        case 1:
                            String gt = objetoPelicula.titulo;
                            objetoPelicula.titulo = newtittle;
                            File filef = new File("Funciones.bin");
                             if(!filef.exists() || filef.length()==0){
                                
                            }
                            else{
                                Funcion.modificar(0, gt, newtittle,"","",1);
                            }
                            
                            break;
                        case 2:
                            objetoPelicula.idioma = newtipe;
                            File fileff = new File("Funciones.bin");
                            if(!fileff.exists() || fileff.length()==0){
                                
                            }
                            else{
                                Funcion.modificar(0,objetoPelicula.titulo,"", newtipe, 4);
                            }
                            break;
                        case 3:
                            File filev = new File("Funciones.bin");
                            if(!filev.exists() || filev.length()==0){
                                
                            }
                            else{
                                Funcion.modificar(0,titulo,"",idioma,3);
                            }
                            break;
                    }
                }
            escritura.writeObject(objetoPelicula);
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
        try {
            
            FileOutputStream archivo = new FileOutputStream("Peliculas.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Peliculasaux.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetoPelicula = (Pelicula)lectura.readObject();
                if(opcion==3 && titulo.equals(objetoPelicula.titulo) && idioma.equals(objetoPelicula.idioma)){
                    continue;
                }
                escritura.writeObject(objetoPelicula);
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
        switch(opcion){
            case 1: 
                System.out.println("");
                System.out.println(Acceso.ANSI_GREEN+"Cambio de titulo exitoso"+Acceso.ANSI_RESET);
                break;
            case 2:
                System.out.println("");
                System.out.println(Acceso.ANSI_GREEN+"Cambio de idioma exitoso"+Acceso.ANSI_RESET);
                break;
            case 3:
                System.out.println("\n"+Acceso.ANSI_GREEN+"Pelicula eliminada correctamente"+Acceso.ANSI_RESET);
                break;
        }
    }
    
    //Agrega peliculas
    public static void agregar(){
        Scanner entrada = new Scanner(System.in);
        String nombre = null;
        String idioma = null;
        File file = new File("Peliculas.bin");
        if(!file.exists() || file.length()==0){
            try {
            FileOutputStream archivo = new FileOutputStream("Peliculas.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            
            int op2;
                Acceso.iterar();
                System.out.println(Acceso.ANSI_YELLOW+" Nueva pelicula");
                System.out.println("----------------"+Acceso.ANSI_RESET);
            do{
                        System.out.println("1. Subtitulada");
                        System.out.println("2. Doblada");
                        System.out.print("\nIngrese el tipo de pelicula: ");
                        op2 = entrada.nextInt();
                        switch(op2){
                            case 1:
                                idioma = "Subtitulada";
                                break;
                            case 2: 
                                idioma = "Doblada";
                                break;
                            default:
                                Acceso.iterar();
                                System.out.println(Acceso.ANSI_YELLOW+" Nueva pelicula");
                                System.out.println("----------------"+Acceso.ANSI_RESET);
                                System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                        }
                    }while(op2<1 || op2>2);
            entrada.nextLine();
            System.out.print("Ingrese el titulo de la pelicula: ");  nombre = entrada.nextLine();
             
            Pelicula pelicula = new Pelicula(nombre, idioma);
            //escribimos objetos
            escritura.writeObject(pelicula);
            System.out.println("");
            System.out.println(Acceso.ANSI_GREEN+"Pelicula añadida exitosamente"+Acceso.ANSI_RESET);
            escritura.close(); //cerramos el archivo binario
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
        else{
            try {
            FileOutputStream archivo = new FileOutputStream("Peliculas.bin",true);
            AñadirContenido añadir = new AñadirContenido(archivo);
            int op2;
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+" Nueva pelicula");
            System.out.println("----------------"+Acceso.ANSI_RESET);
            do{
                        System.out.println("1. Subtitulada");
                        System.out.println("2. Doblada");
                        System.out.print("\nIngrese el tipo de pelicula: ");
                        op2 = entrada.nextInt();
                        switch(op2){
                            case 1:
                                idioma = "Subtitulada";
                                break;
                            case 2: 
                                idioma = "Doblada";
                                break;
                            default:
                                Acceso.iterar();
                                System.out.println(Acceso.ANSI_YELLOW+" Nueva pelicula");
                                System.out.println("----------------"+Acceso.ANSI_RESET);
                                System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                        }
                    }while(op2<1 || op2>2);
            entrada.nextLine();
            do{
                
                System.out.print("Ingrese el titulo de la pelicula: "); nombre = entrada.nextLine();
                if(confirmarPelicula(nombre, idioma)==true){
                    System.out.println(Acceso.ANSI_RED+"Pelicula ya existente"+Acceso.ANSI_RESET);
                }
            }while(confirmarPelicula(nombre,idioma)==true);
            Pelicula pelicula = new Pelicula(nombre, idioma);
            //escribimos objetos
            añadir.writeObject(pelicula);
            System.out.println("");
            System.out.println(Acceso.ANSI_GREEN+"Pelicula añadida exitosamente"+Acceso.ANSI_RESET);
            
            añadir.close(); //cerramos el archivo binario
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
    }
    
    //Comprueba si existe un objeto en el archivo Peliculas
    public static boolean comprobarInformacion(){
        try {
            FileInputStream archivito = new FileInputStream("Peliculas.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            Pelicula objetoPelicula;
            objetoPelicula = (Pelicula)lectura.readObject();
            if(objetoPelicula==null){
                return true;
            }
            else{
                return false;
            }
            
        } catch (FileNotFoundException ex) {
            System.err.println("Error,"+ex);
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            System.err.println("Error,"+ex);
        }
        return true;
    }
    
    //Confirma si existe el nombre de la pelicula
    private static boolean confirmarNombre(String name){
        Pelicula objetoPelicula;
        try {
            FileInputStream archivito = new FileInputStream("Peliculas.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoPelicula = (Pelicula)lectura.readObject();
                if(objetoPelicula.titulo.equals(name)){
                        archivito.close();
                        lectura.close();
                        return true;
                    
                   
                }
                
            }
        } catch(EOFException ex){
            return false; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error, "+ex);
            }
        return false;
    }
    
    //Confirma si existe la pelicula con el nombre y el idioma
    private static boolean confirmarPelicula(String name, String idioma){
        Pelicula objetoPelicula;
        File file = new File("Peliculas.bin");
        if(!file.exists() || file.length()==0){
            return false;
        }
        else{
        try {
            FileInputStream archivito = new FileInputStream("Peliculas.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoPelicula = (Pelicula)lectura.readObject();
                if(objetoPelicula.titulo.equals(name)){
                    if(objetoPelicula.idioma.equals(idioma)){
                        archivito.close();
                        lectura.close();
                        return true;
                    }
                   
                }
                
            }
        } catch(EOFException ex){
            return false; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error, "+ex);
            }
        }
        return false;
    }
    
    //Ve todas las peliculas en el sistema
    public static void verInformacion(){
        Pelicula objetoPelicula;
        int numpel=1;
        File file = new File("Peliculas.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna pelicula en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna pelicula en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        System.out.println("");
        System.out.println(Acceso.ANSI_CYAN+"Peliculas en el sistema: "+Acceso.ANSI_RESET);
        try {
            FileInputStream archivito = new FileInputStream("Peliculas.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoPelicula = (Pelicula)lectura.readObject();
                if(objetoPelicula!=null){
                    System.out.print(Acceso.ANSI_GREEN+numpel+++". Pelicula\n"+Acceso.ANSI_RESET);
                    System.out.println("  Titulo: "+objetoPelicula.titulo);
                    System.out.println("  Idioma: "+objetoPelicula.idioma);
                }
                
                
            }
        } catch(EOFException ex){
            return; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
        }
}
