package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import clases.Libro;
import db.DatabaseConnection;


public class LibroDAO {
	public void insertLibro(Libro libro) {
		String sql = "INSERT INTO TBL_LIBROS(titulo, autor, isbn, existencia) VALUES (?, ?, ?, ?);";
		
		try(Connection connection = DatabaseConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			
			connection.setAutoCommit(false);
			
			statement.setString(1, libro.getTitulo());
			statement.setString(2, libro.getAutor());
			statement.setLong(3, libro.getIsbn());
			statement.setLong(4, 1);
			
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

	public static void eliminarLibro(int id) {
        String sql = "DELETE FROM TBL_LIBROS WHERE id = ?;";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setInt(1, id);
            statement.executeUpdate();
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

	public void actualizarLibro(int id, String titulo, String autor, int isbn, int existencia) {
        String sql = "UPDATE TBL_LIBROS SET titulo = ?, autor = ?, isbn = ?, existencia = ? WHERE id = ?;";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, titulo);
            statement.setString(2, autor);
            statement.setInt(3, isbn);
            statement.setInt(4, existencia);
            statement.setInt(5, id);
            
            statement.executeUpdate();
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
	
	public void buscarLibro(String criterio, String valor) {
        String sql = "SELECT * FROM TBL_LIBROS WHERE " + criterio + " = ?;";
        
        try(Connection connection = DatabaseConnection.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {
            
            if (criterio.equals("isbn")) {
                statement.setInt(1, Integer.parseInt(valor));
            } else {
                statement.setString(1, valor);
            }
            
            ResultSet resultSet = statement.executeQuery();
            
            boolean contenido = false;
            while (resultSet.next()) {
            	contenido = true;
                System.out.println("ID: " + resultSet.getInt("id"));
                System.out.println("Título: " + resultSet.getString("titulo"));
                System.out.println("Autor: " + resultSet.getString("autor"));
                System.out.println("ISBN: " + resultSet.getInt("isbn"));
                System.out.println("---------------------");
            }
            if (contenido == false) {
				System.out.println("Libro no encontrado");
			}
            
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
