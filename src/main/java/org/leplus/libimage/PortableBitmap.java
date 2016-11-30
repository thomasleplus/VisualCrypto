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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.leplus.lib2D.Polygon2D;

/**
 * Portable Bitmap.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public class PortableBitmap
	implements Cloneable {
	
	/**
	 * Les données du bitmap.
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
	 * Construit le bitmap à partir du tableau et des dimensions donnée.
	 *
	 * @param t le tableau d'au moins h lignes et ceil(w/8) colonnes.
	 * @param w la largeur.
	 * @param h la hauteur.
	 */
	public PortableBitmap(byte[][] t, int w, int h) {
		if (w < 1 || h < 1 || h > t.length)
			throw new IndexOutOfBoundsException();
		int l = (int)StrictMath.ceil((double)w / 8);
		for (int i = 0; i < h; i++)
			if (t[i].length < l)
				throw new IndexOutOfBoundsException();
		height = h;
		width = w;
		table = t;
	}
	
	/**
	 * Construit le bitmap vide à partir du tableau et des dimensions donnée.
	 *
	 * @param w la largeur.
	 * @param h la hauteur.
	 */
	public PortableBitmap(int w, int h) {
		this(new byte[h][(int)StrictMath.ceil((double)w / 8)], w, h);
	}
	
	/**
	 * Construit le bitmap vide.
	 */
	public PortableBitmap() {
		height = 0;
		width = 0;
		table = null;
	}
	
	/**
	 * Construit le bitmap à partir des données dans le fot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public PortableBitmap(InputStream input)
		throws IOException {
		read(input);
	}
	
	/**
	 * Retourne la valeur du bitmap pour la coordonnée donnée.
	 *
	 * @param u la colonne.
	 * @param v la ligne.
	 * @return le bit
	 */
	public boolean getValue(int u, int v) {
		if (u < 0 || u >= width || v < 0 || v >= height)
			throw new IndexOutOfBoundsException();
		return (table[v][u >>> 3] & (128 >>> (u & 7))) != 0;
	}
	
	/**
	 * Change la valeur du bitmap pour la coordonnée donnée.
	 *
	 * @param u la colonne.
	 * @param v la ligne.
	 * @param b la valeur.
	 */
	public void setValue(boolean b, int u, int v) {
		if (u < 0 || u >= width || v < 0 || v >= height)
			throw new IndexOutOfBoundsException();
		if (b) table[v][u >>> 3] |= 128 >>> (u & 7);
		else   table[v][u >>> 3] &= 127 >>> (u & 7);
	}
	
	/**
	 * Lit la clé dans le flot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public void read(InputStream input)
		throws IOException {
		if (!readLine(input).equals("P4"))
			throw new IOException("Invalid format");
		width = Integer.parseInt(readLine(input));
		height = Integer.parseInt(readLine(input));
		int w = (int)StrictMath.ceil((double)width / 8);
		table = new byte[height][w];
		int m = 0xFF << (width & 0x07);
		for (int i = 0; i < height; i++) {
			readBytes(input, table[i]);
			table[i][w - 1] &= m;
		}
	}
	
	/**
	 * Lit un octet dans le flot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	private byte readByte(InputStream input)
		throws IOException {
		int b = input.read();
		if (b < 0)
			throw new IOException("Unexpected EOF");
		return (byte)b;
	}
	
	/**
	 * Lit des octets dans le flot.
	 *
	 * @param input le flot d'entrée.
	 * @param bytes le tableau d'octets à remplir.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	private void readBytes(InputStream input, byte[] bytes)
		throws IOException {
		int m;
		for (int n = 0; n < bytes.length; n += m) {
			m = input.read(bytes, n, bytes.length - n);
			if (m < 0)
				throw new IOException("Unexpected EOF");
		}
	}
	
	/**
	 * Lit une ligne de caractères dans le flot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	private String readLine(InputStream input)
		throws IOException {
		char c;
		String s = "";
		while ((c = (char)readByte(input)) != '\n') s += c;
		return s;
	}
	
	/**
	 * Écrit le bitmap dans le flot.
	 *
	 * @param output le flot de sortie.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public void write(OutputStream output)
		throws IOException {
		String header = "P4\n" + width + "\n" + height + "\n";
		output.write(header.getBytes("US-ASCII"));
		for (int i = 0; i < height; i++)
			output.write(table[i]);
	}
	
	/**
	 * Trace une ligne.
	 *
	 * @param b la couleur
	 * @param x1 la colonne du point 1
	 * @param y1 la ligne du point 1
	 * @param x2 la colonne du point 2
	 * @param y2 la ligne du point 2
	 */
	public void drawLine(boolean b, int x1, int y1, int x2, int y2) {
		int dx = StrictMath.abs(x2 - x1);
		int dy = StrictMath.abs(y2 - y1);
		int ix, iy;
		if (x1 > x2) ix = -1; else ix = 1;
		if (y1 > y2) iy = -1; else iy = 1;
		if (dx >= dy) {           
			int dpr = dy << 1;
			int dpru = dpr - (dx << 1);
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
			int dpr = dx << 1;
			int dpru = dpr - (dy << 1);
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
	
	/**
	 * Trace une ligne.
	 *
	 * @param b la couleur
	 * @param x la colonne du germe
	 * @param y la ligne du germe
	 */
	public void floodFill(boolean b, int x, int y) {
		if (getValue(x, y) == b) return;
		setValue(b, x, y);
		if (x + 1 < width) floodFill(b, x + 1, y);
		if (x - 1 >= 0) floodFill(b, x - 1, y);
		if (y + 1 < height) floodFill(b, x, y + 1);
		if (y - 1 >= 0) floodFill(b, x, y - 1);
	}
	
	/**
	 * Dessine le polygône sur le bitmap.
	 *
	 * @param b la couleur.
	 * @param pg le polygône.
	 */
	public void draw(boolean b, Polygon2D pg) {
		int x1, y1, x2, y2;
		x1 = (int)StrictMath.rint(pg.getTop(pg.getNumTops() - 1).getX());
		y1 = (int)StrictMath.rint(pg.getTop(pg.getNumTops() - 1).getY());
		for (int i = 0; i < pg.getNumTops(); i++) {
			x2 = (int)StrictMath.rint(pg.getTop(i).getX());
			y2 = (int)StrictMath.rint(pg.getTop(i).getY());
			drawLine(b, x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}
	
	/**
	 * Dessine le polygône plein sur le bitmap.
	 *
	 * @param b la couleur.
	 * @param pg le polygône.
	 */
	public void drawFilled(boolean b, Polygon2D pg) {
		draw(b, pg);
		floodFill(b, (int)StrictMath.rint(pg.getCenter().getX()), (int)StrictMath.rint(pg.getCenter().getY()));
	}
	
	public String toString() {
		String s = width + " " + height + "\n";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++)
				s += getValue(j, i) ? "1" : "0";
			s += "\n";
		}
		return s;
	}
	
	public Object clone() {
		int w = (int)StrictMath.ceil((double)width / 8);
		byte[][] t = new byte[height][w];
		for (int i = 0; i < table.length; i++)
			System.arraycopy(table[i], 0, t[i], 0, t[i].length);
		return new PortableBitmap(t, width, height);
	}
	
	public boolean equals(Object object) {
		PortableBitmap m = (PortableBitmap)object;
		if (table.length != m.table.length)
			return false;
		for (int i = 0; i < table.length; i++) {
			if (table[i].length != m.table[i].length)
				return false;
			for (int j = 0; j < table[i].length; j++)
				if (table[i][j] != m.table[i][j])
					return false;
		}
		return true;
	}
	
}
