package cliente;

import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.GestorBD;
import modelo.GestorJugador;
import modelo.Jugador;
import vista.VentanaCliente;
import vista.VentanaRegistro;

public class VentanaPrincipal {

    public static void main(String[] args) {

        VentanaRegistro aplicacion = new VentanaRegistro();
        aplicacion.mostrar();
//        Jugador j1 = new Jugador("4", "Bove", "123");
//        Jugador j2 = new Jugador("5", "Riccardo", "425");
//
//        GestorJugador gj = new GestorJugador();
        try {
//            gj.guardar(j1);
//            gj.consultar();
//        VentanaCliente vc = new VentanaCliente(null);
//        vc.iniciar();
        } catch (Exception ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

// Actualizacion will lista!!
