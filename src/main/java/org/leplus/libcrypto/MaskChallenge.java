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
