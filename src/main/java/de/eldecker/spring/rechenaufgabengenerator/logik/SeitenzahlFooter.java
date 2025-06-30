package de.eldecker.spring.rechenaufgabengenerator.logik;

import static com.lowagie.text.Element.ALIGN_LEFT;
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
public class SeitenzahlFooter extends PdfPageEventHelper {

    private static final Font SCHRIFTART = FontFactory.getFont( HELVETICA, 10, NORMAL );

    
    @Override
    public void onEndPage( PdfWriter writer, Document document ) {
    	
        PdfContentByte cb = writer.getDirectContent();
        
        final String text = "Seite " + writer.getPageNumber();
        final float x = document.left(); // Linker Rand
        final float y = document.bottom() - 15; // Unmittelbar unter dem Inhalt

        
        final Phrase phrase = new Phrase( text, SCHRIFTART );
        
        ColumnText.showTextAligned(
                cb,
                ALIGN_LEFT,
                phrase,
                x,
                y,
                0
        );
    }
	
}
