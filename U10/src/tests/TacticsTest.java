package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import darts.Tactics;
import darts.Player;


/**
 * @author Nils Rollshausen
 *
 */
public class TacticsTest {

	@Test
	public void testNormalGame() {
		Tactics game = new Tactics(2);
		
		Player player1 = new Player("A");
		Player player2 = new Player("B");
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		// Round 1
		
		assertTrue(game.throwDart(10, 3));
		assertTrue(game.throwDart(11, 3));
		assertTrue(game.throwDart(12, 3));
		
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		assertTrue(game.throwDart(25, 2));
		assertTrue(game.throwDart(25, 1));
		assertTrue(game.throwDart(20, 3));
		
		// Round 2
		
		game.throwDart(13, 3);
		game.throwDart(14, 1);
		game.throwDart(15, 3);
		
		game.throwDart(19, 3);
		game.throwDart(18, 3);
		game.throwDart(17, 3);
		
		// Round 3
		
		game.throwDart(16, 3);
		game.throwDart(17, 3);
		game.throwDart(18, 3);
		
		game.throwDart(16, 3);
		game.throwDart(15, 3);
		game.throwDart(14, 3);
		
		// Round 4
		
		game.throwDart(19, 3);
		game.throwDart(20, 3);
		game.throwDart(25, 2);
		
		game.throwDart(13, 3);
		game.throwDart(12, 3);
		game.throwDart(11, 3);
		
		// Round 5
		
		game.throwDart(25, 1);
		
		assertTrue(game.isRunning());
		assertFalse(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), null);
		
		game.throwDart(14, 1);
		game.throwDart(5, 3);
				
		game.throwDart(10, 1);
		game.throwDart(0, 0);
		game.throwDart(10, 2);
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player2);
	}
	
	@Test
	public void testDartCounting() {
		Tactics game = new Tactics(1);
		
		Player player1 = new Player("Some Dude");
		
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		
		game.throwDart(10, 2);
		game.throwDart(11, 1);
		game.throwDart(12, 1);
		game.throwDart(13, 1);
		game.throwDart(14, 1);
		game.throwDart(15, 1);
		game.throwDart(16, 1);
		game.throwDart(17, 1);
		game.throwDart(18, 3);
		game.throwDart(19, 1);
		game.throwDart(20, 1);
		game.throwDart(25, 2);
		
		game.throwDart(0, 0);
		game.throwDart(5, 3);
		game.throwDart(9, 2);
		
		game.throwDart(10, 3);
		game.throwDart(11, 1);
		game.throwDart(12, 1);
		game.throwDart(13, 2);
		game.throwDart(14, 1);
		game.throwDart(15, 1);
		game.throwDart(16, 1);
		game.throwDart(17, 1);
		game.throwDart(19, 1);
		game.throwDart(20, 1);
		game.throwDart(25, 1);
		
		game.throwDart(11, 1);
		game.throwDart(12, 1);
		game.throwDart(14, 1);
		game.throwDart(16, 1);
		game.throwDart(17, 1);
		game.throwDart(19, 1);
		game.throwDart(20, 1);
		
		assertTrue(game.throwDart(7, 1));
		assertTrue(game.throwDart(3, 3));
		assertTrue(game.throwDart(1, 2));
		
		assertTrue(game.isRunning());
		assertNull(game.getWinner());
		
		game.throwDart(15, 2);
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player1);
	}
	
	@Test
	public void testMultiplayer() {
		Tactics game = new Tactics(10);
		
		Player player1 = new Player("A");
		Player player2 = new Player("B");
		Player player3 = new Player("C");
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		assertTrue("Player add failed", game.addPlayer(player3));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);

		game.throwDart(10, 3);
		game.throwDart(11, 3);
		assertEquals(game.getLeftDarts(), 1);
		game.throwDart(12, 3);

		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);

		game.throwDart(10, 3);
		game.throwDart(11, 3);
		game.throwDart(12, 3);

		game.throwDart(10, 3);
		game.throwDart(11, 3);
		game.throwDart(12, 3);

		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);

		game.throwDart(13, 3);
		game.throwDart(14, 3);
		game.throwDart(15, 3);

		game.throwDart(13, 3);
		game.throwDart(14, 3);
		game.throwDart(15, 3);

		game.throwDart(13, 3);
		game.throwDart(14, 3);
		game.throwDart(15, 3);

		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);

		game.throwDart(16, 3);
		game.throwDart(17, 3);
		game.throwDart(18, 3);

		game.throwDart(16, 3);
		game.throwDart(17, 3);
		game.throwDart(18, 3);

		game.throwDart(16, 3);
		game.throwDart(17, 3);
		game.throwDart(18, 3);

		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);		

		game.throwDart(19, 3);
		game.throwDart(20, 3);
		game.throwDart(25, 2);

		game.throwDart(19, 3);
		game.throwDart(20, 3);
		game.throwDart(25, 2);

		game.throwDart(19, 3);
		game.throwDart(20, 3);
		game.throwDart(25, 2);

		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);	
		assertTrue(game.isRunning());
		assertNull(game.getWinner());
		
		game.throwDart(0, 0);
		game.throwDart(5, 3);
		game.throwDart(9, 2);
		
		game.throwDart(13, 2);
		game.throwDart(14, 1);
		game.throwDart(15, 1);

		game.throwDart(16, 1);
		game.throwDart(17, 1);
		game.throwDart(19, 1);

		assertTrue(game.isRunning());
		assertNull(game.getWinner());

		game.throwDart(0, 0);

		assertTrue(game.isRunning());
		assertNull(game.getWinner());

		game.throwDart(25, 1);

		assertFalse(game.isRunning());
		assertEquals(game.getWinner(), player1);
	}

}
