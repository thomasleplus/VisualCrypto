package org.leplus.libcrypto;

import java.security.SecureRandom;
import java.util.Random;

import org.leplus.libimage.PortableBitmap;

/**
 * G�n�rateur de Masques Cryptographiques.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public final class MaskKeyGenerator {

	/**
	 * La source d'al�at.
	 */
	private final Random random;

	/**
	 * La hauteur du masque.
	 */
	private final int height;

	/**
	 * La largeur du masque.
	 */
	private final int width;

	/**
	 * Construit le g�n�rateur de masques.
	 *
	 * @param w la largeur des masques.
	 * @param h la hauteur des masques.
	 */
	public MaskKeyGenerator(final int w, final int h) {
		if (w < 1 || h < 1) {
			throw new IndexOutOfBoundsException();
		}
		width = w;
		height = h;
		random = new SecureRandom();
	}

	/**
	 * G�n�re une cl�.
	 *
	 * @return la cl�.
	 */
	public MaskKey generateKey() {
		final byte[][] table = new byte[height][(int) StrictMath.ceil((double) width / 8)];
		for (int i = 0; i < height; i++) {
			random.nextBytes(table[i]);
		}
		return new MaskKey(new Mask(new PortableBitmap(table, width, height)));
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

}
