import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import clases.Miembro;
import daos.MiembroDAO;
import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MiembroDAOTest {
    private MiembroDAO miembroDAO;
    private Connection connection;

    @Before
    public void setUp() throws SQLException {
        miembroDAO = new MiembroDAO();
        connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(false); // Evitar cambios reales en la base de datos
    }

    @After
    public void tearDown() throws SQLException {
        connection.rollback(); // Revertir cambios
        connection.close();
    }

    @Test
    public void testInsertMiembro() {
        Miembro miembro = new Miembro("Test Name");
        MiembroDAO.insertMiembro(miembro);

        // Verificar si el miembro fue insertado correctamente
        String query = "SELECT * FROM TBL_MIEMBROS WHERE nombre = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, miembro.getNombre());
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next()); // El miembro debe existir
            assertEquals("Test Name", resultSet.getString("nombre"));
        }catch(SQLException e) {
        	System.out.println(e);
        }
    }

    @Test
    public void testEliminarMiembro() {
        int id = 1; // Debes asegurarte de que el ID existe en la base de datos
        miembroDAO.eliminarMiembro(id);

     // Verificar si el miembro fue eliminado correctamente
        String query = "SELECT * FROM TBL_MIEMBROS WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            assertFalse(resultSet.next()); // El miembro no debe existir
        }catch(SQLException e) {
        	System.out.println(e);
        }
    }

    @Test
    public void testActualizarMiembro() {
        int id = 1; // Debes asegurarte de que el ID existe en la base de datos
        MiembroDAO.actualizarMiembro(id, "juan");

     // Verificar si el miembro fue actualizado correctamente
        String query = "SELECT * FROM TBL_MIEMBROS WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next()); // El miembro debe existir
            assertEquals("Updated Name", resultSet.getString("nombre"));
        }catch(SQLException e) {
        	System.out.println(e);
        }
    }

}
