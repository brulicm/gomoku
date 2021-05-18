package gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import vodja.Vodja;
import vodja.VrstaIgralca;
import logika.Igra;
import logika.Igralec;


/**
 * Glavno okno aplikacije hrani trenutno
 * stanje igre in nadzoruje potek igre.
 */
@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	private Platno platno; // JPanel
	
	// Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	private JMenu menuVelikostIgre;
	private JMenu menuAlgoritem;
	
	private JMenuItem menuCasPoteze;
	private JMenuItem velikost15;
	private JMenuItem velikost19;
	private JMenuItem algoritemMinimax;
	private JMenuItem algoritemAlfaBeta;

	/**
	 * Ustvari novo okno in začne igrati igro.
	 */
	public Okno() {
		
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// Menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu menuIgra = dodajMenu(menu_bar, "Nova Igra");
		JMenu menuNastavitve = dodajMenu(menu_bar, "Nastavitve");
	
		this.igraClovekRacunalnik = dodajMenuItem(menuIgra, "Človek - računalnik");
		this.igraRacunalnikClovek = dodajMenuItem(menuIgra, "Računalnik - človek");
		this.igraClovekClovek = dodajMenuItem(menuIgra, "Človek - človek");
		this.igraRacunalnikRacunalnik = dodajMenuItem(menuIgra, "Računalnik - računalnik");
		
		this.menuVelikostIgre = dodajPodmenu(menuNastavitve, "Velikost plošče...");
		this.velikost15 = dodajMenuItem(menuVelikostIgre, "15x15");
		this.velikost19 = dodajMenuItem(menuVelikostIgre, "19x19");
		
		this.menuAlgoritem = dodajPodmenu(menuNastavitve, "Algoritem...");
		this.algoritemMinimax = dodajMenuItem(menuAlgoritem, "Minimax");
		this.algoritemAlfaBeta = dodajMenuItem(menuAlgoritem, "AlfaBeta");
		
		this.menuCasPoteze = dodajMenuItem(menuNastavitve, "Zakasnitev računalnikove poteze...");
		
		// Igralna plošča
		this.platno = new Platno();

		GridBagConstraints platno_layout = new GridBagConstraints();
		platno_layout.gridx = 0;
		platno_layout.gridy = 0;
		platno_layout.fill = GridBagConstraints.BOTH;
		platno_layout.weightx = 1.0;
		platno_layout.weighty = 1.0;
		getContentPane().add(platno, platno_layout);
		
		// Statusna vrstica za sporočila
		this.status = new JLabel();
		this.status.setFont(new Font(this.status.getFont().getName(),
							    this.status.getFont().getStyle(), 20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite željeno igro!");
	}
	
	// Pomožna metoda za kontruiranje menuja
	public JMenu dodajMenu(JMenuBar menu_bar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menu_bar.add(menu);
		return menu;
	}
	
	// Pomožna metoda za kontruiranje menuja
	public JMenu dodajPodmenu(JMenu menu, String naslov) {
		JMenu podmenu = new JMenu(naslov);
		menu.add(podmenu);
		return podmenu;
	}
	
	// Pomožna metoda za kontruiranje menuja
	public JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuitem = new JMenuItem(naslov);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		return menuitem;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.HUMAN); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.COMP);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.COMP); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.HUMAN);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.HUMAN); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.HUMAN);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.COMP); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.COMP);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == velikost15) {
			if (Vodja.igra != null) status.setText("Igra je že v teku. Ne morete je več spreminjati.");
			else Igra.N = 15;
		} else if (e.getSource() == velikost19) {
			if (Vodja.igra != null) status.setText("Igra je že v teku. Ne morete je več spreminjati.");
			else Igra.N = 19;
		} else if (e.getSource() == menuCasPoteze) {
			JTextField field = new JTextField();
			JComponent[] lab = {new JLabel("Vnesi zakasnitev poteze:"), field};
			int izbira = JOptionPane.showConfirmDialog(this, lab, "Input", JOptionPane.OK_CANCEL_OPTION);
			String casPoteze = field.getText();
			if (izbira == JOptionPane.OK_OPTION && casPoteze.matches("\\d+")) {
				Vodja.zakasnitev = Integer.valueOf(casPoteze);
			};
			
		}
		else if (e.getSource() == algoritemAlfaBeta) {} // TODO
		else if (e.getSource() == algoritemMinimax) {} // TODO
	}
	
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Izberite željeno igro!");
		} else {
			switch(Vodja.igra.stanjeIgre()) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU:
				if (Vodja.igra.naPotezi == Igralec.WHITE) {
					status.setText("Na potezi je beli - " + Vodja.vrstaIgralca.get(Igralec.WHITE));
					break;
				} else {
					status.setText("Na potezi je črni - " + Vodja.vrstaIgralca.get(Igralec.BLACK));
					break;
				}
			case ZMAGA_WHITE:
				status.setText("Zmagal je beli - " +
						Vodja.vrstaIgralca.get(Igralec.WHITE));
				break;
			case ZMAGA_BLACK:
				status.setText("Zmagal je črni - " +
						Vodja.vrstaIgralca.get(Igralec.BLACK));
				break;
			}
		}
		
		platno.repaint();
	}
	
}
