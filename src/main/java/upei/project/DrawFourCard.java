package uno;

public class DrawFourCard extends Card {
    public DrawFourCard() {
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
        game.drawCards(4);
        game.skipNextPlayer();
    }

    @Override
    public String toString() {
        return "Wild Draw Four";
    }
}

