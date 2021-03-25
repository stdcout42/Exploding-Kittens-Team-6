package softwaredesign.model.cards;

import softwaredesign.gamelogic.Game;
import softwaredesign.gamelogic.SkipCommand;

public class SkipCard extends AbsCard {

    protected SkipCard(Game game) {
        super();
        command = new SkipCommand(game);
    }

    @Override
    public String toString() {
        return "SKIP";
    }
}
