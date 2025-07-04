package de.eldecker.spring.rechenaufgabengenerator;

import static java.lang.System.getProperty;
import static java.util.Arrays.asList;
import static org.springframework.boot.WebApplicationType.NONE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Einstiegsklasse für die Spring Boot Anwendung.
 */
@SpringBootApplication
public class RechenaufgabengeneratorApplication {

	public static void main( String[] args ) {

		final SpringApplication app = new SpringApplication( RechenaufgabengeneratorApplication.class );

		// Wenn das Profil "konsole" aktiv ist, dann Web-Umgebung deaktivieren,
		// damit Anwendung nach Erzeugung PDF sich beendet
		if ( asList( args ).contains( "--spring.profiles.active=konsole" ) ||
		     getProperty( "spring.profiles.active", "" ).contains( "konsole" ) ) {

			app.setWebApplicationType( NONE );
		}

		app.run( args );
	}

}
