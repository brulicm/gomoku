package logika;
import java.util.Arrays;

/**
 * Objekt predstavlja poljubno vrstico (dol�ine 5) na plo��i (navpi�no, vodoravno, po�evno).
 */

public class Vrstica {
	
	public int[] x;
	public int[] y;
	
	public Vrstica(int[] x, int[] y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Vrne reprezentacijo vrstice z nizom.
	 * @return Vrstica [x= , y= ]
	 */
	@Override
	public String toString() {
		return "Vrstica [x=" + Arrays.toString(this.x) + ", y=" + Arrays.toString(this.y)+ "]"; 
	}
	
}
