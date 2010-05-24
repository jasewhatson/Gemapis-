/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import MapEncodingDecoding.TileDataManagment.TileCanvas;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author jason
 */
public class ImageSheetTilePreview extends JPanel implements
        java.awt.event.MouseMotionListener, PropertyChangeListener{
    
    private JScrollPane imageSheetScroller;
    private JPanel imageSheetPanel;
    private JPanel belowImageSheetPanel;
    private JPanel tilePreviewPanel;
    private JPanel dimensionsPanel;
    private JTextField tileWidth;
    private JTextField tileHeight;
    private BufferedImage tileImage = null;
    
    //the last images that were added to the panels. Used for removing them
    //  later ;)
    private JLabel lastImageSheet = null;
    private JLabel lastTilePreview = null;
    // floor(pos/get.width)* get.width
    public ImageSheetTilePreview() {
        
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        
        imageSheetPanel = new JPanel();
        
        //imageSheetPanel.addMouseListener(this);
        imageSheetPanel.addMouseMotionListener(this);
        
        imageSheetScroller = new JScrollPane(imageSheetPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        belowImageSheetPanel = new JPanel(new GridLayout(1,2));
        
        this.add(imageSheetScroller);
        this.add(belowImageSheetPanel);
        
        imageSheetScroller.setPreferredSize(new Dimension(350, 50));
        
        dimensionsPanel = new JPanel(new GridLayout(2,2));
        
        tilePreviewPanel = new JPanel();
        
        belowImageSheetPanel.add(dimensionsPanel);
        belowImageSheetPanel.add(tilePreviewPanel);
        
        dimensionsPanel.add(new javax.swing.JLabel("Tile width:"));
        dimensionsPanel.add(tileWidth = new JTextField("32"));
        dimensionsPanel.add(new javax.swing.JLabel("Tile height:"));
        dimensionsPanel.add(tileHeight = new JTextField("60"));
        
    }
    
    public void propertyChange(PropertyChangeEvent evt) {
        
        //Only listen to property changed evens
        if (evt.getPropertyName().equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            //Retrive the selected file
            File selectedFile = (File) evt.getNewValue();
            
            //If the file
            if (selectedFile == null){
                return;
            }
            
            //If the images is not null and is a image type file
            if (
                    selectedFile.getAbsolutePath().toLowerCase().endsWith(".jpg") ||
                    selectedFile.getAbsolutePath().toLowerCase().endsWith(".jpeg") ||
                    selectedFile.getAbsolutePath().toLowerCase().endsWith(".gif") ||
                    selectedFile.getAbsolutePath().toLowerCase().endsWith(".png")) {
                try {
                    
                    
                    tileImage = ImageIO.read( new File(selectedFile.getAbsolutePath()) );
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
                if (lastImageSheet != null)
                    imageSheetPanel.remove(lastImageSheet);
                if (lastTilePreview != null)
                    tilePreviewPanel.remove(lastTilePreview);
                
                lastImageSheet = new JLabel(new ImageIcon(tileImage));
                lastTilePreview = new JLabel(new ImageIcon(tileImage.getSubimage(0,0,Integer.parseInt(this.tileWidth.getText()),Integer.parseInt(this.tileHeight.getText()))));
                
                
                imageSheetPanel.add(lastImageSheet);
                //imageSheetPanel.add(new TileCanvas());
                tilePreviewPanel.add(lastTilePreview);
                
                this.validate();
                this.repaint();
                imageSheetPanel.repaint();
                
            }else{
                return;
            }
        }
    }
    
    public void mouseDragged(MouseEvent e) {
    }
    
    public void mouseMoved(MouseEvent e) {
        if (lastTilePreview != null)
            tilePreviewPanel.remove(lastTilePreview);
        
        int swidth = Integer.parseInt(this.tileWidth.getText());
        int sheight = Integer.parseInt(this.tileHeight.getText());
        
        lastTilePreview = new JLabel(new ImageIcon(tileImage.getSubimage((int) Math.floor(e.getX() / swidth) * swidth, (int) Math.floor(e.getY() / sheight) * sheight ,Integer.parseInt(this.tileWidth.getText()),Integer.parseInt(this.tileHeight.getText()))));
        
        
        tilePreviewPanel.add(lastTilePreview);
        
        this.validate();
        this.repaint();
    }
}

