package rougelike.world.mapgen;

import java.util.Random;

public enum WorldType {
    Maze,
    Boss,
    Random;
    public static WorldType getRandomType() {
        Random r = new Random();
        WorldType[] types = WorldType.values();
        int rand = r.nextInt(types.length);
        if(types[rand] == Boss) {
            return getRandomType();
        }else {
            return types[rand];
        }
    }
}
