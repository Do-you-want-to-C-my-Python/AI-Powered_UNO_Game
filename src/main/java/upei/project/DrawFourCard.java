package upei.project;

public class DrawFourCard extends WildCard {
    public DrawFourCard() {
        super();
    }

    @Override
    public String toString() {
        if (color.equals("Wild")) {
            return "Wild Draw Four";
        }
        return "Wild Draw Four (" + color + ")";
    }
}