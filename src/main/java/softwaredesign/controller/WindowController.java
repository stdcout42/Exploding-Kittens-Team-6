package softwaredesign.controller;

import javafx.fxml.Initializable;
import softwaredesign.model.gamelogic.Game;
import softwaredesign.view.ViewFactory;

/**
 * Subclasses of WindowController control the respective windows 
 */
public abstract class WindowController implements Initializable {
    protected Game game;
    protected ViewFactory viewFactory;
    private final String fxmlName;

    protected WindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        this.game = game;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
