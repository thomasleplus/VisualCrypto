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
 * Vecteur.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public class Vector2D {

	/**
	 * Le vecteur (0, 0).
	 */
	public static final Vector2D ZERO = new Vector2D(0, 0);

	/**
	 * Le vecteur (1, 1).
	 */
	public static final Vector2D ONE = new Vector2D(1, 1);

	/**
	 * Le vacteur (0, 1).
	 */
	public static final Vector2D X = new Vector2D(0, 1);

	/**
	 * Le vecteur (1, 0).
	 */
	public static final Vector2D Y = new Vector2D(1, 0);

	protected final double x;
	protected final double y;

	/**
	 * Construit un vecteur.
	 *
	 * @param x
	 * @param y
	 */
	protected Vector2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Construit un vecteur A -> B.
	 *
	 * @param a l'origine.
	 * @param b la destination.
	 */
	protected Vector2D(final Point2D a, final Point2D b) {
		x = b.x - a.x;
		y = b.y - a.y;
	}

	/**
	 * Retourne la somme de ce vecteur et vector.
	 *
	 * @return le r�sulat.
	 */
	public Vector2D add(final Vector2D vector) {
		return new Vector2D(x + vector.x, y + vector.y);
	}

	/**
	 * Retourne le produit scalaire de ce vecteur et vector.
	 *
	 * @param vector un vecteur.
	 * @return le produit scalaire.
	 */
	public double dot(final Vector2D vector) {
		return x * vector.x + y * vector.y;
	}

	@Override
	public boolean equals(final Object object) {
		final Vector2D p = (Vector2D) object;
		return x == p.x && y == p.y;
	}

	/**
	 * Retourne r.
	 *
	 * @return r
	 */
	public double getR() {
		return StrictMath.sqrt(x * x + y * y);
	}

	/**
	 * Retourne theta (en radians compris en -pi et pi).
	 *
	 * @return theta
	 */
	public double getT() {
		return StrictMath.atan2(y, x);
	}

	/**
	 * Retourne x.
	 *
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Retourne y.
	 *
	 * @return y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Retourne le vecteur oppos� � ce vecteur. (-x, -y)
	 *
	 * @return le vecteur oppos�.
	 */
	public Vector2D neg() {
		return new Vector2D(-x, -y);
	}

	/**
	 * Retourne un vecteur perpendiculaire � ce vecteur. (-y, x)
	 *
	 * @return un vecteur perpendiculaire.
	 */
	public Vector2D perp() {
		return new Vector2D(-y, x);
	}

	/**
	 * Retourne la diff�rence de ce vecteur et vector.
	 *
	 * @return le r�sulat.
	 */
	public Vector2D subtract(final Vector2D vector) {
		return new Vector2D(x - vector.x, y - vector.y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * Retourne le vecteur apr�s la transformation donn�e.
	 *
	 * @param t la matrice de transformation
	 * @return le nouveau vecteur
	 */
	public Vector2D transform(final Matrix2D t) {
		return t.multiply(new Point2D(x, y)).subtract(t.multiply(Point2D.ZERO));
	}

}
