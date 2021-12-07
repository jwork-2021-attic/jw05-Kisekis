package rougelike.world;


public enum Tile {

    FLOOR((char) 12),

    WALL((char) 14),

    BOX((char) 15),

    BOUNDS((char) 0),

    DOOR((char)13),

    OPENED_DOOR_0((char)9),

    OPENED_DOOR_1((char)10);

    private char glyph;

    public char glyph() {
        return glyph;
    }

    public boolean isDiggable() {
        return
                this != Tile.BOUNDS
                && this != Tile.DOOR
                && this != Tile.BOX
                ;
    }

    public boolean isGround() {
        return this == Tile.FLOOR;
    }

    public boolean isEnterable() { return this == Tile.DOOR;}

    Tile(char glyph) {
        this.glyph = glyph;
    }
}
