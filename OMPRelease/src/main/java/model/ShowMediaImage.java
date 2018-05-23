package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ShowMediaImage  extends JPanel {

        private BufferedImage image;

        public ShowMediaImage(String path) {
            try {
                image = ImageIO.read(new File(path));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        }

}

