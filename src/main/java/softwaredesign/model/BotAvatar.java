package softwaredesign.model;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class BotAvatar extends Parent {
    private final int botNumber;
    private Rectangle botRectangle;
    private int numOfCards;
    private Text numberOfCardsText;

    public BotAvatar(int botNumber) {
        this.botNumber = botNumber;
        this.numOfCards = 0;
        botRectangle = new Rectangle(80, 100);
        botRectangle.setArcWidth(20);
        botRectangle.setArcHeight(20);
        botRectangle.setFill(Color.ORANGE);

        Text playerName = new Text(toString());
        playerName.setWrappingWidth(70);
        numberOfCardsText = new Text("Cards: " + numOfCards);
        getChildren().add(new VBox(new StackPane(botRectangle, playerName), numberOfCardsText));
    }

    @Override
    public String toString() {
        return "Player " + this.botNumber;
    }

    public void incNumOfCards() {
        numOfCards++;
        numberOfCardsText.setText("Cards: " + numOfCards);
    }
    public void decNumOfCards(){
        numOfCards--;
        numberOfCardsText.setText("Cards: " + numOfCards);
    }
}
