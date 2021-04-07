
public class Koordinate {
	
	private int x;
	private int y;
	
	public Koordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Pridobi koordinato x.
	 * @return x
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * Pridobi koordinato y.
	 * @return y
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Vrne reprezentacijo koordinat z nizom.
	 * @return Koordinate [x= , y= ]
	 */
	@Override
	public String toString() {
		return "Koordinate [x=" + this.x + ", y=" + this.y + "]";
	}
	
}
