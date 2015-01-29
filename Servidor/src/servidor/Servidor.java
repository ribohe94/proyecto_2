
package servidor;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.Jugador;
import modelo.Modelo;
import protocolo.Protocolo;

public class Servidor {

    public Servidor(Modelo nuevoModelo) {
        datos = nuevoModelo;
        clientes = new ArrayList<>();
        cantClientes = 0;        
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
        
            //Registra los 2 primeros clientes y espera 5 segundos para un tercero 
            //  o inicia automáticamente
            primeros2Registros();            
            controlarRegistros(); 
            datos.registrarCambios("ocultar");                        
            
            while (true){//Comienza una partida
                if(cantClientes == 2){
                    System.out.println("Esperando a un posible tercer jugador...(3 segs)");
                    tercerRegistro();                    
                }
                
                //Reparte cartas a los jugadores
                reparteCartas();                
                
                //Juega una vez
                controlarTurnos();
                
                if(JOptionPane.showConfirmDialog(null, "Desea volver a jugar?","Fin del Juego", JOptionPane.YES_NO_OPTION) != JOptionPane.OK_OPTION){
                    JOptionPane.showMessageDialog(null, String.format("Gracias por jugar! %nLa aplicación ahora se cerrará..."));                    
                    cerrarServidor();
                    System.exit(0);
                }                
                datos.registrarCambios("reiniciar");
            }
        } catch (IOException | HeadlessException e) {            
            System.err.println(e.getMessage());
        }
    }
    
    private void primeros2Registros(){
        while (cantClientes < 2 && hiloAtencion == Thread.currentThread()) {
            try {
                Socket skt = srv.accept();
                cantClientes++;                   
                    
                GestorCliente nuevoCliente = new GestorCliente(this, skt, cantClientes);
                //nuevoCliente.notificarNumeroCliente();
                    
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
        JOptionPane.showMessageDialog(null,"El mínimo de jugadores es 2");               
    }
    
    private void tercerRegistro() throws IOException{   //Acepta a algún otro cliente que está solicitando ingresar
        try {
            srv.setSoTimeout(3000);
            Socket skt = srv.accept();                        
            cantClientes++;
                    
            GestorCliente nuevoCliente = new GestorCliente(this, skt, cantClientes);
            //nuevoCliente.notificarNumeroCliente();
                    
            clientes.add(nuevoCliente);           
            System.out.println("Agregando: " + nuevoCliente + "Cliente" + cantClientes);
            registrar(nuevoCliente);
                    
            Thread hiloCliente = new  Thread (nuevoCliente);
            hiloCliente.start();                    
               
            Jugador j = null;
        
            clientes.get(2).escribirMensajeCliente("registro");                  
            j = clientes.get(2).leerEntradaCliente();
            clientes.get(2).escribirMensajeCliente(j.getNombreUsuario());      
            datos.agregarJugador(j);
            System.out.println(j);   
            
        } catch (SocketTimeoutException e) {
            // No se conectó un tercer cliente.   
            System.out.println("No se conectó un tercer cliente, continuando...");
        }
    }
    
    public void controlarRegistros(){
        Jugador j;
        for(GestorCliente cliente: clientes){
            cliente.escribirMensajeCliente("registro");                 
            j = cliente.leerEntradaCliente();
            //cliente.escribirEntradaCliente(j.getNombreUsuario());      
            datos.agregarJugador(j);
            System.out.println(j);
        }                                      
    }
    
    public void reparteCartas(){
        datos.repartirCroupier();     //El croupier obtiene dos cartas del mazo        
        
         //Se le dan 2 cartas a cada jugador y se le envían las cartas a sus clientes para que las dibujen
        for(int i = 0; i < cantClientes; i++){            
            clientes.get(i).dibujarCartas(datos.repartidaInicial(i));             
        }
        
        datos.registrarCambios("turno");
            //NOTA: HAY QUE ENVIARLE LAS RUTAS DE LAS CARTAS DEL CROUPIER A CADA CLIENTE
            //PARA QUE ÉSTE LAS DIBUJE??
    }
    
    public void controlarTurnos(){                
        for(int i = 0;i < cantClientes; i++){                                             
            while(!datos.devuelveJugador(i).getListo()){
                //Espera a que el jugador se "plante"
                if(clientes.get(i).leerMensajeCliente().equals("carta")){
                    clientes.get(i).escribirMensajeCliente(datos.entregaCarta(i));
                    
                }
            }
            datos.comparaManos(i);                
        }        
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
               srv.close();
               for(GestorCliente cliente: clientes){
                   if(cliente!=null){
                       cliente.cerrarGestorCliente();
                   }
                }                
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
}
