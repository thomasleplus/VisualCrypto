package org.leplus.libcrypto;

import java.io.IOException;
import java.io.InputStream;

/**
 * Clé de Masque Cryptographique.
 */
public final class MaskKey extends Mask {

	/**
	 * Construit la clé à partir des données dans le fot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public MaskKey(final InputStream input) throws IOException {
		super(input);
	}

	/**
	 * Construit la clé à partir du masque.
	 *
	 * @param m le masque.
	 */
	public MaskKey(final Mask m) {
		super(m);
	}

}
