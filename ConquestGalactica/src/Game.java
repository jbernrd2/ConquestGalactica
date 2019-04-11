import edu.illinois.cs.cs125.lib.zen.Zen;
import java.util.LinkedList;
import java.util.Stack;
import java.awt.event.KeyEvent;
//-----------------------//
//For the Dark Theme:
//https://raw.github.com/guari/eclipse-ui-theme/master/com.github.eclipseuitheme.themes.updatesite
//-----------------------//
public class Game {

	// Constructors
	public Game() {	
	}
	
	public Game(Planet[] plist) {
		this.planetList=plist; 
		this.state = "mainMenu";
	}
	

	// ---- Class Variables ---- //
	public Planet[] planetList;
	public String state = "mainMenu"; public String lastState;
	public Menu activeMenu;
	public Planet activePlanet;
	public Button activeButton;
	public int clicked = 0;
	public boolean isMouseClicked = false; public boolean isRunning = false;
	public int mouseClickX = 0; public int mouseClickY = 0;
	public int mouseX; public int mouseY;
	public Team[] teams; public int numTeams;
	public String mainState = "setUpGame";
	public String command = "mainMenu"; 
	public int screenSplit = 1150;
	
	public String message = "";
	public float mLoc = 0;
	public int teamIndex = 0;
	public Team mTeam;
	public String activeName = null;
	public LinkedList<String> charQueue = new LinkedList<>();
	
	boolean settingUp = true;
	
	// Menus
	public Menu menu; public Menu mainMenu;
	public Menu newGameMenu; public MultiMenu charSelectMenu;

	// Character Stuff
	public Character[] characters; public int numCharacters = 0;
	
	// ------------------------------------------------------------------ //	
	// ------------------ Drawing/Showing Functions --------------------- //
	// ------------------------------------------------------------------ //
	
	public void drawPlanets() {
		for (int i = 0; i < planetList.length; i++) {
    		if (checkHighlight(planetList[i])) {
    			planetList[i].highlight();
    			planetList[i].showDetails();
    			checkClick(planetList[i]);
    		}
    		planetList[i].draw();
    	}
	}
	
	public void drawPlanets(Planet p) {
		for (int i = 0; i < planetList.length; i++) {
    		if (planetList[i] != p && checkHighlight(planetList[i])) {
    			planetList[i].highlight();
    			planetList[i].showDetails();
    		}
    		planetList[i].draw();
    	}
	}

	public void drawAndCheckPlanets(Planet p) {
		for (int i = 0; i < planetList.length; i++) {
    		if (planetList[i] != p && checkHighlight(planetList[i])) {
    			planetList[i].highlight();
    			planetList[i].showDetails();
    		}
    		planetList[i].draw();
    	}
	}
	
	public void drawAndCheckPlanets() {
		for (int i = 0; i < planetList.length; i++) {
    		if (checkHighlight(planetList[i])) {
    			planetList[i].highlight();
    			planetList[i].showDetails();
    			checkClick(planetList[i]);
    		}
    		planetList[i].draw();
    	}
	}
	
	public void drawTeams() {  // Used for drawing team ships in game
		for (int i = 0; i < numTeams; i++) {
			teams[i].drawShips();
		}
	}
	
	public void drawNeighbors() {
		int i = 0; int l = planetList.length;
		while (i < l) {
			planetList[i].drawNeighbors();
			i++;
		}
		checkBack();
	}
	
				// ---------------------------------------------//
	
	public void showTeams() {
		int i = 0; 
		while (i < teams.length) {
			teams[i].showTeam();
			i++;
		}
	}
	
	public void showMessage() {
		Zen.setColor("black");
		Zen.fillRect(0, 0, 1900, 45);
		if (mTeam == null) {
			Zen.setColor("white");
			Zen.fillRect(0, 2, 1900, 41);
			Zen.setColor("black");
		} else {
			Zen.setColor(mTeam.getColor());
			Zen.fillRect(0, 2, 1900, 41);
			Zen.setColor(mTeam.getBannerTextColor());
		}
		Zen.drawText(message, (int) mLoc, 27);
		mLoc += 1;
	}
	
	public void changeMessage(String S) {
		mLoc = 5; mTeam = null;
		message = S;
	}
	
	public void changeMessage(String S, Team T) {
		mLoc = 5; mTeam = T;
		message = S;
	}
	
	
	// ------------------------------------------------------------------ //	
	// ------------------------- Check Functions ------------------------ //
	// ------------------------------------------------------------------ //
	
 	public void checkClick(Planet p) {
		if(p.checkMouse(mouseX,mouseY)) {
			p.highlight();
			if (isMouseClicked) {
				activePlanet = p;
				this.lastState = (String) state;
				command = p.getAction();
			}
		}
	}
	
	public void checkClick(Button b) {
		if (b.checkButtonClick(this.mouseX, this.mouseY)) {
			b.highlight();
			if (isMouseClicked) {
				this.lastState = (String) state;
				this.state =  b.getAction();
			}
		}		
	}
	
	public void movePlayers(Planet destination, Planet source) {
		Button b; int max = source.getNumInhab();
		CharMenu c = source.getCharMenu();
		for (int i = 0; i < max; i++) {
			b = c.buttonList.get(i);
			if (b.isSelected()) {
				destination.addInhab(new Character(b.getName()));
				source.removeInhab(b.getName());
				i--; max--;
			}
		}
	}
	
	public void checkMove() {
		Planet[] neighbors = activePlanet.getNeighbors();
		for (int i = 0; i < neighbors.length; i++) {
			if (neighbors[i] != null) {
				if (neighbors[i].checkIfClicked(this)) {
					movePlayers(neighbors[i],activePlanet);
					state = "map";
				}
			}
		}	
	}
	
	public boolean checkHighlight(Planet p) {
		float x = p.getX(); float y = p.getY(); float r = p.getR();
		
		float dsquared = (x + r - mouseX)*(x + r - mouseX) + (y + r - mouseY)*(y + r - mouseY);
		if (Math.pow(dsquared, 0.5) <= r) {
			return true;
		} else {
			return false;
		}
	}
	
	public void checkIfSelected(CharMenu c) {
		for (int i = 0; i < c.getNumCharacters(); i++) {
			Button b = c.buttonList.get(i);
			if (this.command != null) {	
				if (this.command.equalsIgnoreCase(b.getAction())) {
					c.buttonList.get(i).select();
					if (c.buttonList.get(i).isSelected()) {
						charQueue.add(b.getName());
					} else {
						charQueue.remove(b.getName());
					}
					this.state = "Move Characters";
				}
			}
		}
	}
	
	public boolean checkEnter()  {
		if (Zen.isVirtualKeyPressed(KeyEvent.VK_ENTER)) {
			return true;
		}
		return false;
	}
	
	public void checkForGameStart() {
		if (checkEnter()) {
			settingUp = false;
		}
	}
	
	// ------------------------------------------------------------------ //	
	// ------------------- Game loops for various states ---------------- //
	// ------------------------------------------------------------------ //
	
	public void setUpGame() {
		activeMenu = mainMenu; state = "mainMenu";
		while(settingUp) {
			Zen.setBackground("light pink");
			doMouseStuff();
			showMessage();
			
			if (this.state.equalsIgnoreCase("mainMenu")) {
				mainMenu.showMenu(this); evalCommand();
			}
			
			if (this.state.equalsIgnoreCase("New Game")) {
				newGameMenu.showMenu(this); evalCommand();
			}
			
			if (this.state.equals("2")) {
				charSelectMenu.showMenu(this);
				teams = new Team[2]; numTeams = 2;
				teams[0] = new Team("Team 1", "Red"); teams[1] = new Team ("Team 2", "light blue");
				this.state = "Team Select"; setScreenSplit(teams);
				setUpTeams();
			}
			
			if (this.state.equals("3")) {
				charSelectMenu.showMenu(this);
				teams = new Team[3]; numTeams = 3;
				teams[0] = new Team("Team 1", "Red"); teams[1] = new Team ("Team 2", "light blue");
				teams[2] = new Team("Team 3", "light Green"); 
				this.state = "Team Select"; setScreenSplit(teams);
				setUpTeams();
			}
			
			if (this.state.equals("4")) {
				charSelectMenu.showMenu(this);
				teams = new Team[4]; numTeams = 4;
				teams[0] = new Team("Team 1", "Red"); teams[1] = new Team ("Team 2", "light blue");
				teams[2] = new Team("Team 3", "light Green"); teams[3] = new Team("Team 4", "Purple");
				this.state = "Team Select"; setScreenSplit(teams);
				setUpTeams(); newMessage(teams[0]);
				
			}
			
			if (this.state.equals("Team Select")) {
				showTeams();
				charSelectMenu.showMenu(this);
				tryAddPlayer(activeName);
				showMessage();
				checkForGameStart();
			}
			
			Zen.flipBuffer();
		}
	}
	
	public void setUpBoard() {
		for (int i = 0; i < teams.length; i++) {
			for (int j = 0; j < teams[i].characters.size(); j++) {
				planetList[i].addInhab(teams[i].characters.get(j));
				teams[i].addShip(planetList[i]);
			}
		}
		state = "map"; command = "map";
	}
	
	public void update() {
		doMouseStuff(); 
		if (this.state == "map") {
			this.drawNeighbors();
			this.drawTeams();
			this.drawAndCheckPlanets();
			evalCommand();
		}
		if (this.state == "menu") {
			this.menu.showMenu(this);
			evalCommand();
		}
		if (this.state == "planetMenu") {
			this.drawNeighbors();
			this.activePlanet.highlight();
			this.drawAndCheckPlanets(activePlanet);
			this.drawTeams();
			this.activePlanet.showMenu(this);
			evalCommand();
			System.out.println(state);
		}
		if (this.state.equalsIgnoreCase("Back")) {
			this.state = "map";
		}
		if (this.state.equalsIgnoreCase("Move Characters")) {
			this.drawNeighbors();
			this.activePlanet.highlight();
			this.drawPlanets(activePlanet);
			this.activePlanet.showCharMenu(this);
			this.checkIfSelected(activePlanet.getCharMenu());
			this.drawTeams();
			//this.checkEnter();
			this.checkMove();
		}
		if (this.state.equalsIgnoreCase("New Game")) {
			this.state = "Team Select";
		}
		if (this.state.equalsIgnoreCase("Move to Ship")) {
			Ship activeShip = activePlanet.getShipClick();
			if (activeShip != null) {
				movePlayers(activePlanet,activeShip);	
			}
		}
		//System.out.println(state);
	}
	
	
	
	// ------------------------------------------------------------------ //
	// ------------------------------------------------------------------ //

	// ----------- Mouse Functions ------------- //

	// Load the menus
	public void loadMenus() {
	
		mainMenu = new Menu(850, 450, 2, "black", "pink", "black");
		mainMenu.addButton("New Game");
	    mainMenu.addButton("Load Game");
	    
	    newGameMenu = new Menu(850, 450, 2, "black", "pink", "black", "Select number of players");
	    newGameMenu.addButton("2 Players", "2");
	    newGameMenu.addButton("3 Players", "3");
	    newGameMenu.addButton("4 Players", "4");
	    
	    String[] charList = new String[] {"Mario","Donkey Kong","Link","Samus","Dark Samus","Yoshi","Kirby","Fox","Pikachu","Luigi","Ness","Captain Falcon","Jigglypuff","Peach","Daisy","Boswer","Ice Climbers","Sheik","Zelda","Dr. Mario","Pichu","Falco","Marth","Lucina","Young Link","Ganondorf","Mewtwo","Roy","Chrom","Mr. Game & Watch","Meta Knight","Pit","Dark Pit","Zero Suit Samus","Wario","Snake","Ike","Pokemon Trainer","Diddy Kong","Lucas","Sonic","King Dedede","Alph","Lucario","R.O.B.","Toon Link","Wolf","Villager","Mega Man","Wii Fit Trainer","Rosaline & Luma","Little Mac","Greninja","Mii Swordfighter","Mii Gunner","Mii Brawler","Palutena","Pac-Man","Robin","Shulk","Bowser Jr.","Duck Hunt","Ryu","Ken","Cloud","Corrin","Bayonetta","Inkling","Ridley","Simon Belmont","Richter","King K. Rool","Isabelle","Incineroar","Piranha Plant","Joker"};
	    charSelectMenu = new MultiMenu(screenSplit, 45 + 2, 2, "black", "orange", "black", 5, 750, 955);
	    charSelectMenu.addButtons(charList); 
	    charSelectMenu.organizeMenu(); 
	    
	}
	
	// ------------ //
	
	public void tryAddPlayer(String name) {
		if (name != null) {
			teams[teamIndex].addCharacter(name);
			teamIndex = (teamIndex + 1) % numTeams; 
			newMessage(teams[teamIndex]);
		}
		mLoc += 0.05;
	}
	
	public void setUpTeams() {
		int i = 0;
		while (i < numTeams) {
			teams[i].setPlayerTagWidth(charSelectMenu.getCalculatedWidth());
			teams[i].setTeamNumber(i);
			i++;
		}
	}
	
	public void updateMouseState() {
		int x = Zen.getMouseClickX(); int y = Zen.getMouseClickY();
		if (mouseClickX != x && mouseClickY != y) {
			isMouseClicked = true;
			mouseClickX = x; mouseClickY = y;
		} else {
			isMouseClicked = false;			
		}
	}
	
	public void doMouseStuff() {
		mouseX = Zen.getMouseX(); mouseY = Zen.getMouseY();
		updateMouseState();
	}
	
	public void checkBack() {
		if (Zen.isVirtualKeyPressed(KeyEvent.VK_B) || Zen.isVirtualKeyPressed(KeyEvent.VK_ESCAPE)) {
			state = "map";
		}
	}
	
	

	
	public void addCharacter(String c) {
		this.characters[numCharacters] = new Character(c);
		//System.out.print("\"" + c + "\"" + ",");
		numCharacters++;
	}

	public void evalCommand() {
		if (command != null) {
			state = command;
		} 
	}
	
	public void setTeam(int i, String name, String color) {
		this.teams[i] = new Team(name,color);
	}

	public void newMessage(Team t) {
		message = t.getName() + ", choose your next fighter ---------- Press enter to start game";
		mTeam = t;
		mLoc = 0;
	}
	

	
	public void setScreenSplit(Team[] T) {
		int i = 0; int max = T.length;
		while (i < max) {
			T[i].setScreenSplit(screenSplit);
			i++;
		}
	}
}
