/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package rougelike.world;

import rougelike.world.creature.Bullet;
import rougelike.world.creature.Player;
import rougelike.world.creatureAI.BulletAI;

import java.awt.Color;

public class Creature implements Ini{

    private World world;

    private Thread thread;

    public World world() {return world;}

    public void setWorld(World world) {
        this.world = world;
    }

    private int x;

    public void setX(int x) {
        this.x = x;
    }

    public int x() {
        return x;
    }

    private int y;

    public void setY(int y) {
        this.y = y;
    }

    public int y() {
        return y;
    }

    private Objt holdObjt;

    public Objt getHoldObjt() {
        return holdObjt;
    }

    public void setHoldObjt(Objt objt) {
        holdObjt = objt;
    }

    private char glyph;

    public char glyph() {
        return this.glyph;
    }


    private CreatureAI ai;

    public void setAI(CreatureAI ai) {
        this.ai = ai;
    }

    public CreatureAI ai() {return ai;}

    private int maxHP;

    public int maxHP() {
        return this.maxHP;
    }

    private int hp;

    public int hp() {
        return this.hp;
    }

    public void modifyHP(int amount) {
        this.hp = hp + amount <=maxHP?hp + amount:maxHP;

        if (this.hp < 1) {
            world.remove(this);
        }
    }
    private int attackValue;

    public int attackValue() {
        return this.attackValue;
    }

    private int defenseValue;

    public int defenseValue() {
        return this.defenseValue;
    }

    private int visionRadius;

    public int visionRadius() {
        return this.visionRadius;
    }

    public boolean canSee(int wx, int wy) {
        return ai.canSee(wx, wy);
    }

    public Tile tile(int wx, int wy) {
        return world.tile(wx, wy);
    }

    public void dig(int wx, int wy) {
        world.dig(wx, wy);
    }

    public void moveBy(int mx, int my) {
        if(mx == 0 && my == 0) return;
        Object other = world.getEntity(x+mx,y+my);

        if (other == null) {
            ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        } else if(other instanceof Creature){
            attack((Creature) other);
        }else if(other instanceof Objt) {
        }
    }

    public void teleport(int x,int y) {
        Object other = world.getEntity(x,y);
        Tile tile = world.tile(x,y);
        if(other == null && tile.isGround()) {
            setX(x);
            setY(y);
        }
    }

    public void attack(Creature other) {
        if(!((this instanceof Player && !(other instanceof Player))
                || (!(this instanceof Player) && other instanceof Player))) {
            return;
        }
        int damage = Math.max(0, this.attackValue() - other.defenseValue());
        damage = (int) (Math.random() * damage) + 1;

        char c1 = (char)((int)other.glyph+16);
        char c2 = (char)((int)glyph+16);
        other.modifyHP(-damage);
        if(this instanceof Bullet) {
            c2 = (char)((int)(((Bullet) this).getOwner().glyph())+16);
        }
        this.notify("You attack the '%s' for %d damage.", c1, damage);
        other.notify("The '%s' attacks you for %d damage.", c2, damage);
    }

    public void update() {
        this.ai.onUpdate();
    }

    public boolean canEnter(int x, int y) {
        return world.tile(x, y).isGround();
    }

    public void notify(String message, Object... params) {
        ai.onNotify(String.format(message, params));
    }

    public Creature(World world, char glyph, int maxHP, int attack, int defense, int visionRadius) {
        this.world = world;
        this.glyph = glyph;
        this.maxHP = maxHP;
        this.hp = maxHP;
        this.attackValue = attack;
        this.defenseValue = defense;
        this.visionRadius = visionRadius;
    }
}
