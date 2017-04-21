package Agent;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import Environment.Angle;
import Environment.Coordinates;
import Environment.Environment;

public class GoodGuy extends Agent {

	private float coeffAlignment;
	private float coeffCohesion;
	private float coeffRepulsion;
	
	public GoodGuy(Coordinates pos, float vMin, float vMax, float radius, float angle, int size, Environment env) {
		super(pos, vMin, vMax, radius, angle, size, env);
		this.coeffAlignment = 4;
		this.coeffCohesion = 2;
		this.coeffRepulsion = 2;
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
			// Sums the force vectors
			Coordinates totalVector = new Coordinates(0, 0);
			totalVector.plus(alignmentVector);
			totalVector.plus(cohesionVector);
			totalVector.plus(repulsionVector);
			float newVelocity = totalVector.norm();
			// The agent has a minimum and a maximum speed
			if (newVelocity < this.minVelocity) {
				this.velocity = this.minVelocity;
			} else if (newVelocity > this.maxVelocity) {
				this.velocity = this.maxVelocity;
			} else {
				this.velocity = newVelocity;
			}
			// TODO : use maxTurn as attribute + probably error on value !!
			this.heading.turnTo(new Angle(totalVector), 10);
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
		float dx, dy;
		for (int i=0; i<neighbors.size(); i++) {
			agent = neighbors.get(i);
			dx = (float) (agent.velocity*Math.cos(agent.heading.getAngle()));
			dy = (float) (agent.velocity*Math.sin(agent.heading.getAngle()));
			// TODO : normalize result
			coordList.add(new Coordinates(dx, dy));
		}
		Coordinates alignmentVector = Coordinates.mean(coordList);
		// Multiplication by a coefficient
		alignmentVector.mult(this.coeffAlignment);
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
		cohesionVector.mult(this.coeffCohesion);
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
		repulsionVector.mult(this.coeffRepulsion);
		return repulsionVector;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int) this.position.x, (int) this.position.y, this.size, this.size);
	}
	
}
