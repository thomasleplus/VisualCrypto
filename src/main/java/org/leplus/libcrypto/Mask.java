/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package org.leplus.libcrypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.awt.image.BufferedImage;

import org.leplus.libimage.PortableBitmap;

/**
 * Masque cryptographique.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public class Mask {
	
	/**
	 * Le bitmap de ce masque.
	 */
	private PortableBitmap bmp;
	
	/**
	 * Construit le masque à partir du masque.
	 *
	 * @param mask le masque
	 */
	public Mask(Mask mask) {
		bmp = mask.bmp;
	}
	
	/**
	 * Construit le masque à partir du bitmap.
	 *
	 * @param pbm le bitmap
	 */
	public Mask(PortableBitmap pbm) {
		bmp = (PortableBitmap)pbm.clone();
		trim();
	}
	
	/**
	 * Construit le masque à partir des données dans le fot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public Mask(InputStream input)
		throws IOException {
		bmp = new PortableBitmap();
		bmp.read(input);
		trim();
	}
	
	/**
	 * Met à zéro les bits inutilisés du masque.
	 */
	private void trim() {
		int w = (int)StrictMath.ceil((double)bmp.width / 8);
		int m = 0xFF << ((w << 3) - bmp.width);
		if (bmp.table.length != bmp.height)
			throw new IndexOutOfBoundsException();
		for (int i = 0; i < bmp.height; i++) {
			if (bmp.table[i].length != w)
				throw new IndexOutOfBoundsException();
			bmp.table[i][w - 1] &= m;
		}
	}
	
	/**
	 * Retourne la hauteur du masque.
	 *
	 * @return la hauteur du masque.
	 */
	public int getHeight() {
		return bmp.height;
	}
	
	/**
	 * Retourne la largeur du masque.
	 *
	 * @return la largeur du masque.
	 */
	public int getWidth() {
		return bmp.width;
	}
	
	/**
	 * Retourne la valeur du masque pour la coordonnée donnée.
	 *
	 * @param u la ligne.
	 * @param v la colonne.
	 */
	public boolean getValue(int u, int v) {
		return bmp.getValue(u, v);
	}
	
	/**
	 * Écrit le masque dans le flot.
	 *
	 * @param output le flot de sortie.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public void write(OutputStream output)
		throws IOException {
		bmp.write(output);
	}
	
	/**
	 * Imprime le masque dans le flot. Un bit dans le masque
	 * devient un block de 8x8 pixels :
	 *
	 * 0 -> 00001111
	 *      00001111
	 *      00001111
	 *      00001111
	 *      11110000
	 *      11110000
	 *      11110000
	 *      11110000
	 *
	 * 1 -> 11110000
	 *      11110000
	 *      11110000
	 *      11110000
	 *      00001111
	 *      00001111
	 *      00001111
	 *      00001111
	 *
	 * @param output le flot de sortie.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
 	public void print(OutputStream output)
		throws IOException {
		String header = "P4\n" + (bmp.width << 3) + "\n" + (bmp.height << 3) + "\n";
		output.write(header.getBytes("US-ASCII"));
		for (int i = 0; i < bmp.height; i++)
			for (int j = 0; j < 8; j++)
				for (int k = 0; k < bmp.width; k++) {
					boolean b = getValue(k, i);
					if ((b & (j < 4)) || (!b & (j > 3)))
						output.write((byte)0xF0);
					else
						output.write((byte)0x0F);
				}
	}
 	
	/**
	 * Imprime le masque dans l'image. Un bit dans le masque
	 * devient un block de 8x8 pixels :
	 *
	 * 0 -> 00001111
	 *      00001111
	 *      00001111
	 *      00001111
	 *      11110000
	 *      11110000
	 *      11110000
	 *      11110000
	 *
	 * 1 -> 11110000
	 *      11110000
	 *      11110000
	 *      11110000
	 *      00001111
	 *      00001111
	 *      00001111
	 *      00001111
	 *
	 * @param output l'image de sortie.
	 */
	public void print(BufferedImage output) {
		for (int i = 0; i < bmp.width; i++)
			for (int j = 0; j < bmp.height; j++) {
				boolean b = getValue(i, j);
				for (int k = 0; k < 8; k++)
					for (int l = 0; l < 8; l++)
						if ((k < 4 && l < 4) || (k > 3 && l > 3))
							output.setRGB(output.getMinX() + (i << 3) + k, output.getMinY() + (j << 3) + l, b ? 0 : ~0);
						else
							output.setRGB(output.getMinX() + (i << 3) + k, output.getMinY() + (j << 3) + l, b ? ~0 : 0);
			}
	}
	
	/**
	 * Retourne la négation du masque.
	 *
	 * @return la négation
	 */
	public Mask not() {
		Mask not = new Mask(bmp);
		for (int i = 0; i < not.bmp.table.length; i++)
			for (int j = 0; j < not.bmp.table[i].length; j++)
				not.bmp.table[i][j] ^= 0xFF;
		not.trim();
		return not;
	}
	
	/**
	 * Retourne le ET du masque et de l'argument.
	 *
	 * @param mask l'argument
	 * @return le ET
	 */
	public Mask and(Mask mask) {
		Mask and = new Mask(bmp);
		if (and.bmp.table.length != mask.bmp.table.length)
			throw new IndexOutOfBoundsException();
		for (int i = 0; i < and.bmp.table.length; i++) {
			if (and.bmp.table[i].length != mask.bmp.table[i].length)
				throw new IndexOutOfBoundsException();
			for (int j = 0; j < and.bmp.table[i].length; j++)
				and.bmp.table[i][j] &= mask.bmp.table[i][j];
		}
		and.trim();
		return and;
	}
	
	/**
	 * Retourne le OU du masque et de l'argument.
	 *
	 * @param mask l'argument
	 * @return le OU
	 */
	public Mask or(Mask mask) {
		Mask or = new Mask(bmp);
		if (or.bmp.table.length != mask.bmp.table.length)
			throw new IndexOutOfBoundsException();
		for (int i = 0; i < or.bmp.table.length; i++) {
			if (or.bmp.table[i].length != mask.bmp.table[i].length)
				throw new IndexOutOfBoundsException();
			for (int j = 0; j < or.bmp.table[i].length; j++)
				or.bmp.table[i][j] |= mask.bmp.table[i][j];
		}
		or.trim();
		return or;
	}
	
	/**
	 * Retourne le OU EXCLUSIF du masque et de l'argument.
	 *
	 * @param mask l'argument
	 * @return le OU EXCLUSIF
	 */
	public Mask xor(Mask mask) {
		Mask xor = new Mask(bmp);
		if (xor.bmp.table.length != mask.bmp.table.length)
			throw new IndexOutOfBoundsException();
		for (int i = 0; i < xor.bmp.table.length; i++) {
			if (xor.bmp.table[i].length != mask.bmp.table[i].length)
				throw new IndexOutOfBoundsException();
			for (int j = 0; j < xor.bmp.table[i].length; j++)
				xor.bmp.table[i][j] ^= mask.bmp.table[i][j];
		}
		xor.trim();
		return xor;
	}
	
	public String toString() {
		return bmp.toString();
	}
	
	public boolean equals(Object object) {
		Mask m = (Mask)object;
		return bmp.equals(m.bmp);
	}
	
}
