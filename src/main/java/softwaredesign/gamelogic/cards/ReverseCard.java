package softwaredesign.gamelogic.cards;

import javafx.scene.paint.Color;
import softwaredesign.gamelogic.commands.Command;

public class ReverseCard extends Card {
    protected ReverseCard(Command command) {
        super(command);
        cardRectangle.setFill(Color.FUCHSIA);
    }

    @Override
    public String toString() {
        return "Reverse";
    }
}
