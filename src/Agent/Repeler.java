package Agent;

import java.awt.Color;
import java.awt.Graphics;

import Environment.Coordinates;
import Environment.Environment;

/** Repels bad guys
 * @author Clément
 */
public class Repeler extends Agent {

	public static int repelRadius = 100;
	public static float repelerSize = 20;
	
	public Repeler(Coordinates pos, float vMin, float vMax, float radius, float angle, float size, Environment env) {
		super(pos, 0, 0, 0, 0, repelerSize, env);
	}

	@Override
	public void applyForces() {}

	@Override
	public void detectCollisions() {}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawOval((int) this.position.x, (int) this.position.y, (int) this.size, (int) this.size);
	}

}
