package com.lucigf.modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

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
		
	
	
	
	
	public void registrarPersona() throws FileNotFoundException, IOException {
		Scanner teclado=new Scanner(System.in);
		if(perfilActual!=Perfil.ADMIN) {
			System.out.println("Acesso denegado, solo el ADMIN puede registrar personas");
			return;
		}
		
		System.out.println("== Registrar nueva persona ==");
		System.out.println("Nombre: ");
		String nombre=teclado.next().trim();
		System.out.println("Email: ");
		String email=teclado.next().trim();
		System.out.println("Nacionalidad: ");
		String nacionalidad=teclado.next().trim();
		System.out.println("Usuario: ");
		String usuario=teclado.next().trim();
		System.out.println("Contraseña: ");
		String contrasenia=teclado.next().trim();
		System.out.println("Perfil: COORDINACION O ARTISTA");
		String tipoPerfil=teclado.nextLine().trim().toUpperCase();
		
		
		boolean esSenior=false;
		String fechaSenior="";
		String apodo="";
		String especialidades="";
		
	
		
		if(tipoPerfil.equals("COORDINACION")) {
			System.out.println("¿Es senior? (S/N): ");
			String respuesta=teclado.nextLine().trim().toUpperCase();
			if(respuesta.equals("S")){
					esSenior=true;
					System.out.println("Fecha desde que es senior (DD/MM/AAAA): ");
					fechaSenior=teclado.nextLine().trim();
			}
			
		}else {
			System.out.println("¿Tiene apodo artístico? (S/N): ");
			String respuesta=teclado.nextLine().trim().toUpperCase();
			if(respuesta.equals("S")) {
				System.out.println("Apodo: ");
				apodo=teclado.nextLine().trim();
			}
			
			
			System.out.println("Especialidades disponibles: " +Arrays.toString(Especialidad.values()));
			
			boolean ok = false;
		    Set<Especialidad> especialidadesSeleccionadas = EnumSet.noneOf(Especialidad.class);

		    while (!ok) {
		        System.out.print("Introduce las especialidades separadas por comas: ");
		        String entrada = teclado.nextLine().trim().toUpperCase();

		        if (entrada.isEmpty()) {
		            System.out.println("Debes introducir al menos una especialidad");
		            continue;
		        }
		        
		        String[]partes=entrada.split(",");
		        ok=true;
		        
		        
		        for(String p:partes) {
		        	try {
		        		Especialidad esp=Especialidad.valueOf(p.trim());
		        	}catch(IllegalArgumentException e) {
		        		System.out.println("No es una especialidad válida");
		        		ok=false;
		        		break;
		        		
		        	}
		        }
		      }
		    especialidades = especialidadesSeleccionadas.toString()
		            .replace("[", "")
		            .replace("]", "")
		            .replace(" ", "");
			
		}
		
		
		//Credenciales
		System.out.println("Nombre de usuario: ");
		usuario=teclado.nextLine().trim().toLowerCase();
		if(!Pattern.matches("[a-z] {3,}", usuario)) {
			System.out.println("La contraseña debe tener al menos 3 caracteres y no contener espacios");
			return;
		}
		
		
		
		//duplicados
		try(BufferedReader br=new BufferedReader(new FileReader("credenciales.txt"))){
			String linea;
			while((linea=br.readLine())!=null) {
				String[]datos=linea.split("\\|");
				if(datos.length>=7) {
					if(datos[1].equals(usuario)) {
						System.out.println("Ya existe usuario con ese nombre");
						return;
					}
					if(datos[3].equalsIgnoreCase(email)) {
						System.out.println("Ya existe una persona registrada con ese email");
						return;
					}
				}
			}
		}catch(FileNotFoundException e){
			
		}catch(IOException e) {
			System.out.println("Error leyendo credenciales.txt: " +e.getMessage());
		}
		
		
		//Calcular id
		
		
		int idpersona=contarLineasFichero("credenciales.txt")+1;
		
		//Escribir en el fichero
		try(BufferedWriter bw=new BufferedWriter(new FileWriter("credenciales.txt",true))){
			bw.write((idpersona++)+ "|" + usuario+ "|" + contrasenia+ "|" +email+ "|" +nacionalidad+ "|" +tipoPerfil);
			bw.newLine();
			System.out.println("Persona registrada correctamente");
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			
		}catch (IOException e) {
			System.out.println("Error escribiendo en credenciales.txt" +e.getMessage());
		}
		
		
		
	}
	
	
	private int contarLineasFichero(String nombreFichero) {
		int contador=0;
		try(BufferedReader br=new BufferedReader(new FileReader(nombreFichero))){
			while(br.readLine()!=null)
				contador++;
		}catch(IOException e) {
			
		}
		return contador;
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
