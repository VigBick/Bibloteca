package clases;

public class Libro {
	private String titulo;
	private String autor;
	private int isbn;
	
	public Libro(String titulo, String autor, int isbn) {
		this.titulo = titulo;
		this.autor = autor;
		this.isbn = isbn;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío.");
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

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {				//Validando que isbn sea positivo
        if (isbn <= 0 && isbn >= 2147483647) {	
            throw new IllegalArgumentException("El ISBN debe ser un número positivo.");
        }
        if(isbn > 2147483647)					//validando que isbn este en el limite de INT
        {
        	throw new IllegalArgumentException("El ISBN es demaciado grande.");
        }
        this.isbn = isbn;
    }
}

