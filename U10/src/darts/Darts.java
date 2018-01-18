package darts;

import java.util.Arrays;

/**
 * Class to model a generic game of Darts, implementing IDarts
 * @author Nils Rollshausen
 */
public abstract class Darts implements IDarts {
	
	private String gamemode;
	private int maxPlayerCount;
	private Player[] players;
	private Player winner = null;
	
	private int playerCount = 0;
	private int activePlayerIndex = 0;
	private int currentPlayerDartsLeft;
	
	private boolean isRunning = false;
	private boolean isEnded = false;
	
	/**
	 * Main constructor for Darts class - initializes new Darts game
	 * @param gamemode Name of the gamemode being played
	 * @param maxPlayers Maximum number of players in the game
	 */
	public Darts(String gamemode, int maxPlayers) {
		this.gamemode = gamemode;
		this.maxPlayerCount = maxPlayers;
		this.players = new Player[maxPlayers];
	}

	/**
	 * Adds a player to the game, 
	 * prints an error in the console if the player limit is exceeded
	 * 
	 * @param player the player that is added to the game
	 * @return true if the player was added, false if an error occurred
	 */
	@Override
	public boolean addPlayer(Player player) {
		// Add player if there is room left, the game is not yet started or ended, and there is no identical player object already in the game
		if(playerCount >= maxPlayerCount) {
			System.out.println("Error: Couldn't add player, game is full");
			return false;
		}
		else if(isRunning) {
			System.out.println("Error: Couldn't add player, game is already running");
			return false;
		}
		else if(isEnded) {
			System.out.println("Error: Couldn't add player, game is already over");
			return false;
		}
		else if(Arrays.asList(players).contains(player)) {
			System.out.println("Error: Couldn't add player, player is already in game");
			return false;
		}
		
		players[playerCount] = player;
		playerCount += 1;
		return handleNewPlayer(playerCount - 1);
	}
	

	/**
	 * Starts the game
	 * @return true if the game was started, false if an error occurred (to few players, game already started,...)
	 */
	@Override
	public boolean start() {
		if(!isRunning && !isEnded && playerCount > 0) {
			this.isRunning = true;
			this.isEnded = false;
			this.winner = null; // Reset in case of object reuse across games (shouldn't be possible, but doesn't hurt anyway)
			this.activePlayerIndex = 0; // First player always starts
			this.currentPlayerDartsLeft = 3; // All gamemodes have 3 darts per turn
			this.handleNextPlayer();
			return true;
		}
		else{ // Something went wrong, print appropriate error message
			if(isRunning)
				System.out.println("Error: Couldn't start game, game is already running");
			else if(isEnded) 
				System.out.println("Error: Couldn't start game, game has already ended");
			else if(playerCount < 1) 
				System.out.println("Error: Couldn't start game, game has no players");
			else 
				System.out.println("Error: Couldn't start game. Unknown error");
			
			return false;
		}
	}
	
	/**
	 * A player throws a dart
	 * 
	 * @param number the number of the hit field, 0 if the player missed
	 * @param multiplicator the multiplier of the hit field, 0 if the player missed
	 * @return true if the throw was valid, false otherwise (invalid field/multiplier, game was not running,...)
	 */
	@Override
	public boolean throwDart(int number, int multiplier) {
		if(isEnded || !isRunning) {
			System.out.println("Error: Invalid throw, game is not running");
			return false;
		}
		else if(!(number >= 0 && number <= 20) && number != 25) { // Number is not in 1-20 range and not 25
			System.out.println("Error: Invalid throw, impossible number field");
			return false;
		}
		else if(multiplier < 0 || multiplier > 3 || (multiplier == 3 && number == 25) || (multiplier == 0 && number != 0) || (multiplier != 0 && number == 0)) {
			System.out.println("Error: Invalid throw, impossible multiplier for that number");
			return false;
		}
		
		handleDart(number, multiplier);
		
		this.currentPlayerDartsLeft -= 1;
		
		if(this.currentPlayerDartsLeft <= 0) {
			this.currentPlayerDartsLeft = 3;
			this.activePlayerIndex = (activePlayerIndex + 1) % playerCount;
			this.handleNextPlayer();
		}
		
		return true;
	}
	
	/**
	 * Returns the winner
	 * @return the player who has won or null if the game isn't over yet or there is no winner
	 */
	@Override
	public Player getWinner() {
		return this.winner;
	}

	/**
	 * This method ends the game
	 */
	@Override
	public void endGame() {
		this.isRunning = false;
		this.isEnded = true;
	}
	
	/**
	 * Returns an array filled with all players
	 * @return an array with all players
	 */
	@Override
	public Player[] getPlayers() {
		// players is statically initialized to maximum player count - we need to return an array containing _only_ players that actually joined the game
		Player[] realPlayers = new Player[playerCount];

		for(int i = 0; i < playerCount; i++) {
			realPlayers[i] = players[i];
		}

		return realPlayers;
	}

	/** 
	 * Returns the number of players playing the game 
	 * @return the number of players
	 */
	@Override
	public int getPlayerCount() {
		return this.playerCount;
	}
	
	/**
	 * Returns the number of the active player
	 * The players are numbered in the order they were added. The first player has number 0, the second 1, ...
	 * @return the number of the active player
	 */
	@Override
	public int getActivePlayerNumber() {
		return this.activePlayerIndex;
	}
	
	/**
	 * returns the number of darts a player has left
	 * @return the number of darts a player has left
	 */
	@Override
	public int getLeftDarts() {
		return this.currentPlayerDartsLeft;
	}

	/**
	 * Returns the name of the gamemode
	 * @return the name of the gamemode
	 */
	@Override
	public String getGamemode() {
		return this.gamemode;
	}

	/**
	 * Checks if the game is running 
	 * @return true if the game is running, false otherwise
	 */
	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	/**
	 * Checks if the game is over
	 * @return true if the game is over, false otherwise
	 */
	@Override
	public boolean isOver() {
		return this.isEnded;
	}
	
	/**
	 * Changes game winner to player with supplied index
	 * @param playerIndex Index of the winning player in the players array
	 */
	void setWinner(int playerIndex) {
		if(playerIndex < players.length)
			this.winner = players[playerIndex];
	}
	
	/**
	 * Manually changes the number of darts the current player has left - used to prematurely end turns
	 * @param newDartsLeft New number of darts left
	 */
	void overrideDartsLeft(int newDartsLeft) {
		this.currentPlayerDartsLeft = newDartsLeft;
	}
	
	/**
	 * Delegates game-specific dart handling / score keeping to the implementing class _after_ throw validity has been confirmed
	 * Called whenever a new dart is thrown
	 * (Note that number and multiplier are guaranteed to correspond to a valid dart board field)
	 * @param number The number value of the field hit
	 * @param multiplier Multiplier of the field hit (1, 2, or 3)
	 */
	abstract void handleDart(int number, int multiplier);
	
	/**
	 * Delegates game-specific player setup to the implementing class after the base player is set up
	 * Player add is only considered successful if handleNewPlayer returns true
	 * @param playerIndex Index of the newly added player in the player array
	 * @return Returns true on success, false otherwise
	 */
	abstract boolean handleNewPlayer(int playerIndex);
	
	/**
	 * Delegates game-specific per-turn setup to the implementing class
	 * Called whenever a new Player takes turn
	 */
	abstract void handleNextPlayer();

}
