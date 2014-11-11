package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

import clueGame.Card.CardType;


public class ComputerPlayer extends Player {
	
	ArrayList<String> cardsSeen;
	ArrayList<String> peopleCards;
	ArrayList<String> weaponCards;
	ArrayList<String> roomCards;
	private boolean canMakeAccusation;
	private Solution possibleSolution;
	
	public ComputerPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
		cardsSeen = new ArrayList<String>();
		peopleCards = new ArrayList<String>();
		weaponCards = new ArrayList<String>();
		roomCards = new ArrayList<String>();
		canMakeAccusation = false;
	}
	
	public ComputerPlayer() {
		
	}
	public void fillCardArrays(Card c){
		if(c.getCardType() == CardType.PERSON)
			peopleCards.add(c.getName());
		if(c.getCardType() == CardType.WEAPON)
			weaponCards.add(c.getName());
		if(c.getCardType() == CardType.ROOM)
			roomCards.add(c.getName());
	}

	public void makeMove(Board b, int roll){
		b.calcTargets(getX(),getY(),roll);
		BoardCell destination = pickLocation(b.getTargets());
		setX(destination.getRow());
		setY(destination.getCol());
		b.repaint();
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		for (BoardCell b: targets) {
			if (b.isRoom() && ((RoomCell) b).getInitial() != getLastRoomVisited()) {
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

	public boolean isCanMakeAccusation() {
		return canMakeAccusation;
	}

	public void setCanMakeAccusation(boolean canMakeAccusation) {
		this.canMakeAccusation = canMakeAccusation;
	}

	public Solution getPossibleSolution() {
		return possibleSolution;
	}

	public void setPossibleSolution(Solution possibleSolution) {
		this.possibleSolution = possibleSolution;
	}
	
	

	
}
