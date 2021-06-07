package inteligenca;

import java.util.LinkedList;
import java.util.List;

import splosno.Koordinati;

public class BufferOcenjenihPotez {
	
	private int velikost;
	LinkedList<OcenjenaPoteza> buffer; // buffer -> medpomnilnik
	
	public BufferOcenjenihPotez(int velikost) {
		this.velikost = velikost;
		this.buffer = new LinkedList<OcenjenaPoteza>();
	}
	
	/**
	 * V buffer doda potezo, �e je njena ocena bolj�a ali enaka ocenam v seznamu.
	 * 
	 * @param ocenjenaPoteza poteza, ki jo zelimo dodati
	 */
	public void add(OcenjenaPoteza ocenjenaPoteza) {
		int i = 0;
		
		for (OcenjenaPoteza p : this.buffer) {
			// Gremo �ez buffer, dokler ne najdemo p, ki je slab�i od ocenjenaPoteza.
			if (ocenjenaPoteza.compareTo(p) != 1) ++i; // (ocenjenaPoteza <= p)
			else break; // (ocenjenaPoteza > p)
		}
		
		// V bufferju ho�emo najbolj�ih (velikost) potez, ki so urejene padajo�e.
		if (i < velikost) this.buffer.add(i, ocenjenaPoteza);
		if (this.buffer.size() > velikost) this.buffer.removeLast();
	}
	
	public List<Koordinati> listKoor() {
		List<Koordinati> rtr = new LinkedList<Koordinati>();
		
		for (OcenjenaPoteza op : this.buffer) {
			rtr.add(op.poteza);
		}
		
		return rtr;
	}
	
}
