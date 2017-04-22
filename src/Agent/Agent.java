package Agent;

import Environment.Coordinates;
import Environment.Environment;

import java.awt.Graphics;

import Environment.Angle;

/** Agent super class
 * @author Clément
 */
public abstract class Agent {
	
	// Used to make the agents turn smoothly
	protected static float maxTurn = (float) (Math.PI/20);
	// Agent attributes
	protected Coordinates position;
	protected float velocity;
	protected float minVelocity;
	protected float maxVelocity;
	protected float sightRadius;
	protected float sightAngle;
	protected Angle heading;
	protected float size;
	// Environment
	protected Environment environment;
	
	/** Default constructor
	 * @param pos
	 * @param vMax
	 * @param size
	 * @param env
	 */
	public Agent(Coordinates pos, float vMin, float vMax, float radius, float angle, float size, Environment env) {
		// Agent attributes
		this.position = pos;
		this.velocity = vMin;
		this.minVelocity = vMin;
		this.maxVelocity = vMax;
		this.sightRadius = radius;
		this.sightAngle = angle;
		this.heading = new Angle();
		this.size = size;
		// Environment
		this.environment = env;
	}
	
	/**
	 * @return position
	 */
	public Coordinates getPosition() {
		return this.position;
	}

	/**
	 * @return velocity
	 */
	public float getVelocity() {
		return this.velocity;
	}

	/**
	 * @return maxVelocity
	 */
	public float getMaxVelocity() {
		return this.maxVelocity;
	}

	/**
	 * @return heading
	 */
	public Angle getHeading() {
		return this.heading;
	}

	/**
	 * @return size
	 */
	public float getSize() {
		return this.size;
	}

	/**
	 * @return environment
	 */
	public Environment getEnvironment() {
		return environment;
	}
	
	/** Returns the class name of an agent
	 * @param agent
	 * @return className
	 */
	public String getAgentClassName() {
		String[] tab = this.getClass().toString().split(" ");
		return tab[1];
	}
	
	/** Moves the agent
	 * @param dx
	 * @param dy
	 */
	public void move(float dx, float dy) {
		this.environment.move(this.position, dx, dy);
	}

	/** Makes the agent go one step forward
	 */
	public void forward() {
		float dx = (float) (this.velocity*Math.cos(this.heading.getAngle()));
		float dy = (float) (this.velocity*Math.sin(this.heading.getAngle()));
		this.environment.move(this.position, dx, dy);
	}
	
	/** Returns true if two agents are colliding, else false
	 * @param agent
	 * @return collideWith
	 */
	public boolean collideWith(Agent agent) {
		return this.position.distance(agent.position) < this.size + agent.size;
	}
	
	/** Applies forces to the agent to change its heading/velocity
	 */
	public abstract void applyForces();
	
	/** Detects collisions and let the agents deal with them
	 */
	public abstract void detectCollisions();
	
	/** Draws an agent on a panel
	 * @param g
	 */
	public abstract void draw(Graphics g);
	
	/** Agent to String
	 */
	public String toString() {
		return "Pos:"+this.position+";H:"+this.heading+";V:"+this.velocity;
	}
	
	/** Equality between two agents
	 * Naive version, assuming two agents can't be at the same exact state at the same moment
	 */
	public boolean equals(Object otherAgent) {
		Agent agent = (Agent) otherAgent;
		boolean samePos = this.position.equals(agent.position);
		boolean sameHead = this.heading.equals(agent.heading);
		boolean sameVel = this.velocity == agent.velocity;
		boolean sameSize = this.size == agent.size;
		return samePos && sameHead && sameVel && sameSize;
	}
	
}
