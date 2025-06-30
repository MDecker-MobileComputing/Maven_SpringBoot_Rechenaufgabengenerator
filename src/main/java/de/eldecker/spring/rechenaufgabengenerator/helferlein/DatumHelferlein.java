package de.eldecker.spring.rechenaufgabengenerator.helferlein;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Hilfsklasse für Arbeit mit Datum und Zeit.
 */
public class DatumHelferlein {

	/** Formatierungs-String für Format {@code dd.MM.yyyy (EE), HH:mm 'Uhr'} */ 
	private static final DateTimeFormatter FORMATIERER = 
			DateTimeFormatter.ofPattern( "dd.MM.yyyy (EE), HH:mm 'Uhr'" );
	
	
	/**
	 * Methode gibt aktuelles Datum und Zeit zurück. 
	 * 
	 * @return Datum und Zeit im deutschen Format.
	 *         Beispielausgabe:<pre>30.06.2025 (Mo.), 13:16 Uhr</pre> 
	 */
    public static String getDatumUndZeitSring() {
 
    	final LocalDateTime jetzt = now();

    	return FORMATIERER.format( jetzt );
    }

}
