package darts;

import java.util.Arrays;

/**
 * @author Nils Rollshausen
 *
 */
public abstract class Darts implements IDarts {
	
	private String gamemode;
	private int maxPlayerCount;
	private Player[] players;
	
	int playerCount = 0;
	Player winner = null;
	int activePlayerIndex = 0;
	int currentPlayerDartsLeft;
	
	boolean isRunning = false;
	boolean isEnded = false;
	
	/**
	 * 
	 * @param gamemode
	 * @param maxPlayers
	 */
	public Darts(String gamemode, int maxPlayers) {
		this.gamemode = gamemode;
		this.maxPlayerCount = maxPlayers;
		this.players = new Player[maxPlayers];
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#addPlayer(darts.Player)
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

	/* (non-Javadoc)
	 * @see darts.IDarts#getPlayerCount()
	 */
	@Override
	public int getPlayerCount() {
		return this.playerCount;
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#getPlayers()
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
	
	/* (non-Javadoc)
	 * @see darts.IDarts#getActivePlayerNumber()
	 */
	@Override
	public int getActivePlayerNumber() {
		return this.activePlayerIndex;
	}
	
	/* (non-Javadoc)
	 * @see darts.IDarts#getLeftDarts()
	 */
	@Override
	public int getLeftDarts() {
		return this.currentPlayerDartsLeft;
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#getGamemode()
	 */
	@Override
	public String getGamemode() {
		return this.gamemode;
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#isRunning()
	 */
	@Override
	public boolean isRunning() {
		return this.isRunning;
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#isOver()
	 */
	@Override
	public boolean isOver() {
		return this.isEnded;
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#start()
	 */
	@Override
	public boolean start() {
		if(!isRunning && !isEnded && playerCount > 0) {
			this.isRunning = true;
			this.isEnded = false;
			this.winner = null; // Reset in case of object reuse across games
			this.activePlayerIndex = 0; // First player always starts
			this.currentPlayerDartsLeft = 3; // All gamemodes have 3 darts per turn
			this.handleNextPlayer();
			return true;
		}
		else{
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
	
	/*
	 * (non-Javadoc)
	 * @see darts.IDarts#throwDart(int, int)
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

	/* (non-Javadoc)
	 * @see darts.IDarts#getWinner()
	 */
	@Override
	public Player getWinner() {
		return this.winner;
	}
	
	/**
	 * 
	 * @param playerIndex
	 */
	void setWinner(int playerIndex) {
		if(playerIndex < players.length)
			this.winner = players[playerIndex];
	}

	/* (non-Javadoc)
	 * @see darts.IDarts#endGame()
	 */
	@Override
	public void endGame() {
		this.isRunning = false;
		this.isEnded = true;
	}
	
	/**
	 * 
	 * @param number
	 * @param multiplier
	 */
	abstract void handleDart(int number, int multiplier);
	
	abstract boolean handleNewPlayer(int playerIndex);
	
	abstract void handleNextPlayer();

}
