/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MapEncodingDecoding.TileDataManagment;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author jason
 */
public class TestTileDataManagment {

    private static JScrollPane imageSheetScroller;
    
    public static void main(String args[]) {
        
        
//        //ZOMG WORKS
        JFrame mainFrame = new JFrame();
        JPanel fuck = new JPanel();
        //fuck.setAutoscrolls(autoscrolls) <- IMPORTANT
        fuck.setAutoscrolls(true);
        mainFrame.setLayout(new GridLayout(2, 2));
        
        //        //On exit event close the window
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing() {
                System.exit(0);
            }
        });
        
        //http://java.sun.com/docs/books/tutorial/uiswing/components/scrollpane.html#scrollable
        //http://forum.java.sun.com/thread.jspa?threadID=5148607&messageID=9577054
        
         TileCanvas tc = new TileCanvas();
         tc.setPreferredSize(new Dimension(768,768));
                 JScrollPane scroller = new JScrollPane(tc,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                 
                 tc.setFocusable(true);
                 
                 tc.setAutoscrolls(true);
                 
                                  MouseMotionListener doScrollRectToVisible = new MouseMotionAdapter() {
     public void mouseDragged(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        ((JPanel)e.getSource()).scrollRectToVisible(r);
    }
 };
 tc.addMouseMotionListener(doScrollRectToVisible);
                 
                 // ScrollPane scroller = new ScrollPane();
                 // scroller.add(tc);
                                   
        scroller.setPreferredSize(new Dimension(200, 200));

        //fuck.add(new JLabel(new ImageIcon("src/Resources/PokemonTileSet.png")));
        mainFrame.add(scroller);
         mainFrame.add(new JButton("22"));
         mainFrame.add(new JButton("22"));
        mainFrame.add(new JButton("22"));

        mainFrame.setSize(200, 200);
        mainFrame.setVisible(true);
        
        mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
        
        
//        runGetTileIDTest();
//        runSetTileIDTest();
//        runTransparencyTest();
//        runGetTileLevelTest();
//        runSetTileLevelTest();
//        runSetTileSolidTest();
//        runGetTileRotationTest();
//        runSetTileRotationTest();
//        runGetTileFlipAttribute();
//        runSetTileFlipAttribute();
//        runFullTileTest1();
//        runFullTileTest2();
    }
    
    private static void runGetTileIDTest(){
                //Run getTileID test
        System.out.println("Test getTileID pass: " +
                (TileManipulation.getTileID(1077) == 53));
    }
    
    private static void runSetTileIDTest() {

        //Run setTileID test
        int tile = ~0;
        tile = TileManipulation.setTileID(tile, 27);

        System.out.println("Test setTileID pass: " +
                (TileManipulation.getTileID(tile) == 27));

        System.out.println("Test setTileID-2 pass: " +
                (TileManipulation.getTileID(43690) == 170));
    }
    
    private static void runTransparencyTest() {
        int tile2 = ~0;

        System.out.println("Test switchTileTransparency (on) pass: " +
                (TileManipulation.getTileTransparency(tile2) == true));

        tile2 = TileManipulation.switchTileTransparency(tile2);

        System.out.println("Test switchTileTransparency (off) pass: " +
                (TileManipulation.getTileTransparency(tile2) == false));
    }
    
    private static void runGetTileLevelTest(){
        System.out.println("Test getTileLevel pass: " +
                (TileManipulation.getTileLevel(512) == 3));
        
        System.out.println("Test getTileLevel-2 pass: " +
                (TileManipulation.getTileLevel(0) == 1));

        System.out.println("Test getTileLevel-3 pass: " +
                (TileManipulation.getTileLevel(256) == 2));
        
        System.out.println("Test getTileLevel-4 pass: " +
                (TileManipulation.getTileLevel(3328) == 2));
        
    }
    
    private static void runSetTileLevelTest() {

        int tile = 0;
        tile = TileManipulation.setTileLevel(tile, 1);
        System.out.println("Test setTileLevel pass: " +
                (TileManipulation.getTileLevel(tile) == 1));

        tile = TileManipulation.setTileLevel(tile, 2);
        System.out.println("Test setTileLevel2 pass: " +
                (TileManipulation.getTileLevel(tile) == 2));

        tile = TileManipulation.setTileLevel(tile, 3);
        System.out.println("Test setTileLevel3 pass: " +
                (TileManipulation.getTileLevel(tile) == 3));


    }
    
    private static void runSetTileSolidTest() {
        int tile2 = ~0;

        System.out.println("Test switchTileSolid (on) pass: " +
                (TileManipulation.isTileSolid(tile2) == true));

        tile2 = TileManipulation.switchTileSolid(tile2);

        System.out.println("Test switchTileSolid (off) pass: " +
                (TileManipulation.isTileSolid(tile2) == false));
    }

    private static void runGetTileRotationTest() {

        System.out.println("Test getTileRotation West) pass: " +
                (TileManipulation.getTileRotation(12303) ==
                TileManipulation.tileRotateDirection.WEST));

        System.out.println("Test getTileRotation North) pass: " +
                (TileManipulation.getTileRotation(16399) ==
                TileManipulation.tileRotateDirection.NORTH));
    }
    
    private static void runSetTileRotationTest() {
        int tile = 12303; //Rotation of tile is west
        
        tile = TileManipulation.setTileRotation(tile, TileManipulation.tileRotateDirection.NORTH);
        
        System.out.println("Test setTileRotation North) pass: " +
                (TileManipulation.getTileRotation(tile) ==
                TileManipulation.tileRotateDirection.NORTH));
        
        tile = 0; //Rotation of tile is west
        
        tile = TileManipulation.setTileRotation(tile, TileManipulation.tileRotateDirection.EAST);
        
        System.out.println("Test setTileRotation East) pass: " +
                (TileManipulation.getTileRotation(tile) ==
                TileManipulation.tileRotateDirection.EAST));
    }
    
    private static void runGetTileFlipAttribute(){
        
        System.out.println("Test getTileFlipAttribute (Vert + horiz) pass: " +
                (TileManipulation.getTileFlipAttribute(~0) ==
                TileManipulation.tileFlipAttribute.VERT_HORIZ));
        
        System.out.println("Test getTileFlipAttribute (no flip) pass: " +
                (TileManipulation.getTileFlipAttribute(0) ==
                TileManipulation.tileFlipAttribute.NOFLIP));
    }
    
    private static void runSetTileFlipAttribute(){
        int tile = 16384; //Flip is set to vertical

        tile = TileManipulation.setTileFlipAttribute(tile, TileManipulation.tileFlipAttribute.HORIZONTAL);

        System.out.println("Test setTileFlipAttribute (horiz) pass: " +
                (TileManipulation.getTileFlipAttribute(tile) ==
                TileManipulation.tileFlipAttribute.HORIZONTAL));
        
        tile = TileManipulation.setTileFlipAttribute(tile, TileManipulation.tileFlipAttribute.VERTICAL);

        System.out.println("Test setTileFlipAttribute (vert) pass: " +
                (TileManipulation.getTileFlipAttribute(tile) ==
                TileManipulation.tileFlipAttribute.VERTICAL));
    }
    
    private static void runFullTileTest1(){
        int tile = 43690;
        
        int tileID = 170;
        int level = 3;
        boolean transparent = false;
        boolean solidTile = true;
        TileManipulation.tileRotateDirection rd = TileManipulation.tileRotateDirection.EAST;
        
        TileManipulation.tileFlipAttribute fa = TileManipulation.tileFlipAttribute.HORIZONTAL;
        
        System.out.println("Test Full tile (1) pass: " + (tileID == TileManipulation.getTileID(tile) &&
        level == TileManipulation.getTileLevel(tile) && transparent == TileManipulation.getTileTransparency(tile) 
        && solidTile == TileManipulation.isTileSolid(tile) && TileManipulation.getTileRotation(tile) == rd &&
        TileManipulation.getTileFlipAttribute(tile) == fa));
        
    }
    
    private static void runFullTileTest2(){
        int tile = TileManipulation.generateTile(123, 2, true, true,
                TileManipulation.tileRotateDirection.NORTH, 
                TileManipulation.tileFlipAttribute.VERT_HORIZ);
        
        System.out.println("Test Full tile (2) pass: " + (123 == TileManipulation.getTileID(tile) &&
        2 == TileManipulation.getTileLevel(tile) && true == TileManipulation.getTileTransparency(tile) 
        && true == TileManipulation.isTileSolid(tile) && TileManipulation.getTileRotation(tile) == TileManipulation.tileRotateDirection.NORTH &&
        TileManipulation.getTileFlipAttribute(tile) == TileManipulation.tileFlipAttribute.VERT_HORIZ));
        
    }
}
