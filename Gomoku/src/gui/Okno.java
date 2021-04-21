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
 * Glavno okno aplikacije hrani trenutno stanje igre in nadzoruje potek
 * igre.
 * 
 * @author AS
 *
 */
@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega igramo
	 */
	private Platno platno;

	
	//Statusna vrstica v spodnjem delu okna
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
	 * Ustvari novo glavno okno in prièni igrati igro.
	 */
	public Okno() {
		
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		JMenu menuIgra = dodajMenu(menu_bar, "Nova Igra");
		JMenu menuNastavitve = dodajMenu(menu_bar, "Nastavitve");
	
		igraClovekRacunalnik = dodajMenuItem(menuIgra, "Èlovek – raèunalnik");
		igraRacunalnikClovek = dodajMenuItem(menuIgra, "Raèunalnik – èlovek");
		igraClovekClovek = dodajMenuItem(menuIgra, "Èlovek – èlovek");
		igraRacunalnikRacunalnik = dodajMenuItem(menuIgra, "Raèunalnik – raèunalnik");
		
		menuVelikostIgre = dodajPodmenu(menuNastavitve, "Velikost igre...");
		velikost15 = dodajMenuItem(menuVelikostIgre, "15×15");
		velikost19 = dodajMenuItem(menuVelikostIgre, "19×19");
		
		menuAlgoritem = dodajPodmenu(menuNastavitve, "Algoritem...");
		algoritemMinimax = dodajMenuItem(menuAlgoritem, "Minimax");
		algoritemAlfaBeta = dodajMenuItem(menuAlgoritem, "AlfaBeta");
		
		menuCasPoteze = dodajMenuItem(menuNastavitve, "Nastavi èas poteze ...");
		
		
		
		// igralno polje
		platno = new Platno();

		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		
		
		// statusna vrstica za sporoèila
		status = new JLabel();
		status.setFont(new Font(status.getFont().getName(),
							    status.getFont().getStyle(),
							    20));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("Izberite igro!");
		
	}
	
	// pomožni metodi za kontruiranje menuja

	public JMenu dodajMenu(JMenuBar menu_bar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menu_bar.add(menu);
		return menu;
	}
	
	public JMenu dodajPodmenu(JMenu menu, String naslov) {
		JMenu podmenu = new JMenu(naslov);
		menu.add(podmenu);
		return podmenu;
	}
	
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
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.C); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.R); 
			Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == velikost15) {
			if (Vodja.igra != null) {
			status.setText("Igra je že v teku. Ne morete je veè spreminjati.");
			}
			else Igra.N = 15;
		} else if (e.getSource() == velikost19) {
			if (Vodja.igra != null) {
			status.setText("Igra je že v teku. Ne morete je veè spreminjati.");
			}
			else Igra.N = 19;
		} else if (e.getSource() == menuCasPoteze) {
			JTextField cas = new JTextField();
			JComponent[] polja = {
					new JLabel("Vnesi èas poteze:"), cas};
			int izbira = JOptionPane.showConfirmDialog(this,  polja, "Input", JOptionPane.OK_CANCEL_OPTION);
			String casPoteze = cas.getText();
			if (izbira == JOptionPane.OK_OPTION && casPoteze.matches("\\d+")) {
				Vodja.cas = Integer.valueOf(casPoteze);
			};
			
		}
		else if (e.getSource() == algoritemAlfaBeta) {}
		else if (e.getSource() == algoritemMinimax) {}
	}
		
	

	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ni v teku.");
		}
		else {
			switch(Vodja.igra.stanjeIgre()) {
			case NEODLOCENO: status.setText("Neodloèeno!"); break;
			case V_TEKU: 
				status.setText("Na potezi je " + Vodja.igra.naPotezi + 
						" - " + Vodja.vrstaIgralca.get(Vodja.igra.naPotezi)); 
				break;
			case ZMAGA_WHITE: 
				status.setText("Zmagal je WHITE - " + 
						Vodja.vrstaIgralca.get(Igralec.WHITE));
				break;
			case ZMAGA_BLACK: 
				status.setText("Zmagal je BLACK - " + 
						Vodja.vrstaIgralca.get(Igralec.BLACK));
				break;
			}
		}
		platno.repaint();
	}
	



}
