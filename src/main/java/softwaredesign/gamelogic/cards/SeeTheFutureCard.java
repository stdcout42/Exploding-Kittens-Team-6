package softwaredesign.gamelogic.cards;

import javafx.scene.paint.Color;
import softwaredesign.gamelogic.commands.Command;

public class SeeTheFutureCard extends Card {

    protected SeeTheFutureCard(Command command) {
        super(command);
        cardRectangle.setFill(Color.PURPLE);
    }

    @Override
    public String toString() {
        return "See The Future";
    }
}
