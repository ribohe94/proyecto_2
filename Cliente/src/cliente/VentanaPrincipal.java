
package cliente;

import vista.VentanaCliente;
import vista.VentanaRegistro;

public class VentanaPrincipal {
    public static void main(String[] args) {           
//            VentanaRegistro aplicacion= new VentanaRegistro();
//            aplicacion.mostrar();
        VentanaCliente vc = new VentanaCliente(0);
        vc.iniciar();
    } 
}
