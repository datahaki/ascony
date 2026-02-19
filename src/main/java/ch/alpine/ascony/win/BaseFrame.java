// code by jph
package ch.alpine.ascony.win;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.OffscreenRender;

public class BaseFrame {
  private static final int SIZE = 800;
  // ---
  public final JFrame jFrame = new JFrame();
  private final JPanel jPanel = new JPanel(new BorderLayout());
  public final JToolBar jToolBar = new JToolBar();
  public final GeometricComponent geometricComponent = new GeometricComponent();

  protected BaseFrame() {
    jFrame.setSize(SIZE, SIZE);
    jFrame.setLocationRelativeTo(null);
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    jToolBar.setFloatable(false);
    jPanel.add(jToolBar, BorderLayout.NORTH);
    jPanel.add(geometricComponent.jComponent, BorderLayout.CENTER);
    jFrame.setContentPane(jPanel);
  }

  public final BufferedImage offscreen() {
    return OffscreenRender.of(geometricComponent.jComponent, BufferedImage.TYPE_INT_ARGB);
  }

  public final void close() {
    jFrame.setVisible(false);
    jFrame.dispose();
  }
}
