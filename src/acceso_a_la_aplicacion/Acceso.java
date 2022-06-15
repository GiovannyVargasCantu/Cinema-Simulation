package acceso_a_la_aplicacion;

import ventayBusqueda.Ticket;
import panelAdministacion.Funcion;
import panelAdministacion.Pelicula;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import panelAdministacion.Usuario;

//Acceso a la aplicacion
public class Acceso {
    //Formato de texto
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_CYAN = "\u001B[36m";
    
    //metodo main
    public static void main(String[] args){
        
        Scanner entrada = new Scanner(System.in);
        //Variables locales
        String user;
        String contraseña;
        Boolean verificar;
        //Inicia sesion si accede con el usaurio predeterminado en el sistema o un usuario de archivos
        System.out.println(Acceso.ANSI_YELLOW+"  Iniciar sesion");
        System.out.print("-------------------"+Acceso.ANSI_RESET);
        do{
            
            System.out.print("\nUsuario: ");
            user = entrada.nextLine();
            System.out.print("Contraseña: ");
            contraseña = entrada.nextLine();
            for(int i = 1;i<15;i++){
                    System.out.println("");
                }
            if(user.equals("Admin") && contraseña.equals("Password")){
                break;
            }
            if(verificar = !Acceso.loggin(user,contraseña)){
                System.out.println(Acceso.ANSI_YELLOW+"  Iniciar sesion");
                System.out.println("-------------------"+Acceso.ANSI_RESET);
            }
        }while(verificar);
        
        iterar();
        //Entra a la aplicacion
        int op;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();     
        System.out.println(Acceso.ANSI_YELLOW+" Aplicacion cinepolis");
        System.out.println("----------------------"+Acceso.ANSI_RESET);
        System.out.println("Bienvenido "+ANSI_CYAN+user+"!"+ANSI_RESET+"\n"+dtf.format(now));
        //Espera a una opción
        try{
        do{
            System.out.println("\nQue desea hacer?");
            System.out.println("1. Venta de boletos");
            System.out.println("2. Busqueda de Ticket");
            System.out.println("3. Panel de Administracion");
            System.out.println("4. Cerrar sesion");
            System.out.println("5. Apagar sistema");
            System.out.print("\nOpcion: "); op = entrada.nextInt();
            switch(op){
                //Venta
                case 1:
                    iterar();
                    Ticket.agregar();
                    entrada.nextLine();
                    System.out.print("Presione enter para continuar: "); entrada.nextLine();
                    iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Aplicacion cinepolis");
                    System.out.println("----------------------"+Acceso.ANSI_YELLOW);
                    System.out.println("Bienvenido "+ANSI_CYAN+user+"!"+ANSI_RESET+"\n"+dtf.format(now));
                    break;
                //Busqueda de ticket
                case 2:
                    iterar();
                    Ticket.buscarticket();
                    entrada.nextLine();
                    System.out.print("Presione enter para continuar");entrada.nextLine();
                    iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Aplicacion cinepolis");
                    System.out.println("----------------------"+Acceso.ANSI_YELLOW);
                    System.out.println("Bienvenido "+ANSI_CYAN+user+"!"+ANSI_RESET+"\n"+dtf.format(now));
                    break;
                //Panel de administraccion
                case 3:
                    iterar();
                    panelAdmin();
                    System.out.println(Acceso.ANSI_YELLOW+" Aplicacion cinepolis");
                    System.out.println("----------------------"+Acceso.ANSI_YELLOW);
                    System.out.println("Bienvenido "+ANSI_CYAN+user+"!"+ANSI_RESET+"\n"+dtf.format(now));
                    break;
                //Cierra la sesion (vuelve a ejecutar el main)
                case 4:
                    iterar();
                    main(args);
                    return;
                //Cierra el software
                case 5:
                    iterar();
                    System.out.println(Acceso.ANSI_GREEN+"FIN DEL SOFTWARE"+Acceso.ANSI_RESET);
                    break;
                //No ingreso una opcion
                default:
                    iterar();
                    System.out.println(Acceso.ANSI_YELLOW+" Aplicacion cinepolis");
                    System.out.println("----------------------"+Acceso.ANSI_YELLOW);
                    System.out.println("Bienvenido "+ANSI_CYAN+user+"!"+ANSI_RESET+"\n"+dtf.format(now));
                    System.out.println(ANSI_RED+"No ingresó ninguna opcion"+ANSI_RESET);
            }
        }while(op!=4 && op!=5); //Se ejecuta mientras sea diferente de 4 o 5
        }catch(InputMismatchException ex){
            iterar();
            System.out.println(ANSI_RED+"Error, inicie sesion nuevamente"+ANSI_RESET);
            main(args);
            return;
        }
    }
    
    //Lee archivos usuarios para ingresar al sistema
    private static Boolean loggin(String usuario,String contraseña){
        Usuario objetoUser;
        File file = new File("Usuarios.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(ANSI_RED+"No se encontró al usuario"+ANSI_RESET);
            return false;
        }
        if(Usuario.comprobarInformacion()){
            System.out.println(ANSI_RED+"No se encontró al usuario"+ANSI_RESET);
            return false;
        }
        try {
            FileInputStream archivito = new FileInputStream("Usuarios.bin");
             ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoUser = (Usuario)lectura.readObject();
                if(objetoUser.getNombreUsuario().equals(usuario)){
                    if(objetoUser.getContraseña().equals(contraseña)){
                        
                    }
                    else{
                        System.out.println(ANSI_RED+"Contraseña incorrecta"+ANSI_RESET);
                        return false;
                    }
                    if(objetoUser.getStatus()){
                        
                        archivito.close();
                        lectura.close();
                        return true;
                    }
                    else{
                        System.out.println(ANSI_RED+"El usuario está inactivo"+ANSI_RESET);
                        return false;
                    }
                }
                else{
                    continue;
                }
                
            }
        } catch(EOFException ex){
            System.out.println(ANSI_RED+"No se encontro el usuario"+ANSI_RESET);
            return false; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException | ClassNotFoundException ex) {
                System.err.println("Error, "+ex);
            }
        return false;
    }
    
    //Panel de acceso
    private static final void panelAdmin(){
        int op=0;
        Scanner entrada = new Scanner(System.in);
        try{
        do{
            System.out.println(ANSI_YELLOW+"  Panel de Administracion");
            System.out.println("-----------------------------"+ANSI_RESET);
            System.out.println("A que recurso desea acceder?");
            System.out.println("\n1. Usuarios");
            System.out.println("2. Peliculas");
            System.out.println("3. Funciones");
            System.out.println("4. Salir");
            System.out.print("\nSeleccione una opcion: "); op = entrada.nextInt();
            
            entrada.nextLine();
            switch(op){
                case 1: 
                    usuario();
                    break;
                case 2:
                    pelicula();
                    break;
                case 3:
                    Funcion();
                    break;
                case 4:
                    iterar();
                    break;
                default:
                    iterar();
                    System.out.println(ANSI_RED+"Opcion Invalida"+ANSI_RESET);
                break;
            }
        }while(op!=4);
        } catch(InputMismatchException ex){
                iterar();
                System.out.println(ANSI_RED+"Porfavor digite un numero"+ANSI_RESET);
            }
    }
    
    //Opcion de Funcion
    private static final void Funcion(){
        int op5;
        Scanner entrada = new Scanner(System.in);
        try{
            iterar();
        do{
            
            System.out.println(ANSI_YELLOW+" Menu de Funcion");
            System.out.println("-------------------"+ANSI_RESET);
            System.out.println("Que desea hacer?");
            System.out.println("\n1. Agregar");
            System.out.println("2. Eliminar Funcion");
            System.out.println("3. Ver Funciones");
            System.out.println("4. Ver Asientos");
            System.out.println("5. Salir");
            System.out.print("\nSeleccione una opcion: "); op5 = entrada.nextInt();
                
            
            entrada.nextLine();
            switch(op5){
                //Agrega funciones
                case 1:
                    Funcion.agregar();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Elimina funciones
                case 2:
                    iterar();
                    Funcion.eliminarFuncion();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Ve las funciones en el sistema
                case 3:
                    iterar();
                    System.out.println(ANSI_YELLOW+"  Funciones");
                    System.out.println("--------------"+ANSI_RESET);
                    Funcion.verFunciones();
                    System.out.print("\nDigite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Ve los asientos de una funcion
                case 4:
                    iterar();
                    Funcion.verAsientos();
                    System.out.print("\nDigite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Salir
                case 5:
                    iterar();
                    break;
                default:
                    iterar();
                    System.out.println(ANSI_RED+"Opcion Invalida"+ANSI_RESET);
            }
        }while(op5!=5);
        } catch(InputMismatchException ex){
                iterar();
                System.out.println(ANSI_RED+"Porfavor digite un numero"+ANSI_RESET);
            }
    }
    
    //Opciones Usuario
    private static final void usuario(){
        int op;
        Scanner entrada = new Scanner(System.in);
        try{
            iterar();
        do{
            System.out.println(ANSI_YELLOW+"  Menu de Usuario");
            System.out.println("-------------------"+ANSI_RESET);
            System.out.println("Que desea hacer?");
            System.out.println("\n1. Agregar");
            System.out.println("2. Modificar");
            System.out.println("3. Ver informacion");
            System.out.println("4. Salir");
            System.out.print("\nSeleccione una opcion: "); op = entrada.nextInt();
            
            
            switch(op){
                //Agregar usuario
                case 1:
                    Usuario.agregar();
                    entrada.nextLine();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Modificar Usuario
                case 2:
                    Usuario.modificar();
                    entrada.nextLine();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Ve los usuarios en el sistema
                case 3:
                    Usuario.verInformacion();
                    entrada.nextLine();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Salir
                case 4:
                    iterar();
                    break;
                //No ingresó una opción
                default:
                    iterar();
                    System.out.println(ANSI_RED+"Opcion Invalida"+ANSI_RESET);
            }
        }while(op!=4); //Sale si ingresa la opcion 4
        } catch(InputMismatchException ex){
                iterar();
                System.out.println(ANSI_RED+"Porfavor digite un numero"+ANSI_RESET);
            }
    }
    
    //Opciones pelicula
    private static final void pelicula(){
        int op;
        Scanner entrada = new Scanner(System.in);
        try{
            iterar();
        do{
            System.out.println(ANSI_YELLOW+" Menu de Pelicula");
            System.out.println("-------------------"+ANSI_RESET);
            System.out.println("Que desea hacer?");
            System.out.println("\n1. Agregar");
            System.out.println("2. Modificar");
            System.out.println("3. Ver informacion");
            System.out.println("4. Salir");
            System.out.print("\nSeleccione una opcion: "); op = entrada.nextInt();
            
            
            switch(op){
                //Agrega una pelicula
                case 1:
                    Pelicula.agregar();
                    entrada.nextLine();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Modifica una pelicula
                case 2:
                    Pelicula.modificar();
                    entrada.nextLine();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Ve todas las peliculas
                case 3:
                    iterar();
                    Pelicula.verInformacion();
                    entrada.nextLine();
                    System.out.print("Digite enter para continuar: "); entrada.nextLine();
                    iterar();
                    break;
                //Salir
                case 4:
                    iterar();
                    
                    break;
                //No ingresó una opción
                default:
                    iterar();
                    System.out.println(ANSI_RED+"Opcion Invalida"+ANSI_RESET);
            }
        }while(op!=4);
        } catch(InputMismatchException ex){
                iterar();
                System.out.println(ANSI_RED+"Porfavor digite un numero"+ANSI_RESET);
            }
    }
    
    //Formato
    public static void iterar(){
        for(int i=0;i<30;i++){
            System.out.println(".");
        }
    }
}
