package softwaredesign.model.cards;

import javafx.scene.paint.Color;
import softwaredesign.model.gamelogic.commands.Command;

public class SkipCard extends Card {

    protected SkipCard(Command command) {
        super(command);
        cardRectangle.setFill(Color.BLUEVIOLET);
    }

    @Override
    public String toString() {
        return "Skip";
    }
}
