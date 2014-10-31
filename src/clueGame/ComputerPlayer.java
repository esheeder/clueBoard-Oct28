package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


public class ComputerPlayer extends Player {
	
	private char lastRoomVisited;
	ArrayList<String> cardsSeen;
	ArrayList<String> peopleCards;
	ArrayList<String> weaponCards;
	ArrayList<String> roomCards;
	
	public ComputerPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
		cardsSeen = new ArrayList<String>();
		peopleCards = new ArrayList<String>();
		weaponCards = new ArrayList<String>();
		roomCards = new ArrayList<String>();
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
	
	public Solution createSuggestion(String room) {
		Solution s = new Solution();
		//Randomly select weapon and person cards we haven't seen before
		while (true) {
			Random rn = new Random();
			int i = rn.nextInt(6);
			if (!cardsSeen.contains(weaponCards.get(i))) {
				s.weapon = weaponCards.get(i);
				break;
			}
		}
		
		while (true) {
			Random rn = new Random();
			int i = rn.nextInt(6);
			if (!cardsSeen.contains(peopleCards.get(i))) {
				s.person = peopleCards.get(i);
				break;
			}
		}
		
		s.room = room;
		
		return s;
	}
	
	public void updateSeen(String seen) {
		cardsSeen.add(seen);
	}
	
	//For testing purposes
	public void setLastRoomVisited(char c) {
		lastRoomVisited = c;
	}
	
	public void fillCardArrays(Card c) {
		if (c.getCardType().equals("PERSON")) {
			peopleCards.add(c.getName());
		} else if (c.getCardType().equals("WEAPON")) {
			weaponCards.add(c.getName());
		} else {
			roomCards.add(c.getName());
		}
	}
}
