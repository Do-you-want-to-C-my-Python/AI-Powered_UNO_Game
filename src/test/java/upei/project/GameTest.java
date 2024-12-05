package upei.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void setUp() {
        List<Player> players;
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        game = new Game(players);
    }

    @Test
    void testInitialGameState() {
        assertEquals(0, game.getCurrentPlayerIndex());
        assertEquals("Player1", game.getCurrentPlayer().toString());
        assertFalse(game.isReversed());
        assertNotNull(game.getTopCard());
        assertInstanceOf(NumberCard.class, game.getTopCard());
    }

    @Test
    void testMoveToNextPlayer() {
        assertEquals("Player1", game.getCurrentPlayer().toString());
        game.moveToNextPlayer();
        assertEquals("Player2", game.getCurrentPlayer().toString());
        game.moveToNextPlayer();
        assertEquals("Player3", game.getCurrentPlayer().toString());
        game.moveToNextPlayer();
        assertEquals("Player1", game.getCurrentPlayer().toString());
    }

    @Test
    void testGetNextPlayer() {
        assertEquals("Player1", game.getCurrentPlayer().toString());
        assertEquals("Player2", game.getNextPlayer().toString());
        game.moveToNextPlayer();
        assertEquals("Player2", game.getCurrentPlayer().toString());
        assertEquals("Player3", game.getNextPlayer().toString());
    }

    @Test
    void testReverseDirection() {
        assertEquals("Player1", game.getCurrentPlayer().toString());
        game.reverseDirection();
        assertTrue(game.isReversed());
        game.moveToNextPlayer();
        assertEquals("Player3", game.getCurrentPlayer().toString());
        game.moveToNextPlayer();
        assertEquals("Player2", game.getCurrentPlayer().toString());
    }

    @Test
    void testSkipNextPlayer() {
        assertEquals("Player1", game.getCurrentPlayer().toString());
        game.setTopCard(new SkipCard(game.getCurrentColor()));
        assertEquals("Player3", game.getCurrentPlayer().toString());
    }

    @Test
    void testDrawTwo() {
        Player nextPlayer = game.getNextPlayer();
        int initialHandSize = nextPlayer.getHandSize();
        game.setTopCard(new DrawTwoCard(game.getCurrentColor()));
        assertEquals(initialHandSize + 2, nextPlayer.getHandSize());
        assertEquals("Player3", game.getCurrentPlayer().toString());
    }

    @Test
    void testDrawFour() {
        Player nextPlayer = game.getNextPlayer();
        int initialHandSize = nextPlayer.getHandSize();
        game.setTopCard(new DrawFourCard());
        assertEquals(initialHandSize + 4, nextPlayer.getHandSize());
        assertEquals("Player3", game.getCurrentPlayer().toString());
    }

    @Test
    void testWildCard() {
        game.setTopCard(new WildCard());
        assertNotNull(game.getCurrentColor());
        // Color should be changed by the wild card
        assertTrue(game.getCurrentColor().equals("Red") ||
                game.getCurrentColor().equals("Blue") ||
                game.getCurrentColor().equals("Green") ||
                game.getCurrentColor().equals("Yellow"));
    }

    @Test
    void testGameOver() {
        assertFalse(game.isGameOver());
        // Empty the deck
        while (!game.getDeck().isEmpty()) {
            game.getDeck().drawCard();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    void testPlayerWins() {
        Player currentPlayer = game.getCurrentPlayer();
        // Remove all cards from current player's hand
        while (currentPlayer.getHandSize() > 0) {
            currentPlayer.playCard(game.getTopCard());
        }
        assertTrue(game.isGameOver());
    }
}