package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
public abstract class Player {

	public static final int CELL_SIZE = 20;
	private String name;
	private Color color;
	private int x;
	private int y;
	private ArrayList<Card> myCards;
	private char lastRoomVisited;
	public Player() {
		myCards = new ArrayList<Card>();
	}

	
	public Player(String playerName, String playerColor, int playerX, int playerY) {
		name = playerName;
		myCards = new ArrayList<Card>();
		switch(playerColor) {
		case "magenta":
			color = Color.magenta;
			break;
		case "blue":
			color  = Color.blue;
			break;
		case "red":
			color = Color.red;
			break;
		case "green":
			color = Color.green;
			break;
		case "yellow":
			color = Color.yellow;
			break;
		case "white":
			color = Color.white;
			break;
		}
		
		x = playerX;
		y = playerY;

	}
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		ArrayList<Card> testCards = new ArrayList<Card>();
		for (Card c : myCards) {
			if (c.getName().equals(person) || c.getName().equals(room) || c.getName().equals(weapon)) {
				testCards.add(c);
			}
		}
		if (testCards.size() > 0) {
			Random rn = new Random();
			return testCards.get(rn.nextInt(testCards.size()));
		}
		return null;
	}
	public abstract void makeMove(Board b, int Roll);

	public String getName() {
		return name;
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	public void setX(int x) {
		this.x = x;
	}


	public void setY(int y) {
		this.y = y;
	}


	public ArrayList<Card> getMyCards() {
		return myCards;
	}
	
	public void setMyCards(Card card) {
		myCards.add(card);
	}
	
	public void draw(Graphics g){
		g.setColor(color);
		g.fillOval(CELL_SIZE*y, CELL_SIZE*x, CELL_SIZE, CELL_SIZE);
		g.setColor(Color.black);
		g.drawOval(CELL_SIZE*y, CELL_SIZE*x, CELL_SIZE, CELL_SIZE);
	}
	
	public abstract void fillCardArrays(Card c);


	public char getLastRoomVisited() {
		return lastRoomVisited;
	}


	public void setLastRoomVisited(char lastRoomVisited) {
		this.lastRoomVisited = lastRoomVisited;
	}

	

}
