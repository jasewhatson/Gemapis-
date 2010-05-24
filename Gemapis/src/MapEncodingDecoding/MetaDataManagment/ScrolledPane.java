/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MapEncodingDecoding.MetaDataManagment;

import MapEncodingDecoding.TileDataManagment.TileCanvas;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

class ScrolledPane
		extends 	JFrame
{
	private		JScrollPane scrollPane;

	public ScrolledPane()
	{
		setTitle( "Scrolling Pane Application" );
		setSize( 300, 200 );
		setBackground( Color.gray );

		JPanel topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		Icon image = new ImageIcon( "src/Resources/PokemonTileSet.png" );
		JLabel label = new JLabel( image );
                label.setAutoscrolls(true);
                
                 MouseMotionListener doScrollRectToVisible = new MouseMotionAdapter() {
     public void mouseDragged(MouseEvent e) {
        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        ((JLabel)e.getSource()).scrollRectToVisible(r);
    }
 };
 label.addMouseMotionListener(doScrollRectToVisible);


		// Create a tabbed pane
		scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		//scrollPane.getViewport().add(new TileCanvas());
                scrollPane.getViewport().add(label);
		topPanel.add( scrollPane, BorderLayout.CENTER );
	}


	public static void main( String args[] )
	{
		// Create an instance of the test application
		ScrolledPane mainFrame	= new ScrolledPane();
		mainFrame.setVisible( true );
	}
}


