package rabbitescape.engine.new_states.character_states.actions.walking.walkingonupslope;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.new_states.character_states.actions.walking.IWalkingState;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_RISING_AND_LOWERING_LEFT;

public class RisingAndLoweringLeft implements IWalkingState {

    @Override
    public State getState() {
        return RABBIT_RISING_AND_LOWERING_LEFT;
    }

    @Override
    public boolean behave(
        World world, Character character
    ) {
        --character.x;
        character.onSlope = true;
        return true;
    }
}
