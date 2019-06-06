package rabbitescape.engine.newstates.characterstates.actions;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.Direction.*;
import static rabbitescape.engine.things.items.ItemType.*;

import java.util.Map;

import rabbitescape.engine.*;
import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.newstates.CharacterStates;
import rabbitescape.engine.newstates.characterstates.CharacterActionStates;
import rabbitescape.engine.newstates.characterstates.actions.climbing.IClimbingState;
import rabbitescape.engine.newstates.characterstates.actions.climbing.NotClimbing;
import rabbitescape.engine.things.Character;

public class Climbing extends CharacterActionStates
{
    private IClimbingState climbingState;
    boolean hasAbility = false;
    public boolean abilityActive = false;

    public Climbing()
    {
        setClimbingState( new NotClimbing() );
    }

    public void setClimbingState( IClimbingState climbingState )
    {
        this.climbingState = climbingState;
    }

    public void setClimbingState(
        IClimbingState right,
        IClimbingState left,
        Character character
    )
    {
        if ( character.dir == RIGHT )
        {
            setClimbingState( right );
        }
        else
        {
            setClimbingState( left );
        }
    }

    @Override
    public void cancel()
    {
        abilityActive = false;
    }

    @Override
    public boolean checkTriggered( Character character, World world )
    {
        BehaviourTools t = new BehaviourTools( character, world );

        return !hasAbility && t.pickUpToken( climb, true );
    }

    @Override
    public State newState( BehaviourTools t, boolean triggered )
    {
        if ( triggered )
        {
            hasAbility = true;
        }

        if ( !hasAbility )
        {
            setClimbingState( new NotClimbing() );
        }

        return climbingState.newState( t, abilityActive );
    }

    @Override
    public boolean behave( World world, Character character, State state )
    {
        BehaviourTools t = new BehaviourTools( character, world );

        if( t.rabbitIsClimbing() )
        { // Can't be both on a wall and on a slope.
            character.onSlope = false;
        }

        switch ( state )
        {
            case RABBIT_CLIMBING_RIGHT_START:
            case RABBIT_CLIMBING_LEFT_START:
            {
                abilityActive = true;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_END:
            case RABBIT_CLIMBING_LEFT_END:
            {
                character.x = t.nextX();
                --character.y;
                if ( t.hereIsUpSlope() )
                {
                    character.onSlope = true;
                }
                abilityActive = false;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_CONTINUE_1:
            case RABBIT_CLIMBING_LEFT_CONTINUE_1:
            {
                abilityActive = true;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_CONTINUE_2:
            case RABBIT_CLIMBING_LEFT_CONTINUE_2:
            {
                abilityActive = true;
                --character.y;
                return true;
            }
            case RABBIT_CLIMBING_RIGHT_BANG_HEAD:
            case RABBIT_CLIMBING_LEFT_BANG_HEAD:
            {
                character.dir = opposite( character.dir );
                return true;
            }
            default:
            {
                return false;
            }
        }
    }

    @Override
    public void saveState( Map<String, String> saveState )
    {
        BehaviourState.addToStateIfTrue(
            saveState, "Climbing.hasAbility", hasAbility
        );

        BehaviourState.addToStateIfTrue(
            saveState, "Climbing.abilityActive", abilityActive
        );
    }

    @Override
    public void restoreFromState( Map<String, String> saveState )
    {
        hasAbility = BehaviourState.restoreFromState(
            saveState, "Climbing.hasAbility", hasAbility
        );

        abilityActive = BehaviourState.restoreFromState(
            saveState, "Climbing.abilityActive", abilityActive
        );
    }

    @Override
    public State getState()
    {
        return null;
    }
}
