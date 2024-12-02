package uno;

public class DrawTwoCard extends ActionCard {
    public DrawTwoCard(String color) {
        super(color, "Draw Two");
    }

    @Override
    public void play(Game game) {
        game.setTopCard(this);
        game.drawCards(2);
        game.skipNextPlayer();
    }
}

