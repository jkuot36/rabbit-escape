package rabbitescape.engine.newstates.character_states.actions.falling;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.newstates.character_states.actions.Falling;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.ChangeDescription.State.RABBIT_FALLING_ONTO_LOWER_RIGHT;

public class FallingOntoLowerRight implements IFallingState {

    @Override
    public State getState() {
        return RABBIT_FALLING_ONTO_LOWER_RIGHT;
    }

    @Override
    public boolean behave(
        World world, Character character, Falling falling
    ) {
        falling.heightFallen += 2;
        character.y = character.y + 2;
        return true;
    }
}
