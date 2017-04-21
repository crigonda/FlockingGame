package Agent;

import Environment.Coordinates;
import Environment.Environment;

import java.awt.Graphics;

import Environment.Angle;

public abstract class Agent {
	
	// Agent attributes
	protected Coordinates position;
	protected float velocity;
	protected float minVelocity;
	protected float maxVelocity;
	protected float sightRadius;
	protected float sightAngle;
	protected Angle heading;
	protected int size;
	// Environment
	protected Environment environment;
	
	/** Default constructor
	 * @param pos
	 * @param vMax
	 * @param size
	 * @param env
	 */
	public Agent(Coordinates pos, float vMin, float vMax, float radius, float angle, int size, Environment env) {
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
	public int getSize() {
		return this.size;
	}

	/**
	 * @return environment
	 */
	public Environment getEnvironment() {
		return environment;
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
	
	/** Applies forces to the agent to change its heading/velocity
	 */
	public abstract void applyForces();
	
	/** Draws an agent on a panel
	 * @param g
	 */
	public abstract void draw(Graphics g);
	
	/** Agent to String
	 */
	public String toString() {
		return "Pos:"+this.position+";H:"+this.heading+";V:"+this.velocity;
	}
	
}
