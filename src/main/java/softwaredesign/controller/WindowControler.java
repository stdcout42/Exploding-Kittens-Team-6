package softwaredesign.controller;

import javafx.fxml.Initializable;
import softwaredesign.Game;
import softwaredesign.view.ViewFactory;


public abstract class WindowControler implements Initializable {
    private Game game;
    private ViewFactory viewFactory;
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
