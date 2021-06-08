package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrstica;

/**
 * Razred vsebuje statično metodo, ki oceni pozicijo igralne plošče s številčno oceno.
 */
public class OceniPozicijo {
	
	protected static final int W = Integer.MAX_VALUE; // vrednost zmage
	protected static final int L = Integer.MIN_VALUE; // vrednost izgube
	protected static final int TIE = 0; // vrednost neodlo�ene igre
	
	/**
	 *  Ocena pozicije v igri. Metoda je klicana le, ko je igra �e V_TEKU.
	 * @param igra
	 * @param jaz
	 * @return ocena trenutne pozicije
	 */
	public static int oceniPozicijo(Igra igra, Igralec jaz) {
		switch (igra.stanjeIgre()) {
		case ZMAGA_WHITE: return (jaz == Igralec.WHITE ? W : L);
		case ZMAGA_BLACK: return (jaz == Igralec.BLACK ? W : L);
		case NEODLOCENO: return TIE;
		default:
			int ocena = 0;
			
			for (Vrstica v : Igra.VRSTICE) {
				ocena += oceniVrsto5(v, igra, jaz);
			}
			
			return ocena;
		}
	}
	
	/**
	 * Oceni posamezno vrsto z vidika igralca na potezi in oceno vrne.
	 * 
	 * @param vrsta v
	 * @param igra
	 * @param igralec jaz
	 * 
	 * @return int
	 */
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
		else if (countWHITE == 4 && jaz == Igralec.WHITE) return W / 50;
		else if (countWHITE == 4 && jaz == Igralec.BLACK) return L / 5;
		else if (countBLACK == 4 && jaz == Igralec.BLACK) return W / 50;
		else if (countBLACK == 4 && jaz == Igralec.WHITE) return L / 5;
		else if (countWHITE == 3 && jaz == Igralec.WHITE) return W / 5000;
		else if (countWHITE == 3 && jaz == Igralec.BLACK) return L / 500;
		else if (countBLACK == 3 && jaz == Igralec.BLACK) return W / 5000;
		else if (countBLACK == 3 && jaz == Igralec.WHITE) return L / 500;
		else if (countWHITE == 2 && jaz == Igralec.WHITE) return W / 500000;
		else if (countWHITE == 2 && jaz == Igralec.BLACK) return L / 50000;
		else if (countBLACK == 2 && jaz == Igralec.BLACK) return W / 500000;
		else if (countBLACK == 2 && jaz == Igralec.WHITE) return L / 50000;
		else if (jaz == Igralec.WHITE) return countWHITE - countBLACK;
		else return countBLACK - countWHITE; // (jaz == Igralec.BLACK)
	}
		
}
