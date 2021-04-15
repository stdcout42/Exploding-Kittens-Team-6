package softwaredesign.model.cards;

public enum CardType {
    SKIP, SEE_THE_FUTURE, REVERSE, SHUFFLE, STEAL, DEFUSE, EXPLODING_KITTEN;

    public static int getNumOfCards(CardType cardType) {
        switch (cardType) {
            case SKIP:
                return 6;
            case SEE_THE_FUTURE:
                return 6;
            case REVERSE:
                return 6;
            case SHUFFLE:
                return 6;
            case STEAL:
                return 6;
            case DEFUSE:
                return 6;
            case EXPLODING_KITTEN:
                return 4;
            default:
                throw new IllegalStateException("Unexpected value: " + cardType);
        }
    }
}
