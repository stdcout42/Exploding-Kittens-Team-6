package softwaredesign;

/**
 * Game class will contain:
 *  - options such as number of players
 *  - deck of cards
 *  - turn of player
 *  - [timer]
 *  ...
 */
public class Game {
    private int numberOfPlayers = 5;

    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers > 1 && numberOfPlayers < 6)
            this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
