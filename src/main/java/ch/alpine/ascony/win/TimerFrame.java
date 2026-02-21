// code by jph
package ch.alpine.ascony.win;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.tensor.Throw;

public class TimerFrame extends BaseFrame {
  protected Timer timer = null;

  /** frame with repaint rate of 20[Hz] */
  public TimerFrame() {
    this(50, TimeUnit.MILLISECONDS);
  }

  /** @param period between repaint invocations */
  public TimerFrame(int period, TimeUnit timeUnit) {
    jFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowOpened(WindowEvent e) {
        Throw.unless(Objects.isNull(timer));
        timer = new Timer();
        // periodic task for rendering
        TimerTask timerTask = new TimerTask() {
          @Override
          public void run() {
            geometricComponent.jComponent.repaint();
          }
        };
        timer.schedule(timerTask, 100, TimeUnit.MILLISECONDS.convert(period, timeUnit));
      }
    });
    WindowClosed.runs(jFrame, () -> {
      timer.cancel();
    });
    AwtUtil.ctrlW(jFrame);
  }
}
