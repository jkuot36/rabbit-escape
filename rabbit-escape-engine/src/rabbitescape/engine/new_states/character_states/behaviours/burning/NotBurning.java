package rabbitescape.engine.new_states.character_states.behaviours.burning;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.things.Character;
import rabbitescape.engine.World;

public class NotBurning implements IBurningState {

    @Override
    public State getState() {
        return null;
    }

    @Override
    public boolean behave(
        World world, Character character
    ) {
        return false;
    }
}
