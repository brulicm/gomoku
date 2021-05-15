package vodja;

import java.util.Random;
import java.util.Map;
import java.util.List;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import gui.Okno;
import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Vodja {	
	
	public static Map<Igralec,VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
	
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
	
	private static Random random = new Random();
	
	public static int zakasnitev = 1; // Premor pred vsako računalnikovo potezo.
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetnaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void>() {
			
			@Override
			protected Koordinati doInBackground() {
				try {TimeUnit.SECONDS.sleep(zakasnitev);} catch (Exception e) {};
				List<Koordinati> moznePoteze = igra.moznePoteze();
				int randomIndex = random.nextInt(moznePoteze.size());
				return moznePoteze.get(randomIndex);
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
