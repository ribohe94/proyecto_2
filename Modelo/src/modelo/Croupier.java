package modelo;

import java.util.ArrayList;

public class Croupier {

    //<editor-fold defaultstate="collapsed" desc=" Constructor">
    
    public Croupier(Mazo mazo) {
        this.mazo = mazo;
        manoServidor = new ArrayList();
    }
    
    public Croupier(){
        this(new Mazo());
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Metodos">
    
    // Regla del BlackJack el croupier se puede plantar al tener 17 o más
    // Buscando, claramente, tener menos o igual que 21
    public boolean necesitoCarta(){
        return sumaCartasActualCasa() < 18;
    }
    
    public Carta agregarCartaCasa() {
        Carta carta = mazo.generarCarta(sumaCartasActualCasa());
        manoServidor.add(carta);
        
        return carta;
    }

    public int sumaCartasActualCasa() {
        int valor = 0;

        if (manoServidor.isEmpty() == false) {
            for (Carta carta : manoServidor) {
                valor += carta.getValor();
            }
        }

        return valor;
    }

    //Compara manos entre el servidor (casa) y un usuario
    // Nota: los usuarios no juegan entre "sí", cada usuario juega contra la casa. Independientemente
    // de otro usuario 
    // devulve true si gana la casa false si gana el jugadaor
    public boolean comparaManos(Jugador jugador) {
        boolean quienGana = true;
        int a = sumaCartasActualCasa();
        int b = jugador.sumaCartasActual();

        if (a == 21) {
            quienGana = true;
        } else {

            if (a == b) {
                quienGana = true;
            } else {
                if (a > b && a < 23) {
                    quienGana = true;
                } else {
                    if (a > b && a > 22) {
                        quienGana = false;
                    } else {
                        if (b > a && b < 23) {
                            quienGana = false;
                        } else {
                            if (b > a && b > 22) {
                                quienGana = true;
                            }
                        }
                    }
                }
            }
        }
        return quienGana;
    }

    // Se reparten dos cartas al jugador para iniciar
    // Nota: se tiene que registrar el cambio en el Modelo
    public Carta[] repartidaInicial(Jugador jugador) {
        Carta carta1 = mazo.generarCarta(jugador.sumaCartasActual());
        Carta carta2 = mazo.generarCarta(jugador.sumaCartasActual());
        
        jugador.agregaCarta(carta1);
        jugador.agregaCarta(carta2);
        Carta[] cartas = new Carta[2];
        cartas[0] = carta1;
        cartas[1] = carta2;
        
        return cartas;
    }

    // El croupier toma dos cartas para iniciar
    // Nota: se tiene que registrar el cambio en el Modelo
    public Carta[] adiquisicionInicial() {
        Carta carta1 = agregarCartaCasa();
        Carta carta2 = agregarCartaCasa();
        
        Carta[] cartas = new Carta[2];
        cartas[0] = carta1;
        cartas[1] = carta2;
        
        return cartas;
    }
    
    // Le da una carta al jugador si este la solicita
    // devuelve true si la suma de total de cartas del jugador, despues de la obtencion
    // de la nueva carta, es menor o igual a 21. Retorna false en caso contrario
    public Carta darCarta(Jugador jugador){
        Carta carta = mazo.generarCarta(jugador.sumaCartasActual());
        jugador.agregaCarta(carta);
        return carta;
    }
    
    // Se inicializa el mazo del jugador para una nueva Partida
    // Nota: se tienen que registrar los cambios en el Modelo
    public void nuevasManos(Jugador jugador){
        jugador.limpiaMano();
    }
    
    // Se inicializa el mazo para una nueva Partida
    // Nota: se tienen que registrar los cambios en el Modelo
    public void reiniciarMazo(){
        mazo.reiniciarMazo();
    }
    
    // Como el jugador perdio se le cobra la apuesta
    // y se le reducen las fichas
    public void cobrarApuesta(Jugador jugador){
        jugador.setFichas(jugador.getFichas() - jugador.getApuesta());
    }
    
    // Como el jugador gano se le paga lo que aposto
    // y mantiene la apuesta
    public void pagarApuesta(Jugador jugador){
        jugador.setFichas(jugador.getFichas() + jugador.getApuesta());
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Atributos">
    
    private ArrayList<Carta> manoServidor;
    private Mazo mazo;
    // </editor-fold>
}
