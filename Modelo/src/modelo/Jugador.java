
package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements Serializable{

    public Jugador(String nombreUsuario, String pass) {
        this.nombreUsuario = nombreUsuario;
        this.pass = pass;
        fichas = 1000;
        cartasMano = new ArrayList();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    //Para Verificar coincidencia de password para jugadores previamente registrados
    public String getPass(){
        return pass;
    }

    public int getFichas(){
        return fichas;
    }

    public void setFichas(int fichas) {
        this.fichas = fichas;
    }        
    
    //Agrega una carta a la mano
    public boolean agregaCarta(Carta carta){
        cartasMano.add(carta);
        return sumaCartasActual() <= 21;
    }
    
    //Limpia la mano de cartas
    public void limpiaMano(){
        cartasMano.clear();
    }
    
    //Devuelve la suma de todas las cartas en la mano
    public int sumaCartasActual(){
        int numero = 0;
        
        for(Carta carta : cartasMano){
            numero += carta.getNumero();
        }
        
        return numero;
    }

    @Override
    public String toString() {
        return nombreUsuario;
    }          
    
    //Atributo
    private String nombreUsuario;
    private String pass;
    private int fichas;
    private ArrayList<Carta> cartasMano;
}
