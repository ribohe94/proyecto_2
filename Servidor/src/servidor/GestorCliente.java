
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
    
    public void notificarNumeroCliente(){               
        switch(nCliente){
            case 1:
                escribirEntradaCliente("primero");
                break;
            case 2:
                escribirEntradaCliente("segundo");
                break;
            case 3:
                escribirEntradaCliente("tercero");
                break;
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
    
    public int leerCartaCliente(){
        int num = 0;
        try {
              num =  (int)entrada.readObject();                        
        } catch (ClassNotFoundException ex) {
                    
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return num;
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
