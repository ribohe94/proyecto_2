
package modelo;

import java.util.ArrayList;
import java.util.Collections;

public class Mazo {
    public Mazo(){
        mazoCartas = new ArrayList<>();
        for(int i = 0; i < 13; i++){
            mazoCartas.add(i);
        }
        
        Collections.shuffle(mazoCartas);       
    }
    
    //Saca la última carta del mazo y la devuelve
    public int getCarta(){         
        if(mazoCartas.size() > 0){
            int pos = mazoCartas.size() - 1;
            int carta = mazoCartas.get(pos);
        
            mazoCartas.remove(pos);
            return carta;
        }else{
            return 0;   //Ya no hay cartas en el mazo
        }        
    }
    
    //Saca las cartas que queden restantes y le ingresa nuevas cartas. Luego revuelve el mazo
    public void reiniciaMazo(){
        mazoCartas.clear();
        
        for(int i = 0; i < 13; i++){
            mazoCartas.add(i);
        }
        
        Collections.shuffle(mazoCartas); 
    }
    
    
    //Atributos
    private ArrayList<Integer> mazoCartas;    
}
