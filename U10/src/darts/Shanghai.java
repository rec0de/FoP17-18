package darts;

/**
 * @author Nils Rollshausen
 *
 */
public class Shanghai extends Darts {
	
	private int round = 0;
	private int[] scores;
	
	private int currentTurnIndex;
	private boolean currentTurnHasSingle;
	private boolean currentTurnHasDouble;
	private boolean currentTurnHasTriple;

	/**
	 * @param maxPlayers
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
	
	public int[] getScore() {
		return this.scores;
	}
	
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
