package rabbitescape.engine;

import java.util.Map;

import rabbitescape.engine.ChangeDescription.*;
import rabbitescape.engine.textworld.Chars;

public abstract class NewStates
{
    public abstract void calcNewState( World world );

    public abstract void step( World world );

    public abstract State setState();

    public abstract Map<String, String> saveState( boolean runtimeMeta );

    public abstract void restoreFromState( Map<String, String> state );

    public abstract String overlayText();

    public abstract void setChars( Change change, Chars chars );
}
