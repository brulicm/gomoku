package splosno;

public class Koordinati {
	
	private int x;
	private int y;
	
	public Koordinati(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { 
		return this.x; 
	}
	
	public int getY() {
		return this.y;
	}
	
	/**
	 * @return Koordinate [x= , y= ]
	 */
	@Override
	public String toString() {
		return "Koordinati [x=" + this.x + ", y=" + this.y + "]";
	}
	
	@Override 
	public boolean equals(Object o) {
		if (this == o) return true;
		else if (o == null || this.getClass() != o.getClass()) return false;
		Koordinati k = (Koordinati) o;
		return this.x == k.x && this.y == k.y;
	}
	
	@Override
	public int hashCode () {
		return (this.x + this.y) * (this.x + this.y + 1) / 2 + this.y;
	}
	
}
