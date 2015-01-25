
package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

public class PanelCarta extends JPanel implements Runnable{
    public PanelCarta(int numeroCliente){    
        this.numeroCliente = numeroCliente;
        switch(numeroCliente){
            case 1:
                numeroClienteOtro1 = 2;
                numeroClienteOtro2 = 3;
                break;
            case 2:
                numeroClienteOtro1 = 3;
                numeroClienteOtro2 = 1;
                break;
            case 3:
                numeroClienteOtro1 = 1;
                numeroClienteOtro2 = 2;
                break;    
        }
        
        numeroCartaOtro1 = 0;
        numeroCartaOtro2 = 0;
        
        rnd = new Random();                
        numeroCarta = 0;	
        usuario = "";
    }   
    
    @Override
    public void run() {
       
    }
    
    public void iniciar(){
        hiloPrincipal = new Thread(this);
        if(hiloPrincipal != null){
            hiloPrincipal.start();
        }
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        g.setColor(Color.RED);
        g.drawRoundRect(20, 20, 200, 200, 5, 5);   
        g.drawRoundRect(245, 20, 93, 93, 5, 5);   
        g.drawRoundRect(245, 128, 93, 93, 5, 5);   
                
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 200));  
        String n1 = "" + numeroCarta;
        
        if(numeroCarta > 9){
            g.drawString(n1, 20, 182);
        }else{
            g.drawString(n1, 75, 182);
        }                                
        
        g.setFont(new Font(Font.SERIF, Font.BOLD, 80));   
        String n2 = "" + numeroCartaOtro1;
        if(numeroCartaOtro1 > 9){
            g.drawString(n2, 253, 92);
        }else{
            g.drawString(n2, 273, 92);
        }          
        
        String n3 = "" + numeroCartaOtro2;
        if(numeroCartaOtro2 > 9){
            g.drawString(n3, 253, 202);
        }else{
            g.drawString(n3, 273, 202);
        }         
                
        String sss = "Cartas de '" + usuario + "'";                
        String otro1 = "Cartas del cliente #"+numeroClienteOtro1;
        String otro2 = "Cartas del cliente #"+numeroClienteOtro2;
        g.setFont(new Font(Font.SERIF, Font.ITALIC, 20));    
        g.drawString(sss, 40, 250);     
                        
        g.setFont(new Font(Font.SERIF, Font.ITALIC, 12));   
        g.drawString(otro1, 345, 70);        
        g.drawString(otro2, 345, 180);        
    }
    
    public int lanzarCarta(){      
        numeroCarta = rnd.nextInt(13) + 1;	
	repaint();        
        return numeroCarta;
    }
    
    public int getNumeroCarta(){
        return numeroCarta;
    }
    
    public void reiniciar(){
        numeroCarta = 0;
        numeroCartaOtro1 = 0;
        numeroCartaOtro2 = 0;
        repaint();
    }
    
    public void asignaUsuario(String usuario){
        this.usuario = usuario;
    }

    public int getCantFichas() {
        return cantFichas;
    }

    public void setCantFichas(int cantFichas) {
        this.cantFichas = cantFichas;
        repaint();
    }        

    public void asignaCartaPrimero(int numeroCarta){
        switch(numeroCliente){
            case 2:
                numeroCartaOtro2 = numeroCarta;
                break;
            case 3:
                numeroCartaOtro1 = numeroCarta;
                break;
        }
        repaint();
    }
    
    public void asignaCartaSegundo(int numeroCarta){
        switch(numeroCliente){
            case 1:
                numeroCartaOtro1 = numeroCarta;
                break;
            case 3:
                numeroCartaOtro2 = numeroCarta;
                break;
        }
        repaint();
    }
    
    public void asignaCartaTercero(int numeroCarta){
        switch(numeroCliente){
            case 1:
                numeroCartaOtro2 = numeroCarta;
                break;
            case 2:
                numeroCartaOtro1 = numeroCarta;
                break;
        }
        repaint();
    }

    //Atributos
    private Thread hiloPrincipal;    
    private final Random rnd;           
    
    private int numeroCarta;    
    private int numeroCartaOtro1;
    private int numeroCartaOtro2;
    
    private int numeroCliente;
    private int numeroClienteOtro1;
    private int numeroClienteOtro2;
    private String usuario;
    private int cantFichas;
}