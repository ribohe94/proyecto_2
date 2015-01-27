package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Mazo {

    //<editor-fold defaultstate="collapsed" desc=" Constructor">
    public Mazo() {
        atras = new Carta(DireccionCarta.DIRECCION_ATRAS, "Comodin", 0);
        mazo = new ArrayList();
        inicializarMazo();
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Metodos">
    private void inicializarMazo() {
        if (mazo.isEmpty() == false) {
            mazo.clear();
        }
        mazo.add(new Carta(DireccionCarta.DIRECCION_Acorazones, "Corazones", 1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Doscorazones, "Corazones", 2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trescorazones, "Corazones", 3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrocorazones, "Corazones", 4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincocorazones, "Corazones", 5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seiscorazones, "Corazones", 6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietecorazones, "Corazones", 7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochocorazones, "Corazones", 8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevecorazones, "Corazones", 9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Diezcorazones, "Corazones", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncecorazones, "Corazones", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docecorazones, "Corazones", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trececorazones, "Corazones", 10));

        mazo.add(new Carta(DireccionCarta.DIRECCION_Atrebol, "Trebol", 1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dostrebol, "Trebol", 2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trestrebol, "Trebol", 3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrotrebol, "Trebol", 4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincotrebol, "Trebol", 5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seistrebol, "Trebol", 6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietetrebol, "Trebol", 7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochotrebol, "Trebol", 8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevetrebol, "Trebol", 9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dieztrebol, "Trebol", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncetrebol, "Trebol", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docetrebol, "Trebol", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trecetrebol, "Trebol", 10));

        mazo.add(new Carta(DireccionCarta.DIRECCION_Adiamantes, "Diamantes", 1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dosdiamantes, "Diamantes", 2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Tresdiamantes, "Diamantes", 3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrodiamantes, "Diamantes", 4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincodiamantes, "Diamantes", 5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seisdiamantes, "Diamantes", 6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietediamantes, "Diamantes", 7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochodiamantes, "Diamantes", 8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevediamantes, "Diamantes", 9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Diezdiamantes, "Diamantes", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncediamantes, "Diamantes", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docediamantes, "Diamantes", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trecediamantes, "Diamantes", 10));

        mazo.add(new Carta(DireccionCarta.DIRECCION_Abastos, "Bastos", 1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dosbastos, "Bastos", 2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Tresbastos, "Bastos", 3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrobastos, "Bastos", 4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincobastos, "Bastos", 5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seisbastos, "Bastos", 6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietebastos, "Bastos", 7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochobastos, "Bastos", 8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevebastos, "Bastos", 9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Diezbastos, "Bastos", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncebastos, "Bastos", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docebastos, "Bastos", 10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trecebastos, "Bastos", 10));
        barajear();
    }

    public void reiniciarMazo() {
        inicializarMazo();
    }

    public int obtenerNumeroCarta(int posicion) {
        return mazo.get(posicion).getNumero();
    }

    public String obtenerDireccionCarta(int posicion) {
        return mazo.get(posicion).getDIRECCION_CARTA();
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public Carta getAtras() {
        return atras;
    }

    // Devuelve null si ya no hay cartas en el mazo
    // Tambien elige el valor que debe tomar la carta si esta es un "As" 0 o 11
    public Carta generarCarta(int sumaCartasActual) {
        Carta cartaSeleccionada = null;
        if (mazo.size() > 0) {
            cartaSeleccionada = mazo.get(mazo.size() - 1);
            mazo.remove(mazo.size() - 1);
            if (cartaSeleccionada.getNumero() == 1) {
                elegirValorDeAs(cartaSeleccionada, sumaCartasActual);
            }
        }
        return cartaSeleccionada;
    }

    private void elegirValorDeAs(Carta as, int sumaCartasActual) {
        if (sumaCartasActual < 11) {
            as.setNumero(11);
        }
    }

    private void barajear() {
        Collections.shuffle(mazo);
    }
     // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Atributos">
    private ArrayList<Carta> mazo;
    private Carta atras;

    private Random rnd = new Random();
    // </editor-fold>

}
