package vista;

import cliente.Cliente;
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
import javax.swing.JPanel;
import javax.swing.JTextArea;
import modelo.Carta;

public class VentanaCliente extends JFrame {

    public VentanaCliente(Cliente cliente) {
        this.cliente = cliente;
        ajustarConfiguracionInicial();
        ajustarComponentes(getContentPane());
        ajustarEventos();
    }

    private void ajustarConfiguracionInicial() {
        setTitle("Jugador");
        setSize(700, 670);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);        
    }

    private void ajustarComponentes(Container c) {
        cantFichas = 1000;
        cantFichasApostadas = 0;
        //inicializamos paneles
        panelCarta = new PanelCarta();
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
                cliente.escribirMensajeServidor("carta");
                btnPedirCarta.setEnabled(false);
            }
        });

        btnQuedarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.escribirMensajeServidor("quedarse");
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

    public int getCantFichas() {
        return cantFichas;
    }

    public void asignaCantFichas(int cantFichas) {
        this.cantFichas = cantFichas;
        panelCarta.setCantFichas(cantFichas);
    }
    
    public void agregarCartaUsuario(Carta carta){
        panelCarta.agregarCartaUsuario(carta.getTipo(), carta.getValor());
    }
    
    public void agregaCartaCroupier(int cant){
        setCantCartasCroupier(getCantCartasCroupier() + cant);
    }
    
    public void setCantCartasCroupier(int cant){
        panelCarta.setCantCartasCroupier(cant);
    }
    
    public int getCantCartasCroupier(){
        return panelCarta.getCantCartasCroupier();
    }

    public void reiniciar() {
        panelCarta.reiniciar();        
    }

    public void asignaUsuario(String usuario) {
        panelCarta.asignaUsuario(usuario);
    }

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

    private Cliente cliente;
    private PanelCarta panelCarta;
    private int cantFichas;
    private int cantFichasApostadas;
}
