package softwaredesign.model;

/**
 * Abstract player class,
 * potential subclasses:
 *   - HumanPlayer
 *   - Bot player
 *  Should contain: list of cards, ...
 */

abstract class Player {
    private int playerNumber;

    public abstract boolean addCard(Card card);
    public abstract boolean removeCard(Card card);
}
