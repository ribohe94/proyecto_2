
package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Observer;
import javax.swing.JOptionPane;
import modelo.Jugador;
import modelo.Mazo;
import modelo.Modelo;
import protocolo.Protocolo;

public class Servidor {

    public Servidor(Modelo nuevoModelo) {
        datos = nuevoModelo;
        clientes = new ArrayList<>();
        cantClientes = 0;
        mazo = new Mazo();
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
            //srv.setSoTimeout(1000);
        
            while (cantClientes < 2 && hiloAtencion == Thread.currentThread()) {
                try {
                    Socket skt = srv.accept();
                    cantClientes++;
                    
                    GestorCliente nuevoCliente = new GestorCliente(this, skt, cantClientes);
                    nuevoCliente.notificarNumeroCliente();
                    
                    clientes.add(nuevoCliente);           
                    System.out.println("Agregando: " + nuevoCliente + "Cliente" + cantClientes);
                    registrar(nuevoCliente);
                    
                    Thread hiloCliente = new  Thread (nuevoCliente);
                    hiloCliente.start();                    
                } catch (SocketTimeoutException e) {
                    // No se ha conectado ningún cliente.
                    // Se mantiene esperando conexiones..                    
                }             
            }
            JOptionPane.showMessageDialog(null,"El mínimo de jugadores es 2");                        
            
            controlarRegistros(); 
            datos.actualizar("ocultar");                        
            
            while (true){//Comienza una partida
                if(cantClientes == 2){
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
                datos.actualizar("reiniciar");
            }
        } catch (Exception e) {            
            System.err.println(e.getMessage());
        }
    }
    
    private void tercerRegistro() throws IOException{   //Acepta a algún otro cliente que está solicitando ingresar
        try {
            Socket skt = srv.accept();
            cantClientes++;
                    
            GestorCliente nuevoCliente = new GestorCliente(this, skt, cantClientes);
            nuevoCliente.notificarNumeroCliente();
                    
            clientes.add(nuevoCliente);           
            System.out.println("Agregando: " + nuevoCliente + "Cliente" + cantClientes);
            registrar(nuevoCliente);
                    
            Thread hiloCliente = new  Thread (nuevoCliente);
            hiloCliente.start();                    
        } catch (SocketTimeoutException e) {
            // No se conectó un tercer cliente.                    
        }
        
        Jugador j = null;
        
        clientes.get(2).escribirEntradaCliente("turno");                  
        j = clientes.get(2).leerEntradaCliente();
        clientes.get(2).escribirEntradaCliente(j.getNombreUsuario());      
        datos.agregarJugador(j);
        System.out.println(j);         
    }
    
    public void controlarRegistros(){
        Jugador j = null;
        for(GestorCliente cliente: clientes){
            cliente.escribirEntradaCliente("turno");                  
            j = cliente.leerEntradaCliente();
            cliente.escribirEntradaCliente(j.getNombreUsuario());      
            datos.agregarJugador(j);
            System.out.println(j);
        }                                      
    }
    
    public void reparteCartas(){
        cartasMano.add(mazo.getCarta());
        
        for(int i = 0; i < cantClientes; i++){
            datos.entregaCarta(i, mazo.getCarta());
        }   
        
        cartasMano.add(mazo.getCarta());
        
        for(int i = 0; i < cantClientes; i++){
            datos.entregaCarta(i, mazo.getCarta());
        }  
    }
    
    public void controlarTurnos(){
        int carta1, carta2, carta3;
        
        clientes.get(0).escribirEntradaCliente("lanzar");
        carta1 = clientes.get(0).leerCartaCliente();
        System.out.println("Carta #1: " + carta1);
        datos.actualizar(String.format("%d%d",1,carta1));        
        
        clientes.get(1).escribirEntradaCliente("lanzar");
        carta2 = clientes.get(1).leerCartaCliente();
        System.out.println("Carta #2: " + carta2);
        datos.actualizar(String.format("%d%d",2,carta2));
                
        clientes.get(2).escribirEntradaCliente("lanzar");
        carta3 = clientes.get(2).leerCartaCliente();
        System.out.println("Carta #3: " + carta3);
        datos.actualizar(String.format("%d%d",3,carta3));
        
        compararLanzamientos(carta1, carta2, carta3);
    }    
    
    public void compararLanzamientos(int carta1, int carta2, int carta3){
        //Compara las 3 cartas
        if(carta1 > carta2 && carta1 > carta3){
            JOptionPane.showMessageDialog(null, String.format("El ganador es el Jugador #1!%nValor de la carta más alta: %d", carta1));
            return;
        }
        if(carta2 > carta1 && carta2 > carta3){
            JOptionPane.showMessageDialog(null, String.format("El ganador es el Jugador #2!%nValor de la carta más alta: %d", carta2));
            return;
        }          
        if(carta3 > carta1 && carta3 > carta2){
            JOptionPane.showMessageDialog(null, String.format("El ganador es el Jugador #3!%nValor de la carta más alta: %d", carta3));
            return;
        }  
        
        if(carta1 == carta2 && carta1 > carta3){
            JOptionPane.showMessageDialog(null, String.format("Hay un empate entre el Jugador #1 y el Jugador #2!%nValor de la carta más alta: %d", carta1));
            return;
        }
        if(carta1 == carta3 && carta1 > carta2){
            JOptionPane.showMessageDialog(null, String.format("Hay un empate entre el Jugador #1 y el Jugador #3!%nValor de la carta más alta: %d", carta1));
            return;
        }
        if(carta2 == carta3 && carta2 > carta1){
            JOptionPane.showMessageDialog(null, String.format("Hay un empate entre el Jugador #2 y el Jugador #3!%nValor de la carta más alta: %d", carta2));            
            return;
        }
        
        if(carta1 == carta2 && carta1 == carta3){
            JOptionPane.showMessageDialog(null, String.format("Hay un empate entre todos los jugadores!%nValor de la carta más alta: %d", carta1));            
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
    private Mazo mazo;
    private ArrayList<Integer> cartasMano;
}
