/*
 * VisualCrypto - A project of visual cryptography.
 * Copyright (C) 2016 Thomas Leplus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.leplus.libcrypto;

import java.util.Random;

import org.leplus.libimage.PortableBitmap;

/**
 * Générateur de Masques Cryptographiques.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public final class MaskKeyGenerator {
	
	/**
	 * La source d'aléat.
	 */
	private Random random;
	
	/**
	 * La hauteur du masque.
	 */
	private int height;
	
	/**
	 * La largeur du masque.
	 */
	private int width;
	
	/**
	 * Construit le générateur de masques.
	 *
	 * @param w la largeur des masques.
	 * @param h la hauteur des masques.
	 */
	public MaskKeyGenerator(int w, int h) {
		if (w < 1 || h < 1)
			throw new IndexOutOfBoundsException();
		width = w;
		height = h;
		random = new Random();
	}
	
	/**
	 * Retourne la hauteur des masques générés.
	 *
	 * @return la hauteur des masques.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Retourne la largeur des masques générés.
	 *
	 * @return la largeur des masques.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Génère une clé.
	 *
	 * @return la clé.
	 */
	public MaskKey generateKey() {
		byte[][] table = new byte[height][(int)StrictMath.ceil((double)width / 8)];
		for (int i = 0; i < height; i++)
			random.nextBytes(table[i]);
		return new MaskKey(new Mask(new PortableBitmap(table, width, height)));
	}
	
}
