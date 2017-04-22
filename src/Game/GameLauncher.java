package Game;

/** Launches a game with given parameters
 * @author Clément
 */
public class GameLauncher {

	public static void main(String[] args) {
		// ============================================== GAME PARAMETERS ==============================================
		int nbGoodGuys = 50;
		int nbBadGuys = 5;
		int agentSize = 15;
		float minVelocity = (float) 3;
		float maxVelocityGG = (float) 4;
		float maxVelocityBG = (float) 3;
		float radius = (float) 50;
		float angle = (float) 100;
		// =============================================================================================================
		// Creates the game
		Game game = new Game(nbGoodGuys, nbBadGuys, agentSize, minVelocity, 
				maxVelocityGG, maxVelocityBG, radius, angle);
		// Runs it
		game.run();
	}

}
