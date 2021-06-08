package splosno;

/**
 * Razred, ki ga potrebuje razred Inteligenca.
 */
public class KdoIgra {
	
	protected String ime;
	
	public KdoIgra(String ime) {
		this.ime = ime;
	}
	
	/**
	 * Vrne reprezentacijo objekta z nizom.
	 * @return ime
	 */
	public String ime() {
		return this.ime;
	}
	
}
