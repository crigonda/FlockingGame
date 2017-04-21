package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Agent.Agent;
import Environment.Environment;

public class CanvasPanel extends JPanel {

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
	
}
