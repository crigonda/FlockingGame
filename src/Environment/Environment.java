package Environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import Agent.Agent;

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
	
	/** Adds an agent to the environment
	 * @param agent
	 */
	public void addAgent(Agent agent) {
		String className = agent.getClass().toString();
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
		for (Entry<String, ArrayList<Agent>> entry : this.agents.entrySet()) {
			agents = entry.getValue();
			for (int i=0; i<agents.size(); i++) {
				agents.get(i).applyForces();
				agents.get(i).forward();
			}
		}
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
	 * @param sameType
	 * @return neighbors
	 */
	public ArrayList<Agent> neighbors(Agent agent, float radius, float sightAngle, boolean sameType) {
		ArrayList<Agent> agents;
		// Iterates only on the interesting agents
		agents = sameType ? this.getTypeAgents(agent.getClass().toString()) : this.getAllAgents();
		ArrayList<Agent> neighbors = new ArrayList<Agent>();
		Coordinates coord = agent.getPosition();
		Coordinates c;
		float angleDiff;
		boolean inRadius = false, inSight = false;
		for (int i=0; i<agents.size(); i++) {
			c = agents.get(i).getPosition();
			inRadius = coord.distance(c) <= radius;
			// Keeps only agents in radius
			// The agent itself should not be considered in its own neighborhood
			if (inRadius && !c.equals(coord)) {
				angleDiff = agent.getHeading().subtract(new Angle(c));
				inSight = Math.abs(angleDiff) <= sightAngle;
				// Keeps only agents within the sight angle
				if (inSight) {
					neighbors.add(agents.get(i));
				}
			}
		}
		return neighbors;
	}
	
}
