package rabbitescape.engine.new_states.character_states.actions.falling;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.new_states.character_states.actions.Falling;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT_2;

public class DyingOfFallingSlopeRiseLeft2 implements IFallingState {

    @Override
    public State getState() {
        return RABBIT_DYING_OF_FALLING_SLOPE_RISE_LEFT_2;
    }

    @Override
    public boolean behave(
        World world, Character character, Falling falling
    ) {
        world.changes.killRabbit(character);
        return true;
    }
}
