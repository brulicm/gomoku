package logika;

import java.util.LinkedList;
import java.util.List;

import inteligenca.Algoritem;
import splosno.Koordinati;

/**
 * Razred predstavlja eno igro. Vsebuje metode za igranje potez, razveljavljanje potez, pridobitev stanja,... 
 */
public class Igra {
	
	// Velikost igralne plošče N x N.
	public static int N = 15; 
	
	// Algoritem
	public static Algoritem algoritem = Algoritem.HITRI_MINIMAX;
	
	// Seznam vseh možnih vrstic dolžine 5 na plošči.
	public static final List<Vrstica> VRSTICE = new LinkedList<Vrstica>();
	
	// This is a static initialization block, "a static version of the constructor".
	// Constructors are run when the class is instantiated.
	// Static initialization blocks get run when the class gets loaded.
	static {
		int[][] smeri = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
		
		// Med VRSTICE dodamo vse možne vrstice dolžine 5 na plošči (objekte razreda Vrstica).
		for (int x = 0; x < N; ++x) {
			for (int y = 0; y < N; ++y) {
				for (int[] smer : smeri) {
					
					int dx = smer[0];
					int dy = smer[1];
					
					// if (cela vrstica znotraj tabele)
					if ((0 <= x + 4 * dx) && (x + 4 * dx < N) && (0 <= y + 4 * dy) && (y + 4 * dy < N)) {
						int[] vrsticaX = new int[5];
						int[] vrsticaY = new int[5];
						
						for (int k = 0; k < 5; ++k) {
							vrsticaX[k] = x + dx * k;
							vrsticaY[k] = y + dy * k;
						}
						
						VRSTICE.add(new Vrstica(vrsticaX, vrsticaY));
					}
				}
			}
		}
	}
	
	// Igralna plošča
	private Polje[][] plosca; 
	
	// Igralec na potezi
	public Igralec naPotezi; 
	
	// Odigrane poteze
	public LinkedList<Koordinati> odigranePoteze; 
	
	/**
	 * Konstruktor
	 * Na začetku so vsa polja EMPTY, na potezi je WHITE.
	 */
	public Igra() {
		this.plosca = new Polje[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				this.plosca[i][j] = Polje.EMPTY;
			}
		}
		this.naPotezi = Igralec.WHITE;
		this.odigranePoteze = new LinkedList<Koordinati>();
	}
	
	/**
	 * Konstruktor za kopijo igre.
	 */
	public Igra(Igra igra) {
		this.plosca = new Polje[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				this.plosca[i][j] = igra.plosca[i][j];
			}
		}
		
		this.naPotezi = igra.naPotezi;
		
		this.odigranePoteze = new LinkedList<Koordinati>();
		for (Koordinati p : igra.odigranePoteze) this.odigranePoteze.add(p);
	}
	
	/**
	 * Vrne igralno ploščo.
	 * @return plosca
	 */
	public Polje[][] getPlosca() {
		return this.plosca;
	}
	
	/**
	 * Vrne seznam koordinat vseh praznih polj - možnih potez - na plošči.
	 * @return seznam koordinat
	 */
	public List<Koordinati> moznePoteze() {
		List<Koordinati> rtr = new LinkedList<Koordinati>();
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (this.plosca[i][j] == Polje.EMPTY) {
					rtr.add(new Koordinati(i, j));
				}
			}
		}
		return rtr;
	}
	
	/**
	 * Vrne igralca, ki ima zasedeno celo vrstico (ta je podana), če ga ni, pa null.
	 * @param v
	 * @return WHITE/BLACK/null
	 */
	private Igralec lastnikVrstice(Vrstica v) {
		int countWhite = 0;
		int countBlack = 0;
		
		for (int i = 0; i < 5 && (countWhite == 0 || countBlack == 0); ++i) {
			switch (this.plosca[v.x[i]][v.y[i]]) {
			case WHITE: countWhite += 1; break; // Breaks switch, not for loop.
			case BLACK: countBlack += 1; break;
			case EMPTY: return null;
			}
		}
		
		if (countWhite == 5) return Igralec.WHITE;
		else if (countBlack == 5) return Igralec.BLACK;
		else return null;
	}
	
	/**
	 * Vrne zmagovalno vrsto, če je ni, pa null.
	 * @return zmagovalnaVrstica/null
	 */
	public Vrstica zmagovalnaVrstica() {
		for (Vrstica v : VRSTICE) {
			Igralec lastnik = lastnikVrstice(v);
			if (lastnik != null) return v;
		}
		return null;
	}
	
	/**
	 * Vrne trenutno stanje igre.
	 * @return Stanje
	 */
	public Stanje stanjeIgre() {
		Vrstica v = zmagovalnaVrstica();
		
		// if (imamo zmagovalca)
		if (v != null) {
			switch (plosca[v.x[0]][v.y[0]]) {
			case WHITE: return Stanje.ZMAGA_WHITE;
			case BLACK: return Stanje.ZMAGA_BLACK;
			case EMPTY: assert false; // shouldn't happen
			}
		}
		
		// Zmagovalca (še) ni. Preverimo, če je na plošči še kakšno prazno polje.
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (this.plosca[i][j] == Polje.EMPTY) return Stanje.V_TEKU;
			}
		}
		
		// Plošča je zapolnjena.
		return Stanje.NEODLOCENO;
	}
	
	/**
	 * Odigra potezo na koordinatah koordinati (igralec igra.naPotezi).
	 * @param koordinati
	 * @return boolean
	 */
	public boolean odigraj(Koordinati koordinati) {
		
		// if (polje na koordinatah je prazno)
		if (this.plosca[koordinati.getX()][koordinati.getY()] == Polje.EMPTY) {
			// Ustrezno zapolnimo polje na plošči na koordinatah koor.
			this.plosca[koordinati.getX()][koordinati.getY()] = this.naPotezi.getPolje();
			// Potezo dodamo v odigranePoteze.
			this.odigranePoteze.add(koordinati);
			// Spremenimo igralca, ki je na potezi.
			this.naPotezi = this.naPotezi.nasprotnik();
			
			return true;
		}
		
		// Polje na koordinatah je že zasedeno.
		return false;
	}
	
	/**
	 * Razveljavi nazadnje odigrano potezo.
	 * @return boolean
	 */
	public boolean razveljaviPotezo() {
		// if (lahko razveljavimo potezo)
		if ((this.odigranePoteze.size() >= 1) && (this.stanjeIgre() == Stanje.V_TEKU)) {
			// Odstranimo zadnjo potezo in jo shranimo v spremenljivko.
			Koordinati zadnjaPoteza = this.odigranePoteze.removeLast();
			// Ustrezno polje na plošči nastavimo na EMPTY.
			this.plosca[zadnjaPoteza.getX()][zadnjaPoteza.getY()] = Polje.EMPTY;
			// Popravimo igralca na potezi.
			this.naPotezi = this.naPotezi.nasprotnik();
			return true;
		}
		
		// Nobena poteza še ni bila odigrana.
		return false;
	}
		
}
