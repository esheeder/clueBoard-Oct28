package clueGame;

import java.awt.Color;
import java.util.Set;


public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
	}
	
	public ComputerPlayer() {
		
	}

	private char lastRoomVisited;

	public BoardCell pickLocation(Set<BoardCell> targets) {
		BoardCell cell = null;
		return cell;
	}
	
	public void createSuggestion() {
		
	}
	
	public void updateSeen(Card seen) {
		
	}
}
