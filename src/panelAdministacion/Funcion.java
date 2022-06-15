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

/**
 *
 * @author PC
 */
public class Funcion implements Serializable{
    //Atributos
    private String tipo;
    private int nsala;
    private String horario;
    private String idioma;
    private String titulo;
    private Asiento[] asientos;
    
    //Contantes Horarios
    public static final String Horario1 = "10am-12pm";
    public static final String Horario2 = "12pm-2pm";
    public static final String Horario3 = "2pm-4pm";
    public static final String Horario4 = "8pm-10pm";
    public static final String Horario5 = "10pm-11:30pm";
    
    //Constructor para crear una funcion
    public Funcion(String tipo, String horario, int nsala, String titulo, String idioma){
        this.idioma = idioma;
        this.titulo = titulo;
        this.nsala = nsala;
        this.horario = horario;
        this.asientos = new Asiento[30];
        this.tipo = tipo;
        char letra=65;
        String id;
        int n=1;
        for(int i=0;i<30;i++){
            id = String.valueOf(n) + String.valueOf(letra++);
            asientos[i] = new Asiento(id,true);
            if(letra=='G'){
                letra=65;
                n++;
            }
        }
    }
    //Metodos get
    public Asiento[] getAsientos() {
        return asientos;
    }
    
    public int getNsala() {
        return nsala;
    }

    public String getHorario() {
        return horario;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getTitulo() {
        return titulo;
    }
    
    //Constructor funcion (modificar asientos)
    public Funcion(String tipo, String horario, int nsala, String titulo, String idioma, Asiento[] asientos){
        this.idioma = idioma;
        this.titulo = titulo;
        this.nsala = nsala;
        this.horario = horario;
        this.asientos = new Asiento[30];
        this.tipo = tipo;
        String nom="";
        boolean dis=false;
        for(int i=0;i<30;i++){
        nom = asientos[i].getNombre();
        dis = asientos[i].getDisponible();
        this.asientos[i] = new Asiento(nom,dis);
    }
        
    }
    
    //Retorna el tipo de sala dado la sala que le den
    public static String tipoSala(int nsala){
        String tipoSala = null;
        switch(nsala){
                    case 1: tipoSala ="Normal"; 
                    break; case 2: tipoSala ="KIDS"; break; 
                    case 3: tipoSala ="MACROXE"; break; 
                    case 4: tipoSala ="4D"; break; 
                    case 5: tipoSala ="MACROXE"; break; case 6: tipoSala ="4D";break; 
                    case 7: tipoSala ="Normal"; break; 
                    case 8: tipoSala ="Normal"; break; 
                    case 9: tipoSala ="Normal"; break;
                    case 10: tipoSala ="4D"; break;
                    default: 
                        Acceso.iterar();
                        System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                        System.out.println("------------------"+Acceso.ANSI_RESET);
                        System.out.println(Acceso.ANSI_RED+"Opcion Invalida"+Acceso.ANSI_RESET);
                        break;
                }
        return tipoSala;
    }
    
    //Agrega una funcion al sistema
    public static void agregar(){
        Scanner entrada = new Scanner(System.in);
        int nsala;
        String idioma = null;
        String tipoSala;
        File file = new File("Funciones.bin");
        File fileP = new File("Peliculas.bin");
        if(!fileP.exists() || fileP.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay peliculas en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(Pelicula.comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay peliculas en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(!file.exists() || file.length()==0){
            try {
            FileOutputStream archivo = new FileOutputStream("Funciones.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
            System.out.println("------------------"+Acceso.ANSI_RESET);
            nsala = elegirSala();
            if(salaOcupada(nsala)){
                Acceso.iterar();
                System.out.println(Acceso.ANSI_RED+"No se pueden agregar más funciones a está sala"+Acceso.ANSI_RESET);
                agregar();
                return;
            }
            tipoSala = tipoSala(nsala);
            int op2 = 0;
            
            Pelicula aux;
                Acceso.iterar();
                System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                System.out.println("------------------"+Acceso.ANSI_RESET);
            do{
                Pelicula.verInformacion();
                System.out.print("\n\nElige una pelicula: "); op2 = entrada.nextInt();
                aux = (Pelicula)elegirPelicula(op2);
                if(op2<=0 || aux==null){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                    System.out.println("------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingresó una opción"+Acceso.ANSI_RESET);
                }
            }while(op2<=0 || aux==null);
            String h;
            h = elegirHorario(nsala);
            Funcion funcion = new Funcion(tipoSala,h,nsala,aux.getTitulo(),aux.getIdioma());
            
            //escribimos objetos
            escritura.writeObject(funcion);
            escritura.close(); //cerramos el archivo binario
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
        else{
            try {
            FileOutputStream archivo = new FileOutputStream("Funciones.bin",true);
            AñadirContenido añadir = new AñadirContenido(archivo);
            if(!fileP.exists() || fileP.length()==0){
                System.out.println(Acceso.ANSI_RED+"No hay peliculas en el sistema"+Acceso.ANSI_RESET);
            return;
            }
            if(Pelicula.comprobarInformacion()){
                System.out.println(Acceso.ANSI_RED+"No hay peliculas en el sistema"+Acceso.ANSI_RESET);
                return;
            }
            nsala = elegirSala();
            if(salaOcupada(nsala)){
                Acceso.iterar();
                System.out.println(Acceso.ANSI_RED+"No se pueden agregar más funciones a está sala"+Acceso.ANSI_RESET);
                agregar();
                return;
            }
            tipoSala = tipoSala(nsala);
            
            int op2 =1;
            Pelicula aux;
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
            System.out.println("------------------"+Acceso.ANSI_RESET);
            do{
                Pelicula.verInformacion();
                System.out.print("\n\nElige una pelicula: "); op2 = entrada.nextInt();
                aux = (Pelicula)elegirPelicula(op2);
                if(op2<=0 || aux==null){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                    System.out.println("------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingresó una opción"+Acceso.ANSI_RESET);
                }
            }while(op2<=0 || aux==null);
            String h = elegirHorario(nsala);
            Funcion funcion = new Funcion(tipoSala,h,nsala,aux.getTitulo(),aux.getIdioma());
            //escribimos objetos
            añadir.writeObject(funcion);
            
            añadir.close(); //cerramos el archivo binario
                System.out.println("");
                System.out.println(Acceso.ANSI_GREEN+"Funcion agregada con exito"+Acceso.ANSI_RESET);
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
    }
    
    //Elije una pelicula en el sistema
    public static Pelicula elegirPelicula(int n){
        int v = 0;
        Pelicula objetoPelicula;
        String idioma;
        String titulo;
        Pelicula aux = new Pelicula("","");
        try {
            FileInputStream archivito = new FileInputStream("Peliculas.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                v++;
                objetoPelicula = (Pelicula)lectura.readObject();
                if(n==v){
                        idioma = objetoPelicula.idioma;
                        titulo = objetoPelicula.titulo;
                        archivito.close();
                        lectura.close();
                        ;
                        return objetoPelicula(titulo, idioma);
                }
                
            }
        } catch(EOFException ex){
            return null; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error, "+ex);
            }
        return null;
    }
    
    //Elije una funcion en el sistema
    public static Funcion elegirFuncion(int n){
        int v = 0;
        Funcion objetoFuncion;
        String tipo;
        int nsala;
        String horario;
        String idioma;
        String titulo;
        try {
            FileInputStream archivito = new FileInputStream("Funciones.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                v++;
                objetoFuncion = (Funcion)lectura.readObject();
                if(n==v){
                        idioma = objetoFuncion.idioma;
                        titulo = objetoFuncion.titulo;
                        horario = objetoFuncion.horario;
                        nsala = objetoFuncion.nsala;
                        tipo = objetoFuncion.tipo;
                        
                        archivito.close();
                        lectura.close();
                        ;
                        return objetoFuncion;
                }
                
            }
        } catch(EOFException ex){
            return null; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error, "+ex);
            }
        return null;
    }
    
    //Devuelve un objetoFuncion
    public static Funcion objetoFuncion(String titulo, String idioma, String horario, String tipo, int nsala){
        Funcion aux = new Funcion(tipo, horario, nsala, titulo, idioma);
        return aux;
    }
    
    //Devuelve un objeto funcion 
    public static Funcion objetoFuncion(String titulo, String idioma, String horario, String tipo, int nsala,Asiento[] asientos){
        Funcion aux = new Funcion(tipo, horario, nsala, titulo, idioma,asientos);
        return aux;
    }
    
    //Imprime los asientos de una sala
    public static void imprimir(Funcion ob){
        int contador = 0;
        System.out.println(Acceso.ANSI_YELLOW+"              Sala "+ob.nsala);
        System.out.println("-------------------------------------"+Acceso.ANSI_RESET);
        for(int i=0;i<30;i++){
            
            if(contador==0){
                System.out.print("\t");
            }
            if(ob.asientos[i].getDisponible()){
                System.out.print(Acceso.ANSI_GREEN+ob.asientos[i].getNombre()+Acceso.ANSI_RESET+" ");
                contador++;
            }
            else{
                System.out.print(Acceso.ANSI_RED+ob.asientos[i].getNombre()+Acceso.ANSI_RESET+" ");
                contador++;
            }
            if(contador==6){
                contador = 0;
                System.out.println("\n");
            }
        }
        System.out.println(Acceso.ANSI_RED+"Ocupado"+Acceso.ANSI_RESET+Acceso.ANSI_GREEN+"                   Disponible"+Acceso.ANSI_RESET);
    }
    
    //Elimina una funcion
    public static void eliminarFuncion(){
        Scanner entrada = new Scanner(System.in);
        Funcion aux;
        File file = new File("Funciones.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay funciones en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna funcion en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        int op2;
        System.out.println(Acceso.ANSI_YELLOW+" Eliminar funcion");
        System.out.println("-------------------"+Acceso.ANSI_RESET);
            do{
                
                Funcion.verFunciones();
                System.out.print("\n\nElige una Funcion para eliminar: "); op2 = entrada.nextInt();
                aux = (Funcion)elegirFuncion(op2);
                if(op2<=0 || aux==null){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Eliminar funcion");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                }
            }while(op2<=0 || aux==null);
      
        modificar(aux.nsala,aux.titulo,aux.horario,aux.idioma,2);
        System.out.println("");
        System.out.println(Acceso.ANSI_GREEN+"Funcion eliminada"+Acceso.ANSI_RESET);
    }
    
    //Ve los asientos en el sistema
    public static void verAsientos(){
        Scanner entrada = new Scanner(System.in);
        Funcion aux;
        File file = new File("Funciones.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay funciones en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna funcion en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        int op2;
        System.out.println(Acceso.ANSI_YELLOW+" Ver asientos");
        System.out.println("-------------------"+Acceso.ANSI_RESET);
            do{
                
                Funcion.verFunciones();
                System.out.print("\n\nElige una Funcion para inspeccionar: "); op2 = entrada.nextInt();
                aux = (Funcion)elegirFuncion(op2);
                if(op2<=0 || aux==null){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Ver asientos");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                }
            }while(op2<=0 || aux==null);
            Acceso.iterar();
            imprimir(aux);
    }
    
    //Elimina-modifica funciones
    public static void modificar(int nsala,String titulo, String horario,String idioma, int op){
        Funcion objetofuncion;
        try {
            
            FileOutputStream archivo = new FileOutputStream("Funcionesaux.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Funciones.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetofuncion = (Funcion)lectura.readObject();
                escritura.writeObject(objetofuncion);
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
            Funcion aux;
            FileOutputStream archivo = new FileOutputStream("Funciones.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Funcionesaux.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetofuncion = (Funcion)lectura.readObject();
                String tipo;
                if(op==1 && idioma.equals(objetofuncion.titulo)){
                    horario = objetofuncion.horario;
                    titulo = idioma;
                    idioma = objetofuncion.idioma;
                    tipo = objetofuncion.tipo;
                    nsala = objetofuncion.nsala;
                    escritura.writeObject(objetoFuncion(titulo, idioma, horario, tipo, nsala));
                    continue;
                }
                if(op==2 && titulo.equals(objetofuncion.titulo) && horario.equals(objetofuncion.horario) && nsala==objetofuncion.nsala){
                    continue;
                }
                if(op==3 && titulo.equals(objetofuncion.titulo) && idioma.equals(objetofuncion.idioma)){
                    continue;
                }
                if(op==4 && titulo.equals(objetofuncion.titulo)){
                    horario = objetofuncion.horario;
                    titulo = objetofuncion.titulo;
                    tipo = objetofuncion.tipo;
                    nsala = objetofuncion.nsala;
                    Asiento[] asientos = objetofuncion.asientos;
                    escritura.writeObject(objetoFuncion(titulo, idioma, horario, tipo, nsala, asientos));
                    continue;
                }
                escritura.writeObject(objetofuncion);
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
    }
    
    //Modifica funciones
    public static void modificar(int nsala,String vt,String titulo, String horario,String idioma, int op){
        Funcion objetofuncion;
        
        try {
            
            FileOutputStream archivo = new FileOutputStream("Funcionesaux.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Funciones.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetofuncion = (Funcion)lectura.readObject();
                escritura.writeObject(objetofuncion);
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
            Funcion aux;
            FileOutputStream archivo = new FileOutputStream("Funciones.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Funcionesaux.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetofuncion = (Funcion)lectura.readObject();
                String tipo;
                Asiento[] asientos;
                if(op==1 && vt.equals(objetofuncion.titulo)){
                    horario = objetofuncion.horario;
                    idioma = objetofuncion.idioma;
                    tipo = objetofuncion.tipo;
                    nsala = objetofuncion.nsala;
                    asientos = objetofuncion.asientos;
                    escritura.writeObject(objetoFuncion(titulo, idioma, horario, tipo, nsala,asientos));
                    continue;
                }
                escritura.writeObject(objetofuncion);
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
            System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
    }
    
    //Ocupa un asiento de una funcion
    public static void ocupar(Funcion auxx, Asiento[] asientosn){
        Funcion objetofuncion;
        try {
            
            FileOutputStream archivo = new FileOutputStream("Funcionesaux.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Funciones.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetofuncion = (Funcion)lectura.readObject();
                if(auxx.horario.equals(objetofuncion.horario) && auxx.nsala==objetofuncion.nsala){
                    
                    escritura.writeObject(objetoFuncion(auxx.titulo, auxx.idioma,auxx.horario,auxx.tipo,auxx.nsala,asientosn));
                    continue;
                }
                escritura.writeObject(objetofuncion);
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
            FileOutputStream archivo = new FileOutputStream("Funciones.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Funcionesaux.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetofuncion = (Funcion)lectura.readObject();
                escritura.writeObject(objetofuncion);
            }
            escritura.close();
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
            System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
    }
    
    //Ve las funciones en el sistema
    public static void verFunciones(){
        Funcion objetoFuncion;
        int n=1;
        File file = new File("Funciones.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna funcion en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna funcion en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        System.out.println(Acceso.ANSI_CYAN+"Funciones en el sistema: "+Acceso.ANSI_RESET);
        try {
            FileInputStream archivito = new FileInputStream("Funciones.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                    objetoFuncion = (Funcion)lectura.readObject();
                    System.out.println(Acceso.ANSI_GREEN+n+++". Funcion"+Acceso.ANSI_RESET);
                    objetoFuncion.mostrardatos();
            }
        } catch(EOFException ex){
            return;
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            } catch (ClassNotFoundException ex) {
            System.err.println("Error, "+ex);
        }
    }
    
    //Comprueba si existe un objeto en el sistema
    public static boolean comprobarInformacion(){
        try {
            FileInputStream archivito = new FileInputStream("Funciones.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            Funcion objetoFuncion;
            objetoFuncion = (Funcion)lectura.readObject();
            if(objetoFuncion==null){
                return true;
            }
            else{
                return false;
            }
        }catch(EOFException ex){
            return true;
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error,"+ex);
        } catch (IOException ex) {
            System.err.println("Error,"+ex);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error,"+ex);
        }
        return false;
    }
    
    //Instancia asientos en un arreglo
    public static Asiento[] crearAsientos(Asiento[] asientos){
        Asiento[] asient = new Asiento[30];
        for(int i=0;i<30;i++){
            asient[i] = new Asiento(asientos[i].getNombre(),asientos[i].getDisponible());
        }
        return asient;
    }
    
    //Muestra los datos de la funcion
    private void mostrardatos(){
        System.out.print("  Sala: "+nsala);
        System.out.println("\tTipo: "+tipo);
        System.out.println("  Pelicula: "+titulo);
        System.out.println("  Idioma: "+idioma);
        System.out.println("  Horario: "+horario);
    }
    
    //Muestra si la sala está ocupada
    private static boolean salaOcupada(int nsala){
        Funcion objetoFuncion;
        int n=0;
        try {
            FileInputStream archivito = new FileInputStream("Funciones.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoFuncion = (Funcion)lectura.readObject();
                if(objetoFuncion.nsala==nsala && (Horario1.equals(Horario1) || Horario2.equals(Horario2)
                        || Horario3.equals(Horario3) || Horario4.equals(Horario4) || Horario5.equals(Horario5))) {
                   n++;
                }
                
            }
        } catch(EOFException ex){
            if(n==5){
                return true;
            }
            else{
                return false;
            }
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error, "+ex);
            }
        return false;
    }
    
    //Devuelve la instancia de un objetoPelicula
    private static Pelicula objetoPelicula(String titulo, String idioma){
        Pelicula aux = new Pelicula(titulo,idioma);
        return aux;
        
    }
    
    //Elije la sala, devuelve la sala
    private static int elegirSala(){
        int nsala;
        Scanner entrada = new Scanner(System.in);
        Acceso.iterar();
        System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
        System.out.println("------------------"+Acceso.ANSI_RESET);
        do{
            System.out.println("Que sala desea elegir?: ");
            System.out.println("1. Sala 1\t6. Sala 6");
            System.out.println("2. Sala 2\t7. Sala 7");
            System.out.println("3. Sala 3\t8. Sala 8");
            System.out.println("4. Sala 4\t9. Sala 9");
            System.out.println("5. Sala 5\t10. Sala 10");
            System.out.print("\nOpcion: "); nsala = entrada.nextInt();
            if(nsala<1 || nsala>10){
                Acceso.iterar();
                System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                System.out.println("------------------"+Acceso.ANSI_RESET);
                System.out.println(Acceso.ANSI_RED+"Opcion Invalida"+Acceso.ANSI_RESET);
            } 
                
         }while(nsala<1 || nsala>10);
        return nsala;
    }
    
    //Elige el horario devuelve el horario indicado
    private static String elegirHorario(int nusala){
        Scanner entrada = new Scanner(System.in);
            int op3;
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
            System.out.println("------------------"+Acceso.ANSI_RESET);
            do{
                System.out.println("Que horario desea elegir?: ");
                System.out.println("1. "+Horario1);
                System.out.println("2. "+Horario2);
                System.out.println("3. "+Horario3);
                System.out.println("4. "+Horario4);
                System.out.println("5. "+Horario5);
                System.out.print("\nOpcion: ");  op3 = entrada.nextInt();
                switch(op3){
                    case 1: 
                        if(disponibilidadh(nusala, Horario1,1)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                            System.out.println("------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario 1 no disponible"+Acceso.ANSI_RESET);
                        }
                        else{
                            return Horario1;
                        }
                        
                    break;
                    case 2:
                        if(disponibilidadh(nusala, Horario2,2)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                            System.out.println("------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario 2 no disponible"+Acceso.ANSI_RESET);
                        }
                        else{
                            return Horario2;
                        }
                        break; 
                    case 3:
                        if(disponibilidadh(nusala, Horario3,3)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                            System.out.println("------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario 3 no disponible"+Acceso.ANSI_RESET);
                        }
                        else{
                            return Horario3;
                        }
                        break; 
                    case 4:
                        if(disponibilidadh(nusala, Horario4,4)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                            System.out.println("------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario 4 no disponible"+Acceso.ANSI_RESET);
                        }
                        else{
                            return Horario4;
                        }
                        break; 
                    case 5:
                        if(disponibilidadh(nusala, Horario5,5)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                            System.out.println("------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario 5 no disponible"+Acceso.ANSI_RESET);
                        }
                        else{
                            return Horario5;
                        }
                        break;
                    default: 
                        Acceso.iterar();
                        System.out.println(Acceso.ANSI_YELLOW+" Agregar funcion");
                        System.out.println("------------------"+Acceso.ANSI_RESET);
                        System.out.println(Acceso.ANSI_RED+"Opcion Invalida"+Acceso.ANSI_RESET);
                        break;
                }
                
            }while(true);
    }
    
    //Checa la disponibilidad de la sala dado un horario
    private static boolean disponibilidadh(int nusala,String Horario, int h){
        Funcion objetoFuncion;
        try {
            FileInputStream archivito = new FileInputStream("Funciones.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoFuncion = (Funcion)lectura.readObject();
                switch(h){
                    case 1:
                        if(objetoFuncion.nsala==nusala && objetoFuncion.horario.equals(Horario1)){
                            return true;
                        }
                        break;
                    case 2:
                        if(objetoFuncion.nsala==nusala && objetoFuncion.horario.equals(Horario2)){
                            return true;
                        }
                        break;
                    case 3:
                        if(objetoFuncion.nsala==nusala && objetoFuncion.horario.equals(Horario3)){
                            return true;
                        }
                        break;
                    case 4:
                        if(objetoFuncion.nsala==nusala && objetoFuncion.horario.equals(Horario4)){
                            return true;
                        }
                        break;
                    case 5:
                        if(objetoFuncion.nsala==nusala && objetoFuncion.horario.equals(Horario5)){
                            return true;
                        }
                        break;
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
}
