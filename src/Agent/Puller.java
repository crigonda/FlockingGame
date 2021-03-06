package Agent;

import java.awt.Color;
import java.awt.Graphics;

import Environment.Coordinates;
import Environment.Environment;

/** Pulls good guys towards itself
 * @author Cl�ment
 */
public class Puller extends Agent {
	
	public static int pullingRadius = 250;
	public static float pullerSize = 15;

	public Puller(Coordinates pos, float vMin, float vMax, float radius, float angle, float size, Environment env) {
		super(pos, 0, 0, 0, 0, pullerSize, env);
	}

	@Override
	public void applyForces() {}
	
	@Override
	public void detectCollisions() {}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval((int) this.position.x, (int) this.position.y, (int) this.size, (int) this.size);
	}

}
