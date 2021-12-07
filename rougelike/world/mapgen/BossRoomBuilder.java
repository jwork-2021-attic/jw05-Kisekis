package rougelike.world.mapgen;

import rougelike.world.CreatureFactory;
import rougelike.world.ObjectFactory;
import rougelike.world.Tile;
import rougelike.world.WorldBuilder;


public class BossRoomBuilder extends WorldBuilder{
    public BossRoomBuilder(int width, int height, boolean[] door) {
        super(width, height, door,(char) 144);
        makeRoom(width, height, door);
    }

    private void makeRoom(int width, int height, boolean[] door) {
        super.setBlock(1,1, Tile.WALL);
        super.setBlock(1,2,Tile.WALL);
        super.setBlock(2,1,Tile.WALL);

        super.setBlock(width-2,height-2, Tile.WALL);
        super.setBlock(width-3,height-2,Tile.WALL);
        super.setBlock(width-2,height -3,Tile.WALL);

        super.setBlock(1,height -2, Tile.WALL);
        super.setBlock(1,height -3,Tile.WALL);
        super.setBlock(2,height -2,Tile.WALL);

        super.setBlock(width -2,1, Tile.WALL);
        super.setBlock(width -2,2,Tile.WALL);
        super.setBlock(width -3,1,Tile.WALL);
    }

    @Override
    protected void createCreatures(CreatureFactory creatureFactory) {
        for (int i = 0; i < 32; i++) {
            creatureFactory.newMelee();
        }
    }
    @Override
    protected void createObjects(ObjectFactory objectFactory) {
        for(int i = 0;i < 32 ;i++) {
            objectFactory.newLootBag(false);
        }
    }
}
