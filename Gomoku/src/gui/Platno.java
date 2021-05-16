package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import vodja.Vodja;

import logika.Igra;
import logika.Polje;
import logika.Vrstica;

/**
 * Pravokotno območje, v katerem je narisano igralno polje.
 */
@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {
	
	public Platno() {
		
		this.setBackground(new Color(210,180,140));
		this.addMouseListener(this);
		
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(390, 390);
	}
	
	// Relativna širina črte
	private final static double LINE_WIDTH = 0.05;
	
	// Širina enega kvadratka
	private double squareWidth() {
		return Math.min(this.getWidth(), this.getHeight()) / Igra.N;
	}
	
	// Relativni prostor okoli žetonov
	private final static double PADDING = 0.14;
	
	/**
	 * Nariše črn krog na polje (i,j)
	 * 
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void paintBLACK(Graphics2D g2, int i, int j, int moveX, int moveY) {
		double w = this.squareWidth();
		double d = w * (1 - LINE_WIDTH - 2 * PADDING); // Premer žetona
		// Koordinate zgornjega levega oglišča objemajočega kvadrata.
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float)(w * LINE_WIDTH)));
		g2.fillOval((int)x + moveX, (int)y + moveY, (int)d, (int)d);
	}
	
	/**
	 * Nariše bel krog na polje (i,j)
	 * 
	 * @param g2
	 * @param i
	 * @param j
	 */
	private void paintWHITE(Graphics2D g2, int i, int j, int moveX, int moveY) {
		double w = this.squareWidth();
		double d = w * (1 - LINE_WIDTH - 2 * PADDING); // Premer kroga
		// Koordinate zgornjega levega oglišča objemajočega kvadrata.
		double x = w * (i + 0.5 * LINE_WIDTH + PADDING);
		double y = w * (j + 0.5 * LINE_WIDTH + PADDING);
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke((float)(w * LINE_WIDTH)));
		g2.fillOval((int)x + moveX, (int)y + moveY, (int)d , (int)d);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;

		double w = this.squareWidth(); // Velikost enega kvadratka
		// Centriranje igralne plošče
		int moveX = Math.max(0, (this.getWidth() - this.getHeight()) / 2);
		int moveY = Math.max(0, (this.getHeight() - this.getWidth()) / 2);

		// Če imamo zmagovalno n-terico, njeno ozadje pobarvamo
		Vrstica v = null;
		if (Vodja.igra != null) v = Vodja.igra.zmagovalnaVrstica();
		if (v != null) {
			g2.setColor(new Color(185,142,86));
			for (int k = 0; k < 5; ++k) {
				int i = v.x[k];
				int j = v.y[k];
				g2.fillRect((int)(w * i) + moveX, (int)(w * j) + moveY, (int)w, (int)w);
			}
		}
		
		// Narišemo črte
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke((float)(w * LINE_WIDTH)));
		for (int i = 0; i <= Igra.N; ++i) {
			g2.drawLine((int)(i * w + moveX), (int)(moveY),
					    (int)(i * w + moveX), (int)(Igra.N * w + moveY));
			g2.drawLine((int)(moveX), (int)(i * w + moveY),
					    (int)(Igra.N * w + moveX), (int)(i * w + moveY));
		}
		
		// Narišemo žetone
		Polje[][] plosca;
		if (Vodja.igra != null) {
			plosca = Vodja.igra.getPlosca();
			for (int i = 0; i < Igra.N; ++i) {
				for (int j = 0; j < Igra.N; ++j) {
					switch(plosca[i][j]) {
					case BLACK: this.paintBLACK(g2, i, j, moveX, moveY); break;
					case WHITE: this.paintWHITE(g2, i, j, moveX, moveY); break;
					default: break;
					}
				}
			}
		}
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Vodja.clovekNaVrsti) {
			double xx = this.getWidth(); // Širina okna
			double yy = this.getHeight(); // Višina okna
			double moveXX = Math.max(0, (double)(xx - yy) / 2.0); // Centriranje igralne plošče
			double moveYY = Math.max(0, (double)(yy - xx) / 2.0); // Centriranje igralne plošče
			
			int x = e.getX() - (int)moveXX;
			int y = e.getY() - (int)moveYY;
			int w = (int)(this.squareWidth());
			
			// Koordinate kvadratka
			int i = x / w;
			int j = y / w;
			// di in dj nam povesta, ali smo v sredini kvadratka ali na črti.
			double di = (x % w) / this.squareWidth();
			double dj = (y % w) / this.squareWidth();
			
			if (0 <= i && i < Igra.N &&
					0.5 * LINE_WIDTH < di && di < 1 - 0.5 * LINE_WIDTH &&
					0 <= j && j < Igra.N && 
					0.5 * LINE_WIDTH < dj && dj < 1 - 0.5 * LINE_WIDTH) {
				Vodja.igrajClovekovoPotezo(new splosno.Koordinati(i, j));
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
}
