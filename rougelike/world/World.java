package rougelike.world;

import rougelike.world.creature.Player;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {

    private Tile[][] tiles;
    private int width;
    private int height;
    private List<Creature> creatures;
    private List<Objt> objects;
    private char glyph;
    public char glyph() {return glyph;};
    private boolean isVisited;
    public boolean isVisited(){return isVisited;}
    public void visit() {isVisited = true;}
    private boolean isPlayerIn;
    public boolean isPlayerIn() {return isPlayerIn;}
    public void setPlayerIn(boolean isIn) {isPlayerIn = isIn;}


    public static final int TILE_TYPES = 2;

    public World(Tile[][] tiles,char glyph) {
        this.tiles = tiles;
        this.width = tiles.length;
        this.height = tiles[0].length;
        this.creatures = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.glyph = glyph;
        isVisited = false;
    }

    public Tile tile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[x][y];
        }
    }

    public char glyph(int x, int y) {
        return tiles[x][y].glyph();
    }


    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void dig(int x, int y) {
        if (tile(x, y).isDiggable()) {
            tiles[x][y] = Tile.FLOOR;
        }
    }

    public <T extends Ini> void addAtEmptyLocation(T t) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * this.width);
            y = (int) (Math.random() * this.height);
        } while (!tile(x, y).isGround()
                || this.getEntity(x,y) != null
        );

        t.setX(x);
        t.setY(y);
        if(t instanceof Creature) {
            this.creatures.add((Creature) t);
        }
        if(t instanceof Objt) {
            this.objects.add((Objt) t);
        }
    }


    public Object getEntity(int x,int y) {
        for (Objt c : this.objects) {
            if (c.x() == x && c.y() == y) {
                return c;
            }
        }
        for (Creature c : this.creatures) {
            if (c.x() == x && c.y() == y) {
                return c;
            }
        }
        return null;

    }

    public List<Creature> getCreatures() {
        return this.creatures;
    }
    public List<Objt> getObjects() {
        return this.objects;
    }

    public <T extends Ini>void add(T target) {
        if(target instanceof Creature) {
            this.creatures.add((Creature) target);
        }
        if(target instanceof Objt) {
            this.objects.add((Objt) target);
        }
    }

    public <T extends Ini> void remove(T target) {
        if(target instanceof Creature) {
            this.creatures.remove(target);
        }
        if(target instanceof Objt) {
            this.objects.remove(target);
        }

    }

    public Player getPlayer() {
        if(isPlayerIn){
            for(Creature x : creatures) {
                if(x instanceof Player) {
                    return (Player) x;
                }
            }
        }
        return null;
    }


    public void update() {
        ArrayList<Creature> toUpdate = new ArrayList<>(this.creatures);

        for (Creature creature : toUpdate) {
            creature.update();
        }
    }
}
