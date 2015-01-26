
package modelo;

import java.util.Observable;

public class Modelo extends Observable{

    public Modelo() {
        jugadores = new ConjuntoJugador();
        mazo = new Mazo();
    }
    
    public void agregarJugador(Jugador nuevoJugador){
        jugadores.agregarJugador(nuevoJugador);
        registrarCambios(nuevoJugador);
    }
    
    public boolean entregaCarta(int pos, Carta carta){
        return jugadores.recuperarJugador(pos).agregaCarta(carta);
    }
    
    //Limpia la manos de cada usuario
    public void nuevaMano(){
        jugadores.nuevaMano();
    }
    
    //Reduce la cantidad de fichas de un usuario
    public boolean restaFichas(String usuario, int cantFichas){
        Jugador jugador = jugadores.recuperarJugador(usuario);
        if(jugador != null){
            jugador.setFichas(jugador.getFichas() - cantFichas);
            return true;
        }else{
            return false;
        }
    }
    
    //Aumenta la cantidad de fichas de un usuario
    public boolean aumentaFichas(String usuario, int cantFichas){
        Jugador jugador = jugadores.recuperarJugador(usuario);
        if(jugador != null){
            jugador.setFichas(jugador.getFichas() + cantFichas);
            return true;
        }else{
            return false;
        }
    }
    
    //La casa le entrega una carta al jugador
    public boolean entregaCarta(String usuario,  Carta carta){
        Jugador jugador = jugadores.recuperarJugador(usuario);
        if(jugador != null){
            jugador.agregaCarta(carta);
            return true;
        }else{
            return false;
        }
    }
    
    //Compara manos entre el servidor (casa) y los usuarios
    // Nota: los usuarios no juegan entre "sí", cada usuario juega contra la casa. Independientemente
    // de otro usuario 
    public void comparaManos(int manoServidor, Jugador jugador){
        if(manoServidor == 21){
            registrarCambios("Gana la casa!");
            return;
        }
        
        int cantJugadores = jugadores.getCantJugadores();
        
            if(jugador.sumaCartasActual() == 21){
                registrarCambios("Gana el jugador " + jugador.getNombreUsuario() + " con un 21!");
                return;
            }           
            
            
            if(manoServidor > jugador.sumaCartasActual()){
                registrarCambios("Gana la casa por carta alta con " + manoServidor + " !");      
            }else{
                registrarCambios("Gana el jugador " + jugador.getNombreUsuario() + " por carta alta con " + jugador.sumaCartasActual() + " !");      
            }              
    }
    
    public void registrarCambios(Object evento){
        setChanged();
        notifyObservers(evento);
    }    
    
    //Atributos
    private ConjuntoJugador jugadores;   
    private Mazo mazo;
}
