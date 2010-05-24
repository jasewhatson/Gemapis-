/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MapEncodingDecoding.TileDataManagment;
import java.awt.geom.AffineTransform;
import MapEncodingDecoding.TileDataManagment.TileManipulation;


/**
 *
 * @author Jason
 * TODO: Check to see if these methods should be static
 */
public class TileAffineTransformHelper {
    
    private static AffineTransform at = new AffineTransform(); 
    
    public static AffineTransform getTilesAffineTransform(int tile){
        at = new AffineTransform();
        setTileATDirection(tile);
        setTileATMirror(tile);
        return at;
    }
    
    private static void setTileATMirror(int tile) {
        if (TileManipulation.getTileFlipAttribute(tile) != TileManipulation.tileFlipAttribute.NOFLIP) {
            /* Temporarily move the origin to the center of the image.
            All transforms are symmetrical about this point.
            Otherwise the image would rotate
            around the upper left corner, and disappear off screen. */
            at.translate(32 / 2.0, 32 / 2.0);
            at.scale(-1.0, 1.0);
            // put origin back to upper left corner
            at.translate(-32 / 2.0, -32 / 2.0);
        }
    }
    
    private static void setTileATDirection(int tile){
        ////at.rotate(180.0 * Math.PI / 180.0, 32 / 2.0, 32 / 2.0);
        
        switch (TileManipulation.getTileRotation(tile)) {
            case NORTH:
                return;
            case SOUTH:
                at.quadrantRotate(2,32/2,32/2);
                return;
            case EAST:
                at.quadrantRotate(1,32/2,32/2);
                return;
            case WEST:
                at.quadrantRotate(3,32/2,32/2);
                return;
        }
    }

}
