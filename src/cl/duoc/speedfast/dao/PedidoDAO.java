package dao;

import conexion.ConexionDB;
import modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // metodo para registrar un nuevo pedido en la base de datos usando preparedstatement
    public boolean create(Pedido p) {
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDireccion());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getEstado());
            ps.executeUpdate();
            return true; // retorna verdadero si la insercion fue exitosa
        } catch (SQLException e) {
            System.err.println("Error en create Pedido: " + e.getMessage());
            return false;
        }
    }

    // metodo para obtener la lista completa de todos los pedidos registrados en el sistema
    public List<Pedido> readAll() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";
        try (Connection con = ConexionDB.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            // recorre cada resultado de la consulta y lo añade a la lista
            while (rs.next()) {
                lista.add(new Pedido(rs.getInt("id"), rs.getString("direccion"), rs.getString("tipo"), rs.getString("estado")));
            }
        } catch (SQLException e) {
            System.err.println("Error en readAll Pedido: " + e.getMessage());
        }
        return lista; // retorna la lista de pedidos
    }

    // metodo para actualizar la informacion de un pedido existente segun su id
    public boolean update(Pedido p) {
        String sql = "UPDATE pedidos SET direccion = ?, tipo = ?, estado = ? WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getDireccion());
            ps.setString(2, p.getTipo());
            ps.setString(3, p.getEstado());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en update Pedido: " + e.getMessage());
            return false;
        }
    }

    // metodo para eliminar un pedido de la base de datos por medio de su id
    public boolean delete(int id) {
        String sql = "DELETE FROM pedidos WHERE id = ?";
        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error en delete Pedido: " + e.getMessage());
            return false;
        }
    }
}