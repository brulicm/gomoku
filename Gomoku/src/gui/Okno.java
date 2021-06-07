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

import inteligenca.Algoritem;
import vodja.Vodja;
import vodja.VrstaIgralca;
import logika.Igra;
import logika.Igralec;
import logika.Stanje;


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
	
	// Nastavitev igralca
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	// Velikost igre
	private JMenu menuVelikostIgre;
	private JMenuItem velikost15;
	private JMenuItem velikost19;
	
	// Algoritem
	private JMenu menuAlgoritem;
	private JMenuItem algoritemNeumni;
	private JMenuItem algoritemMinimax;
	private JMenuItem algoritemRandomMinimax;
	private JMenuItem algoritemMinimaxAlphaBeta;
	private JMenuItem algoritemHitriMinimax;
	
	// Zakasnitev poteze
	private JMenuItem menuZakasnitevPoteze;
	
	//Razveljavi potezo
	private JMenuItem razveljaviPotezo;

	/**
	 * Ustvari novo okno in za�ne igrati igro.
	 */
	public Okno() {
		
		// Nastavi naslov okna
		this.setTitle("Gomoku");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// Ustvari menu vrstico
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		
		// Ustvari menuja Nova Igra in Nastavitve
		JMenu menuIgra = dodajMenu(menu_bar, "Nova Igra");
		JMenu menuNastavitve = dodajMenu(menu_bar, "Nastavitve");
	
		// Izbire v menuju Nova Igra 
		this.igraClovekRacunalnik = dodajMenuItem(menuIgra, "�lovek - Ra�unalnik");
		this.igraRacunalnikClovek = dodajMenuItem(menuIgra, "Ra�unalnik - �lovek");
		this.igraClovekClovek = dodajMenuItem(menuIgra, "�lovek - �lovek");
		this.igraRacunalnikRacunalnik = dodajMenuItem(menuIgra, "Ra�unalnik - Ra�unalnik");
		
		// Ustvari podmenu Velikost plo��e in razli�ne izbire velikosti
		this.menuVelikostIgre = dodajPodmenu(menuNastavitve, "Velikost plo��e...");
		this.velikost15 = dodajMenuItem(menuVelikostIgre, "15x15 (default)");
		this.velikost19 = dodajMenuItem(menuVelikostIgre, "19x19");
		
		// Ustvari podmenu Algoritem in razli�ne izbire algoritma
		this.menuAlgoritem = dodajPodmenu(menuNastavitve, "Inteligenca...");
		this.algoritemNeumni = dodajMenuItem(menuAlgoritem, "Naklju�ne poteze");
		this.algoritemMinimax = dodajMenuItem(menuAlgoritem, "Minimax");
		this.algoritemRandomMinimax = dodajMenuItem(menuAlgoritem, "Naklju�ni minimax");
		this.algoritemMinimaxAlphaBeta = dodajMenuItem(menuAlgoritem, "Minimax + AlphaBeta");
		this.algoritemHitriMinimax = dodajMenuItem(menuAlgoritem, "Hitri minimax (default)");
		
		// Ustvari zakasnitev poteze 
		this.menuZakasnitevPoteze = dodajMenuItem(menuNastavitve, "Zakasnitev ra�unalnikove poteze...");
		
		// Ustvari razveljavi potezo
		this.razveljaviPotezo = dodajMenuItem(menuNastavitve, "Razveljavi zadnjo potezo!");
		
		// Ustvari novo igralno plo��o
		this.platno = new Platno();

		// Na�in in mesto postavitve platna v oknu
		GridBagConstraints platno_layout = new GridBagConstraints();
		platno_layout.gridx = 0;
		platno_layout.gridy = 0;
		platno_layout.fill = GridBagConstraints.BOTH;
		platno_layout.weightx = 1.0;
		platno_layout.weighty = 1.0;
		getContentPane().add(platno, platno_layout);
		
		// Statusna vrstica za sporo�ila
		this.status = new JLabel();
		this.status.setFont(new Font(this.status.getFont().getName(),
		this.status.getFont().getStyle(), 16));
		
		// Na�in in mesto postavitve statusne vrstice v oknu
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		// Za�etno stanje statusne vrstice
		this.status.setText("Izberite �eljeno igro!");
	}
	
	/**
	 * Pomo�na metoda za konstruiranje menuja
	 * @param menubar vrstica z vsemi menuji
	 * @param naslov poimenovanje menu-ja
	 * @return menu z danim naslovom
	 */
	public JMenu dodajMenu(JMenuBar menu_bar, String naslov) {
		JMenu menu = new JMenu(naslov);
		menu_bar.add(menu);
		return menu;
	}
	
	/**
	 * Pomo�na metoda za konstruiranje podmenuja
	 * @param menu v katerem bo ta podmenu
	 * @param naslov poimenovanje podmenuja
	 * @return podmenu z danim naslovom
	 */
	public JMenu dodajPodmenu(JMenu menu, String naslov) {
		JMenu podmenu = new JMenu(naslov);
		menu.add(podmenu);
		return podmenu;
	}
	
	/**
	 * Pomo�na metoda za konstruiranje izbire v menuju
	 * @param menu v katerem bo ta izbira
	 * @param naslov poimenovanje izbire
	 * @return izbira z danim naslovom v menuju
	 */
	public JMenuItem dodajMenuItem(JMenu menu, String naslov) {
		JMenuItem menuitem = new JMenuItem(naslov);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		return menuitem;		
	}
	
	/**
	 * Odzivi ob izbirah razli�nih opcij v menuju
	 */
	@Override
	
	// MENU NOVA IGRA
		// Nastavi izbiro igralcev
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == this.igraClovekRacunalnik) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.HUMAN); 
				Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.COMP);
				Vodja.igramoNovoIgro();
			}
		
			else if (e.getSource() == this.igraRacunalnikClovek) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.COMP); 
				Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.HUMAN);
				Vodja.igramoNovoIgro();
			}
		
			else if (e.getSource() == this.igraClovekClovek) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.HUMAN); 
				Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.HUMAN);
				Vodja.igramoNovoIgro();
			}
			
			else if (e.getSource() == this.igraRacunalnikRacunalnik) {
				Vodja.vrstaIgralca = new EnumMap<Igralec,VrstaIgralca>(Igralec.class);
				Vodja.vrstaIgralca.put(Igralec.WHITE, VrstaIgralca.COMP); 
				Vodja.vrstaIgralca.put(Igralec.BLACK, VrstaIgralca.COMP);
				Vodja.igramoNovoIgro();
			}
			
		// MENU NASTAVITVE
			// Nastavi velikost igralne plo��e
			else if (e.getSource() == this.velikost15) {
				if (Vodja.igra == null) {
					Igra.N = 15;
					this.status.setText("Velikost nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.N = 15;
					this.status.setText("Velikost nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, velikosti ne morete spreminjati.");
				this.platno.repaint();
			}
			
			else if (e.getSource() == this.velikost19) {
				if (Vodja.igra == null) {
					Igra.N = 19;
					this.status.setText("Velikost nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.N = 19;
					this.status.setText("Velikost nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, velikosti ne morete spreminjati.");
				this.platno.repaint();
			}
			
			// Nastavi algoritem ra�unalnikove inteligence
			else if (e.getSource() == this.algoritemNeumni) {
				if (Vodja.igra == null) {
					Igra.algoritem = Algoritem.NEUMNI;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.algoritem = Algoritem.NEUMNI;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, inteligence ne morete spreminjati.");
				
				this.platno.repaint();
			}
			
			else if (e.getSource() == this.algoritemMinimax) {
				if (Vodja.igra == null) {
					Igra.algoritem = Algoritem.MINIMAX;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.algoritem = Algoritem.MINIMAX;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, inteligence ne morete spreminjati.");
				
				this.platno.repaint();
			}
			
			else if (e.getSource() == this.algoritemRandomMinimax) {
				if (Vodja.igra == null) {
					Igra.algoritem = Algoritem.RANDOM_MINIMAX;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.algoritem = Algoritem.RANDOM_MINIMAX;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, inteligence ne morete spreminjati.");
				
				this.platno.repaint();
			}
			
			else if (e.getSource() == this.algoritemMinimaxAlphaBeta) {
				if (Vodja.igra == null) {
					Igra.algoritem = Algoritem.MINIMAX_ALPHA_BETA;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.algoritem = Algoritem.MINIMAX_ALPHA_BETA;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, inteligence ne morete spreminjati.");
				
				this.platno.repaint();
			}
			
			else if (e.getSource() == this.algoritemHitriMinimax) {
				if (Vodja.igra == null) {
					Igra.algoritem = Algoritem.HITRI_MINIMAX;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else if (Vodja.igra.stanjeIgre() != Stanje.V_TEKU) {
					Vodja.igra = null;
					Igra.algoritem = Algoritem.HITRI_MINIMAX;
					this.status.setText("Inteligenca nastavljena. Izberite �eljeno igro!");
				}
				else this.status.setText("Dokler je igra v teku, inteligence ne morete spreminjati.");
				
				this.platno.repaint();
			}
			
			// Nastavi zakasnitev poteze
			else if (e.getSource() == this.menuZakasnitevPoteze) {
				JTextField field = new JTextField();
				JComponent[] lab = {new JLabel("Vnesi zakasnitev poteze (default: 1s):"), field};
				int izbira = JOptionPane.showConfirmDialog(this, lab, "Input", JOptionPane.OK_CANCEL_OPTION);
				String zakasnitevPoteze = field.getText();
				if (izbira == JOptionPane.OK_OPTION && zakasnitevPoteze.matches("\\d+")) {
					Vodja.zakasnitev = Integer.valueOf(zakasnitevPoteze);
					this.status.setText("Zakasnitev uspe�no spremenjena. " + this.status.getText());
				} else this.status.setText("Prosimo vnesite nenegativno celo �tevilo.");
				
				this.platno.repaint();
			}
			
			// Razveljavi potezo
			else if (e.getSource() == this.razveljaviPotezo) {
				boolean bool1 = false;
				boolean bool2 = false;
				
				// Ko igrata ra�unalnika poteze ne razveljavimo
				if (Vodja.igra == null) {
					this.status.setText("Najprej izberite �eljeno igro!");
				} else if (Vodja.vrstaIgralca.get(Igralec.WHITE) == VrstaIgralca.COMP
						&& Vodja.vrstaIgralca.get(Igralec.BLACK) == VrstaIgralca.COMP) {
					this.status.setText("Ko igrata ra�unalnika, potez ne morete razveljaviti");
					
				// Ko igrata �loveka razveljavimo zadnjo potezo
				} else if (Vodja.vrstaIgralca.get(Igralec.WHITE) == VrstaIgralca.HUMAN
						&& Vodja.vrstaIgralca.get(Igralec.BLACK) == VrstaIgralca.HUMAN) {
					bool1 = Vodja.igra.razveljaviPotezo();
					if (bool1) this.status.setText("Poteza uspe�no razveljavljena. " + this.status.getText());
					else this.status.setText("Poteza ni bila razveljavljena. " + this.status.getText());
					
				// Ko igrata �lovek in ra�unalnik razveljavimo zadnji dve potezi
				} else if (Vodja.vrstaIgralca.get(Igralec.WHITE) == VrstaIgralca.COMP
						&& Vodja.vrstaIgralca.get(Igralec.BLACK) == VrstaIgralca.HUMAN
						&& Vodja.igra.naPotezi == Igralec.BLACK) {
					bool1 = Vodja.igra.razveljaviPotezo();
					bool2 = Vodja.igra.razveljaviPotezo();
					if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. " + this.status.getText());
					else this.status.setText("Potezi nista bili razveljavljeni. " + this.status.getText());
				
				// Ko igrata �lovek in ra�unalnik razveljavimo zadnji dve potezi
				} else if (Vodja.vrstaIgralca.get(Igralec.WHITE) == VrstaIgralca.HUMAN
						&& Vodja.vrstaIgralca.get(Igralec.BLACK) == VrstaIgralca.COMP
						&& Vodja.igra.naPotezi == Igralec.WHITE) {
					bool1 = Vodja.igra.razveljaviPotezo();
					bool2 = Vodja.igra.razveljaviPotezo();
					if (bool1 && bool2) this.status.setText("Potezi razveljavljeni. " + this.status.getText());
					else this.status.setText("Potezi nista bili razveljavljeni. " + this.status.getText());
				}
				
				this.platno.repaint();
			}
		this.osveziGUI();
	}
	
	/**
	 *  Prika�e trenutno stanje v igri na igralni plo��i in v statusni vrstici.
	 */
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Izberite �eljeno igro!");
		} else {
			switch(Vodja.igra.stanjeIgre()) {
			case NEODLOCENO: status.setText("Neodlo�eno!"); break;
			case V_TEKU:
				if (Vodja.igra.naPotezi == Igralec.WHITE && Vodja.vrstaIgralca.get(Igralec.WHITE) == VrstaIgralca.HUMAN) {
					status.setText("Na potezi je beli - �lovek.");
					break;
				} else if (Vodja.igra.naPotezi == Igralec.WHITE) {
					status.setText("Na potezi je beli - " + Vodja.racunalnikovaInteligenca.ime() + ".");
					break;
				} else if (Vodja.igra.naPotezi == Igralec.BLACK && Vodja.vrstaIgralca.get(Igralec.BLACK) == VrstaIgralca.HUMAN) {
					status.setText("Na potezi je �rni - �lovek.");
					break;
				} else {
					status.setText("Na potezi je �rni - " + Vodja.racunalnikovaInteligenca.ime() + ".");
					break;
				}
			case ZMAGA_WHITE:
				if (Vodja.vrstaIgralca.get(Igralec.WHITE) == VrstaIgralca.HUMAN) {
					status.setText("Zmagal je beli - �lovek. ");
					break;
				} else {
					status.setText("Zmagal je beli - " + Vodja.racunalnikovaInteligenca.ime() + ".");
					break;
				}
			case ZMAGA_BLACK:
				if (Vodja.vrstaIgralca.get(Igralec.BLACK) == VrstaIgralca.HUMAN) {
					status.setText("Zmagal je �rni - �lovek");
					break;
				} else {
					status.setText("Zmagal je �rni - " + Vodja.racunalnikovaInteligenca.ime() + ".");
					break;
				}
			}
		}
		
		platno.repaint();
	}
	
}
