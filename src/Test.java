import Environment.Angle;
import Environment.Coordinates;

public class Test {

	public static void main(String[] args) {
		float pi = (float) Math.PI;
		float maxTurn = pi/2;
		Angle a = new Angle(5*pi/4);
		//Angle b = new Angle(7*pi/4);
		//System.out.println(a.turnTo(b, maxTurn));
		float dx = (float) Math.cos(a.getAngle());
		float dy = (float) Math.sin(a.getAngle());
		System.out.println("dx:" + dx + ";dy:" + dy);
		System.out.println(new Coordinates(a));
	}

}
