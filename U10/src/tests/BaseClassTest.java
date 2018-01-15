package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;

import darts.Shanghai;
import darts.DoubleOut;
import darts.Player;


/**
 * @author Nils Rollshausen
 *
 */
public class BaseClassTest {

	@Test
	public void testGameStart() {
		Shanghai game = new Shanghai(2);

		Player player1 = new Player("A");
		Player player2 = new Player("B");
		Player player3 = new Player("C");

		assertFalse(game.isRunning());
		assertFalse(game.start()); // Game is empty
		assertTrue(game.addPlayer(player1));
		assertFalse(game.addPlayer(player1)); // Player 1 is already in game
		assertTrue(game.addPlayer(player2));
		assertFalse(game.addPlayer(player3)); // Game is already full

		assertTrue(game.start());
		assertFalse(game.start()); // Game is already running
		assertTrue(game.isRunning());

		game.endGame();
		assertTrue(game.isOver());
		assertFalse(game.isRunning());
		assertFalse(game.start());
	}
	
	@Test
	public void testJoinTiming() {
		DoubleOut game = new DoubleOut(5, 501);

		Player player1 = new Player("Some Dude");
		Player player2 = new Player("Another Dude");
		Player player3 = new Player("Dude who's late af");

		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));

		assertTrue("Game start failed", game.start());
		assertTrue("Game start failed", game.isRunning());

		assertFalse(game.addPlayer(player3));

		game.endGame();
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertFalse(game.addPlayer(player3));
	}

	@Test
	public void testMetrics() {
		Shanghai game = new Shanghai(10);

		Player player1 = new Player("A");
		Player player2 = new Player("B");
		Player player3 = new Player("C");
		Player player4 = new Player("D");
		Player player5 = new Player("E");

		Player[] testplayers3 = {player1, player2, player3};
		Player[] testplayers5 = {player1, player2, player3, player4, player5};

		assertEquals(game.getGamemode(), "Shanghai");
		assertEquals(game.getPlayerCount(), 0);

		assertTrue("Player add failed", game.addPlayer(player1));
		assertEquals(game.getPlayerCount(), 1);

		assertTrue("Player add failed", game.addPlayer(player2));
		assertEquals(game.getPlayerCount(), 2);

		assertTrue("Player add failed", game.addPlayer(player3));
		assertEquals(game.getPlayerCount(), 3);

		assertTrue(Arrays.equals(game.getPlayers(), testplayers3));

		assertTrue("Player add failed", game.addPlayer(player4));
		assertEquals(game.getPlayerCount(), 4);

		assertTrue("Player add failed", game.addPlayer(player5));
		assertEquals(game.getPlayerCount(), 5);

		assertTrue(Arrays.equals(game.getPlayers(), testplayers5));
	}
	
	@Test
	public void testThrowDart() {
		Shanghai game = new Shanghai(1);

		Player player1 = new Player("Solitude");

		game.addPlayer(player1);

		assertFalse(game.throwDart(3, 3));

		assertTrue("Couldn't start game", game.start());

		assertTrue(game.throwDart(0, 0));
		assertFalse(game.throwDart(4, 0));
		assertFalse(game.throwDart(0, 2));
		assertFalse(game.throwDart(-2, 1));
		assertFalse(game.throwDart(20, 4));
		assertFalse(game.throwDart(25, 3));
		assertFalse(game.throwDart(30, 1));
		assertEquals(game.getLeftDarts(), 2);
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(1, 1));

		assertTrue(game.throwDart(25, 1));
		assertTrue(game.throwDart(25, 2));
		assertTrue(game.throwDart(17, 2));
		assertFalse(game.throwDart(20, -2));

		game.endGame();

		assertFalse(game.throwDart(4, 1));
	}

}
