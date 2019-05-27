package rabbitescape.engine.behaviours;

import static rabbitescape.engine.ChangeDescription.State.*;

import rabbitescape.engine.Behaviour;
import rabbitescape.engine.BehaviourTools;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.Character;
import rabbitescape.engine.Rabbit;
import rabbitescape.engine.World;

public class Burning extends Behaviour
{
    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        return world.fireAt( character.x, character.y );
    }

    @Override
    public State newState(
        BehaviourTools t, boolean triggered
        )
    {
        if ( triggered )
        {
            if ( t.character.onSlope )
            {
                return RABBIT_BURNING_ON_SLOPE;
            }
            else
            {
                return RABBIT_BURNING;
            }
        }

        return null;
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        switch ( state )
        {
        case RABBIT_BURNING:
        case RABBIT_BURNING_ON_SLOPE:
        {
            world.changes.killRabbit( character );
            return true;
        }
        default:
        {
            return false;
        }
        }
    }
}
