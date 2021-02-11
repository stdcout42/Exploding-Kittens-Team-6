package softwaredesign;

public class Game {
    private int numberOfPlayers = 4;

    public void setNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers > 1 && numberOfPlayers < 6)
            this.numberOfPlayers = numberOfPlayers;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
