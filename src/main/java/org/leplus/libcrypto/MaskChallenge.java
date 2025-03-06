package org.leplus.libcrypto;

import java.io.IOException;
import java.io.InputStream;

/**
 * Challenge d'Identification par Masque.
 */
public final class MaskChallenge extends Mask {

	/**
	 * Construit le challenge de masque à partir des données dans le fot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public MaskChallenge(final InputStream input) throws IOException {
		super(input);
	}

	/**
	 * Construit le challenge à partir du masque.
	 *
	 * @param m le masque.
	 */
	public MaskChallenge(final Mask m) {
		super(m);
	}

}
