/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package MapEncodingDecoding.TileDataManagment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class MapDataFileEncoding {
    //cOMMENTR
    public static String createMapString(int[][][] mapData) {
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream(mapData.length * mapData[0].length);
            
        DataOutputStream out = new DataOutputStream(baos);
       
        for(int z = 0; z < mapData[0][0].length; z++){
            for(int i = 0;i < mapData[0].length;i++){
                for (int j = 0; j < mapData.length; j++) {
                    try {
                        out.writeInt(mapData[i][j][z]);
                    } catch (IOException ex) {
                        Logger.getLogger(MapDataFileEncoding.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }   
            }
        }
        
        return baos.toString();
    }
    
    public static int[][][] createMapFromString(String encodedMapString,int width, int height,int depth){
        int[][][] mapData = new int[height][width][3];
         DataInputStream in = null;
        /*DataInputStream in = new DataInputStream( 
                    new ByteArrayInputStream(encodedMapString.getBytes())); <- FOR REAL IMPLEMENTATION*/ 
        try{
           in = new DataInputStream(new FileInputStream("TileData.bin"));
        }catch(FileNotFoundException ex){
            Logger.getLogger(MapDataFileEncoding.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int z = 0; z < depth; z++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    try {
                        mapData[i][j][z] = in.readInt();
                    } catch (IOException ex) {
                        Logger.getLogger(MapDataFileEncoding.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        
        return mapData;
    }

}
