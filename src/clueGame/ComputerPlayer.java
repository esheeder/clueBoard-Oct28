package clueGame;

import java.awt.Color;
import java.util.Set;


public class ComputerPlayer extends Player {
	
	public ComputerPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
	}

	private char lastRoomVisited;

	public void pickLocation(Set<BoardCell> targets) {
		
	}
	
	public void createSuggestion() {
		
	}
	
	public void updateSeen(Card seen) {
		
	}
}
