package Agent;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import Environment.Angle;
import Environment.Coordinates;
import Environment.Environment;

/** Tries to kill good guys
 * @author Clément
 */
public class BadGuy extends Agent {

	// Force coefficients
	private static float coeffPursuit = (float) 1;
	private static float coeffRepel = (float) 20;
	
	public BadGuy(Coordinates pos, float vMin, float vMax, float radius, float angle, float size, Environment env) {
		super(pos, vMin, vMax, radius, angle, size, env);
	}

	@Override
	public void applyForces() {
		// The bad guy is attracted by nearby good guys
		ArrayList<Agent> neighbors = 
				this.environment.neighbors(this, this.sightRadius, this.sightAngle, "Agent.GoodGuy");
		// Sum of the force vectors
		Coordinates totalVector = new Coordinates(0, 0);
		// Repeling force
		Coordinates repelingForce = this.repelingForce();
		totalVector.plus(repelingForce);
		// Applies forces only if there are neighbors agents
		if (neighbors.size() > 0) {
			// Pursuit force
			Coordinates pursuitVector = this.pursuitForce(neighbors);
			totalVector.plus(pursuitVector);
		}
		float newVelocity = totalVector.norm();
		// The agent has a minimum and a maximum speed
		if (newVelocity < this.minVelocity) {
			this.velocity = this.minVelocity;
		} else if (newVelocity > this.maxVelocity) {
			this.velocity = this.maxVelocity;
		} else {
			this.velocity = newVelocity;
		}
		// If the vector is not (0, 0)
		if (newVelocity != 0) {
			this.heading.turnTo(new Angle(totalVector), Agent.maxTurn);
		}
	}

	/** Computes the force applied by the nearby good guys
	 * @param neighbors
	 * @return pursuitVector
	 */
	private Coordinates pursuitForce(ArrayList<Agent> neighbors) {
		ArrayList<Coordinates> coordList = new ArrayList<Coordinates>();
		for(int i=0; i<neighbors.size(); i++) {
			coordList.add(neighbors.get(i).position);
		}
		Coordinates pursuitVector = Coordinates.mean(coordList);
		pursuitVector.minus(this.position);
		// Multiplication by a coefficient
		pursuitVector.mult(BadGuy.coeffPursuit);
		return pursuitVector;
	}
	
	/** Computes the force applied by the repeler
	 * @return repelingVector
	 */
	private Coordinates repelingForce() {
		Coordinates repelingVector = new Coordinates(0, 0);
		ArrayList<Agent> list = this.environment.getTypeAgents("Agent.Repeler");
		if (list!=null && list.size()>0) {
			Agent repeler = list.get(0);
			if (Environment.isVisible(this, repeler, Repeler.repelRadius, this.sightAngle)) {
				// Distance should not be equal to 0
				float distance = this.position.distance(repeler.position) + (float) 0.1;
				repelingVector = new Coordinates(this.position);
				repelingVector.minus(repeler.position);
				repelingVector.mult(1/distance);
				// Multiplication by a coefficient
				repelingVector.mult(BadGuy.coeffRepel);
			}
		}
		return repelingVector;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int) this.position.x, (int) this.position.y, (int) this.size, (int) this.size);
	}

	@Override
	public void detectCollisions() {
		// The bad guy eats good guys when close enough
		ArrayList<Agent> list = this.environment.getTypeAgents("Agent.GoodGuy");
		Agent goodGuy;
		if (list!=null) {
			Iterator<Agent> itr = list.iterator();
			while(itr.hasNext()) {
				goodGuy = itr.next();
				if (this.collideWith(goodGuy)) {
					// Eats the good guy
					itr.remove();
					this.size += 0.8;
				}
			}
		}
	}

}
