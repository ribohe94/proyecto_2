package vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class VentanaCliente extends JFrame {

    public VentanaCliente(int numCliente) {
        this.numCliente = numCliente;
        ajustarConfiguracionInicial();
        ajustarComponentes(getContentPane());
        ajustarEventos();
    }

    private void ajustarConfiguracionInicial() {
        setTitle("Jugador");
        setSize(900, 900);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }

    private void ajustarComponentes(Container c) {
        cantFichas = 1000;
        cantFichasApostadas = 0;
        //inicializamos paneles
        panelCarta = new PanelCarta(numCliente);
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //Botones
        btnPedirCarta = new JButton("Pedir Carta");
        btnPedirCarta.setEnabled(false);
        btnQuedarse = new JButton("Quedarse");
        btnQuedarse.setEnabled(false);
        btnApostar = new JButton("Apostar");
        btnApostar.setEnabled(true);
        btnSubirApuesta = new JButton(new ImageIcon("src\\img\\add.png"));
        btnBajarApuesta = new JButton(new ImageIcon("src\\img\\sub.png"));

        //TextArea
        txtaApuestas = new JTextArea("0");
        
        //Labels
        lbCantFichas = new JLabel("Fichas: " + cantFichas);
        lbFichasApostadas = new JLabel("Fichas Apostadas: " + cantFichasApostadas);
        
        //ajustamos TXTA
        txtaApuestas.setEditable(false);
        txtaApuestas.setFont(new Font("Arial", Font.BOLD, 13));
        //Ajustamos botones
        btnSubirApuesta.setPreferredSize(new Dimension(20, 20));
        btnSubirApuesta.setBorder(null);
        btnSubirApuesta.setContentAreaFilled(false);

        btnBajarApuesta.setPreferredSize(new Dimension(20, 20));
        btnBajarApuesta.setBorder(null);
        btnBajarApuesta.setContentAreaFilled(false);

        //Ajustamos botones
        panelBotones.add(btnPedirCarta);
        panelBotones.add(btnQuedarse);
        panelBotones.add(btnApostar);
        panelBotones.add(btnBajarApuesta);
        panelBotones.add(txtaApuestas);
        panelBotones.add(btnSubirApuesta);
        panelBotones.add(lbFichasApostadas);
        panelBotones.add(lbCantFichas);

        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        c.add(panelCarta, BorderLayout.CENTER);
        c.add(panelBotones, BorderLayout.SOUTH);
    }

    private void ajustarEventos() {

        btnBajarApuesta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(txtaApuestas.getText()) != 0) {
                    txtaApuestas.setText(String.valueOf(Integer.parseInt(txtaApuestas.getText()) - 10));
                }
            }
        });

        btnSubirApuesta.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(Integer.parseInt(txtaApuestas.getText()) < cantFichas)
                txtaApuestas.setText(String.valueOf(Integer.parseInt(txtaApuestas.getText()) + 10));
            }
        });

        btnPedirCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cliente.escribirMensajeServidor(panelCarta.lanzarCarta());
                btnPedirCarta.setEnabled(false);
            }
        });

        btnQuedarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //cliente.escribirMensajeServidor("quedarse");
                btnQuedarse.setEnabled(false);
                btnPedirCarta.setEnabled(false);
            }
        });

        btnApostar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                lbCantFichas.setText("Fichas: " + (cantFichas - (Integer.parseInt(txtaApuestas.getText()))));
                cantFichas = cantFichas - (Integer.parseInt(txtaApuestas.getText()));
                lbFichasApostadas.setText("Fichas Apostadas: " + (cantFichasApostadas + (Integer.parseInt(txtaApuestas.getText()))));
                cantFichasApostadas = cantFichasApostadas + (Integer.parseInt(txtaApuestas.getText()));
                txtaApuestas.setText("0");
                
//                int fichas;
//                String str = JOptionPane.showInputDialog(null, "Ingrese la cantidad de fichas que desea apostar.");
//                try {
//                    fichas = Integer.parseInt(str);
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(null, "Error al ingresar la cantidad de fichas, inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
//                if (fichas > cantFichas) {
//                    JOptionPane.showMessageDialog(null, "Usted no dispone de tantas fichas, inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//
////                cliente.setCantFichas(cantFichas - fichas);
////                cliente.escribirMensajeServidor("rf" + fichas); //rf = Resta Fichas
//                btnApostar.setEnabled(false);
            }
        });
    }

    public void habilitarLanzar() {
        btnPedirCarta.setEnabled(true);
        repaint();
    }

    public void iniciar() {
        setVisible(true);
    }

//    public int getNumeroCarta(){
//        return panelCarta.getNumeroCarta();
//    }
    public int getCantFichas() {
        return cantFichas;
    }

    public void setCantFichas(int cantFichas) {
        this.cantFichas = cantFichas;
        panelCarta.setCantFichas(cantFichas);
    }

    public void reiniciar() {
        panelCarta.reiniciar();
        cantFichas = 1000;
    }

    public void asignaUsuario(String usuario) {
        panelCarta.asignaUsuario(usuario);
    }

//    public void asignaCartaPrimero(int numeroCarta){
//        panelCarta.asignaCartaPrimero(numeroCarta);
//    }
//    
//    public void asignaCartaSegundo(int numeroCarta){
//        panelCarta.asignaCartaSegundo(numeroCarta);
//    }
//    
//    public void asignaCartaTercero(int numeroCarta){
//        panelCarta.asignaCartaTercero(numeroCarta);
//    }
    //Atributos
    /*Panel*/
    private JPanel panelBotones;
    /*Botones*/
    private JButton btnPedirCarta;
    private JButton btnQuedarse;
    private JButton btnApostar;
    private JButton btnSubirApuesta;
    private JButton btnBajarApuesta;
    /*JTextArea*/
    private JTextArea txtaApuestas;
    /*Labels*/
    private JLabel lbCantFichas;
    private JLabel lbFichasApostadas;
    /*CMB*/
    private JComboBox cmbApostar;

    private PanelCarta panelCarta;
    private int numCliente;
    private int cantFichas;
    private int cantFichasApostadas;
}
