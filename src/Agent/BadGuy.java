package Agent;

import java.awt.Color;
import java.awt.Graphics;

import Environment.Coordinates;
import Environment.Environment;

/** Tries to kill good guys
 * @author Clément
 */
public class BadGuy extends Agent {

	public BadGuy(Coordinates pos, float vMin, float vMax, float radius, float angle, int size, Environment env) {
		super(pos, vMin, vMax, radius, angle, size, env);
	}
	
	@Override
	public void applyForces() {
		// TODO : implement
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval((int) this.position.x, (int) this.position.y, this.size, this.size);
	}

	@Override
	public void detectCollisions() {
		// TODO : collisions between good guys and bad guys are going to be dealt with
		// in this function !!
	}

}
