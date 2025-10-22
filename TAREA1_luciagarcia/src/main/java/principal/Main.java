package principal;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.NonWritableChannelException;
import java.util.ArrayList;
import java.util.Scanner;

import com.lucigf.modelo.Artista;
import com.lucigf.modelo.Coordinacion;
import com.lucigf.modelo.Credenciales;
import com.lucigf.modelo.Espectaculo;
import com.lucigf.modelo.Perfil;
import com.lucigf.modelo.Sesion;

public class Main {

	private static Sesion sesionActual=new Sesion(null, Perfil.INVITADO);
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
		
		public static void iniciarSesion() {
			
			if(sesionActual.getPerfilActual()!=Perfil.INVITADO) {
				System.out.println("Ya hay una sesión activa");
				return;
			}
		
			System.out.println("== INICIO DE SESIÓN ==");
			System.out.println("Usuario: ");
			String usuario=teclado.nextLine();
			System.out.println("Contraseña: ");
			String password=teclado.nextLine();
			
			
		}
		
		//Cerrar sesión 
		
		public static void cerrarSesion() {
			if(sesionActual.getPerfilActual()== Perfil.INVITADO) {
				System.out.println("No hay ninguna sesion activa");
				
			}else{
				sesionActual.logout();
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
	
	


