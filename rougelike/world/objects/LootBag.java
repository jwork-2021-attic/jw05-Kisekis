package rougelike.world.objects;

import rougelike.world.Objt;
import rougelike.world.World;
import rougelike.world.creature.Player;

import java.awt.*;
import java.util.Random;

public class LootBag extends Objt {
    private int countCoin;
    private int countMana;
    private int countHP;
    private boolean isKey;

    public LootBag(World world, char glyph,boolean isKey) {
        super(world,glyph);
        Random rand = new Random();
        countCoin = rand.nextInt(50)+1;
        countMana = rand.nextInt(20)+1;
        countHP = rand.nextInt(20) + 1;
        this.isKey = isKey;
    }

    @Override
    public void use(int d) {
        if(isKey) {
            Key k = new Key(getOwner().world(), (char)8);
            ((Player)getOwner()).addItem(k);
        }else {
            Random rand = new Random();
            int r = rand.nextInt(6);
            if(r == 0) {
                ((Player)getOwner()).minusMana(-countMana);
            }else if(r == 1) {
                ((Player)getOwner()).modifyHP(countHP);
            }else {
                ((Player)getOwner()).minusCoin(-countCoin);
            }
        }


    }
}
