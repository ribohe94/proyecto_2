package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Jugador implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc=" Constructor">

    public Jugador(String nombreUsuario, String pass) {
        this.nombreUsuario = nombreUsuario;
        this.pass = pass;
        fichas = 1000;
        apuesta = 0;
        cartasMano = new ArrayList();
        listo = false;
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Metodos">
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    //Para Verificar coincidencia de password para jugadores previamente registrados
    public String getPass() {
        return pass;
    }

    public int getFichas() {
        return fichas;
    }

    public void setFichas(int fichas) {
        this.fichas = fichas;
    }
    
    public int getApuesta(){
        return apuesta;
    }
    
    public void setApuesta(int apuesta){
        this.apuesta = apuesta;
    }
    
    public ArrayList<Carta> getCartasMano(){
        return cartasMano;
    }
    
    public void setListo(boolean listo){
        this.listo = listo;
    }
    
    // Nos indica si el jugador ya no quiere más cartas, ni apostar más
    // Osea se PLANTA. Entonces gracias a esto indica al Croupier que es
    // hora de comparar cartas
    public boolean getListo(){
        return listo;
    }

    //Agrega una carta a la mano
    public boolean agregaCarta(Carta carta) {
        cartasMano.add(carta);
        return sumaCartasActual() <= 21;
    }

    //Limpia la mano de cartas
    public void limpiaMano() {
        cartasMano.clear();
    }

    //Devuelve la suma de todas las cartas en la mano
    public int sumaCartasActual() {
        int numero = 0;

        if (cartasMano.isEmpty() == false) {
            for (Carta carta : cartasMano) {
                numero += carta.getNumero();
            }
        }

        return numero;
    }

    @Override
    public String toString() {
        return nombreUsuario;
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Atributos">
    
    private String nombreUsuario;
    private String pass;
    private int fichas;
    private int apuesta;
    private ArrayList<Carta> cartasMano;
    private boolean listo;
    // </editor-fold>
}
