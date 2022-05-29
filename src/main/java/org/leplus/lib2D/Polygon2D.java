package org.leplus.lib2D;

import java.util.Arrays;

/**
 * Polygone g�n�rique.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public class Polygon2D {

	/**
	 * Un triangle �quilat�ral de c�t� 1. (0.0, 1.0/sqrt(3)) (-0.5, -0.5/sqrt(3))
	 * (0.5, -0.5/sqrt(3))
	 */
	public static final Polygon2D TRIANGLE = new Polygon2D(new Point2D(0, 0), new Point2D(0, 1 / StrictMath.sqrt(3)),
			new Point2D(-0.5, -0.5 / StrictMath.sqrt(3)), new Point2D(0.5, -0.5 / StrictMath.sqrt(3)));

	/**
	 * Un carr� de c�t� 1. (0.5, 0.5) (-0.5, 0.5) (-0.5, -0.5) (0.5, -0.5)
	 */
	public static final Polygon2D SQUARE = new Polygon2D(new Point2D(0, 0), new Point2D(0.5, 0.5),
			new Point2D(-0.5, 0.5), new Point2D(-0.5, -0.5), new Point2D(0.5, -0.5));

	/**
	 * Les sommets du polygone.
	 */
	protected Point2D[] points;

	/**
	 * Construit un polygone r�gulier centr� en (0, 0) et de n sommets (au moins 3),
	 * le premier sommet �tant toujours plac� en (0, 1) et les suivants dans le sens
	 * trigonom�trique.
	 *
	 * @param n le nombre de sommets du polygone (au moins 3).
	 */
	public Polygon2D(final int n) {
		if (n < 3) {
			throw new IndexOutOfBoundsException();
		}
		points = new Point2D[n + 1];
		points[0] = new Point2D(0, 0);
		points[1] = new Point2D(0, 1);
		final Matrix2D t = Matrix2D.rotation(2 * StrictMath.PI / n);
		for (int i = 2; i <= n; i++) {
			points[i] = points[i - 1].transform(t);
		}
	}

	/**
	 * Construit un polygone avec les trois sommets donn�s.
	 */
	public Polygon2D(final Point2D center, final Point2D a, final Point2D b, final Point2D c) {
		points = new Point2D[4];
		points[0] = center;
		points[1] = a;
		points[2] = b;
		points[3] = c;
	}

	/**
	 * Construit un polygone avec les quatre sommets donn�s.
	 */
	public Polygon2D(final Point2D center, final Point2D a, final Point2D b, final Point2D c, final Point2D d) {
		points = new Point2D[5];
		points[0] = center;
		points[1] = a;
		points[2] = b;
		points[3] = c;
		points[4] = d;
	}

	/**
	 * Construit un polygone dont les sommets sont les points donn�s (le premier
	 * point est le centre et les points suivants sont les sommets successifs).
	 *
	 * @param p les points du polygone.
	 */
	public Polygon2D(final Point2D[] t) {
		points = t;
	}

	@Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(points);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Polygon2D other = (Polygon2D) obj;
        return Arrays.equals(points, other.points);
    }

	/**
	 * Retourne le centre du polygone.
	 *
	 * @return le centre du polygone.
	 */
	public Point2D getCenter() {
		return points[0];
	}

	/**
	 * Retourne le nombre de sommets du polygone.
	 *
	 * @return le nombre de sommets du polygone.
	 */
	public int getNumTops() {
		return points.length - 1;
	}

	/**
	 * Retourne le ni�me sommet du polygone.
	 *
	 * @return le ni�me sommet du polygone.
	 */
	public Point2D getTop(final int n) {
		if (n < 0) {
			throw new IndexOutOfBoundsException();
		}
		return points[n + 1];
	}

	@Override
	public String toString() {
		String s = "center: " + getCenter() + "\n" + getNumTops() + " tops:\n";
		for (int i = 0; i < getNumTops(); i++) {
			s += "   " + getTop(i) + "\n";
		}
		return s;
	}

	/**
	 * Applique la matrice de transformation donn�e au polygone et retourne le
	 * r�sultat.
	 *
	 * @param m la matrice de transformation.
	 * @return le r�sultat.
	 */
	public Polygon2D transform(final Matrix2D m) {
		final Polygon2D n = new Polygon2D(points);
		for (int i = 0; i < n.points.length; i++) {
			n.points[i] = n.points[i].transform(m);
		}
		return n;
	}

}
