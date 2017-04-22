package Environment;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/** 2D Coordinates
 * @author Clément
 */
public class Coordinates {
	
	public float x;
	public float y;
	
	public Coordinates(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Coordinates of a vector from an angle
	 * @param angle
	 */
	public Coordinates(Angle angle) {
		this.x = (float) Math.cos(angle.getAngle());
		this.y = (float) Math.sin(angle.getAngle());
	}
	
	/** Copy constructor
	 * @param c
	 */
	public Coordinates(Coordinates c) {
		this.x = c.x;
		this.y = c.y;
	}
	
	/** Creates a set of random coordinates
	 * @param xMax
	 * @param yMax
	 * @return coordinates
	 */
	public static Coordinates random(int xMax, int yMax) {
		float x = ThreadLocalRandom.current().nextInt(0, xMax+1);
		float y = ThreadLocalRandom.current().nextInt(0, yMax+1);
		return new Coordinates(x, y);
	}
	
	/** Adds two set of coordinates
	 * @param coord
	 */
	public void plus(Coordinates coord) {
		this.x += coord.x;
		this.y += coord.y;
	}
	
	/** Subtracts two set of coordinates
	 * @param coord
	 */
	public void minus(Coordinates coord) {
		this.x -= coord.x;
		this.y -= coord.y;
	}
	
	/** Multiplies the two coordinates by a real number
	 * @param k
	 */
	public void mult(float k) {
		this.x *= k;
		this.y *= k;
	}
	
	/** Returns the scalar product of two set of coordinates
	 * @param coord
	 * @return scalarProduct
	 */
	public float scalar(Coordinates coord) {
		return this.x*coord.x + this.y*coord.y;
	}
	
	/** Computes the norm of a vector, represented by a set of coordinates
	 * @return norm
	 */
	public float norm() {
		return (float) Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	/** Computes the distance from a set of coordinates to another
	 * @param c
	 * @return distance
	 */
	public float distance(Coordinates c) {
		return (float) Math.sqrt(Math.pow(this.x - c.x, 2) + Math.pow(this.y - c.y, 2));
	}
	
	/** Computes the sum of a list of coordinates
	 * @param coordList
	 * @return sum
	 */
	public static Coordinates sum(ArrayList<Coordinates> coordList) {
		Coordinates sum = new Coordinates(0, 0);
		int nbCoord = coordList.size();
		for(int i=0; i<nbCoord; i++) {
			sum.plus(coordList.get(i));
		}
		return sum;
	}
	
	/** Computes the mean of a list of coordinates
	 * @param coordList
	 * @return mean
	 */
	public static Coordinates mean(ArrayList<Coordinates> coordList) {
		Coordinates mean = Coordinates.sum(coordList);
		int nbCoord = coordList.size();
		if (nbCoord != 0) {
			mean.x /= nbCoord;
			mean.y /= nbCoord;
		}
		return mean;
	}
	
	/** Equality between two set of coordinates
	 */
	public boolean equals(Object coord) {
		Coordinates c = (Coordinates) coord;
		return c.x == this.x && c.y == this.y;
	}
	
	/** Coordinates to string
	 */
	public String toString() {
		return "(" + this.x + ";" + this.y + ")";
	}
	
}
