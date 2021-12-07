package rougelike.world.creatureAI;

import rougelike.world.CreatureAI;
import rougelike.world.CreatureFactory;
import rougelike.world.NPC;

public class NPCAI extends CreatureAI {
    private NPC npc;

    private CreatureFactory factory;

    public NPCAI(NPC npc, CreatureFactory factory) {
        super(npc);
        this.npc = npc;
        this.factory = factory;
    }

    public void onUpdate() {

    }
}
