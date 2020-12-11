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

package org.leplus.libimage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.leplus.lib2D.Polygon2D;

/**
 * Portable Bitmap.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public class PortableBitmap implements Cloneable {

	/**
	 * Les donn�es du bitmap.
	 */
	public byte[][] table;

	/**
	 * La largeur du bitmap.
	 */
	public int width;

	/**
	 * La hauteur du bitmap.
	 */
	public int height;

	/**
	 * Construit le bitmap vide.
	 */
	public PortableBitmap() {
		height = 0;
		width = 0;
		table = null;
	}

	/**
	 * Construit le bitmap � partir du tableau et des dimensions donn�e.
	 *
	 * @param t le tableau d'au moins h lignes et ceil(w/8) colonnes.
	 * @param w la largeur.
	 * @param h la hauteur.
	 */
	public PortableBitmap(final byte[][] t, final int w, final int h) {
		if (w < 1 || h < 1 || h > t.length) {
			throw new IndexOutOfBoundsException();
		}
		final int l = (int) StrictMath.ceil((double) w / 8);
		for (int i = 0; i < h; i++) {
			if (t[i].length < l) {
				throw new IndexOutOfBoundsException();
			}
		}
		height = h;
		width = w;
		table = t;
	}

	/**
	 * Construit le bitmap � partir des donn�es dans le fot.
	 *
	 * @param input le flot d'entr�e.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public PortableBitmap(final InputStream input) throws IOException {
		read(input);
	}

	/**
	 * Construit le bitmap vide � partir du tableau et des dimensions donn�e.
	 *
	 * @param w la largeur.
	 * @param h la hauteur.
	 */
	public PortableBitmap(final int w, final int h) {
		this(new byte[h][(int) StrictMath.ceil((double) w / 8)], w, h);
	}

	@Override
	public Object clone() {
		final int w = (int) StrictMath.ceil((double) width / 8);
		final byte[][] t = new byte[height][w];
		for (int i = 0; i < table.length; i++) {
			System.arraycopy(table[i], 0, t[i], 0, t[i].length);
		}
		return new PortableBitmap(t, width, height);
	}

	/**
	 * Dessine le polyg�ne sur le bitmap.
	 *
	 * @param b  la couleur.
	 * @param pg le polyg�ne.
	 */
	public void draw(final boolean b, final Polygon2D pg) {
		int x1, y1, x2, y2;
		x1 = (int) StrictMath.rint(pg.getTop(pg.getNumTops() - 1).getX());
		y1 = (int) StrictMath.rint(pg.getTop(pg.getNumTops() - 1).getY());
		for (int i = 0; i < pg.getNumTops(); i++) {
			x2 = (int) StrictMath.rint(pg.getTop(i).getX());
			y2 = (int) StrictMath.rint(pg.getTop(i).getY());
			drawLine(b, x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}

	/**
	 * Dessine le polyg�ne plein sur le bitmap.
	 *
	 * @param b  la couleur.
	 * @param pg le polyg�ne.
	 */
	public void drawFilled(final boolean b, final Polygon2D pg) {
		draw(b, pg);
		floodFill(b, (int) StrictMath.rint(pg.getCenter().getX()), (int) StrictMath.rint(pg.getCenter().getY()));
	}

	/**
	 * Trace une ligne.
	 *
	 * @param b  la couleur
	 * @param x1 la colonne du point 1
	 * @param y1 la ligne du point 1
	 * @param x2 la colonne du point 2
	 * @param y2 la ligne du point 2
	 */
	public void drawLine(final boolean b, int x1, int y1, final int x2, final int y2) {
		int dx = StrictMath.abs(x2 - x1);
		int dy = StrictMath.abs(y2 - y1);
		int ix, iy;
		if (x1 > x2) {
			ix = -1;
		} else {
			ix = 1;
		}
		if (y1 > y2) {
			iy = -1;
		} else {
			iy = 1;
		}
		if (dx >= dy) {
			final int dpr = dy << 1;
			final int dpru = dpr - (dx << 1);
			int p = dpr - dx;
			for (; dx >= 0; dx--) {
				setValue(b, x1, y1);
				if (p > 0) {
					x1 += ix;
					y1 += iy;
					p += dpru;
				} else {
					x1 += ix;
					p += dpr;
				}
			}
		} else {
			final int dpr = dx << 1;
			final int dpru = dpr - (dy << 1);
			int p = dpr - dy;
			for (; dy >= 0; dy--) {
				setValue(b, x1, y1);
				if (p > 0) {
					x1 += ix;
					y1 += iy;
					p += dpru;
				} else {
					y1 += iy;
					p += dpr;
				}
			}
		}
	}

	@Override
	public boolean equals(final Object object) {
		final PortableBitmap m = (PortableBitmap) object;
		if (table.length != m.table.length) {
			return false;
		}
		for (int i = 0; i < table.length; i++) {
			if (table[i].length != m.table[i].length) {
				return false;
			}
			for (int j = 0; j < table[i].length; j++) {
				if (table[i][j] != m.table[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Trace une ligne.
	 *
	 * @param b la couleur
	 * @param x la colonne du germe
	 * @param y la ligne du germe
	 */
	public void floodFill(final boolean b, final int x, final int y) {
		if (getValue(x, y) == b) {
			return;
		}
		setValue(b, x, y);
		if (x + 1 < width) {
			floodFill(b, x + 1, y);
		}
		if (x - 1 >= 0) {
			floodFill(b, x - 1, y);
		}
		if (y + 1 < height) {
			floodFill(b, x, y + 1);
		}
		if (y - 1 >= 0) {
			floodFill(b, x, y - 1);
		}
	}

	/**
	 * Retourne la valeur du bitmap pour la coordonn�e donn�e.
	 *
	 * @param u la colonne.
	 * @param v la ligne.
	 * @return le bit
	 */
	public boolean getValue(final int u, final int v) {
		if (u < 0 || u >= width || v < 0 || v >= height) {
			throw new IndexOutOfBoundsException();
		}
		return (table[v][u >>> 3] & 128 >>> (u & 7)) != 0;
	}

	/**
	 * Lit la cl� dans le flot.
	 *
	 * @param input le flot d'entr�e.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public void read(final InputStream input) throws IOException {
		if (!readLine(input).equals("P4")) {
			throw new IOException("Invalid format");
		}
		width = Integer.parseInt(readLine(input));
		height = Integer.parseInt(readLine(input));
		final int w = (int) StrictMath.ceil((double) width / 8);
		table = new byte[height][w];
		final int m = 0xFF << (width & 0x07);
		for (int i = 0; i < height; i++) {
			readBytes(input, table[i]);
			table[i][w - 1] &= m;
		}
	}

	/**
	 * Lit un octet dans le flot.
	 *
	 * @param input le flot d'entr�e.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	private byte readByte(final InputStream input) throws IOException {
		final int b = input.read();
		if (b < 0) {
			throw new IOException("Unexpected EOF");
		}
		return (byte) b;
	}

	/**
	 * Lit des octets dans le flot.
	 *
	 * @param input le flot d'entr�e.
	 * @param bytes le tableau d'octets � remplir.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	private void readBytes(final InputStream input, final byte[] bytes) throws IOException {
		int m;
		for (int n = 0; n < bytes.length; n += m) {
			m = input.read(bytes, n, bytes.length - n);
			if (m < 0) {
				throw new IOException("Unexpected EOF");
			}
		}
	}

	/**
	 * Lit une ligne de caract�res dans le flot.
	 *
	 * @param input le flot d'entr�e.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	private String readLine(final InputStream input) throws IOException {
		char c;
		String s = "";
		while ((c = (char) readByte(input)) != '\n') {
			s += c;
		}
		return s;
	}

	/**
	 * Change la valeur du bitmap pour la coordonn�e donn�e.
	 *
	 * @param u la colonne.
	 * @param v la ligne.
	 * @param b la valeur.
	 */
	public void setValue(final boolean b, final int u, final int v) {
		if (u < 0 || u >= width || v < 0 || v >= height) {
			throw new IndexOutOfBoundsException();
		}
		if (b) {
			table[v][u >>> 3] |= 128 >>> (u & 7);
		} else {
			table[v][u >>> 3] &= 127 >>> (u & 7);
		}
	}

	@Override
	public String toString() {
		String s = width + " " + height + "\n";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				s += getValue(j, i) ? "1" : "0";
			}
			s += "\n";
		}
		return s;
	}

	/**
	 * �crit le bitmap dans le flot.
	 *
	 * @param output le flot de sortie.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public void write(final OutputStream output) throws IOException {
		final String header = "P4\n" + width + "\n" + height + "\n";
		output.write(header.getBytes("US-ASCII"));
		for (int i = 0; i < height; i++) {
			output.write(table[i]);
		}
	}

}
