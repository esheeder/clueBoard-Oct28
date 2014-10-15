package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	private String name;
	private Color color;
	private int x;
	private int y;
	private ArrayList<Card> myCards;
	
	public Player(String playerName, String playerColor, int playerX, int playerY) {
		name = playerName;
		color = Color.getColor(playerColor);
		x = playerX;
		y = playerY;
	}
	
	public Card disproveSuggestion(String person, String room, String weapon) {
		Card card = new Card();
		return card;
	}
}
