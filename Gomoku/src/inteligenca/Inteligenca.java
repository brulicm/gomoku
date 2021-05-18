package inteligenca;

import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Inteligenca extends splosno.KdoIgra {
	
	private static final Random RANDOM = new Random();
	
	private int globina;
	
	public Inteligenca(String ime) {
		super(ime);
		this.globina = 6;
	}
	
	public Koordinati izberiPotezo (Igra igra) {
		Koordinati poteza = null;
		
		int choice = 4;
		
		// Neumni igralec.
		if (choice == 0) {
			poteza = neumniRacunalnik(igra);
		}
		
		// Minimax.
		else if (choice == 1) {
			poteza = minimax(igra, this.globina, igra.naPotezi).poteza;
		}
		
		// Random minimax.
		else if (choice == 2) {
			List<OcenjenaPoteza> ocenjenePoteze = randomMinimax(igra, this.globina);
			int i = RANDOM.nextInt(ocenjenePoteze.size());	
			poteza = ocenjenePoteze.get(i).poteza;
		}
		
		// Alpha-Beta prunning na algoritmu Minimax.
		else if (choice == 3) {
			poteza = alphaBeta(igra, this.globina, OceniPozicijo.L, OceniPozicijo.W, igra.naPotezi).poteza;
		}
		
		// Hitri minimax.
		else if (choice == 4) {
			poteza = hitriMinimax(igra, this.globina, igra.naPotezi).poteza;
		}
		
		assert poteza != null;
		return poteza;
	}
	
	// Neumni igralec, izbere naključno potezo na plošči.
	public Koordinati neumniRacunalnik(Igra igra) {
		List<Koordinati> moznePoteze = igra.moznePoteze();
		int randomIndex = RANDOM.nextInt(moznePoteze.size());
		return moznePoteze.get(randomIndex);
	}
	
	// Vrne prvo izmed najboljših potez z vidika igralca jaz (klasični minimax algoritem).
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		
		for (Koordinati p : igra.moznePoteze()) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			
			int ocena;
			
			switch (kopijaIgre.stanjeIgre()) {
			case ZMAGA_WHITE: ocena = (jaz == Igralec.WHITE ? OceniPozicijo.W : OceniPozicijo.L); break;
			case ZMAGA_BLACK: ocena = (jaz == Igralec.BLACK ? OceniPozicijo.W : OceniPozicijo.L); break;
			case NEODLOCENO: ocena = OceniPozicijo.TIE; break;
			default:
				if (globina == 1) {
					ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				} else { // globina > 1
					ocena = minimax(kopijaIgre, globina - 1, jaz).ocena;
				}
			}
			
			if (najboljsaPoteza == null
					|| jaz == igra.naPotezi && ocena > najboljsaPoteza.ocena
					|| jaz != igra.naPotezi && ocena < najboljsaPoteza.ocena)
				najboljsaPoteza = new OcenjenaPoteza(p, ocena);
		}
		
		return najboljsaPoteza;
	}
	
	// Vrne seznam vseh najboljših potez z vidika igralca na potezi (random minimax algoritem).
	public static List<OcenjenaPoteza> randomMinimax(Igra igra, int globina) {
		NajboljsePoteze najboljsePoteze = new NajboljsePoteze();
		
		for (Koordinati p : igra.moznePoteze()) {
			Igra kopijaIgre = new Igra(igra); 
			kopijaIgre.odigraj(p);
			
			int ocena;
			
			switch (kopijaIgre.stanjeIgre()) {
			case ZMAGA_WHITE: ocena = OceniPozicijo.W; break;
			case ZMAGA_BLACK: ocena = OceniPozicijo.W; break;
			case NEODLOCENO: ocena = OceniPozicijo.TIE; break;
			default:
				if (globina == 1) ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, igra.naPotezi);
				else ocena = -randomMinimax(kopijaIgre, globina - 1).get(0).ocena;
			}
			
			najboljsePoteze.addIfBest(new OcenjenaPoteza(p, ocena));			
		}
		
		return najboljsePoteze.list();
	}
	
	// Vrne prvo izmed najboljših potez z vidika igralca jaz (klasični minimax algoritem z alpha-beta obrezovanjem).
	public static OcenjenaPoteza alphaBeta(Igra igra, int globina, int alpha, int beta, Igralec jaz) {
		int ocena;
		// Računalnik začne z začetno oceno L, in jo nato maksimizira.
		// Nasprotnik začne z začento oceno W, in jo nato minimizira.
		if (igra.naPotezi == jaz) {ocena = OceniPozicijo.L;} else {ocena = OceniPozicijo.W;}
		
		List<Koordinati> moznePoteze = igra.moznePoteze();
		Koordinati kandidat = moznePoteze.get(0);
		
		for (Koordinati p : moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			
			int ocenaP;
			
			switch (kopijaIgre.stanjeIgre()) {
			case ZMAGA_WHITE: ocenaP = (jaz == Igralec.WHITE ? OceniPozicijo.W : OceniPozicijo.L); break;
			case ZMAGA_BLACK: ocenaP = (jaz == Igralec.BLACK ? OceniPozicijo.W : OceniPozicijo.L); break;
			case NEODLOCENO: ocenaP = OceniPozicijo.TIE; break;
			default:
				if (globina == 1) ocenaP = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
				else ocenaP = alphaBeta(kopijaIgre, globina - 1, alpha, beta, jaz).ocena;
			}
			
			if (igra.naPotezi == jaz) { // Maksimiramo oceno.
				if (ocenaP > ocena) {
					ocena = ocenaP;
					kandidat = p;
					alpha = Math.max(alpha, ocena);
				}
			} else { // Minimiziramo oceno.
				if (ocenaP < ocena) {
					ocena = ocenaP;
					kandidat = p;
					beta = Math.min(beta, ocena);				
				}
			}
			
			if (alpha >= beta) // Gremo iz zanke, saj so ostale poteze slabše.
				return new OcenjenaPoteza(kandidat, ocena);
		}
		
		return new OcenjenaPoteza(kandidat, ocena);
	}
	
	// Deluje kot minimax, a veliko hitreje, ker na vsakem koraku pogleda le najboljših (globina) vej drevesa.
	public OcenjenaPoteza hitriMinimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		
		List<Koordinati> moznePoteze = igra.moznePoteze();
		if (moznePoteze.size() < globina) globina = moznePoteze.size();
		
		BufferOcenjenihPotez izbranePoteze = new BufferOcenjenihPotez(globina);
		
		// Izdelamo izbranePoteze, to je najboljših (globina) potez izmed moznePoteze.
		for (Koordinati p : moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			
			int ocena = OceniPozicijo.oceniPozicijo(kopijaIgre, jaz);
			
			OcenjenaPoteza op = new OcenjenaPoteza(p, ocena);
			
			// Potezo op dodamo med izbranePoteze, a le če je med najboljših (globina) potez. Za to poskrbi metoda add.
			izbranePoteze.add(op);
		}
		
		// Pogleda poddrevesa, vendar le za izbranePoteze. Tako se velikost drevesa močno zmanjša.
		for (OcenjenaPoteza op : izbranePoteze.buffer) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(op.poteza);
			
			int ocena1;
			
			if (globina == 1) {
				ocena1 = op.ocena;
			} else { // globina > 1
				ocena1 = hitriMinimax(kopijaIgre, globina - 1, jaz).ocena;
			}
			
			if (najboljsaPoteza == null
					|| jaz == igra.naPotezi && ocena1 > najboljsaPoteza.ocena
					|| jaz != igra.naPotezi && ocena1 < najboljsaPoteza.ocena)
				najboljsaPoteza = op;
		}
		
		return najboljsaPoteza;
	}
	
}
