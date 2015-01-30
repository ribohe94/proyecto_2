
package vista;

import cliente.Cliente;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.Jugador;

public class VentanaRegistro extends JFrame{    
    public VentanaRegistro(){
        super("Ventana Jugador"); 
        conectarse();
        ajustarConfiguracionInicial();
        ajustarComponentes(getContentPane());
        ajustarEventos();        
    }
    
    private void ajustarConfiguracionInicial(){        
        setSize(260,200);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    private void ajustarComponentes(Container c){        
        JPanel panelFormulario = new JPanel(new GridBagLayout());        
        GridBagConstraints constraints = new GridBagConstraints();
        
        lbNomUsuario = new JLabel("Usuario:"); 
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(5,2,5,10);
        panelFormulario.add(lbNomUsuario,constraints);
        
        txtNomUsuario = new JTextField(10);
        constraints.gridx = 1;        
        constraints.insets = new Insets(8,2,5,2);
        panelFormulario.add(txtNomUsuario,constraints);
        
        lbPass = new JLabel("Contraseña:"); 
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(5,2,5,10);
        panelFormulario.add(lbPass,constraints);
        
        txtPass = new JTextField(10);
        constraints.gridx = 1;
        constraints.insets = new Insets(8,2,5,2);
        panelFormulario.add(txtPass,constraints);
        
        btnRegistrar = new JButton("Registrar");
        btnRegistrar.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5,7,5,2);
        constraints.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(btnRegistrar,constraints);
        
        JPanel panelBarraEstado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBarraEstado.setBorder(BorderFactory.createBevelBorder(1));
        lbEstado = new JLabel("Ningún jugador registrado ...");
        panelBarraEstado.add(lbEstado);
        
        c.add(panelFormulario, BorderLayout.CENTER);
        c.add(panelBarraEstado, BorderLayout.PAGE_END);
    }
    
    private void ajustarEventos(){
        btnRegistrar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = txtNomUsuario.getText();
                String pass = txtPass.getText();
                                
                if(nom.equals("")){
                    JOptionPane.showMessageDialog(null, "Porfavor ingrese un nombre de usuario.");
                    return;
                }
                
                if(pass.equals("")){
                    JOptionPane.showMessageDialog(null, "Porfavor ingrese una contraseña.");
                    return;
                }
                
                Jugador j = new Jugador("4", nom, pass); // cambiar el numero de jugador
                cliente.setJugador(j);                                              
                cliente.escribirMensajeServidor(j);
                
                txtNomUsuario.setText("");
                txtPass.setText("");
                btnRegistrar.setEnabled(false);           
                mostrarMensaje("Usuario: " + nom);                     
            }            
        });
        
        this.addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               cerrarAplicacion();
           }
        });
    }
    
    public void cerrarAplicacion() {
        cliente.cerrarCliente ();
        if (JOptionPane.showConfirmDialog(this, "¿Desea cerrar la aplicación?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {           
            System.exit(0);
        }
    }
    
    public void habilitarBoton(){
        btnRegistrar.setEnabled(true);
    }
    
    public void mostrarMensaje(String m){        
        lbEstado.setText(m);        
    }
    
    public Cliente obtenerCliente(){
        return cliente;
    }    
    
    public void ocultarRegistro(){
        setVisible(false);        
    }
    
    private void conectarse(){           
           cliente = new Cliente(this);
           cliente.iniciar();
           hiloCliente= new Thread( cliente);           
           hiloCliente.start();
    }
           
    public void mostrar(){
        setVisible(true);
    }    
    
    //Atributos       
    private Thread hiloCliente;
    private Cliente cliente;       
    
    private JButton btnRegistrar;
    private JLabel lbEstado;
    private JLabel lbNomUsuario;
    private JLabel lbPass;
    private JTextField txtNomUsuario;
    private JTextField txtPass;
}
