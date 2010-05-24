
package MapEncodingDecoding.TileDataManagment;

/**
 *
 * @author Jason Whatson
 */
public class TileManipulation {
    
    private static final int TILE_BIT_SIZE = 32;
    
    //Tile ID
    private static final int TILE_ID_BIT = 1;
    private static final int TILE_ID_SIZE = 8;
    
    private static final int MAX_TILEID = 255;
    private static final int MIN_TILEID = 0;
    
    //Tile Level
    private static final int TILE_Level_BIT = 8;
    private static final int TILE_Level_SIZE = 2;
    
    private static final int TILE_Level_CHECKCLEAR_MASK = 768;
    
    private static final int TILE_Level1_MASK = 0;
    private static final int TILE_Level2_MASK = 256;
    private static final int TILE_Level3_MASK = 512;
    private static final int TILE_Level4_MASK = 768;
    
    //Tile transaparency
    private static final int TILE_Trans_BIT = 11;
    private static final int TILE_Trans_Size = 1;
    private static final int TILE_TRANS_MASK = 1024;
    
    //Tile solid
    private static final int TILE_Soild_BIT = 12;
    private static final int TILE_Solid_Size = 1;
    private static final int TILE_Solid_MASK = 2048;
    
    //Tile rotate
    private static final int TILE_Rotate_BIT = 13;
    private static final int TILE_Rotate_Size = 2;
    
    public static enum tileRotateDirection{NORTH,SOUTH,EAST,WEST};
    
    private static final int TILE_Rotate_CHECKCLEAR_MASK = 12288;
    
    private static final int TILE_Rotate_NORTH_MASK = 0;
    private static final int TILE_Rotate_SOUTH_MASK = 4096;
    private static final int TILE_Rotate_EAST_MASK = 8192;
    private static final int TILE_Rotate_WEST_MASK = 12288;
    
    //Tile flip
    private static final int TILE_Flip_BIT = 15;
    private static final int TILE_Flip_Size = 2;
    
    public static enum tileFlipAttribute{NOFLIP,VERTICAL,HORIZONTAL,VERT_HORIZ}
    
    private static final int TILE_Flip_CHECKCLEAR_MASK = 49152;
    
    private static final int TILE_Flip_NOFLIP_MASK = 0;
    private static final int TILE_Flip_VERTICAL = 16384;
    private static final int TILE_Flip_HORIZONTAL_MASK = 32768;
    private static final int TILE_Flip_VERT_HORIZ_MASK = 49152;
    
    /*--------------------------
     *GETTERS 
     *-------------------------*/
    
    /**
     * Extracts the tile ID from the specified tile data (bits 7-15)
     * 
     * @param tile The tile to extract ID information from.
     * @return A byte primitive representing the tile ID.
     */
    public static int getTileID(int tile) {
        return (tile << (TILE_BIT_SIZE - TILE_ID_SIZE) >>> 
                (TILE_BIT_SIZE - TILE_ID_SIZE)); 
    }
    
    public static int getTileLevel(int tile){
        switch(tile & TILE_Level_CHECKCLEAR_MASK){
            case TILE_Level1_MASK:
                return 1;
            case TILE_Level2_MASK:
                return 2;
            case TILE_Level3_MASK:
                return 3;
        }
        return -1;
    }
    
    /**
     * Extracts transparency information from the supplied tile.
     * 
     * @param tile The tile to extract transparency information from.
     * @return The state of the transparency bit of tile.
     */
    public static boolean getTileTransparency(int tile) {
        return ((tile & TILE_TRANS_MASK) == TILE_TRANS_MASK);
    }
    
    public static boolean isTileSolid(int tile){
        return ((tile & TILE_Solid_MASK) == TILE_Solid_MASK);
    }
    
    public static tileRotateDirection getTileRotation(int tile) {
        switch (tile & TILE_Rotate_CHECKCLEAR_MASK) {
            case TILE_Rotate_NORTH_MASK:
                return TileManipulation.tileRotateDirection.NORTH;
            case TILE_Rotate_EAST_MASK:
                return TileManipulation.tileRotateDirection.EAST;
            case TILE_Rotate_SOUTH_MASK:
                return TileManipulation.tileRotateDirection.SOUTH;
            case TILE_Rotate_WEST_MASK:
                return TileManipulation.tileRotateDirection.WEST;                 
        }
        
        return TileManipulation.tileRotateDirection.NORTH;
    }
    
    public static tileFlipAttribute getTileFlipAttribute(int tile) {
        switch (tile & TILE_Flip_CHECKCLEAR_MASK) {
            case TILE_Flip_NOFLIP_MASK:
                return tileFlipAttribute.NOFLIP;
            case TILE_Flip_VERTICAL:
                return tileFlipAttribute.VERTICAL;
            case TILE_Flip_HORIZONTAL_MASK:
                return tileFlipAttribute.HORIZONTAL;
            case TILE_Flip_VERT_HORIZ_MASK:
                return tileFlipAttribute.VERT_HORIZ;
        }

        return tileFlipAttribute.NOFLIP;
    }
    
    /*--------------------------
     *MUTATORS
     *-------------------------*/
       
    public static int setTileID(int tile, int tileID) {
        if (tileID > MAX_TILEID || tileID < MIN_TILEID) {
            return -1;
        }
        return tileID | (tile >> TILE_ID_SIZE) << TILE_ID_SIZE;
    }
     
    public static int setTileLevel(int tile,int level) {
        
        //Clear level bits 
        tile = tile & ~TILE_Level_CHECKCLEAR_MASK;
        
        switch (level) {
            case 1:
                return tile;
            case 2:
                return tile | TILE_Level2_MASK;
            case 3:
                return tile | TILE_Level3_MASK;
        }        
               
        return -1;
    }
    
    /**
     * Toggles the specified tile's transparency bit.
     * @param tile
     * @return
     */
    public static int switchTileTransparency(int tile) {
        return tile ^ (1 << (TILE_Trans_BIT -1));
    }
    
    public static int switchTileSolid(int tile) {
        return tile ^ (1 << (TILE_Soild_BIT -1));
    }
    
    public static int setTileRotation(int tile,tileRotateDirection trd) {
        
        //Clear rotate bits 
        tile = tile & ~TILE_Rotate_CHECKCLEAR_MASK;
        
        switch (trd) {
            case NORTH:
                return tile;
            case SOUTH:
                return tile | TILE_Rotate_SOUTH_MASK;
            case EAST:
                return tile | TILE_Rotate_EAST_MASK;
            case WEST:
                return tile | TILE_Rotate_WEST_MASK;
        }   
               
        return -1;
    }
    
    public static int setTileFlipAttribute(int tile, tileFlipAttribute tfa) {
        //Clear flip bits 
        tile = tile & ~TILE_Flip_CHECKCLEAR_MASK;

        switch (tfa) {
            case NOFLIP:
                return tile;
            case VERTICAL:
                return tile | TILE_Flip_VERTICAL;
            case HORIZONTAL:
                return tile | TILE_Flip_HORIZONTAL_MASK;
            case VERT_HORIZ:
                return tile | TILE_Flip_VERT_HORIZ_MASK;
        }
        
        return -1;
    }
    
    /*--------------------------
     *Other/Generic 
     *-------------------------*/
    
    public static int generateTile(int tileID,int level,boolean transparent,
            boolean issolid,tileRotateDirection trd,tileFlipAttribute tfa){
        
        int tile = 0;
        
        tile = setTileID(tile, tileID);
        tile = setTileLevel(tile, level);
        if(transparent) tile = switchTileTransparency(tile);
        if(issolid) tile = switchTileSolid(tile);
        tile = setTileRotation(tile, trd);
        tile = setTileFlipAttribute(tile, tfa);
    
        return tile;
    }
    
    //This will check to see if a tile is in a default state
    public static boolean isDefaultTile(int tile){
        return true;
    }

    /**
     * Generic method for bit toggling. Toggles the specified bit of the 
     * specified tile.
     * 
     * @param tile The tile to modify.
     * @param bit The bit to toggle (0 = least significant bit)
     * @return The modified tile.
     */
    public static int switchTileBit(int tile, int bit) {
        return tile = (tile ^ (1 << bit));
    }
}
