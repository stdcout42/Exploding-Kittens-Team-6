package softwaredesign.gamelogic.cards;

import javafx.scene.paint.Color;
import softwaredesign.gamelogic.commands.Command;

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
