package darts;

/**
 * @author Nils Rollshausen
 *
 */
public class DoubleOut extends Darts {
	
	private int startScore;
	private int[] scores;
	private int scoreBeforeTurn;

	/**
	 * @param gamemode
	 * @param maxPlayers
	 */
	public DoubleOut(int maxPlayers, int startScore) {
		super("DoubleOut", maxPlayers);
		this.startScore = startScore;
		this.scores = new int[maxPlayers];
	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleDart(int, int)
	 */
	@Override
	void handleDart(int number, int multiplier) {
		int newScore = scores[activePlayerIndex] - number * multiplier;
		
		if(newScore < 0 || (multiplier != 2 && newScore <= 1)) {
			// discard remaining throws, end turn
			this.currentPlayerDartsLeft = 1; // Value is decremented by one after handleDart() is called
			this.scores[activePlayerIndex] = scoreBeforeTurn;
			return;
		}
		
		this.scores[activePlayerIndex] = newScore;
		
		if(newScore == 0) {
			this.setWinner(activePlayerIndex);
			this.endGame();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see darts.Darts#handleNewPlayer(int)
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
	
	@Override
	void handleNextPlayer() {
		this.scoreBeforeTurn = scores[activePlayerIndex];
	}
	
	/**
	 * 
	 * @return
	 */
	public int[] getScore() {
		return this.scores;
	}

}
