package modelo;

import java.util.Observable;

public class Modelo extends Observable {

    //<editor-fold defaultstate="collapsed" desc=" Constructor">
    
    public Modelo() {
        jugadores = new ConjuntoJugador();
        mazo = new Mazo();
        croupier = new Croupier(mazo);
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Metodos">

    // Agregando un nuevo Jugador
    public void agregarJugador(Jugador nuevoJugador) {
        jugadores.agregarJugador(nuevoJugador);
        registrarCambios(nuevoJugador);
        registrarCambios("Agregando a : " + nuevoJugador.getNombreUsuario() + "...");
    }
    
    // El croupier hace la su adquisicion inicial. Osea obtiene dos cartas del mazo
    // Este metodo devuelve un vector de String donde vendran las dos rutas de las dos
    // cartas iniciales para darselas a cliente y este poder pintarlas en la vista
    public String[] adquisicionInicial(){
        String[] rutas = croupier.adiquisicionInicial();
        registrarCambios(croupier);
        registrarCambios("El croupier ha tomado sus primeras dos cartas...");
        return rutas;
    }
    
    // El croupier hace la adquisicion de una carta. 
    // PERO SOLO EN EL ESTRICTO CASO QUE LA NECESITE
    // Este metodo devuelve un String que sera la ruta de la
    // carta para darsela a cliente y este poder pintarla en la vista
    private String agregarCartaCroupier(){
        String ruta = croupier.agregarCartaCasa();
        registrarCambios(croupier);
        registrarCambios("Carta agregada al Croupier...");
        return ruta;
    }
    
    // Implementación del metodo agregarCartaCroupier() de esta clase
    // devuelve "NO" si el croupier no necesitaba agregar carta
    // para esto se debe hacer una lectura adecuada en el cliente
    // con el metodo equals(), algo asi: if(entrada.equals("NO")) entonces 
    // no hay ninguna ruta, de lo contrario si la hay y se dibujara en la vista
    // la nueva carta adquirida por el croupier
    public String crupierNecesitaCarta(){
        String ruta = "NO";
        if(croupier.necesitoCarta() == true){
            ruta = agregarCartaCroupier();
        }
        return ruta;
    }
    
    // El croupier hace la repartida inicial. Osea darle dos cartas al jugador
    // Este metodo devuelve un vector de String donde vendran las dos rutas de las dos
    // cartas iniciales para darselas a cliente y este poder pintarlas en la vista
    public String[] repartidaInicial(int pos){
        Jugador jugador = jugadores.recuperarJugador(pos);
        String[] rutas = croupier.repartidaInicial(jugador);
        registrarCambios(jugador);
        registrarCambios("Cartas iniciales reapartidas a : "+ jugador.getNombreUsuario()+"...");
        return rutas;
    }

    // Casa entrega carta al jugador, ya que éste la debio de pedir
    // Nota : se obtiene el jugador por posicion
    // Devuelve direccion de carta para darsela a cliente y este poder pintarla en la vista
    public String entregaCarta(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        String direccionCarta = croupier.darCarta(jugador);
        registrarCambios(jugador);
        registrarCambios("Carta entregada a : " + jugador.getNombreUsuario() + "...");
        return direccionCarta;
    }
    
    // Casa entrega carta al jugador, ya que éste la debio de pedir
    // Nota : se obtiene el jugador por nombre
    // Devuelve direccion de carta agregada para pintarla en la vista
    public String entregaCarta(String usuario) {
        Jugador jugador = jugadores.recuperarJugador(usuario);
        String direccionCarta = croupier.darCarta(jugador);
        registrarCambios(jugador.getCartasMano());
        registrarCambios("Carta entregada a : " + jugador.getNombreUsuario() + "...");
        return direccionCarta;
    }

    // Limpia la mano de un jugador
    public void nuevaMano(int posicion) {
        Jugador jugador = jugadores.recuperarJugador(posicion);
        croupier.nuevasManos(jugador);
        registrarCambios(jugador);
        registrarCambios("Jugador : " + jugador.getNombreUsuario() + ", listo para nueva partida...");
    }
    
    // Restaura el mazo para empezar nueva partida
    public void restaurarMazo(){
        croupier.reiniciarMazo();
        registrarCambios(mazo);
        registrarCambios("Mazo restaurado satisfactoriamente...");
    }

    // Reduce la cantidad de fichas de un usuario. Osea reduce lo que el jugador aposto
    // Metodo private porque es solamente usado en el metodo comparaManos(int)
    private void restaFichas(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        croupier.cobrarApuesta(jugador);
        registrarCambios(jugador);
        registrarCambios("El jugador : " + jugador.getNombreUsuario() + ", perdio la apuesta de : " + jugador.getApuesta() + "...");
    }

    // Aumenta la cantidad de fichas de un usuario. Osea mantiene la apuesta y recibe lo que aposto.
    // Metodo private porque es solamente usado en el metodo comparaManos(int)
    private void aumentaFichas(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        croupier.pagarApuesta(jugador);
        registrarCambios(jugador);
        registrarCambios("El jugador : " + jugador.getNombreUsuario() + ", gano la apuesta de : " + jugador.getApuesta() + "...");
    }

    // Metodo que verifica quien gana
    // No se notifica porque ya se ha notificado en los metodos que se llaman dentro de este metodo
    public void comparaManos(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        boolean quienGana = croupier.comparaManos(jugador);
        if (quienGana == true) {
            restaFichas(pos);
        }else{
            aumentaFichas(pos);
        }
    }

    // Registra cambios a Observadores
    public void registrarCambios(Object evento) {
        setChanged();
        notifyObservers(evento);
    }
    // </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" Atributos">
    
    private ConjuntoJugador jugadores;
    private Mazo mazo;
    private Croupier croupier;
    // </editor-fold>
}
