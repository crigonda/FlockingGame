package Game;

import java.awt.Dimension;
import java.util.concurrent.TimeUnit;

import Agent.BadGuy;
import Agent.GoodGuy;
import Environment.Coordinates;
import Environment.Environment;
import GUI.EnvironmentFrame;

public class Game {

	// Game parameters
	private int nbGoodGuys;
	private int nbBadGuys;
	private int agentSize;
	private float minVelocity;
	private float maxVelocityGG;
	private float maxVelocityBG;
	private float radius;
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
	public Game(int nbGoodGuys, int nbBadGuys, int agentSize, float minVelocity,
			float maxVelocityGG, float maxVelocityBG, float radius, float angle) {
		// Game parameters
		this.nbGoodGuys = nbGoodGuys;
		this.nbBadGuys = nbBadGuys;
		this.agentSize = agentSize;
		this.minVelocity = minVelocity;
		this.maxVelocityGG = maxVelocityGG;
		this.maxVelocityBG = maxVelocityBG;
		this.radius = radius;
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
		System.out.println(size);
		this.env = new Environment(size, size);
	}

	/** Adds some good guys to the environment
	 */
	private void addGoodGuys() {
		Coordinates coord;
		for (int i=0; i<this.nbGoodGuys; i++) {
			coord =  Coordinates.random(this.env.getSizeX(), this.env.getSizeY());
			this.env.addAgent(new GoodGuy(coord, this.minVelocity, this.maxVelocityGG, 
					this.radius, this.angle, this.agentSize, this.env));
		}
	}

	/** Adds a bunch of bad guys
	 */
	private void addBadGuys() {
		Coordinates coord;
		for (int i=0; i<this.nbBadGuys; i++) {
			coord =  Coordinates.random(this.env.getSizeX(), this.env.getSizeY());
			this.env.addAgent(new BadGuy(coord, this.minVelocity, this.maxVelocityBG,
					this.radius, this.angle, this.agentSize, this.env));
		}
	}

	/** Runs the game
	 */
	public void run() {
		// Sleeping time (in milliseconds)
		int sleepTime = 13;
		try {
			while(true) {
				// Makes the agents move
				this.env.moveAgents();
				// Updates the GUI
				this.gui.update();
				// Sleeps for a given amount of time
				TimeUnit.MILLISECONDS.sleep(sleepTime);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* TODO : runner must call these things:
		- detect collisions between good guys and bad guys
		- notify GUI = send new agents positions */
	}

}
