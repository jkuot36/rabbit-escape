package rabbitescape.engine.things.environment;

import rabbitescape.engine.World;
import rabbitescape.engine.things.Environment;

import static rabbitescape.engine.ChangeDescription.State.*;

import java.util.HashMap;
import java.util.Map;

public class Exit extends Environment {

    World world;

    public Exit(int x, int y) {
        super(x, y, EXIT);
    }

    @Override
    public void calcNewState(World world) {
    }

    @Override
    public void step(World world) {
        this.world = world;
    }

    @Override
    public Map<String, String> saveState(boolean runtimeMeta) {
        return new HashMap<String, String>();
    }

    @Override
    public void restoreFromState(Map<String, String> state) {
    }

    @Override
    public String overlayText() {
        return world == null ? "Exit" : "Exit\n" + world.num_saved + " saved";
    }
}
