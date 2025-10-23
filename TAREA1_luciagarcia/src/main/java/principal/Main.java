package principal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.NonWritableChannelException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lucigf.modelo.Artista;
import com.lucigf.modelo.Configuracion;
import com.lucigf.modelo.Coordinacion;
import com.lucigf.modelo.Credenciales;
import com.lucigf.modelo.Especialidad;
import com.lucigf.modelo.Espectaculo;
import com.lucigf.modelo.Perfil;
import com.lucigf.modelo.Sesion;

/**
 * @author Lucía García Fernández
 * @version 1.0
 * @since 2025
 */
public class Main {

	private static String nombreUsuario = null;
	private static Perfil perfilActual = Perfil.INVITADO;
	private static Sesion sesionActual = new Sesion(null, Perfil.INVITADO);
	private static Scanner teclado = new Scanner(System.in);

	// Rutas ficheros
	private static final String rutaEspectaculos = "src/main/resources/ficheros/espectaculos.dat";
	private static final String rutaCredenciales = "src/main/resources/ficheros/credenciales.txt";
	private static final String rutaPaises = "src/main/resources/ficheros/paises.xml";

	// CU1: Ver espectáculos

	public static void verEspectaculos() {

		File fichero = new File(rutaEspectaculos);
		if (!fichero.exists()) {
			System.out.println("Archivo espectaculos.dat no encontrado");
			return;
		}
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaEspectaculos))) {

			ArrayList<Espectaculo> espectaculos = (ArrayList<Espectaculo>) ois.readObject();
			System.out.println("== Espectáculos ==");
			for (Espectaculo e : espectaculos) {
				System.out.println("ID: " + e.getId() + "| Nombre: " + e.getNombre() + "| Fechas: " + e.getFechaini()
						+ "-" + e.getFechafin());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Archivo espectaculos.dat no encontrado");

		} catch (IOException | ClassNotFoundException e) {
			System.out.print("Error al leer espectáculos " + e.getMessage());

		}
	}

	// CU2: Login

	public static boolean iniciarSesion() {

		if (perfilActual != Perfil.INVITADO) {
			System.out.println("Ya hay una sesión activa");
			return false;
		}

		System.out.println("Usuario: ");
		String usuario = teclado.nextLine().trim();
		System.out.println("Contraseña: ");
		String contrasenia = teclado.nextLine().trim();

		if (usuario.isEmpty() || contrasenia.isEmpty()) {
			System.out.println("Usuario o contraseña vacíos");
			return false;
		}

		try (BufferedReader br = new BufferedReader(new FileReader(rutaCredenciales))) {

			String linea;
			while ((linea = br.readLine()) != null) {

				String[] datos = linea.split("\\|");
				if (datos.length >= 7 && datos[1].equals(usuario) && datos[2].equals(contrasenia)) {
					nombreUsuario = datos[1];
					perfilActual = Perfil.valueOf(datos[6].toUpperCase());
					sesionActual = new Sesion(Long.parseLong(datos[0]), perfilActual);
					System.out.println("Login correcto. Bienvenido, " + datos[4] + "(" + perfilActual + ")");
					return true;
				}
			}
		} catch (IOException e) {
			System.out.println("Error al leer credenciales.txt " + e.getMessage());
		}

		System.out.println("Credenciales incorrectas");
		return false;

	}

	// CU2: Logout

	public static void cerrarSesion() {
		if (perfilActual == Perfil.INVITADO) {
			System.out.println("No hay ninguna sesion activa");
			return;
		}
		System.out.println("Sesión cerrada " + nombreUsuario);
		perfilActual = Perfil.INVITADO;
		nombreUsuario = null;

	}

	// CU3: Registrar persona

	public static void registrarPersona() throws IOException {
		Scanner teclado = new Scanner(System.in);
		/*
		 * if (perfilActual != Perfil.ADMIN) {
		 * System.out.println("Acceso denegado, solo el ADMIN puede registrar personas"
		 * ); return; }
		 */
		System.out.println("== Registrar nueva persona ==");

		InputStream input = Main.class.getClassLoader().getResourceAsStream(Configuracion.get("ficheronacionalidades"));
		if (input == null) {
			System.out.println("No se pudo encontrar el archivo de nacionalidades.");
			return;
		}

		System.out.print("Nombre: ");
		String nombre = teclado.nextLine().trim();
		System.out.print("Email: ");
		String email = teclado.nextLine().trim();

		Map<Integer, String> paises = cargarPaises();
		if (paises.isEmpty()) {
			System.out.println("No se pudieron cargar los paises");
			return;
		}
		System.out.println("Lista de paises disponibles: ");
		paises.forEach((id, nombrePais) -> System.out.println(id + " - " + nombrePais));

		int idSeleccionado;
		while (true) {
			System.out.print("Introduce el id del país: ");
			try {
				idSeleccionado = Integer.parseInt(teclado.nextLine().trim());
				if (paises.containsKey(idSeleccionado))
					break;
				System.out.print("ID no válido, intentalo de nuevo");

			} catch (NumberFormatException e) {
				System.out.print("Por favor, introduzca un número válido");
			}

		}

		String nacionalidad = paises.get(idSeleccionado);
		System.out.print("Nacionalidad seleccionada: " + nacionalidad);

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
			boolean opcion = false;
			Set<Especialidad> especialidadesSeleccionadas = EnumSet.noneOf(Especialidad.class);

			while (!opcion) {
				System.out.print("Introduce las especialidades separadas por comas: ");
				String entrada = teclado.nextLine().trim().toUpperCase();

				if (entrada.isEmpty()) {
					System.out.println("Debes introducir al menos una especialidad");
					continue;
				}

				String[] partes = entrada.split(",");
				opcion = true;

				for (String p : partes) {
					try {
						Especialidad esp = Especialidad.valueOf(p.trim());
						especialidadesSeleccionadas.add(esp);
					} catch (IllegalArgumentException e) {
						System.out.println("No es una especialidad válida");
						opcion = false;
						break;
					}
				}
			}
			especialidades = especialidadesSeleccionadas.toString().replace("[", "").replace("]", "").replace(" ", "");
		}

		// Credenciales
		System.out.println("Nombre de usuario: ");
		usuario = teclado.nextLine().trim().toLowerCase();
		if (!Pattern.matches("[a-z]{3,}", usuario)) {
			System.out.println("La contraseña debe tener al menos 3 caracteres y no contener espacios");
			return;
		}

		// duplicados
		try (BufferedReader br = new BufferedReader(new FileReader(rutaCredenciales))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split("\\|");
				if (datos.length >= 7) {
					if (datos[1].equals(usuario)) {
						System.out.println("Ya existe usuario con ese nombre");
						return;
					}
					if (datos[3].equalsIgnoreCase(email)) {
						System.out.println("Ya existe una persona registrada con ese email");
						return;
					}
				}
			}
		} catch (FileNotFoundException e) {

		} catch (IOException e) {
			System.out.println("Error leyendo credenciales.txt: " + e.getMessage());
		}

		// ID automático
		int idpersona = contarLineasFichero(rutaCredenciales) + 1;

		// Escribir en el fichero

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaCredenciales, true))) {
			bw.write(idpersona + "|" + usuario + "|" + contrasenia + "|" + email + "|" + nacionalidad + "|" + tipoPerfil
					+ "|" + perfilActual);
			bw.newLine();
			System.out.println("Persona registrada correctamente");
		} catch (IOException e) {
			System.out.println("Error escribiendo en credenciales.txt: " + e.getMessage());
		}
	}

	private static int contarLineasFichero(String nombreFichero) {
		int contador = 0;
		try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
			while (br.readLine() != null)
				contador++;
		} catch (IOException e) {

		}
		return contador;
	}

	// Fichero xml
	private static Map<Integer, String> cargarPaises() {
		Map<Integer, String> paises = new LinkedHashMap<>();
		File xmlFile = new File("paises.xml");
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document docu = (Document) builder.parse(xmlFile);
			NodeList lista = ((org.w3c.dom.Document) docu).getElementsByTagName("pais");

			for (int i = 0; i < lista.getLength(); i++) {
				Element e = (Element) lista.item(i);
				int id = Integer.parseInt(e.getAttribute("id"));
				String nombre = e.getAttribute("nombre");
				paises.put(id, nombre);
			}

		} catch (Exception e) {
			System.out.println("Error leyendo paises.xml: " + e.getMessage());
		}
		return paises;
	}

	// CU5: Crear espectáculo
	public static void crearEspectaculo() throws FileNotFoundException, IOException {
		if (perfilActual != Perfil.ADMIN && perfilActual != Perfil.COORDINACION) {
			System.out.println("No tienes permisos para crear espectáculos");
			return;
		}

		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		System.out.println("== Crear nuevo espectáculo ==");

		System.out.println("Nombre del espectáculo: ");
		String nombre = teclado.nextLine().trim();

		if (nombre.isEmpty()) {
			System.out.println("El nombre no puede estar vacío");
			return;
		}
		if (nombre.length() > 25) {
			System.out.println("El nombre no puede superar los 25 caracteres");
			return;
		}
		LocalDate fechaIni, fechaFin;

		try {
			System.out.println("Fecha inicio (DD/MM/AAAA): ");
			fechaIni = LocalDate.parse(teclado.nextLine().trim(), formato);
			System.out.println("Fecha fin (DD/MM/AAAA): ");
			fechaFin = LocalDate.parse(teclado.nextLine().trim(), formato);

			if (fechaFin.isBefore(fechaIni)) {
				System.out.println("La fecha fin no puede ser anterior a la de inicio");
				return;
			}

		} catch (DateTimeParseException e) {
			System.out.println("Formato de fecha inválido. Debe ser DD/MM/AAAA");
			return;
		}

		// Leer espectáculos
		ArrayList<Espectaculo> lista = new ArrayList<>();
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rutaEspectaculos))) {
			lista = (ArrayList<Espectaculo>) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("Fichero no encontrado");

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error leyendo espectaculos: " + e.getMessage());
			return;
		}
		// Validar nombre
		for (Espectaculo e : lista) {
			if (e.getNombre().equalsIgnoreCase(nombre)) {
				System.out.println("Ya existe un espectáculo con ese nombre");
				return;
			}

		}

		// Nuevo ID
		long nuevoId = lista.isEmpty() ? 1 : lista.get(lista.size() - 1).getId() + 1;

		// Asignar coordinador
		Coordinacion coordinadorAsignado = null;
		ArrayList<Coordinacion> coordinadores = obtenerCoordinadores();

		if (perfilActual == Perfil.ADMIN) {
			if (coordinadores.isEmpty()) {
				System.out.println("No hay coordinadores disponibles para asignar");
				return;
			}
			System.out.println("Seleccione un coordinador de la lista:");
			for (int i = 0; i < coordinadores.size(); i++) {
				System.out.println((i + 1) + " . " + coordinadores.get(i).getNombre());
			}

			int opcion = 0;
			while (true) {
				System.out.println("Opcion: ");

				try {
					opcion = Integer.parseInt(teclado.nextLine().trim());
					if (opcion >= 1 && opcion <= coordinadores.size()) {
						coordinadorAsignado = coordinadores.get(opcion - 1);
						break;

					} else {
						System.out.println("Opcion fuera de rango. Intentalo de nuevo");

					}
				} catch (NumberFormatException e) {
					System.out.println("Selección inválida. Intentalo de nuevo");

				}
			}

		} else if (perfilActual == Perfil.COORDINACION) {

			coordinadorAsignado = new Coordinacion();
			coordinadorAsignado.setNombre(nombreUsuario);
			coordinadorAsignado.setIdCoord(sesionActual.getId());
		}

		Espectaculo nuevo = new Espectaculo(nuevoId, nombre, fechaIni, fechaFin, coordinadorAsignado);
		lista.add(nuevo);

		// Guardar en el fichero
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rutaEspectaculos))) {
			oos.writeObject(lista);
			System.out.println("Espectáculo creado correctamente");
		} catch (IOException e) {
			System.out.println("Error guardando el espectáculo: " + e.getMessage());
		}

	}

	private static ArrayList<Coordinacion> obtenerCoordinadores() {
		ArrayList<Coordinacion> listaCoord = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(rutaCredenciales))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split("\\|");
				if (datos.length >= 7 && datos[6].equalsIgnoreCase("COORDINACION")) {
					Coordinacion c = new Coordinacion();
					c.setNombre(datos[4]);
					c.setIdCoord(Long.parseLong(datos[0]));// id
					listaCoord.add(c);
				}
			}

		} catch (Exception e) {
			System.out.println("Error leyendo coordinadores: " + e.getMessage());

		}
		return listaCoord;
	}

	// Menu dependiendo del perfil

	public static void mostrarMenu() {
		if (perfilActual == Perfil.INVITADO) {

			System.out.println("== Menú Invitado ==");
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Login");
			System.out.println("0. Salir");
		} else {
			switch (perfilActual) {
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

		boolean salir = false;

		Scanner teclado = new Scanner(System.in);

		System.out.println("Bienvenidos al sistema de gestión del Circo de Lucía");
		System.out.println("--------------------------------------------------------");
		System.out.println("Aquí podrás gestionar espectáculos, artistas y mucho más");
		System.out.println();

		while (!salir) {

			mostrarMenu();
			System.out.print("Selecciona una opcion: ");
			String opcion = teclado.nextLine();

			switch (sesionActual.getPerfilActual()) {

			case INVITADO:
				salir = menuInvitado(opcion);

				break;

			case ADMIN:
				salir = menuAdmin(opcion);
				break;

			case COORDINACION:
				salir = menuCoordinacion(opcion);
				break;

			case ARTISTA:
				salir = menuArtista(opcion);
				break;

			default:
				System.out.println("Saliendo...");

			}

		}
		System.out.println("Programa finalizado. ¡Hasta luego!");

	}

	// Menú según el perfil

	// Invitado
	private static boolean menuInvitado(String opcion) {
		switch (opcion) {

		case "1":
			verEspectaculos();
			break;
		case "2":
			iniciarSesion();
			break;
		case "0":
			System.out.println("Gracias por visitar el Circo. ¡Hasta pronto!");
			return true;
		default:
			System.out.println("Opcion no válida");

		}
		return false;
	}

	// Admin
	private static boolean menuAdmin(String opcion) throws FileNotFoundException, IOException {

		switch (opcion) {
		case "1":
			registrarPersona();
			break;
		case "2":
			System.out.println("Gestión de credenciales");
			break;
		case "3":
			crearEspectaculo();
			break;
		case "4":
			verEspectaculos();
			break;
		case "0":
			cerrarSesion();
			return true;
		default:
			System.out.println("Opción no válida");

		}
		return false;

	}

	private static boolean menuCoordinacion(String opcion) throws FileNotFoundException, IOException {
		switch (opcion) {
		case "1":
			System.out.println("Crear/modificar espectáculos");
			crearEspectaculo();
			break;
		case "2":
			System.out.println("Gestionar números");
			break;
		case "3":
			System.out.println("Asignar artistas a números");
			break;
		case "4":
			verEspectaculos();
			break;
		case "0":
			cerrarSesion();
			break;
		default:
			System.out.println("Opción inválida");
		}
		return false;
	}

	// Artista

	private static boolean menuArtista(String opcion) {
		switch (opcion) {
		case "1":
			System.out.println("Ver mi ficha personal");
			break;
		case "2":
			verEspectaculos();
			break;
		case "0":
			cerrarSesion();
			break;
		default:
			System.out.println("Opción no válida");

		}
		return false;
	}

}
