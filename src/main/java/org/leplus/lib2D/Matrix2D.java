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

package org.leplus.lib2D;

/**
 * Matrice 2D.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public class Matrix2D {

	/**
	 * La matrice nulle.
	 *
	 * |0 0| |0 0|
	 *
	 */
	public static final Matrix2D ZERO = new Matrix2D(0, 0);

	/**
	 * La matrice identiti�e.
	 *
	 * |1 0| |0 1|
	 *
	 */
	public static final Matrix2D ONE = new Matrix2D(1, 1);

	/**
	 * La matrice anti-identit�.
	 *
	 * |-1 0| | 0 -1|
	 *
	 */
	public static final Matrix2D NEG = new Matrix2D(-1, -1);

	/**
	 * Retourne une matrice de rotation centr�e � l'origine (sens trigonom�trique).
	 *
	 * @param t angle de rotation.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D rotation(final double t) {
		return new Matrix2D(StrictMath.cos(t), -StrictMath.sin(t), StrictMath.sin(t), StrictMath.cos(t));
	}

	/**
	 * Retourne une matrice de rotation (sens trigonom�trique).
	 *
	 * @param c centre de rotation.
	 * @param t angle de rotation.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D rotation(final Point2D c, final double t) {
		return translation(new Vector2D(c, Point2D.ZERO)).rotate(t).translate(new Vector2D(Point2D.ZERO, c));
	}

	/**
	 * Retourne une matrice de changement d'�chelle.
	 *
	 * @param k changement d'�chelle.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D scaling(final double k) {
		return scaling(k, k);
	}

	/**
	 * Retourne une matrice de changement d'�chelle.
	 *
	 * @param x changement d'�chelle en x.
	 * @param y changement d'�chelle en y.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D scaling(final double x, final double y) {
		return new Matrix2D(x, y);
	}

	/**
	 * Retourne une matrice de changement d'�chelle.
	 *
	 * @param vector le vecteur de changement d'�chelle.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D scaling(final Vector2D vector) {
		return scaling(vector.x, vector.y);
	}

	/**
	 * Retourne une matrice de translation.
	 *
	 * @param x changement d'�chelle en x.
	 * @param y changement d'�chelle en y.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D translation(final double x, final double y) {
		return new Matrix2D(1, 0, x, 0, 1, y, 0, 0, 1);
	}

	/**
	 * Retourne une matrice de translation.
	 *
	 * @param vector le vecteur de changement d'�chelle.
	 * @return la matrice de transformation.
	 */
	public static Matrix2D translation(final Vector2D vector) {
		return translation(vector.x, vector.y);
	}

	protected final double[] m;

	/**
	 * Construit une matrice 2D avec les valeurs :
	 *
	 * |a 0| |0 b|
	 *
	 * @param a
	 * @param b
	 */
	protected Matrix2D(final double a, final double b) {
		this(a, 0, 0, b);
	}

	/**
	 * Construit une matrice 2D avec les valeurs :
	 *
	 * |a b| |c d|
	 *
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	protected Matrix2D(final double a, final double b, final double c, final double d) {
		this(a, b, 0, c, d, 0, 0, 0, 1);
	}

	/**
	 * Construit une matrice 2D avec les valeurs :
	 *
	 * |a b c| |d e f| |g h i|
	 *
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @param f
	 * @param g
	 * @param h
	 * @param i
	 */
	protected Matrix2D(final double a, final double b, final double c, final double d, final double e, final double f,
			final double g, final double h, final double i) {
		m = new double[9];
		m[0] = a;
		m[1] = b;
		m[2] = c;
		m[3] = d;
		m[4] = e;
		m[5] = f;
		m[6] = g;
		m[7] = h;
		m[8] = i;
	}

	/**
	 * Retourne la matrice de transformation correspondant � appliquer cette matrice
	 * puis matrix.
	 *
	 * @param matrix
	 * @return la composition.
	 */
	public Matrix2D compose(final Matrix2D matrix) {
		return matrix.multiply(this);
	}

	@Override
	public boolean equals(final Object object) {
		final Matrix2D n = (Matrix2D) object;
		return m[0] == n.m[0] && m[1] == n.m[1] && m[2] == n.m[2] && m[3] == n.m[3] && m[4] == n.m[4] && m[5] == n.m[5]
				&& m[6] == n.m[6] && m[7] == n.m[7] && m[8] == n.m[8];
	}

	/**
	 * Retourne la matrice de transformation inverse de cette matrice.
	 *
	 * @return l'inverse.
	 */
	public Matrix2D inverse() {
		// Gauss-Jordan
		final double[] t = new double[18];
		t[0] = m[0];
		t[1] = m[1];
		t[2] = m[2];
		t[3] = 1;
		t[4] = 0;
		t[5] = 0;
		t[6] = m[3];
		t[7] = m[4];
		t[8] = m[5];
		t[9] = 0;
		t[10] = 1;
		t[11] = 0;
		t[12] = m[6];
		t[13] = m[7];
		t[14] = m[8];
		t[15] = 0;
		t[16] = 0;
		t[17] = 1;
		for (int k = 0; k < 3; k++) {
			for (int j = 5; j >= k; j--) {
				t[k * 6 + j] /= t[k * 7];
			}
			for (int i = 0; i < 3; i++) {
				if (i != k) {
					for (int j = 5; j >= k; j--) {
						t[i * 6 + j] -= t[i * 6 + k] * t[k * 6 + j];
					}
				}
			}
		}
		return new Matrix2D(t[3], t[4], t[5], t[9], t[10], t[11], t[15], t[16], t[17]);
	}

	/**
	 * Retourne cette matrice * double.
	 *
	 * @return le r�sultat.
	 */
	protected Matrix2D multiply(final double d) {
		return new Matrix2D(m[0] * d, m[1] * d, m[2] * d, m[3] * d, m[4] * d, m[5] * d, m[6] * d, m[7] * d, m[8] * d);
	}

	/**
	 * Retourne cette matrice * matrix.
	 *
	 * @return le r�sultat.
	 */
	protected Matrix2D multiply(final Matrix2D matrix) {
		return new Matrix2D(m[0] * matrix.m[0] + m[1] * matrix.m[3] + m[2] * matrix.m[6],
				m[0] * matrix.m[1] + m[1] * matrix.m[4] + m[2] * matrix.m[7],
				m[0] * matrix.m[2] + m[1] * matrix.m[5] + m[2] * matrix.m[8],
				m[3] * matrix.m[0] + m[4] * matrix.m[3] + m[5] * matrix.m[6],
				m[3] * matrix.m[1] + m[4] * matrix.m[4] + m[5] * matrix.m[7],
				m[3] * matrix.m[2] + m[4] * matrix.m[5] + m[5] * matrix.m[8],
				m[6] * matrix.m[0] + m[7] * matrix.m[3] + m[8] * matrix.m[6],
				m[6] * matrix.m[1] + m[7] * matrix.m[4] + m[8] * matrix.m[7],
				m[6] * matrix.m[2] + m[7] * matrix.m[5] + m[8] * matrix.m[8]);
	}

	/**
	 * Retourne cette matrice * point.
	 *
	 * @return le r�sultat.
	 */
	protected Point2D multiply(final Point2D point) {
		return new Point2D((m[0] * point.x + m[1] * point.y + m[2]) / (m[6] * point.x + m[7] * point.y + m[8]),
				(m[3] * point.x + m[4] * point.y + m[5]) / (m[6] * point.x + m[7] * point.y + m[8]));
	}

	/**
	 * Retourne cette matrice * vector.
	 *
	 * @return le r�sultat.
	 */
	protected Vector2D multiply(final Vector2D vector) {
		return new Vector2D((m[0] * vector.x + m[1] * vector.y + m[2]) / (m[6] * vector.x + m[7] * vector.y + m[8]),
				(m[3] * vector.x + m[4] * vector.y + m[5]) / (m[6] * vector.x + m[7] * vector.y + m[8]));
	}

	/**
	 * Retourne cette matrice compos�e avec une rotation centr�e � l'origine (sens
	 * trigonom�trique).
	 *
	 * @param t angle de rotation.
	 * @return la matrice de transformation.
	 */
	public Matrix2D rotate(final double t) {
		return compose(rotation(t));
	}

	/**
	 * Retourne cette matrice compos�e avec une rotation (sens trigonom�trique).
	 *
	 * @param c centre de rotation.
	 * @param t angle de rotation.
	 * @return la matrice de transformation.
	 */
	public Matrix2D rotate(final Point2D c, final double t) {
		return compose(rotation(c, t));
	}

	/**
	 * Retourne cette matrice compos�e avec un changement d'�chelle.
	 *
	 * @param k changement d'�chelle.
	 * @return la matrice de transformation.
	 */
	public Matrix2D scale(final double k) {
		return compose(scaling(k));
	}

	/**
	 * Retourne cette matrice compos�e avec un changement d'�chelle.
	 *
	 * @param x changement d'�chelle en x.
	 * @param y changement d'�chelle en y.
	 * @return la matrice de transformation.
	 */
	public Matrix2D scale(final double x, final double y) {
		return compose(scaling(x, y));
	}

	/**
	 * Retourne cette matrice compos�e avec un changement d'�chelle.
	 *
	 * @param vector le vecteur de changement d'�chelle.
	 * @return la matrice de transformation.
	 */
	public Matrix2D scale(final Vector2D vector) {
		return compose(scaling(vector));
	}

	@Override
	public String toString() {
		return m[0] + " " + m[1] + " " + m[2] + "\n" + m[3] + " " + m[4] + " " + m[5] + "\n" + m[6] + " " + m[7] + " "
				+ m[8] + "\n";
	}

	/**
	 * Retourne cette matrice compos�e avec une translation.
	 *
	 * @param x changement d'�chelle en x.
	 * @param y changement d'�chelle en y.
	 * @return la matrice de transformation.
	 */
	public Matrix2D translate(final double x, final double y) {
		return compose(translation(x, y));
	}

	/**
	 * Retourne cette matrice compos�e avec une translation.
	 *
	 * @param vector le vecteur de changement d'�chelle.
	 * @return la matrice de transformation.
	 */
	public Matrix2D translate(final Vector2D vector) {
		return compose(translation(vector));
	}

	/**
	 * Retourne la transpos�e de cette matrice.
	 *
	 * @return la transpos�e.
	 */
	public Matrix2D transpose() {
		return new Matrix2D(m[0], m[3], m[6], m[1], m[4], m[7], m[2], m[5], m[8]);
	}

}
