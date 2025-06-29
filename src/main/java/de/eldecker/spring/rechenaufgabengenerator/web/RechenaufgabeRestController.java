package de.eldecker.spring.rechenaufgabengenerator.web;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import org.springframework.http.HttpHeaders;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping( "/api/v1" )
public class RechenaufgabeRestController {

    @GetMapping( value = "/pdf", produces = APPLICATION_PDF_VALUE )
    public ResponseEntity<InputStreamResource> generatePDF( 
                                                @RequestParam("zahl1min") int zahl1min,
                                                @RequestParam("zahl1max") int zahl1max,
                                                @RequestParam("zahl2min") int zahl2min,
                                                @RequestParam("zahl2max") int zahl2max,
                                                @RequestParam("anzahl"  ) int anzahl ) {

        
        
        return null;
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
