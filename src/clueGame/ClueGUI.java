package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


@SuppressWarnings("serial")
public class ClueGUI extends JPanel {

	private JTextField whoseTurn;
	JTextField roll;
	private JTextField guess;
	private JTextField response;
	private JButton nextPlayer;
	private JButton makeAccusation;
	private String currentPlayer;
	private String rollString;
	private Board board;
	private ClueGame game;

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
		//this could be not hard coded, maybe check it later
		whoseTurn.setText("professor plum");
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
	private class ButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == makeAccusation){
				//Must be players turn
				if(!game.isPlayerMustFinish())
					JOptionPane.showMessageDialog(game, "It has to be your turn!");
				else{
					HumanPlayer human = (HumanPlayer) game.getPlayers().get(0);
					JComboBox<Object> peopleCB = new JComboBox<Object>(human.peopleCards.toArray());
					JComboBox<Object> weaponCB = new JComboBox<Object>(human.weaponCards.toArray());
					JComboBox<Object> roomCB = new JComboBox<Object>(human.roomCards.toArray());
					Object[] message = { "Room: ", roomCB, "Person: ", peopleCB, "Weapon: ", weaponCB};
					JOptionPane.showMessageDialog(game, message, "Accusation!", JOptionPane.OK_OPTION);
					String person = peopleCB.getSelectedItem().toString();
					String weapon = weaponCB.getSelectedItem().toString();
					String room = roomCB.getSelectedItem().toString();
					Solution soln = new Solution(person,weapon,room);
					if(game.checkAccusation(soln)){
						Object[] options = {"Ok"};
						int input = JOptionPane.showOptionDialog(game, "Correct Accusation! You Win!", "You don't suck", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
						if(input == 0)
							System.exit(0);
					}else{
						Object[] options = {"Ok"};
						JOptionPane.showOptionDialog(game, "Incorrect", "You suck", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
						game.setPlayerMustFinish(false);
						board.repaint();
					}
				}
			}
			if(e.getSource() == nextPlayer){
				if(game.isPlayerMustFinish()){
					JOptionPane.showMessageDialog(game, "You must finish your turn!");
				} else {
					game.advanceToNextPlayer();
					whoseTurn.setText(game.getPlayers().get(game.getCurrentPlayer()).getName());
					if(game.isPlayerMustFinish()){
						Player humanPlayer = game.getPlayers().get((game.getCurrentPlayer()));
						board.calcTargets(humanPlayer.getX(), humanPlayer.getY(), roll());
						//Paint the targets, wait for click in board
						board.repaint();
					}
					else{
						//Find the player
						ComputerPlayer currentPlayer = (ComputerPlayer) game.getPlayers().get(game.getCurrentPlayer());
						//ComputerPlayer Move
						if(currentPlayer.isCanMakeAccusation()){
							if(game.checkAccusation(currentPlayer.getPossibleSolution())){
								Object[] message = { currentPlayer.getName(), " has won!" };
								Object[] options = {"Ok"};
								int input = JOptionPane.showOptionDialog(game, message, "You suck", JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
								if(input == 0)
									System.exit(0);
							}	
						}
						currentPlayer.makeMove(board,roll());
						//Need to check if the destination is a room
						BoardCell location = board.getCellAt(currentPlayer.getX(), currentPlayer.getY());
						if(location.isRoom()){
							// It's a room, get a create a suggestion and update display
							RoomCell roomLoc = (RoomCell) location;
							String room = game.getRooms().get(roomLoc.getInitial());
							Solution s = currentPlayer.createSuggestion(room);
							//Update the display with the suggestion
							guess.setText(s.person + ", "+s.weapon+", "+s.room);
							Card shown = game.handleSuggestion(s.person, s.room, s.weapon, currentPlayer);
							if(shown == null){
								response.setText("No cards");
								currentPlayer.setCanMakeAccusation(true);
								currentPlayer.setPossibleSolution(s);
							}
							else
								response.setText(shown.toString());
							for(Player p : game.getPlayers()){
								//Iterate through the players to find the suggested, the move
								if(s.person.equals(p.getName())){
									p.setX(currentPlayer.getX());
									p.setY(currentPlayer.getY());
									p.setLastRoomVisited(roomLoc.getInitial());
								}
							}
						}
					}

				}

			}
		}
	}
	public int roll(){
		Random r = new Random();
		int roll = r.nextInt(6)+1;
		//Update roll panel
		this.roll.setText(Integer.toString(roll));
		//Repaint?
		return roll;
	}
	
	
	public JTextField getGuess() {
		return guess;
	}
	public JTextField getResponse() {
		return response;
	}
	public ClueGUI(Board b,ClueGame game){
		this.board = b;
		board.calcAdjacencies();
		this.game = game;
		setLayout(new GridLayout(2,4));

		/*
		// Contents of North Layout
		nPanel = northPanel();
		add(nPanel);
		 */
		//WhosePanel
		JPanel whosePanel = whosePanel();
		add(whosePanel);
		nextPlayer = new JButton("Next Player");
		makeAccusation = new JButton("Make Accusation");
		add(nextPlayer);
		add(makeAccusation);
		nextPlayer.addActionListener(new ButtonListener());
		makeAccusation.addActionListener(new ButtonListener());
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
