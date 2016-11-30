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

package org.leplus.libcrypto;

import org.leplus.lib2D.Point2D;
import org.leplus.lib2D.Matrix2D;
import org.leplus.lib2D.Polygon2D;
import org.leplus.libimage.PortableBitmap;

import java.util.Hashtable;
import java.util.Random;

/**
 * Oracle de Challenge d'Identification par Masque.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public final class MaskChallengeOracle {
	
	public static final int TRIANGLE =  1;
	public static final int SQUARE   =  2;
	public static final int PENTAGON =  4;
	public static final int HEXAGON  =  8;
	public static final int STAR     = 16;
	public static final int CROSS    = 32;
	public static final int CIRCLE   = 64;
	
	/**
	 * Les réponses des challenges générés.
	 */
	private static final Hashtable responses = new Hashtable();
	
	/**
	 * La source d'aléat.
	 */
	private Random random;
	
	/**
	 * Construit l'oracle.
	 */
	public MaskChallengeOracle() {
		random = new Random();
	}
	
	/**
	 * Génère un challenge pour la clé donnée.
	 *
	 * @param key la clé.
	 * @return le challenge.
	 */
	public MaskChallenge generateChallenge(MaskKey key) {
		double w = key.getWidth();
		double h = key.getHeight();
		PortableBitmap pbm = new PortableBitmap((int)w, (int)h);
		Matrix2D m = Matrix2D.scaling(StrictMath.min(w / 3.3, h / 2.2));
		int[] figs = { TRIANGLE, SQUARE, PENTAGON, HEXAGON, STAR, CROSS, CIRCLE };
		shuffle(figs);
		for (int i = 0; i < 6; i++) {
			Polygon2D p = null;
			switch(figs[i]) {
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
				p = makePolygon((int)(StrictMath.min(w / 3, h / 2)));
				break;
			}
			p.transform(m.rotate(random.nextDouble()).translate(w / 3 * (i % 3) + w / 6, h / 2 * (i / 3) + h / 4));
			pbm.drawFilled(true, p);
		}
		Mask mask = new Mask(pbm);
		if (random.nextBoolean())
			mask = mask.not();
 		MaskChallenge mc = new MaskChallenge(mask.xor(key));
		responses.put(mc, new Integer(figs[6]));
		return mc;
	}
	
	private void shuffle(int[] t) {
		for (int i = 0; i < t.length - 1; i++) {
			int j = (StrictMath.abs(random.nextInt()) % (t.length - i - 1)) + i + 1;
			int k = t[i];
			t[i] = t[j];
			t[j] = k;
		}
	}
	
	private Polygon2D makeStar() {
		Point2D[] points = new Point2D[9];
		points[0] = new Point2D( 0    ,  0    );
		points[1] = new Point2D( 0    ,  0.5  );
		points[2] = new Point2D( 0.125,  0.125);
		points[3] = new Point2D( 0.5  ,  0    );
		points[4] = new Point2D( 0.125, -0.125);
		points[5] = new Point2D( 0    , -0.5  );
		points[6] = new Point2D(-0.125, -0.125);
		points[7] = new Point2D(-0.5  ,  0    );
		points[8] = new Point2D(-0.125,  0.125);
		return new Polygon2D(points);
	}
	
	private Polygon2D makeCross() {
		Point2D[] points = new Point2D[13];
		points[ 0] = new Point2D( 0    ,  0    );
		points[ 1] = new Point2D( 0.125,  0.484);
		points[ 2] = new Point2D( 0.125,  0.125);
		points[ 3] = new Point2D( 0.484,  0.125);
		points[ 4] = new Point2D( 0.484, -0.125);
		points[ 5] = new Point2D( 0.125, -0.125);
		points[ 6] = new Point2D( 0.125, -0.484);
		points[ 7] = new Point2D(-0.125, -0.484);
		points[ 8] = new Point2D(-0.125, -0.125);
		points[ 9] = new Point2D(-0.484, -0.125);
		points[10] = new Point2D(-0.484,  0.125);
		points[11] = new Point2D(-0.125,  0.125);
		points[12] = new Point2D(-0.125,  0.484);
		return new Polygon2D(points);
	}
	
	private Polygon2D makePolygon(int faces) {
		return (new Polygon2D(faces)).transform(Matrix2D.scaling(0.5));
	}
	
	/**
	 * Génère un challenge pour la clé donnée.
	 *
	 * @param challenge le challenge.
	 * @param response la réponse.
	 * @return true si la réponse est correcte, false sinon.
	 */
	public boolean verifyChallenge(MaskChallenge challenge, int response) {
		Integer i = (Integer)responses.get(challenge);
		return i.intValue() == response;
	}
	
}
