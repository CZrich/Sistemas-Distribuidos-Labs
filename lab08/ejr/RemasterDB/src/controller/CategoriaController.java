package controller;

import model.Categoria;
import util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {
    private Connection con;

    public CategoriaController() {
        con = ConexionBD.conectar();
    }

    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Categorias")) {
            while (rs.next()) {
                Categoria cat = new Categoria(
                        rs.getInt("IDCategoria"),
                        rs.getString("Nombre"),
                        rs.getString("Descripcion"));
                lista.add(cat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Categoria cat) {
        String sql = "INSERT INTO Categorias (Nombre, Descripcion) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Categoria cat) {
        String sql = "UPDATE Categorias SET Nombre = ?, Descripcion = ? WHERE IDCategoria = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cat.getNombre());
            ps.setString(2, cat.getDescripcion());
            ps.setInt(3, cat.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Categorias WHERE IDCategoria = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
