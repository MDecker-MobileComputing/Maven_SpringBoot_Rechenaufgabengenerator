package de.eldecker.spring.rechenaufgabengenerator.logik;


/**
 * Eigene Exception-Klasse.
 */
@SuppressWarnings( "serial" )
public class PdfExportException extends Exception {

    public PdfExportException( String fehlerbeschreibung ) {

        super( fehlerbeschreibung );
    }


    public PdfExportException( String fehlerbeschreibung, Exception ex ) {

        super( fehlerbeschreibung, ex );
    }

}
