package clueGame;

import java.awt.Color;
import java.util.Set;


public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	
	public ComputerPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
	}
	
	public ComputerPlayer() {
		
	}

	

	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell b: targets) {
			
		}
		return null;
	}
	
	public void createSuggestion() {
		
	}
	
	public void updateSeen(Card seen) {
		
	}
}
