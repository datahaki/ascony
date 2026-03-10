// code by jph
package ch.alpine.ascony.win;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.OffscreenRender;
import ch.alpine.bridge.awt.WindowClosed;
import ch.alpine.bridge.gfx.GeometricComponent;
import ch.alpine.tensor.Throw;

public class TimerFrame extends JFrame {
  record TTWrap(TimerTask task, long delay, long period) {
    public void schedule(Timer timer) {
      timer.schedule(task, delay, period);
    }
  }

  private final JPanel jPanel = new JPanel(new BorderLayout());
  public final JToolBar jToolBar = new JToolBar();
  public final GeometricComponent geometricComponent = new GeometricComponent();
  private Timer timer = null;
  private final List<TTWrap> list = new LinkedList<>();

  /** frame with repaint rate of 20[Hz] */
  public TimerFrame() {
    this(50, TimeUnit.MILLISECONDS);
  }

  /** @param period between repaint invocations */
  public TimerFrame(int period, TimeUnit timeUnit) {
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
    jToolBar.setFloatable(false);
    jPanel.add(new JScrollPane(jToolBar, //
        ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, //
        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.NORTH);
    jPanel.add(geometricComponent, BorderLayout.CENTER);
    setContentPane(jPanel);
    addWindowListener(new WindowAdapter() {
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
            geometricComponent.repaint();
          }
        };
        timer.schedule(timerTask, 100, TimeUnit.MILLISECONDS.convert(period, timeUnit));
      }
    });
    // DO NOT SIMPLIFY THIS LINE !!!
    // the object "timer" is a mutable field !
    WindowClosed.runs(this, () -> timer.cancel());
    AwtUtil.ctrlW(this);
  }

  public final BufferedImage offscreen() {
    return OffscreenRender.of(geometricComponent, BufferedImage.TYPE_INT_ARGB);
  }

  private boolean west_available = true;

  protected final void addWest(JComponent jComponent) {
    Throw.unless(west_available);
    west_available = false;
    jPanel.add(jComponent, BorderLayout.WEST);
  }

  public final void close() {
    setVisible(false);
    dispose();
  }

  /** allows to schedule tasks before the window is opened, and before the timer is started
   * 
   * @param task
   * @param delay
   * @param period */
  public void timer_schedule(TimerTask task, long delay, long period) {
    TTWrap ttWrap = new TTWrap(task, delay, period);
    if (Objects.nonNull(timer))
      ttWrap.schedule(timer);
    else
      list.add(ttWrap);
  }
}
