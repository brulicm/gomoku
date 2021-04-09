package logika;

public enum Igralec {
	
	WHITE,
	BLACK;
	
	/**
	 * Vrne nasprotnika igralca, na katerem je metoda klicana.
	 * @return WHITE/BLACK
	 */
	public Igralec nasprotnik() {
		return (this == WHITE ? BLACK : WHITE);
	}
	
	/**
	 * Vrne polje igralca, na katerem je metoda klicana.
	 * @return WHITE/BLACK
	 */
	public Polje getPolje() {
		return (this == WHITE ? Polje.WHITE : Polje.BLACK);
	}
	
}
