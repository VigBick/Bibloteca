import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.az.daos.PrestamoDAO;
import com.az.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PrestamoDAOTest {
    private PrestamoDAO prestamoDAO;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        prestamoDAO = new PrestamoDAO();
        connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Evitar cambios reales en la base de datos
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback(); // Revertir cambios
        connection.close();
    }

    @Test
    public void testRegistrarPrestamo() throws SQLException {
        int libroID = 1; // Asegúrate de que el ID existe
        int miembroID = 1; // Asegúrate de que el ID existe

        prestamoDAO.registrarPrestamo(libroID, miembroID);

        // Verificar si el préstamo fue registrado correctamente
        String query = "SELECT * FROM TBL_PRESTAMOS WHERE libro_id = ? AND miembro_id = ? AND fecha_devolucion IS NULL";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, libroID);
            statement.setInt(2, miembroID);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next()); // El préstamo debe existir
            assertEquals(libroID, resultSet.getInt("libro_id"));
            assertEquals(miembroID, resultSet.getInt("miembro_id"));
            assertNull(resultSet.getDate("fecha_devolucion")); // Asegurarse de que la fecha de devolución es nula (no devuelto)
        }
    }

    @Test
    public void testRegistrarDevolucion() throws SQLException {
        int prestamoID = 1; // Asegúrate de que el ID existe

        prestamoDAO.registrarDevolucion(prestamoID);

        // Verificar si la devolución fue registrada correctamente
        String query = "SELECT * FROM TBL_PRESTAMOS WHERE id = ? AND fecha_devolucion IS NOT NULL";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, prestamoID);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next()); // El préstamo debe existir
            assertNotNull(resultSet.getDate("fecha_devolucion")); // Asegurarse de que la fecha de devolución no es nula
        }
    }

}
