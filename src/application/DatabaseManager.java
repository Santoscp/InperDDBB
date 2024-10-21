package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/inper";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static List<Producto> getAllProduct() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT nombre FROM producto";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("nombre");
                productos.add(new Producto(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    // Método para eliminar productos por nombre
    public static int deleteProductsByName(String nombre) {
        String sql = "DELETE FROM producto WHERE nombre LIKE ?"; // Usa LIKE para permitir coincidencias

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nombre + "%"); // Añade comodines para permitir coincidencias parciales
            return pstmt.executeUpdate(); // Retorna el número de filas eliminadas
        } catch (SQLException e) {
            e.printStackTrace();
            return 0; // Retorna 0 en caso de error
        }
    }
    
    public static List<Producto> searchProductsByName(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT nombre FROM producto WHERE nombre LIKE ?"; // Usar LIKE para buscar coincidencias

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + nombre + "%"); // Usar comodines para coincidencias parciales
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("nombre");
                productos.add(new Producto(name)); // Agregar a la lista de productos
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }


}
