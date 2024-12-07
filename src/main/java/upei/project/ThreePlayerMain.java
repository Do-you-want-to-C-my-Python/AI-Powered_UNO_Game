package upei.project;

import java.util.*;

public class ThreePlayerMain {
    private static final String[] STRATEGY_NAMES = {
            "First Available Card Strategy",
            "Number Card First Strategy",
            "Action Card First Strategy"
    };

    public static void main(String[] args) {
        int turnCount = 0;
        System.out.println("=== UNO Three Player Game ===\n");

        // Create players with their respective strategies
        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Player 1 (" + STRATEGY_NAMES[0] + ")");
        Player player2 = new Player("Player 2 (" + STRATEGY_NAMES[1] + ")");
        Player player3 = new Player("Player 3 (" + STRATEGY_NAMES[2] + ")");
        players.add(player1);
        players.add(player2);
        players.add(player3);

        System.out.println("Players:");
        System.out.println("1. " + player1);
        System.out.println("2. " + player2);
        System.out.println("3. " + player3+"\n");

        // Create and play the game
        Game game = new Game(players);

        while (!game.isGameOver() && turnCount < 1000) {
            turnCount++;
            System.out.println("\n--- Turn " + turnCount + " ---");
            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\nCurrent player: " + currentPlayer);
            displayGameState(game);

            // Display all players' hands
            displayPlayerHands(players);
            System.out.println("Cards left in deck: " + game.getDeck().getSize()+"\n");

            TurnResult result = playTurn(game, currentPlayer);
            Card playedCard = result.playedCard;
            Card drawnCard = result.drawnCard;

            if (playedCard != null) {
                if (playedCard != drawnCard) {
                    System.out.println(currentPlayer + " plays " + playedCard);
                }
                game.setTopCard(playedCard);
                if (currentPlayer.getHandSize() == 1) {
                    currentPlayer.callUno();
                    System.out.println(currentPlayer + " calls UNO!");
                } else if (currentPlayer.getHandSize() == 0) {
                    System.out.println("\n" + currentPlayer + " wins!");
                    return;
                }
            }

            displayHandSizes(players);
            game.checkForMissedUnoCall(currentPlayer);
            currentPlayer.resetUnoCall();
        }

        if (turnCount >= 1000) {
            System.out.println("\nGame forcefully ended after 1000 turns to prevent infinite loop.");
        }
        System.out.println("\nGame over after " + turnCount + " turns.");
        if (game.getDeck().isEmpty()) {
            System.out.println("Deck is empty.");
        }
        Player winner = game.getWinner();
        System.out.println("\nFinal hand sizes:");
        displayHandSizes(players);
        System.out.println("\nWinner: " + winner + " with " + winner.getHandSize() + " cards");
    }

    private static class TurnResult {
        Card playedCard;
        Card drawnCard;

        TurnResult(Card playedCard, Card drawnCard) {
            this.playedCard = playedCard;
            this.drawnCard = drawnCard;
        }
    }

    private static void displayPlayerHand(Player player) {
        List<Card> hand = player.getHand();
        System.out.println(player + " (" + hand.size() + " cards): " +
                hand.stream()
                        .map(Card::toString)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Empty hand"));
    }

    private static void displayHandSizes(List<Player> players) {
        System.out.print("\nHand sizes - ");
        for (int i = 0; i < players.size(); i++) {
            System.out.print("P" + (i + 1) + ": " + players.get(i).getHandSize());
            if (i < players.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    private static void displayPlayerHands(List<Player> players) {
        System.out.println("\nCurrent hands:");
        for (Player player : players) {
            displayPlayerHand(player);
        }
        System.out.println();
    }

    private static TurnResult playTurn(Game game, Player currentPlayer) {
        Card playedCard;
        Card drawnCard = null;

        if (game.getDrawTwoCount() > 0) {
            playedCard = currentPlayer.playDrawTwo(game.getTopCard());
            if (playedCard == null) {
                System.out.println(currentPlayer + " draws " + (2 * game.getDrawTwoCount()) + " cards");
                System.out.println(currentPlayer + " turn skipped");
                game.drawCards(2 * game.getDrawTwoCount());
                return new TurnResult(null, null);
            }
        } else if (game.getDrawFourCount() > 0) {
            playedCard = currentPlayer.playDrawFour();
            if (playedCard == null) {
                System.out.println(currentPlayer + " draws " + (4 * game.getDrawFourCount()) + " cards");
                System.out.println(currentPlayer + " turn skipped");
                game.drawCards(4 * game.getDrawFourCount());
                return new TurnResult(null, null);
            }
        } else {
            playedCard = switch (game.getCurrentPlayerIndex() % 3) {
                case 1 -> currentPlayer.playNumberCardFirst(game.getTopCard());
                case 2 -> currentPlayer.playActionCardFirst(game.getTopCard());
                default -> currentPlayer.playCard(game.getTopCard());
            };
            if (playedCard == null && !game.getDeck().isEmpty()) {
                drawnCard = game.getDeck().drawCard();
                if (game.canPlayCard(drawnCard)) {
                    System.out.println(currentPlayer + " draws a card");
                    System.out.println(currentPlayer + " plays drawn card: " + drawnCard);
                    playedCard = drawnCard;
                } else {
                    currentPlayer.addCard(drawnCard);
                    System.out.println(currentPlayer + " draws a card");
                    System.out.println(currentPlayer + " turn skipped");
                    game.moveToNextPlayer();
                }
            }
        }
        return new TurnResult(playedCard, drawnCard);
    }

    private static void displayGameState(Game game) {
        System.out.println("Top card: " + game.getTopCard() + ", Current color: " + game.getCurrentColor());
    }
}
