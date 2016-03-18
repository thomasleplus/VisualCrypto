/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package ift6350.libcrypto;

import java.util.Random;

import ift6350.libimage.PortableBitmap;

/**
 * G�n�rateur de Masques Cryptographiques.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public final class MaskKeyGenerator {
	
	/**
	 * La source d'al�at.
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
	 * Construit le g�n�rateur de masques.
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
	 * Retourne la hauteur des masques g�n�r�s.
	 *
	 * @return la hauteur des masques.
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Retourne la largeur des masques g�n�r�s.
	 *
	 * @return la largeur des masques.
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * G�n�re une cl�.
	 *
	 * @return la cl�.
	 */
	public MaskKey generateKey() {
		byte[][] table = new byte[height][(int)StrictMath.ceil((double)width / 8)];
		for (int i = 0; i < height; i++)
			random.nextBytes(table[i]);
		return new MaskKey(new Mask(new PortableBitmap(table, width, height)));
	}
	
}
