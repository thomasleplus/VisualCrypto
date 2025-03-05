package org.leplus.libcrypto;

import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.Random;

import org.leplus.lib2D.Matrix2D;
import org.leplus.lib2D.Point2D;
import org.leplus.lib2D.Polygon2D;
import org.leplus.libimage.PortableBitmap;

/**
 * Oracle de Challenge d'Identification par Masque.
 */
public final class MaskChallengeOracle {

	public static final int TRIANGLE = 1;
	public static final int SQUARE = 2;
	public static final int PENTAGON = 4;
	public static final int HEXAGON = 8;
	public static final int STAR = 16;
	public static final int CROSS = 32;
	public static final int CIRCLE = 64;

	/**
	 * Les r�ponses des challenges g�n�r�s.
	 */
	private static final Hashtable responses = new Hashtable();

	/**
	 * La source d'al�at.
	 */
	private final Random random;

	/**
	 * Construit l'oracle.
	 */
	public MaskChallengeOracle() {
		random = new SecureRandom();
	}

	/**
	 * G�n�re un challenge pour la cl� donn�e.
	 *
	 * @param key la cl�.
	 * @return le challenge.
	 */
	public MaskChallenge generateChallenge(final MaskKey key) {
		final double w = key.getWidth();
		final double h = key.getHeight();
		final PortableBitmap pbm = new PortableBitmap((int) w, (int) h);
		final Matrix2D m = Matrix2D.scaling(StrictMath.min(w / 3.3, h / 2.2));
		final int[] figs = { TRIANGLE, SQUARE, PENTAGON, HEXAGON, STAR, CROSS, CIRCLE };
		shuffle(figs);
		for (int i = 0; i < 6; i++) {
			Polygon2D p = null;
			switch (figs[i]) {
			case TRIANGLE:
				p = makePolygon(3);
				break;
			case SQUARE:
				p = makePolygon(4);
				break;
			case PENTAGON:
				p = makePolygon(5);
				break;
			case HEXAGON:
				p = makePolygon(6);
				break;
			case STAR:
				p = makeStar();
				break;
			case CROSS:
				p = makeCross();
				break;
			case CIRCLE:
				p = makePolygon((int) StrictMath.min(w / 3, h / 2));
				break;
			default:
			    throw new IllegalStateException("Unexpected figure " + figs[i]);
			}
			p = p.transform(m.rotate(random.nextDouble()).translate(w / 3.0 * (i % 3) + w / 6.0, h / 2.0 * (i / 3.0) + h / 4.0));
			pbm.drawFilled(true, p);
		}
		Mask mask = new Mask(pbm);
		if (random.nextBoolean()) {
			mask = mask.not();
		}
		final MaskChallenge mc = new MaskChallenge(mask.xor(key));
		responses.put(mc, figs[6]);
		return mc;
	}

	private Polygon2D makeCross() {
		final Point2D[] points = new Point2D[13];
		points[0] = new Point2D(0, 0);
		points[1] = new Point2D(0.125, 0.484);
		points[2] = new Point2D(0.125, 0.125);
		points[3] = new Point2D(0.484, 0.125);
		points[4] = new Point2D(0.484, -0.125);
		points[5] = new Point2D(0.125, -0.125);
		points[6] = new Point2D(0.125, -0.484);
		points[7] = new Point2D(-0.125, -0.484);
		points[8] = new Point2D(-0.125, -0.125);
		points[9] = new Point2D(-0.484, -0.125);
		points[10] = new Point2D(-0.484, 0.125);
		points[11] = new Point2D(-0.125, 0.125);
		points[12] = new Point2D(-0.125, 0.484);
		return new Polygon2D(points);
	}

	private Polygon2D makePolygon(final int faces) {
		return new Polygon2D(faces).transform(Matrix2D.scaling(0.5));
	}

	private Polygon2D makeStar() {
		final Point2D[] points = new Point2D[9];
		points[0] = new Point2D(0, 0);
		points[1] = new Point2D(0, 0.5);
		points[2] = new Point2D(0.125, 0.125);
		points[3] = new Point2D(0.5, 0);
		points[4] = new Point2D(0.125, -0.125);
		points[5] = new Point2D(0, -0.5);
		points[6] = new Point2D(-0.125, -0.125);
		points[7] = new Point2D(-0.5, 0);
		points[8] = new Point2D(-0.125, 0.125);
		return new Polygon2D(points);
	}

	private void shuffle(final int[] t) {
		for (int i = 0; i < t.length - 1; i++) {
			final int j = StrictMath.abs(random.nextInt(Integer.MAX_VALUE)) % (t.length - i - 1) + i + 1;
			final int k = t[i];
			t[i] = t[j];
			t[j] = k;
		}
	}

	/**
	 * G�n�re un challenge pour la cl� donn�e.
	 *
	 * @param challenge le challenge.
	 * @param response  la r�ponse.
	 * @return true si la r�ponse est correcte, false sinon.
	 */
	public boolean verifyChallenge(final MaskChallenge challenge, final int response) {
		final Integer i = (Integer) responses.get(challenge);
		return i.intValue() == response;
	}

}
