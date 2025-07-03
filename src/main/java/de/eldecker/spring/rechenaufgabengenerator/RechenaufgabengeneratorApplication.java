package de.eldecker.spring.rechenaufgabengenerator;

import static org.springframework.boot.WebApplicationType.NONE;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Einstiegsklasse für die Spring Boot Anwendung.
 */
@SpringBootApplication
public class RechenaufgabengeneratorApplication {

	public static void main( String[] args ) {

		final SpringApplication app = new SpringApplication( RechenaufgabengeneratorApplication.class );

		// Wenn "konsole"-Profil aktiv ist, dann Web-Umgebung deaktivieren,
		// damit Anwendung nach Erzeugung PDF sich beendet
		if ( Arrays.asList( args ).contains( "--spring.profiles.active=konsole" ) ||
		     System.getProperty( "spring.profiles.active", "" ).contains( "konsole" ) ) {

			app.setWebApplicationType( NONE );
		}

		app.run( args );
	}

}
