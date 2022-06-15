package ventayBusqueda;

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
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Scanner;
import panelAdministacion.Funcion;
import panelAdministacion.AñadirContenido;

//Clase Ticket la cual sus objetos se almacenarán en Tickets.bin
public class Ticket implements Serializable{
    //Atributos Ticket
    private String ticket; //Ticket
    private int matricula; //Matricula de busqueda
    
    //Constructor
    public Ticket(String ticket, int matricula) {
        this.ticket = ticket;
        this.matricula = matricula;
    }
    
    //Agrega un Ticket en Tickets.bin
    public static void agregar(){
        
        File filek = new File("Funciones.bin");
        if(!filek.exists() || filek.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay funciones en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        if(Funcion.comprobarInformacion()){
            System.out.println(Acceso.ANSI_RED+"No hay ninguna funcion en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        
        Scanner entrada = new Scanner(System.in);
        String nombreCliente;
        System.out.println(Acceso.ANSI_YELLOW+"       Venta");
        System.out.println("-------------------"+Acceso.ANSI_RESET);
        System.out.print("Nombre del cliente: "); 
        nombreCliente = entrada.nextLine();
        String ticket;
        int codigo;
        
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        ticket ="             Ticket\n";
        
        ticket+="-----------------------------------\n";
        ticket+="Nombre el cliente: "+nombreCliente+"\n";
        ticket+="Fecha: "+timeStamp+"\n";
        codigo = contador();
        ticket+="Codigo: "+String.valueOf(codigo)+"\n";
        ticket+="\nBoletos: \n";
        
        int cantidadB;
        float precio;
        float total=0;
        float totalPagar=0;
        String asiento;
        int op3;
        int bandera;

         String Horario1 = new String("10am-12pm");
         String Horario2 = new String("12pm-2pm");
         String Horario3 = new String("2pm-4pm");
         String Horario4 = new String("8pm-10pm");
         String Horario5 = new String("10pm-11:30pm");
        int hor=0;
        do{
        int op2;
        Funcion aux;
        Acceso.iterar();
        System.out.println(Acceso.ANSI_YELLOW+"       Venta");
        System.out.println("-------------------"+Acceso.ANSI_RESET);
            do{
                bandera = 1;
                Funcion.verFunciones();
                System.out.print("\n\nElige una Funcion: "); op2 = entrada.nextInt();
                aux = (Funcion)Funcion.elegirFuncion(op2);
                if(op2<=0 || aux == null){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                    continue;
                }
                if(cantidadDis(aux)==0){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No hay asientos disponibles"+Acceso.ANSI_RESET);
                    continue;
                }
                String horarioactual = aux.getHorario();
                if(horarioactual.equals(Horario1)) hor = 1;
                if(horarioactual.equals(Horario2)) hor = 2;
                if(horarioactual.equals(Horario3)) hor = 3;
                if(horarioactual.equals(Horario4)) hor = 4;
                if(horarioactual.equals(Horario5)) hor = 5;
                LocalTime currentTime = LocalTime.now();
              
                switch(hor){
                    case 1: 
                    LocalTime open1 = LocalTime.of(10,0);
                    LocalTime closed1 = LocalTime.of(12, 0);
                    if (currentTime.isAfter(closed1)){
                         Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                            System.out.println("-------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario "+Funcion.Horario1+" paso la hora"+Acceso.ANSI_RESET);
                            bandera = 0;
                    }
                
                    break;
                    case 2:
                    LocalTime open2 = LocalTime.of(12,0);
                    LocalTime closed2 = LocalTime.of(14, 0);
                    if (currentTime.isAfter(closed2)){
                         Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                            System.out.println("-------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario "+Funcion.Horario2+" paso la hora"+Acceso.ANSI_RESET);
                            bandera = 0;
                    }
               
                    break;
                        
                    case 3:
                    LocalTime open3 = LocalTime.of(14,0);
                    LocalTime closed3 = LocalTime.of(16, 0);
                    if (currentTime.isAfter(closed3)){
                         Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                            System.out.println("-------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario "+Funcion.Horario3+" paso la hora"+Acceso.ANSI_RESET);
                            bandera = 0;
                    }
                
                    break;
                     
                    case 4:
                    LocalTime open4 = LocalTime.of(20,0);
                    LocalTime closed4 = LocalTime.of(22, 0);
                    if (currentTime.isAfter(closed4)){
                         Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                            System.out.println("-------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario "+Funcion.Horario4+" paso la hora"+Acceso.ANSI_RESET);
                            bandera = 0;
                    }
                        
                    break;
                       
                    case 5:
                    LocalTime open5 = LocalTime.of(22,0);
                    LocalTime closed5 = LocalTime.of(23,30);
                    if (currentTime.isAfter(closed5)){
                            Acceso.iterar();
                            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                            System.out.println("-------------------"+Acceso.ANSI_RESET);
                            System.out.println(Acceso.ANSI_RED+"Horario "+Funcion.Horario5+" paso la hora"+Acceso.ANSI_RESET);
                            bandera = 0;
                           }
              
                
                    break;
                }
   
                
            }while((op2<=0)||(bandera == 0 || cantidadDis(aux) == 0));
            precio = precio(aux.getNsala());
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
            System.out.println("-------------------"+Acceso.ANSI_RESET);
            do{
                System.out.print("Ingrese la cantidad de boletos: "); cantidadB = entrada.nextInt();
                if(cantidadDis(aux)<cantidadB){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"Cantidad de boletos mayor a la disponible"+Acceso.ANSI_RESET);
                }
                if(cantidadB<=0){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"Cantidad de boletos invalida"+Acceso.ANSI_RESET);
                }
            }while(cantidadDis(aux)<cantidadB || cantidadB<=0);
            ticket+="\nSala: "+aux.getNsala()+"  Tipo: "+Funcion.tipoSala(aux.getNsala())+"  Precio: "+precio+"\n"+"Horario: "+aux.getHorario()+"\n";
            ticket+="Pelicula: "+aux.getTitulo()+" Idioma: "+aux.getIdioma()+"\n";
            ticket+=cantidadB+" Asientos: ";
            total = precio*cantidadB;
            entrada.nextLine();
            for(int i=0;i<cantidadB;i++){
                Acceso.iterar();
                Funcion.imprimir(aux);
                int verificador=0;
                do{
                    verificador=0;
                    System.out.print("\nElija un asiento: "); asiento = entrada.nextLine();
                    for(int j=0;j<30;j++){
                        if(asiento.equals(aux.getAsientos()[j].getNombre())){
                            if(!aux.getAsientos()[j].getDisponible()){
                                Acceso.iterar();
                                Funcion.imprimir(aux);
                                System.out.println(Acceso.ANSI_RED+"Asiento no disponible"+Acceso.ANSI_RESET);
                                break;
                            }
                            else{
                                aux.getAsientos()[j].setDisponible(false);
                                
                                Funcion.ocupar(aux,Funcion.crearAsientos(aux.getAsientos()));
                                verificador=1;
                                break;
                            }
                        }
                        if(verificador==0 && j==29){
                            Acceso.iterar();
                            Funcion.imprimir(aux);
                            System.out.println(Acceso.ANSI_RED+"Asiento invalido"+Acceso.ANSI_RESET);
                        }
                    }
                    
                }while(verificador==0);
                if(i==cantidadB-1){
                    ticket+=asiento+".\n";
                    ticket+="Total: "+total;
                    totalPagar+=total;
                }
                else{
                    ticket+=asiento+", ";
                }
            }
            Acceso.iterar();
            System.out.println(Acceso.ANSI_YELLOW+"       Venta");
            System.out.println("-------------------"+Acceso.ANSI_RESET);
            do{
                System.out.println("Desea comprar más boletos?");
                System.out.println("\n1) Si");
                System.out.println("2) No");
                System.out.print("\nOpcion: ");
                op3 = entrada.nextInt();
                if(op3<1 || op3>2){
                    Acceso.iterar();
                    System.out.println(Acceso.ANSI_YELLOW+"       Venta");
                    System.out.println("-------------------"+Acceso.ANSI_RESET);
                    System.out.println(Acceso.ANSI_RED+"No ingreso una opcion"+Acceso.ANSI_RESET);
                }
            }while(op3<1 || op3>2);
            switch(op3){
                    case 1:
                        ticket+="\n";
                        break;
                    case 2:
                        ticket+="\n\nTotal a pagar: "+totalPagar;
                        Acceso.iterar();
                        System.out.println(ticket);
                        System.out.println("");
                        System.out.println(Acceso.ANSI_GREEN+"Ticket guardado correctamente"+Acceso.ANSI_RESET);
                        break;
                }
        }while(op3!=2);
        File file = new File("Tickets.bin");
        if(!file.exists() || file.length()==0){
            try {
                FileOutputStream archivo = new FileOutputStream("Tickets.bin");
                ObjectOutputStream escritura = new ObjectOutputStream(archivo);
                Ticket tickett = new Ticket(ticket,contador());
            
                //escribimos objetos
                escritura.writeObject(tickett);
                escritura.close(); //cerramos el archivo binario
            } catch (FileNotFoundException ex) {
                System.err.println("Error, "+ex);
            } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
            
        }
        else{
            try {
            FileOutputStream archivo = new FileOutputStream("Tickets.bin",true);
            AñadirContenido añadir = new AñadirContenido(archivo);
            Ticket tickett = new Ticket(ticket,contador());
            //escribimos objetos
            añadir.writeObject(tickett);
            añadir.close(); //cerramos el archivo binario
        } catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }
        }
    }
    
    //Genera el precio dependiendo de la sala
    private static float precio(int nsala){
        float precio=0;
        switch(nsala){
            case 1:
            case 7:
            case 8:
            case 9:
                precio = 60;
                break;
            case 3:
            case 5:
                precio = 100;
                break;
            case 4:
            case 10:
            case 6:
                precio = 200;
                break;
            case 2:
                precio = 120;
                break;
              
        }
        return precio;
    }
    
    //Genera la matricula el ticket
    public static int contador(){
        Ticket objetoTicket;
        int contador=1;
        File file = new File("Tickets.bin");
        if(!file.exists() || file.length()==0){
            return 1;
        }
        try {
            FileInputStream archivito = new FileInputStream("Tickets.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoTicket = (Ticket)lectura.readObject();
                if(objetoTicket!=null){
                    contador++;
                }
                
                
            }
        } catch(EOFException ex){
            return contador; //Finalizó de leer todo el archivo binario
        }
        catch (FileNotFoundException ex) {
            System.err.println("Error, "+ex);
        } catch (IOException ex) {
                System.err.println("Error, "+ex);
            }   catch (ClassNotFoundException ex) {
                    System.err.println("Error, "+ex);
                }
        return contador;
    }
    
    //Cuenta la cantidad de boletos disponibles en una funcion
    public static int cantidadDis(Funcion aux){
        int cant=0;
        for(int i=0;i<30;i++){
            if(aux.getAsientos()[i].getDisponible()){
                cant++;
            }
        }
        return cant;
    }
    
    //Busca un ticket y lo imprime en pantalla
    public static void buscarticket(){
        Scanner entrada = new Scanner(System.in);
        int codigo;
        Ticket objetoTicket;
        System.out.print("Ingrese el codigo: ");
        codigo = entrada.nextInt();
        File file = new File("Tickets.bin");
        if(!file.exists() || file.length()==0){
            System.out.println(Acceso.ANSI_RED+"No hay ningunn ticket en el sistema"+Acceso.ANSI_RESET);
            return;
        }
        try {
            FileInputStream archivito = new FileInputStream("Tickets.bin");
            ObjectInputStream lectura = new ObjectInputStream(archivito);
            while(true){
                //Recorremos el archivo binario
                objetoTicket = (Ticket)lectura.readObject();
                if(objetoTicket.matricula==codigo){
                    System.out.println("\n"+objetoTicket.ticket);
                    return;
                }
                
                
            }
        } catch(EOFException ex){
            System.out.println(Acceso.ANSI_RED+"No se encontró el ticket"+Acceso.ANSI_RESET);
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
