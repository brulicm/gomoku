package vodja;

import java.util.Map;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import gui.Okno;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

/**
 * Vodenje poteka igre.
 *
 */
public class Vodja {	
	
	public static Map<Igralec, VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
	
	public static int zakasnitev = 1; // Premor pred vsako raèunalnikovo potezo.
	
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca("inteligenca");
	
	/** 
	 * Ustvari novo igro in jo zažene.
	 */
	public static void igramoNovoIgro() {
		igra = new Igra();
		igramo();
	}
	
	/**
	 * Potek igre
	 */
	public static void igramo() {
		okno.osveziGUI();
		
		switch (igra.stanjeIgre()) {
		case ZMAGA_WHITE: return;
		case ZMAGA_BLACK: return;
		case NEODLOCENO: return;
		case V_TEKU: 
			Igralec igralec = igra.naPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			
			switch (vrstaNaPotezi) {
			case HUMAN: 
				clovekNaVrsti = true;
				break;
			case COMP:
				igrajRacunalnikovoPotezo();
				break;
			}
		}
	}
	
	/**
	 *  Odigra raèunalnikovo potezo in posodobi grafiko - nariše potezo.
	 *  Preden odigra poèaka.
	 */
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void>() {
			
			@Override
			protected Koordinati doInBackground() {
				long startTime = System.nanoTime();
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				long endTime = System.nanoTime();
				
				long executionTime = (endTime - startTime) / 1000000; // milisekunde
				
				// èe je poteza krajša od (zakasnitev), zaspimo do skupnega èasa (zakasnitev).
				if (executionTime < 1000*zakasnitev) {
					try {TimeUnit.MILLISECONDS.sleep(1000*zakasnitev - executionTime);} catch (Exception e) {};
				}
				System.out.println("" + executionTime);
				
				return poteza;
			}
			
			@Override
			protected void done() {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
				/*
				 *  Preveri èe uporabnik med izvajanjem ni spremenil igre, t. j. da ni v meniju izbral nove igre,
				 *  saj ne želimo odigrati poteze na stari igri.
				 */
				if (igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo();
				}
			}
			
		};
		
		worker.execute();
	}
	
	/**
	 * Odigra èlovekovo potezo, èe je možna in nadaljuje z igro.
	 * 
	 * @param poteza izbrana poteza 
	 */
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo();
	}
	
}
