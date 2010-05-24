/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MapEncodingDecoding.TileDataManagment;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JPanel;
/**
 *
 * @author jason
 */
public class TileCanvas extends JPanel implements
        java.awt.event.MouseMotionListener, MouseListener, java.awt.event.KeyListener{
    
    String fileName = "TileData.bin";
    
    DataOutputStream out;

    private static int mapWidth = 24;
    private static int mapHeight = 24;
    private static int mapLayers = 3;
    
    private static boolean[] layerVisableStatus = new boolean[mapLayers];
    
    
    private int selectedTileX=0,selectedTileY=0;
    
    private int mouseOverTileX=0,mouseOverTileY = 0;
    
    private int selectedAreaStartTileX=0,selectedAreaStartTileY = 0;
    private int selectedAreaEndTileX=0,selectedAreaEndTileY = 0;
    
    private int[][][] mapData = new int[mapWidth][mapHeight][mapLayers];
    
    private BufferedImage[][][] tileData = new BufferedImage[mapWidth][mapHeight][mapLayers];
    
    private static int tileSize = 32;
    
    private BufferedImage tilesetImage = null;
    
    private int selectedLayer = 0;
    
    
    int deleteme = 0;
    
        // The image that will contain everything that has been drawn on
    // bufferGraphics.
    private Image offscreen;
    
    // The object we will use to write with instead of the standard screen graphics
    private Graphics2D ggg2d;
    
    BufferedImage buffer; // The image we use for double-buffering
    
            int swidth = 32;
        int sheight = 32;
        
        AffineTransform at = new AffineTransform();
    
    /*/home/jason/Programming/Java/TileDataManagment/tileset.png*/
    
    public TileCanvas(){
        
        addMouseListener(this);
        addMouseMotionListener(this);
        
        try {
            //tilesetImage = ImageIO.read(new File("/home/jason/Programming/Java/TileDataManagment/tileset.png"));
                tilesetImage = ImageIO.read(new File("src/Resources/PokemonTileSet.png"));
        } catch (IOException ex) {
            System.out.println("Cannot find file");
        }
        
        init();
    }
    
    public void init() {

        this.addKeyListener(this);

        buffer = new BufferedImage(768, 768, BufferedImage.TYPE_INT_RGB);

        ggg2d = buffer.createGraphics();

        for (int i = 0; i < layerVisableStatus.length; i++) layerVisableStatus[i] = true;
        
        //Fill with grass
        //for (int z = 0; z < mapData[0][0].length; z++) {
            for (int i = 0; i < mapData[0].length; i++) {
                for (int j = 0; j < mapData.length; j++) {
                    tileData[i][j][0] = tilesetImage.getSubimage(224, 704, 32, 32);
                    //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
                    mapData[i][j][0] = TileManipulation.setTileID(mapData[i][j][0], 1);
                }
            }
        //}
        
        //Trees
        tileData[6][6][1] = tilesetImage.getSubimage(96, 960, 32, 32);
        //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
        mapData[6][6][1] = TileManipulation.setTileID(mapData[6][6][1], 1);
        
        tileData[7][6][1] = tilesetImage.getSubimage(96, 992, 32, 32);
        //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
        mapData[7][6][1] = TileManipulation.setTileID(mapData[7][6][1], 1);
        
                tileData[6][5][1] = tilesetImage.getSubimage(96, 960, 32, 32);
        //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
        mapData[6][5][1] = TileManipulation.setTileID(mapData[6][6][1], 1);
        
        tileData[7][5][1] = tilesetImage.getSubimage(96, 992, 32, 32);
        //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
        mapData[7][5][1] = TileManipulation.setTileID(mapData[7][5][1], 1);
        
        tileData[6][4][1] = tilesetImage.getSubimage(96, 960, 32, 32);
        //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
        mapData[6][4][1] = TileManipulation.setTileID(mapData[6][6][1], 1);

        tileData[7][4][1] = tilesetImage.getSubimage(96, 992, 32, 32);
        //tileData[i][j][z] = tilesetImage.getSubimage(96, 64, 32, 32);
        mapData[7][4][1] = TileManipulation.setTileID(mapData[7][4][1], 1);
        

        
        
        //Houses
        tileData[8][8][2] = tilesetImage.getSubimage(64, 130, 32, 32);
        mapData[8][8][2] = TileManipulation.setTileID(mapData[8][8][2], 1);
        
        tileData[8][9][2] = tilesetImage.getSubimage(96, 130, 32, 32);
        mapData[8][9][2] = TileManipulation.setTileID(mapData[8][9][2], 1);
        
        tileData[8][10][2] = tilesetImage.getSubimage(128, 130, 32, 32);
        mapData[8][10][2] = TileManipulation.setTileID(mapData[8][10][2], 1);
        
        tileData[8][11][2] = tilesetImage.getSubimage(160, 130, 32, 32);
        mapData[8][11][2] = TileManipulation.setTileID(mapData[8][11][2], 1);
        
        //House row 2
        tileData[9][8][2] = tilesetImage.getSubimage(64, 162, 32, 32);
        mapData[9][8][2] = TileManipulation.setTileID(mapData[9][8][2], 1);
        
        tileData[9][9][2] = tilesetImage.getSubimage(96, 162, 32, 32);
        mapData[9][9][2] = TileManipulation.setTileID(mapData[9][9][2], 1);
        
        tileData[9][10][2] = tilesetImage.getSubimage(128, 162, 32, 32);
        mapData[9][10][2] = TileManipulation.setTileID(mapData[9][10][2], 1);
        
        tileData[9][11][2] = tilesetImage.getSubimage(160, 162, 32, 32);
        mapData[9][11][2] = TileManipulation.setTileID(mapData[9][11][2], 1);
        
        //House row 3
        tileData[10][8][2] = tilesetImage.getSubimage(64, 194, 32, 32);
        mapData[10][8][2] = TileManipulation.setTileID(mapData[10][8][2], 1);
        
        tileData[10][9][2] = tilesetImage.getSubimage(96, 194, 32, 32);
        mapData[10][9][2] = TileManipulation.setTileID(mapData[10][9][2], 1);
        
        tileData[10][10][2] = tilesetImage.getSubimage(128, 194, 32, 32);
        mapData[10][10][2] = TileManipulation.setTileID(mapData[10][10][2], 1);
        
        tileData[10][11][2] = tilesetImage.getSubimage(160, 194, 32, 32);
        mapData[10][11][2] = TileManipulation.setTileID(mapData[10][11][2], 1);
        
        //House row 4
        tileData[11][8][2] = tilesetImage.getSubimage(64, 224, 32, 32);
        mapData[11][8][2] = TileManipulation.setTileID(mapData[11][8][2], 1);
        
        tileData[11][9][2] = tilesetImage.getSubimage(96, 224, 32, 32);
        mapData[11][9][2] = TileManipulation.setTileID(mapData[11][9][2], 1);
        
        tileData[11][10][2] = tilesetImage.getSubimage(128, 224, 32, 32);
        mapData[11][10][2] = TileManipulation.setTileID(mapData[11][10][2], 1);
        
        tileData[11][11][2] = tilesetImage.getSubimage(160, 224, 32, 32);
        mapData[11][11][2] = TileManipulation.setTileID(mapData[11][11][2], 1);
        
        //Sign 
        tileData[10][13][2] = tilesetImage.getSubimage(64, 976, 32, 32);
        mapData[10][13][2] = TileManipulation.setTileID(mapData[10][13][2], 1);
        
        tileData[11][13][2] = tilesetImage.getSubimage(64, 1008, 32, 32);
        mapData[11][13][2] = TileManipulation.setTileID(mapData[11][13][2], 1);
        
        //Pokeballs
        
        tileData[11][7][2] = tilesetImage.getSubimage(192,992, 32, 32);
        mapData[11][7][2] = TileManipulation.setTileID(mapData[11][7][2], 1);
        
        tileData[11][6][2] = tilesetImage.getSubimage(192,992, 32, 32);
        mapData[11][6][2] = TileManipulation.setTileID(mapData[11][6][2], 1);
        
        tileData[10][7][2] = tilesetImage.getSubimage(192,992, 32, 32);
        mapData[10][7][2] = TileManipulation.setTileID(mapData[10][7][2], 1);
        
        tileData[10][6][2] = tilesetImage.getSubimage(192,992, 32, 32);
        mapData[10][6][2] = TileManipulation.setTileID(mapData[10][6][2], 1);
        
        
        



            /*for (int i = 0; i < mapWidth; i++) {
                for (int j = 0; j < mapHeight; j++) {
                    tileData[i][j][0] = tilesetImage.getSubimage(96, 64, 32, 32);
                    mapData[i][j][0] = TileManipulation.setTileID(mapData[i][j][0], 1);
                }
            }
            
            mapData[7][7][2] = TileManipulation.setTileID(0,1);
            tileData[7][7][2] = tilesetImage.getSubimage(192,992, 32, 32);

            tileData[1][1][0] = tilesetImage.getSubimage(96, 96, 32, 32);
            //mapData[1][1] = TileManipulation.setTileFlipAttribute(mapData[1][1], TileManipulation.tileFlipAttribute.HORIZONTAL);
             mapData[6][6][0] = TileManipulation.setTileRotation(mapData[6][6][0], TileManipulation.tileRotateDirection.SOUTH);

            at.rotate(90.0 * Math.PI / 180.0, tileData[0][0][0].getWidth() / 2.0, tileData[0][0][0].getHeight() / 2.0);
            */
            



    }
    
    public void update(Graphics g){
       this.paint(g);
    }
    
    public java.util.ArrayList<Point> getSelectedAreaTilesXY(){
        
        java.util.ArrayList<Point> selectTilesXY = new ArrayList();
        
        int xStart = this.selectedAreaStartTileX / 32; 
        int xEnd = (this.selectedAreaEndTileX -32) / 32;
        int yStart = this.selectedAreaStartTileY / 32;
        int yEnd = (this.selectedAreaEndTileY -32) / 32;
        
        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
               selectTilesXY.add(new Point(i,j));
            }
        }
        
        return selectTilesXY;
        /*int a = this.selectedAreaStartTileY;
        int b = this.selectedAreaStartTileX;
                
        int c = this.selectedAreaEndTileY -32;
        int d = this.selectedAreaEndTileX -32;
        
        int xs = b/32;
        int xe = d/32;
        
        int ys = a/32;
         int ye = c/32;
        
        System.out.println(xs);
        System.out.println(xe);
        System.out.println(ys);
       System.out.println(ye);
        
        for (int i = xs; i <= xe; i++) {
            for (int j = ys; j <= ye; j++) {
                tileData[j][i] = null;
            }
        }
   
        this.repaint();
        return null;*/
    }
    
    public void drawTileLayer(int layer, Graphics2D destination) {
        int xpos = 0;
        int ypos = 0;

        for (int i = 0; i < mapWidth; i++) {
                for (int j = 0; j < mapHeight; j++) {

                    if (mapData[i][j][layer] != 0) {
                        destination.drawImage(tileData[i][j][layer], new AffineTransformOp(
                                new TileAffineTransformHelper().getTilesAffineTransform(mapData[i][j][layer]), null), xpos, ypos);

                        if (TileManipulation.isTileSolid(mapData[i][j][layer])) {
                            destination.setColor(new Color(255, 0, 0, 100)); // A BIT LESS THAN 50% transparent 
                            destination.fillRect(xpos, ypos, 32, 32);
                        }
                    }
                    xpos += 32;
                }
                xpos = 0;
                ypos += 32;
            }
    }
    
    public void paint(Graphics g) { 
        
        int xpos = 0;
        int ypos = 0;   
              
        ggg2d.clearRect(0, 0,768, 768);
        
        for(int i = 0;i < layerVisableStatus.length;i++){
            if(layerVisableStatus[i])drawTileLayer(i,ggg2d);
        }
        //Draw tiles
        /*for (int z = 0; z < mapLayers; z++) {
            for (int i = 0; i < mapWidth; i++) {
                for (int j = 0; j < mapHeight; j++) {

                    if (mapData[i][j][z] != 0) {
                        ggg2d.drawImage(tileData[i][j][z], new AffineTransformOp(
                                new TileAffineTransformHelper().getTilesAffineTransform(mapData[i][j][z]), null), xpos, ypos);

                        if (TileManipulation.isTileSolid(mapData[i][j][z])) {
                            ggg2d.setColor(new Color(255, 0, 0, 100)); // A BIT LESS THAN 50% transparent 
                            ggg2d.fillRect(xpos, ypos, 32, 32);
                        }
                    }
                    xpos += 32;
                }
                xpos = 0;
                ypos += 32;
            }
            xpos = 0;
            ypos = 0;
        }*/
        
         /*ggg2d.drawImage(tileData[1][1],64 ,64,null);
        
        AffineTransform at2 = new AffineTransform();
        //at2 = at2.getScaleInstance(-1,1);
        
           /* Temporarily move the origin to the center of the image.
      All transforms are symmetrical about this point.
      Otherwise the image would rotate
      around the upper left corner, and disappear off screen. */
       /* at2.translate( 32 / 2.0, 32 / 2.0 );

   
        at2.scale(-1.0, 1.0);
        
        // put origin back to upper left corner
   at2.translate( -32 / 2.0, -32 / 2.0 );

        
         ggg2d.drawImage(tileData[1][1],new AffineTransformOp(at2,null),0 ,0);*/

        //Draw selection
        ggg2d.setColor(Color.BLUE);
        ggg2d.drawRect(selectedTileX, selectedTileY, 32, 32);
        
        ggg2d.setColor(Color.red);
        ggg2d.drawRect(mouseOverTileX, mouseOverTileY, 32, 32);
        
         ggg2d.setColor(Color.ORANGE);
         ggg2d.drawRect(selectedAreaStartTileX, selectedAreaStartTileY, selectedAreaEndTileX - selectedAreaStartTileX, selectedAreaEndTileY - selectedAreaStartTileY);
         
         //ggg2d.drawImage(tilesetImage.getSubimage(192,992, 32, 32), 64, 64, this);
        /*ggg2d.setColor(new Color(255,0,0,100)); // A BIT LESS THAN 50% transparent 
        
        ggg2d.fillRect(60, 60, 60, 60);*/
        /*AfineTransform Debugging
        ggg2d.drawImage(tileData[0][0], new AffineTransformOp(at, null), 50, 50);*/
        
        g.drawImage(buffer, 0, 0, this);
       
    }
    
    private Point getSelectedTileLocation(){
        
        return new Point(selectedTileX /32, selectedTileY /32);
       /* int x = selectedTileX;
        int y = selectedTileY;
      
        int sx = selectedTileX /32;
        int sy = selectedTileY /32;
        
        System.out.println("x= " + sx + ":" + "y=" + sy);
        
        //if(TileManipulation.getTileRotation(mapData[sx][sy]) == TileManipulation.tileRotateDirection.SOUTH){
                   // System.out.println("South");
            
                  mapData[sy][sx] = TileManipulation.setTileRotation(mapData[sy][sx], TileManipulation.tileRotateDirection.SOUTH);
                  
                  this.repaint();
        //}*/
    }


    public void mouseMoved(MouseEvent e) {


        
        if(e.getX() > mouseOverTileX + swidth || e.getY() > mouseOverTileY + sheight
        || e.getX() < mouseOverTileX || e.getY() < mouseOverTileY){
            mouseOverTileX = (int) Math.floor(e.getX() / swidth) * swidth;
            mouseOverTileY = (int) Math.floor(e.getY() / sheight) * sheight;

            this.repaint();
        }


    }

    public void mouseClicked(MouseEvent e) {
        
        int swidth = 32;
        int sheight = 32;
        
        this.selectedTileX = (int) Math.floor(e.getX() / swidth) * swidth;
        this.selectedTileY = (int) Math.floor(e.getY() / sheight) * sheight;
        
        selectedAreaStartTileX = 0;
        selectedAreaStartTileY = 0;
        
        selectedAreaEndTileX = 0;
        selectedAreaEndTileY = 0;
        
        this.repaint();
    }

    public void mousePressed(MouseEvent e) {
        
        int swidth = 32;
        int sheight = 32;
        
        selectedAreaStartTileX = (int) Math.floor(e.getX() / swidth) * swidth;
        selectedAreaStartTileY = (int) Math.floor(e.getY() / sheight) * sheight;
        
        selectedAreaEndTileX = 0;
        selectedAreaEndTileY = 0;
                
         System.out.println("Mouse pressed");
         //System.out.println("selectedAreaStartTileX = " + selectedAreaStartTileX);
         //System.out.println("selectedAreaStartTileY = " + selectedAreaStartTileY);
         
         /*selectedAreaEndTileX = (int) Math.floor(e.getX() / swidth) * swidth;
         selectedAreaEndTileX += swidth;
            selectedAreaEndTileY = (int) Math.floor(e.getY() / sheight) * sheight;
            selectedAreaEndTileY += sheight;
                    
         System.out.println("selectedAreaEndTileX = " + selectedAreaEndTileX);
         System.out.println("selectedAreaEndTileY = " + selectedAreaEndTileY); */  
            
            
    }

    public void mouseReleased(MouseEvent e) {
        System.out.println("Mouse released");

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
            

         
        //Works 
        
        
        //Only draw (repaint) when we need to? //This section is wrong.
       // if (e.getX() > selectedAreaEndTileX + swidth || e.getY() > selectedAreaEndTileY + sheight || 
        //e.getX() < selectedAreaEndTileX || e.getY() < selectedAreaEndTileY) {
        
                if(e.getX() > mouseOverTileX + swidth || e.getY() > mouseOverTileY + sheight
        || e.getX() < mouseOverTileX || e.getY() < mouseOverTileY){
            mouseOverTileX = (int) Math.floor(e.getX() / swidth) * swidth;
            mouseOverTileY = (int) Math.floor(e.getY() / sheight) * sheight;

            selectedAreaEndTileX = (int) Math.floor(e.getX() / swidth) * swidth;
            selectedAreaEndTileY = (int) Math.floor(e.getY() / sheight) * sheight;
            
           selectedAreaEndTileX += swidth;
           selectedAreaEndTileY += sheight;

           deleteme++;

            this.repaint();
            System.out.println("Dragging and repainting" + deleteme);

        }
        //System.out.println("dargging mouse");



//        int swidth = 32;
//        int sheight = 32;
//
//        mouseOverTileX = (int) Math.floor(e.getX() / swidth) * swidth;
//        mouseOverTileY = (int) Math.floor(e.getY() / sheight) * sheight;
//
//        this.repaint();

        
//        int swidth = 32;
//        int sheight = 32;
//        
//        mouseOverTileX = (int) Math.floor(e.getX() / swidth) * swidth;
//        mouseOverTileY = (int) Math.floor(e.getY() / sheight) * sheight;
//        
//        this.repaint();
    }
    
    private void switchToNextLayer(){
        this.selectedLayer++;
        
        if(selectedLayer > 2){
            this.selectedLayer = 0;
        }
        
        System.out.println(selectedLayer);
    }
    
    public void markTileAsSolid() {
        java.util.ArrayList<Point> selectTilesXY = this.getSelectedAreaTilesXY();
        

        for (int i = 0; i < selectTilesXY.size(); i++) {
                    
            mapData[selectTilesXY.get(i).y][selectTilesXY.get(i).x][selectedLayer] = TileManipulation.switchTileSolid(mapData[selectTilesXY.get(i).y][selectTilesXY.get(i).x][selectedLayer]);
            //tileData[selectTilesXY.get(i).y][selectTilesXY.get(i).x] = null; //<- Debugging
        }
        
        this.repaint();
    }

    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == 'd'){
            System.out.println("DELETE !!");
            markTileAsSolid();
        }else if(e.getKeyChar() == 'z'){
            switchToNextLayer();
        }else if(e.getKeyChar() == 'm'){
            handleMirror();
        } else if (e.getKeyChar() == 's') {
            saveMap();
        } else if (e.getKeyChar() == 'l') {
            loadMap();
        } else if (e.getKeyChar() == '1') {
            toggleLayer(1);
        } else if (e.getKeyChar() == '2') {
            toggleLayer(2);
        } else if (e.getKeyChar() == '3') {
            toggleLayer(3);
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            handleRotation(TileManipulation.tileRotateDirection.WEST);
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            handleRotation(TileManipulation.tileRotateDirection.EAST);
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            handleRotation(TileManipulation.tileRotateDirection.NORTH);
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            handleRotation(TileManipulation.tileRotateDirection.SOUTH);
        }
    }
    
    public void handleMirror(){
        int tile = mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer];
        
        if(TileManipulation.getTileFlipAttribute(tile) == TileManipulation.tileFlipAttribute.NOFLIP){
            mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer] = TileManipulation.setTileFlipAttribute(tile, TileManipulation.tileFlipAttribute.HORIZONTAL);
        }else if(TileManipulation.getTileFlipAttribute(tile) == TileManipulation.tileFlipAttribute.HORIZONTAL){
             mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer] = TileManipulation.setTileFlipAttribute(tile, TileManipulation.tileFlipAttribute.NOFLIP);
        }
            this.repaint();
    }
    
    public void handleRotation(TileManipulation.tileRotateDirection trd){
        
        int tile = mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer];
                
        switch (trd) {
            case NORTH:
                mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer] = TileManipulation.setTileRotation(tile, TileManipulation.tileRotateDirection.NORTH);
                break;
            case SOUTH:
                mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer] = TileManipulation.setTileRotation(tile, TileManipulation.tileRotateDirection.SOUTH);
                break;
            case EAST:
                mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer] = TileManipulation.setTileRotation(tile, TileManipulation.tileRotateDirection.EAST);
                break;
            case WEST:
                mapData[this.getSelectedTileLocation().y][this.getSelectedTileLocation().x][selectedLayer] = TileManipulation.setTileRotation(tile, TileManipulation.tileRotateDirection.WEST);
                break;
                
               // mapData[sy][sx] = TileManipulation.setTileRotation(mapData[sy][sx], TileManipulation.tileRotateDirection.SOUTH);
        }
        
        this.repaint();
    }
    
    public void saveMap() {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(MapDataFileEncoding.createMapString(mapData));
            out.close();

            /*DataInputStream in = new DataInputStream(
                    new FileInputStream(fileName));

            System.out.println("First Tile " + mapData[0][0]);
            System.out.println("Read in from file " + in.readInt());

            in.close();*/

        } catch (IOException e) {
        }
    }
    
    public void loadMap(){
        this.mapData = MapDataFileEncoding.createMapFromString(null, mapWidth, mapHeight, this.mapLayers);
        this.repaint();
    }

    public void keyPressed(KeyEvent e) {
               if(e.getKeyChar() == 'd'){
            System.out.println("DELETE");
            this.getSelectedAreaTilesXY();
        }else if(e.getKeyChar() == 'r'){
            //handleTile();
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT){
            handleRotation(TileManipulation.tileRotateDirection.WEST);
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            handleRotation(TileManipulation.tileRotateDirection.EAST);
        }else if(e.getKeyCode() == KeyEvent.VK_UP){
            handleRotation(TileManipulation.tileRotateDirection.NORTH);
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            handleRotation(TileManipulation.tileRotateDirection.SOUTH);
        }
    }

    public void keyReleased(KeyEvent e) {
       // throw new UnsupportedOperationException("Not supported yet.");
    }

    private void toggleLayer(int i) {
        this.layerVisableStatus[i - 1] = !this.layerVisableStatus[i - 1];
        this.repaint();
    }
}

