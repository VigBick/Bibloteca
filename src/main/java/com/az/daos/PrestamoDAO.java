package com.az.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.az.db.DatabaseConnection;


public class PrestamoDAO {
	public void registrarPrestamo(int libroID, int miembroID) throws SQLException {
	    String sqlPrestamo = "INSERT INTO TBL_PRESTAMOS(fechaINI, libroID, miembroID) VALUES (?, ?, ?)";
	    String sqlIncrementarLibrosPrestados = "UPDATE TBL_LIBROS SET prestados = prestados + 1 WHERE id = ?";
	    String sqlVerificarLibro = "SELECT COUNT(*) FROM TBL_LIBROS WHERE id = ?";
	    String sqlVerificarMiembro = "SELECT COUNT(*) FROM TBL_MIEMBROS WHERE id = ?";

	    try (Connection connection = DatabaseConnection.getInstance().getConnection();
	         PreparedStatement statementVerificarLibro = connection.prepareStatement(sqlVerificarLibro);
	         PreparedStatement statementVerificarMiembro = connection.prepareStatement(sqlVerificarMiembro);
	         PreparedStatement statementPrestamo = connection.prepareStatement(sqlPrestamo);
	         PreparedStatement statementIncrementarLibrosPrestados = connection.prepareStatement(sqlIncrementarLibrosPrestados)) {

	        connection.setAutoCommit(false);

	        // Verificar existencia del libro
	        statementVerificarLibro.setInt(1, libroID);
	        ResultSet rsLibro = statementVerificarLibro.executeQuery();
	        if (rsLibro.next() && rsLibro.getInt(1) == 0) {
	            throw new SQLException("Libro ID no encontrado: " + libroID);
	        }

	        // Verificar existencia del miembro
	        statementVerificarMiembro.setInt(1, miembroID);
	        ResultSet rsMiembro = statementVerificarMiembro.executeQuery();
	        if (rsMiembro.next() && rsMiembro.getInt(1) == 0) {
	            throw new SQLException("Miembro ID no encontrado: " + miembroID);
	        }

	        // Registrar el préstamo
	        Date fechaActual = new Date(System.currentTimeMillis());
	        statementPrestamo.setDate(1, fechaActual);
	        statementPrestamo.setInt(2, libroID);
	        statementPrestamo.setInt(3, miembroID);
	        statementPrestamo.executeUpdate();

	        // Incrementar libros prestados
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
        String sqlDecrementarLibrosPrestados = "UPDATE TBL_LIBROS SET Prestados = Prestados - 1 WHERE id = (SELECT libroID FROM TBL_PRESTAMOS WHERE id = ?)";
        String sqlVerificarPrestamo = "SELECT COUNT(*) FROM TBL_PRESTAMOS WHERE id = ?";
        
        
        try (Connection connection = DatabaseConnection.getInstance().getConnection();
        	 PreparedStatement statementVerificarPrestamo = connection.prepareStatement(sqlVerificarPrestamo);
             PreparedStatement statementDevolucion = connection.prepareStatement(sqlDevolucion);
             PreparedStatement statementDecrementarLibrosPrestados = connection.prepareStatement(sqlDecrementarLibrosPrestados)) {
        	
        	statementVerificarPrestamo.setInt(1, prestamoID);
            ResultSet rsPrestamo = statementVerificarPrestamo.executeQuery();
            if (rsPrestamo.next() && rsPrestamo.getInt(1) == 0) {
                throw new SQLException("Prestamo no encontrado: " + prestamoID);
            }

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

	        // Verificar si hay resultados antes de procesar el ResultSet
	        if (!resultSet.next()) {
	            // No se encontraron préstamos para el miembro especificado
	            historial.add("No se encontraron préstamos para el miembro con ID: " + miembroID);
	        } else {
	            // Procesar los resultados del ResultSet
	            do {
	                int prestamoID = resultSet.getInt("id");
	                String titulo = resultSet.getString("titulo");
	                Date fechaINI = resultSet.getDate("fechaINI");
	                Date fechaFIN = resultSet.getDate("fechaFIN");
	                historial.add("Prestamo ID: " + prestamoID + ", Libro: " + titulo + ", Fecha Inicio: " + fechaINI + ", Fecha Fin: " + (fechaFIN != null ? fechaFIN : "No devuelto"));
	            } while (resultSet.next());
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }

	    return historial;
	}

    public List<String> historialLibro(int libroID) throws SQLException {
        List<String> historial = new ArrayList<>();
        String sql = "SELECT l.titulo, l.autor, l.isbn, p.fechaINI, p.fechaFIN, m.nombre " +
                     "FROM TBL_LIBROS l " +
                     "JOIN TBL_PRESTAMOS p ON l.id = p.libroID " +
                     "JOIN TBL_MIEMBROS m ON p.miembroID = m.id " +
                     "WHERE l.id = ?";

        try (Connection connection = DatabaseConnection.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, libroID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String titulo = resultSet.getString("titulo");
                    String autor = resultSet.getString("autor");
                    int isbn = resultSet.getInt("isbn");
                    String fechaINI = resultSet.getDate("fechaINI").toString();
                    String fechaFIN = resultSet.getDate("fechaFIN") != null ? resultSet.getDate("fechaFIN").toString() : "No devuelto";
                    String nombreMiembro = resultSet.getString("nombre");

                    historial.add("Título: " + titulo + ", Autor: " + autor + ", ISBN: " + isbn +
                                  ", Fecha de Préstamo: " + fechaINI + ", Fecha de Devolución: " + fechaFIN +
                                  ", Miembro: " + nombreMiembro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return historial;
    }
}
