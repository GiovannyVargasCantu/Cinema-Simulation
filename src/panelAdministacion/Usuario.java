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

//Clase usuarios, almacenaremos usuarios en un archivo .bin
public class Usuario implements Serializable{
    //Atributos del Usuario
    private String nombreUsuario;
    private String contraseña; 
    private boolean status;
    //Constructor para crear un usuario
    private Usuario(String nombreUsuario, String contraseña, boolean status) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.status = status;
    }
    
    //Muestra los datos del usuario
    private void mostrarDatos(){
        System.out.println("  Nombre: "+nombreUsuario);
        System.out.println("  Contraseña: "+contraseña);
        System.out.println("  Estatus: "+status());
    }
    //Muestra en palabras el status
    private String status(){
        if(status){
            return "Activo";
        }
        else{
            return "Inactivo";
        }
    }
    
    //Confirma si existe el nombre del usuario
    private static boolean confirmarUser(String name){
        Usuario objetoUser;
        try {
            FileInputStream archivito = new FileInputStream("Usuarios.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoUser = (Usuario)lectura.readObject();
                if(objetoUser.getNombreUsuario().equals(name)){
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
    
    //Metodos get para obtener los atributos del usuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public String getContraseña() {
        return contraseña;
    }

    public boolean getStatus() {
        return status;
    }
    //Metodos set para establecer los atributos del usuario
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    //Agrega Usuarios a nuestro archivo Usuarios.bin
    public static void agregar() {
        Scanner entrada = new Scanner(System.in);
        String Usuario;
        String Contraseña;
        Boolean status = true;
        File file = new File("Usuarios.bin");
        if(!file.exists() || file.length()==0){
            try {
            FileOutputStream archivo = new FileOutputStream("Usuarios.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+"          Agregar usuario");
            System.out.println("---------------------------------------"+Acceso.ANSI_YELLOW);
            System.out.print("Ingrese su nombre de usuario: ");
            Usuario = entrada.nextLine();
            System.out.print("Ingrese su contraseña: ");
            Contraseña = entrada.nextLine();
            Usuario user = new Usuario(Usuario,Contraseña,status);
            
            //escribimos objetos
            escritura.writeObject(user);
            escritura.close(); //cerramos el archivo binario
                System.out.println(Acceso.ANSI_GREEN+"\nUsuario Agregado exitosamente"+Acceso.ANSI_RESET);
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
        else{
            try {
            FileOutputStream archivo = new FileOutputStream("Usuarios.bin",true);
            AñadirContenido añadir = new AñadirContenido(archivo);
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+"          Agregar usuario");
            System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
            do{
                System.out.print("Ingrese su nombre de usuario: ");
                Usuario = entrada.nextLine();
                if(confirmarUser(Usuario)==true){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"          Agregar usuario");
                    System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"Nombre de usuario ya existente"+Acceso.ANSI_RESET);
                }
            }while(confirmarUser(Usuario));
            
            System.out.print("Ingrese su contraseña: ");
            Contraseña = entrada.nextLine();
            Usuario user = new Usuario(Usuario,Contraseña,status);
            //escribimos objetos
            añadir.writeObject(user);
                System.out.println("");
                System.out.println(Acceso.ANSI_GREEN+"Usuario agregado correctamente"+Acceso.ANSI_RESET);
            
            añadir.close(); //cerramos el archivo binario
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
}
    
    //Imprime los usuarios en el sistema
    public static void verInformacion(){
        Usuario objetoUser;
        int numuser=1;
        File file = new File("Usuarios.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay usuarios en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay usuarios en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        Acceso.iterar();
        System.out.println(Acceso.ANSI_YELLOW+" Lista de Usuarios");
        System.out.println("-------------------"+Acceso.ANSI_RESET);
        System.out.println(Acceso.ANSI_CYAN+"Usuarios en el sistema: "+Acceso.ANSI_RESET);
        try {
            FileInputStream archivito = new FileInputStream("Usuarios.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoUser = (Usuario)lectura.readObject();
                if(objetoUser!=null){
                    System.out.println(Acceso.ANSI_GREEN+"Usuario: "+numuser+++Acceso.ANSI_RESET);
                }
                objetoUser.mostrarDatos();
                
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
    
    
    
    //Modifica en nuestro archivo.bin su contenido, dado el usuario que queramos modificar
    public static void modificar(){
        Scanner entrada = new Scanner(System.in);
        int opcion;
        String usuario;
        String newuser = null;
        String newcontra = null;
        System.out.println("");
        File file = new File("Usuarios.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay usuarios en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay usuarios en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        Acceso.iterar();
        System.out.println(Acceso.ANSI_YELLOW+"          Modificar usuario");
        System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
        do{
          
          System.out.print("Digite el usuario que desea modificar: ");
          usuario = entrada.nextLine();
          if(!confirmarUser(usuario)){
              Acceso.iterar();
              System.out.println(Acceso.ANSI_YELLOW+"          Modificar usuario");
              System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
              System.out.println(Acceso.ANSI_RED+"No se ha encontrado el usuario"+Acceso.ANSI_RESET);
          }
        }while(!confirmarUser(usuario));
        Acceso.iterar();
        System.out.println(Acceso.ANSI_YELLOW+"          Modificar usuario");
        System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
        do{
            System.out.println("Que desea modificar?");
            System.out.println("\n1) Nombre de usuario");
            System.out.println("2) Contraseña");
            System.out.println("3) Cambiar estado de la cuenta");
            System.out.print("Ingrese una opcion: ");
            opcion = entrada.nextInt();
            entrada.nextLine();
            switch(opcion){
                case 1:
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"     Modificar nombre de usuario");
                    System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
                    do{
                        
                        System.out.print("Ingrese su nuevo nombre de usuario: ");
                        newuser = entrada.nextLine(); 
                        Acceso.iterar();
                        
                        if(confirmarUser(newuser)){
                            if(newuser.equals(usuario)){
                                Acceso.iterar();
                                System.out.println(Acceso.ANSI_YELLOW+"     Modificar nombre de usuario");
                                System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
                                System.out.println(Acceso.ANSI_RED+"Ya tiene este nombre de usuario"+Acceso.ANSI_RESET);
                                continue;
                            }
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"     Modificar nombre de usuario");
                            System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
                            System.out.print(Acceso.ANSI_RED+"Usuario ya existente\n"+Acceso.ANSI_RESET);
                            continue;
                        }
                        break;
                    }while(true);
                    
                break;
                case 2:
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"        Modificar contraseña");
                    System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
                    System.out.print("Ingrese su nueva contraseña: ");
                    newcontra = entrada.nextLine();
                    
                    break;
                case 3:
                            
                    break;
                default:
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"          Modificar usuario");
                    System.out.println("---------------------------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingreso ninguna opcion"+Acceso.ANSI_RESET);
                    
            }
        }while(opcion<1 || opcion>3);
        Usuario objetoUser;
        try {
            
            FileOutputStream archivo = new FileOutputStream("Usuariosaux.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Usuarios.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetoUser = (Usuario)lectura.readObject();
                if(objetoUser.getNombreUsuario().equals(usuario)){
                    switch(opcion){
                        case 1:
                            objetoUser.setNombreUsuario(newuser);
                            break;
                        case 2:
                            objetoUser.setContraseña(newcontra);
                            break;
                        case 3:
                            objetoUser.setStatus(!objetoUser.getStatus());
                            break;
                    }
                }
            escritura.writeObject(objetoUser);
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
            
            FileOutputStream archivo = new FileOutputStream("Usuarios.bin");
            ObjectOutputStream escritura = new ObjectOutputStream(archivo);
            FileInputStream archivito = new FileInputStream("Usuariosaux.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(archivito.available() > 0){
                objetoUser = (Usuario)lectura.readObject();
                escritura.writeObject(objetoUser);
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
                System.out.println(Acceso.ANSI_GREEN+"Cambio de nombre de usuario exitosamente"+Acceso.ANSI_RESET);
                break;
            case 2:
                System.out.println("");
                System.out.println(Acceso.ANSI_GREEN+"Cambio de contraseña exitosamente"+Acceso.ANSI_RESET);
                break;
            case 3:
                System.out.println("");
                System.out.println(Acceso.ANSI_GREEN+"El usuario ha cambiado de status correctamente"+Acceso.ANSI_RESET);
                break;
        }
    }
    
    //Comprueba si existe al menos un objeto
    public static boolean comprobarInformacion(){
        try {
            FileInputStream archivito = new FileInputStream("Usuarios.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            Usuario objetoUsuario;
            objetoUsuario = (Usuario)lectura.readObject();
            if(objetoUsuario==null){
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
        return false;
    }
}
