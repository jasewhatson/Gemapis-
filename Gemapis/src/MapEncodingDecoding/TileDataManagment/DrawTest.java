package MapEncodingDecoding.TileDataManagment;

import java.awt.Canvas;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author jason
 */
public class DrawTest extends Canvas{
    
    private BufferedImage tilesetImage = null;
    
    public DrawTest(){
        try {
                tilesetImage = ImageIO.read(new File("tileset.png"));
        } catch (IOException ex) {
            System.out.println("Cannot find file");
        }
    }

    public void update(Graphics g) {
        this.paint(g);
    }

    public void paint(Graphics g) {
        g.drawImage(tilesetImage, 0, 0, null);
    }
}
