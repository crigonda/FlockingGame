package Environment;

import java.util.concurrent.ThreadLocalRandom;

public class Angle {

	private float angle;
	
	/** Random angle constructor
	 */
	public Angle() {
		this.angle = ThreadLocalRandom.current().nextInt(0, 360+1);
	}
	
	/**
	 * @param init
	 */
	public Angle(float init) {
		float temp = init % 360;
		this.angle = temp < 0 ? temp + 360 : temp;
	}

	/** Creates an angle from a vector (set of coordinates)
	 * @param vector
	 */
	public Angle(Coordinates vector) {
		this.angle = (float) ((180/Math.PI) * Math.atan2(vector.y, vector.x));
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
	public float subtract(Angle oldDirection) {
		float newAngle = this.angle;
		float oldAngle = oldDirection.getAngle();
		float diff = Math.abs(newAngle - oldAngle) % 360;
		diff = diff > 180 ? 360 - diff : diff;
		float sign = (oldAngle + diff) % 360 == newAngle ? 1 : -1;
		return diff*sign;
	}
	
	/** Turns from a given delta
	 * @param delta
	 * @return newAngle
	 */
	public float turnFrom(float delta) {
		this.angle = (this.angle + delta) % 360;
		if (this.angle < 0) {
			this.angle += 360;
		}
		return this.angle;
	}
	
	/** Turns to a given direction
	 * @param newDirection
	 * @return newAngle
	 */
	public float turnTo(Angle newDirection, int maxTurn) {
		float diff = newDirection.subtract(this);
		if (Math.abs(diff) > maxTurn) {
			diff = diff < 0 ? -maxTurn : maxTurn;
		}
		this.turnFrom(diff);
		return this.angle;
	}
	
	/** Angle to string
	 */
	public String toString() {
		return "" + this.angle + "°";
	}
	
}
