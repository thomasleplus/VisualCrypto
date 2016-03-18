/* $Id: Key.java,v 1.2 2003/03/25 22:30:35 leplusth Exp $ */

package ift6350.keygen;

import ift6350.libimage.PortableBitmap;

import ift6350.libcrypto.Mask;
import ift6350.libcrypto.MaskKeyGenerator;

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
