package application;

public class Producto {
String nombre;
int id;
String tipo;
String marca;

public Producto(String nombre) {
	super();
	this.nombre = nombre;
}



public String getNombre() {
	return nombre;
}
public Producto(String nombre, int id, String tipo, String marca) {
	super();
	this.nombre = nombre;
	this.id = id;
	this.tipo = tipo;
	this.marca = marca;
}



public void setNombre(String nombre) {
	this.nombre = nombre;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getTipo() {
	return tipo;
}
public void setTipo(String tipo) {
	this.tipo = tipo;
}

public String getMarca() {
	return marca;
}
public void setMarca(String marca) {
	this.marca = marca;
}
@Override
public String toString() {
    return nombre;
}
}