package com.az.clases;

public class Miembro {
	String nombre;
	int id;
	public Miembro(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {				//validando que el nombre no
        if (nombre == null || nombre.trim().isEmpty()) {//este vacio
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nombre.length() < 2) {//Validando que el nombre tenga almenos 2 caracteres
            throw new IllegalArgumentException("El Nombre es demaciado corto.");
        }
        // Verifica que los espacios solo se permitan si hay más de una palabra
        if (nombre.trim().contains("  ") || nombre.startsWith(" ") || nombre.endsWith(" ")) {
            throw new IllegalArgumentException("El título no puede tener múltiples espacios consecutivos, ni comenzar ni terminar con espacios.");
        }
        // Verifica que solo se pueda intruducir caracteres alfabeticos y espacios
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
            throw new IllegalArgumentException("El nombre solo puede contener caracteres alfabéticos y espacios.");
        }
        this.nombre = nombre;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
        if (id <= 0) {	//validando que el ID sea positivo
            throw new IllegalArgumentException("El ID debe ser un número positivo.");
        }
        this.id = id;
    }
	
}
