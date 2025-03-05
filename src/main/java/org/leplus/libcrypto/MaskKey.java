package org.leplus.libcrypto;

import java.io.IOException;
import java.io.InputStream;

/**
 * Cl� de Masque Cryptographique.
 */
public final class MaskKey extends Mask {

	/**
	 * Construit la cl� � partir des donn�es dans le fot.
	 *
	 * @param input le flot d'entr�e.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public MaskKey(final InputStream input) throws IOException {
		super(input);
	}

	/**
	 * Construit la cl� � partir du masque.
	 *
	 * @param m le masque.
	 */
	public MaskKey(final Mask m) {
		super(m);
	}

}
