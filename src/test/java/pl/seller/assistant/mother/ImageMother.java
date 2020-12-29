package pl.seller.assistant.mother;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.imageio.ImageIO;

public class ImageMother {

  public static BufferedImage exampleBufferedImage() {
    try {
      return ImageIO.read(new File("src/test/resources/pictures/example_image.png"));
    } catch (IOException exception) {
      throw new NoSuchElementException("Test image is missing.");
    }
  }
}
