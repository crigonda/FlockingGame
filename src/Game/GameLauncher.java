package Game;

public class GameLauncher {

	public static void main(String[] args) {
		// ============================================== GAME PARAMETERS ==============================================
		int nbGoodGuys = 50;
		int nbBadGuys = 5;
		int agentSize = 15;
		float minVelocity = 2;
		float maxVelocityGG = 3;
		float maxVelocityBG = (float) 2.5;
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
