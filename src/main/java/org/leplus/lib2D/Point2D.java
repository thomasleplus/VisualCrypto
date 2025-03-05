package org.leplus.lib2D;

import java.util.Objects;

/**
 * Point.
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
	public Point2D(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Retourne la somme de ce point et point.
	 *
	 * @return le r�sulat.
	 */
	public Point2D add(final Point2D point) {
		return new Point2D(x + point.x, y + point.y);
	}

	@Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point2D other = (Point2D) obj;
        return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x)
                && Double.doubleToLongBits(y) == Double
                        .doubleToLongBits(other.y);
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
	 * Retourne le point oppos� � ce point. (-x, -y)
	 *
	 * @return le point oppos�.
	 */
	public Point2D neg() {
		return new Point2D(-x, -y);
	}

	/**
	 * Retourne la diff�rence de ce point et point.
	 *
	 * @return le r�sulat.
	 */
	public Vector2D subtract(final Point2D point) {
		return new Vector2D(x - point.x, y - point.y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	/**
	 * Retourne le point apr�s la transformation donn�e.
	 *
	 * @param t la matrice de transformation
	 * @return le nouveau point
	 */
	public Point2D transform(final Matrix2D t) {
		return t.multiply(this);
	}

}
