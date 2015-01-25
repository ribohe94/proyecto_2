
package modelo;

import java.util.Observable;

public class Modelo extends Observable{

    public Modelo() {
        jugadores = new ConjuntoJugador();
    }
    
    public void agregarJugador(Jugador nuevoJugador){
        jugadores.agregarJugador(nuevoJugador);
        //actualizar(nuevoJugador);
    }
    
    public boolean entregaCarta(int pos, int carta){
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
    public boolean entregaCarta(String usuario, int carta){
        Jugador jugador = jugadores.recuperarJugador(usuario);
        if(jugador != null){
            jugador.agregaCarta(carta);
            return true;
        }else{
            return false;
        }
    }
    
    //Compara manos entre el servidor (casa) y los usuarios
    public void comparaManos(int manoServidor){
        if(manoServidor == 21){
            actualizar("Gana la casa!");
            return;
        }
        
        int cantJugadores = jugadores.getCantJugadores();
        
        for(int i = 0; i < cantJugadores; i++){
            if(jugadores.recuperarJugador(i).cartasMano() == 21){
                actualizar("Gana el jugador " + jugadores.recuperarJugador(i).getNombreUsuario() + " con un 21!");
                return;
            }
        }
        
        Jugador jugador = jugadores.buscaCartaAlta();               
                
        if(jugador != null){            
            if(manoServidor > jugador.cartasMano()){
                actualizar("Gana la casa por carta alta con " + manoServidor + " !");      
            }else{
                actualizar("Gana el jugador " + jugador.getNombreUsuario() + " por carta alta con " + jugador.cartasMano() + " !");      
            }                                    
        }                
    }
    
    public void actualizar(Object evento){
        setChanged();
        notifyObservers(evento);
    }    
    
    //Atributos
    private ConjuntoJugador jugadores;    
}
