package upei.project;

public class ReverseCard extends ActionCard {
    public ReverseCard(String color) {
        super(color);
    }

    @Override
    public String toString() {
        return color + " Reverse";
    }
}