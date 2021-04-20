
import java.io.IOException;

import gui.GlavnoOkno;
import vodja.Vodja;

public class Gomoku {
	
	public static void main(String[] args) throws IOException {

		GlavnoOkno glavno_okno = new GlavnoOkno();
		glavno_okno.pack();
		glavno_okno.setVisible(true);
		Vodja.okno = glavno_okno;
	}

}
