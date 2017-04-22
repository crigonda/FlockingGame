package Environment;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map.Entry;

import Agent.Agent;

/** The environment of the agents
 * @author Clément
 */
public class Environment {
	
	private int sizeX;
	private int sizeY;
	private HashMap<String, ArrayList<Agent>> agents;

	/** Builds up an environment for the agents to move in
	 * @param sizeX
	 * @param sizeY
	 */
	public Environment(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.agents = new HashMap<String, ArrayList<Agent>>();
	}
	
	/**
	 * @return sizeX
	 */
	public int getSizeX() {
		return this.sizeX;
	}

	/**
	 * @return sizeY
	 */
	public int getSizeY() {
		return this.sizeY;
	}
	
	/**
	 * @return agents
	 */
	public HashMap<String, ArrayList<Agent>> getAgents() {
		return this.agents;
	}
	
	/** Adds an agent to the environment
	 * @param agent
	 */
	public void addAgent(Agent agent) {
		String className = agent.getAgentClassName();
		// Creates a list to store the agents of the same type, if it does not already exist 
		if (this.agents.get(className) == null) {
			this.agents.put(className, new ArrayList<Agent>());
		}
		this.agents.get(className).add(agent);
	}
	
	/** The list of agents with the given type
	 * @param type
	 * @return agents
	 */
	public ArrayList<Agent> getTypeAgents(String type) {
		return this.agents.get(type);
	}
	
	/** Returns the list of all the agents in the environment
	 * @return allAgents
	 */
	public ArrayList<Agent> getAllAgents() {
		ArrayList<Agent> allAgents = new ArrayList<Agent>();
		for (Entry<String, ArrayList<Agent>> entry : this.agents.entrySet()) {
			allAgents.addAll(entry.getValue());
		}
		return allAgents;
	}
	
	/** Makes all the agents in the environment move
	 */
	public void moveAgents() {
		ArrayList<Agent> agents;
		try {
			for (Entry<String, ArrayList<Agent>> entry : this.agents.entrySet()) {
				agents = entry.getValue();
				for (int i=0; i<agents.size(); i++) {
					// Apply forces on the agent
					agents.get(i).applyForces();
					// Makes the agent move one step forward
					agents.get(i).forward();
					// Detects collisions and deal with them
					agents.get(i).detectCollisions();
				}
			}
		} catch (ConcurrentModificationException e) {}
	}
	
	/** Moves an object in the environment (torus behavior)
	 * @param coord
	 * @param dx
	 * @param dy
	 */
	public void move(Coordinates coord, float dx, float dy) {
		coord.x = (coord.x + dx) % this.sizeX;
		coord.y = (coord.y + dy) % this.sizeY;
		coord.x = coord.x < 0 ? coord.x + this.sizeX : coord.x;
		coord.y = coord.y < 0 ? coord.y + this.sizeY : coord.y;
	}
	
	/** Returns the list of an agent neighbors, within its sight
	 * @param agent
	 * @param radius
	 * @param sightAngle
	 * @param typeName <br>Use empty string ("") as parameter to consider all types of agent
	 * @return neighbors
	 */
	public ArrayList<Agent> neighbors(Agent agent, float radius, float sightAngle, String typeName) {
		ArrayList<Agent> agents;
		// Iterates only on the interesting agents
		agents = !typeName.equals("") ? this.getTypeAgents(typeName) : this.getAllAgents();
		ArrayList<Agent> neighbors = new ArrayList<Agent>();
		Coordinates c, coord = agent.getPosition();
		for (int i=0; i<agents.size(); i++) {
			c = agents.get(i).getPosition();
			// Keeps only agents within a radius and a sight angle
			// The agent itself should not be considered in its own neighborhood
			if (!c.equals(coord) && Environment.isVisible(agent, agents.get(i), radius, sightAngle)) {
				neighbors.add(agents.get(i));
			}
		}
		return neighbors;
	}

	/** Returns true if agent b is visible to agent a (in sight radius and angle)
	 * @param a
	 * @param b
	 * @param sightAngle
	 * @return isVisible
	 */
	public static boolean isVisible(Agent a, Agent b, float radius, float sightAngle) {
		Coordinates c = b.getPosition();
		// In radius
		boolean inRadius = a.getPosition().distance(c) <= radius;
		// In sight
		float angleDiff = a.getHeading().subtract(new Angle(c));
		boolean inSight = Math.abs(angleDiff) <= sightAngle;
		// Is visible
		return inRadius && inSight;
	}
	
}
