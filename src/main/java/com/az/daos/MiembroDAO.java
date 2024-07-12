package com.az.daos;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.az.clases.Miembro;
import com.az.db.DatabaseConnection;


public class MiembroDAO {
	public static void insertMiembro(Miembro miembro) {
        String sql = "INSERT INTO TBL_MIEMBROS(nombre, id, fecha) VALUES (?, ?, ?);";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            connection.setAutoCommit(false);
            
            statement.setString(1, miembro.getNombre());
            statement.setInt(2, miembro.getId());
            
            // Obtener la fecha y hora actual
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            statement.setTimestamp(3, currentTimestamp);
            
            statement.addBatch();
            
            int[] results = statement.executeBatch();
            connection.commit();
            
            for (int index : results) {
                if(index == PreparedStatement.EXECUTE_FAILED) {
                    throw new SQLException("Error al insertar un producto en lote");
                }
            }
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

	public void eliminarMiembro(int id) {
        String sql = "DELETE FROM TBL_MIEMBROS WHERE id = ?;";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            statement.executeUpdate();
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

	public static void actualizarMiembro(int id, String nombre) {
        String sql = "UPDATE TBL_MIEMBROS SET nombre = ?  WHERE id = ?;";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, nombre);
            statement.setInt(2, id);
            
            statement.executeUpdate();
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
	
	public static void buscarMiembro(String valor) {
        String sql = "SELECT * FROM TBL_MIEMBROS WHERE nombre = ?;";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, valor);

            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Nombre: " + resultSet.getString("nombre"));
                System.out.println("---------------------");
            }
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}

