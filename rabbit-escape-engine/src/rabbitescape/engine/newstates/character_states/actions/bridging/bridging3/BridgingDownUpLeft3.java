package rabbitescape.engine.newstates.character_states.actions.bridging.bridging3;

import rabbitescape.engine.Block;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.newstates.character_states.actions.Bridging;
import rabbitescape.engine.newstates.character_states.actions.Bridging.*;
import rabbitescape.engine.newstates.character_states.actions.bridging.IBridgingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.Block.Material.EARTH;
import static rabbitescape.engine.Block.Shape.BRIDGE_UP_LEFT;
import static rabbitescape.engine.ChangeDescription.State.RABBIT_BRIDGING_DOWN_UP_LEFT_3;

public class BridgingDownUpLeft3 implements IBridgingState {

    @Override
    public State getState() {
        return RABBIT_BRIDGING_DOWN_UP_LEFT_3;
    }

    @Override
    public boolean behave(
        World world, Character character, Bridging bridging
    ) {
        System.out.println("\t\tRABBIT_BRIDGING_DOWN_UP_LEFT_3");
        character.onSlope = true;
        character.x--;
        world.changes.addBlock(
            new Block(
                character.x,
                character.y,
                EARTH,
                BRIDGE_UP_LEFT,
                0
            )
        );
        return true;
    }
}
