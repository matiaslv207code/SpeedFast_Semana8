package dao;

import conexion.ConexionDB;
import modelo.Repartidor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    // metodo para registrar un nuevo repartidor en la base de datos usando preparedstatement
    public boolean create(Repartidor r) {
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.executeUpdate();
            return true; // retorna verdadero si la insercion se realizo con exito
        } catch (SQLException e) {
            System.err.println("Error en create Repartidor: " + e.getMessage());
            return false;
        }
    }

    // metodo para obtener la lista completa de todos los repartidores guardados
    public List<Repartidor> readAll() {
        List<Repartidor> lista = new ArrayList<>();
        String sql = "SELECT * FROM repartidores";
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // recorre cada fila de la consulta y la agrega a la lista de repartidores
            while (rs.next()) {
                lista.add(new Repartidor(rs.getInt("id"), rs.getString("nombre")));
            }
        } catch (SQLException e) {
            System.err.println("Error en readAll Repartidor: " + e.getMessage());
        }
        return lista; // retorna la lista con los repartidores
    }

    // metodo para actualizar el nombre de un repartidor existente buscando por su id
    public boolean update(Repartidor r) {
        String sql = "UPDATE repartidores SET nombre = ? WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setInt(2, r.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en update Repartidor: " + e.getMessage());
            return false;
        }
    }

    // metodo para eliminar un repartidor de la base de datos de acuerdo a su id
    public boolean delete(int id) {
        String sql = "DELETE FROM repartidores WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en delete Repartidor: " + e.getMessage());
            return false;
        }
    }
}