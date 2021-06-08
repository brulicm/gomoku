package inteligenca;

import java.util.List;
import java.util.LinkedList;

/**
 * Razred se uporablja pri algoritmu naključni minimax. Vsebuje seznam vseh najboljših možnih potez.
 */
public class NajboljsePoteze {
	
	// V buffer se shranjujejo vse najboljše poteze.
	private LinkedList<OcenjenaPoteza> buffer;
	
	/**
	 * Konstruktor
	 */
	public NajboljsePoteze() {
		this.buffer = new LinkedList<OcenjenaPoteza>();
	}
	
	/**
	 * Kot parameter dobi ocenjeno potezo. Preveri, kako se poteza primerja s potezami v bufferju.
	 * Ustrezno jo v buffer doda ali ne.
	 * 
	 * @param ocenjenaPoteza
	 */
	public void addIfBest(OcenjenaPoteza ocenjenaPoteza) {
		if (buffer.isEmpty()) buffer.add(ocenjenaPoteza);
		else {
			OcenjenaPoteza p = buffer.getFirst();
			
			switch (ocenjenaPoteza.compareTo(p)) {
			case 1: // ocenjenaPoteza > p
				buffer.clear();
			case 0: // ocenjenaPoteza >= p
				buffer.add(ocenjenaPoteza);
			}
		}
	}
	
	/**
	 * Vrne buffer kot List (namesto LinkedList).
	 * 
	 * @return buffer
	 */
	public List<OcenjenaPoteza> list() {
		return (List<OcenjenaPoteza>)buffer;
	}
	
}
