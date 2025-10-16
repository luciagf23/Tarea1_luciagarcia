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

	
	//Ver espectaculo
	public static void verEspectaculos() throws IOException, ClassNotFoundException {
		try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream("espectaculos.dat"))){
			
			ArrayList<Espectaculo>espectaculos=(ArrayList<Espectaculo>) ois.readObject();
			System.out.println("== Espectáculos ==");
			for(Espectaculo e:espectaculos) {
				System.out.println(e);
			}
		}catch(FileNotFoundException e) {
			System.out.println("Archivo no encontrado");
			
		}
			
		}
	
	
		
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub

		
		
		Scanner teclado=new Scanner(System.in);
		
		int opcion;
		 
		
		do {
			System.out.println("1. Ver espectáculos");
			System.out.println("2. Iniciar Sesion");
			System.out.println("3. Salir");
			
			System.out.println("Introduce una opcion");
			opcion=teclado.nextInt();
			
			switch(opcion) {
			
			case 1:verEspectaculos();
				
				break;
				
			case 2:
				
				break;
				
			case 3:
				break;
				
				default: System.out.println("Saliendo...");
			
			
			}
			
			
			
		}while(opcion!=5);
		
		
		
		
		
		/*
		Credenciales credAdmin = new Credenciales(1L, "admin", "admin", Perfil.ADMIN);
        

        Credenciales credCoord = new Credenciales(2L, "laura", "1234", Perfil.COORDINACION);
        Coordinacion coord = new Coordinacion();

        Credenciales credArtista = new Credenciales(3L, "carlos", "abcd", Perfil.ARTISTA);
        Artista artista = new Artista(23l,"Lucia");
        
        Sesion sesion2 = new Sesion();
        sesion2.mostrarMenu();
		
    */    
        
        
	}

}
