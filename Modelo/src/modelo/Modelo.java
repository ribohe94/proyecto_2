package modelo;

import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Modelo extends Observable {

    //<editor-fold defaultstate="collapsed" desc=" Constructor">
    public Modelo() {
        jugadores = new ConjuntoJugador();
        mazo = new Mazo();
        croupier = new Croupier(mazo);
        BDJugador = new GestorJugador();
        cargar();
    }
    // </editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Metodos">
    // Cargamos jugadores predefinidos
    public void cargar() {
        try {
            List<Jugador> listJ = BDJugador.getJugadores();
            for (int i = 0; i < listJ.size(); i++) {
                jugadores.agregarJugador(listJ.get(i));
                System.out.println(jugadores.getJugadores().get(i));
            }
        } catch (Exception ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Agregando un nuevo Jugador
    public void agregarJugador(Jugador nuevoJugador) {
        boolean flag = true;
        for (int i = 0; i < jugadores.getJugadores().size(); i++) {
            if (nuevoJugador.getNombreUsuario().equals(jugadores.getJugadores().get(i).getNombreUsuario())
                    && nuevoJugador.getPass().equals(jugadores.getJugadores().get(i).getPass())) {
                flag = false;
                System.out.println("Usuario ya registrado******************");
                JOptionPane.showMessageDialog(null, "Iniciada sesion como " + nuevoJugador.getNombreUsuario(), "Titulo", JOptionPane.INFORMATION_MESSAGE);
                actualizar("Usuario ya registrado : " + nuevoJugador.getNombreUsuario() + "...");
                break;
            }
        }
        if (flag) {
            jugadores.agregarJugador(new Jugador(nuevoJugador.getId(), nuevoJugador.getNombreUsuario(), nuevoJugador.getPass()));
            try {
                BDJugador.guardar(new Jugador(nuevoJugador.getId(), nuevoJugador.getNombreUsuario(), nuevoJugador.getPass()));
                System.out.println("GUARDA****");
                BDJugador.consultar();
                System.out.println("CONSULTA****");
            } catch (Exception ex) {
                Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            }
            actualizar("Agregando a : " + nuevoJugador.getNombreUsuario() + "...");
        }
    }

    //Regresa la cantidad de jugadores
    public int getCantJugadores() {
        return jugadores.getCantJugadores();
    }

    //Devuelve un jugador en específico
    public Jugador devuelveJugador(int p) {
        return jugadores.recuperarJugador(p);
    }

    // El croupier hace la su adquisicion inicial. Osea obtiene dos cartas del mazo
    // Este metodo devuelve un vector de Cartas donde vendran las dos 
    // cartas iniciales para darselas a cliente y este poder pintarlas en la vista
    public Carta[] repartirCroupier() {
        Carta[] cartas = croupier.adiquisicionInicial();
        actualizar(2);        //Informa a los clientes la cantidad de cartas que tomó el croupier
        actualizar("El croupier ha tomado sus primeras dos cartas...");
        return cartas;

//        Carta[] cartasV = croupier.adiquisicionInicial();
//        ArrayList<Carta> cartas = new ArrayList<>();
//        
//        actualizar(2);        //Informa a los clientes la cantidad de cartas que tomó el croupier
//        actualizar("El croupier ha tomado sus primeras dos cartas...");
//        
//        cartas.add(cartasV[0]);
//        cartas.add(cartasV[1]);
//        return cartas;
    }

    // El croupier hace la adquisicion de una carta. 
    // PERO SOLO EN EL ESTRICTO CASO QUE LA NECESITE
    // Este metodo devuelve la que sera la
    // carta para darsela a cliente y este poder pintarla en la vista
    private Carta agregarCartaCroupier() {
        Carta carta = croupier.agregarCartaCasa();
        actualizar("Carta agregada al Croupier...");
        return carta;
    }

    // Implementación del metodo agregarCartaCroupier() de esta clase
    // devuelve "NO" si el croupier no necesitaba agregar carta
    // para esto se debe hacer una lectura adecuada en el cliente
    // con el metodo equals(), algo asi: if(entrada.equals("NO")) entonces 
    // no hay ninguna carta, de lo contrario si la hay y se dibujara en la vista
    // la nueva carta adquirida por el croupier
    public String crupierNecesitaCarta() {
        if (croupier.necesitoCarta() == true) {
            Carta carta = agregarCartaCroupier();
            return "SI";
        } else {
            return "NO";
        }
    }

    // El croupier hace la repartida inicial. Osea darle dos cartas al jugador
    // Este metodo devuelve un vector de Cartas donde vendran las dos
    // cartas iniciales para darselas a cliente y este poder pintarlas en la vista
    public Carta[] repartidaInicial(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        Carta[] cartas = croupier.repartidaInicial(jugador);
        actualizar("Cartas iniciales reapartidas a : " + jugador.getNombreUsuario() + "...");
        return cartas;
    }

    // Casa entrega carta al jugador, ya que éste la debio de pedir
    // Nota : se obtiene el jugador por posicion
    // Devuelve la carta para darsela a cliente y este poder pintarla en la vista
    public Carta entregaCarta(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        Carta carta = croupier.darCarta(jugador);
        actualizar("Carta entregada a : " + jugador.getNombreUsuario() + "...");
        return carta;
    }

    //Comprueba si el croupier aún puede jugar o si perdió
    public boolean croupierPerdio() {
        return croupier.sumaCartasActualCasa() > 21;
    }

    // Limpia la mano de un jugador
    public void nuevaMano(int posicion) {
        Jugador jugador = jugadores.recuperarJugador(posicion);
        croupier.nuevasManos(jugador);
        actualizar("Jugador : " + jugador.getNombreUsuario() + ", listo para nueva partida...");
    }

    // Restaura el mazo para empezar nueva partida
    public void restaurarMazo() {
        croupier.reiniciarMazo();
        actualizar("Mazo restaurado satisfactoriamente...");
    }

    // Reduce la cantidad de fichas de un usuario. Osea reduce lo que el jugador aposto
    // Metodo private porque es solamente usado en el metodo comparaManos(int)
    private void restaFichas(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        croupier.cobrarApuesta(jugador);
        actualizar("El jugador : " + jugador.getNombreUsuario() + ", perdio la apuesta de : " + jugador.getApuesta() + "...");
    }

    // Aumenta la cantidad de fichas de un usuario. Osea mantiene la apuesta y recibe lo que aposto.
    // Metodo private porque es solamente usado en el metodo comparaManos(int)
    private void aumentaFichas(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        croupier.pagarApuesta(jugador);
        actualizar("El jugador : " + jugador.getNombreUsuario() + ", gano la apuesta de : " + jugador.getApuesta() + "...");
    }

    // Metodo que verifica quien gana
    // No se notifica porque ya se ha notificado en los metodos que se llaman dentro de este metodo
    public void comparaManos(int pos) {
        if (croupierPerdio()) {
            aumentaFichas(pos);
            return;
        }

        Jugador jugador = jugadores.recuperarJugador(pos);
        boolean quienGana = croupier.comparaManos(jugador);
        if (quienGana == true) {
            restaFichas(pos);
        } else {
            aumentaFichas(pos);
        }
    }

    public void muestraCartasCroupier() {
        actualizar(croupier.getMano());
    }

    public void reiniciar() {
        for (int i = 0; i < jugadores.getCantJugadores(); i++) {
            croupier.nuevasManos(jugadores.recuperarJugador(i));
            croupier.limpiaMano();
            croupier.reiniciarMazo();
            jugadores.recuperarJugador(i).setListo(false);
        }

        restaurarMazo();
    }

    // Registra cambios a Observadores
    public void actualizar(Object evento) {
        setChanged();
        notifyObservers(evento);
    }
    // </editor-fold>

    public static Modelo obtenerInstancia() {
        if (instancia == null) {
            instancia = new Modelo();
        }
        return instancia;
    }

    public Jugador recuperar() {
        return BDJugador.recuperar();
    }

    public void guardar(Jugador nuevoJugador) throws Exception {
        BDJugador.guardar(nuevoJugador);
        setChanged();
        notifyObservers();
    }

    public void actualizar(Jugador actual, Jugador nueva) throws Exception {
        BDJugador.actualizar(actual, nueva);
        setChanged();
        notifyObservers();
    }

    public void eliminar(String idPersona) throws Exception {
        BDJugador.eliminar(idPersona);
        setChanged();
        notifyObservers();
    }

    //<editor-fold defaultstate="collapsed" desc=" Atributos">
    private ConjuntoJugador jugadores;
    private Mazo mazo;
    private Croupier croupier;
    private static Modelo instancia = null;
    private GestorJugador BDJugador;
    // </editor-fold>
}
