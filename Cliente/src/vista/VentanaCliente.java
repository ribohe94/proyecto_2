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
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import modelo.Carta;

public class VentanaCliente extends JFrame {

    public VentanaCliente(Cliente cliente) {
        this.cliente = cliente;
        sumaCartasUsuario = 0;
        sumaCartasCroupier = 0;
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
        cantFichas = auxCantidadFichas;
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
                    setearLabelsParaBajar();
                }
            }
        });

        btnSubirApuesta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(txtaApuestas.getText()) < 1000 && cantFichas > 0) {
                    txtaApuestas.setText(String.valueOf(Integer.parseInt(txtaApuestas.getText()) + 10));
                    setearLabelsParaSubir();
                }

            }
        });

        btnPedirCarta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.escribirMensajeServidor("carta");
            }
        });

        btnQuedarse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cliente.escribirMensajeServidor("quedarse");
                deshabilitarBotones();
            }
        });

        btnApostar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lbFichasApostadas.getText().equals("Fichas Apostadas: 0")) {
                    JOptionPane.showMessageDialog(rootPane, "DEBE DE APOSTAR UNA CANTIDAD DE FICHAS MAYOR A 0" + "\n" + "DE LO CONTRARIO NO SE PUEDE EMPEZAR EL JUEGO");
                } else {

                    btnPedirCarta.setEnabled(true);
                    btnQuedarse.setEnabled(true);
                    //cliente.getJugador().setApuesta(cantFichasApostadas);
                    StringBuilder s = new StringBuilder();
                    s.append(cantFichasApostadas);
                    cliente.escribirMensajeServidor("apostar" + s.toString());
                    btnApostar.setEnabled(false);
                    btnSubirApuesta.setEnabled(false);
                    btnBajarApuesta.setEnabled(false);
                    //System.out.println("La apuesta de "+cliente.getJugador().getNombreUsuario()+" es de: "+cliente.getJugador().getApuesta());
                    //cliente.escribirMensajeServidor("apostar");
                }
            }
        });
    }

    private void setearLabelsParaSubir() {
        lbCantFichas.setText("Fichas: " + (cantFichas - 10));
        cantFichas = cantFichas - 10;
        lbFichasApostadas.setText("Fichas Apostadas: " + (cantFichasApostadas + 10));
        cantFichasApostadas = cantFichasApostadas + 10;
        //txtaApuestas.setText("0");
    }

    private void setearLabelsParaBajar() {
        lbCantFichas.setText("Fichas: " + (cantFichas + 10));
        cantFichas = cantFichas + 10;
        lbFichasApostadas.setText("Fichas Apostadas: " + (cantFichasApostadas - 10));
        cantFichasApostadas = cantFichasApostadas - 10;
        //txtaApuestas.setText("0");
    }

    private void reiniciarCamposApuesta() {
        lbFichasApostadas.setText("Fichas Apostadas: " + 0);
        cantFichasApostadas = 0;
        txtaApuestas.setText("0");
    }

    public void habilitarLanzar() {
        // En caso de que el cliente sea sacado del juego el sistema se queda esperando la respuesta de ese cliente que acaba
        // salir... ¿Cómo resolverlo?
        if (cantFichas == 0) {
            JOptionPane.showMessageDialog(rootPane, "USTED SE HA QUEDADO SIN FICHAS PARA APOSTAR" + "\n" + "EL SISTEMA LO RETIRAR DEL JUEGO");
            cliente.cerrarCliente();
            dispose();
        } else {
            cantFichas = auxCantidadFichas;
            System.out.println("Cantidad fichas actuales: " + cantFichas);
            btnApostar.setEnabled(true);
            btnSubirApuesta.setEnabled(true);
            btnBajarApuesta.setEnabled(true);
            btnQuedarse.setEnabled(false);
            btnPedirCarta.setEnabled(false);
            reiniciarCamposApuesta();
        }
        repaint();
    }

    public void deshabilitarBotones() {
        btnPedirCarta.setEnabled(false);
        btnQuedarse.setEnabled(false);
        btnApostar.setEnabled(false);
    }

    public void iniciar() {
        setVisible(true);
    }

    public int getCantFichas() {
        return cantFichas;
    }

    public int getSumaManoJugador() {
        return sumaCartasUsuario;
    }

    public int getSumaManoCroupier() {
        return sumaCartasCroupier;
    }

    public void asignaCantFichas(int cantFichas) {
        this.cantFichas = cantFichas;
        panelCarta.setCantFichas(cantFichas);
    }

    public void dibujaCartaUsuario(Carta carta) {
        sumaCartasUsuario += carta.getValor();
        panelCarta.dibujaCartaUsuario(carta.getTipo(), carta.getNumero());
    }

    public void dibujarCartasCroupier(Carta[] cartas) {
        for (Carta carta : cartas) {
            sumaCartasCroupier += carta.getValor();
        }

        panelCarta.cambiarCartasCroupier(cartas);
    }

    public void agregaCartaCroupier(int cant) {
        panelCarta.agregarCartaCroupier(cant);
    }

    public void reiniciar() {
        sumaCartasUsuario = 0;
        sumaCartasCroupier = 0;
        cantFichasApostadas = 0;
        cantFichas = cliente.getCantFichas();
        panelCarta.reiniciar();
        panelCarta.setCantFichas(cantFichas);
        btnPedirCarta.setEnabled(true);
        btnQuedarse.setEnabled(true);
        btnApostar.setEnabled(true);
    }

    public void asignaUsuario(String usuario) {
        panelCarta.asignaUsuario(usuario);
    }

    public void setAuxCantFichas(int fichas) {
        auxCantidadFichas = fichas;
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

    private final Cliente cliente;
    private PanelCarta panelCarta;
    private int cantFichas;
    private int cantFichasApostadas;
    private int sumaCartasUsuario;
    private int sumaCartasCroupier;
    private int auxCantidadFichas = 1000;
}
