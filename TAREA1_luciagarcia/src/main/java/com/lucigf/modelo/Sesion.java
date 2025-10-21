package com.lucigf.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Scanner;

public class Sesion {

	private String nombreUsuario;
	private Perfil perfilActual;

	
	
	public Sesion(String nombreUsuario, Perfil perfilActual) {
		this.nombreUsuario = nombreUsuario;
		this.perfilActual = perfilActual;
	}




	public boolean login(String usuario, String contrasenia) {
		
		if(perfilActual != Perfil.INVITADO) {
			System.out.println("Ya hay una sesion activa.");
			return false;
		}
		
		 if (usuario.isEmpty() || contrasenia.isEmpty()) {
	            System.out.println("Usuario o contraseña vacíos");
	            return false;
	        }
		/*
		if("admin".equals(usuario) && "admin".equals(contrasenia)) {
			perfilActual=Perfil.ADMIN;
			nombreUsuario="Admin";
			return true;
		}
		*/
		
		try(BufferedReader br=new BufferedReader(new FileReader("credenciales.txt"))){
			
			String linea;
			while((linea=br.readLine())!=null) {
				
				String[]datos=linea.split("\\|");
				if(datos.length>=7 && datos.equals(usuario) && datos.equals(contrasenia)) {
					nombreUsuario=datos[1];
					perfilActual=Perfil.valueOf(datos[6]);
					System.out.println("Login correcto. Bienvenido, " +datos[4]+ "("+perfilActual+ ")");
					return true;
				}
					
			}
				
				
			}catch(IOException e) {
				System.out.println("Error al leer credencieles.txt " +e.getMessage());
			}
			
			System.out.println("Credenciales incorrectas");
			return false;
		}
		
	
	
		public void logout() {
			if(perfilActual==Perfil.INVITADO) {
				System.out.println("No hay sesion activa para cerrar");
				return;
			}
			
			System.out.println("Sesión cerrada " +nombreUsuario);
			perfilActual=Perfil.INVITADO;
			nombreUsuario=null;
		}
		
	
	
	
	
	public void registrarPersona() {
		Scanner teclado=new Scanner(System.in);
		if(perfilActual!=Perfil.ADMIN) {
			System.out.println("Acesso denegado, solo el ADMIN puede registrar personas");
			return;
		}
		
		System.out.println("== Registrar nueva persona ==");
		System.out.println("Nombre: ");
		String nombre=teclado.next();
		System.out.println("Email: ");
		String email=teclado.next();
		System.out.println("Nacionalidad: ");
		String nacionalidad=teclado.next();
		System.out.println("Usuario: ");
		String usuario=teclado.next();
		System.out.println("Contraseña: ");
		String contrasenia=teclado.next();
		System.out.println("Perfil: COORDINACION O ARTISTA");
		Perfil perfil=Perfil.valueOf(teclado.next().toUpperCase());
		
		
	
		int idpersona=0;
		
		//Escribir en el fichero
		try(BufferedWriter bw=new BufferedWriter(new FileWriter("credenciales.txt",true))){
			bw.write((idpersona++)+ "|" + usuario+ "|" + contrasenia+ "|" +email+ "|" +nacionalidad+ "|" +perfil);
			bw.newLine();
			System.out.println("Persona registrada correctamente");
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			
		}catch (IOException e) {
			System.out.println("Error escribiendo en credenciales.txt" +e.getMessage());
		}
		
		
		
	}
	
	// Menu dependiendo del perfil

		public void mostrarMenu() {
			if(perfilActual==null) {
				
				System.out.println("== Menú Invitado ==" );
				System.out.println("1. Ver espectáculos");
				System.out.println("0. Salir");
			}else {
				switch(perfilActual) {
				case ADMIN:
					System.out.println("== Menú Administrador ==");
					System.out.println("1. Registrar nueva persona");
					System.out.println("2. Gestionar credenciales");
					System.out.println("3. Gestionar espectáculos");
					System.out.println("4. Ver todos los datos del circo");
					System.out.println("0. Cerrar sesión");
					break;
					
				case COORDINACION:
					System.out.println("== Menú Coordinación ==");
					System.out.println("1. Crear o modificar espectáculos");
					System.out.println("2. Gestionar números");
					System.out.println("3. Asignar artistas a números");
					System.out.println("4. Ver información completa de espectáculos");
					System.out.println("0. Cerrar sesión");
					break;
					
				case ARTISTA:
					System.out.println("== Menú Artista ==");
					System.out.println("1. Ver mi ficha personal");
					System.out.println("2. Ver mis espectáculos y números");
					System.out.println("0. Cerrar sesión");
					break;
				}
			}
		}
	

	
	 // Getters
    public Perfil getPerfilActual() {
        return perfilActual;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
	

}
