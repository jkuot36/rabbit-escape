package rabbitescape.engine.new_states.item_states.dig;

import rabbitescape.engine.new_states.ItemState;

public abstract class DigItemState extends ItemState {

    @Override
    public ItemState newState() {
        ItemState newState;

        if (isOnSlope()) {
            newState = new DigOnSlope();
        } else if (!isMoving()) {
            newState = new DigStill();
        } else if (isSlopeBelow()) {
            newState = new DigFallToSlope();
        } else {
            newState = new DigFalling();
        }

        return newState;
    }

}
