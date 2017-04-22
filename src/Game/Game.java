package Game;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Agent.BadGuy;
import Agent.GoodGuy;
import Environment.Coordinates;
import Environment.Environment;
import GUI.EnvironmentFrame;

/** Game
 * @author Clément
 */
public class Game implements Runnable {

	// Game parameters
	private int nbGoodGuys;
	private int nbBadGuys;
	private int agentSize;
	private float minVelocity;
	private float maxVelocityGG;
	private float maxVelocityBG;
	private float ggRadius;
	private float bgRadius;
	private float angle;
	// Environment
	private Environment env;
	// GUI
	private EnvironmentFrame gui;

	/** Constructor
	 * @param nbGoodGuys
	 * @param nbBadGuys
	 * @param agentSize
	 * @param minVelocity
	 * @param maxVelocityGG
	 * @param maxVelocityBG
	 * @param radius
	 * @param angle
	 */
	public Game(int nbGoodGuys, int nbBadGuys, int agentSize, float minVelocity, float maxVelocityGG,
			float maxVelocityBG, float ggRadius, float bgRadius, float angle) {
		// Game parameters
		this.nbGoodGuys = nbGoodGuys;
		this.nbBadGuys = nbBadGuys;
		this.agentSize = agentSize;
		this.minVelocity = minVelocity;
		this.maxVelocityGG = maxVelocityGG;
		this.maxVelocityBG = maxVelocityBG;
		this.ggRadius = ggRadius;
		this.bgRadius = bgRadius;
		this.angle = angle;
		// Creates the environment
		this.createEnvironment();
		// Adds good guys to the environment
		this.addGoodGuys();
		// Adds bad guys to the environment
		this.addBadGuys();
		// Creates the GUI
		this.gui = new EnvironmentFrame(this.env);
	}

	/** Creates the environment
	 */
	private void createEnvironment() {
		// The environment dimension depends on the screen size
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int size = (int)dimension.getHeight() - 10;
		this.env = new Environment(size, size);
	}

	/** Adds some good guys to the environment
	 */
	private void addGoodGuys() {
		Coordinates coord;
		for (int i=0; i<this.nbGoodGuys; i++) {
			coord =  Coordinates.random(this.env.getSizeX(), this.env.getSizeY());
			this.env.addAgent(new GoodGuy(coord, this.minVelocity, this.maxVelocityGG, 
					this.ggRadius, this.angle, this.agentSize, this.env));
		}
	}

	/** Adds a bunch of bad guys
	 */
	private void addBadGuys() {
		Coordinates coord;
		for (int i=0; i<this.nbBadGuys; i++) {
			coord =  Coordinates.random(this.env.getSizeX(), this.env.getSizeY());
			this.env.addAgent(new BadGuy(coord, this.minVelocity, this.maxVelocityBG,
					this.bgRadius, this.angle, this.agentSize, this.env));
		}
	}

	/** Returns true if there are no more good guys
	 * @return gameFinished
	 */
	private boolean gameFinished() {
		return this.env.getTypeAgents("Agent.GoodGuy").size() <= 0;
	}

	/** Runs the game
	 */
	public void run() {
		// Sleeping time (in milliseconds)
		int sleepTime = 14;
		try {
			// Using "currentTimeMillis" rather than "nanoTime" on purpose
			long start = System.currentTimeMillis();
			// While there are still good guys alive
			while(!this.gameFinished()) {
				// Makes the agents move
				this.env.moveAgents();
				// Updates the GUI
				this.gui.update();
				// Sleeps for a given amount of time
				TimeUnit.MILLISECONDS.sleep(sleepTime);
			}
			long end = System.currentTimeMillis();
			long elapsedTime = (long) ((end-start)/1000.0);
			// Displays the game over dialog
			this.gameOver(elapsedTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Game over dialog
	 * @param elapsedTime
	 */
	private void gameOver(long elapsedTime) {
		// Tells the player how long he managed to stay alive
		String text = "Well played, you held for " + elapsedTime + "s.";
		String iconPath = "/assets/gameOver.png";
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(iconPath)));
		Object[] options = {"Quit", "Play again"};
		int n = JOptionPane.showOptionDialog(this.gui, text, "Game Over",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				icon, options, options[0]);
		// If the player wants to quit
		if (n <= 0) {
			this.gui.dispatchEvent(new WindowEvent(this.gui, WindowEvent.WINDOW_CLOSING));
		} else {
			// Else launches a new game
			this.gui.dispose();
			try {
				GameLauncher.main(new String[] {});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
