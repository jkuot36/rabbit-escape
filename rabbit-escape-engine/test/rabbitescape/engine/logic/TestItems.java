package rabbitescape.engine.logic;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import static rabbitescape.engine.ChangeDescription.State.*;
import static rabbitescape.engine.textworld.TextWorldManip.*;
import static rabbitescape.engine.util.WorldAssertions.*;

import org.junit.Test;

import rabbitescape.engine.things.items.BashItem;
import rabbitescape.engine.things.items.BrollyItem;
import rabbitescape.engine.things.Item;
import rabbitescape.engine.World;
import rabbitescape.engine.things.items.ItemType;

public class TestItems {
    // TODO: slopes and bridges

    @Test
    public void Items_return_their_state_names_lowercase() {
        Item t = new BashItem(1, 2);
        t.state = TOKEN_BASH_FALLING;
        assertThat(t.stateName(), equalTo("token_bash_falling"));
    }

    @Test
    public void Items_fall_slowly_and_stop_on_ground() {
        assertWorldEvolvesLike(
            "bdikcp" + "\n" +
                "      " + "\n" +
                "      " + "\n" +
                "      " + "\n" +
                "######",

            "      " + "\n" +
                "bdikcp" + "\n" +
                "ffffff" + "\n" +
                "      " + "\n" +
                "######",

            "      " + "\n" +
                "      " + "\n" +
                "bdikcp" + "\n" +
                "ffffff" + "\n" +
                "######",

            "      " + "\n" +
                "      " + "\n" +
                "      " + "\n" +
                "bdikcp" + "\n" +
                "######",

            "      " + "\n" +
                "      " + "\n" +
                "      " + "\n" +
                "bdikcp" + "\n" +
                "######"
        );
    }

    @Test
    public void Items_disappear_when_they_drop_outside_world() {
        World world = createWorld(
            "bdikc",
            "     "
        );

        // Sanity - we have 5 things
        assertThat(world.things.size(), equalTo(5));

        world.step();

        // Still 5 things, not off bottom yet
        assertThat(world.things.size(), equalTo(5));

        world.step();

        // Now off bottom - all gone
        assertThat(world.things.size(), equalTo(0));
    }

    @Test
    public void Can_add_items_on_empty_and_sloping_blocks() {
        World world = createWorld(
            "\\) (/",  // 2 slopes, 2 bridges
            "#####",
            ":dig=5"
        );

        // Sanity - no items yet
        assertThat(world.things.size(), equalTo(0));
        assertThat(world.abilities.get(ItemType.dig), equalTo(5));

        // This is what we are testing: add items on slopes, bridges, space
        world.changes.addItem(0, 0, ItemType.dig);
        world.changes.addItem(1, 0, ItemType.dig);
        world.changes.addItem(2, 0, ItemType.dig);
        world.changes.addItem(3, 0, ItemType.dig);
        world.changes.addItem(4, 0, ItemType.dig);
        world.step();

        // All 4 items were added
        assertThat(world.things.size(), equalTo(5));
        assertThat(world.abilities.get(ItemType.dig), equalTo(0));
    }

    @Test
    public void Cant_add_items_on_solid_blocks() {
        World world = createWorld(
            "\\) (/",  // 2 slopes, 2 bridges
            "#####",
            ":dig=5"
        );

        // Sanity - no items yet
        assertThat(world.things.size(), equalTo(0));
        assertThat(world.abilities.get(ItemType.dig), equalTo(5));

        // This is what we are testing: add items on solid blocks
        world.changes.addItem(0, 1, ItemType.dig);
        world.changes.addItem(1, 1, ItemType.dig);
        world.changes.addItem(2, 1, ItemType.dig);
        world.changes.addItem(3, 1, ItemType.dig);
        world.changes.addItem(4, 1, ItemType.dig);
        world.step();

        // None of them were were added
        assertThat(world.things.size(), equalTo(0));
        assertThat(world.abilities.get(ItemType.dig), equalTo(5));
    }

    @Test
    public void Items_do_not_fall_through_half_built_bridges_from_down_slope() {
        assertWorldEvolvesLike(
            "r d" + "\n" +
                "#* " + "\n" +
                ":*=i\\",      // Bridging token on down slope

            "   " + "\n" +
                "#rB",         // Dig token hits bridge

            "   " + "\n" +
                "#r[",

            "   " + "\n" +
                "#r{",

            "   " + "\n" +
                "#\\D"         // Starts digging
        );
    }

    @Test
    public void Items_do_not_fall_through_half_built_bridges_from_flat() {
        assertWorldEvolvesLike(
            "  d" + "\n" +
                "ri " + "\n" +
                "## ",         // Bridging token on flat

            "   " + "\n" +
                " rB" + "\n" +
                "## ",         // Dig token hits bridge

            "   " + "\n" +
                " r[" + "\n" +
                "## ",

            "   " + "\n" +
                " r{" + "\n" +
                "## ",

            "   " + "\n" +
                "  D" + "\n" +
                "## "          // Starts digging
        );
    }

    @Test
    public void Items_do_not_fall_through_half_built_bridges_from_up_slope() {
        assertWorldEvolvesLike(
            "  d" + "\n" +
                "   " + "\n" +
                "r* " + "\n" +
                "## " + "\n" +
                ":*=i/",       // Bridging token on up slope

            "   " + "\n" +
                "  B" + "\n" +
                " r " + "\n" +
                "## ",         // Dig token hits bridge

            "   " + "\n" +
                "  [" + "\n" +
                " r " + "\n" +
                "## ",

            "   " + "\n" +
                "  {" + "\n" +
                " r " + "\n" +
                "## ",

            "   " + "\n" +
                "  D" + "\n" +  // Starts digging
                " / " + "\n" +
                "## "
        );
    }

    @Test
    public void Items_do_not_fall_through_half_built_bridges_in_tight_corners() {
        assertWorldEvolvesLike(
            " d " + "\n" +
                "#r#" + "\n" +
                "#*#" + "\n" +
                "###" + "\n" +
                ":*=i/",       // Bridging token on up slope in hole

            "   " + "\n" +
                "#B#" + "\n" + // Dig token hits bridge
                "#r#" + "\n" +
                "###",

            "   " + "\n" +
                "#[#" + "\n" +
                "#r#" + "\n" +
                "###",

            "   " + "\n" +
                "#{#" + "\n" +
                "#r#" + "\n" +
                "###",

            "   " + "\n" +
                "#D#" + "\n" +  // Starts digging
                "#/#" + "\n" +
                "###"
        );
    }

    @Test
    public void Items_falling_onto_bridgers_in_corner_take_effect() {
        // This looks like the character catches it when it's off to the side,
        // because really the character is stuck in a hole, so it's not too
        // bad, but inconsistent with
        // Items_do_not_fall_through_half_built_bridges_in_tight_corners

        assertWorldEvolvesLike(
            " d#" + "\n" +
                "  #" + "\n" +
                "ri#" + "\n" +
                "###",         // Bridging token next to wall

            "  #" + "\n" +
                " d#" + "\n" +
                " f#" + "\n" +
                "###",

            "  #" + "\n" +
                "  #" + "\n" +
                " r#" + "\n" + // Dig token hits bridge and converts character
                "#D#"
        );
    }

    @Test
    public void Rabbits_falling_to_death_do_not_consume_items() {
        World world = createWorld(
            " r       ",
            "  j      ",
            "   r     ",
            "    j    ",
            "     r   ",
            "      j  ",
            "       r ",
            "         ",
            "         ",
            "         ",
            "         ",
            "#bdikcp*#",
            "####)(*/#",
            "#########",
            ":*=db",
            ":*=\\"
        );

        assertWorldEvolvesLike(
            world,
            8,
            new String[]{
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "         ",
                "#bdi    #",
                "####****#",
                "#########",
                ":*=)k",
                ":*=(c",
                ":*=\\p",
                ":*=/db"
            });
    }

    @Test
    public void Rabbits_falling_and_living_do_consume_items() {
        World world = createWorld(
            " r       ",
            "   r   r ",
            "     r   ",
            "#bpbpcpdp",
            "####)(\\/#",
            "#########"
        );

        assertWorldEvolvesLike(
            world,
            8,
            new String[]{
                "         ",
                "         ",
                "         ",
                "#       p",
                "####)(\\ #",
                "####### #"
            });
    }

    @Test
    public void Items_start_off_in_non_falling_states() {
        // See https://github.com/andybalaam/rabbit-escape/issues/447

        World world = createWorld(
            "  ",
            " /",
            "##"
        );

        Item inAir = new BrollyItem(0, 0, world);
        Item onSlope = new BrollyItem(1, 1, world);

        // Until a time step passes, these are in non-moving characterStates
        assertThat(inAir.state, is(TOKEN_BROLLY_STILL));
        assertThat(onSlope.state, is(TOKEN_BROLLY_ON_SLOPE));
    }
}
