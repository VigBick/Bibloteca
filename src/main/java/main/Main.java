package main;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import clases.Libro;
import clases.Miembro;
import daos.LibroDAO;
import daos.MiembroDAO;
import daos.PrestamoDAO;

public class Main {
	private static Scanner scanner = new Scanner(System.in);
	static LibroDAO libroDAO = new LibroDAO();
	static MiembroDAO miembroDAO = new MiembroDAO();
	static PrestamoDAO prestamoDAO = new PrestamoDAO();
	public static void main(String[] args) {
        boolean loopMenuInicial = true;
        while (loopMenuInicial) {
            mostrarMenu();
			int opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1:
                	MenuLibros();
                    break;
                case 2:
                    MenuMiembros();
                    break;
                case 3:
                    MenuPrestamos();
                    break;
                case 4:
                	loopMenuInicial = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
	}

	private static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1) Libros");
        System.out.println("2) Miembros");
        System.out.println("3) Prestamos");
        System.out.print("Seleccione una opción: ");
    }
	private static void MenuLibros() {
		
		boolean loop2 = true;
		while(loop2)
		{
	        System.out.println("\n--- Libros ---");
	        System.out.println("1) Nuevo Libro");
	        System.out.println("2) Eliminar libro");
	        System.out.println("3) Actualizar libro");
	        System.out.println("4) Buscar libro");
	        System.out.println("5) Menu anterior");
	        System.out.print("Seleccione una opción: ");
	        
	        int opcion2 = scanner.nextInt();
	        scanner.nextLine(); // Consumir el salto de línea
	        switch (opcion2) {
            case 1:
            	nuevoLibro();
                break;
            case 2:
            	eliminarLibro();
                break;
            case 3:
            	actualizarLibro();
                break;
            case 4:
                buscarLibro();
                break;
            case 5:
                loop2 = false;
                break;
            default:
                System.out.println("Opción no válida, intente de nuevo.");
	        }
		}
    }
	
	private static void MenuMiembros() {
		boolean loop2 = true;
		while(loop2)
		{
			System.out.println("\n--- Miembro ---");
			System.out.println("1) Nuevo Miembro");
			System.out.println("2) Eliminar Miembro");
			System.out.println("3) Actualizar Miembro");
			System.out.println("4) Buscar Miembro");
			System.out.println("5) Menu anterior");
			System.out.print("Seleccione una opción: ");
			int opcion2 = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
            switch (opcion2) {
            case 1:
            	nuevoMiembro();
                break;
            case 2:
                eliminarMiembro();
                break;
            case 3:
            	actualizarMiembro();
                break;
            case 4:
            	buscarMiembro();
                break;
            case 5:
                loop2 = false;
                break;
            default:
                System.out.println("Opción no válida, intente de nuevo.");
            }
			
		}
	}
	private static void MenuPrestamos() {
		boolean loop2 = true;
		while(loop2)
		{
			System.out.println("\n--- Prestamos ---");
			System.out.println("1) registrar Prestamo");
			System.out.println("2) registrar devolucion");
			System.out.println("3) Ver historial de miembros");
			System.out.println("4) Ver historial de libros");
			System.out.println("5) Menu anterior");
			System.out.print("Seleccione una opción: ");
			int opcion2 = scanner.nextInt();
			scanner.nextLine(); // Consumir el salto de línea
			switch (opcion2) {
			case 1:
				registrarPrestamo();
				break;
			case 2:
				registrarDevolucion();
				break;
			case 3:
				historialMiembros();
				break;
			case 4:
				disponibilidadLibros();
				break;
			case 5:
				loop2 = false;
				break;
			default:
				System.out.println("Opción no válida, intente de nuevo.");
			}
		}
	}

	private static void nuevoLibro() {
        System.out.println("titulo: ");
        String titulo = scanner.nextLine();
        System.out.println("autor: ");
        String autor = scanner.nextLine();
        System.out.println("ISBN: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();
        Libro libro = new Libro(titulo, autor, isbn);
		libroDAO.insertLibro(libro);
    }
	
	private static void actualizarLibro() {
        System.out.println("Ingrese el ID del Libro a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nuevo titulo: ");
        String titulo = scanner.nextLine();
        System.out.println("Nueva autor: ");
        String autor = scanner.nextLine();
        System.out.println("Nuevo isbn: ");
        int isbn = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Intrudusca existencia: ");
        int existencia = scanner.nextInt();
        scanner.nextLine();
        libroDAO.actualizarLibro(id, titulo, autor, isbn, existencia);
    }
	
	private static void eliminarLibro() {
        System.out.println("Ingrese el ID del Libro a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        LibroDAO.eliminarLibro(id);
    }
	
	private static void buscarLibro() {
        System.out.println("Buscar libro por:");
        System.out.println("1) Título");
        System.out.println("2) Autor");
        System.out.println("3) ISBN");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        String criterio = "";
        String valor = "";

        switch (choice) {
            case 1:
                criterio = "titulo";
                System.out.println("Ingrese el título del libro:");
                valor = scanner.nextLine();
                break;
            case 2:
                criterio = "autor";
                System.out.println("Ingrese el autor del libro:");
                valor = scanner.nextLine();
                break;
            case 3:
                criterio = "isbn";
                System.out.println("Ingrese el ISBN del libro:");
                valor = scanner.nextLine();
                break;
            default:
                System.out.println("Opción no válida, intente de nuevo.");
                return;
        }

        libroDAO.buscarLibro(criterio, valor);
    }
	
	private static void nuevoMiembro() {
        System.out.println("nombre: ");
        String nombre = scanner.nextLine();
        Miembro miembro = new Miembro(nombre);
		MiembroDAO.insertMiembro(miembro);
    }
	
	private static void actualizarMiembro() {
        System.out.println("Ingrese el ID del miembro a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Nuevo nombre: ");
        String titulo = scanner.nextLine();
        scanner.nextLine();
        MiembroDAO.actualizarMiembro(id, titulo);
    }
	
	private static void eliminarMiembro() {
        System.out.println("Ingrese el ID del miembro a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        miembroDAO.eliminarMiembro(id);
    }
	
	private static void buscarMiembro() {
		String valor = "";
		System.out.println("Ingrese el nombre del miembro:");
        valor = scanner.nextLine();
		MiembroDAO.buscarMiembro(valor);
	}
	
	public static void registrarPrestamo() {
        System.out.print("Ingrese el ID del libro: ");
        int libroID = scanner.nextInt();
        System.out.print("Ingrese el ID del miembro: ");
        int miembroID = scanner.nextInt();

        try {
            prestamoDAO.registrarPrestamo(libroID, miembroID);
            System.out.println("Préstamo registrado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar el préstamo: " + e.getMessage());
        }
    }
	
	public static void registrarDevolucion() {
        System.out.print("Ingrese el ID del préstamo: ");
        int prestamoID = scanner.nextInt();

        try {
            prestamoDAO.registrarDevolucion(prestamoID);
            System.out.println("Devolución registrada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar la devolución: " + e.getMessage());
        }
    }
	
	public static void historialMiembros() {
        System.out.print("Ingrese el ID del miembro: ");
        int miembroID = scanner.nextInt();

        try {
            List<String> historial = prestamoDAO.historialMiembros(miembroID);
            for (String registro : historial) {
                System.out.println(registro);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el historial de préstamos: " + e.getMessage());
        }
    }
	
	public static void disponibilidadLibros() {
        try {
            List<String> disponibilidad = prestamoDAO.disponibilidadLibros();
            for (String registro : disponibilidad) {
                System.out.println(registro);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la disponibilidad de los libros: " + e.getMessage());
        }
    }
}