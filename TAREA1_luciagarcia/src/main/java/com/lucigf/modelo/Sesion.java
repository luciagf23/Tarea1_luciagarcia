package com.lucigf.modelo;

/**
 * @author Lucía García Fernández
 * @version 1.0
 * @since 2025
 */

public class Sesion {

	private Long id;
	private String nombreUsuario;
	private String password;
	private Perfil perfilActual;

	public Sesion(String nombreUsuario, Perfil perfilActual) {
		super();
		this.id = id;
		this.nombreUsuario = nombreUsuario;
		this.password = password;
		this.perfilActual = perfilActual;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Perfil getPerfilActual() {
		return perfilActual;
	}

	public void setPerfilActual(Perfil perfilActual) {
		this.perfilActual = perfilActual;
	}

	@Override
	public String toString() {
		return "Sesion [id=" + id + ", nombreUsuario=" + nombreUsuario + ", password=" + password + ", perfilActual="
				+ perfilActual + "]";
	}

}
