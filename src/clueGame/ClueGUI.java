package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class ClueGUI extends JPanel {

	private JTextField whoseTurn;
	private JTextField roll;
	private JTextField guess;
	private JTextField response;

	// Contents of northPanel: Whose turn label and text-box, Next Player
	// and Make Accusation buttons
	private JPanel northPanel() {
		JPanel panel = new JPanel();
		JLabel whoseTurnLabel = new JLabel("Whose Turn?");
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		whoseTurn = new JTextField(15);
		panel.add(whoseTurnLabel);
		panel.add(whoseTurn);
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;

	}
	private JPanel whosePanel() {
		JPanel panel = new JPanel();
		JLabel whoseTurnLabel = new JLabel("Whose Turn?");
		whoseTurn = new JTextField(15);
		panel.add(whoseTurnLabel);
		panel.add(whoseTurn);
		return panel;
	}
	// Contents of rollPanel: Roll label and text-box, and Die border
	private JPanel rollPanel() {
		JPanel panel = new JPanel();
		JLabel rollLabel = new JLabel("Roll");
		roll = new JTextField(5);
		panel.add(rollLabel);
		panel.add(roll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die"));
		return panel;
	}

	// Contents of guessPanel: Guess label and text-box, and Guess border
	private JPanel guessPanel() {
		JPanel panel = new JPanel();
		//JLabel guessLabel = new JLabel("Guess");
		guess = new JTextField(20);
		//panel.add(guessLabel);
		panel.add(guess);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}

	// Contents of guessResultPanel: Response label and text-box, 
	// and Guess Result border
	private JPanel guessResultPanel() {
		JPanel panel = new JPanel();
		//JLabel responseLabel = new JLabel("Response");
		response = new JTextField(20);
		//panel.add(responseLabel);
		panel.add(response);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		return panel;
	}

	public ClueGUI(){
		setLayout(new GridLayout(2,4));
		
		/*
		// Contents of North Layout
		JPanel nPanel = new JPanel();
		nPanel = northPanel();
		add(nPanel);
		*/
		//WhosePanel
		JPanel whosePanel = whosePanel();
		add(whosePanel);
		JButton nextPlayer = new JButton("Next Player");
		JButton makeAccusation = new JButton("Make Accusation");
		add(nextPlayer);
		add(makeAccusation);
		
		// Contents of West Layout
		JPanel wPanel = new JPanel();
		wPanel = rollPanel();
		add(wPanel);


		JPanel cPanel = new JPanel();
		cPanel = guessPanel();
		add(cPanel);


		JPanel ePanel = new JPanel();
		ePanel = guessResultPanel();
		add(ePanel);

	}
	/*
	// main method to display panels
	public static void main(String[] args) {

		ClueGUI gui = new ClueGUI();
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setTitle("Clue");
		gui.setSize(800,200);

		// Contents of North Layout
		JPanel nPanel = new JPanel();
		nPanel = gui.northPanel();
		gui.add(nPanel, BorderLayout.NORTH);

		// Contents of West Layout
		JPanel wPanel = new JPanel();
		wPanel = gui.rollPanel();
		gui.add(wPanel, BorderLayout.WEST);

		// Contents of Center Layout
		JPanel cPanel = new JPanel();
		cPanel = gui.guessPanel();
		gui.add(cPanel, BorderLayout.CENTER);

		// Contents of East Layout
		JPanel ePanel = new JPanel();
		ePanel = gui.guessResultPanel();
		gui.add(ePanel, BorderLayout.EAST);

	}
	 */
}
