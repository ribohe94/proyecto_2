package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.Carta;
import modelo.Jugador;
import protocolo.Protocolo;
import vista.VentanaCliente;
import vista.VentanaRegistro;

public class Cliente implements Runnable {

    public Cliente(VentanaRegistro ventanaRegistro) {
        this.ventanaRegistro = ventanaRegistro;
        ventanaCliente = new VentanaCliente(this);
    }

    @Override
    public void run() {
        while (true) {
            leer();
            leer();
        }
    }

    public void iniciar() {
        try {
            skt = new Socket("localhost", Protocolo.PUERTO_POR_DEFECTO);
            entrada = new ObjectInputStream(skt.getInputStream());
            salida = new ObjectOutputStream(skt.getOutputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void leer() {
        try {            
            Object objeto = entrada.readObject(); 
            
            if(objeto instanceof Carta[]){
                ventanaCliente.dibujarCartasCroupier((Carta[])objeto);                
                System.out.println(ventanaCliente.getSumaManoCroupier());                
                return;
            }  

            if(objeto instanceof Carta){
                ventanaCliente.dibujaCartaUsuario((Carta)objeto);                
                System.out.println("Suma de la mano del Jugador" + ventanaCliente.getSumaManoJugador());                
                if(ventanaCliente.getSumaManoJugador() > 20){
                    ventanaCliente.deshabilitarBotones();
                    escribirMensajeServidor("quedarse");
                }
                return;
            }                             
        
            String mensaje = objeto.toString();
            
            try{
                int cantCartasCroupier = Integer.parseInt(mensaje);
                ventanaCliente.agregaCartaCroupier(cantCartasCroupier);
                return;
            }catch(NumberFormatException ex){}            
            
            if (mensaje.equals("salir")) {
                System.out.println("Se ha cerrado el cliente");
                System.exit(0);
            }

            if (mensaje.equals("ocultar")) {
                esconderVentanaRegistro();
                return;
            }

            if (mensaje.equals("reiniciar")) {
                reiniciar();
                return;
            }

            if (mensaje.equals("registro")) {
                System.out.println("Mi turno de registrarme");
                ventanaRegistro.habilitarBoton();
                return;
            }

            if (mensaje.equals("turno")) {
                System.out.println("Mi turno");
                ventanaCliente.iniciar();
                ventanaCliente.habilitarLanzar();
                return;
            }                        

            if (mensaje.equals("esperar")) {               
                final JOptionPane pane = new JOptionPane(String.format("Esperando a un tercer cliente.%n(Por favor esperar 3 segundos...)"), JOptionPane.INFORMATION_MESSAGE);                
                final JDialog dialog = pane.createDialog(ventanaCliente, "Esperando");
                
                new Thread(new Runnable(){
                    public void run(){
                        try{
                            Thread.sleep(3000);
                            dialog.dispose();

                        }catch ( Throwable th ){}
                    }
                }).start();
                dialog.setVisible(true);

                return;
            }  
            
            //Quitar esto
            if(!mensaje.isEmpty()){
                System.out.println("Mensaje perdido: " + mensaje);                
            }            
        } catch (IOException | ClassNotFoundException  | ClassCastException ex) {

        }
    }

    public void escribirMensajeServidor(Object obj) {
        try {
            salida.writeObject(obj);
        } catch (Exception ex) {
        }
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        ventanaCliente.asignaUsuario(jugador.getNombreUsuario());
        ventanaCliente.asignaCantFichas(jugador.getFichas());
    }

    public int getCantFichas() {
        return ventanaCliente.getCantFichas();
    }

    public void setCantFichas(int cantFichas) {
        ventanaCliente.asignaCantFichas(cantFichas);
    }

    public void esconderVentanaRegistro() {        
        ventanaRegistro.ocultarRegistro();
    }

    public void reiniciar() {
        ventanaCliente.reiniciar();
    }

    public void cerrarCliente() {
        try {
            salida.close();
            entrada.close();
            skt.close();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    //Atributos
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;

    private Socket skt;
    private VentanaRegistro ventanaRegistro;
    private VentanaCliente ventanaCliente;    
    private Jugador jugador;
}
