package org.leplus.libcrypto;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.leplus.libimage.PortableBitmap;

/**
 * Masque cryptographique.
 *
 * @version $Revision: 1.2 $
 * @author Thomas Leplus <thomas@leplus.org>
 */
public class Mask {

    /**
     * Le bitmap de ce masque.
     */
    private final PortableBitmap bmp;

    /**
     * Construit le masque � partir des donn�es dans le fot.
     *
     * @param input le flot d'entr�e.
     * @throws IOException si une erreure se produit dans le flot.
     */
    public Mask(final InputStream input) throws IOException {
        bmp = new PortableBitmap();
        bmp.read(input);
        trim();
    }

    /**
     * Construit le masque � partir du masque.
     *
     * @param mask le masque
     */
    public Mask(final Mask mask) {
        bmp = mask.bmp;
    }

    /**
     * Construit le masque � partir du bitmap.
     *
     * @param pbm le bitmap
     */
    public Mask(final PortableBitmap pbm) {
        bmp = (PortableBitmap) pbm.clone();
        trim();
    }

    /**
     * Retourne le ET du masque et de l'argument.
     *
     * @param mask l'argument
     * @return le ET
     */
    public Mask and(final Mask mask) {
        final Mask and = new Mask(bmp);
        if (and.bmp.table.length != mask.bmp.table.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < and.bmp.table.length; i++) {
            if (and.bmp.table[i].length != mask.bmp.table[i].length) {
                throw new IndexOutOfBoundsException();
            }
            for (int j = 0; j < and.bmp.table[i].length; j++) {
                and.bmp.table[i][j] &= mask.bmp.table[i][j];
            }
        }
        and.trim();
        return and;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Mask other = (Mask) obj;
        return Objects.equals(bmp, other.bmp);
    }

    /**
     * Retourne la hauteur du masque.
     *
     * @return la hauteur du masque.
     */
    public int getHeight() {
        return bmp.height;
    }

    /**
     * Retourne la valeur du masque pour la coordonn�e donn�e.
     *
     * @param u la ligne.
     * @param v la colonne.
     */
    public boolean getValue(final int u, final int v) {
        return bmp.getValue(u, v);
    }

    /**
     * Retourne la largeur du masque.
     *
     * @return la largeur du masque.
     */
    public int getWidth() {
        return bmp.width;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bmp);
    }

    /**
     * Retourne la n�gation du masque.
     *
     * @return la n�gation
     */
    public Mask not() {
        final Mask not = new Mask(bmp);
        for (int i = 0; i < not.bmp.table.length; i++) {
            for (int j = 0; j < not.bmp.table[i].length; j++) {
                not.bmp.table[i][j] ^= 0xFF;
            }
        }
        not.trim();
        return not;
    }

    /**
     * Retourne le OU du masque et de l'argument.
     *
     * @param mask l'argument
     * @return le OU
     */
    public Mask or(final Mask mask) {
        final Mask or = new Mask(bmp);
        if (or.bmp.table.length != mask.bmp.table.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < or.bmp.table.length; i++) {
            if (or.bmp.table[i].length != mask.bmp.table[i].length) {
                throw new IndexOutOfBoundsException();
            }
            for (int j = 0; j < or.bmp.table[i].length; j++) {
                or.bmp.table[i][j] |= mask.bmp.table[i][j];
            }
        }
        or.trim();
        return or;
    }

    /**
     * Imprime le masque dans l'image. Un bit dans le masque devient un block de
     * 8x8 pixels :
     *
     * 0 -> 00001111 00001111 00001111 00001111 11110000 11110000 11110000
     * 11110000
     *
     * 1 -> 11110000 11110000 11110000 11110000 00001111 00001111 00001111
     * 00001111
     *
     * @param output l'image de sortie.
     */
    public void print(final BufferedImage output) {
        for (int i = 0; i < bmp.width; i++) {
            for (int j = 0; j < bmp.height; j++) {
                final boolean b = getValue(i, j);
                for (int k = 0; k < 8; k++) {
                    for (int l = 0; l < 8; l++) {
                        if (k < 4 && l < 4 || k > 3 && l > 3) {
                            output.setRGB(output.getMinX() + (i << 3) + k,
                                    output.getMinY() + (j << 3) + l,
                                    b ? 0 : ~0);
                        } else {
                            output.setRGB(output.getMinX() + (i << 3) + k,
                                    output.getMinY() + (j << 3) + l,
                                    b ? ~0 : 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * Imprime le masque dans le flot. Un bit dans le masque devient un block de
     * 8x8 pixels :
     *
     * 0 -> 00001111 00001111 00001111 00001111 11110000 11110000 11110000
     * 11110000
     *
     * 1 -> 11110000 11110000 11110000 11110000 00001111 00001111 00001111
     * 00001111
     *
     * @param output le flot de sortie.
     * @throws IOException si une erreure se produit dans le flot.
     */
    public void print(final OutputStream output) throws IOException {
        final String header = "P4\n" + (bmp.width << 3) + "\n"
                + (bmp.height << 3) + "\n";
        output.write(header.getBytes(StandardCharsets.UTF_8));
        for (int i = 0; i < bmp.height; i++) {
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < bmp.width; k++) {
                    final boolean b = getValue(k, i);
                    if (b ? j < 4 : j > 3) {
                        output.write((byte) 0xF0);
                    } else {
                        output.write((byte) 0x0F);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return bmp.toString();
    }

    /**
     * Met � z�ro les bits inutilis�s du masque.
     */
    private void trim() {
        final int w = (int) StrictMath.ceil((double) bmp.width / 8);
        final int m = 0xFF << (w << 3) - bmp.width;
        if (bmp.table.length != bmp.height) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < bmp.height; i++) {
            if (bmp.table[i].length != w) {
                throw new IndexOutOfBoundsException();
            }
            bmp.table[i][w - 1] &= m;
        }
    }

    /**
     * �crit le masque dans le flot.
     *
     * @param output le flot de sortie.
     * @throws IOException si une erreure se produit dans le flot.
     */
    public void write(final OutputStream output) throws IOException {
        bmp.write(output);
    }

    /**
     * Retourne le OU EXCLUSIF du masque et de l'argument.
     *
     * @param mask l'argument
     * @return le OU EXCLUSIF
     */
    public Mask xor(final Mask mask) {
        final Mask xor = new Mask(bmp);
        if (xor.bmp.table.length != mask.bmp.table.length) {
            throw new IndexOutOfBoundsException();
        }
        for (int i = 0; i < xor.bmp.table.length; i++) {
            if (xor.bmp.table[i].length != mask.bmp.table[i].length) {
                throw new IndexOutOfBoundsException();
            }
            for (int j = 0; j < xor.bmp.table[i].length; j++) {
                xor.bmp.table[i][j] ^= mask.bmp.table[i][j];
            }
        }
        xor.trim();
        return xor;
    }

}
