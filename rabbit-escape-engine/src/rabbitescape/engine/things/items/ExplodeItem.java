package rabbitescape.engine.things.items;

import rabbitescape.engine.ChangeDescription.State;
import rabbitescape.engine.World;
import rabbitescape.engine.things.Item;

public class ExplodeItem extends Item
{
    private static final ItemType type = ItemType.explode;

    public ExplodeItem( int x, int y )
    {
        super( x, y, State.TOKEN_EXPLODE_FALL_TO_SLOPE, type );
    }

    public ExplodeItem( int x, int y, World world )
    {
        super( x, y, State.TOKEN_EXPLODE_FALL_TO_SLOPE, type, world );
    }

    @Override
    public char getCharRepresentation()
    {
        return 'p';
    }

    @Override
    public State getStillState()
    {
        return State.TOKEN_EXPLODE_STILL;
    }

    @Override
    public State getFallingState()
    {
        return State.TOKEN_EXPLODE_FALLING;
    }

    @Override
    public State getFallToSlopState()
    {
        return State.TOKEN_EXPLODE_FALL_TO_SLOPE;
    }

    @Override
    public State getOnSlopeState()
    {
        return State.TOKEN_EXPLODE_ON_SLOPE;
    }

    @Override
    public Item copyWithoutState()
    {
        return new ExplodeItem( this.x, this.y );
    }
}