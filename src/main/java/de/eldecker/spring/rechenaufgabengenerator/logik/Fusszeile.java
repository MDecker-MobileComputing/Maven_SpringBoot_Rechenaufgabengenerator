package de.eldecker.spring.rechenaufgabengenerator.logik;

import static de.eldecker.spring.rechenaufgabengenerator.helferlein.DatumHelferlein.getDatumUndZeitSring;

import static com.lowagie.text.Element.ALIGN_LEFT;
import static com.lowagie.text.Element.ALIGN_RIGHT;
import static com.lowagie.text.Font.NORMAL;
import static com.lowagie.text.FontFactory.HELVETICA;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Event-Handler für Erzeugen von Fußzeile.
 */
public class Fusszeile extends PdfPageEventHelper {

    private static final Font SCHRIFTART = FontFactory.getFont( HELVETICA, 10, NORMAL );
    
    private static final int ABSTAND_RAND_VERTIKAL = 15;


    /**
     * Event-Handler-Methode, wird aufgerufen, wenn eine Seite fertig ist
     */
    @Override
    public void onEndPage( PdfWriter writer, Document document ) {


    	addSeitenzahl(   writer, document );
    	addDatumUhrzeit( writer, document );
    }
    
    
    private void addSeitenzahl( PdfWriter writer, Document document ) {
    	
        final PdfContentByte cb = writer.getDirectContent();

        final String seitenzahlText = "Seite " + writer.getPageNumber();

        final float x1 = document.left();        // Linker Rand
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
    
    
    private void addDatumUhrzeit( PdfWriter writer, Document document ) {
    	
    	final PdfContentByte cb = writer.getDirectContent();
    	
        final String datumZeitString = getDatumUndZeitSring();

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
