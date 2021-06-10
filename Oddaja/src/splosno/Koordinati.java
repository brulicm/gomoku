package splosno;

/**
 * Razred predstavlja polje na igralni plošči.
 */
public class Koordinati {

	private int x;
	private int y;
	
	public Koordinati(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Vrne x koordinato poteze.
	 * @return x
	 */
	public int getX() { 
		return this.x; 
	}
	
	/**
	 * Vrne y koordinato poteze.
	 * @return y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Vrne reprezentacijo objekta z nizom.
	 * @return Koordinate [x= , y= ]
	 */
	@Override
	public String toString() {
		return "Koordinati [x=" + this.x + ", y=" + this.y + "]";
	}
	
	/**
	 * Podan objekt o primerja s seboj in vrne true ali false.
	 * @param o
	 * @return boolean
	 */
	@Override 
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (o == null || this.getClass() != o.getClass()) return false;
		Koordinati k = (Koordinati) o;
		return this.x == k.x && this.y == k.y;
	}
	
	/**
	 * Vsakemu objektu Koordinati dodeli svojo unikatno kodo.
	 * @return int
	 */
	@Override
	public int hashCode () {
		return (this.x + this.y) * (this.x + this.y + 1) / 2 + this.y;
	}
	
}
