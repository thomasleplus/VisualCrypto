package org.leplus.libcrypto;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.leplus.libimage.PortableBitmap;

/** Masque cryptographique. */
public class Mask {

  /** Le bitmap de ce masque. */
  private final PortableBitmap bmp;

  /**
   * Construit le masque à partir des données dans le fot.
   *
   * @param input le flot d'entrée.
   * @throws IOException si une erreure se produit dans le flot.
   */
  @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
  @SuppressWarnings("this-escape")
  public Mask(final InputStream input) throws IOException {
    bmp = new PortableBitmap();
    bmp.read(input);
    trim();
  }

  /**
   * Construit le masque à partir du masque.
   *
   * @param mask le masque
   */
  public Mask(final Mask mask) {
    bmp = mask.bmp;
  }

  /**
   * Construit le masque à partir du bitmap.
   *
   * @param pbm le bitmap
   */
  @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
  @SuppressWarnings("this-escape")
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
    if (and.bmp.getTable().length != mask.bmp.getTable().length) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < and.bmp.getTable().length; i++) {
      if (and.bmp.getTable()[i].length != mask.bmp.getTable()[i].length) {
        throw new IndexOutOfBoundsException();
      }
      for (int j = 0; j < and.bmp.getTable()[i].length; j++) {
        and.bmp.getTable()[i][j] &= mask.bmp.getTable()[i][j];
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
    return bmp.getHeight();
  }

  /**
   * Retourne la valeur du masque pour la coordonnée donnée.
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
    return bmp.getWidth();
  }

  @Override
  public int hashCode() {
    return Objects.hash(bmp);
  }

  /**
   * Retourne la négation du masque.
   *
   * @return la négation
   */
  public Mask not() {
    final Mask not = new Mask(bmp);
    for (int i = 0; i < not.bmp.getTable().length; i++) {
      for (int j = 0; j < not.bmp.getTable()[i].length; j++) {
        not.bmp.getTable()[i][j] ^= (byte) 0xFF;
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
    if (or.bmp.getTable().length != mask.bmp.getTable().length) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < or.bmp.getTable().length; i++) {
      if (or.bmp.getTable()[i].length != mask.bmp.getTable()[i].length) {
        throw new IndexOutOfBoundsException();
      }
      for (int j = 0; j < or.bmp.getTable()[i].length; j++) {
        or.bmp.getTable()[i][j] |= mask.bmp.getTable()[i][j];
      }
    }
    or.trim();
    return or;
  }

  /**
   * Imprime le masque dans l'image. Un bit dans le masque devient un block de 8x8 pixels :
   *
   * <p>0 -> 00001111 00001111 00001111 00001111 11110000 11110000 11110000 11110000
   *
   * <p>1 -> 11110000 11110000 11110000 11110000 00001111 00001111 00001111 00001111
   *
   * @param output l'image de sortie.
   */
  public void print(final BufferedImage output) {
    for (int i = 0; i < getWidth(); i++) {
      for (int j = 0; j < getHeight(); j++) {
        final boolean b = getValue(i, j);
        for (int k = 0; k < 8; k++) {
          for (int l = 0; l < 8; l++) {
            if (k < 4 && l < 4 || k > 3 && l > 3) {
              output.setRGB(
                  output.getMinX() + (i << 3) + k, output.getMinY() + (j << 3) + l, b ? 0 : ~0);
            } else {
              output.setRGB(
                  output.getMinX() + (i << 3) + k, output.getMinY() + (j << 3) + l, b ? ~0 : 0);
            }
          }
        }
      }
    }
  }

  /**
   * Imprime le masque dans le flot. Un bit dans le masque devient un block de 8x8 pixels :
   *
   * <p>0 -> 00001111 00001111 00001111 00001111 11110000 11110000 11110000 11110000
   *
   * <p>1 -> 11110000 11110000 11110000 11110000 00001111 00001111 00001111 00001111
   *
   * @param output le flot de sortie.
   * @throws IOException si une erreure se produit dans le flot.
   */
  public void print(final OutputStream output) throws IOException {
    final String header = "P4\n" + (getWidth() << 3) + "\n" + (getHeight() << 3) + "\n";
    output.write(header.getBytes(StandardCharsets.UTF_8));
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < 8; j++) {
        for (int k = 0; k < getWidth(); k++) {
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

  /** Met à zéro les bits inutilisés du masque. */
  private void trim() {
    final int w = (int) StrictMath.ceil((double) getWidth() / 8);
    final int m = 0xFF << (w << 3) - getWidth();
    if (bmp.getTable().length != getHeight()) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < getHeight(); i++) {
      if (bmp.getTable()[i].length != w) {
        throw new IndexOutOfBoundsException();
      }
      bmp.getTable()[i][w - 1] &= (byte) m;
    }
  }

  /**
   * écrit le masque dans le flot.
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
    if (xor.bmp.getTable().length != mask.bmp.getTable().length) {
      throw new IndexOutOfBoundsException();
    }
    for (int i = 0; i < xor.bmp.getTable().length; i++) {
      if (xor.bmp.getTable()[i].length != mask.bmp.getTable()[i].length) {
        throw new IndexOutOfBoundsException();
      }
      for (int j = 0; j < xor.bmp.getTable()[i].length; j++) {
        xor.bmp.getTable()[i][j] ^= mask.bmp.getTable()[i][j];
      }
    }
    xor.trim();
    return xor;
  }
}
