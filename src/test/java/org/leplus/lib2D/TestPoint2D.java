package org.leplus.lib2D;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/** Test the Point2D class. */
class TestPoint2D {

  private static final double DELTA = 1e-9;

  @Test
  void addIsComponentWise() {
    final Point2D r = new Point2D(1, 2).add(new Point2D(3, 4));
    Assertions.assertEquals(4.0, r.getX(), DELTA);
    Assertions.assertEquals(6.0, r.getY(), DELTA);
  }

  @Test
  void subtractReturnsVectorBetweenPoints() {
    final Vector2D v = new Point2D(3, 4).subtract(new Point2D(1, 1));
    Assertions.assertEquals(2.0, v.getX(), DELTA);
    Assertions.assertEquals(3.0, v.getY(), DELTA);
  }

  @Test
  void negNegatesBothComponents() {
    final Point2D r = new Point2D(1, -2).neg();
    Assertions.assertEquals(-1.0, r.getX(), DELTA);
    Assertions.assertEquals(2.0, r.getY(), DELTA);
  }

  @Test
  void polarRadiusIsHypotenuse() {
    Assertions.assertEquals(5.0, new Point2D(3, 4).getR(), DELTA);
  }

  @Test
  void polarAngle() {
    Assertions.assertEquals(0.0, new Point2D(1, 0).getT(), DELTA);
    Assertions.assertEquals(Math.PI / 2, new Point2D(0, 1).getT(), DELTA);
  }

  @Test
  void equalsAndHashCodeAreConsistent() {
    final Point2D a = new Point2D(1, 2);
    final Point2D b = new Point2D(1, 2);
    Assertions.assertEquals(a, b);
    Assertions.assertEquals(a.hashCode(), b.hashCode());
    Assertions.assertNotEquals(a, new Point2D(1, 3));
    Assertions.assertNotEquals(a, null);
  }

  @Test
  void zeroConstantIsOrigin() {
    Assertions.assertEquals(0.0, Point2D.ZERO.getX(), DELTA);
    Assertions.assertEquals(0.0, Point2D.ZERO.getY(), DELTA);
  }
}
