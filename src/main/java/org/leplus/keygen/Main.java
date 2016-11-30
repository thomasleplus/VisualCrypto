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

import org.leplus.libimage.PortableBitmap;

import org.leplus.libcrypto.Mask;
import org.leplus.libcrypto.MaskKeyGenerator;

import java.io.IOException;
import java.io.FileOutputStream;

public final class Main {
	
	public static MaskKeyGenerator gen = new MaskKeyGenerator(75, 50);
	
	public static void main(String[] args)
		throws IOException {
		for (int i = 0; i < args.length; i++) {
			Mask mask = gen.generateKey();
			FileOutputStream key = new FileOutputStream(args[i] + ".key");
			mask.write(key);
			key.close();
			FileOutputStream pbm = new FileOutputStream(args[i] + ".pbm");
			mask.print(pbm);
			pbm.close();
		}
	}
	
}
