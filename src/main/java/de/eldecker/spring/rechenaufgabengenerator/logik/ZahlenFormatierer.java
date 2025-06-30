package de.eldecker.spring.rechenaufgabengenerator.logik;

import static java.util.Locale.GERMAN;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


/**
 * Hilfsklasse für Formatierung von int-Zahlen mit Tausendertrennpunkten.
 */
public class ZahlenFormatierer {

	private static DecimalFormat ZAHLENFORMATIERER = erzeugeDecimalFormat();
	
	
    /**
     * Zahlenformatierer erzeugen.
     * 
     * @return Zahlenformatierer, der Tausendertrennpunkte einfügt.
     */
    private static DecimalFormat erzeugeDecimalFormat() {
        
        final DecimalFormatSymbols symbole = new DecimalFormatSymbols( GERMAN );
        symbole.setGroupingSeparator( '.' );
        
        return new DecimalFormat( "#,###", symbole );
    }
    
    
    /**
     * Formatiert {@code zahl} mit Tausendertrennpunkten.
     * 
     * @param zahl Zahl, die formatiert werden soll
     * 
     * @return Formatierte Zahl, z.B. {@code 1.234}
     */
    public static String formatiereZahl( int zahl ) {
    	
    	return ZAHLENFORMATIERER.format( zahl );
    }
    
}
