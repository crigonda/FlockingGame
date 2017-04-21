package Environment;

import java.util.concurrent.ThreadLocalRandom;

/** Angle between 0 and 2Pi
 * @author Clément
 *
 */
public class Angle {

	private float angle;
	private static float max = (float) (2*Math.PI);
	
	/** Random angle constructor
	 */
	public Angle() {
		this.angle = ThreadLocalRandom.current().nextFloat()*Angle.max;
	}
	
	/**
	 * @param init
	 */
	public Angle(float init) {
		float temp = init % Angle.max;
		this.angle = temp < 0 ? temp + Angle.max : temp;
	}

	/** Creates an angle from a vector (set of coordinates)
	 * @param vector
	 */
	public Angle(Coordinates vector) {
		float temp = (float) Math.atan2(vector.y, vector.x);
		this.angle = temp < 0 ? temp + Angle.max : temp;
	}
	
	/**
	 * @return angle
	 */
	public float getAngle() {
		return this.angle;
	}
	
	/** Computes the oriented difference with a given angle
	 * @param oldDirection
	 * @return diff
	 */
	public float subtract(Angle angle) {
		float newAngle = this.angle;
		float oldAngle = angle.getAngle();
		float diff = (oldAngle - newAngle) % Angle.max;
		if (Math.abs(diff)>Angle.max/2) {
			diff = diff > 0 ? diff - Angle.max : diff + Angle.max;
		}
		return diff;
	}
	
	/** Turns from a given delta
	 * @param delta
	 * @return newAngle
	 */
	public float turnFrom(float delta) {
		this.angle = (this.angle + delta) % Angle.max;
		this.angle = this.angle < 0 ? this.angle += Angle.max : this.angle;
		return this.angle;
	}
	
	/** Turns to a given direction
	 * @param newDirection
	 * @return newAngle
	 */
	public float turnTo(Angle newDirection, float maxTurn) {
		float diff = this.subtract(newDirection);
		if (Math.abs(diff) > maxTurn) {
			diff = diff < 0 ? -maxTurn : maxTurn;
		}
		this.turnFrom(diff);
		return this.angle;
	}
	
	/** Angle to string
	 */
	public String toString() {
		return this.angle + "rad";
	}
	
}
