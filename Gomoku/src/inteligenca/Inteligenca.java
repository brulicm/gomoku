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
		this.globina = 2;
	}
	
	@SuppressWarnings("unused")
	public Koordinati izberiPotezo (Igra igra) {
		Koordinati poteza;
		
		// Neumni igralec.
		if (1 < 0) {
			poteza = neumniRacunalnik(igra);
		}
		
		// Minimax.
		if (1 < 0) {
			poteza = minimax(igra, this.globina, igra.naPotezi).poteza;
		}
		
		// Random minimax.
		if (1 < 2) {
			List<OcenjenaPoteza> ocenjenePoteze = randomMinimax(igra, this.globina);
			int i = RANDOM.nextInt(ocenjenePoteze.size());	
			poteza = ocenjenePoteze.get(i).poteza;
		}
		
		return poteza;
	}
	
	// Neumni igralec, izbere naključno potezo na plošči.
	public Koordinati neumniRacunalnik(Igra igra) {
		List<Koordinati> moznePoteze = igra.moznePoteze();
		int randomIndex = RANDOM.nextInt(moznePoteze.size());
		return moznePoteze.get(randomIndex);
	}
	
	// Vrne prvo izmed najboljših potez z vidika igralca jaz
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
	
	// Vrne seznam vseh najboljših potez z vidika igralca na potezi.
	public static List<OcenjenaPoteza> randomMinimax(Igra igra, int globina) {
		NajboljsePoteze najboljsePoteze = new NajboljsePoteze();
		
		List<Koordinati> moznePoteze = igra.moznePoteze();
		
		for (Koordinati p : moznePoteze) {
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
	
}
