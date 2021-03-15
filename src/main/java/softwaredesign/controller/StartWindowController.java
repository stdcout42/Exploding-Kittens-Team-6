package softwaredesign.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import softwaredesign.Game;
import softwaredesign.view.ViewFactory;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class StartWindowController extends WindowControler{
    public StartWindowController(Game game, ViewFactory viewFactory, String fxmlName) {
        super(game, viewFactory, fxmlName);
    }

    @FXML
    private Label titleLabel;

    @FXML
    private ChoiceBox<Integer> optionsButton;


    @FXML
    private void playButtonAction() {
        if(optionsButton.getValue() == null){
            game.setNumberOfPlayers(1);
        }else {
            game.setNumberOfPlayers(optionsButton.getValue());
        }
        viewFactory.showGameWindow();
        Stage thisStage = (Stage) titleLabel.getScene().getWindow();
        viewFactory.closeStage(thisStage);
    }

//    @FXML
//    private void optionsButtonAction() {
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
