package softwaredesign.model.cards;

import javafx.scene.paint.Color;
import softwaredesign.model.gamelogic.commands.Command;

public class DefuseCard extends Card {

    protected DefuseCard(Command command) {
        super(command);
        cardRectangle.setFill(Color.GREENYELLOW);
    }

    @Override
    public String toString() {
        return "Defuse";
    }
}
