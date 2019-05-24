package com.demineur;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

//Les messages de victoire et défaite sont affichés dans la console

public class Demineur extends JFrame implements ActionListener, MouseListener {

	int lignes = 10;
	int colonnes = 10;
	GridLayout layout = new GridLayout(lignes, colonnes);
	// initialisation du layout

	boolean[] mines = new boolean[lignes * colonnes];
	boolean[] clickable = new boolean[lignes * colonnes];
	// initialisation des flags sur chaque case

	boolean lost = false;
	boolean win = false;
	// flag de victoire ou défaite

	int[] numbers = new int[lignes * colonnes];
	JToggleButton[] buttons = new JToggleButton[lignes * colonnes];
	boolean[] clickdone = new boolean[lignes * colonnes];

	JPanel panel = new JPanel();

	public Demineur() {
		panel.setLayout(layout);
		setup();
		for (int i = 0; i < (lignes * colonnes); i++) {
			panel.add(buttons[i]);
		}

		this.add(panel);
		pack();
		setVisible(true);
	}

	// Création de la grille
	public void setup() {
		for (int x = 0; x < lignes; x++) {
			for (int y = 0; y < lignes; y++) {
				mines[(lignes * y) + x] = false;
				clickdone[(lignes * y) + x] = false;
				clickable[(lignes * y) + x] = true;
				buttons[(lignes * y) + x] = new JToggleButton();
				buttons[(lignes * y) + x].setPreferredSize(new Dimension(45, 45));
				buttons[(lignes * y) + x].addActionListener(this);
				buttons[(lignes * y) + x].addMouseListener(this);
			}
		}
		ArrayList<Integer> positionMines = Remplissage.remplirMines(mines, lignes, colonnes);
		// devrait permettre de remplir les boutons mines avec l'image des mines mais ne
		// fonctionne pas
		for (Integer integer : positionMines) {
			ImageIcon imgP = new ImageIcon("images/mine.png");
			buttons[integer].setSelectedIcon(imgP);
			buttons[integer].setDisabledIcon(imgP);
			buttons[integer].setDisabledSelectedIcon(imgP);
		}
		Remplissage.remplirNombres(mines, lignes, colonnes, numbers);
	}

	public static void main(String[] args) {
		new Demineur();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (!win) {
			for (int x = 0; x < lignes; x++) {
				for (int y = 0; y < colonnes; y++) {
					if (e.getSource() == buttons[(lignes * y) + x] && !win && clickable[(lignes * y) + x]) {
						doCheck(x, y);
						break;
					}
				}
			}
		}

		checkWin();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// l'utilisateur utilise le clic gauche de la souris
		if (e.getButton() == 3) {
			for (int x = 0; x < lignes; x++) {
				for (int y = 0; y < colonnes; y++) {
					// récupère l'adresse du bouton "cliqué" qui ne devient plus "cliquable"
					if (e.getSource() == buttons[(lignes * y) + x]) {
						clickable[(lignes * y) + x] = !clickable[(lignes * y) + x];
					}
					if (!clickdone[(lignes * y) + x]) {
						if (!clickable[(lignes * y) + x]) {
							buttons[(lignes * y) + x].setText("X");
						} else {
							buttons[(lignes * y) + x].setText("");
						}
					}
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void doCheck(int x, int y) {
		int curseur = (lignes * y) + x;
		boolean l = (x - 1) >= 0;
		boolean r = (x + 1) < lignes;
		boolean u = (y - 1) >= 0;
		boolean d = (y + 1) < colonnes;
		int left = (lignes * (y)) + (x - 1);
		int right = (lignes * (y)) + (x + 1);
		int up = (lignes * (y - 1)) + (x);
		int upleft = (lignes * (y - 1)) + (x - 1);
		int upright = (lignes * (y - 1)) + (x + 1);
		int down = (lignes * (y + 1)) + (x);
		int downleft = (lignes * (y + 1)) + (x - 1);
		int downright = (lignes * (y + 1)) + (x + 1);

		clickdone[curseur] = true;
		buttons[curseur].setEnabled(false);
		if (numbers[curseur] == 0 && !mines[curseur] && !lost && !win) {
			if (u && !win) {
				if (!clickdone[up] && !mines[up]) {
					clickdone[up] = true;
					buttons[up].doClick();
				}
				if (l && !win) {
					if (!clickdone[upleft] && numbers[upleft] != 0 && !mines[upleft]) {
						clickdone[upleft] = true;
						buttons[upleft].doClick();
					}
				}
				if (r && !win) {
					if (!clickdone[upright] && numbers[upright] != 0 && !mines[upright]) {
						clickdone[upright] = true;
						buttons[upright].doClick();
					}
				}
			}
			if (d && !win) {
				if (!clickdone[down] && !mines[down]) {
					clickdone[down] = true;
					buttons[down].doClick();
				}
				if (l && !win) {
					if (!clickdone[downleft] && numbers[downleft] != 0 && !mines[downleft]) {
						clickdone[downleft] = true;
						buttons[downleft].doClick();
					}
				}
				if (r && !win) {
					if (!clickdone[downright] && numbers[downright] != 0 && !mines[downright]) {
						clickdone[downright] = true;
						buttons[downright].doClick();
					}
				}
			}
			if (l && !win) {
				if (!clickdone[left] && !mines[left]) {
					clickdone[left] = true;
					buttons[left].doClick();
				}
			}
			if (r && !win) {
				if (!clickdone[right] && !mines[right]) {
					clickdone[right] = true;
					buttons[right].doClick();
				}
			}
		} else {
			buttons[curseur].setText("" + numbers[curseur]);
			if (!mines[curseur] && numbers[curseur] == 0) {
				buttons[curseur].setText("");
			}
		}
		if (mines[curseur] && !win) {
			buttons[curseur].setText("0");
			System.out.println("Vous avez perdu!");
		}
	}

	public void checkWin() {
		for (int x = 0; x < lignes; x++) {
			for (int y = 0; y < colonnes; y++) {
				int curseur = (lignes * y) + x;
				if (!clickdone[curseur]) {
					if (mines[curseur]) {
						continue;
					} else {
						return;
					}
				}
			}
		}
		win = true;
		System.out.println("Vous avez gagné!");
	}

}
