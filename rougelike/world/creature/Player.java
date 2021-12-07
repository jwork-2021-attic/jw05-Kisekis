package rougelike.world.creature;

import rougelike.screen.PlayScreen;
import rougelike.world.*;
import rougelike.world.creatureAI.PlayerAI;

import java.awt.*;
import java.util.List;


public class Player extends Creature {

    public PlayScreen screen;

    public PlayScreen getScreen() {
        return screen;
    }


    private int mana = 100;
    private int maxMana = 100;
    public int mana() {
        return this.mana;
    }
    public int maxMana() {return this.maxMana;}
    public void minusMana(int num) {
        mana = mana>num?
                ((mana-num)>maxMana?maxMana:mana-num):0;
    }

    private int coin = 1000;
    public int coin() {
        return this.coin;
    }
    public void minusCoin(int num) { coin = coin>num?coin-num:0;}

    private ObjtStack[] backpack;

    public int selectedObjt;

    public void setObjt(Objt obj,int i) {
        if(backpack[i] == null) {
            backpack[i] = new ObjtStack(obj,1);
        }else {
            backpack[i].num ++;
        }
        obj.setOwner(this);
    }
    public void useObjt(int i,int d) {
        if(i<0 && i>4) return;
        if(backpack[i]!=null) {
            backpack[i].objt.use(d);
            if(!backpack[i].objt.multiUse) {
                if(backpack[i].num != 1) {
                    backpack[i].num -=1;
                }else {
                    backpack[i] = null;
                }
            }
        }
    }
    public ObjtStack getObjt(int i) {
        return backpack[i];
    }

    public Player(World world, char glyph, int maxHP, int attack, int defense, int visionRadius, PlayScreen screen) {
        super(world,glyph,maxHP,attack,defense,visionRadius);
        backpack = new ObjtStack[5];
        this.screen = screen;
        selectedObjt = 0;
    }

    public void pick(Objt other,int i) {
        setObjt(other,i);
        world().remove(other);
    }


    @Override
    public void moveBy(int mx, int my) {
        int x = super.x();
        int y = super.y();
        World world = super.world();
        PlayerAI ai = (PlayerAI) super.ai();

        Object other = world.getEntity(x+mx,y+my);
        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        } else if(other instanceof Creature){
            if(other instanceof NPC) {
                ((NPC) other).interact(this);
            }else {
                attack((Creature) other);
            }
        }else if(other instanceof Objt) {
            addItem((Objt) other);
        }
    }

    public void addItem(Objt other) {
        other.setOwner(this);
        int mark = -1;
        for(int i =0;i<5;i++) {
            if(backpack[i] != null) {
                if(backpack[i].objt.getClass() == other.getClass()) {
                    pick((Objt)other,i);
                    mark = -1;
                    break;
                }
            }else if(mark == -1){
                mark = i;
            }
        }
        if(mark != -1) {
            pick((Objt)other,mark);
        }

    }

    public class ObjtStack {
        public Objt objt;
        public int num;
        public ObjtStack(Objt objt,int num) {
            this.num = num;
            this.objt = objt;
        }
    }

}

