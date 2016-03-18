/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package ift6350.libcrypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Challenge d'Identification par Masque.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public final class MaskChallenge
	extends Mask {
	
	/**
	 * Construit le challenge à partir du masque.
	 *
	 * @param m le masque.
	 */
	public MaskChallenge(Mask m) {
		super(m);
	}
	
	/**
	 * Construit le challenge de masque à partir des données dans le fot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public MaskChallenge(InputStream input)
		throws IOException {
		super(input);
	}
	
}
