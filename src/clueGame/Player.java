package clueGame;

import java.awt.Color;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Map;
public class Player {

	private String name;
	private Color color;
	private int x;
	private int y;
	private ArrayList<Card> myCards;

	
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
		Card card = new Card("","");
		return card;
	}

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

	public ArrayList<Card> getMyCards() {
		return myCards;
	}
	
	public void setMyCards(Card card) {
		myCards.add(card);
	}
	
	
	
}
