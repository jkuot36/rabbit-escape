package rabbitescape.engine;

import static rabbitescape.engine.ChangeDescription.State.*;

public class Rabbit extends Character
{
    String name = "character";

    public Rabbit(int x,int y,Direction dir)
    {
        super( x, y, RABBIT_WALKING_LEFT );
        this.dir = dir;
    }

    @Override
    int getFatalHeight() { return 4; }

    @Override
    public String stateName()
    {
        String normalName = super.stateName();
        return normalName;
    }
}