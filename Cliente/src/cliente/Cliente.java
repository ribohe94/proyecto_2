package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
            
            if(objeto instanceof Carta){
                ventanaCliente.agregarCartaUsuario((Carta)objeto);
                return;
            }        
        
            String mensaje = objeto.toString();
            
            try{
                int cantCartasCroupier = Integer.parseInt(mensaje);
                ventanaCliente.agregaCartaCroupier(cantCartasCroupier);
                return;
            }catch(NumberFormatException ex){
                
            }            
            
            if (mensaje.equals("salida")) {
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

            //Quitar esto
            if(!mensaje.isEmpty()){
                System.out.println("Mensaje perdido: " + mensaje);                
            }            
        } catch (IOException | ClassNotFoundException ex) {

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
    private int numCliente;
    private Jugador jugador;
}
