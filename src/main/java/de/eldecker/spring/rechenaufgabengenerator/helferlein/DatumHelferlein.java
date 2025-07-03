package de.eldecker.spring.rechenaufgabengenerator.helferlein;

import static java.time.LocalDateTime.now;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * Hilfsklasse für Arbeit mit Datum und Zeit.
 */
public class DatumHelferlein {

	/** Formatierungs-String für Format {@code dd.MM.yyyy (EE), HH:mm 'Uhr'}. */ 
	private static final DateTimeFormatter FORMATIERER1 = 
			DateTimeFormatter.ofPattern( "dd.MM.yyyy (EE), HH:mm 'Uhr'" );
	
	/** Formatierungs-String für Format {@code YYYY-MM-dd_HH-mm}. */ 
	private static final DateTimeFormatter FORMATIERER2 = 
			DateTimeFormatter.ofPattern( "YYYY-MM-dd_HH-mm" );
	
	/**
	 * Methode gibt aktuelles Datum und Zeit für Verwendung auf PDF-Seite
	 * zurück. 
	 * 
	 * @return Datum und Zeit im deutschen Format.
	 *         Beispielausgabe:<pre>30.06.2025 (Mo.), 13:16 Uhr</pre> 
	 */
    public static String getDatumUndZeitString() {
 
    	final LocalDateTime jetzt = now();

    	return FORMATIERER1.format( jetzt );
    }


	/**
	 * Methode gibt aktuelles Datum und Zeit für Dateiname zurück. 
	 * 
	 * @return Datum und Zeit im deutschen Format.
	 *         Beispielausgabe:<pre>2025-07-03_08-05</pre> 
	 */    
    public static String getDatumUndZeitStringFuerDateiname() {

    	final LocalDateTime jetzt = now();

    	return FORMATIERER2.format( jetzt );
    }

}
