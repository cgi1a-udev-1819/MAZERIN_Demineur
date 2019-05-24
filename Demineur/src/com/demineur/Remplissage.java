package com.demineur;

import java.util.ArrayList;

public class Remplissage {
	// rempli les cases de mines
	public static ArrayList<Integer> remplirMines(boolean mines[], int lignes, int colonnes) {
		int nombreDeMines = 10;
		ArrayList<Integer> positionMines = new ArrayList<Integer>();
		while (nombreDeMines > 0) {
			int x = (int) Math.floor(Math.random() * lignes);
			int y = (int) Math.floor(Math.random() * colonnes);
			if (!mines[(lignes * y) + x]) {
				mines[(lignes * y) + x] = true;
				positionMines.add((lignes * y) + x);
				nombreDeMines--;
			}
		}
		return positionMines;
	}

	// ajoute les nombres qui donnent un indice sur les mines adjacentes
	public static void remplirNombres(boolean mines[], int lignes, int colonnes, int numbers[]) {
		for (int x = 0; x < lignes; x++) {
			for (int y = 0; y < colonnes; y++) {
				int curseur = (lignes * y) + x;
				if (mines[curseur]) {
					numbers[curseur] = 0;
					continue;
				}
				int temp = 0;
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
				if (u) {
					if (mines[up]) {
						temp++;
					}
					if (l) {
						if (mines[upleft]) {
							temp++;
						}
					}
					if (r) {
						if (mines[upright]) {
							temp++;
						}
					}
				}
				if (d) {
					if (mines[down]) {
						temp++;
					}
					if (l) {
						if (mines[downleft]) {
							temp++;
						}
					}
					if (r) {
						if (mines[downright]) {
							temp++;
						}
					}
				}
				if (l) {
					if (mines[left]) {
						temp++;
					}
				}
				if (r) {
					if (mines[right]) {
						temp++;
					}
				}
				numbers[curseur] = temp;
			}
		}
	}
}
