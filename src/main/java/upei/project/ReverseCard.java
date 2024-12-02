package uno;

public class ReverseCard extends ActionCard {
    public ReverseCard(String color) {
        super(color, "Reverse");
    }

    @Override
    public void play(Game game) {
        game.setTopCard(this);
        game.reverseDirection();
    }
}

