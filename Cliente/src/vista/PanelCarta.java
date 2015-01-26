package vista;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelCarta extends JPanel implements Runnable {

    public PanelCarta(int numeroCliente) {
        ajustarComponentes();
        this.numeroCliente = numeroCliente;
        switch (numeroCliente) {
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

        for (int i = 0; i < cartasCasa.length; i++) {
            cartasCasa[i] = "";
        }

        for (int i = 0; i < cartasCliente.length; i++) {
            cartasCliente[i] = "";
        }

        for (int i = 0; i < cartasOtro1.length; i++) {
            cartasOtro1[i] = "";
        }

        for (int i = 0; i < cartasOtro2.length; i++) {
            cartasOtro2[i] = "";
        }

        //
        cartasCasa[0] = "?";
        cartasCasa[1] = "?";

        cartasCliente[0] = "" + 1;
        cartasCliente[1] = "" + 2;
        cartasCliente[2] = "" + 3;
        cartasCliente[3] = "" + 4;
        cartasCliente[4] = "" + 5;
        cartasCliente[5] = "" + 6;

        cartasOtro1[0] = "" + 1;
        cartasOtro1[1] = "" + 2;
        cartasOtro1[2] = "" + 3;
        cartasOtro1[3] = "" + 4;
        cartasOtro1[4] = "" + 5;
        cartasOtro1[5] = "" + 6;

        cartasOtro2[0] = "" + 1;
        cartasOtro2[1] = "" + 2;
        cartasOtro2[2] = "" + 3;
        cartasOtro2[3] = "" + 4;
//        cartasOtro2[4] = 5;
//        cartasOtro2[5] = 6;

        //        
        rnd = new Random();
        usuario = "Prueba";

//        try {            
//            fondo = ImageIO.read(getClass().getResourceAsStream("/pruebas/imagenes/2.png"));
//        } catch (IOException ex) {
//            System.out.println("No se pudo cargar el fondo...");
//        }               
    }

    private void ajustarComponentes() {
        //init Imagenes
        arrayClubs = new BufferedImage[13];
        arrayHearts = new BufferedImage[13];
        arrayDiamonds = new BufferedImage[13];
        arraySpades = new BufferedImage[13];
        listArray = new LinkedList<>();

        //ajustamos List
        listArray.add(arrayClubs);
        listArray.add(arrayHearts);
        listArray.add(arrayDiamonds);
        listArray.add(arraySpades);
        try {
            fondo = ImageIO.read(getClass().getResourceAsStream("/img/Green-Background-27-1024x640.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(PanelCarta.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            imgBack = ImageIO.read(getClass().getResourceAsStream("/img/Actions/playing-card-back.png"));

            arrayClubs[0] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/2_of_clubs.png"));
            arrayClubs[1] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/3_of_clubs.png"));
            arrayClubs[2] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/4_of_clubs.png"));
            arrayClubs[3] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/5_of_clubs.png"));
            arrayClubs[4] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/6_of_clubs.png"));
            arrayClubs[5] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/7_of_clubs.png"));
            arrayClubs[6] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/8_of_clubs.png"));
            arrayClubs[7] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/9_of_clubs.png"));
            arrayClubs[8] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/10_of_clubs.png"));
            arrayClubs[9] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/jack_of_clubs.png"));
            arrayClubs[10] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/queen_of_clubs.png"));
            arrayClubs[11] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/king_of_clubs.png"));
            arrayClubs[12] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/ace_of_clubs.png"));

            arrayHearts[0] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/2_of_hearts.png"));
            arrayHearts[1] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/3_of_hearts.png"));
            arrayHearts[2] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/4_of_hearts.png"));
            arrayHearts[3] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/5_of_hearts.png"));
            arrayHearts[4] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/6_of_hearts.png"));
            arrayHearts[5] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/7_of_hearts.png"));
            arrayHearts[6] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/8_of_hearts.png"));
            arrayHearts[7] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/9_of_hearts.png"));
            arrayHearts[8] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/10_of_hearts.png"));
            arrayHearts[9] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/jack_of_hearts.png"));
            arrayHearts[10] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/queen_of_hearts.png"));
            arrayHearts[11] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/king_of_hearts.png"));
            arrayHearts[12] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/ace_of_hearts.png"));

            arrayDiamonds[0] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/2_of_diamonds.png"));
            arrayDiamonds[1] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/3_of_diamonds.png"));
            arrayDiamonds[2] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/4_of_diamonds.png"));
            arrayDiamonds[3] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/5_of_diamonds.png"));
            arrayDiamonds[4] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/6_of_diamonds.png"));
            arrayDiamonds[5] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/7_of_diamonds.png"));
            arrayDiamonds[6] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/8_of_diamonds.png"));
            arrayDiamonds[7] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/9_of_diamonds.png"));
            arrayDiamonds[8] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/10_of_diamonds.png"));
            arrayDiamonds[9] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/jack_of_diamonds.png"));
            arrayDiamonds[10] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/queen_of_diamonds.png"));
            arrayDiamonds[11] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/king_of_diamonds.png"));
            arrayDiamonds[12] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/ace_of_diamonds.png"));

            arraySpades[0] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/2_of_spades.png"));
            arraySpades[1] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/3_of_spades.png"));
            arraySpades[2] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/4_of_spades.png"));
            arraySpades[3] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/5_of_spades.png"));
            arraySpades[4] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/6_of_spades.png"));
            arraySpades[5] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/7_of_spades.png"));
            arraySpades[6] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/8_of_spades.png"));
            arraySpades[7] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/9_of_spades.png"));
            arraySpades[8] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/10_of_spades.png"));
            arraySpades[9] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/jack_of_spades.png"));
            arraySpades[10] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/queen_of_spades.png"));
            arraySpades[11] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/king_of_spades.png"));
            arraySpades[12] = ImageIO.read(getClass().getResourceAsStream("/img/Actions/ace_of_spades.png"));
        } catch (IOException ex) {
            Logger.getLogger(PanelCarta.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setLayout(new BorderLayout());

    }

    @Override
    public void run() {

    }

    public void iniciar() {
        hiloPrincipal = new Thread(this);
        if (hiloPrincipal != null) {
            hiloPrincipal.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F);
        g2d.setComposite(alpha);

        g2d.drawImage(fondo, 0, 0, this.getWidth(), this.getHeight(), null);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //Dibuja "tableros" para todos
        g.setColor(new Color(151, 0, 0));
        g.drawRoundRect(190, 10, 370, 170, 5, 5);
        g.drawRoundRect(190, 510, 370, 170, 5, 5);
        g.drawRoundRect(30, 160, 120, 410, 5, 5);
        g.drawRoundRect(590, 160, 120, 410, 5, 5);

        //Dibuja las letras (rótulos)
        g.setFont(new Font(Font.SERIF, Font.ITALIC, 15));
        g.setColor(Color.BLACK);
        String cliente = "Cartas de '" + usuario + "'";
        String fichas = "Cantidad de Fichas: " + cantFichas;
        String otro1 = "Cartas del cliente #" + numeroClienteOtro1;
        String otro2 = "Cartas del cliente #" + numeroClienteOtro2;

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
        for (int i = 0; i < cartasCasa.length; i++) {
            int posX = 200 + (i * 50);
            g.drawImage(imgBack, posX, 20, this);
        }

        //Cartas Cliente    
        for (int i = 0; i < cartasCliente.length; i++) {
            int posX = 200 + (i * 50);
            g.drawImage(arrayClubs[i], posX, 520, this);
        }

        //Cartas Otro1
        g.setFont(new Font(Font.SERIF, Font.BOLD, 60));
        for (int i = 0; i < cartasOtro1.length; i++) {
            int posY = 172 + (i * 48);
            g.drawImage(imgBack, 38, posY, this);
        }

        //Cartas Otro2
        for (int i = 0; i < cartasOtro2.length; i++) {
            int posY = 172 + (i * 48);
            g.drawImage(imgBack, 600, posY, this);
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
    public void reiniciar() {
        for (int i = 0; i < cartasCasa.length; i++) {
            cartasCasa[i] = "";
        }

        for (int i = 0; i < cartasCliente.length; i++) {
            cartasCliente[i] = "";
        }

        for (int i = 0; i < cartasOtro1.length; i++) {
            cartasOtro1[i] = "";
        }

        for (int i = 0; i < cartasOtro2.length; i++) {
            cartasOtro2[i] = "";
        }

        repaint();
    }

    public void agregaCartaCasa(int a) {
        for (int i = 0; i < cartasCasa.length; i++) {
            if (cartasCasa[i].equals("")) {
                cartasCasa[i] = "" + a;
                return;
            }
        }
    }

    public void agregaCartaCliente(int a) {
        for (int i = 0; i < cartasCliente.length; i++) {
            if (cartasCliente[i].equals("")) {
                cartasCliente[i] = "" + a;
                return;
            }
        }
    }

    public void agregaCartaOtro1(int a) {
        for (int i = 0; i < cartasOtro1.length; i++) {
            if (cartasOtro1[i].equals("")) {
                cartasOtro1[i] = "" + a;
                return;
            }
        }
    }

    public void agregaCartaOtro2(int a) {
        for (int i = 0; i < cartasOtro2.length; i++) {
            if (cartasOtro2[i].equals("")) {
                cartasOtro2[i] = "" + a;
                return;
            }
        }
    }

    public void asignaUsuario(String usuario) {
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

    private List<BufferedImage[]> listArray;
    private BufferedImage[] arrayClubs;
    private BufferedImage[] arrayHearts;
    private BufferedImage[] arrayDiamonds;
    private BufferedImage[] arraySpades;
    private Image imgBack;

    private BufferedImage fondo;
}
