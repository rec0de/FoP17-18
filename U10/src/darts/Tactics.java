package darts;

/**
 * @author Nils Rollshausen
 *
 */
public class Tactics extends Darts {

	// Scores are a 2d array with first index playerIndex, second index field number and value = times hit
	private int scores[][];

	/**
	 * Main constructor for Tactics class - initializes new Tactics game
	 * @param maxPlayers Maximum number of players in the game
	 */
	public Tactics(int maxPlayers) {
		super("Tactics", maxPlayers);
		scores = new int[maxPlayers][]; 
	}

	/**
	 * Handles a single valid dart throw, updating the score of the current player accordingly
	 * Ends the game if the player has hit all target fields
	 * 
	 * @param number the number of the hit field, 0 if the player missed
	 * @param multiplicator the multiplier of the hit field, 0 if the player missed
	 * @return true if the throw was valid, false otherwise (invalid field/multiplier, game was not running,...)
	 */
	@Override
	void handleDart(int number, int multiplier) {
		if(number >= 10) {
			int activePlayerIndex = this.getActivePlayerNumber();
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

	/**
	 * Handles a player join by initializing the players score table - called on player join
	 * @param playerIndex Index of joining player in players array
	 * @return Returns true on success, false otherwise
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

	/**
	 * Stub called on new turn - Does nothing as there is no Tactics-specific per-turn setup
	 */
	@Override
	void handleNextPlayer() {
		// Nothing needs to be done on turn change in this gamemode
	}

}
