package clueGame;

import java.awt.Color;
import java.util.ArrayList;



import clueGame.Card.CardType;

public class HumanPlayer extends Player {
	ArrayList<String> peopleCards;
	ArrayList<String> weaponCards;
	ArrayList<String> roomCards;
	
	public HumanPlayer(String playerName, String playerColor, int x, int y) {
		super(playerName, playerColor, x, y);
		peopleCards = new ArrayList<String>();
		weaponCards = new ArrayList<String>();
		roomCards = new ArrayList<String>();
	}
	@Override
	public void fillCardArrays(Card c) {
		if(c.getCardType() == CardType.PERSON)
			peopleCards.add(c.getName());
		if(c.getCardType() == CardType.WEAPON)
			weaponCards.add(c.getName());
		if(c.getCardType() == CardType.ROOM)
			roomCards.add(c.getName());
		}
	
	@Override
	public void makeMove(Board b, int Roll) {
		// TODO Auto-generated method stub
		
	}

}
