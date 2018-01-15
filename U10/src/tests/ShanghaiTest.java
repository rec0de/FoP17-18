package tests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import darts.Shanghai;
import darts.Player;


/**
 * @author Nils Rollshausen
 *
 */
public class ShanghaiTest {

	@Test
	public void testClassicGame() {
		Shanghai game = new Shanghai(2);
		
		Player player1 = new Player("A");
		Player player2 = new Player("B");
		
		int[] expectedScores = {0, 0};
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		// Round 1
		
		assertTrue(game.throwDart(1, 3));
		assertTrue(game.throwDart(0, 0));
		assertTrue(game.throwDart(14, 2));
		
		expectedScores[0] = 3;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		assertTrue(game.throwDart(25, 2));
		assertTrue(game.throwDart(1, 1));
		assertTrue(game.throwDart(1, 3));
		
		expectedScores[1] = 4;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 2
		
		game.throwDart(2, 1);
		game.throwDart(1, 2);
		game.throwDart(3, 1);
		
		expectedScores[0] += 2;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		game.throwDart(2, 3);
		game.throwDart(19, 1);
		game.throwDart(2, 1);
		
		expectedScores[1] += 8;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 3
		
		game.throwDart(0, 0);
		game.throwDart(2, 3);
		game.throwDart(3, 3);
		
		expectedScores[0] += 9;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		game.throwDart(25, 1);
		game.throwDart(13, 3);
		game.throwDart(3, 1);
		
		expectedScores[1] += 3;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 4
		
		game.throwDart(25, 1);
		game.throwDart(13, 3);
		game.throwDart(3, 1);
		
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		game.throwDart(4, 2);
		game.throwDart(5, 1);
		game.throwDart(0, 0);
		
		expectedScores[1] += 8;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 5
		
		game.throwDart(5, 3);
		game.throwDart(5, 1);
		game.throwDart(5, 2);
				
		expectedScores[0] += 30;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
				
		game.throwDart(5, 1);
		game.throwDart(5, 3);
		game.throwDart(5, 3);
				
		expectedScores[1] += 35;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 6
		
		game.throwDart(7, 1);
		game.throwDart(5, 2);
		game.throwDart(0, 0);
						
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
						
		game.throwDart(6, 1);
		game.throwDart(13, 1);
		game.throwDart(6, 2);
						
		expectedScores[1] += 18;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 7
		
		game.throwDart(7, 1);
		game.throwDart(5, 2);
		game.throwDart(0, 0);
								
		expectedScores[0] += 7;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
								
		game.throwDart(6, 1);
		game.throwDart(25, 1);
		game.throwDart(19, 2);
							
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 8
		
		game.throwDart(8, 2);
		game.throwDart(7, 1);
		game.throwDart(8, 1);
						
		expectedScores[0] += 24;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
						
		game.throwDart(7, 3);
		game.throwDart(8, 1);
		game.throwDart(9, 2);
					
		expectedScores[1] += 8;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 9
		
		game.throwDart(8, 1);
		game.throwDart(9, 1);
		game.throwDart(8, 1);
						
		expectedScores[0] += 9;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
						
		game.throwDart(0, 0);
		game.throwDart(7, 1);
		game.throwDart(9, 3);
						
		expectedScores[1] += 27;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player2);
	}
	
	@Test
	public void testShanghai() {
		Shanghai game = new Shanghai(2);
		
		Player player1 = new Player("Some Dude");
		Player player2 = new Player("Another Dude");
		
		int[] expectedScores = {0, 0};
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertTrue(game.throwDart(20, 3));
		assertTrue(game.throwDart(1, 2));
		assertTrue(game.throwDart(5, 2));
		
		expectedScores[0] += 2;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertTrue(game.throwDart(1, 1));
		assertTrue(game.throwDart(1, 2));
		assertTrue(game.throwDart(1, 3));
		
		expectedScores[1] += 6;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player2);
	}
	
	@Test
	public void testMultiplayer() {
		Shanghai game = new Shanghai(5);
		
		Player player1 = new Player("A");
		Player player2 = new Player("B");
		Player player3 = new Player("C");
		Player player4 = new Player("D");
		Player player5 = new Player("E");
		
		int[] expectedScores = {0, 0, 0, 0, 0};
		
		assertTrue("Player add failed", game.addPlayer(player1));
		assertTrue("Player add failed", game.addPlayer(player2));
		assertTrue("Player add failed", game.addPlayer(player3));
		assertTrue("Player add failed", game.addPlayer(player4));
		assertTrue("Player add failed", game.addPlayer(player5));
		
		assertTrue("Game start failed", game.start());
		assertNull(game.getWinner());
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		// Round 1
		
		assertTrue(game.throwDart(1, 3));
		assertTrue(game.throwDart(0, 0));
		assertTrue(game.throwDart(14, 2));
		
		expectedScores[0] = 3;
		assertEquals(game.getActivePlayerNumber(), 1);
		assertEquals(game.getLeftDarts(), 3);
		
		assertTrue(game.throwDart(25, 2));
		assertTrue(game.throwDart(1, 1));
		assertTrue(game.throwDart(1, 3));
		
		expectedScores[1] = 4;
		assertEquals(game.getActivePlayerNumber(), 2);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(2, 1);
		game.throwDart(1, 2);
		game.throwDart(3, 1);
		
		expectedScores[2] += 2;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertEquals(game.getActivePlayerNumber(), 3);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(2, 3);
		game.throwDart(1, 1);
		game.throwDart(2, 1);
		
		expectedScores[3] += 1;
		assertEquals(game.getActivePlayerNumber(), 4);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(0, 0);
		game.throwDart(2, 3);
		game.throwDart(3, 3);
		
		expectedScores[4] += 0;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		// Round 2
		
		assertEquals(game.getActivePlayerNumber(), 0);
		assertEquals(game.getLeftDarts(), 3);
		
		game.throwDart(2, 1);
		game.throwDart(2, 2);
		game.throwDart(2, 3);
		
		expectedScores[0] += 12;
		assertTrue("Unexpected score", Arrays.equals(game.getScore(), expectedScores));
		
		assertFalse(game.isRunning());
		assertTrue(game.isOver());
		assertEquals("Unexpected winner", game.getWinner(), player1);
		
	}

}
