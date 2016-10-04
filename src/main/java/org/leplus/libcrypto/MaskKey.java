/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package org.leplus.libcrypto;

import java.io.InputStream;
import java.io.IOException;

/**
 * Clé de Masque Cryptographique.
 *
 * @version $Revision: 1.2 $
 * @author  Thomas Leplus <thomas@leplus.org>
 */
public final class MaskKey
	extends Mask {
	
	/**
	 * Construit la clé à partir du masque.
	 *
	 * @param m le masque.
	 */
	public MaskKey(Mask m) {
		super(m);
	}
	
	/**
	 * Construit la clé à partir des données dans le fot.
	 *
	 * @param input le flot d'entrée.
	 * @throws IOException si une erreure se produit dans le flot.
	 */
	public MaskKey(InputStream input)
		throws IOException {
		super(input);
	}
	
}
