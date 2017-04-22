package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Game.Game;

/** Allows the user to choose game parameters
 * @author Clément
 */
public class ParamFrame extends JFrame {

	// GUI elements
	private static final long serialVersionUID = 1L;
	private JLabel ggLabel;
	private JLabel bgLabel;
	private JSlider ggSlider;
	private JSlider bgSlider;
	// Game parameters
	private int agentSize;
	private float minVelocity;
	private float maxVelocityGG;
	private float maxVelocityBG;
	private float ggRadius;
	private float bgRadius;
	private float angle;

	public ParamFrame(int nbGoodGuys, int nbBadGuys, int agentSize, float minVelocity, float maxVelocityGG,
			float maxVelocityBG, float ggRadius, float bgRadius, float angle) {
		// =========== Parameters ===========
		this.agentSize = agentSize;
		this.minVelocity = minVelocity;
		this.maxVelocityGG = maxVelocityGG;
		this.maxVelocityBG = maxVelocityBG;
		this.ggRadius = ggRadius;
		this.bgRadius = bgRadius;
		this.angle = angle;
		// ==================================
		// Set the text in the menu bar
		this.setTitle("Flocking Game");
		// Set the dimensions of the frame
		Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)dimension.getWidth()/3;
		int height = (int)dimension.getHeight()/3;
		this.setSize(width, height);
		// Set the relative position of the frame
		this.setLocationRelativeTo(null);
		// Set the default operation when the frame is closed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adds the components
		this.getContentPane().setLayout(new GridLayout(3, 2));
		this.addComponents(nbGoodGuys, nbBadGuys);
		// Sets the frame to visible
		this.setVisible(true);
	}

	/** Builds the labels and sliders
	 * @param nbGoodGuys
	 * @param nbBadGuys
	 */
	private void addComponents(int nbGoodGuys, int nbBadGuys) {
		// Labels
		this.ggLabel = new JLabel("Number of good guys : " + nbGoodGuys, JLabel.RIGHT);
		this.ggLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
		this.bgLabel = new JLabel("Number of bad guys : " + nbBadGuys, JLabel.RIGHT);
		this.bgLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
		this.formatLabel(this.ggLabel);
		this.formatLabel(this.bgLabel);
		// Sliders
		this.ggSlider = this.createGGSlider(nbGoodGuys);
		this.bgSlider = this.createBGSlider(nbBadGuys);
		this.getContentPane().add(this.ggLabel);
		this.getContentPane().add(this.ggSlider);
		this.getContentPane().add(this.bgLabel);
		this.getContentPane().add(this.bgSlider);
		// Validation button
		ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().
				getImage(getClass().getResource("/assets/check.png")));
		JButton validateButton = new JButton(" New Game !");
		validateButton.setIcon(icon);
		validateButton.addMouseListener(new ValidateListener(this));
		this.getContentPane().add(new JLabel(""));
		this.getContentPane().add(validateButton);
	}

	/** Formats the label of the upper panel
	 * @param label
	 */
	private void formatLabel(JLabel label) {
		label.setFont(new Font("Courier New", Font.CENTER_BASELINE, 13));
		label.setOpaque(true);
	}

	/**
	 * @param nbGoodGuys
	 * @return ggSlider
	 */
	private JSlider createGGSlider(int nbGoodGuys) {
		JSlider sizeSlider = new JSlider(JSlider.HORIZONTAL, 10, 200, nbGoodGuys);
		sizeSlider.addChangeListener(new SliderListener(this.ggLabel, "Number of good guys : "));
		sizeSlider.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
		sizeSlider.setMajorTickSpacing(10);
		sizeSlider.setPaintTicks(true);
		return sizeSlider;
	}

	/**
	 * @param nbBadGuys
	 * @return bgSlider
	 */
	private JSlider createBGSlider(int nbBadGuys) {
		JSlider mushroomSlider = new JSlider(JSlider.HORIZONTAL, 0, 50, nbBadGuys);
		mushroomSlider.addChangeListener(new SliderListener(this.bgLabel, "Number of bad guys : "));
		mushroomSlider.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 10));
		mushroomSlider.setMajorTickSpacing(10);
		mushroomSlider.setPaintTicks(true);
		return mushroomSlider;
	}

	/** Returns a list containing the value of the parameters (ie sliders' values)
	 * @return paramList
	 */
	public ArrayList<Integer> getParam() {
		ArrayList<Integer> paramList = new ArrayList<Integer>();
		int nbGoodGuys = this.ggSlider.getValue();
		int nbBadGuys = this.bgSlider.getValue();
		paramList.add(nbGoodGuys);
		paramList.add(nbBadGuys);
		return paramList;
	}

	// ============================================= Change Listener =============================================
	class SliderListener implements ChangeListener {

		private JLabel label;
		private String defaultText;

		public SliderListener(JLabel label, String defaultText) {
			this.label = label;
			this.defaultText = defaultText;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider) e.getSource();
			this.label.setText(this.defaultText + source.getValue());
		}

	}
	// ===========================================================================================================

	// ============================================= Validate Listener =============================================
	class ValidateListener implements MouseListener {

		private ParamFrame paramFrame;

		public ValidateListener(ParamFrame paramFrame) {
			this.paramFrame = paramFrame;
		}

		private void onClick() {
			ArrayList<Integer> paramList = this.paramFrame.getParam();
			int nbGoodGuys = paramList.get(0);
			int nbBadGuys = paramList.get(1);
			int agentSize = this.paramFrame.agentSize;
			float minVelocity = this.paramFrame.minVelocity;
			float maxVelocityGG = this.paramFrame.maxVelocityGG;
			float maxVelocityBG = this.paramFrame.maxVelocityBG;
			float ggRadius = this.paramFrame.ggRadius;
			float bgRadius = this.paramFrame.bgRadius;
			float angle = this.paramFrame.angle;
			// Destroys the parameter frame
			this.paramFrame.dispose();
			// Creates the game and the GUI
			Game game = new Game(nbGoodGuys, nbBadGuys, agentSize, minVelocity, 
					maxVelocityGG, maxVelocityBG, ggRadius, bgRadius, angle);
			// Launches the game in a new thread
			Thread gameThread = new Thread(game);
			gameThread.start();
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			this.onClick();
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			this.onClick();
		}

		@Override
		public void mouseReleased(MouseEvent e) {}

	}
	// ===========================================================================================================

}
