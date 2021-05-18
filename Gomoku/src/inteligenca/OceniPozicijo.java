package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrstica;

public class OceniPozicijo {
	
	protected static final int W = Integer.MAX_VALUE;
	protected static final int L = Integer.MIN_VALUE;
	protected static final int TIE = 0;
	
	// Metoda je klicana le, ko je igra še V_TEKU.
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		int ocena = 0;
		
		for (Vrstica v : Igra.VRSTICE) {
			ocena += oceniVrsto5(v, igra, jaz);
		}
		
		for (Vrstica v : Igra.VRSTICE_6) {
			ocena += oceniVrsto6(v, igra, jaz);
		}
		
		return ocena;	
	}
	
	// Metoda je klicana le, ko je igra še V_TEKU.
	public static int oceniVrsto5(Vrstica v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		
		int countWHITE = 0;
		int countBLACK = 0;
		
		for (int k = 0; k < 5 && (countWHITE == 0 || countBLACK == 0); ++k) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case WHITE: countWHITE += 1; break;
			case BLACK: countBLACK += 1; break;
			case EMPTY: break;
			}
		}
		
		if (countWHITE > 0 && countBLACK > 0) return 0;
		else if (countWHITE == 4 && jaz == Igralec.WHITE) return W / 100;
		else if (countWHITE == 4 && jaz == Igralec.BLACK) return L / 50;
		else if (countBLACK == 4 && jaz == Igralec.BLACK) return W / 100;
		else if (countBLACK == 4 && jaz == Igralec.WHITE) return L / 50;
		else if (countWHITE == 3 && jaz == Igralec.WHITE) return W / 1000;
		else if (countWHITE == 3 && jaz == Igralec.BLACK) return L / 500;
		else if (countBLACK == 3 && jaz == Igralec.BLACK) return W / 1000;
		else if (countBLACK == 3 && jaz == Igralec.WHITE) return L / 500;
		else if (jaz == Igralec.WHITE) return countWHITE - countBLACK;
		else return countBLACK - countWHITE; // jaz == Igralec.BLACK
	}
	
	// Metoda je klicana le, ko je igra še V_TEKU.
	// Vrne visoko oceno le, če imamo vrstico oblike [E, B/W, B/W, B/W, B/W, E], sicer 0.
	public static int oceniVrsto6(Vrstica v, Igra igra, Igralec jaz) {
		Polje[][] plosca = igra.getPlosca();
		
		if (plosca[v.x[0]][v.y[0]] != Polje.EMPTY || plosca[v.x[5]][v.y[5]] != Polje.EMPTY) return 0;
		
		int countWHITE = 0;
		int countBLACK = 0;
		
		for (int k = 1; k < 5 && (countWHITE == 0 || countBLACK == 0); ++k) {
			switch (plosca[v.x[k]][v.y[k]]) {
			case WHITE: countWHITE += 1; break;
			case BLACK: countBLACK += 1; break;
			case EMPTY: return 0;
			}
		}
		
		if (countWHITE == 4) {
			if (jaz == Igralec.WHITE) return W / 10;
			else if (jaz == Igralec.BLACK) return L / 5;
			else assert false; return 0;
		}
		else if (countBLACK == 4) {
			if (jaz == Igralec.BLACK) return W / 10;
			else if (jaz == Igralec.WHITE) return L / 5;
			else assert false; return 0;
		}
		else return 0;
	}
	
}
