package rabbitescape.engine.new_states.character_states.actions.bridging.bridging1;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.new_states.character_states.actions.Bridging;
import rabbitescape.engine.new_states.character_states.actions.Bridging.BridgeType;
import rabbitescape.engine.new_states.character_states.actions.bridging.IBridgingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_BRIDGING_IN_CORNER_UP_LEFT_1;

public class BridgingInCornerUpLeft1 implements IBridgingState {

    @Override
    public State getState() {
        return RABBIT_BRIDGING_IN_CORNER_UP_LEFT_1;
    }

    @Override
    public boolean behave(World world, Character character, Bridging bridging) {
        bridging.bridgeType = BridgeType.ALONG;
        character.onSlope = true;
        return true;
    }
}
