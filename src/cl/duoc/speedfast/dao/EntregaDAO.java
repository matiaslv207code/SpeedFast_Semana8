package dao;

import conexion.ConexionDB;
import modelo.Entrega;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAO {

    public boolean create(Entrega e) {
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, e.getIdPedido());
            ps.setInt(2, e.getIdRepartidor());
            ps.setString(3, e.getFecha());
            ps.setString(4, e.getHora());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println("Error en create Entrega: " + ex.getMessage());
            return false;
        }
    }

    public List<Entrega> readAll() {
        List<Entrega> lista = new ArrayList<>();
        String sql = "SELECT * FROM entregas";
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
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
        return lista;
    }

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