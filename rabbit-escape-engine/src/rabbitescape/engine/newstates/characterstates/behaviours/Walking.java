package rabbitescape.engine.newstates.characterstates.behaviours;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.newstates.characterstates.CharacterBehaviourStates;
import rabbitescape.engine.newstates.characterstates.actions.Blocking;
import rabbitescape.engine.newstates.characterstates.behaviours.walking.*;
import rabbitescape.engine.newstates.characterstates.behaviours.walking.walkingonflat.WalkingLeft;
import rabbitescape.engine.newstates.characterstates.behaviours.walking.walkingonflat.WalkingRight;
import rabbitescape.engine.things.Character;

import static rabbitescape.engine.Block.Shape.BRIDGE_UP_LEFT;
import static rabbitescape.engine.Block.Shape.BRIDGE_UP_RIGHT;
import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.LEFT;
import static rabbitescape.engine.Direction.RIGHT;

public class Walking extends CharacterBehaviourStates
{
    private IWalkingState walkingState;

    public Walking()
    {
        setWalkingState( new WalkingRight() );
    }

    public void setWalkingState( IWalkingState walkingState )
    {
        this.walkingState = walkingState;
    }

    @Override
    public State getState()
    {
        return null;
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
        if ( t.isOnUpSlope() )
        {
            Block aboveNext = t.blockAboveNext();
            Block above = t.blockAbove();
            int nextX = t.nextX();
            int nextY = t.character.y - 1;

            if
            (
                t.isWall( aboveNext )
                    || Blocking.blockerAt( t.world, nextX, nextY )
                    || t.isRoof( above )
                    || ( t.isCresting() &&
                             Blocking.blockerAt( t.world, nextX, t.character.y ) )
            )
            {
                return t.rl(
                    RABBIT_TURNING_RIGHT_TO_LEFT_RISING,
                    RABBIT_TURNING_LEFT_TO_RIGHT_RISING
                );
            }
            else if ( t.isUpSlope( aboveNext ) )
            {
                return t.rl(
                    RABBIT_RISING_RIGHT_CONTINUE,
                    RABBIT_RISING_LEFT_CONTINUE
                );
            }
            else if ( t.isDownSlope( t.blockNext() ) )
            {
                return t.rl(
                    RABBIT_RISING_AND_LOWERING_RIGHT,
                    RABBIT_RISING_AND_LOWERING_LEFT
                );
            }
            else
            {
                return t.rl(
                    RABBIT_RISING_RIGHT_END,
                    RABBIT_RISING_LEFT_END
                );
            }
        }
        else if ( t.isOnDownSlope() )
        {
            int nextX = t.nextX();
            int nextY = t.character.y + 1;
            Block next = t.blockNext();
            Block belowNext = t.blockBelowNext();

            if (
                t.isWall( next )
                    || Blocking.blockerAt( t.world, nextX, nextY )
                    || ( t.isValleying() &&
                             Blocking.blockerAt( t.world, nextX, t.character.y ) )
            )
            {
                return t.rl(
                    RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING,
                    RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING
                );
            }
            else if ( t.isUpSlope( next ) )
            {
                return t.rl(
                    RABBIT_LOWERING_AND_RISING_RIGHT,
                    RABBIT_LOWERING_AND_RISING_LEFT
                );
            }
            else if ( t.isDownSlope( belowNext ) )
            {
                return t.rl(
                    RABBIT_LOWERING_RIGHT_CONTINUE,
                    RABBIT_LOWERING_LEFT_CONTINUE
                );
            }
            else
            {
                if ( Blocking.blockerAt( t.world, nextX, t.character.y ) )
                {
                    return t.rl(
                        RABBIT_TURNING_RIGHT_TO_LEFT_LOWERING,
                        RABBIT_TURNING_LEFT_TO_RIGHT_LOWERING
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_LOWERING_RIGHT_END,
                        RABBIT_LOWERING_LEFT_END
                    );
                }
            }
        }
        else  // On flat ground now
        {
            int nextX = t.nextX();
            int nextY = t.character.y;
            Block next = t.blockNext();

            if
            (
                t.isWall( next )
                    || Blocking.blockerAt( t.world, nextX, nextY )
            )
            {
                return t.rl(
                    RABBIT_TURNING_RIGHT_TO_LEFT,
                    RABBIT_TURNING_LEFT_TO_RIGHT
                );
            }
            else if ( t.isUpSlope( next ) )
            {
                return t.rl(
                    RABBIT_RISING_RIGHT_START,
                    RABBIT_RISING_LEFT_START
                );
            }
            else if ( t.isDownSlope( t.blockBelowNext() ) )
            {
                if ( Blocking.blockerAt( t.world, nextX, t.character.y + 1 ) )
                {
                    return t.rl(
                        RABBIT_TURNING_RIGHT_TO_LEFT,
                        RABBIT_TURNING_LEFT_TO_RIGHT
                    );
                }
                else
                {
                    return t.rl(
                        RABBIT_LOWERING_RIGHT_START,
                        RABBIT_LOWERING_LEFT_START
                    );
                }
            }
            else
            {
                return t.rl(
                    RABBIT_WALKING_RIGHT,
                    RABBIT_WALKING_LEFT
                );
            }
        }
    }

    @Override
    @SuppressWarnings("fallthrough")
    public boolean behave( World world, Character character, State state )
    {
        /*
        default:
        {
            throw new AssertionError(
                "Should have handled all states in Walking or before,"
                    + " but we are in state " + state.name()
            );
        }
         */

        return walkingState.behave( world, character );
    }

    @Override
    public boolean behave(
        World world, Character character, State state, NewStates newState
    )
    {
        return behave( world, character, state );
    }
}
