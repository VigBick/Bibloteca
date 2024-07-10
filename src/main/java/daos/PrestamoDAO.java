package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import db.DatabaseConnection;


public class PrestamoDAO {
	public void registrarPrestamo(int libroID, int miembroID) throws SQLException {
        String sqlPrestamo = "INSERT INTO TBL_PRESTAMOS(fechaINI, libroID, miembroID) VALUES (?, ?, ?)";
        String sqlIncrementarLibrosPrestados = "UPDATE TBL_LIBROS SET librosPrestados = librosPrestados + 1 WHERE id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statementPrestamo = connection.prepareStatement(sqlPrestamo);
             PreparedStatement statementIncrementarLibrosPrestados = connection.prepareStatement(sqlIncrementarLibrosPrestados)) {

            connection.setAutoCommit(false);
            Date fechaActual = new Date(System.currentTimeMillis());

            statementPrestamo.setDate(1, fechaActual);
            statementPrestamo.setInt(2, libroID);
            statementPrestamo.setInt(3, miembroID);
            statementPrestamo.executeUpdate();

            statementIncrementarLibrosPrestados.setInt(1, libroID);
            statementIncrementarLibrosPrestados.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

	public void registrarDevolucion(int prestamoID) throws SQLException {
        String sqlDevolucion = "UPDATE TBL_PRESTAMOS SET fechaFIN = ? WHERE id = ?";
        String sqlDecrementarLibrosPrestados = "UPDATE TBL_LIBROS SET librosPrestados = librosPrestados - 1 WHERE id = (SELECT libroID FROM TBL_PRESTAMOS WHERE id = ?)";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statementDevolucion = connection.prepareStatement(sqlDevolucion);
             PreparedStatement statementDecrementarLibrosPrestados = connection.prepareStatement(sqlDecrementarLibrosPrestados)) {

            connection.setAutoCommit(false);
            Date fechaActual = new Date(System.currentTimeMillis());

            statementDevolucion.setDate(1, fechaActual);
            statementDevolucion.setInt(2, prestamoID);
            statementDevolucion.executeUpdate();

            statementDecrementarLibrosPrestados.setInt(1, prestamoID);
            statementDecrementarLibrosPrestados.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<String> historialMiembros(int miembroID) throws SQLException {
        List<String> historial = new ArrayList<>();
        String sql = "SELECT p.id, l.titulo, p.fechaINI, p.fechaFIN FROM TBL_PRESTAMOS p JOIN TBL_LIBROS l ON p.libroID = l.id WHERE p.miembroID = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, miembroID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int prestamoID = resultSet.getInt("id");
                String titulo = resultSet.getString("titulo");
                Date fechaINI = resultSet.getDate("fechaINI");
                Date fechaFIN = resultSet.getDate("fechaFIN");
                historial.add("Prestamo ID: " + prestamoID + ", Libro: " + titulo + ", Fecha Inicio: " + fechaINI + ", Fecha Fin: " + (fechaFIN != null ? fechaFIN : "No devuelto"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return historial;
    }

    public List<String> disponibilidadLibros() throws SQLException {
        List<String> disponibilidad = new ArrayList<>();
        String sql = "SELECT titulo, existencias, librosPrestados, (existencias - librosPrestados) as disponibles FROM TBL_LIBROS";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                int existencias = resultSet.getInt("existencias");
                int librosPrestados = resultSet.getInt("librosPrestados");
                int disponibles = resultSet.getInt("disponibles");
                disponibilidad.add("Libro: " + titulo + ", Existencias: " + existencias + ", Prestados: " + librosPrestados + ", Disponibles: " + disponibles);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return disponibilidad;
    }
}
