package logika;
import java.util.Arrays;

/**
 * Razred predstavlja poljubno vrstico (dolžine 5) na igralni plošči (navpično, vodoravno, poševno).
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
