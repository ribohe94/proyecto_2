
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import protocolo.Protocolo;
import vista.VentanaCliente;
import vista.VentanaRegistro;

public class Cliente implements Runnable {  
    
    public Cliente(VentanaRegistro ventanaRegistro){
        this.ventanaRegistro = ventanaRegistro;                        
    }
    
    @Override
    public void run() {        
        while(true){
            leer();
            leer();
        }
    }
    
    public void iniciar(){
        try {
            skt = new Socket("localhost", Protocolo.PUERTO_POR_DEFECTO);            
            entrada = new ObjectInputStream(skt.getInputStream());
            salida = new ObjectOutputStream(skt.getOutputStream());
             
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void leer(){
         String mensaje = "";
             try {
                 mensaje = entrada.readObject().toString();
                 
                 if(mensaje.equals("salida")){
                     System.exit(0);
                 }
                 
                 if(mensaje.equals("ocultar")){
                     esconderVentanaRegistro();
                     return;
                 }
                 
                 if(mensaje.equals("reiniciar")){
                     reiniciar();
                     return;
                 }
                 
                 if(mensaje.equals("turno")){
                     System.out.println("Mi turno");
                    ventanaRegistro.habilitarBoton();
                    return;
                 }
                 
                if(mensaje.equals("lanzar")){
                    ventanaCliente.iniciar();
                    ventanaCliente.habilitarLanzar();   
                    return;
                }                                   
                
//                if(mensaje.equals("primero")){
//                    numCliente = 1;
//                    ventanaCliente = new VentanaCliente(this, numCliente);
//                    return;
//                }
//                
//                if(mensaje.equals("segundo")){
//                    numCliente = 2;
//                    ventanaCliente = new VentanaCliente(this, numCliente);
//                    return;
//                }
//                
//                if(mensaje.equals("tercero")){
//                    numCliente = 3;
//                    ventanaCliente = new VentanaCliente(this, numCliente);
//                    return;
//                }
                
                try{ 
                    int numCliente = Integer.parseInt(mensaje.substring(0,1));     
                    System.out.println(mensaje);
                    switch(numCliente){
//                        case 1:
//                            ventanaCliente.asignaCartaPrimero(Integer.parseInt(mensaje.substring(1)));
//                            break;
//                        case 2:                
//                            ventanaCliente.asignaCartaSegundo(Integer.parseInt(mensaje.substring(1)));
//                            break;
//                        case 3:
//                            ventanaCliente.asignaCartaTercero(Integer.parseInt(mensaje.substring(1)));
//                            break;
                    }
                }catch(Exception ex){
                    System.out.println("Usuario: " + mensaje);
                    ventanaRegistro.mostrarMensaje(mensaje);        
                }

             } catch (IOException | ClassNotFoundException ex) {
                 
             }       
    }
    
    public void escribirMensajeServidor(Object obj){        
        try {          
          salida.writeObject(obj);
        }
        catch (Exception ex){
        }
   }        
    
    public int getNumeroCarta(){
        //return ventanaCliente.getNumeroCarta();
        return 0; //quitar
    }
    
    public int getCantFichas(){
        return ventanaCliente.getCantFichas();        
    }
    
    public void setCantFichas(int cantFichas){
        ventanaCliente.setCantFichas(cantFichas);
    }
    
    public void esconderVentanaRegistro(){
        ventanaRegistro.setVisible(false);
    }
    
    public void reiniciar(){
        ventanaCliente.reiniciar();        
    }

    public void cerrarCliente (){
     try {
         salida.close();
         entrada.close();
         skt.close();       
     }catch (Exception ex){
        System.err.println(ex.getMessage());
     }
    }
    
    public void asignaUsuario(String usuario){
        ventanaCliente.asignaUsuario(usuario);
    }

    //Atributos
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    private Socket skt;
    private VentanaRegistro ventanaRegistro;
    private VentanaCliente ventanaCliente;
    private int numCliente;    
}