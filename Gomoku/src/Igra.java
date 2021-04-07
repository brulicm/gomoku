import java.util.LinkedList;
import java.util.List;

public class Igra {
	
	// Velikost igralne plošče N x N.
	public static final int N = 15; // 19
	// Seznam vseh možnih vrstic dolžine 5 na plošči.
	private static final List<Vrstica> VRSTICE = new LinkedList<Vrstica>();
	
	// (source - Stack Overflow)
	// This is a static initialization block, "a static version of the constructor".
	// Constructors are run when the class is instantiated.
	// Static initialization blocks get run when the class gets loaded.
	static {
		// Med VRSTICE dodamo vse možne vrstice dolžine 5 na plošči (objekte razreda Vrstica).
		int[][] smeri = {{1, 0}, {0, 1}, {1, 1}, {1, -1}};
		
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
	
	private Polje[][] plosca;
	public Igralec naPotezi;
	
	/**
	 * Konstruktor
	 * Na začetku so vsa polja EMPTY, na potezi je WHITE.
	 */
	public Igra() {
		this.plosca = new Polje[N][N];
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				plosca[i][j] = Polje.EMPTY;
			}
		}
		this.naPotezi = Igralec.WHITE;
	}
	
	/**
	 * Vrne seznam koordinat vseh praznih polj - možnih potez - na plošči.
	 * @return seznam Koordinat
	 */
	public List<Koordinate> moznePoteze() {
		// Spremenljivko, ki jo fukncija vrne, ponavadi poimenujem rtr, če se ne spomnem boljšega imena.
		LinkedList<Koordinate> rtr = new LinkedList<Koordinate>();
		
		for (int i = 0; i < N; ++i) {
			for (int j = 0; j < N; ++j) {
				if (this.plosca[i][j] == Polje.EMPTY) {
					rtr.add(new Koordinate(i, j));
				}
			}
		}
		return rtr;
	}
	
}
