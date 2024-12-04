package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class PlayerStrategyTest {

    @Test
    public void testFirstAvailableCardStrategy() {
        Player player = new Player("P1");
        Game game = new Game(Arrays.asList(player, new Player("P2")));
        Card topCard = game.getTopCard();
        Card playedCard = player.playCard(topCard);
        assertNotNull(playedCard, "Player should play the first available card");
    }

    @Test
    public void testNumberCardFirstStrategy() {
        Player player = new Player("P1");
        Game game = new Game(Arrays.asList(player, new Player("P2")));
        Card topCard = game.getTopCard();
        Card playedCard = player.playNumberCardFirst(topCard);
        assertTrue(playedCard instanceof NumberCard || playedCard == null, "Player should play a number card if available");
    }

    @Test
    public void testActionCardFirstStrategy() {
        Player player = new Player("P1");
        Game game = new Game(Arrays.asList(player, new Player("P2")));
        Card topCard = game.getTopCard();
        Card playedCard = player.playActionCardFirst(topCard);
        assertTrue(playedCard instanceof ActionCard || playedCard == null, "Player should play an action card if available");
    }
}