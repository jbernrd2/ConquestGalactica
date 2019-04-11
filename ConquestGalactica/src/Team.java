import java.util.LinkedList;

import edu.illinois.cs.cs125.lib.zen.Zen;

public class Team {
	//----------Constructors-------------//
	Team() {
		
	}	
	Team(String Name, String TeamColor) {
		new Team();
		this.name = Name; this.teamColor = TeamColor;
	}
	
	//----------Class Variables----------//
	String name,teamColor,bannerTextColor = "black";
	String tagColor = "black";
	String tagBorderColor = "white";
	
	LinkedList<Ship> ships = new LinkedList<>(); 
	LinkedList<Character> characters = new LinkedList<>();
	
	int numCharacters = 0;
	int maxCharacters = 20;
	int numShips = 0;
	int screenSplit;
	int pTagWidth;
	int pTagHeight = 40;
	int numPTagRows = 5;
	
	int teamNumber;   //Used for banners in charSelect and starting planet
	int bannerLocation;  //What y value is the banner located at
	int bannerHeight = 50;  //How thick is the banner
	int bannerSpacing = 250; //How much space between banners
	
	
	//----------Class Functions----------//
	
	// Character operations
	public void addCharacter(Character c) {
		characters.add(c);
		numCharacters++;
	}

	public void addCharacter(String name) {
		addCharacter(new Character(name));
	}
	
	public void removeCharacter(Character c) {
		for (int i = 0; i < characters.size(); i++) {
			if (characters.get(i).getName().equals(c)) {
				characters.remove(i);
				numCharacters--;
				return;
			}
		}
		
	}

	public void addShip(Ship s) {
		ships.add(s); 
		numShips++;
	}
	
	public void addShip(Planet p) {		
		Ship s = new Ship(p, this);
		ships.add(s);
		numShips++; 
	}
	
	// Drawing Functions 
	public void showTeam() {
		Zen.setColor("black");
		Zen.fillRect(0, teamNumber*250 + 45, screenSplit, bannerHeight);
		Zen.setColor(teamColor);
		Zen.fillRect(0, teamNumber*250 + 45, screenSplit, bannerHeight - 5);
		Zen.setColor("gray");
		Zen.drawText(name,10,teamNumber*250 + 70); // +25 for name shift, +45 for message bar shift
		drawCharacterTags();
	}

	public void drawCharacterTags() {
		int i = 0; int xLoc = 0 , yLoc = bannerLocation + bannerHeight + pTagHeight + 5;
		while (i < characters.size()) {
			Zen.setColor(tagBorderColor);
			Zen.fillRect(xLoc, yLoc, pTagWidth, pTagHeight);
			Zen.setColor(tagColor);
			Zen.fillRect(xLoc + 2, yLoc + 2, pTagWidth - 2, pTagHeight - 2);
			Zen.setColor(tagBorderColor);
			Zen.drawText(characters.get(i).getName(), xLoc + 5, yLoc + pTagHeight/2);
			yLoc += pTagHeight; i++; 
			if (i % numPTagRows  == 0) {  // Shift pTags if necessary
				yLoc = bannerLocation + bannerHeight + pTagHeight + 5; 
				xLoc += pTagWidth;
			}
			
		}
	}
	
	public void drawShips() {
		int i =0; 
		while (i < numShips) {
			ships.get(i).drawShip();
			
			i++;
		}
	}
	
	// Getters
	public String getColor() {
		return teamColor;
	}
	public String getBannerTextColor() {
		return bannerTextColor;
	}
	public String getName() {
		return this.name;
	}
	
	// Setters
	public void setScreenSplit(int i) {
		screenSplit = i;
	}
	public void setPlayerTagWidth(int i) {
		pTagWidth = i;
	}
	public void setTeamNumber(int i) { // Also set bannerLocation
		teamNumber = i;
		bannerLocation = bannerSpacing*i;
		
	}
}

