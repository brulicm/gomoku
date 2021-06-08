package inteligenca;

import splosno.Koordinati;

/** 
 * Razred predstavlja potezo skupaj z njeno oceno.
 */
public class OcenjenaPoteza {
	
	// "Package private" fields (podobno kot protected).
	Koordinati poteza;
	int ocena;
	
	/**
	 * Konstruktor, objektu nastavi potezo in oceno.
	 */
	public OcenjenaPoteza (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	/**
	 * Primerja dve (ocenjeni) potezi med sabo. Vrne ustrezno število izmed -1, 0, 1.
	 * @return int
	 */
	public int compareTo (OcenjenaPoteza p) {
		if (this.ocena < p.ocena) return -1;
		else if (this.ocena > p.ocena) return 1;
		else return 0;
	}
	
}
