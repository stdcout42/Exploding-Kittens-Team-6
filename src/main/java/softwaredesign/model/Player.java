package softwaredesign.model;

/**
 * Abstract player class,
 * potential subclasses:
 *   - HumanPlayer
 *   - Bot player
 *  Should contain: list of cards, ...
 */

public abstract class Player {

    private int playerNumber;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public abstract void addCard(Card card);
    public abstract void removeCard(Card card);
    public int getPlayerNumber() {
        return playerNumber;
    }

    public abstract boolean hasDefuseCard();

    @Override
    public String toString() {
        return "Player " + playerNumber;
    }

}
