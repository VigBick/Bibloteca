package clases;

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
