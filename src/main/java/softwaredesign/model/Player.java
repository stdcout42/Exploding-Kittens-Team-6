package softwaredesign.model;

public abstract class Player {

    private final int playerNumber;
    private boolean isExploding;

    protected Player(int playerNumber) {
        this.playerNumber = playerNumber;
        this.isExploding = false;
    }

    public abstract void addCard(Card card);
    public abstract void removeCard(Card card);
    public abstract boolean hasDefuseCard();
    public abstract Card extractRandomCard();
    public abstract Card extractDefuseCard();
    public abstract boolean hasCards();

    public boolean getIsExploding() {return isExploding; }
    public void setIsExploding(boolean hasExplodingKitten) {
        this.isExploding = hasExplodingKitten;
    }

    @Override
    public String toString() {
        return "Player " + playerNumber;
    }

}
