package de.eldecker.spring.rechenaufgabengenerator.logik;

import static de.eldecker.spring.rechenaufgabengenerator.logik.ZahlenFormatierer.formatiereZahl;

import java.util.Random;


/**
 * Ein Objekt dieser Klasse repräsentiert eine einzelne Rechenaufgabe.
 */
public class Rechenaufgabe {

    /** Zufallsgenerator. */
    private static final Random ZUFALL = new Random();
    
    /**
     * Klassenvariable für abwechselnd Plus/Minus.
     */
    private static boolean sIstPlus = false;
        
    
    /** Operand 1. */
    private int _zahl1;
    
    /** Operand 2.*/ 
    private int _zahl2;
    
    /** Ergebnis der Addition/Subtraktion für Musterlösung. */
    private int _ergebnis;
    
    /** Wenn {@code true}, dann Addition, sonst Subtraktion. */
    private boolean _istAddition;
       
    
    /**
     * Neue Rechenaufgabe erzeugen.
     * 
     * @param spec Spezifikation Rechenaufgabe mit Wertebereiche der Zahlen
     */
    public Rechenaufgabe( RechenaufgabenSpec spec ) {
        
        _zahl1 = getZufallszahlNotMod10( spec.zahl1min(), spec.zahl1max() );
        _zahl2 = getZufallszahlNotMod10( spec.zahl2min(), spec.zahl2max() );
       
        _istAddition = getPlusMinusAbwechselnd();
        
        if ( _istAddition ) {
            
            _ergebnis = _zahl1 + _zahl2;
            
        } else {
            
            _ergebnis = _zahl1 - _zahl2;
        }
    }
    
        
    
    /**
     * Methode erzeugt ungerade Zufallszahlen in bestimmten Bereich,
     * die aber nicht ein Vielfaches von 10 ist.
     * 
     * @param min Untere Grenze
     * 
     * @param max Obere Grenze
     * 
     * @return Zufallszahl (evtl. {@code max+1}, damit nicht Vielfaches von 10)
     */
    private static int getZufallszahlNotMod10( int min, int max ) {
        
        final int delta = max - min;
        
        int zahl = min + ZUFALL.nextInt( delta );

        if ( zahl % 10 == 0 ) { zahl++; }
        
        return zahl;
    }
    
    
    /**
     * Methode gibt abwechselnd Plus (true) oder Minus (false) zurück.
     * 
     * @return {@code true} für Plus, {@code false} für Minus
     */
    public static boolean getPlusMinusAbwechselnd() {
        
        sIstPlus = !sIstPlus;
        
        return sIstPlus;
    }
    
    
    /**
     * Operator der Rechenaufgabe (Plus oder Minus) als String.
     * 
     * @return "+" oder "-"
     */
    private char operatorAlsString() {
        
        return _istAddition ? '+' : '-';
    }
    
    
    /**
     * Gibt Aufgabe ohne Ergebnis zurück.
     * 
     * @return Beispiel: "102 - 5 = "
     */
    @Override
    public String toString() {
        
        final String zahl1str = formatiereZahl( _zahl1 );
        final String zahl2str = formatiereZahl( _zahl2 );
        
        return String.format( "%s %s %s = ", 
                              zahl1str, operatorAlsString(), zahl2str );
    }
    
    
    /**
     * Gibt Aufgabe mit Ergebnis für Musterlösung zurück.
     * 
     * @return Beispiel: "1.102 - 5 = 1.097"
     */
    public String toStringMitErgebnis() {
        
        final String zahl1str = formatiereZahl( _zahl1    );
        final String zahl2str = formatiereZahl( _zahl2    );
        final String ergebStr = formatiereZahl( _ergebnis );        
        
        return String.format( "%s %s %s = %s", 
                              zahl1str, operatorAlsString(), zahl2str, 
                              ergebStr );        
    }
    
}
