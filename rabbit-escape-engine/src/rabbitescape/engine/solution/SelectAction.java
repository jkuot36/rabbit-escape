package rabbitescape.engine.solution;

import rabbitescape.engine.items.ItemType;

public class SelectAction implements CommandAction, TimeStepAction
{
    public final ItemType type;

    public SelectAction( ItemType type )
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "SelectAction( " + type.name() + " )";
    }

    @Override
    public boolean equals( Object otherObj )
    {
        if ( ! ( otherObj instanceof SelectAction ) )
        {
            return false;
        }
        SelectAction other = (SelectAction)otherObj;

        return ( type == other.type );
    }

    @Override
    public int hashCode()
    {
        return type.hashCode();
    }

    @Override
    public void typeSwitch( CommandActionTypeSwitch actionTypeSwitch )
    {
        actionTypeSwitch.caseSelectAction( this );
    }

    @Override
    public void typeSwitch( TimeStepActionTypeSwitch timeStepActionTypeSwitch )
    {
        timeStepActionTypeSwitch.caseSelectAction( this );
    }
}
