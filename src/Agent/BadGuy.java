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

	public BadGuy(Coordinates pos, float vMin, float vMax, float radius, float angle, float size, Environment env) {
		super(pos, vMin, vMax, radius, angle, size, env);
	}

	@Override
	public void applyForces() {
		// The bad guy is attracted by nearby good guys
		ArrayList<Agent> neighbors = 
				this.environment.neighbors(this, this.sightRadius, this.sightAngle, "Agent.GoodGuy");
		// Applies flocking forces only if there are neighbors agents
		if (neighbors.size() > 0) {
			ArrayList<Coordinates> coordList = new ArrayList<Coordinates>();
			for(int i=0; i<neighbors.size(); i++) {
				coordList.add(neighbors.get(i).position);
			}
			Coordinates cohesionVector = Coordinates.mean(coordList);
			cohesionVector.minus(this.position);
			float newVelocity = cohesionVector.norm();
			// The agent has a minimum and a maximum speed
			if (newVelocity < this.minVelocity) {
				this.velocity = this.minVelocity;
			} else if (newVelocity > this.maxVelocity) {
				this.velocity = this.maxVelocity;
			} else {
				this.velocity = newVelocity;
			}
			this.heading.turnTo(new Angle(cohesionVector), Agent.maxTurn);
		} else {
			// If there are no good guys around, the bad guy wanders at minimum speed
			this.velocity = this.minVelocity;
		}
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
