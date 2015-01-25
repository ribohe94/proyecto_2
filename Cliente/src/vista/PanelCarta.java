
package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
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
        
        cartasCasa = new String[6];
        cartasCliente = new String[6];
        cartasOtro1 = new String[6];
        cartasOtro2 = new String[6];
        
        for(int i = 0; i < cartasCasa.length; i++){
            cartasCasa[i] = "";
        }
        
        for(int i = 0; i < cartasCliente.length; i++){
            cartasCliente[i] = "";
        }
        
        for(int i = 0; i < cartasOtro1.length; i++){
            cartasOtro1[i] = "";
        }
        
        for(int i = 0; i < cartasOtro2.length; i++){
            cartasOtro2[i] = "";
        }
        
        //
        
        cartasCasa[0] = "?";
        cartasCasa[1] = "?";
        
        cartasCliente[0] = ""+1;
        cartasCliente[1] = ""+2;
        cartasCliente[2] = ""+3;
        cartasCliente[3] = ""+4;
        cartasCliente[4] = ""+5;
        cartasCliente[5] = ""+6;
        
        cartasOtro1[0] = ""+1;
        cartasOtro1[1] = ""+2;
        cartasOtro1[2] = ""+3;
        cartasOtro1[3] = ""+4;
        cartasOtro1[4] = ""+5;
        cartasOtro1[5] = ""+6;
        
        cartasOtro2[0] = ""+1;
        cartasOtro2[1] = ""+2;
        cartasOtro2[2] = ""+3;
        cartasOtro2[3] = ""+4;
//        cartasOtro2[4] = 5;
//        cartasOtro2[5] = 6;
        
        //        
        
        rnd = new Random();                        	
        usuario = "Prueba";
        
        try {            
            fondo = ImageIO.read(getClass().getResourceAsStream("/pruebas/imagenes/2.png"));
        } catch (IOException ex) {
            System.out.println("No se pudo cargar el fondo...");
        }               
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
        Graphics2D g2d = (Graphics2D)g;                
        g2d.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), null);                
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        //Dibuja "tableros" para todos
        g.setColor(Color.RED);                             
        g.drawRoundRect(132, 5, 300, 93, 5, 5);                   
        g.drawRoundRect(132, 445, 300, 93, 5, 5);                   
        g.drawRoundRect(5, 120, 93, 300, 5, 5);     
        g.drawRoundRect(466, 120, 93, 300, 5, 5);     
        
        //Dibuja las letras (rótulos)
        g.setFont(new Font(Font.SERIF, Font.ITALIC, 15));   
        g.setColor(Color.BLACK);
        String cliente = "Cartas de '" + usuario + "'";     
        String fichas = "Cantidad de Fichas: " + cantFichas;
        String otro1 = "Cartas del cliente #"+numeroClienteOtro1;
        String otro2 = "Cartas del cliente #"+numeroClienteOtro2;  
        
        g.drawString("Cartas de la casa", 222, 115);                
        g.drawString(cliente, 222, 438);        
        g.drawString(otro1, 101, 270);
        g.drawString(otro2, 335, 270);
        
        g.setColor(Color.BLUE);                
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 15));   
        g.drawString(fichas, 205, 415);
        
        //Dibuja los numeros de las cartas
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 80));   
        
        //Cartas Casa                   
        for(int i = 0; i < cartasCasa.length; i++){
            int posX = 136 + (i*50);
            g.drawString(cartasCasa[i], posX, 80);
        }
        
        //Cartas Cliente    
        for(int i = 0; i < cartasCliente.length; i++){
            int posX = 136 + (i*50);
            g.drawString(cartasCliente[i], posX, 520);
        }
        
        //Cartas Otro1
        g.setFont(new Font(Font.SERIF, Font.BOLD, 60));   
        for(int i = 0; i < cartasOtro1.length; i++){            
            int posY = 172 + (i*48);
            g.drawString(cartasOtro1[i], 38, posY);
        }
        
        //Cartas Otro2
        for(int i = 0; i < cartasOtro2.length; i++){
            int posY = 172 + (i*48);
            g.drawString(cartasOtro2[i], 500, posY);
        }                          
    }    
    
//    public int lanzarCarta(){      
//        numeroCarta = rnd.nextInt(13) + 1;	
//	repaint();        
//        return numeroCarta;
//    }
//    
//    public int getNumeroCarta(){
//        return numeroCarta;
//    }
    
    public void reiniciar(){
        for(int i = 0; i < cartasCasa.length; i++){
            cartasCasa[i] = "";
        }
        
        for(int i = 0; i < cartasCliente.length; i++){
            cartasCliente[i] = "";
        }
        
        for(int i = 0; i < cartasOtro1.length; i++){
            cartasOtro1[i] = "";
        }
        
        for(int i = 0; i < cartasOtro2.length; i++){
            cartasOtro2[i] = "";
        }
        
        repaint();
    }
    
    public void agregaCartaCasa(int a){
        for(int i = 0; i < cartasCasa.length; i++){
            if(cartasCasa[i].equals("")){
                cartasCasa[i] = "" + a;
                return;
            }
        }
    }
    
    public void agregaCartaCliente(int a){
        for(int i = 0; i < cartasCliente.length; i++){
            if(cartasCliente[i].equals("")){
                cartasCliente[i] = "" + a;
                return;
            }
        }
    }
    
    public void agregaCartaOtro1(int a){
        for(int i = 0; i < cartasOtro1.length; i++){
            if(cartasOtro1[i].equals("")){
                cartasOtro1[i] = "" + a;
                return;
            }
        }
    }
    
    public void agregaCartaOtro2(int a){
        for(int i = 0; i < cartasOtro2.length; i++){
            if(cartasOtro2[i].equals("")){
                cartasOtro2[i] = "" + a;
                return;
            }
        }
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

//    public void asignaCartaPrimero(int numeroCarta){
//        switch(numeroCliente){
//            case 2:
//                numeroCartaOtro2 = numeroCarta;
//                break;
//            case 3:
//                numeroCartaOtro1 = numeroCarta;
//                break;
//        }
//        repaint();
//    }
//    
//    public void asignaCartaSegundo(int numeroCarta){
//        switch(numeroCliente){
//            case 1:
//                numeroCartaOtro1 = numeroCarta;
//                break;
//            case 3:
//                numeroCartaOtro2 = numeroCarta;
//                break;
//        }
//        repaint();
//    }
//    
//    public void asignaCartaTercero(int numeroCarta){
//        switch(numeroCliente){
//            case 1:
//                numeroCartaOtro2 = numeroCarta;
//                break;
//            case 2:
//                numeroCartaOtro1 = numeroCarta;
//                break;
//        }
//        repaint();
//    }

    //Atributos
    private Thread hiloPrincipal;    
    private final Random rnd;           
    
//    private int numeroCarta;    
//    private int numeroCartaOtro1;
//    private int numeroCartaOtro2;
    
    private int numeroCliente;
    private int numeroClienteOtro1;
    private int numeroClienteOtro2;
    private String usuario;
    private int cantFichas;
    
    private String[] cartasCasa;
    private String[] cartasCliente;
    private String[] cartasOtro1;            
    private String[] cartasOtro2;
    
    private BufferedImage fondo;
}