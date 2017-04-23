package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Agent.Agent;
import Agent.Puller;
import Agent.Repeler;
import Environment.Coordinates;
import Environment.Environment;

/** Panel used as a canvas to draw agents
 * @author Clément
 */
public class CanvasPanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private Image image;
	private Environment environment;

	/** Constructor
	 * @param imagePath
	 * @param agents
	 */
	public CanvasPanel(String imagePath, Environment env) {
		super();
		this.image = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
		this.environment = env;
		this.addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.image, 0, 0, null);
		ArrayList<Agent> agents = this.environment.getAllAgents();
		for(int i=0; i<agents.size(); i++) {
			this.drawAgent(agents.get(i), g);
		}
	}

	/** Draw an agent on the panel
	 * @param agent
	 * @param g
	 */
	private void drawAgent(Agent agent, Graphics g) {
		agent.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point pos = e.getPoint();
		if (SwingUtilities.isLeftMouseButton(e)) {
			this.createPuller(pos);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			this.createRepeler(pos);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		Point pos = e.getPoint();
		if (SwingUtilities.isLeftMouseButton(e)) {
			this.createPuller(pos);
		} else if (SwingUtilities.isRightMouseButton(e)) {
			this.createRepeler(pos);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	/** Creates a puller agent, adds it to the environment
	 * @param pos
	 */
	private void createPuller(Point pos) {
		Coordinates coord = new Coordinates(pos.x, pos.y);
		// Removes the existing puller if there is one
		this.environment.getAgents().remove("Agent.Puller");
		// Adds a puller
		this.environment.addAgent(new Puller(coord, 0, 0, 0, 0, 0, this.environment));
	}
	
	private void createRepeler(Point pos) {
		Coordinates coord = new Coordinates(pos.x, pos.y);
		// Removes the existing puller if there is one
		this.environment.getAgents().remove("Agent.Repeler");
		// Adds a repeler
		this.environment.addAgent(new Repeler(coord, 0, 0, 0, 0, 0, this.environment));
		
	}
}
