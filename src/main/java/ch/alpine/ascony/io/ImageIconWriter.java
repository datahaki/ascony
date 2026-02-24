package ch.alpine.ascony.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

public class ImageIconWriter extends AnimatedGifWriter implements AutoCloseable {
  public static ImageIconWriter of(int period, boolean loop) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    return new ImageIconWriter(ImageIO.createImageOutputStream(baos), period, loop, baos);
  }

  final ByteArrayOutputStream baos;

  ImageIconWriter(ImageOutputStream imageOutputStream, int period, boolean loop, ByteArrayOutputStream baos) {
    super(imageOutputStream, 0, false);
    this.baos = baos;
  }

  @Override
  public void close() throws IOException {
    super.close();
  }

  public ImageIcon getIconImage() {
    return new ImageIcon(baos.toByteArray());
  }
}
