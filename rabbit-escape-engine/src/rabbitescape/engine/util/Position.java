package rabbitescape.engine.util;

public class Position implements Comparable<Position>
{
    public final int x;
    public final int y;

    public Position( int x, int y )
    {
        this.x = x;
        this.y = y;
    }

    public Position plus( Position p )
    {
        return new Position( this.x + p.x, this.y + p.y );
    }

    /**
     * Lexical comparison: y then x.
     */
    @Override
    public int compareTo( Position other )
    {
        if ( y < other.y )
        {
            return -1;
        }
        else if( y > other.y )
        {
            return 1;
        }
        else
        {
            if ( x < other.x )
            {
                return -1;
            }
            else if( x > other.x )
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

}
