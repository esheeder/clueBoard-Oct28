package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Card.CardType;


public class CardPanel extends JPanel{
	ArrayList<Card> playerCards;
	
	public CardPanel(ArrayList<Card> playerCards){
		this.playerCards = playerCards;
		setLayout(new GridLayout(3,1));
		add(roomPanel());
		add(weaponPanel());
		add(personPanel());
	}
	public JPanel roomPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Rooms"));
		for(Card c : playerCards){
			if(c.getCardType() == CardType.ROOM){
				JTextField field = new JTextField(c.getName());
				panel.add(field);
			}
				
		}
		return panel;
	}
	public JPanel weaponPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(),"Weapons"));
		for(Card c : playerCards){
			if(c.getCardType() == CardType.WEAPON){
				JTextField field = new JTextField(c.getName());
				panel.add(field);
			}
				
		}
		return panel;
	}
	public JPanel personPanel(){
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(),"People"));
		for(Card c : playerCards){
			if(c.getCardType() == CardType.PERSON){
				JTextField field = new JTextField(c.getName());
				panel.add(field);
			}
				
		}
		return panel;
	}
}
