package inteligenca;

import splosno.Koordinati;

public class OcenjenaPoteza {
	
	// "Package private" fields (podobno kot protected).
	Koordinati poteza;
	int ocena;
	
	public OcenjenaPoteza (Koordinati poteza, int ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	// Primerja dve (ocenjeni) potezi med sabo.
	public int compareTo (OcenjenaPoteza p) {
		if (this.ocena < p.ocena) return -1;
		else if (this.ocena > p.ocena) return 1;
		else return 0;
	}
	
}
