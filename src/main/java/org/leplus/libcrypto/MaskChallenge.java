package org.leplus.libcrypto;

import java.io.IOException;
import java.io.InputStream;

/**
 * Challenge d'Identification par Masque.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public final class MaskChallenge extends Mask {

	/**
	 * Construit le challenge de masque � partir des donn�es dans le fot.
	 *
	 * @param input le flot d'entr�e.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public MaskChallenge(final InputStream input) throws IOException {
		super(input);
	}

	/**
	 * Construit le challenge � partir du masque.
	 *
	 * @param m le masque.
	 */
	public MaskChallenge(final Mask m) {
		super(m);
	}

}
