/*
 * r3 : Added a grid to the preview image representing tile width and height.
 * r2 : Class clean up to remove component recreation.
 * r1 : Initial Revision.
 */
package UI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
 * @author jason
 */
public class ImageSheetTilePreview extends JPanel implements
        java.awt.event.MouseMotionListener, PropertyChangeListener, DocumentListener {

    private ImageJLabel tileCanvas;
    private JScrollPane imageSheetScroller;
    private JPanel imageSheetPanel;
    private JPanel belowImageSheetPanel;
    private JPanel tilePreviewPanel;
    private JPanel dimensionsPanel;
    private JTextField tileWidth;
    private JTextField tileHeight;
    private BufferedImage tileImage = null;
    private GridedImageJLabel imageSheet = null;
    // floor(pos/get.width)* get.width
    private String[] supportedFormats = new String[] {".jpg", ".jpeg", ".gif", ".png"};

    public ImageSheetTilePreview() {
        this.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );

        imageSheetPanel = new JPanel();
        imageSheet = new GridedImageJLabel();
        imageSheetPanel.add( imageSheet );
        //imageSheetPanel.addMouseListener(this);
        imageSheet.addMouseMotionListener( this );
        imageSheetScroller = new JScrollPane( imageSheetPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

        belowImageSheetPanel = new JPanel( new GridLayout( 1, 2 ) );
        imageSheetScroller.setPreferredSize( new Dimension( 350, 150 ) );

        dimensionsPanel = new JPanel( new GridLayout( 2, 2 ) );

        tilePreviewPanel = new JPanel( new BorderLayout() );
        //tilePreview = new JLabel();
        tileCanvas = new ImageJLabel();
        tilePreviewPanel.add( tileCanvas );



        dimensionsPanel.add( new javax.swing.JLabel( "Tile width:" ) );
        dimensionsPanel.add( tileWidth = new JTextField( "32" ) );
        dimensionsPanel.add( new javax.swing.JLabel( "Tile height:" ) );
        dimensionsPanel.add( tileHeight = new JTextField( "60" ) );

        tileWidth.getDocument().addDocumentListener( this );
        tileHeight.getDocument().addDocumentListener( this );

        belowImageSheetPanel.add( dimensionsPanel );
        belowImageSheetPanel.add( tilePreviewPanel );

        this.add( imageSheetScroller );
        this.add( belowImageSheetPanel );
    }

    private boolean isSupportedImage( File file ) {
        String name = file.getAbsolutePath().toLowerCase();
        for ( String str : supportedFormats ) {
            if ( name.endsWith( str ) ) {
                return true;
            }
        }
        return false;
    }

    public void propertyChange( PropertyChangeEvent evt ) {

        //Only listen to property changed evens
        if ( evt.getPropertyName().equals( JFileChooser.SELECTED_FILE_CHANGED_PROPERTY ) ) {
            //Retrive the selected file
            File selectedFile = (File) evt.getNewValue();

            //If the file is null
            if ( selectedFile == null ) {
                return;
            }

            //If the images is not null and is a image type file
            if ( selectedFile != null && isSupportedImage( selectedFile ) ) {
                try {
                    tileImage = ImageIO.read( new File( selectedFile.getAbsolutePath() ) );

                    imageSheet.setIcon( new ImageIcon( tileImage ) );
                    int width = Integer.parseInt( this.tileWidth.getText() );
                    int height = Integer.parseInt( this.tileHeight.getText() );
                    setTileFromPreview( 0, 0, width, height );

                    refreshGrid();
                    tilePreviewPanel.repaint();
                    imageSheet.repaint();

                } catch ( IOException ex ) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void setTileFromPreview( int mX, int mY, int swidth, int sheight ) {
        int iwidth = tileImage.getWidth();
        int iheight = tileImage.getHeight();
        int iX = (int) Math.floor( mX / swidth ) * swidth;
        int iY = (int) Math.floor( mY / sheight ) * sheight;

        if ( ( iX + swidth ) > iwidth || ( iY + sheight ) > iheight
                || iwidth <= 0 || iheight <= 0 ) {
            this.tileCanvas.setImage( null );
            return;
        }

        Image img = tileImage.getSubimage( iX, iY, swidth, sheight );
        this.tileCanvas.setImage( img );
    }

    private void refreshGrid() {
        int width = 0, height = 0;
        //Two trys here: forces width and height to be tried seperatly
        try {
            width = Integer.parseInt( this.tileWidth.getText() );
        } catch ( NumberFormatException nfe ) {
        }
        try {
            height = Integer.parseInt( this.tileHeight.getText() );
        } catch ( NumberFormatException nfe ) {
        }
        if ( width > 0 && height > 0 ) {
            setTileFromPreview( 0, 0, width, height );
        }
        imageSheet.setGrid( width, height );
        imageSheet.repaint();
    }

    public void mouseDragged( MouseEvent e ) {
    }

    public void mouseMoved( MouseEvent e ) {
        if ( e.getSource() == imageSheet ) {
            if ( tileImage != null ) {
                try {
                    int width = Integer.parseInt( this.tileWidth.getText() );
                    int height = Integer.parseInt( this.tileHeight.getText() );
                    setTileFromPreview( e.getX(), e.getY(), width, height );
                } catch ( NumberFormatException ex ) {
                }
            }
        }
    }

    public void insertUpdate( DocumentEvent e ) {
        refreshGrid();
    }

    public void removeUpdate( DocumentEvent e ) {
        refreshGrid();
    }

    public void changedUpdate( DocumentEvent e ) {
    }

    class ImageJLabel extends JLabel {

        private Image image = null;

        @Override
        public void paint( Graphics g ) {
            super.paint( g );
            if ( image != null ) {
                int x = Math.max( 0, ( this.getWidth() - image.getWidth( null ) ) / 2 );
                int y = Math.max( 0, ( this.getHeight() - image.getHeight( null ) ) / 2 );
                g.drawImage( image, x, y, null );
            }
        }

        public void setImage( Image img ) {
            this.image = img;
            this.repaint();
        }
    }

    class GridedImageJLabel extends JLabel {

        protected int gridWidth = 0;
        protected int gridHeight = 0;

        @Override
        public void paint( Graphics g ) {
            super.paint( g );
            if ( gridWidth > 0 && gridHeight > 0 ) {
                g.setColor( Color.yellow );
                for ( int r = gridHeight; r < this.getHeight(); r += gridHeight ) {
                    g.drawLine( 0, r, this.getWidth(), r );
                }
                for ( int c = gridWidth; c < this.getWidth(); c += gridWidth ) {
                    g.drawLine( c, 0, c, this.getHeight() );
                }
            }
        }

        public void setGrid( int width, int height ) {
            this.gridWidth = width;
            this.gridHeight = height;
        }
    }
}

