package darts;

/**
 * Class modeling a game of Shanghai, implementing the IDarts interface
 *
 * @author Nils Rollshausen
 */
public class Shanghai extends Darts {
	
	private int round = 0;
	private int[] scores;
	
	private int currentTurnIndex;
	private boolean currentTurnHasSingle;
	private boolean currentTurnHasDouble;
	private boolean currentTurnHasTriple;

	/**
	 * Main constructor for Shanghai class - initializes new Shanghai game
	 * @param maxPlayers Maximal number of players
	 */
	public Shanghai(int maxPlayers) {
		super("Shanghai", maxPlayers);
		this.scores = new int[maxPlayers];
	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleDart(int, int)
	 */
	@Override
	void handleDart(int number, int multiplier) {
		if(number == round) {
			scores[activePlayerIndex] += number * multiplier;
			
			if(multiplier == 1 && currentTurnIndex == 1)
				this.currentTurnHasSingle = true;
			else if(multiplier == 2 && currentTurnIndex == 2)
				this.currentTurnHasDouble = true;
			else if(multiplier == 3 && currentTurnIndex == 3)
				this.currentTurnHasTriple = true;
			
			if(currentTurnHasSingle && currentTurnHasDouble && currentTurnHasTriple) {
				// SHANGHAI!
				this.setWinner(activePlayerIndex);
				this.endGame();
			}
		}
		
		this.currentTurnIndex += 1;

	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleNewPlayer(int)
	 */
	@Override
	boolean handleNewPlayer(int playerIndex) {
		// Prevent out of bounds array access - this should never happen
		if(playerIndex >= scores.length)
			return false;
				
		this.scores[playerIndex] = 0; // Init score
		return true;
	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleNextPlayer()
	 */
	@Override
	void handleNextPlayer() {
		this.currentTurnHasSingle = false;
		this.currentTurnHasDouble = false;
		this.currentTurnHasTriple = false;
		this.currentTurnIndex = 1;
		
		if(activePlayerIndex == 0) {
			this.nextRound();
		}
	}
	
	/**
	 * Returns the current scores of all players as an array
	 * @return Array of player scores
	 */
	public int[] getScore() {
		// scores is statically initialized to maximum player count - we need to return an array containing _only_ scores that are actually connected to players
		int[] realScores = new int[playerCount];

		for(int i = 0; i < playerCount; i++) {
			realScores[i] = scores[i];
		}

		return realScores;
	}
	
	/**
	 * Advances the round counter by one and ends the game if 9 rounds have been played.
	 * Sets game winner to player with the highest score on game end.
	 */
	private void nextRound() {
		this.round += 1;
		
		if(this.round > 9) {
			int bestscore = -1;
			int winnerIndex = 0;
			
			for(int i = 0; i < scores.length; i++) {
				if(scores[i] > bestscore)
					winnerIndex = i;
			}
			
			this.setWinner(winnerIndex);
			this.endGame();
		}
	}

}
