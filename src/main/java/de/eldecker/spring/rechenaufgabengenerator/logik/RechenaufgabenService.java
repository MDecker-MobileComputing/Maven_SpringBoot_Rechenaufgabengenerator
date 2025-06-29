package de.eldecker.spring.rechenaufgabengenerator.logik;

import org.springframework.stereotype.Service;


/**
 * Klasse mit Geschäftslogik zur Erzeugung von Rechenaufgaben.
 */
@Service
public class RechenaufgabenService {

    public Rechenaufgabe[] erzeugeRechenaufgaben( int min1, int max1, int min2, int max2, 
                                                  int anzahl ) {
        
        final Rechenaufgabe[] rechenaufgabenArray = new Rechenaufgabe[ anzahl ];
        
        for (int i = 0; i < anzahl; i++) {
            
            rechenaufgabenArray[i] = new Rechenaufgabe( min1, max1, min2, max2 );
        }
        
        return rechenaufgabenArray;
    }
    
}
