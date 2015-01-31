/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Bove
 */
public class GestorJugador {

    public GestorJugador() {
    }

    public Jugador recuperar() {
        Jugador resultado = null;
        return resultado;
    }

    public void guardar(Jugador nuevoJugador) throws Exception {
        System.out.println("Nuevo jugador " + nuevoJugador);
        Connection cnx = GestorBD.obtenerInstancia().obtenerConexion();
        PreparedStatement stm = cnx.prepareStatement(COMANDO_INSERTAR);
        stm.clearParameters();
        stm.setString(1, nuevoJugador.getId());
        stm.setString(2, nuevoJugador.getNombreUsuario());
        stm.setString(3, nuevoJugador.getPass());
        if (stm.executeUpdate() != 1) {
            throw new Exception();
        }
        GestorBD.obtenerInstancia().cerrarConexion();
    }

    public void actualizar(Jugador actual, Jugador nuevo) throws Exception {
        Connection cnx = GestorBD.obtenerInstancia().obtenerConexion();

        PreparedStatement stm = cnx.prepareStatement(COMANDO_ACTUALIZAR);

        stm.setString(1, nuevo.getNombreUsuario());
        stm.setString(2, nuevo.getPass());

        if (stm.executeUpdate() != 1) {
            throw new Exception();
        }

        GestorBD.obtenerInstancia().cerrarConexion();
    }

    public void eliminar(String nombre) throws Exception {
        Connection cnx = GestorBD.obtenerInstancia().obtenerConexion();

        PreparedStatement stm = cnx.prepareStatement(COMANDO_ELIMINAR);
        stm.clearParameters();
        stm.setString(1, nombre);
        if (stm.executeUpdate() != 1) {
            throw new Exception();
        }

        GestorBD.obtenerInstancia().cerrarConexion();
    }

    public void consultar() throws Exception {
        Connection cnx = GestorBD.obtenerInstancia().obtenerConexion();
        Statement stm = cnx.createStatement();
        ResultSet rs = stm.executeQuery(COMANDO_CONSULTAR);
        while (rs.next()) {
            String jugador = rs.getString("nombre");
            String pw = rs.getString("contrasena");
            System.out.println(String.format("%s\t%s", jugador, pw));
        }
        GestorBD.obtenerInstancia().cerrarConexion();
    }

    //Atributos
    // Comandos SQL
    private static final String COMANDO_INSERTAR
            = "INSERT INTO personas "
            + "(id, nombre, contrasena) "
            + "VALUES(?, ?, ?); ";
    private static final String COMANDO_ELIMINAR
            = "DELETE FROM personas "
            + "WHERE id=?; ";

    private static final String COMANDO_CONSULTAR
            = "SELECT * FROM personas; ";

    private static final String COMANDO_ACTUALIZAR
            = "UPDATE personas SET nombre=?, apellido=? WHERE id=?; ";
}
