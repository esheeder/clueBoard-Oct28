package clueGame;

public class Card {
	private String name;
	public enum CardType{PERSON, WEAPON, ROOM};
	private CardType cardType;
	
	public Card(String type, String cardName) {
		switch(type) {
		case "P":
			cardType = CardType.PERSON;
			break;
		case "W":
			cardType = CardType.WEAPON;
			break;
		case "R":
			cardType = CardType.ROOM;
			break;
		}
		
		name = cardName;
	}

	public String getName() {
		return name;
	}

	public CardType getCardType() {
		return cardType;
	}
	
	
}
