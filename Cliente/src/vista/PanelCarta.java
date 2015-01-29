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

    public PanelCarta() {
        ajustarComponentes();        

        cartasCasa = new String[6];
        cartasCliente = new String[6];


        for (int i = 0; i < cartasCasa.length; i++) {
            cartasCasa[i] = "";
        }

        for (int i = 0; i < cartasCliente.length; i++) {
            cartasCliente[i] = "";
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
     
        rnd = new Random();
        usuario = "Prueba";
        
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
        g.drawRoundRect(155, 40, 370, 170, 5, 5);
        g.drawRoundRect(155, 410, 370, 170, 5, 5);

        //Dibuja las letras (rótulos)
        g.setFont(new Font(Font.SERIF, Font.ITALIC, 15));
        g.setColor(Color.BLACK);
        String cliente = "Cartas de '" + usuario + "'";
        String fichas = "Cantidad de Fichas: " + cantFichas;
        
        g.drawString("Cartas de la casa", 285, 30);
        g.drawString(cliente, 285, 398);

        g.setColor(Color.BLUE);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 15));
        g.drawString(fichas, 285, 610);

        //Dibuja los numeros de las cartas
        g.setColor(Color.BLACK);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 80));

        //Cartas Casa                   
        for (int i = 0; i < cartasCasa.length; i++) {
            int posX = 165 + (i * 50);
            g.drawImage(imgBack, posX, 50, this);
        }

        //Cartas Cliente    
        for (int i = 0; i < cartasCliente.length; i++) {
            int posX = 165 + (i * 50);
            g.drawImage(arrayClubs[i], posX, 420, this);
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

    //Atributos
    private Thread hiloPrincipal;
    private final Random rnd;
    private String usuario;
    private int cantFichas;

    private String[] cartasCasa;
    private String[] cartasCliente;

    private List<BufferedImage[]> listArray;
    private BufferedImage[] arrayClubs;
    private BufferedImage[] arrayHearts;
    private BufferedImage[] arrayDiamonds;
    private BufferedImage[] arraySpades;
    private Image imgBack;

    private BufferedImage fondo;
}