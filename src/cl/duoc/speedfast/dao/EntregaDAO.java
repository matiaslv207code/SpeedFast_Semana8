package dao;

import conexion.ConexionDB;
import modelo.Entrega;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    // metodo para registrar una nueva entrega en la base de datos usando preparedstatement
    public boolean create(Entrega e) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getIdPedido());
            ps.setInt(2, e.getIdRepartidor());
            ps.setString(3, e.getFecha());
            ps.setString(4, e.getHora());
            ps.executeUpdate();
            return true; // retorna verdadero si la insercion fue exitosa
        } catch (SQLException ex) {
            System.err.println("Error en create Entrega: " + ex.getMessage());
            return false;
        }
    }

    // metodo para obtener la lista completa de todas las entregas registradas
    public List<Entrega> readAll() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregas";
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // recorre cada registro obtenido de la base de datos y lo agrega a la lista
            while (rs.next()) {
                lista.add(new Entrega(
                        rs.getInt("id"),
                        rs.getInt("id_pedido"),
                        rs.getInt("id_repartidor"),
                        rs.getString("fecha"),
                        rs.getString("hora")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Error en readAll Entrega: " + ex.getMessage());
        }
        return lista; // retorna la lista de entregas
    }

    // metodo para actualizar los datos de una entrega existente segun su id
    public boolean update(Entrega e) {
        String sql = "UPDATE entregas SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getIdPedido());
            ps.setInt(2, e.getIdRepartidor());
            ps.setString(3, e.getFecha());
            ps.setString(4, e.getHora());
            ps.setInt(5, e.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error en update Entrega: " + ex.getMessage());
            return false;
        }
    }

    // metodo para eliminar un registro de entrega de la base de datos por medio de su id
    public boolean delete(int id) {
        String sql = "DELETE FROM entregas WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error en delete Entrega: " + ex.getMessage());
            return false;
        }
    }
}