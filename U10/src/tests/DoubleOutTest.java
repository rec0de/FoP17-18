package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import darts.DoubleOut;
import darts.Player;


/**
 * @author Nils Rollshausen
 *
 */
public class DoubleOutTest {

	@Test
	public void testClassicGame() {
		DoubleOut game = new DoubleOut(2, 501);
		
		Player player1 = new Player("Taylor");
		Player player2 = new Player("Barnefeld");
		
		int[] expectedScores = {501, 501};
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		assertTrue(game.throwDart(20, 3));
		assertEquals(game.getLeftDarts(), 2);
		assertTrue(game.throwDart(20, 3));
		assertEquals(game.getLeftDarts(), 1);
		assertEquals(game.getActivePlayerNumber(), 0);
		assertTrue(game.throwDart(20, 3));
		
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		expectedScores[0] -= 180;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertTrue(game.throwDart(20, 3));
		assertEquals(game.getLeftDarts(), 2);
		assertTrue(game.throwDart(20, 1));
		assertEquals(game.getLeftDarts(), 1);
		assertEquals(game.getActivePlayerNumber(), 1);
		assertTrue(game.throwDart(1, 3));
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		expectedScores[1] -= 83;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		game.throwDart(20, 3);
		game.throwDart(20, 3);
		game.throwDart(20, 3);
		
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		expectedScores[0] -= 180;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		game.throwDart(20, 3);
		game.throwDart(20, 3);
		game.throwDart(20, 3);
		
		expectedScores[1] -= 180;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		game.throwDart(20, 3);
		game.throwDart(19, 3);
		
		expectedScores[0] -= 117;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 1);
		assertTrue(game.isRunning());
		assertFalse(game.isOver());
		assertNull(game.getWinner());
		
		game.throwDart(12, 2);
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player1);
		assertEquals("Unexpected score", game.getScore()[0], 0);
	}
	
	@Test
	public void testOverthrowing() {
		DoubleOut game = new DoubleOut(2, 141);
		
		Player player1 = new Player("Some Dude");
		Player player2 = new Player("Another Dude");
		
		int[] expectedScores = {141, 141};
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(25, 2));
		
		assertEquals("Unexpected score", game.getScore()[0], 141);
		
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(1, 1));
		
		assertEquals("Unexpected score", game.getScore()[1], 20);
		
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(20, 1));
		
		assertEquals("Unexpected score", game.getScore()[0], 141);
		
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		assertTrue(game.throwDart(25, 1));
		
		assertEquals("Unexpected score", game.getScore()[1], 20);
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		assertTrue(game.throwDart(0, 0));
		assertTrue(game.throwDart(0, 0));
		assertTrue(game.throwDart(0, 0));
		
		assertTrue(game.throwDart(10, 2));
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player2);
		assertEquals("Unexpected score", game.getScore()[1], 0);
		
	}
	
	@Test
	public void testMultiplayer() {
		DoubleOut game = new DoubleOut(5, 100);
		
		Player player1 = new Player("A");
		Player player2 = new Player("B");
		Player player3 = new Player("C");
		Player player4 = new Player("D");
		Player player5 = new Player("E");
		
		int[] expectedScores = {100, 100, 100, 100, 100};
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		assertTrue("Player add failed", game.addPlayer(player3));
		assertTrue("Player add failed", game.addPlayer(player4));
		assertFalse("Could add duplicate player", game.addPlayer(player4)); // Player 4 is already in game
		assertTrue("Player add failed", game.addPlayer(player5));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(15, 2);
		game.throwDart(10, 1);
		game.throwDart(25, 1);
		
		expectedScores[0] -= 65;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(19, 3);
		game.throwDart(0, 0);
		game.throwDart(5, 1);
		
		expectedScores[1] -= 62;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 2);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(25, 2);
		game.throwDart(10, 1);
		game.throwDart(2, 1);
		
		expectedScores[2] -= 62;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 3);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(13, 2);
		game.throwDart(7, 2);
		game.throwDart(6, 1);
		
		expectedScores[3] -= 46;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 4);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(0, 0);
		game.throwDart(14, 3);
		game.throwDart(14, 1);
		
		expectedScores[4] -= 56;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(20, 1);
		game.throwDart(15, 1);
		// Score 0, but no double out
		
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(20, 1);
		game.throwDart(9, 2);
		
		expectedScores[1] = 0;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player2);
	}
	
	@Test
	public void testNonFullGame() {
		DoubleOut game = new DoubleOut(256, 100);
		
		Player player1 = new Player("A");
		Player player2 = new Player("B");
		Player player3 = new Player("C");
		Player player4 = new Player("D");
		
		int[] expectedScores = {100, 100, 100, 100};
		
		assertTrue(game.addPlayer(player1));
		assertTrue(game.addPlayer(player2));
		assertTrue(game.addPlayer(player3));
		assertTrue(game.addPlayer(player4));
		
		assertTrue(game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
	}

}
