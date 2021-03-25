package softwaredesign.gamelogic.cards;

import javafx.scene.paint.Color;
import softwaredesign.gamelogic.commands.Command;

public class StealCard extends Card {
    public StealCard(Command command) {
        super(command);
        cardRectangle.setFill(Color.CORAL);
    }

    @Override
    public String toString() {
        return "Steal";
    }
}
