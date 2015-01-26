package modelo;

import java.util.ArrayList;
import java.util.Random;

public class ConjuntoCarta {
    
    // Constructor
    public ConjuntoCarta() {
        atras = new Carta(DireccionCarta.DIRECCION_ATRAS,"Comodin", 0);
        mazo = new ArrayList();
        inicializarMazo();
        treboles = new ArrayList();
        diamantes = new ArrayList();
        bastos = new ArrayList();
        
    }
    
    // Metodos
    
    private void inicializarMazo(){
        mazo.add(new Carta(DireccionCarta.DIRECCION_Acorazones,"Corazones",1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Doscorazones,"Corazones",2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trescorazones,"Corazones",3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrocorazones,"Corazones",4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincocorazones,"Corazones",5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seiscorazones,"Corazones",6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietecorazones,"Corazones",7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochocorazones,"Corazones",8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevecorazones,"Corazones",9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Diezcorazones,"Corazones",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncecorazones,"Corazones",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docecorazones,"Corazones",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trececorazones,"Corazones",10));
        
        mazo.add(new Carta(DireccionCarta.DIRECCION_Atrebol,"Trebol",1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dostrebol,"Trebol",2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trestrebol,"Trebol",3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrotrebol,"Trebol",4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincotrebol,"Trebol",5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seistrebol,"Trebol",6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietetrebol,"Trebol",7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochotrebol,"Trebol",8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevetrebol,"Trebol",9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dieztrebol,"Trebol",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncetrebol,"Trebol",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docetrebol,"Trebol",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trecetrebol,"Trebol",10));
        
        mazo.add(new Carta(DireccionCarta.DIRECCION_Adiamantes,"Diamantes",1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dosdiamantes,"Diamantes",2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Tresdiamantes,"Diamantes",3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrodiamantes,"Diamantes",4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincodiamantes,"Diamantes",5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seisdiamantes,"Diamantes",6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietediamantes,"Diamantes",7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochodiamantes,"Diamantes",8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevediamantes,"Diamantes",9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Diezdiamantes,"Diamantes",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncediamantes,"Diamantes",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docediamantes,"Diamantes",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trecediamantes,"Diamantes",10));
        
        mazo.add(new Carta(DireccionCarta.DIRECCION_Abastos,"Bastos",1));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Dosbastos,"Bastos",2));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Tresbastos,"Bastos",3));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cuatrobastos,"Bastos",4));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Cincobastos,"Bastos",5));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Seisbastos,"Bastos",6));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Sietebastos,"Bastos",7));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Ochobastos,"Bastos",8));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Nuevebastos,"Bastos",9));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Diezbastos,"Bastos",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Oncebastos,"Bastos",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Docebastos,"Bastos",10));
        mazo.add(new Carta(DireccionCarta.DIRECCION_Trecebastos,"Bastos",10));
    }
    
    public int obtenerNumeroCarta(ArrayList<Carta> mazo, int posicion){
        return mazo.get(posicion).getNumero();
    }
    
    public String obtenerDireccionCarta(ArrayList<Carta> mazo, int posicion){
        return mazo.get(posicion).getDIRECCION_CARTA();
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public Carta getAtras() {
        return atras;
    }
    
    public Carta generarCartaAleatoria(){
        return seleccionarCarta();
    }
    
    // Metodos Helpers
    
    private ArrayList<Carta> seleccionarMazo(int mazo){
        ArrayList<Carta> mazoSeleccionado = null;
        switch(mazo){
//            case 1 :
//                mazoSeleccionado = getCorazones();
//                break;
//            case 2:
//                mazoSeleccionado = getTreboles();
//                break;
//            case 3:
//                mazoSeleccionado = getDiamantes();
//                break;
//            case 4:
//                mazoSeleccionado = getBastos();
//                break;
        }
        
        return mazoSeleccionado;
    }
    
    private int elegirNumeroDeCarta(){
        return rnd.nextInt(52);
    }
    
    private Carta seleccionarCarta(){
        Carta cartaElegida = mazo.get(elegirNumeroDeCarta());
        usadas.add(cartaElegida);
        mazo.remove(cartaElegida);
        return cartaElegida;
    }
   
    
    // Atributos
    
    private ArrayList<Carta> mazo;
    private ArrayList<Carta> usadas;
    private ArrayList<Carta> treboles;
    private ArrayList<Carta> diamantes;
    private ArrayList<Carta> bastos;
    private Carta atras;
    
    private Random rnd = new Random();
    
    
}
