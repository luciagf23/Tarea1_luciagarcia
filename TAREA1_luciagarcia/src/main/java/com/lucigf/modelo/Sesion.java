package com.lucigf.modelo;

public class Sesion extends Credenciales {

	private Persona usuario;
	private Perfil perfil;
	private boolean activa;

	

	public Sesion(Long id, String nombre, String password, Perfil perfil, Persona usuario, boolean activa) {
		super(id, nombre, password, perfil);
		this.usuario = usuario;
		this.activa = activa;
	}



	public Sesion() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Sesion(Long id, String nombre, String password, Perfil perfil) {
		super(id, nombre, password, perfil);
		// TODO Auto-generated constructor stub
	}



	public void cerrarSesion() {
		this.activa = false;
		System.out.println("Sesión cerrada para " + usuario.getNombre());
	}

	// Menu dependiendo del perfil

	public void mostrarMenu() {
		System.out.println("\n=== MENÚ DE " + perfil + " ===");

		switch (perfil) {
		case ADMIN:
			mostrarMenuAdmin();
			break;
		case COORDINACION:
			mostrarMenuCoordinacion();
			break;
		case ARTISTA:
			mostrarMenuArtista();
			break;
		default:
			mostrarMenuInvitado();
		}
	}

	private void mostrarMenuAdmin() {
		System.out.println("1. Registrar nueva persona");
		System.out.println("2. Gestionar credenciales");
		System.out.println("3. Gestionar espectáculos");
		System.out.println("4. Ver todos los datos del circo");
		System.out.println("0. Cerrar sesión");
	}

	private void mostrarMenuCoordinacion() {
		System.out.println("1. Crear o modificar espectáculos");
		System.out.println("2. Gestionar números");
		System.out.println("3. Asignar artistas a números");
		System.out.println("4. Ver información completa de espectáculos");
		System.out.println("0. Cerrar sesión");
	}

	private void mostrarMenuArtista() {
		System.out.println("1. Ver mi ficha personal");
		System.out.println("2. Ver mis espectáculos y números");
		System.out.println("0. Cerrar sesión");
	}

	private void mostrarMenuInvitado() {
	        System.out.println("1. Ver lista básica de espectáculos");
	        System.out.println("0. Salir");
	}

	@Override
	public String toString() {
		return "Sesion [usuario=" + usuario + ", perfil=" + perfil + ", activa=" + activa + ", getId()=" + getId()
				+ ", getNombre()=" + getNombre() + ", getPassword()=" + getPassword() + ", getPerfil()=" + getPerfil()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
