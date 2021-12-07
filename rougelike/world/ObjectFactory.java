package rougelike.world;

import rougelike.asciiPanel.AsciiPanel;
import rougelike.world.objects.*;
import rougelike.world.weapon.Gun;

public class ObjectFactory {
    private World world;

    public ObjectFactory(World world) {
        this.world = world;
    }

    public LootBag newLootBag(boolean isKey) {
        LootBag bag = new LootBag(this.world, (char)7,isKey);
        world.addAtEmptyLocation(bag);
        return bag;
    }

    public Gun newGun() {
        Gun gun = new Gun(this.world, (char)6);
        world.addAtEmptyLocation(gun);
        return gun;
    }

}
