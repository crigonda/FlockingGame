package Game;

import GUI.ParamFrame;

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
		float ggRadius = (float) 50;
		float bgRadius = 2*ggRadius;
		float angle = (float) Math.PI;
		// =============================================================================================================
		if (args.length == 2) {
			nbGoodGuys = Integer.parseInt(args[0]);
			nbBadGuys = Integer.parseInt(args[1]);
		} else {
			// Asks the user for parameters values
			new ParamFrame(nbGoodGuys, nbBadGuys, agentSize, minVelocity, maxVelocityGG, maxVelocityBG,
					ggRadius, bgRadius, angle);
		}
	}

}
