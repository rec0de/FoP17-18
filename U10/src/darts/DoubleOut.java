package darts;

/**
 *  Class modeling a game of DoubleOut, implementing the IDarts interface
 *
 * @author Nils Rollshausen
 */
public class DoubleOut extends Darts {
	
	private int startScore;
	private int[] scores;
	private int scoreBeforeTurn;

	/**
	 * Main constructor for DoubleOut Class - initializes a new DoubleOut game
	 * @param startScore Initial score that is counted down from - traditionally 501
	 * @param maxPlayers Maximum number of players in the game
	 */
	public DoubleOut(int maxPlayers, int startScore) {
		super("DoubleOut", maxPlayers);
		this.startScore = startScore;
		this.scores = new int[maxPlayers];
	}

	/**
	 * Handles a single valid dart throw, updating the score of the current player accordingly and ending the turn if overthrown
	 * 
	 * @param number the number of the hit field, 0 if the player missed
	 * @param multiplicator the multiplier of the hit field, 0 if the player missed
	 * @return true if the throw was valid, false otherwise (invalid field/multiplier, game was not running,...)
	 */
	@Override
	void handleDart(int number, int multiplier) {
		int activePlayerIndex = this.getActivePlayerNumber();
		int newScore = scores[activePlayerIndex] - number * multiplier;
		
		if(newScore < 0 || (multiplier != 2 && newScore <= 1)) {
			// discard remaining throws, end turn
			this.overrideDartsLeft(1);// Value is decremented by one after handleDart() is called, used to end turn
			this.scores[activePlayerIndex] = scoreBeforeTurn; // Return score back to score before turn
			return;
		}
		
		this.scores[activePlayerIndex] = newScore;
		
		if(newScore == 0) {
			this.setWinner(activePlayerIndex);
			this.endGame();
		}
	}
	
	/**
	 * Handles a player join by initializing the players score to the start score - called on player join
	 * @param playerIndex Index of joining player in players array
	 * @return Returns true on success, false otherwise
	 */
	@Override
	boolean handleNewPlayer(int playerIndex) {
		
		// Prevent out of bounds array access - this should never happen
		if(playerIndex >= scores.length) {
			return false;
		}
		
		this.scores[playerIndex] = this.startScore; // Init score
		return true;
	}
	
	/**
	 * Re-sets Double-Out specific settings on turn end (specifically setting the pre-turn score of the player)
	 */
	@Override
	void handleNextPlayer() {
		this.scoreBeforeTurn = scores[this.getActivePlayerNumber()];
	}
	
	/**
	 * Returns the current scores of all players as an array (indices corresponding to getPlayers() numbering)
	 * @return Array of player scores
	 */
	public int[] getScore() {
		// scores is statically initialized to maximum player count - we need to return an array containing _only_ scores that are actually connected to players
		int playerCount = this.getPlayerCount();
		int[] realScores = new int[playerCount];

		for(int i = 0; i < playerCount; i++) {
			realScores[i] = scores[i];
		}

		return realScores;
	}

}
