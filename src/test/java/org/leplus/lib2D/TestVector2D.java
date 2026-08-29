package org.leplus.lib2D;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** Test the Vector2D class. */
class TestVector2D {

  private static final double DELTA = 1e-9;

  @Test
  void dotProduct() {
    Assertions.assertEquals(11.0, new Vector2D(1, 2).dot(new Vector2D(3, 4)), DELTA);
  }

  @Test
  void addIsComponentWise() {
    final Vector2D r = new Vector2D(1, 2).add(new Vector2D(3, 4));
    Assertions.assertEquals(4.0, r.getX(), DELTA);
    Assertions.assertEquals(6.0, r.getY(), DELTA);
  }

  @Test
  void perpIsOrthogonal() {
    // perp() returns (-y, x), which is perpendicular to the original vector.
    final Vector2D v = new Vector2D(3, 4);
    final Vector2D p = v.perp();
    Assertions.assertEquals(-4.0, p.getX(), DELTA);
    Assertions.assertEquals(3.0, p.getY(), DELTA);
    Assertions.assertEquals(0.0, v.dot(p), DELTA);
  }

  @Test
  void negNegatesBothComponents() {
    final Vector2D r = new Vector2D(1, -2).neg();
    Assertions.assertEquals(-1.0, r.getX(), DELTA);
    Assertions.assertEquals(2.0, r.getY(), DELTA);
  }

  @Test
  void equalsAndHashCodeAreConsistent() {
    final Vector2D a = new Vector2D(1, 2);
    final Vector2D b = new Vector2D(1, 2);
    Assertions.assertEquals(a, b);
    Assertions.assertEquals(a.hashCode(), b.hashCode());
    Assertions.assertNotEquals(a, new Vector2D(2, 1));
  }
}
