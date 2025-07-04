package de.eldecker.spring.rechenaufgabengenerator;

import static de.eldecker.spring.rechenaufgabengenerator.helferlein.DatumHelferlein.getDatumUndZeitStringFuerDateiname;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import de.eldecker.spring.rechenaufgabengenerator.logik.PdfExportException;
import de.eldecker.spring.rechenaufgabengenerator.logik.RechenaufgabenService;
import de.eldecker.spring.rechenaufgabengenerator.logik.RechenaufgabenSpec;


/**
 * {@code CommandLineRunner}-Bean, die nur bei aktivem "konsole"-Profil geladen wird.
 * Erzeugt eine PDF-Datei mit Rechenaufgaben und beendet dann die Anwendung.
 */
@Component
@Profile( "konsole" )
public class KonsoleRunner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger( KonsoleRunner.class );


    /** Bean mit Geschäftslogik zur Erzeugung von Rechenaufgaben im PDF-Format. */
    @Autowired
    private RechenaufgabenService _rechenaufgabenService;


    /**
     * Wird beim Start der Anwendung aufgerufen, wenn das "konsole"-Profil aktiv ist.
     * Erzeugt eine PDF-Datei mit Rechenaufgaben und beendet die Anwendung.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     */
    @Override
    public void run( String... args ) throws Exception {

        try {

            final RechenaufgabenSpec spec = new RechenaufgabenSpec(
            		1_050, 9_050,  // zahl1 im Bereich 1.050 bis 9.050
                      120,   900,  // zahl2 im Bereich   120 bis   900
                              72   // 72 Aufgaben => knapp 2 Seiten
            );

            final ByteArrayOutputStream pdfStream = 
            		_rechenaufgabenService.erzeugePdf( spec ); // throws PdfExportException

            final String dateiname = String.format( "Rechenaufgaben_%s.pdf",
                                                    getDatumUndZeitStringFuerDateiname() );

            try ( FileOutputStream fos = new FileOutputStream( dateiname ) ) { // throws FileNotFoundException
            	
            	final byte[] byteArray = pdfStream.toByteArray(); 
                fos.write( byteArray ); // throws IOException
            }

            LOG.info( "PDF-Datei erfolgreich erstellt: {}", dateiname );


        } catch ( PdfExportException ex ) {
        	
            LOG.error( "Fehler bei PDF-Erzeugung: {}", ex.getMessage(), ex );
            System.exit( 1 );
            
        } catch ( IOException ex ) {
        	
            LOG.error( "Fehler beim Schreiben der PDF-Datei: {}", ex.getMessage(), ex );
            System.exit( 2 );
        }
        
        System.exit( 0 ); // Anwendung explizit beenden
    }
    
}
