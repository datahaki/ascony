// code by jph
package ch.alpine.ascony.win;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.tensor.Throw;

public class TimerFrame extends BaseFrame {
  record TTWrap(TimerTask task, long delay, long period) {
    public void schedule(Timer timer) {
      timer.schedule(task, delay, period);
    }
  }

  private Timer timer = null;
  private final List<TTWrap> list = new LinkedList<>();

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
        list.forEach(ttWrap -> ttWrap.schedule(timer));
        list.clear();
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
    // DO NOT SIMPLIFY THIS LINE !!!
    // the object "timer" is a mutable field !
    WindowClosed.runs(jFrame, () -> timer.cancel());
    AwtUtil.ctrlW(jFrame);
  }

  public void timer_schedule(TimerTask task, long delay, long period) {
    TTWrap ttWrap = new TTWrap(task, delay, period);
    if (Objects.nonNull(timer))
      ttWrap.schedule(timer);
    else
      list.add(ttWrap);
  }
}
