/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package ift6350.lib2D;

/**
 * Vecteur.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
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
	protected Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Construit un vecteur A -> B.
	 *
	 * @param a l'origine.
	 * @param b la destination.
	 */
	protected Vector2D(Point2D a, Point2D b) {
		x = b.x - a.x;
		y = b.y - a.y;
	}
 	
	/**
	 * Retourne le produit scalaire de ce vecteur et vector.
	 *
	 * @param vector un vecteur.
	 * @return le produit scalaire.
	 */
	public double dot(Vector2D vector) {
		return x*vector.x+y*vector.y;
	}
 	
	/**
	 * Retourne un vecteur perpendiculaire à ce vecteur.
	 * (-y, x)
	 *
	 * @return un vecteur perpendiculaire.
	 */
	public Vector2D perp() {
		return new Vector2D(-y, x);
	}
 	
	/**
	 * Retourne le vecteur opposé à ce vecteur.
	 * (-x, -y)
	 *
	 * @return le vecteur opposé.
	 */
	public Vector2D neg() {
		return new Vector2D(-x, -y);
	}
 	
	/**
	 * Retourne la somme de ce vecteur et vector.
	 *
	 * @return le résulat.
	 */
	public Vector2D add(Vector2D vector) {
		return new Vector2D(x+vector.x, y+vector.y);
	}
 	
	/**
	 * Retourne la différence de ce vecteur et vector.
	 *
	 * @return le résulat.
	 */
	public Vector2D subtract(Vector2D vector) {
		return new Vector2D(x-vector.x, y-vector.y);
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
	 * Retourne le vecteur après la transformation donnée.
	 *
	 * @param t la matrice de transformation
	 * @return le nouveau vecteur
	 */
	public Vector2D transform(Matrix2D t) {
		return t.multiply(new Point2D(x, y)).subtract(t.multiply(Point2D.ZERO));
	}
	
	public boolean equals(Object object) {
		Vector2D p = (Vector2D)object;
		return x == p.x && y == p.y;
	}
	
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
