package softwaredesign.gamelogic.cards;

import javafx.scene.paint.Color;
import softwaredesign.gamelogic.commands.Command;

public class ShuffleCard extends Card {
    protected ShuffleCard(Command command) {
        super(command);
        cardRectangle.setFill(Color.CYAN);
    }

    @Override
    public String toString() {
        return "Shuffle";
    }
}
