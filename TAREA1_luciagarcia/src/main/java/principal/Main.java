package principal;

import com.lucigf.modelo.Artista;
import com.lucigf.modelo.Coordinacion;
import com.lucigf.modelo.Credenciales;
import com.lucigf.modelo.Perfil;
import com.lucigf.modelo.Sesion;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Credenciales credAdmin = new Credenciales(1L, "admin", "admin", Perfil.ADMIN);
        

        Credenciales credCoord = new Credenciales(2L, "laura", "1234", Perfil.COORDINACION);
        Coordinacion coord = new Coordinacion();

        Credenciales credArtista = new Credenciales(3L, "carlos", "abcd", Perfil.ARTISTA);
        Artista artista = new Artista(23l,"Lucia");
        
        Sesion sesion2 = new Sesion();
        sesion2.mostrarMenu();
		
	}

}
