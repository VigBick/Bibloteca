package com.az.clases;

public class Libro {
	private String titulo;
	private String autor;
	private long isbn;
	
	public Libro(String titulo, String autor, long isbn) {
		setTitulo(titulo);
        setAutor(autor);
        setIsbn(isbn);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
        }

        // Verifica que el título contenga solo caracteres alfabéticos, números y espacios
        if (!titulo.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ0-9 ]+")) {
            throw new IllegalArgumentException("El título solo puede contener caracteres alfabéticos, números y espacios.");
        }

        // Verifica que los espacios solo se permitan si hay más de una palabra
        if (titulo.trim().contains("  ") || titulo.startsWith(" ") || titulo.endsWith(" ")) {
            throw new IllegalArgumentException("El título no puede tener múltiples espacios consecutivos, ni comenzar ni terminar con espacios.");
        }

        // Verifica que si hay números, también haya al menos una palabra
        if (titulo.matches("\\d+") || (titulo.matches(".*\\d.*") && !titulo.matches(".*[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ].*"))) {
            throw new IllegalArgumentException("El título debe contener al menos una palabra si incluye números.");
        }
        this.titulo = titulo;
    }

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {	//Validando que el valor del autor no sea nulo
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío.");
        }
        this.autor = autor;
    }

	public long getIsbn() {
		return isbn;
	}

	public void setIsbn(long isbn) {				//Validando que isbn sea positivo
        if (isbn <= 0) {	
            throw new IllegalArgumentException("El ISBN debe ser un número positivo.");
        }
        if(isbn > 922337203685477580l)					//validando que isbn este en el limite positivo de LONG
        {
        	throw new IllegalArgumentException("El ISBN es demaciado grande.");
        }
        this.isbn = isbn;
    }
}

