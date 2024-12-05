package upei.project;

public class DrawTwoCard extends ActionCard {
    public DrawTwoCard(String color) {
        super(color);
    }

    @Override
    public String toString() {
        return color + " Draw Two";
    }
}