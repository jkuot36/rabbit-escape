package rabbitescape.engine.new_states.item_states.brolly;

import rabbitescape.engine.new_states.ItemState;

public abstract class BrollyItemState extends ItemState {

    @Override
    public ItemState newState() {
        ItemState newState;

        if (isOnSlope()) {
            newState = new BrollyOnSlope();
        } else if (!isMoving()) {
            newState = new BrollyStill();
        } else if (isSlopeBelow()) {
            newState = new BrollyFallToSlope();
        } else {
            newState = new BrollyFalling();
        }

        return newState;
    }

}
