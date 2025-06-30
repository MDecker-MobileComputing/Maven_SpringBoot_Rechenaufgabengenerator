package de.eldecker.spring.rechenaufgabengenerator.logik;

import static com.lowagie.text.Element.ALIGN_CENTER;
import static com.lowagie.text.Font.BOLD;
import static com.lowagie.text.FontFactory.HELVETICA;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Table;
import com.lowagie.text.Cell;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Klasse mit Geschäftslogik zur Erzeugung von Rechenaufgaben.
 */
@Service
public class RechenaufgabenService {
    
    private static Logger LOG = LoggerFactory.getLogger( RechenaufgabenService.class );
    
    /** Schriftart für Überschrift (groß+fett). */
    private static final Font FONT_TITEL = FontFactory.getFont( HELVETICA, 16, BOLD );

    /** Fette Schrift normaler Größe. */
    private static final Font FONT_FETT = FontFactory.getFont( HELVETICA, 12, BOLD );

    
    /**
     * Erzeugt ein PDF-Dokument mit Rechenaufgaben.
     * 
     * @param spec Spezifikation der Aufgaben: Zahlenbereich, Anzahl Aufgaben
     *  
     * @return ByteArrayOutputStream mit PDF-Dokument, das die Rechenaufgaben enthält
     * 
     * @throws PdfExportException wenn ein Fehler bei der PDF-Erzeugung auftritt
     */
    public ByteArrayOutputStream erzeugePdf( RechenaufgabenSpec spec ) throws PdfExportException {
        
        final Rechenaufgabe[] rechenaufgabenArray = erzeugeRechenaufgaben( spec );
        
        try {

            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final BufferedOutputStream  bos  = new BufferedOutputStream( baos );

            final Document document = new Document();
            final PdfWriter pdfWriter = PdfWriter.getInstance( document, bos );
            
            pdfWriter.setPageEvent( new SeitenzahlFooter() );

            metadatenSetzen( document, spec );

            document.open();

            inhaltSchreiben( document, rechenaufgabenArray );

            document.close();

            bos.flush();
            bos.close();

            LOG.info( "PDF mit Rechenaufgaben erzeugt: {}", spec ); 

            return baos;
        }
        catch ( IOException | DocumentException ex ) {

            final String fehlerText = "Fehler bei PDF-Erzeugung aufgetreten: " + ex.getMessage();

            LOG.error( fehlerText, ex );
            throw new PdfExportException( fehlerText , ex );
        }
    }
    
    
    /**
     * Rechenaufgaben in PDF schreiben.
     * 
     * @param document PDF-Dokument
     * 
     * @param nutzerName Name des Nutzers, für den Tagebucheinträge in PDF geschrieben werden sollen.
     * 
     * @throws DocumentException Fehler bei PDF-Erzeugung
     */
   private void inhaltSchreiben( Document document, Rechenaufgabe[] rechenaufgabenArray ) 
		   throws DocumentException {
       
       final Paragraph titelAbsatz = new Paragraph( "Rechenaufgaben (Addition/Subtraktion)", FONT_TITEL );
       titelAbsatz.setAlignment( ALIGN_CENTER );
       titelAbsatz.setSpacingAfter( 20 );
       document.add( titelAbsatz );
       
       // Tabelle mit 2 Spalten erstellen
       final Table tabelle = new Table( 2 );
       tabelle.setWidth( 100 );     // 100% der Seitenbreite
       tabelle.setBorderWidth( 0 ); // Keine sichtbaren Rahmen
       tabelle.setSpacing( 10 );    // Abstand zwischen Zellen
       
       for ( int i = 0; i < rechenaufgabenArray.length; i++ ) {
           
    	   final Paragraph absatzMitRechenaufgabe = 
    			   new Paragraph( rechenaufgabenArray[i].toString(), FONT_FETT );
    	   
           final Cell zelle = new Cell( absatzMitRechenaufgabe );
           zelle.setBorder( 0 ); // Keine Zellenrahmen
           
           tabelle.addCell( zelle );
           
           // Falls ungerade Anzahl von Aufgaben, leere Zelle hinzufügen
           if ( i == rechenaufgabenArray.length - 1 && rechenaufgabenArray.length % 2 != 0 ) {
               
               final Cell leereZelle = new Cell( new Paragraph( "", FONT_FETT ) );
               leereZelle.setBorder( 0 );
               tabelle.addCell( leereZelle );
           }
       }
       
       document.add( tabelle );
    }
   
    
    /**
     * Metadaten in PDF-Dokument setzen.
     * 
     * @param document PDF-Dokument, in dem Metadaten zu setzen sind
     * 
     * @param spec Spezifikation der zu erzeugenden Rechenaufgaben mit Zahlenbereichen
     *             und Anzahl der Aufgaben 
     */
    private void metadatenSetzen( Document document, RechenaufgabenSpec spec ) {
        
        document.addTitle   ( "Rechenaufgaben"                                    );
        document.addSubject ( "Rechenaufgaben Addition und Subtraktion"           );
        document.addCreator ( "Spring-Boot-Anwendung \"Rechenaufgabengenerator\"" );
        document.addKeywords( spec.toString()                                     );
    }
    
    
    /**
     * Eigentliche Erzeugung der Rechenaufgaben.
     * 
     * @param spec Spezifikation der zu erzeugenden Rechenaufgaben mit Zahlenbereichen
     *             und Anzahl der Aufgaben 
     * 
     * @return Array mit Rechenaufgaben
     */
    private Rechenaufgabe[] erzeugeRechenaufgaben( RechenaufgabenSpec spec ) {
        
        final Rechenaufgabe[] rechenaufgabenArray = new Rechenaufgabe[ spec.anzahl() ];
        
        for ( int i = 0; i < spec.anzahl(); i++ ) {
            
            rechenaufgabenArray[i] = new Rechenaufgabe( spec );
        }
        
        return rechenaufgabenArray;
    }
    
}
