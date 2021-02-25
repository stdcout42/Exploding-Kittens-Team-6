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
    private boolean hasExplodingKitten;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.hasExplodingKitten = false;
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

    public boolean isExploding() {
        return hasExplodingKitten;
    }

    public void setHasExplodingKitten(boolean hasExplodingKitten) {
        this.hasExplodingKitten = hasExplodingKitten;
    }

    public abstract Card extractDefuseCard();
}
