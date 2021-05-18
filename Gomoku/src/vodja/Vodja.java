package vodja;

import java.util.Map;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import gui.Okno;
import inteligenca.Inteligenca;
import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Vodja {	
	
	public static Map<Igralec, VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
	
	public static int zakasnitev = 1; // Premor pred vsako računalnikovo potezo.
	
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca("smiselno uredi ime");
	
	public static void igramoNovoIgro() {
		igra = new Igra();
		igramo();
	}
	
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
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void>() {
			
			@Override
			protected Koordinati doInBackground() {
				long startTime = System.nanoTime();
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				long endTime = System.nanoTime();
				
				long executionTime = (endTime - startTime) / 1000000; // milisekunde
				
				// Če je poteza krajša od (zakasnitev), zaspimo do skupnega časa (zakasnitev).
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
				if (igra == zacetnaIgra) {
					igra.odigraj(poteza);
					igramo();
				}
			}
			
		};
		
		worker.execute();
	}
	
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo();
	}
	
}
