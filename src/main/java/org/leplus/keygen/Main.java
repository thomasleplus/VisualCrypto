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

package org.leplus.keygen;

import java.io.FileOutputStream;
import java.io.IOException;

import org.leplus.libcrypto.Mask;
import org.leplus.libcrypto.MaskKeyGenerator;

public final class Main {

	public static MaskKeyGenerator gen = new MaskKeyGenerator(75, 50);

	public static void main(final String[] args) throws IOException {
		for (int i = 0; i < args.length; i++) {
			final Mask mask = gen.generateKey();
			final FileOutputStream key = new FileOutputStream(args[i] + ".key");
			mask.write(key);
			key.close();
			final FileOutputStream pbm = new FileOutputStream(args[i] + ".pbm");
			mask.print(pbm);
			pbm.close();
		}
	}

}
