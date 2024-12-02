package uno;

public class WildCard extends Card {
    public WildCard() {
        super("Wild");
    }

    @Override
    public boolean canPlayOn(Card topCard) {
        return true;
    }

    @Override
    public void play(Game game) {
        String newColor = game.getCurrentPlayer().chooseColor();
        game.setTopCard(this);
        game.setCurrentColor(newColor);
    }

    @Override
    public String toString() {
        return "Wild";
    }
}

