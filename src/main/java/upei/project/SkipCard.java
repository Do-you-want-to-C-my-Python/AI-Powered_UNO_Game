package upei.project;

public class SkipCard extends ActionCard {
    public SkipCard(String color) {
        super(color);
    }

    @Override
    public String toString() {
        return color + " Skip";
    }
}