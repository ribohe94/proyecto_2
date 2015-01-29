
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import modelo.Jugador;

public class GestorCliente implements Observer, Runnable {

    public GestorCliente(Servidor nuevoGestor, Socket skt, int num) {
        gestorPrincipal = nuevoGestor;
        direccionCliente = skt.getInetAddress();
        nCliente = num;
        nEvento = 0;
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
        escribirEntradaCliente(evt);        
    }
    
    @Override
    public void run() {    
    
    }
    
    public void dibujarCartas(String[] rutas){
        for(int i = 0; i < rutas.length; i++){
            escribirEntradaCliente("ruta" + rutas[i]);
        }
    }
    
    public void escribirEntradaCliente(Object e){
        try {
          salida.writeObject(e);          
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }        
    }
    
    public Jugador leerEntradaCliente(){
        Jugador j = null;
        try {
              j =  (Jugador)entrada.readObject();                        
        } catch (ClassNotFoundException ex) {
                    
                } 
        catch (IOException ex) {            
            System.err.println(ex.getMessage());
        }
        return j;
    } 

    public int getnCliente() {
        return nCliente;
    }
    
    public void cerrarGestorCliente (){
     try {
         escribirEntradaCliente("salida");
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
                "Cliente@ %s", direccionCliente.getHostName(),nCliente);
    }
    
    private Servidor gestorPrincipal;
    private InetAddress direccionCliente;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    private Socket socket;
    private int nCliente;
    private int nEvento;
}
