package de.eldecker.spring.rechenaufgabengenerator.web;

import static de.eldecker.spring.rechenaufgabengenerator.helferlein.DatumHelferlein.getDatumUndZeitStringFuerDateiname;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.eldecker.spring.rechenaufgabengenerator.logik.PdfExportException;
import de.eldecker.spring.rechenaufgabengenerator.logik.RechenaufgabenService;
import de.eldecker.spring.rechenaufgabengenerator.logik.RechenaufgabenSpec;


/**
 * REST-Controller für Bereitstellung von Rechenaufgaben als PDF-Datei.
 */
@Controller
@RequestMapping( "/api/v1" )
public class RechenaufgabeRestController {

    /** Bean mit Geschäftslogik zur Erzeugung von Rechenaufgaben im PDF-Format. */
    @Autowired
    private RechenaufgabenService _rechenaufgabenService;
    
    
    /**
     * REST-Endpunkt, der ein PDF-Dokument mit Rechenaufgaben generiert.
     * 
     * @param min1 Untergrenze für erste Zahl
     * 
     * @param max1 Obergrenze für erste Zahl
     * 
     * @param min2 Untergrenze für zweite Zahl
     * 
     * @param max2 Obergrenze für zweite Zahl
     * 
     * @param anzahl Anzahl der Rechenaufgaben, die erzeugt werden sollen
     * 
     * @return HTTP-Response mit PDF-Dokument als Body.
     * 
     * @throws PdfExportException Fehler bei PDF-Erzeugung aufgetreten
     */        
    @GetMapping( value = "/rechenaufgaben", produces = APPLICATION_PDF_VALUE )
    public ResponseEntity<InputStreamResource> generatePDF( 
	                                                @RequestParam( "zahl1min" ) int zahl1min,
	                                                @RequestParam( "zahl1max" ) int zahl1max,
	                                                @RequestParam( "zahl2min" ) int zahl2min,
	                                                @RequestParam( "zahl2max" ) int zahl2max,
	                                                @RequestParam( "anzahl"   ) int anzahl    
                                               ) 
                                          throws PdfExportException {
    	final RechenaufgabenSpec spec = 
    			new RechenaufgabenSpec( zahl1min, zahl1max, zahl2min, zahl2max, anzahl );  
    	
        final ByteArrayOutputStream pdfStream = _rechenaufgabenService.erzeugePdf( spec );         			 
        					                                 
        final ByteArrayInputStream  pdfInputStream = 
        								new ByteArrayInputStream( pdfStream.toByteArray() );
        final InputStreamResource   inputStreamResource = 
        								new InputStreamResource( pdfInputStream );
        
        final HttpHeaders httpHeaders = erzeugeHeader();
        
        return ResponseEntity.ok()
                             .headers( httpHeaders )
                             .contentType( APPLICATION_PDF )
                             .body( inputStreamResource );
    }
    
    
    /**
     * Erzeugt HTTP-Header "Content-Disposition", mit dem der Dateiname der PDF-Datei
     * beim Download bestimmt wird. Wegen Attribut "inline" wird die PDF-Datei zuerst
     * im Browser angezeigt, der Nutzer kann sie dann bei Gefallen herunterladen,
     * wobei der mit {@code filename} gesetzte Dateiname vorgeschlagen würde.
     * Wenn die PDF-Datei immer sofort heruntergeladen werden soll, dann ist
     * statt "inline" der Wert "attachment" zu verwenden.
     * <br><br>
     * 
     * Beispielwert für Header "Content-Disposition":
     * <pre>
     * inline; filename=Rechenaufgaben_2025-07-03_08-07.pdf
     * </pre>
     * Die lange Zahl am Ende des Dateinamens repräsentiert Datum/Uhrzeit in
     * Form der Millisekunden seit dem 1. Januar 1970.
     *
     * @return Header-Objekt für {@code ResponseEntity}
     */        
    private HttpHeaders erzeugeHeader() {

        final String contentDispositionHeader = 
                        String.format( "inline; filename=Rechenaufgaben_%s.pdf",
                        		       getDatumUndZeitStringFuerDateiname() );
        
        final HttpHeaders headers = new HttpHeaders();
        headers.add( CONTENT_DISPOSITION, contentDispositionHeader );

        return headers;
    }

}
