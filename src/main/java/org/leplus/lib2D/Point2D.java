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
 * Point.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public class Point2D {
	
	/**
	 * Le point (0, 0).
	 */
	public static final Point2D ZERO = new Point2D(0, 0);
	
	/**
	 * Le point (1, 1).
	 */
	public static final Point2D ONE = new Point2D(1, 1);
	
	/**
	 * Le point (0, 1).
	 */
	public static final Point2D X = new Point2D(0, 1);
	
	/**
	 * Le point (1, 0).
	 */
	public static final Point2D Y = new Point2D(1, 0);
	
	protected final double x;
	protected final double y;
	
	/**
	 * Construit un point.
	 *
	 * @param x
	 * @param y
	 */
	public Point2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
 	
	/**
	 * Retourne le point oppos� � ce point.
	 * (-x, -y)
	 *
	 * @return le point oppos�.
	 */
	public Point2D neg() {
		return new Point2D(-x, -y);
	}
 	
	/**
	 * Retourne la somme de ce point et point.
	 *
	 * @return le r�sulat.
	 */
	public Point2D add(Point2D point) {
		return new Point2D(x+point.x, y+point.y);
	}
 	
	/**
	 * Retourne la diff�rence de ce point et point.
	 *
	 * @return le r�sulat.
	 */
	public Vector2D subtract(Point2D point) {
		return new Vector2D(x-point.x, y-point.y);
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
	 * Retourne r.
	 *
	 * @return r
	 */
	public double getR() {
		return StrictMath.sqrt(x*x+y*y);
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
	 * Retourne le point apr�s la transformation donn�e.
	 *
	 * @param t la matrice de transformation
	 * @return le nouveau point
	 */
	public Point2D transform(Matrix2D t) {
		return t.multiply(this);
	}
	
	public boolean equals(Object object) {
		Point2D p = (Point2D)object;
		return x == p.x && y == p.y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
