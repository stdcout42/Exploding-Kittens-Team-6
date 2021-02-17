package softwaredesign.controller;

import javafx.fxml.Initializable;
import softwaredesign.Game;
import softwaredesign.view.ViewFactory;

/**
 * Subclasses of WindowController control the respective windows 
 */
public abstract class WindowControler implements Initializable {
    protected Game game;
    protected ViewFactory viewFactory;
    private String fxmlName;

    public WindowControler(Game game, ViewFactory viewFactory, String fxmlName) {
        this.game = game;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }

    public String getFxmlName() {
        return fxmlName;
    }
}
