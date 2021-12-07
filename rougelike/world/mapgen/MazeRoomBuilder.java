package rougelike.world.mapgen;

import rougelike.world.*;

public class MazeRoomBuilder extends WorldBuilder {
    public MazeRoomBuilder(int width, int height, boolean[] door) {
        super(width, height, door, (char) 14);
        makeRoom(width, height, door);
    }

    private void makeRoom(int width, int height, boolean[] door) {
        int d = (width-2)/3;
        MazeGenerator mazeGenerator = new MazeGenerator(d);
        mazeGenerator.generateMaze();
        while((door[0] && mazeGenerator.getMazeBlock(d/2,0)==0)
                ||(door[1] && mazeGenerator.getMazeBlock(d/2,d-1)==0
                ||(door[2] && mazeGenerator.getMazeBlock(0,d/2)==0)
                ||(door[3] && mazeGenerator.getMazeBlock(d-1,d/2)==0)
        )){
            mazeGenerator = new MazeGenerator(d);
            mazeGenerator.generateMaze();
        }

        for(int i = 0;i<d;i++) {
            for(int j =0;j<d;j++) {
                if(mazeGenerator.getMazeBlock(i,j)==0) {
                    super.setBlock(3*i+1,3*j+1,Tile.WALL);
                    super.setBlock(3*i+1,3*j+2,Tile.WALL);
                    super.setBlock(3*i+1,3*j+3,Tile.WALL);
                    super.setBlock(3*i+2,3*j+1,Tile.WALL);
                    super.setBlock(3*i+2,3*j+2,Tile.WALL);
                    super.setBlock(3*i+2,3*j+3,Tile.WALL);
                    super.setBlock(3*i+3,3*j+1,Tile.WALL);
                    super.setBlock(3*i+3,3*j+2,Tile.WALL);
                    super.setBlock(3*i+3,3*j+3,Tile.WALL);
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
