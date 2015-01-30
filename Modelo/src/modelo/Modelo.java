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
        actualizar("Agregando a : " + nuevoJugador.getNombreUsuario() + "...");
    }
    
    //Devuelve un jugador en específico
    public Jugador devuelveJugador(int p){
        return jugadores.recuperarJugador(p);
    }
    
    // El croupier hace la su adquisicion inicial. Osea obtiene dos cartas del mazo
    // Este metodo devuelve un vector de Cartas donde vendran las dos 
    // cartas iniciales para darselas a cliente y este poder pintarlas en la vista
    public Carta[] repartirCroupier(){
        Carta[] cartas = croupier.adiquisicionInicial();
        actualizar(2);        //Informa a los clientes la cantidad de cartas que tomó el croupier
        actualizar("El croupier ha tomado sus primeras dos cartas...");
        return cartas;
    }
    
    // El croupier hace la adquisicion de una carta. 
    // PERO SOLO EN EL ESTRICTO CASO QUE LA NECESITE
    // Este metodo devuelve la que sera la
    // carta para darsela a cliente y este poder pintarla en la vista
    private Carta agregarCartaCroupier(){
        Carta carta = croupier.agregarCartaCasa();
        actualizar(croupier);
        actualizar("Carta agregada al Croupier...");
        return carta;
    }
    
    // Implementación del metodo agregarCartaCroupier() de esta clase
    // devuelve "NO" si el croupier no necesitaba agregar carta
    // para esto se debe hacer una lectura adecuada en el cliente
    // con el metodo equals(), algo asi: if(entrada.equals("NO")) entonces 
    // no hay ninguna carta, de lo contrario si la hay y se dibujara en la vista
    // la nueva carta adquirida por el croupier
    public String crupierNecesitaCarta(){
        if(croupier.necesitoCarta() == true){
            Carta carta = agregarCartaCroupier();
            return "SI";
        }else{
            return "NO";   
        }        
    }
    
    // El croupier hace la repartida inicial. Osea darle dos cartas al jugador
    // Este metodo devuelve un vector de Cartas donde vendran las dos
    // cartas iniciales para darselas a cliente y este poder pintarlas en la vista
    public Carta[] repartidaInicial(int pos){
        Jugador jugador = jugadores.recuperarJugador(pos);
        Carta[] cartas = croupier.repartidaInicial(jugador);
        //registrarCambios(jugador);
        actualizar("Cartas iniciales reapartidas a : "+ jugador.getNombreUsuario()+"...");
        return cartas;
    }

    // Casa entrega carta al jugador, ya que éste la debio de pedir
    // Nota : se obtiene el jugador por posicion
    // Devuelve direccion de carta para darsela a cliente y este poder pintarla en la vista
    public Carta entregaCarta(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        Carta carta = croupier.darCarta(jugador);
        //registrarCambios(jugador);
        actualizar("Carta entregada a : " + jugador.getNombreUsuario() + "...");
        return carta;
    }
    
    // Casa entrega carta al jugador, ya que éste la debio de pedir
    // Nota : se obtiene el jugador por nombre
    // Devuelve direccion de carta agregada para pintarla en la vista
    public Carta entregaCarta(String usuario) {
        Jugador jugador = jugadores.recuperarJugador(usuario);
        Carta carta = croupier.darCarta(jugador);
        actualizar(jugador.getCartasMano());
        actualizar("Carta entregada a : " + jugador.getNombreUsuario() + "...");
        return carta;
    }
    
    //Comprueba si el croupier aún puede jugar o si perdió
    public boolean croupierPerdio(){
        return croupier.sumaCartasActualCasa() > 21;        
    }

    // Limpia la mano de un jugador
    public void nuevaMano(int posicion) {
        Jugador jugador = jugadores.recuperarJugador(posicion);
        croupier.nuevasManos(jugador);
        actualizar(jugador);
        actualizar("Jugador : " + jugador.getNombreUsuario() + ", listo para nueva partida...");
    }
    
    // Restaura el mazo para empezar nueva partida
    public void restaurarMazo(){
        croupier.reiniciarMazo();
        actualizar(mazo);
        actualizar("Mazo restaurado satisfactoriamente...");
    }

    // Reduce la cantidad de fichas de un usuario. Osea reduce lo que el jugador aposto
    // Metodo private porque es solamente usado en el metodo comparaManos(int)
    private void restaFichas(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        croupier.cobrarApuesta(jugador);
        actualizar(jugador);
        actualizar("El jugador : " + jugador.getNombreUsuario() + ", perdio la apuesta de : " + jugador.getApuesta() + "...");
    }

    // Aumenta la cantidad de fichas de un usuario. Osea mantiene la apuesta y recibe lo que aposto.
    // Metodo private porque es solamente usado en el metodo comparaManos(int)
    private void aumentaFichas(int pos) {
        Jugador jugador = jugadores.recuperarJugador(pos);
        croupier.pagarApuesta(jugador);
        actualizar(jugador);
        actualizar("El jugador : " + jugador.getNombreUsuario() + ", gano la apuesta de : " + jugador.getApuesta() + "...");
    }

    // Metodo que verifica quien gana
    // No se notifica porque ya se ha notificado en los metodos que se llaman dentro de este metodo
    public void comparaManos(int pos) {
        if(croupierPerdio()){
            aumentaFichas(pos);
            return;
        }
        
        Jugador jugador = jugadores.recuperarJugador(pos);
        boolean quienGana = croupier.comparaManos(jugador);
        if (quienGana == true) {
            restaFichas(pos);
        }else{
            aumentaFichas(pos);
        }
    }

    // Registra cambios a Observadores
    public void actualizar(Object evento) {
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
