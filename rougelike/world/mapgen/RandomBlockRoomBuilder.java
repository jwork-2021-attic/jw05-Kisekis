package rougelike.world.mapgen;

import rougelike.world.CreatureFactory;
import rougelike.world.ObjectFactory;
import rougelike.world.Tile;
import rougelike.world.WorldBuilder;

import java.util.Random;

public class RandomBlockRoomBuilder extends WorldBuilder {
    public RandomBlockRoomBuilder(int width, int height, boolean[] door) {
        super(width, height, door, (char) 15);
        makeRoom(width, height, door);
    }
    private void makeRoom(int width, int height, boolean[] door) {
        Random r = new Random();
        for(int i = 0;i<(width-2)/3;i++) {
            for(int j = 0;j<(height-2)/3;j++) {
                if( (i==0)&& (j==(width-2)/6)
                        ||(i == (width-2)/3-1) && (j==(width-2)/6)
                        ||(j==0)&& (i==(width-2)/6)
                        ||(j == (width-2)/3-1) && (i==(width-2)/6)
                ) {
                    continue;
                }else{
                    if(r.nextInt(2) == 1) {
                        int a = r.nextInt(9);
                        int b = r.nextInt(9);
                        super.setBlock(3*i+1+a/3,3*j+1+a%3, Tile.BOX);
                        super.setBlock(3*i+1+b/3,3*j+1+b%3, Tile.BOX);
                    }
                }
            }
        }
    }

    @Override
    protected void createCreatures(CreatureFactory creatureFactory) {
        for (int i = 0; i < 8; i++) {
            creatureFactory.newMelee();
            creatureFactory.newMagician();
        }
        creatureFactory.newTrader();
    }
    @Override
    protected void createObjects(ObjectFactory objectFactory) {
        for(int i = 0;i < 16 ;i++) {
            objectFactory.newLootBag(false);

        }
//        for(boolean x : super.door){
//            if(x) {
//                objectFactory.newLootBag(true);
//            }
//        }
    }
}
