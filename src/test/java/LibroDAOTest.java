import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.az.clases.Libro;
import com.az.daos.LibroDAO;
import com.az.db.DatabaseConnection;

class LibroDAOTest {
	private LibroDAO libroDAO;
    private Connection connection;
    
    @Before
    public void setUp() throws SQLException {
        libroDAO = new LibroDAO();
        connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Ednd tos
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback(); // Revertir cambios
        connection.close();
    }
    
	@Test
    public void testInsertLibro() {
        Libro libro = new Libro("Test Title", "Test Author", 123456);
        libroDAO.insertLibro(libro);

        // Verificar si el libro fue insertado correctamente
        String query = "SELECT * FROM TBL_LIBROS WHERE titulo = ? AND autor = ? AND isbn = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setInt(3, libro.getIsbn());
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next()); // El libro debe existir
            assertEquals("Test Title", resultSet.getString("titulo"));
            assertEquals("Test Author", resultSet.getString("autor"));
            assertEquals(123456, resultSet.getInt("isbn"));
        }catch(SQLException e) {
        	System.out.println(e);
        }
    }

    @Test
    public void testEliminarLibro() {
        int id = 1; // Debes asegurarte de que el ID existe en la base de datos
        LibroDAO.eliminarLibro(id);

        // Verificar si el libro fue eliminado correctamente
        String query = "SELECT * FROM TBL_LIBROS WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            assertFalse(resultSet.next()); // El libro no debe existir
        }catch(SQLException e) {
        	System.out.println(e);
        }
    }

    @Test
    public void testActualizarLibro() {
        int id = 1; // Debes asegurarte de que el ID existe en la base de datos
        libroDAO.actualizarLibro(id, "Updated Title", "Updated Author", 654321, 10);

        // Verificar si el libro fue actualizado correctamente
        String query = "SELECT * FROM TBL_LIBROS WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next()); // El libro debe existir
            assertEquals("Updated Title", resultSet.getString("titulo"));
            assertEquals("Updated Author", resultSet.getString("autor"));
            assertEquals(654321, resultSet.getInt("isbn"));
            assertEquals(10, resultSet.getInt("existencia"));
        }catch(SQLException e) {
        	System.out.println(e);
        }
    }
}