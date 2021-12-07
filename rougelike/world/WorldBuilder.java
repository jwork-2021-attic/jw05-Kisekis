package rougelike.world;

import rougelike.world.mapgen.BossRoomBuilder;
import rougelike.world.mapgen.MazeRoomBuilder;
import rougelike.world.mapgen.RandomBlockRoomBuilder;
import rougelike.world.mapgen.WorldType;

public class WorldBuilder {

    private int width;
    private int height;
    private char glyph;
    private Tile[][] tiles;
    protected boolean[] door;

    public WorldBuilder(int width, int height, boolean[] door, char glyph) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.glyph = glyph;
        this.door = door;
        makeBoundary();
    }

    public static WorldBuilder WorldBuilder(int width, int height, boolean[] neighbor, WorldType type) {
        switch (type){
            case Maze:
                return new MazeRoomBuilder(width,height,neighbor);
            case Boss:
                return new BossRoomBuilder(width,height,neighbor);
            case Random:
                return new RandomBlockRoomBuilder(width,height,neighbor);
        }
        return null;
    }

    public void setBlock(int x,int y,Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getBlock(int x,int y) {return tiles[x][y];}

    public World build() {
        World world = new World(tiles,glyph);
        generateEntity(world);
        return world;
    }

    public void makeBoundary() {
        for(int i =0;i<width;i++) {
            for(int j = 0;j<height;j++) {
                if(i == 0 || j == 0 || i == width-1 || j == height -1) {
                    tiles[i][j] = Tile.BOUNDS;
                }else {
                    tiles[i][j] = Tile.FLOOR;
                }
            }
        }
    }
    public void makeDoor(boolean[] door) {
        if(door[0]) {
            tiles[width/2][0] = Tile.DOOR;
            tiles[width/2+1][0] = Tile.DOOR;
            tiles[width/2 + 2][0] = Tile.DOOR;
        }
        if(door[1]) {
            tiles[width/2][height-1] = Tile.DOOR;
            tiles[width/2+1][height-1] = Tile.DOOR;
            tiles[width/2 + 2][height-1] = Tile.DOOR;
        }
        if(door[2]) {
            tiles[0][height/2] = Tile.DOOR;
            tiles[0][height/2+1] = Tile.DOOR;
            tiles[0][height/2+2] = Tile.DOOR;
        }
        if(door[3]) {
            tiles[width-1][height/2] = Tile.DOOR;
            tiles[width-1][height/2+1] = Tile.DOOR;
            tiles[width-1][height/2+2] = Tile.DOOR;
        }
    }
    public void generateEntity(World world) {
        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);
        ObjectFactory objectFactory = new ObjectFactory(world);
        createObjects(objectFactory);
    }
    protected void createCreatures(CreatureFactory creatureFactory) {
    }

    protected void createObjects(ObjectFactory objectFactory) {
    }
}
