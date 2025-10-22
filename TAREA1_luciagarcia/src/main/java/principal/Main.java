package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import com.lucigf.modelo.Artista;
import com.lucigf.modelo.Coordinacion;
import com.lucigf.modelo.Credenciales;
import com.lucigf.modelo.Especialidad;
import com.lucigf.modelo.Espectaculo;
import com.lucigf.modelo.Perfil;
import com.lucigf.modelo.Sesion;

public class Main {

	private static String nombreUsuario=null;
	private static Perfil perfilActual = Perfil.INVITADO;
	private static Sesion sesionActual=new Sesion(null,Perfil.INVITADO);
	private static Scanner teclado=new Scanner(System.in);
	
	//CU1: Ver espectáculos básicos
	
	public static void verEspectaculos() {
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("espectaculos.dat"))){
			
			ArrayList<Espectaculo>espectaculos=(ArrayList<Espectaculo>) ois.readObject();
			System.out.println("== Espectáculos ==");
			for(Espectaculo e:espectaculos) {
				System.out.println("ID: " +e.getId()+
						"| Nombre: " +e.getNombre()+
						"| Fechas: " +e.getFechaini() + "-" + e.getFechafin());
			}
		}catch(FileNotFoundException e) {
			System.out.println("Archivo espectaculos.dat no encontrado");
			
		}catch(IOException|ClassNotFoundException e){
			System.out.print("Error al leer espectáculos " +e.getMessage());
			
			}
		}
	
		//CU2: Iniciar sesión
		
		public static boolean iniciarSesion(String usuario,String contrasenia){
			
			if(perfilActual !=Perfil.INVITADO) {
				System.out.println("Ya hay una sesión activa");
				return false;
			}
		
			if (usuario.isEmpty() || contrasenia.isEmpty()) {
	            System.out.println("Usuario o contraseña vacíos");
	            return false;
	        }
			
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
		
		//Cerrar sesión 
		
		public static void cerrarSesion() {
			if(perfilActual== Perfil.INVITADO) {
				System.out.println("No hay ninguna sesion activa");
				return;
			}
			System.out.println("Sesión cerrada " +nombreUsuario);
			perfilActual=Perfil.INVITADO;
			nombreUsuario=null;
			
		}
		
		public static void registrarPersona() throws IOException {
	        Scanner teclado = new Scanner(System.in);
	        if (perfilActual != Perfil.ADMIN) {
	            System.out.println("Acceso denegado, solo el ADMIN puede registrar personas");
	            return;
	        }

	        System.out.println("== Registrar nueva persona ==");
	        System.out.print("Nombre: ");
	        String nombre = teclado.nextLine().trim();
	        System.out.print("Email: ");
	        String email = teclado.nextLine().trim();
	        System.out.print("Nacionalidad: ");
	        String nacionalidad = teclado.nextLine().trim();
	        System.out.print("Usuario: ");
	        String usuario = teclado.nextLine().trim();
	        System.out.print("Contraseña: ");
	        String contrasenia = teclado.nextLine().trim();
	        System.out.print("Perfil (COORDINACION/ARTISTA): ");
	        String tipoPerfil = teclado.nextLine().trim().toUpperCase();

	        boolean esSenior = false;
	        String fechaSenior = "";
	        String apodo = "";
	        String especialidades = "";

	        if (tipoPerfil.equals("COORDINACION")) {
	            System.out.print("¿Es senior? (S/N): ");
	            String respuesta = teclado.nextLine().trim().toUpperCase();
	            if (respuesta.equals("S")) {
	                esSenior = true;
	                System.out.print("Fecha desde que es senior (DD/MM/AAAA): ");
	                fechaSenior = teclado.nextLine().trim();
	            }
	        } else {
	            System.out.print("¿Tiene apodo artístico? (S/N): ");
	            String respuesta = teclado.nextLine().trim().toUpperCase();
	            if (respuesta.equals("S")) {
	                System.out.print("Apodo: ");
	                apodo = teclado.nextLine().trim();
	            }

	            System.out.println("Especialidades disponibles: " + Arrays.toString(Especialidad.values()));
	            boolean ok = false;
	            Set<Especialidad> especialidadesSeleccionadas = EnumSet.noneOf(Especialidad.class);

	            while (!ok) {
	                System.out.print("Introduce las especialidades separadas por comas: ");
	                String entrada = teclado.nextLine().trim().toUpperCase();

	                if (entrada.isEmpty()) {
	                    System.out.println("Debes introducir al menos una especialidad");
	                    continue;
	                }

	                String[] partes = entrada.split(",");
	                ok = true;

	                for (String p : partes) {
	                    try {
	                        Especialidad esp = Especialidad.valueOf(p.trim());
	                        especialidadesSeleccionadas.add(esp);
	                    } catch (IllegalArgumentException e) {
	                        System.out.println("No es una especialidad válida");
	                        ok = false;
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
			
	        
	        
	        int idpersona = contarLineasFichero("credenciales.txt") + 1;
		
	        //Escribir en el fichero
	        
	        try (BufferedWriter bw = new BufferedWriter(new FileWriter("credenciales.txt", true))) {
	            bw.write(idpersona + "|" + usuario + "|" + contrasenia + "|" + email + "|" + nacionalidad + "|" + tipoPerfil + "|" + perfilActual);
	            bw.newLine();
	            System.out.println("Persona registrada correctamente");
	        } catch (IOException e) {
	            System.out.println("Error escribiendo en credenciales.txt: " + e.getMessage());
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
		
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		boolean salir=false;
		
		
		Scanner teclado=new Scanner(System.in);
		
		while(!salir) {
		 
			sesionActual.mostrarMenu();
			System.out.print("Selecciona una opcion: ");
			String opcion=teclado.nextLine();
			
	
			
			switch(sesionActual.getPerfilActual()) {
			
			case INVITADO:
				menuInvitado(opcion);
				
				break;
				
			case ADMIN:
					menuAdmin(opcion);
				break;
				
			case COORDINACION:
					menuCoordinacion(opcion);
				break;
				
			case ARTISTA:
					menuArtista(opcion);
				break;
				
				default: System.out.println("Saliendo...");
			
			
			}
      
	}
		System.out.println("Programa finalizado. ¡Hasta luego!");
		
	}
	
	
	
	//Menú según el perfil
	
	
	//Invitado
	private static boolean menuInvitado(String opcion){
		switch(opcion) {
		
		case "1":
			verEspectaculos();
		break;
		case "2":
			iniciarSesion();
		case "0":
			return true;
			default:System.out.println("Opcion no válida");
		
		}
		return false;
	}
	
	
	//Admin
	private static boolean menuAdmin(String opcion) throws FileNotFoundException, IOException {
		
		switch(opcion) {
		case "1":
			sesionActual.registrarPersona();
			break;
		case "2":
			System.out.println("Gestión de credenciales");
			break;
		case "3":
			System.out.println("Gestion de espectáculos");
			break;
		case "4":
			verEspectaculos();
			break;
		case "0":cerrarSesion();
			default:System.out.println("Opción no válida");
			
		
		}
		return false;
		
	}
		
		//Coordinacion
		
		private static boolean menuCoordinacion(String opcion) {
			switch(opcion) {
				case "1":System.out.println("Crear/modificar espectáculos");
				break;
				
				case "2":System.out.println("Gestionar números");
				break;
				case "3":System.out.println("Asignar artistas a números");
				break;
				case "4":verEspectaculos();
				break;
				case "0":cerrarSesion();
				default:System.out.println("Opción inválida");
			}
			return false;
		}
		
	
		//Artista
		
		private static boolean menuArtista(String opcion){
			switch(opcion) {
			case "1": System.out.println("Ver mi ficha personal");
				break;
			case "2": verEspectaculos();
			break;
			case "0":System.out.println("Opción no válida");
				
			}
			return false;
		}
		
		
		
	}
	
	


