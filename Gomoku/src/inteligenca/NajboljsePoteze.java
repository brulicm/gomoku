package inteligenca;

import java.util.List;
import java.util.LinkedList;

public class NajboljsePoteze {
	
	private LinkedList<OcenjenaPoteza> buffer;
	
	public NajboljsePoteze() {
		this.buffer = new LinkedList<OcenjenaPoteza>();
	}
	
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
	
	public List<OcenjenaPoteza> list() {
		return (List<OcenjenaPoteza>)buffer;
	}
	
}
