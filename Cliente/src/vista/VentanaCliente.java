
package vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class VentanaCliente extends JFrame{
    public VentanaCliente(int numCliente){                
        this.numCliente = numCliente;
        
        ajustarConfiguracionInicial();
        ajustarComponentes(getContentPane());
        ajustarEventos();
    }
    
    private void ajustarConfiguracionInicial(){
        setTitle("Jugador");        
        setSize(571, 645);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    private void ajustarComponentes(Container c){
        panelCarta = new PanelCarta(numCliente);        
        
        btnPedirCarta = new JButton("Pedir Carta");
        btnPedirCarta.setEnabled(false);
        btnQuedarse = new JButton("Quedarse");
        btnQuedarse.setEnabled(false);
        btnApostar = new JButton("Apostar");
        btnApostar.setEnabled(false);        
        
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.add(btnPedirCarta);
        panelBotones.add(btnQuedarse);
        panelBotones.add(btnApostar);        
        
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        c.add(panelCarta, BorderLayout.CENTER);
        c.add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void ajustarEventos(){
        btnPedirCarta.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //cliente.escribirMensajeServidor(panelCarta.lanzarCarta());
                btnPedirCarta.setEnabled(false);
            }            
        });
        
        btnQuedarse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                //cliente.escribirMensajeServidor("quedarse");
                btnQuedarse.setEnabled(false);
                btnPedirCarta.setEnabled(false);
            }            
        });
        
        btnApostar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int fichas;
                String str = JOptionPane.showInputDialog(null, "Ingrese la cantidad de fichas que desea apostar.");
                try{
                    fichas = Integer.parseInt(str);
                }catch(NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "Error al ingresar la cantidad de fichas, inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if(fichas > cantFichas){
                    JOptionPane.showMessageDialog(null, "Usted no dispone de tantas fichas, inténtelo de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                                
//                cliente.setCantFichas(cantFichas - fichas);
//                cliente.escribirMensajeServidor("rf" + fichas); //rf = Resta Fichas
                btnApostar.setEnabled(false);                
            }            
        });
    }
    
    public void habilitarLanzar(){
        btnPedirCarta.setEnabled(true);
        repaint();
    }            
    
    public void iniciar(){
        setVisible(true);
    }
    
//    public int getNumeroCarta(){
//        return panelCarta.getNumeroCarta();
//    }
    
    public int getCantFichas(){
        return cantFichas;
    }    
    
    public void setCantFichas(int cantFichas){
        this.cantFichas = cantFichas;
        panelCarta.setCantFichas(cantFichas);        
    }
    
    public void reiniciar(){
        panelCarta.reiniciar();
        cantFichas = 1000;
    }
    
    public void asignaUsuario(String usuario){
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
    private JPanel panelBotones;
    private JButton btnPedirCarta;
    private JButton btnQuedarse;
    private JButton btnApostar;
    
    private PanelCarta panelCarta;    
    private int numCliente;
    private int cantFichas;
}
