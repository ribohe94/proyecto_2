
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import modelo.Carta;

import modelo.Jugador;

public class GestorCliente implements Observer, Runnable {

    public GestorCliente(Servidor nuevoGestor, Socket skt) {
        gestorPrincipal = nuevoGestor;
        direccionCliente = skt.getInetAddress();                
        salida = null;
        entrada = null;
        this.socket = skt;
        try {
            salida = new ObjectOutputStream(socket.getOutputStream());
            entrada  = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }                
    }

    @Override
    public void update(Observable m, Object evt) {        
        escribirMensajeCliente(evt);        
    }
    
    @Override
    public void run() {    
    
    }        
    
    public void dibujarCartas(Carta[] cartas){
        for (Carta carta : cartas) {
            escribirMensajeCliente(carta);
        }
    }
    
    public void escribirMensajeCliente(Object e){
        try {
          salida.writeObject(e);          
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }        
    }
    
    public String leerMensajeCliente(){
        String mensaje = "";
        
        try {
            mensaje = entrada.readObject().toString();                        
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());        
        }
        
        return mensaje;
    }
    
    public Jugador leerJugadorCliente(){
        Jugador j = null;
        
        try {
              j =  (Jugador)entrada.readObject();                        
        } catch (ClassNotFoundException | IOException ex) {
            System.err.println(ex.getMessage());        
        }
        
        return j;
    } 
    
//    public void enviarJugadorCliente(Jugador j){
//        escribirMensajeCliente(j);
//    } 
    
    public void cerrarGestorCliente (){
     try {
         escribirMensajeCliente("salida");
         salida.close();
         entrada.close();
         socket.close();         
     }catch (Exception ex){
        System.err.println(ex.getMessage());
     }
    }
    
    @Override
    public String toString() {
        return String.format(
                "Cliente@ %s", direccionCliente.getHostName());
    }
    
    private Servidor gestorPrincipal;
    private InetAddress direccionCliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Socket socket;       
}
