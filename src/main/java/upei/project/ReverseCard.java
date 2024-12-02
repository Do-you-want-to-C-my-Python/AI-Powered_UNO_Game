package upei.project;


public class ReverseCard extends upei.project.ActionCard {
    public ReverseCard(String color) {
        super(color, "Reverse");
    }

    @Override
    public void play(upei.project.Game game) {
        game.setTopCard(this);
        game.reverseDirection();
    }
}

