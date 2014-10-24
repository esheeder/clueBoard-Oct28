package clueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {

	public DetectiveNotes() {
		setTitle("Detective Notes");
		setSize(500,500);
		setLayout(new GridLayout(3,2));
		JPanel people = createPeople();
		add(people);
		JPanel guessPeople = guessPeople();
		add(guessPeople);
		JPanel rooms = createRooms();
		add(rooms);
		JPanel guessRoom = guessRoom();
		add(guessRoom);
		JPanel weapons = createWeapon();
		add(weapons);
		JPanel guessWeapon = guessWeapon();
		add(guessWeapon);
	}
	
	private JPanel createPeople() {
		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		jp.setLayout(new GridLayout(3,2));
		jp.add(new JCheckBox("Miss Scarlet"));
		jp.add(new JCheckBox("Colonel Mustard"));
		jp.add(new JCheckBox("Mr. Green"));
		jp.add(new JCheckBox("Mrs. White"));
		jp.add(new JCheckBox("Mrs. Peacock"));
		jp.add(new JCheckBox("Professor Plum"));
		return jp;
	}
	
	private JPanel guessPeople() {
		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		JComboBox<String> person = new JComboBox<String>();
		person.addItem("Miss Scarlet");
		person.addItem("Colonel Mustard");
		person.addItem("Mr. Green");
		person.addItem("Mrs. White");
		person.addItem("Mrs. Peacock");
		person.addItem("Professor Plum");
		jp.add(person);
		return jp;
	}
	
	private JPanel createRooms() {
		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		jp.setLayout(new GridLayout(5,2));
		jp.add(new JCheckBox("Kitchen"));
		jp.add(new JCheckBox("Lounge"));
		jp.add(new JCheckBox("Conservatory"));
		jp.add(new JCheckBox("Study"));
		jp.add(new JCheckBox("Billard Room"));
		jp.add(new JCheckBox("Dining Room"));
		jp.add(new JCheckBox("Ballroom"));
		jp.add(new JCheckBox("Hall"));
		jp.add(new JCheckBox("Library"));
		return jp;
	}
	
	private JPanel guessRoom() {
		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		JComboBox<String> room = new JComboBox<String>();
		room.addItem("Kitchen");
		room.addItem("Lounge");
		room.addItem("Conservatory");
		room.addItem("Study");
		room.addItem("Billard Room");
		room.addItem("Dining Room");
		room.addItem("Ballroom");
		room.addItem("Hall");
		room.addItem("Library");
		jp.add(room);
		return jp;
	}
	
	private JPanel createWeapon() {
		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		jp.setLayout(new GridLayout(3,2));
		jp.add(new JCheckBox("Candlestick"));
		jp.add(new JCheckBox("Knife"));
		jp.add(new JCheckBox("Pipe"));
		jp.add(new JCheckBox("Revolver"));
		jp.add(new JCheckBox("Rope"));
		jp.add(new JCheckBox("Wrench"));
		return jp;
	}
	
	private JPanel guessWeapon() {
		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		JComboBox<String> weapon = new JComboBox<String>();
		weapon.addItem("Candlestick");
		weapon.addItem("Knife");
		weapon.addItem("Pipe");
		weapon.addItem("Revolver");
		weapon.addItem("Rope");
		weapon.addItem("Wrench");
		jp.add(weapon);
		return jp;
	}
}
