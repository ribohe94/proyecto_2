
package modelo;

import java.util.ArrayList;

public class ConjuntoJugador {
    
    //<editor-fold defaultstate="collapsed" desc=" Constructor">

    public ConjuntoJugador() {
        jugadores = new ArrayList<>();
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Metodos">
    
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
    
    //Reduce la cantidad de fichas de un usuario
    public boolean restaFichas(String usuario, int cantFichas){
        Jugador jugador = recuperarJugador(usuario);
        if(jugador != null){
            jugador.setFichas(jugador.getFichas() - cantFichas);
            return true;
        }else{
            return false;
        }
    }
    
    //Aumenta la cantidad de fichas de un usuario
    public boolean aumentaFichas(String usuario, int cantFichas){
        Jugador jugador = recuperarJugador(usuario);
        if(jugador != null){
            jugador.setFichas(jugador.getFichas() + cantFichas);
            return true;
        }else{
            return false;
        }
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Atributos">
    
    private ArrayList<Jugador> jugadores;
    // </editor-fold>
}
