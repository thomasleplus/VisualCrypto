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
