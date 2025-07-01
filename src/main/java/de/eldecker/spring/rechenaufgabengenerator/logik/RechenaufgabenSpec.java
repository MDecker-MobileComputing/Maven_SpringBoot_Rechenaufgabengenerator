package de.eldecker.spring.rechenaufgabengenerator.logik;

import static de.eldecker.spring.rechenaufgabengenerator.helferlein.ZahlenFormatierer.formatiereZahl;
import static java.lang.String.format;


/**
 * Spezifikation der zu erzeugenden Rechenaufgaben mit Zahlenbereichen
 * und Anzahl der Aufgaben.
 * 
 * @param zahl1min Untergrenze für erste Zahl
 * 
 * @param zahl1max Obergrenze für erste Zahl
 * 
 * @param zahl2min Untergrenze für zweite Zahl
 * 
 * @param zahl2max Obergrenze für zweite Zahl
 * 
 * @param anzahl Anzahl der Rechenaufgaben, die erzeugt werden sollen 
 */
public record RechenaufgabenSpec( int zahl1min,
		                          int zahl1max,
		                          int zahl2min,
		                          int zahl2max,
		                          int anzahl
		                         ) {
	
	/**
	 * String-Repräsentation des Objekts. 
	 * Beispielausgabe:
	 * <pre>
	 * 36 Rechenaufgaben mit zahl1 aus Bereich 1.050-9.000 und zahl2 aus Bereich 120-900.
	 * </pre>
	 * 
	 * @return String mit Anzahl Aufgaben und Wertebereichen         
	 */
	
	
	@Override
	public String toString() {
	
		return format(
					"%s Rechenaufgaben mit zahl1 aus Bereich %s-%s und zahl2 aus Bereich %s-%s.",
					formatiereZahl( anzahl   ),
					formatiereZahl( zahl1min ),
					formatiereZahl( zahl1max ),
					formatiereZahl( zahl2min ),
					formatiereZahl( zahl2max ) 
			);
	}
	
}
