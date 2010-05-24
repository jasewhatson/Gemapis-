/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package UI;

import javax.swing.JFileChooser;

/**
 *
 * @author jason
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ImageSheetTilePreview isp = new ImageSheetTilePreview();
        JFileChooser ch = new JFileChooser();
        ch.setAccessory(isp);
        ch.addPropertyChangeListener(isp);
        ch.showOpenDialog(ch);
    }

}
