package clueGame;

import java.util.Map;

public class ClueGame {
	private Map<Character, String> rooms;
	private Board board;
	public ClueGame() {
		// TODO Auto-generated constructor stub
	}
	public void loadConfigFiles(){
		board.loadBoardConfig();
	}
	public Board getBoard(){
		return board;
	}

}
