// code by jph
package ch.alpine.ascony.ren;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensors;

class ArgMinValueTest {
  @Test
  void testSimple() {
    Optional<ArgMinValue> optional = ArgMinValue.of(Tensors.vector(3, 2, 3, 4, 5, 1, 2, 3, 4));
    ArgMinValue argMinValue = optional.orElseThrow();
    assertEquals(argMinValue.index(), 5);
    assertFalse(argMinValue.filter(Rational.HALF).isPresent());
    assertEquals(argMinValue.filter(RealScalar.of(123)).get().index(), (Integer) 5);
    assertEquals(argMinValue.value(), RealScalar.ONE);
    assertFalse(argMinValue.filter(RealScalar.of(0.1)).isPresent());
    assertEquals(argMinValue.filter(RealScalar.of(123)).get().value(), RealScalar.ONE);
  }
}
