package GUI;

import javax.swing.JFrame;

import Environment.Environment;

public class EnvironmentFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private CanvasPanel canvasPanel;
	
	/** Constructor
	 * @param env
	 */
	public EnvironmentFrame(Environment env) {
		// Set the text in the menu bar
		this.setTitle("Flocking Game");
		this.setSize(env.getSizeX(), env.getSizeY());
		// Set the relative position of the frame
		this.setLocationRelativeTo(null);
		// Set the default operation when the frame is closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Makes the windows non resizable
		this.setResizable(false);
		// Creates the canvasPanel
		this.canvasPanel = new CanvasPanel("/assets/background.jpg", env);
		this.getContentPane().add(this.canvasPanel);
		// Sets the frame to visible
		this.setVisible(true);
	}
	
	/** Updates the GUI
	 */
	public void update() {
		this.canvasPanel.repaint();
	}
	
}
