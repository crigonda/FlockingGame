package Agent;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Environment.Angle;
import Environment.Coordinates;
import Environment.Environment;

/** Agent with a flocking++ behaviour
 * @author Clément
 */
public class GoodGuy extends Agent {

	// Used to make the agents turn smoothly
	private static float maxTurn = (float) (Math.PI/20);
	// Force coefficients
	private static float coeffAlignment = (float) 4;
	private static float coeffCohesion = (float) 1.1;
	private static float coeffRepulsion = (float) 13;
	private static float coeffPulling = (float) 0.5;

	public GoodGuy(Coordinates pos, float vMin, float vMax, float radius, float angle, int size, Environment env) {
		super(pos, vMin, vMax, radius, angle, size, env);
	}

	@Override
	public void applyForces() {
		ArrayList<Agent> neighbors = this.environment.neighbors(this, this.sightRadius, this.sightAngle, true);
		// Applies flocking forces only if there are neighbors agents
		if (neighbors.size() > 0) {
			// Alignment force
			Coordinates alignmentVector = this.alignmentForce(neighbors);
			// Cohesion force
			Coordinates cohesionVector = this.cohesionForce(neighbors);
			// Repulsion force
			Coordinates repulsionVector = this.repulsionForce(neighbors);
			// Pulling force
			Coordinates pullingVector = this.pullingForce();
			// Sums the force vectors
			Coordinates totalVector = new Coordinates(0, 0);
			totalVector.plus(alignmentVector);
			totalVector.plus(cohesionVector);
			totalVector.plus(repulsionVector);
			totalVector.plus(pullingVector);
			float newVelocity = totalVector.norm();
			// The agent has a minimum and a maximum speed
			if (newVelocity < this.minVelocity) {
				this.velocity = this.minVelocity;
			} else if (newVelocity > this.maxVelocity) {
				this.velocity = this.maxVelocity;
			} else {
				this.velocity = newVelocity;
			}
			// TODO : use maxTurn as attribute
			this.heading.turnTo(new Angle(totalVector), GoodGuy.maxTurn);
		} else {
			// Lets the agent wander in its current direction
			this.velocity = this.minVelocity;
		}
	}

	/** Computes the alignment force vector
	 * @param neighbors
	 * @return alignmentVector
	 */
	private Coordinates alignmentForce(ArrayList<Agent> neighbors) {
		ArrayList<Coordinates> coordList = new ArrayList<Coordinates>();
		Agent agent;
		Coordinates vect;
		for (int i=0; i<neighbors.size(); i++) {
			agent = neighbors.get(i);
			// Direction vector of the agent
			vect = new Coordinates(agent.heading);
			vect.mult(agent.velocity);
			coordList.add(vect);
		}
		Coordinates alignmentVector = Coordinates.mean(coordList);
		// Multiplication by a coefficient
		alignmentVector.mult(GoodGuy.coeffAlignment);
		return alignmentVector;
	}

	/** Computes the cohesion force vector
	 * @param neighbors
	 * @return cohesionVector
	 */
	private Coordinates cohesionForce(ArrayList<Agent> neighbors) {
		ArrayList<Coordinates> coordList = new ArrayList<Coordinates>();
		for(int i=0; i<neighbors.size(); i++) {
			coordList.add(neighbors.get(i).position);
		}
		Coordinates cohesionVector = Coordinates.mean(coordList);
		cohesionVector.minus(this.position);
		// Multiplication by a coefficient
		cohesionVector.mult(GoodGuy.coeffCohesion);
		return cohesionVector;
	}

	/** Computes the repulsion force vector
	 * @param neighbors
	 * @return repulsionVector
	 */
	private Coordinates repulsionForce(ArrayList<Agent> neighbors) {
		Coordinates coord, tempVector;
		float distance;
		Coordinates repulsionVector = new Coordinates(0, 0);
		for (int i=0; i<neighbors.size() ; i++) {
			coord = neighbors.get(i).position;
			// Distance should not be equal to 0
			distance = coord.distance(this.position) + (float) 0.1;
			tempVector = new Coordinates(this.position);
			tempVector.minus(coord);
			tempVector.mult(1/distance);
			repulsionVector.plus(tempVector);
		}
		// Multiplication by a coefficient
		repulsionVector.mult(GoodGuy.coeffRepulsion);
		return repulsionVector;
	}

	/** Computes the force applied by the puller agent if existing
	 * @return pullingVector
	 */
	private Coordinates pullingForce() {
		Coordinates pullingVector = new Coordinates(0, 0);
		ArrayList<Agent> list = this.environment.getTypeAgents("Agent.Puller");
		// If there is a puller agent in the environment
		if (list!=null) {
			Agent puller = list.get(0);
			// Agents are attracted only if the puller is visible
			if (Environment.isVisible(this, puller, Puller.pullingRadius, this.sightAngle)) {
				// The force is proportional to the distance to the puller
				pullingVector = new Coordinates(puller.position);
				pullingVector.minus(this.position);
				// Multiplication by a coefficient
				pullingVector.mult(GoodGuy.coeffPulling);
			}
		}
		return pullingVector;
	}

	@Override
	public void detectCollisions() {
		// Whenever a good guy pass on the puller, it is destroyed
		ArrayList<Agent> list = this.environment.getTypeAgents("Agent.Puller");
		// If there is a puller agent in the environment
		if (list!=null) {
			Agent puller = list.get(0);
			// Verifies if an agent is close enough to the puller to destroy it
			if (this.position.distance(puller.position) < this.size + Puller.pullerSize) {
				this.environment.getAgents().remove("Agent.Puller");
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int) this.position.x, (int) this.position.y, this.size, this.size);
	}
}
