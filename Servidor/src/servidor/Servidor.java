
package servidor;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.Jugador;
import modelo.Modelo;
import protocolo.Protocolo;

public class Servidor {

    public Servidor(Modelo nuevoModelo) {
        datos = nuevoModelo;
        clientes = new ArrayList<>();
        cantClientes = 0; 
        cantJugadores = datos.getCantJugadores();
//        datos.cargar();
    }

    public Servidor() {
        this(new Modelo());
    }

    public void iniciar() {
        System.out.println("Iniciando aplicación..");
        hiloAtencion = new Thread(new Runnable() {
            @Override
            public void run() {
                atenderClientes();
            }
        });
        hiloAtencion.start();
    }

    public void registrar(Observer nuevoObservador) {
        datos.addObserver(nuevoObservador);
    }

    public void atenderClientes() {        
        System.out.println("Atendiendo clientes..");
        try {
            srv = new ServerSocket(Protocolo.PUERTO_POR_DEFECTO);            
        
            //Registra los 2 primeros clientes
            primeros2Registros();            
            controlarRegistros(); 
            datos.actualizar("ocultar");    
            
            //Espera 5 segundos para un tercer cliente o se comienza a jugar automáticamente            
            jugar();                        
            
        } catch (IOException | HeadlessException e) {            
            System.err.println(e.getMessage());
        }
    }
    
    private void jugar(){
        //Comienza una partida
        while (true){
            if(cantClientes == 2){
                try {
                    System.out.println("Esperando a un posible tercer jugador...(3 segs)");                    
                    tercerRegistro();
                } catch (IOException ex) {}
            }
                
            //Reparte cartas a los jugadores
            reparteCartas();                
                
            //Juega una vez
            controlaPartida();
                
            if(JOptionPane.showConfirmDialog(null, "Desea volver a jugar?","Fin del Juego", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION){
                JOptionPane.showMessageDialog(null, String.format("Gracias por jugar! %nLa aplicación ahora se cerrará..."));                    
                datos.actualizar("salir");
                cerrarServidor();
                System.exit(0);
            }                
            datos.reiniciar();
            datos.actualizar("reiniciar");
            //Fin de una partida
        }
    }
    
    private void primeros2Registros(){
        while (cantClientes < 2 && hiloAtencion == Thread.currentThread()) {
            try {
                Socket skt = srv.accept();
                cantClientes++;                   
                    
                GestorCliente nuevoCliente = new GestorCliente(this, skt);                                    
                clientes.add(nuevoCliente);           
                System.out.println(String.format("Agregando cliente #%d%nDirección: %s",cantClientes, nuevoCliente));                    
                registrar(nuevoCliente);
                    
                Thread hiloCliente = new  Thread (nuevoCliente);
                hiloCliente.start();                    
            } catch (SocketTimeoutException e) {
                // No se ha conectado ningún cliente.
                // Se mantiene esperando conexiones..                    
            } catch (IOException ex) {
                    
            }             
        }
        System.out.println("El mínimo de jugadores es 2");
        //JOptionPane.showMessageDialog(null,"El mínimo de jugadores es 2");               
    }
    
    private void tercerRegistro() throws IOException{   //Acepta a algún otro cliente que está solicitando ingresar
        try {
            srv.setSoTimeout(3000);
            datos.actualizar("esperar");
            Socket skt = srv.accept();                        
            cantClientes++;
                    
            GestorCliente nuevoCliente = new GestorCliente(this, skt);                                
            clientes.add(nuevoCliente);           
            System.out.println("Agregando: " + nuevoCliente + "Cliente" + cantClientes);
            registrar(nuevoCliente);
                    
            Thread hiloCliente = new  Thread (nuevoCliente);
            hiloCliente.start();                    
               
            Jugador j;        
            clientes.get(2).escribirMensajeCliente("registro");                  
            j = clientes.get(2).leerJugadorCliente();
            clientes.get(2).escribirMensajeCliente(j.getNombreUsuario());      
            datos.agregarJugador(j);
            System.out.println(j);   
            
        } catch (SocketTimeoutException e) {
            // No se conectó un tercer cliente.   
            System.out.println("No se conectó un tercer cliente, continuando...");
        }
    }
    
    private void controlarRegistros(){
        Jugador j;
        for(GestorCliente cliente: clientes){
            cliente.escribirMensajeCliente("registro");                 
            j = cliente.leerJugadorCliente();
            cantJugadores++;
            j.setId(String.valueOf(cantJugadores));
            datos.agregarJugador(j);
            System.out.println(j);
        }                                      
    }
    
    private void reparteCartas(){
        datos.repartirCroupier();     //El croupier obtiene dos cartas del mazo        
        
        //Se le dan 2 cartas a cada jugador y se le envían las cartas a sus clientes para que las dibujen
        for(int i = 0; i < cantClientes; i++){                  
            clientes.get(i).dibujarCartas(datos.repartidaInicial(i));             
        }                
    }
    
    private void controlaPartida(){                                
        switch(cantClientes){
            case 2:
                iniciaPartidaDe2();
                break;
            case 3:
                iniciaPartidaDe3();
                break;
        }
    }    
    
    private void iniciaPartidaDe2(){
        datos.actualizar("turno");        
        String solicitud1;
        String solicitud2;
            
            while(!datos.devuelveJugador(0).getListo()){
                solicitud1 = clientes.get(0).leerMensajeCliente();
                if(solicitud1.equals("carta")){
                    clientes.get(0).escribirMensajeCliente(datos.entregaCarta(0));
                } 
                if(solicitud1.equals("apostar")){
                    //////////////////////
                }  
                if(solicitud1.equals("quedarse")){
                    datos.devuelveJugador(0).setListo(true);
                }  
                
                solicitud1 = "";
            }
            
            while(!datos.devuelveJugador(1).getListo()){
                solicitud2 = clientes.get(1).leerMensajeCliente();
                if(solicitud2.equals("carta")){
                    clientes.get(1).escribirMensajeCliente(datos.entregaCarta(1));
                } 
                if(solicitud2.equals("apostar")){
                    //////////////////////
                }  
                if(solicitud2.equals("quedarse")){
                    datos.devuelveJugador(1).setListo(true);
                }  
                
                solicitud2 = "";
            }                          
                                                        
        //Comienza el turno del Croupier            
        while(datos.crupierNecesitaCarta().equals("SI")){
            datos.actualizar(1);    //El Croupier tomó una carta más que se dibujará en las vistas
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {}
        }
            
        if(datos.croupierPerdio()){
            //Notificar a los usuarios que ellos ganaron
            //Revelar las cartas del croupier            
        }
            
        //Compara las manos del Croupier con las de los jugadores y termina la partida
        datos.comparaManos(0);   
        datos.comparaManos(1);        
        
        datos.muestraCartasCroupier();
    }
    
    private void iniciaPartidaDe3(){
        datos.actualizar("turno");
        boolean jugadoresListos = false;
        String solicitud1 = "";
        String solicitud2 = "";
        String solicitud3  = "";

        //Espera a que los jugadors se "planten"
        while(jugadoresListos == false){               
            if(!datos.devuelveJugador(0).getListo()){
                solicitud1 = clientes.get(0).leerMensajeCliente();
            }            
            if(!datos.devuelveJugador(1).getListo()){
                solicitud2 = clientes.get(1).leerMensajeCliente();
            }            
            if(!datos.devuelveJugador(2).getListo()){
                solicitud3 = clientes.get(2).leerMensajeCliente();
            }

            //Preguntar si quieren una carta
            if(solicitud1.equals("carta")){
                clientes.get(0).escribirMensajeCliente(datos.entregaCarta(0));
            }                    
            if(solicitud2.equals("carta")){
                clientes.get(1).escribirMensajeCliente(datos.entregaCarta(1));   
            }
            if(solicitud3.equals("carta")){
                clientes.get(2).escribirMensajeCliente(datos.entregaCarta(2));   
            }
            
            //Preguntar si quieren apostar
            if(solicitud1.equals("apostar")){
                //////////////////////
            }                    
            if(solicitud2.equals("apostar")){
                //////////////////////
            }
            if(solicitud3.equals("apostar")){
                //////////////////////
            }
            
            //Preguntar si quieren plantarse
            if(solicitud1.equals("plantarse")){
                datos.devuelveJugador(0).setListo(true);
            }                    
            if(solicitud2.equals("carta")){
                datos.devuelveJugador(1).setListo(true);
            }
            if(solicitud3.equals("carta")){
                datos.devuelveJugador(2).setListo(true);
            }
            
            solicitud1 = "";
            solicitud2 = ""; 
            solicitud3 = ""; 
        
             //Pregunta si ya todos los jugadores están listos            
            jugadoresListos = (!datos.devuelveJugador(0).getListo() && !datos.devuelveJugador(1).getListo() && !datos.devuelveJugador(2).getListo());   
        }                        
            
            
            //Comienza el turno del Croupier            
        while(datos.crupierNecesitaCarta().equals("SI")){
                datos.actualizar(1);    //El Croupier tomó una carta más que se dibujará en las vistas
        }
            
        if(datos.croupierPerdio()){
            //Notificar a los usuarios que ellos ganaron
            //Revelar las cartas del croupier            
        }
            
        //Compara las manos del Croupier con las de los jugadores y termina la partida
        datos.comparaManos(0);   
        datos.comparaManos(1);
        datos.comparaManos(2);
        
        datos.muestraCartasCroupier();
    }
    
    public void eliminarClientes() {
        for(GestorCliente cliente: clientes){
           clientes.remove(cliente);
           datos.deleteObserver(cliente);
        }
    }
    
    public void retirarCliente(GestorCliente cliente) {
        clientes.remove(cliente);
        datos.deleteObserver(cliente);
        System.out.println("Cliente eliminado: " + cliente);
    }
    
    public void cerrarServidor(){            
           try{               
               for(GestorCliente cliente: clientes){
                   if(cliente!=null){
                       cliente.cerrarGestorCliente();
                   }
                }                
               srv.close();
               System.out.println("Se ha cerrado el servidor");
           }
           catch (Exception ex)
           {   
               System.err.println(ex.getMessage());
           }
    }
    
    //Atributos    
    private Modelo datos;
    private Thread hiloAtencion;
    private ServerSocket srv;
    private ArrayList<GestorCliente> clientes;
    private int cantClientes;
    private int cantJugadores;
}
