package de.eldecker.spring.rechenaufgabengenerator.logik;

import static de.eldecker.spring.rechenaufgabengenerator.helferlein.DatumHelferlein.getDatumUndZeitString;
import static org.openpdf.text.Element.ALIGN_LEFT;
import static org.openpdf.text.Element.ALIGN_RIGHT;

import org.openpdf.text.Document;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.Phrase;
import org.openpdf.text.pdf.ColumnText;
import org.openpdf.text.pdf.PdfContentByte;
import org.openpdf.text.pdf.PdfPageEventHelper;
import org.openpdf.text.pdf.PdfWriter;


import static org.openpdf.text.FontFactory.HELVETICA_BOLD;


/**
 * Event-Handler für Erzeugen von Fußzeile mit Seitenzahl (links) und Datum+Uhrzeit (rechts).
 */
public class FusszeilenErzeuger extends PdfPageEventHelper {

    private static final Font SCHRIFTART = FontFactory.getFont( HELVETICA_BOLD, 10 );
    
    private static final int ABSTAND_RAND_VERTIKAL = 15;


    /**
     * Event-Handler-Methode, wird aufgerufen, wenn eine Seite fertig ist;
     * fügt Seitenzahl links und Datum+Uhrzeit rechts dazu.
     */
    @Override
    public void onEndPage( PdfWriter writer, Document document ) {

    	addSeitenzahl(   writer, document );
    	addDatumUhrzeit( writer, document );
    }
    
    
    /**
     * Seitenzahl in Fußzeile hinzufügen.
     */
    private void addSeitenzahl( PdfWriter writer, Document document ) {
    	
        final PdfContentByte cb = writer.getDirectContent();

        final String seitenzahlText = "Seite " + writer.getPageNumber();

        final float x1 = document.left();  // Linker Rand
        final float y  = document.bottom() - ABSTAND_RAND_VERTIKAL; // Unmittelbar unter dem Inhalt

        final Phrase seitenzahlPhrase = new Phrase( seitenzahlText, SCHRIFTART );

        ColumnText.showTextAligned(
                cb,
                ALIGN_LEFT,
                seitenzahlPhrase,
                x1, y,
                0
        );
    }
    

    /**
     * Datum+Uhrzeit in Fußzeile rechts hinzufügen.
     */
    private void addDatumUhrzeit( PdfWriter writer, Document document ) {
    	
    	final PdfContentByte cb = writer.getDirectContent();
    	
        final String datumZeitString = getDatumUndZeitString();

        final float x2 = document.right();
        final float y  = document.bottom() - ABSTAND_RAND_VERTIKAL; 
        
        final Phrase datumZeitPhrase = new Phrase( datumZeitString, SCHRIFTART );

        ColumnText.showTextAligned(
                cb,
                ALIGN_RIGHT,
                datumZeitPhrase,
                x2, y,
                0
        );
    }

}
