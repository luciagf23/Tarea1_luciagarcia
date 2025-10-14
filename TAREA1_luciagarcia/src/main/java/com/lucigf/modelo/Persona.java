package com.lucigf.modelo;

public abstract class Persona {

	protected Long id;
	protected String nombre;
	protected String email;
	protected String nacionalidad;
	protected Credenciales credenciales;

	public Persona() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Persona(Long id, String nombre, String email, String nacionalidad, Credenciales credenciales) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.nacionalidad = nacionalidad;
		this.credenciales = credenciales;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNacionalidadg() {
		return nacionalidad;
	}

	public void setNacionalidadg(String nacionalidadg) {
		this.nacionalidad = nacionalidadg;
	}

	@Override
	public String toString() {
		return "Persona [id=" + id + ", nombre=" + nombre + ", email=" + email + ", nacionalidad=" + nacionalidad
				+ ", credenciales=" + credenciales + "]";
	}

}
