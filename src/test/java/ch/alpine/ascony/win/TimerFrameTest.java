// code by jph
package ch.alpine.ascony.win;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class TimerFrameTest {
  @Test
  void testSimple() {
    long convert = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS);
    assertEquals(convert, 1000);
  }

  @Test
  void testTimer() {
    TimerFrame timerFrame = new TimerFrame();
    assertNull(timerFrame.timer);
  }
}
