
package modelo;

import java.util.ArrayList;

public class ConjuntoJugador {

    public ConjuntoJugador() {
        jugadores = new ArrayList<>();
    }
    
    public void agregarJugador(Jugador nuevoJugador){
        jugadores.add(nuevoJugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }
    
    //Devuelve la cantidad de jugadores
    public int getCantJugadores(){
        return jugadores.size();
    }
    
    //Devuelve un jugador, busca con el numero de posición
    public Jugador recuperarJugador(int i){
        return jugadores.get(i);
    }
    
    //Devuelve un jugador, busca con el usuario
    public Jugador recuperarJugador(String usuario){
        for(Jugador jugador : jugadores){
            if(jugador.getNombreUsuario().equals(usuario)){
                return jugador;
            }
        }
        
        return null;
    }
    
    //Limpia la manos de cada usuario
    public void nuevaMano(){
        for(Jugador jugador : jugadores){
            jugador.limpiaMano();
        }
    }        
    
    //Busca y devuelve el jugador que tenga la mano más alta
    public Jugador buscaCartaAlta(){        
        int cartaAlta = 0;
        Jugador jugador = null;
        
        for (Jugador jugadore : jugadores) {
            int a = jugadore.sumaCartasActual();
            if (a < 22) {
                if (a > cartaAlta) {
                    cartaAlta = a;                    
                    jugador = jugadore;
                }
            }                        
        }
        
        return jugador;
    }
    
    //Atributos
    private ArrayList<Jugador> jugadores;       
}
