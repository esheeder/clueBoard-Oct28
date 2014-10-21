package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	ArrayList<String> cardsSeen;
	
	public ComputerPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
		cardsSeen = new ArrayList<String>();
	}
	
	public ComputerPlayer() {
		
	}

	

	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell b: targets) {
			if (b.isRoom() && ((RoomCell) b).getInitial() != lastRoomVisited) {
				return b;
			}
		}
		int rn = new Random().nextInt(targets.size());
		int i = 0;
		for (BoardCell b: targets) {
			if (i == rn) {
				return b;
			}
			i++;
		}
		return null;
	}
	
	public Solution createSuggestion() {
		return null;
		
	}
	
	public void updateSeen(String seen) {
		cardsSeen.add(seen);
	}
	
	//For testing purposes
	public void setLastRoomVisited(char c) {
		lastRoomVisited = c;
	}
}
