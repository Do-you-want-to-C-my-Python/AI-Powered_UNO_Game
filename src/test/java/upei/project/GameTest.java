package upei.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

public class GameTest {

    @Test
    public void testInitialDeckSize() {
        Game game = new Game(Arrays.asList(new Player("P1"), new Player("P2"), new Player("P3"), new Player("P4")));
        assertEquals(108, game.getDeck().getCards().size(), "Initial deck should have 108 cards");
    }

    @Test
    public void testInitialPlayerHandSize() {
        Game game = new Game(Arrays.asList(new Player("P1"), new Player("P2"), new Player("P3"), new Player("P4")));
        for (Player player : game.getPlayers()) {
            assertEquals(7, player.getHandSize(), "Each player should start with 7 cards");
        }
    }

    @Test
    public void testPlayCard() {
        Game game = new Game(Arrays.asList(new Player("P1"), new Player("P2")));
        Player currentPlayer = game.getCurrentPlayer();
        Card topCard = game.getTopCard();
        Card playedCard = currentPlayer.playCard(topCard);
        assertNotNull(playedCard, "Player should be able to play a card");
        assertEquals(playedCard, game.getTopCard(), "Played card should become the new top card");
    }

    @Test
    public void testDrawCard() {
        Game game = new Game(Arrays.asList(new Player("P1"), new Player("P2")));
        Player currentPlayer = game.getCurrentPlayer();
        int initialHandSize = currentPlayer.getHandSize();
        game.drawCards(1);
        assertEquals(initialHandSize + 1, currentPlayer.getHandSize(), "Player's hand should increase by 1 after drawing");
    }

    @Test
    public void testGameOver() {
        Game game = new Game(Arrays.asList(new Player("P1"), new Player("P2")));
        while (!game.isGameOver()) {
            game.play();
        }
        assertTrue(game.isGameOver(), "Game should be over when a player has no cards or deck is empty");
    }
}