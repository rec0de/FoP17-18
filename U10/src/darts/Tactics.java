package darts;

/**
 * @author Nils Rollshausen
 *
 */
public class Tactics extends Darts {

	private int scores[][];

	/**
	 * @param maxPlayers
	 */
	public Tactics(int maxPlayers) {
		super("Tactics", maxPlayers);
		scores = new int[maxPlayers][];
	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleDart(int, int)
	 */
	@Override
	void handleDart(int number, int multiplier) {
		if(number >= 10) {
			int index = (number == 25) ? 11 : number - 10; // Index in hit counting array is 0-10 for 10-20 and 11 for bull
			this.scores[activePlayerIndex][index] += multiplier;

			// Check if game is won
			boolean gameWon = true;

			for(int i = 0; i < 12; i++) {
				if(scores[activePlayerIndex][i] < 3) { // If one field is hit less than three times, game can't be won
					gameWon = false;
					break;
				}
			}

			if(gameWon) {
				this.setWinner(activePlayerIndex);
				this.endGame();
			}
		}
	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleNewPlayer(int)
	 */
	@Override
	boolean handleNewPlayer(int playerIndex) {
		// Prevent out of bounds array access - this should never happen
		if(playerIndex >= scores.length)
			return false;

		this.scores[playerIndex] = new int[12]; // Init array for hit fields

		// Set all dart fields to 'not hit' (index 11 being bull)
		for(int i = 0; i < 12; i++) {
			this.scores[playerIndex][i] = 0;
		}

		return true;
	}

	/* (non-Javadoc)
	 * @see darts.Darts#handleNextPlayer()
	 */
	@Override
	void handleNextPlayer() {
		// Nothing needs to be done in this gamemode
	}

}
