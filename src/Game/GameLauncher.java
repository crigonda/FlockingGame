package Game;

public class GameLauncher {

	public static void main(String[] args) {
		// ============================================== GAME PARAMETERS ==============================================
		int nbGoodGuys = 50;
		int nbBadGuys = 5;
		int agentSize = 15;
		float minVelocity = 3;
		float maxVelocityGG = 4;
		float maxVelocityBG = (float) 3;
		float radius = 50;
		float angle = 100;
		// =============================================================================================================
		// Creates the game
		Game game = new Game(nbGoodGuys, nbBadGuys, agentSize, minVelocity, 
				maxVelocityGG, maxVelocityBG, radius, angle);
		// Runs it
		game.run();
	}

}
