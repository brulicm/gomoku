
import java.io.IOException;

import gui.Okno;
import vodja.Vodja;

public class Gomoku {
	
	public static void main(String[] args) throws IOException {

		Okno glavno_okno = new Okno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		Vodja.okno = glavno_okno;
	}

}
