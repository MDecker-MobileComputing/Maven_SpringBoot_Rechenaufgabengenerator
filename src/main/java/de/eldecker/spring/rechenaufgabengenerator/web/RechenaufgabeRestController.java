package de.eldecker.spring.rechenaufgabengenerator.web;

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


/**
 * REST-Controller für Bereitstellung von Rechenaufgaben als PDF-Datei.
 */
@Controller
@RequestMapping( "/api/v1" )
public class RechenaufgabeRestController {

    @Autowired
    private RechenaufgabenService _rechenaufgabenService;
    
    
    /**
     * REST-Endpunkt, der ein PDF-Dokument mit Rechenaufgaben generiert.
     * 
     * @param min1 Min-Wert für erste Zahl
     * 
     * @param max1 Max-Wert für erste Zahl
     * 
     * @param min2 Min-Wert für zweite Zahl
     * 
     * @param max2 Max-Wert für zweite Zahl
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
                                                @RequestParam( "anzahl"   ) int anzahl ) 
                                          throws PdfExportException {

        final ByteArrayOutputStream pdfStream           = _rechenaufgabenService.erzeugePdf( zahl1min, zahl1max, zahl2min, zahl2max, anzahl );
        final ByteArrayInputStream  pdfInputStream      = new ByteArrayInputStream( pdfStream.toByteArray() );
        final InputStreamResource   inputStreamResource = new InputStreamResource( pdfInputStream );
        
        final HttpHeaders httpHeaders = erzeugeHeader();
        
        return ResponseEntity.ok()
                             .headers( httpHeaders )
                             .contentType( APPLICATION_PDF )
                             .body( inputStreamResource );
    }
    
    
    /**
     * Erzeugt HTTP-Header "Content-Disposition", laut dem der Browser die Datei herunterladen soll
     * (und nicht versuchen soll, sie anzuzeigen). Dieser Header enthält auch einen Vorschlag für den
     * Dateinamen.
     *
     * @return Header-Objekt für {@code ResponseEntity}
     */
    private HttpHeaders erzeugeHeader() {

        final String contentDispositionHeader = 
                        String.format( "attachment; filename=Rechenaufgaben_%d.pdf",
                                       System.currentTimeMillis() );
        
        final HttpHeaders headers = new HttpHeaders();
        headers.add( CONTENT_DISPOSITION, contentDispositionHeader );

        return headers;
    }

}
