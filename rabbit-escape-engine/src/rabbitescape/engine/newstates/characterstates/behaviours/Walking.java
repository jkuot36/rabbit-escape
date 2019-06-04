package rabbitescape.engine.newstates.characterstates.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.newstates.CharacterStates;
import rabbitescape.engine.newstates.characterstates.behaviours.walking.*;
import rabbitescape.engine.things.Character;

public class Walking extends CharacterStates
{
    private IWalkingState walkingState, rightState, leftState;

    public void setWalkingState( IWalkingState walkingState )
    {
        this.walkingState = walkingState;
    }

    @Override
    public void cancel()
    {
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        return false; // To avoid cancelling other actions, return false
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        //TODO: Another pattern here?
        if ( t.isOnUpSlope() )
        {
            setWalkingState( new WalkingOnUpSlope().newState( t, triggered ) );
        }
        else if ( t.isOnDownSlope() )
        {
            setWalkingState( new WalkingOnDownSlope().newState( t, triggered ) );
        }
        else  // On flat ground now
        {
            setWalkingState( new WalkingOnFlat().newState( t, triggered ) );
        }

        return walkingState.newState();
    }

    @Override
    @SuppressWarnings("fallthrough")
    public boolean behave( World world, Character character, State state )
    {
        /*
        default:
        {
            throw new AssertionError(
                "Should have handled all characterStates in Walking or before,"
                + " but we are in walkingState " + state.name()
            );
        }
        */

        //TODO: Must deal with duplicate code of checkJumpOntoSlope() and isBridge()
        return walkingState.behave( world, character );
    }

    @Override
    public String toString()
    {
        return "Walking";
    }
}
